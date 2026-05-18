package com.learn.core.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.learn.common.domain.AccumDistLineDomainData;
import com.learn.common.domain.AllDomainData;
import com.learn.common.domain.AroonDomainData;
import com.learn.common.domain.ChaikinOscillatorDomainData;
import com.learn.common.domain.CmoDomainData;
import com.learn.common.domain.DataRequest;
import com.learn.common.domain.DataResponse;
import com.learn.common.domain.Dividend;
import com.learn.common.domain.DmiDomainData;
import com.learn.common.domain.EmaDomainData;
import com.learn.common.domain.HistoryDomainData;
import com.learn.common.domain.MacdDomainData;
import com.learn.common.domain.OnBalanceVolumeDomainData;
import com.learn.common.domain.RSquaredDomainData;
import com.learn.common.domain.RsiDomainData;
import com.learn.common.domain.StdDomainData;
import com.learn.common.domain.VolumeDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;
import com.learn.core.collection.AccumDistLineDataCollection;
import com.learn.core.collection.AroonDataCollection;
import com.learn.core.collection.ChaikinOscillatorDataCollection;
import com.learn.core.collection.CmoDataCollection;
import com.learn.core.collection.DataCollectionBase;
import com.learn.core.collection.DmiDataCollection;
import com.learn.core.collection.EmaDataCollection;
import com.learn.core.collection.HistoryDataCollection;
import com.learn.core.collection.MacdDataCollection;
import com.learn.core.collection.OnBalanceVolumeDataCollection;
import com.learn.core.collection.RSquaredDataCollection;
import com.learn.core.collection.RsiDataCollection;
import com.learn.core.collection.StdDataCollection;
import com.learn.core.collection.VolumeDataCollection;
import com.learn.core.raw.RawData;
import com.learn.core.raw.RawDataUtils;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;


//import com.learn.persistence.util.SpringServiceFacade;

@Scope("session")
@Named
public class DomainDataUtils {
    private static final Logger LOG = LogManager.getLogger();

    protected static final String QQQ = "qqq";

    protected static List<RawData> QqqRawDataVector;

    private static final String[] INDEXES = { "^gspc", "^dji", "^ixic", "slv", "qqq", };

    @Inject
    private AccessService accessService;

    public List<Dividend> getDividendData(String symbol) {
        List<Dividend> list = new ArrayList<Dividend>();

        try {
            symbol = MbaUtils.formalizeSysmbol(symbol);
        } catch (Exception e) {
            return list;
        }

        File divCvsFile = MbaUtils.getDivCsvFile(symbol);

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(divCvsFile);
            bufferedReader = new BufferedReader(fileReader);

            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                String[] fields = line.split(",");
                if (fields.length != 2) {
                    continue;
                }

                try {
                    float value = Float.parseFloat(fields[1].trim());
                    Dividend dividend = new Dividend();
                    dividend.setDate(fields[0].trim());
                    dividend.setValue(value);
                    list.add(dividend);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Throwable e) {
        }

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (Throwable e) {
            }
        }

        if (fileReader != null) {
            try {
                fileReader.close();
            } catch (Throwable e) {
            }
        }

