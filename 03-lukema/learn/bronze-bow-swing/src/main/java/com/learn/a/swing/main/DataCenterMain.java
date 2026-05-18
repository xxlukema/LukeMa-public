package com.learn.a.swing.main;


import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learn.b.swing.all.common.Setting;
import com.learn.b.swing.all.common.TraderBase;
import com.learn.b.swing.all.concert.ConcertLineFrame;
import com.learn.b.swing.all.concert.SharedValue;
import com.learn.b.swing.all.concert.ZoomSize;
import com.learn.b.swing.all.raw.RawDataUtils;

import lombok.extern.log4j.Log4j2;


@SpringBootApplication(scanBasePackages = { "com.learn" })
@Log4j2
public class DataCenterMain {

  /**
   * Nasdaq 100:
   *    ^NDX --- Yahoo Finance Nasdaq 100
   *    $NDX.X --- TD Ameritrade Nasdaq 100
   *    $NDXL3 --- TD Ameritrade Nasdaq 100 3x Leveraged Index
   */
  private static final String NDX = "NDX";
  private static final String NDX_IDX = "^" + NDX;
  private static final String[] Symbols = { "QTEC", "QQQE", "DHI", "LUV", "UAL", "PBA", "VALE", "PBR", "ASHR", "UNH", NDX_IDX };

  public static void main(String[] args) {

    String symbol = null;
    if (args.length == 0) {
      log.debug("\n\tdr/drw {}\n", Arrays.asList(Symbols));
      return;
    } else {
      symbol = args[0];

      // String initSymbol = symbol;
      symbol = symbol.toUpperCase();

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < symbol.length(); i++) {
        char ch = symbol.charAt(i);
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || ch == '.' || ch == '^') {
          sb.append(ch);
        }
      }

      String normalizedSymbol = sb.toString();

      if (NDX.equals(normalizedSymbol)) {
        normalizedSymbol = NDX_IDX;
      }

      String nSymbol = normalizedSymbol;
      // log.debug("Symbol: entered {}, normalized {}", () -> initSymbol, () -> nSymbol);

      if (nSymbol != null) {
        getDataAndDraw(nSymbol);
      }
    }
  }

  private static final void getDataAndDraw(String symbol) {

    SharedValue.ZOOM_SIZE = ZoomSize.MEDIUM;
    SharedValue.DX = Setting.MM_DX;

    TraderBase.setSymbol(symbol);

    try {
      RawDataUtils.retrieveDataFromRest(TraderBase.getSymbol());

      ConcertLineFrame.init();
      ConcertLineFrame.draw(TraderBase.getSymbol(), null);
    } catch (Exception e) {
      log.error("Exception with symbol: {}", symbol, e);
      return;
    }

    // log.debug("RawDataUtils.getData().size(): {}", () -> RawDataUtils.getData().size());
  }
}