        return list;
    }

    public DataResponse doRequest(DataRequest dataRequest) {
        LOG.info("Entering doRequest...");

        DataResponse dataResponse = new DataResponse();
        dataResponse.setSuccess(false);

        if (dataRequest == null) {
            String errorMessage = "Request cannot be null.";
            dataResponse.setErrorMessage(errorMessage);

            return dataResponse;
        }

        String symbol = dataRequest.getSymbol();

        try {
            symbol = MbaUtils.formalizeSysmbol(symbol);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            LOG.error(errorMessage, e);
            dataResponse.setErrorMessage(errorMessage);

            return dataResponse;
        }

        File csvFile = MbaUtils.getCsvFile(symbol);

        if (!MbaUtils.isCsvFileUptodate(csvFile)) {
            synchronized (DomainDataUtils.class) {
                if (!MbaUtils.isCsvFileUptodate(csvFile)) {
                    try {
                        LOG.info("Getting csv file out of sync for: " + symbol);
                        HistoryQuoteGetter.getInstance().getDataFromYahoo(symbol);
                        HistoryDividendGetter.getInstance().getDataFromYahoo(symbol);
                        LOG.info("Csv file updated for: " + symbol);
                    } catch (FileNotFoundException e) {
                        String errorMessage = "Symbol not found. Check the symbol: " + symbol;
                        LOG.error(e.getMessage());
                        dataResponse.setErrorMessage(errorMessage);

                        return dataResponse;
                    } catch (Throwable e) {
                        String errorMessage = "Unable to get data for the symbol: " + symbol;
                        LOG.error(errorMessage, e);
                        dataResponse.setErrorMessage(errorMessage);

                        return dataResponse;
                    }
                }
            }
        }

        /**
         * TODO
         * 
         * Cache
         */
        /////////////////////////
        // Cache
        /////////////////////////

        LOG.info("Loading raw data: " + symbol);
        Vector<RawData> rawDataVector = RawDataUtils.getNewRawDataVector(csvFile);
        if (rawDataVector == null || rawDataVector.size() == 0) {
            String errorMessage = "Raw data vector is null or empty.";
            LOG.error(errorMessage);
            dataResponse.setErrorMessage(errorMessage);

            return dataResponse;
        }

        LOG.info("Vector size of raw data: " + rawDataVector.size() + ". Loading domain data...");
        if (rawDataVector.size() < 30) {
            String errorMessage = "The stock must have been trading for at least 30 days. Current data size: " + rawDataVector.size();
            LOG.error(errorMessage);
            dataResponse.setErrorMessage(errorMessage);

            return dataResponse;
        }

        // Log to hot list
        boolean isIndex = false;
        for (String sym : INDEXES) {
            if (symbol.equals(sym)) {
                isIndex = true;
                break;
            }
        }

        if (!isIndex) {
            try {
                accessService.updateAccessHotList(symbol);
            } catch (AppException e) {
                String errorMessage = "Database access exception: " + e.getMessage();
                LOG.error(errorMessage);
                dataResponse.setErrorMessage(errorMessage);

                return dataResponse;
            }
        }

        // Fix missing data from csv file with respect to qqq:
        if (!symbol.equalsIgnoreCase(QQQ)) {
            File qqqCsvFile = MbaUtils.getCsvFile(QQQ);

            if (!MbaUtils.isCsvFileUptodate(qqqCsvFile)) {
                synchronized (DomainDataUtils.class) {
                    if (!MbaUtils.isCsvFileUptodate(qqqCsvFile)) {
                        try {
                            LOG.info("Getting cvs file out of sync for: " + QQQ);
                            HistoryQuoteGetter.getInstance().getDataFromYahoo(QQQ);
                            LOG.info("Cvs file updated for: " + QQQ);

                            QqqRawDataVector = RawDataUtils.getNewRawDataVector(qqqCsvFile);
                        } catch (Throwable e) {
                            String errorMessage = "Unable to get data for the symbol: " + symbol;
                            LOG.error(errorMessage, e);
                            dataResponse.setErrorMessage(errorMessage);

                            return dataResponse;
                        }
                    }
                }
            }

            if (QqqRawDataVector == null) {
                QqqRawDataVector = RawDataUtils.getNewRawDataVector(qqqCsvFile);
            }

            if (QqqRawDataVector != null && QqqRawDataVector.size() > 30) {
                RawData lastQQQRawData = QqqRawDataVector.get(QqqRawDataVector.size() - 1);
                RawData lastRawdata = rawDataVector.get(rawDataVector.size() - 1);
                if (lastRawdata.getDate().compareTo(lastQQQRawData.getDate()) < 0) {
                    for (RawData qqqRawData : QqqRawDataVector) {
                        if (lastRawdata.getDate().compareTo(qqqRawData.getDate()) < 0) {
                            RawData rawData = new RawData();
                            rawData.setDate(qqqRawData.getDate());
                            rawDataVector.add(rawData);
                        }
                    }

                    try {
                        csvFile.delete();
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            }
        }

        AllDomainData allDomainData = new AllDomainData();
        dataResponse.setAllDomainData(allDomainData);

        // 1. History
        int y0Position = ChartConstants.FrameTopEdge + ChartConstants.HistoryChartHeight;
        HistoryDomainData historyDomainData = getHistoryDomainData(rawDataVector, y0Position);
        allDomainData.setHistoryDomainData(historyDomainData);

        // 2. Volume
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        VolumeDomainData volumeDomainData = getVolumeDomainData(rawDataVector, y0Position);
        allDomainData.setVolumeDomainData(volumeDomainData);

        // 3. RSI
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        RsiDomainData rsiDomainData = getRSIDomainData(rawDataVector, y0Position);
        allDomainData.setRsiDomainData(rsiDomainData);

        // 4. DMI
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        DmiDomainData dmiDomainData = getDMIDomainData(rawDataVector, y0Position);
        allDomainData.setDmiDomainData(dmiDomainData);

        // 5. MACD 
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        MacdDomainData macdDomainData = getMACDDomainData(rawDataVector, y0Position);
        allDomainData.setMacdDomainData(macdDomainData);

        // 6. EMA      
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        EmaDomainData emaDomainData = getEMADomainData(rawDataVector, y0Position);
        allDomainData.setEmaDomainData(emaDomainData);

        // 7. Accumulation Distribution
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        AccumDistLineDomainData accumDistLineDomainData = getAccumDistLineDomainData(rawDataVector, y0Position);
        allDomainData.setAccumDistLineDomainData(accumDistLineDomainData);

        // 8. OBV PVT
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        OnBalanceVolumeDomainData onBalanceVolumeDomainData = getOnBalanceVolumeDomainData(rawDataVector, y0Position);
        allDomainData.setOnBalanceVolumeDomainData(onBalanceVolumeDomainData);

        // 9. CMO       
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        CmoDomainData cmoDomainData = getCMODomainData(rawDataVector, y0Position);
        allDomainData.setCmoDomainData(cmoDomainData);

        // 10. Chaikin
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        ChaikinOscillatorDomainData chaikinOscillatorDomainData = getChaikinOscillatorDomainData(rawDataVector, y0Position);
        allDomainData.setChaikinOscillatorDomainData(chaikinOscillatorDomainData);

        // 11. R Squared
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        RSquaredDomainData rSquaredDomainData = getRSquaredDomainData(rawDataVector, y0Position);
        allDomainData.setRSquaredDomainData(rSquaredDomainData);

        // 12. STD
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        StdDomainData stdDomainData = getSTDDomainData(rawDataVector, y0Position);
        allDomainData.setStdDomainData(stdDomainData);

        // 13. Aroon
        y0Position += ChartConstants.ChartSpacer + ChartConstants.IndicatorChartHeight;
        AroonDomainData aroonDomainData = getAroonDomainData(rawDataVector, y0Position);
        allDomainData.setAroonDomainData(aroonDomainData);

        dataResponse.setSuccess(true);

        LOG.info("Domain data loaded. Leaving doRequest.");

        return dataResponse;
    }

    private static AccumDistLineDomainData getAccumDistLineDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new AccumDistLineDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static AroonDomainData getAroonDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new AroonDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static ChaikinOscillatorDomainData getChaikinOscillatorDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new ChaikinOscillatorDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static CmoDomainData getCMODomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new CmoDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static DmiDomainData getDMIDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new DmiDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static EmaDomainData getEMADomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new EmaDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static HistoryDomainData getHistoryDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new HistoryDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static MacdDomainData getMACDDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new MacdDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static OnBalanceVolumeDomainData getOnBalanceVolumeDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new OnBalanceVolumeDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    public static RsiDomainData getRSIDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new RsiDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static RSquaredDomainData getRSquaredDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new RSquaredDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    public static StdDomainData getSTDDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new StdDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

    private static VolumeDomainData getVolumeDomainData(Vector<RawData> rawDataVector, int y0Position) {
        DataCollectionBase dataCollection = new VolumeDataCollection(rawDataVector, y0Position);
        return dataCollection.getDomainData();
    }

}
