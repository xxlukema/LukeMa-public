package com.learn.b.swing.all.option.regression;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.learn.b.swing.all.common.TodayOHLLast;
import com.learn.b.swing.all.common.Utility;


public class OptionDataProcessor {
  private boolean isVerbose = false;

  private float optimalSpot = 0;

  private float optimalTraderProfit = 0;

  private float lastTrade = -1;

  private boolean isDelayedLastTrade = false;

  private boolean calculateOneMonthOnly = false;

  private boolean printTraderProfit = false;

  private String symbol = null;

  private final List<OptionNode> CALL_LIST = new LinkedList<OptionNode>();

  private final List<OptionNode> PUT_LIST = new LinkedList<OptionNode>();

  private final List<ExpirationDateCallListPutListNode> OPTION_LIST = new LinkedList<ExpirationDateCallListPutListNode>();

  private final String OPTION_URL = "http://finance.yahoo.com/q/op?s=";

  private int callOutOfMoneyCounter = 0;

  static {
    Properties prop = System.getProperties();
    String usr = prop.getProperty("user.name");
    if (usr != null && usr.equals("lm6491")) {
      prop.put("http.proxyHost", "dincauth.sbms.sbc.com");
      prop.put("http.proxyPort", "8080");
    }
  }

  public OptionDataProcessor(String symbol, boolean isVerbose, boolean calculateOneMonthOnly, boolean printTraderProfit) {
    this.isVerbose = isVerbose;
    this.calculateOneMonthOnly = calculateOneMonthOnly;
    this.printTraderProfit = printTraderProfit;

    if (symbol != null) {
      this.symbol = symbol.trim();
    }

    retrieveLastTrade();
    getDataFromYahoo();
  }

  public void solveForSpotPrint() {
    solveForSpot();
    printOptimalSpotResult();
  }

  public void listOptionTimeValueSolve() {
    listOptionTimeValue();
    System.out.println("");
    solveForSpotPrint();
    System.out.println("");
  }

  public void listOptionTimeValue() {
    for (Iterator<ExpirationDateCallListPutListNode> it = OPTION_LIST.iterator(); it.hasNext();) {
      ExpirationDateCallListPutListNode node = it.next();

      System.out.print("\n============ Expire at close " + node.getExpirationDate() + " ============");

      String outStr = "   Last Trade: " + Utility.lpadSpace(Utility.float2str(lastTrade, 2), 6);
      if (isDelayedLastTrade) {
        outStr += " (*)";
      }
      System.out.println(outStr);

      System.out.println(" Symbol    Strike    Bid    Ask BreakEven Spread TimeValue Spread+TV");
      List<OptionNode> callList = node.getCallList();
      callOutOfMoneyCounter = 0;
      for (Iterator<OptionNode> callIt = callList.iterator(); callIt.hasNext() && callOutOfMoneyCounter < 1;) {
        OptionNode on = (OptionNode) callIt.next();
        listCallNode(on);
      }

      List<OptionNode> putList = node.getPutList();
      System.out.println(" -  -  -  -  -  -  -  -  -  -  - Put -  -  -  -  -  -  -  -  -  -  -");
      OptionNode lastNode = null;
      boolean putOutOfMoneyNodePrinted = false;
      for (Iterator<OptionNode> putIt = putList.iterator(); putIt.hasNext();) {
        OptionNode on = (OptionNode) putIt.next();

        if (on.getStrike() > lastTrade) {
          if (lastNode != null && !putOutOfMoneyNodePrinted) {
            listPutNode(lastNode);
            putOutOfMoneyNodePrinted = true;
          }

          listPutNode(on);
        }
        lastNode = on;
      }
    }
  }

  public void listCallNode(OptionNode on) {
    if (on.getStrike() > lastTrade) {
      callOutOfMoneyCounter++;
    }

    String outStr = Utility.rpadSpace(on.getOptionSymbol(), 10) + Utility.lpadSpace(Utility.float2str(on.getStrike(), 2), 7);

    if (on.getBid() == -1) {
      outStr += Utility.lpadSpace("N/A", 7);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(on.getBid(), 2), 7);
    }

    if (on.getAsk() == -1) {
      outStr += Utility.lpadSpace("N/A", 7);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(on.getAsk(), 2), 7);
    }

    float breakEven = on.getStrike() + on.getAsk();
    if (on.getAsk() == -1) {
      outStr += Utility.lpadSpace("N/A", 10);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(breakEven, 2), 10);
    }

    // spread
    if (on.getAsk() == -1 || on.getBid() == -1) {
      outStr += Utility.lpadSpace("N/A", 7);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str((on.getAsk() - on.getBid()), 2), 7);
    }

    float timeValue = breakEven - lastTrade;
    if (on.getAsk() == -1) {
      outStr += Utility.lpadSpace("N/A", 10);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(timeValue, 2), 10);
    }

    if (on.getAsk() == -1 || on.getBid() == -1) {
      outStr += Utility.lpadSpace("N/A", 10);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str((on.getAsk() - on.getBid() + timeValue), 2), 10);
    }

    outStr += " C";

    if (on.getStrike() > lastTrade) {
      outStr += " OOM$";
    }

    System.out.println(outStr);
  }

  public void listPutNode(OptionNode on) {
    String outStr = Utility.rpadSpace(on.getOptionSymbol(), 10) + Utility.lpadSpace(Utility.float2str(on.getStrike(), 2), 7);

    if (on.getBid() == -1) {
      outStr += Utility.lpadSpace("N/A", 7);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(on.getBid(), 2), 7);
    }

    if (on.getAsk() == -1) {
      outStr += Utility.lpadSpace("N/A", 7);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(on.getAsk(), 2), 7);
    }

    float breakEven = on.getStrike() - on.getAsk();
    if (on.getAsk() == -1) {
      outStr += Utility.lpadSpace("N/A", 10);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(breakEven, 2), 10);
    }

    // spread
    if (on.getAsk() == -1 || on.getBid() == -1) {
      outStr += Utility.lpadSpace("N/A", 7);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str((on.getAsk() - on.getBid()), 2), 7);
    }

    float timeValue = lastTrade - breakEven;
    if (on.getAsk() == -1) {
      outStr += Utility.lpadSpace("N/A", 10);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str(timeValue, 2), 10);
    }

    if (on.getAsk() == -1 || on.getBid() == -1) {
      outStr += Utility.lpadSpace("N/A", 10);
    } else {
      outStr += Utility.lpadSpace(Utility.float2str((on.getAsk() - on.getBid() + timeValue), 2), 10);
    }

    outStr += " P";

    if (on.getStrike() < lastTrade) {
      outStr += " OOM$";
    }

    System.out.println(outStr);
  }

  protected boolean isVerbose() {
    return isVerbose;
  }

  protected String getWholePage(String urlStr) {
    /*
    if (isVerbose())
    {
     System.out.println(urlStr);
    }
    */

    String page = "";
    String line = null;

    try {
      URL url = new URI(urlStr).toURL();
      URLConnection urlConn = url.openConnection();

      InputStream is = urlConn.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      while ((line = br.readLine()) != null) {
        page += line + "\n";
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }

    /*
    if (isVerbose())
    {
     System.out.println("Page downloaded.");
    }
    */

    return page;
  }

  protected String getValue(String line, String beginStr, String endStr) {
    if (line == null) {
      return null;
    }

    int beginIndex = line.indexOf(beginStr);
    int endIndex = line.indexOf(endStr, beginIndex);

    if (beginIndex == -1 || endIndex == -1) {
      return null;
    }

    return line.substring(beginIndex + beginStr.length(), endIndex).trim();
  }

  protected String[] readAvailableMonths(String page) {
    List<String> monthList = new LinkedList<String>();

    String beginMonthSectionStr = "View By Expiration:";
    String endMonthSectionStr = "CALL OPTIONS";

    String monthSectionLine = getValue(page, beginMonthSectionStr, endMonthSectionStr);

    String beginStr = "&amp;m=";
    String endStr = "\">";

    String month = getValue(monthSectionLine, beginStr, endStr);

    while (month != null) {
      monthList.add(month);

      String tmpStr = beginStr + month + endStr;
      int tmpIndex = monthSectionLine.indexOf(tmpStr);

      monthSectionLine = monthSectionLine.substring(tmpIndex + tmpStr.length());
      month = getValue(monthSectionLine, beginStr, endStr);
    }

    return (String[]) monthList.toArray(new String[0]);
  }

  protected String retrieveLast() {
    return TodayOHLLast.retrieveLast(symbol);
  }

  protected String retrieveLastDelayed() {
    return TodayOHLLast.retrieveLastDelayed(symbol);
  }

  public void getDataFromYahoo() {
    String urlSpec = OPTION_URL + symbol;
    String page = getWholePage(urlSpec);

    String noOptionData = "There is no  data available for " + symbol.toUpperCase();
    if (page.indexOf(noOptionData) != -1) {
      System.out.println("\n\tThere is no option data for " + symbol.toUpperCase() + "\n");

      return;
    }

    String notValidSymbol = "is not a valid ticker symbol";
    if (page.indexOf(notValidSymbol) != -1) {
      System.out.println("\n\t'" + symbol + "' " + notValidSymbol + "\n");

      return;
    }

    extractDataFromPage(page);

    if (!calculateOneMonthOnly) {
      String[] months = readAvailableMonths(page);
      for (int i = 0; i < months.length; i++) {
        urlSpec = OPTION_URL + symbol + "&m=" + months[i];
        page = getWholePage(urlSpec);

        extractDataFromPage(page);
      }
    }

    /*
    if (isVerbose())
    {
     printLists();
    }
    */
  }

  private void extractDataFromPage(String page) {
    String expirationDate = readExpirationDate(page);
    page = chopOneTable(page);
    page = chopOneTable(page);
    addCallPutDataFromPageToLists(page, expirationDate);
  }

  protected void fillTheCallPutLists() {
    if (CALL_LIST.size() > 0 && PUT_LIST.size() > 0) {
      return;
    }

    for (Iterator<ExpirationDateCallListPutListNode> it = OPTION_LIST.iterator(); it.hasNext();) {
      ExpirationDateCallListPutListNode node = it.next();

      CALL_LIST.addAll(node.getCallList());
      PUT_LIST.addAll(node.getPutList());
    }
  }

  protected void printLists() {
    for (Iterator<ExpirationDateCallListPutListNode> it = OPTION_LIST.iterator(); it.hasNext();) {
      ExpirationDateCallListPutListNode node = it.next();

      System.out.println("############ Expire at close " + node.getExpirationDate() + " ############");

      System.out.println("############ CALL LIST ############");
      List<OptionNode> callList = node.getCallList();
      for (Iterator<OptionNode> callIt = callList.iterator(); callIt.hasNext();) {
        OptionNode on = (OptionNode) callIt.next();
        System.out.println(on.toString());
      }

      List<OptionNode> putList = node.getPutList();
      System.out.println("############ PUT  LIST ############");
      for (Iterator<OptionNode> putIt = putList.iterator(); putIt.hasNext();) {
        OptionNode on = (OptionNode) putIt.next();
        System.out.println(on.toString());
      }
    }
  }

  protected void printOptimalSpotResult() {
    if (lastTrade > 0) {
      float growth = optimalSpot - lastTrade;
      float growthRate = (float) (growth / lastTrade) * 100;
      printSpotResult(optimalSpot, lastTrade, growthRate, growth);
    } else {
      System.out.println("\n\t###### Last trade price is not avlid. ######\n");
    }

    if (isVerbose() || printTraderProfit) {
      System.out.println("Minimized Trader Profit: $" + (int) (optimalTraderProfit / 1000000) + " million.");
    }
  }

  protected void retrieveLastTrade() {
    SimpleDateFormat weekSDF = new SimpleDateFormat("EEE", Locale.US);
    SimpleDateFormat hourMinuteSDF = new SimpleDateFormat("HH:mm", Locale.US);
    Date now = Calendar.getInstance().getTime();

    String nowWeek = weekSDF.format(now);
    Date tradeStartTime = null;
    Date tradeCloseTime = null;

    try {
      tradeStartTime = hourMinuteSDF.parse("08:29");
    } catch (Throwable t) {
      t.printStackTrace();
    }

    try {
      tradeCloseTime = hourMinuteSDF.parse("15:01");
    } catch (Throwable t) {
      t.printStackTrace();
    }

    Date tmpNow = null;
    try {
      tmpNow = hourMinuteSDF.parse(hourMinuteSDF.format(now));
    } catch (Throwable t) {
      t.printStackTrace();
    }

    if (tmpNow == null) {
      return;
    }

    if ((!nowWeek.equals("Sat") && !nowWeek.equals("Sun"))
        && (tmpNow.compareTo(tradeStartTime) > 0 && tmpNow.compareTo(tradeCloseTime) < 0)) {
      try {
        String strLast = retrieveLast();
        lastTrade = Float.parseFloat(strLast);
      } catch (Throwable t) {
        retrieveLastDelayedAsFloat();
      }
    } else {
      retrieveLastDelayedAsFloat();
    }
  }

  protected void retrieveLastDelayedAsFloat() {
    String strLast = retrieveLastDelayed();

    try {
      lastTrade = Float.parseFloat(strLast);
      isDelayedLastTrade = true;
    } catch (Throwable tt) {
      System.out.println(",  Delayed Last: " + strLast + "\n");

      tt.printStackTrace();
    }
  }

  protected void solveForSpot() {
    fillTheCallPutLists();

    if (CALL_LIST.size() == 0 || PUT_LIST.size() == 0) {
      System.out.println("Not enough data to solve. Calls = " + CALL_LIST.size() + ", Puts = " + PUT_LIST.size());

      return;
    }

    float dSpot = 0.005f;

    float leftSpot = 1;
    float leftTraderProfit = calculateTraderProfit(leftSpot);

    float rightSpot = 300;
    float rightTraderProfit = calculateTraderProfit(rightSpot);

    float tmpSpot = 0;
    float tmpTraderProfit = 0;

    float tmpSpot1 = 0;
    float tmpTraderProfit1 = 0;

    float regressionDone = 0.0001f;

    float profitPercentage = 0;

    for (int iteratorCounter = 0; iteratorCounter < 100; iteratorCounter++) {
      profitPercentage = (float) (Math.abs(1.0 - rightTraderProfit / leftTraderProfit));

      if (profitPercentage < regressionDone) {
        optimalSpot = tmpSpot;
        optimalTraderProfit = tmpTraderProfit;

        break;
      }

      tmpSpot = (float) ((leftSpot + rightSpot) / 2);
      tmpTraderProfit = calculateTraderProfit(tmpSpot);

      tmpSpot1 = tmpSpot + dSpot;
      tmpTraderProfit1 = calculateTraderProfit(tmpSpot1);

      /*
      if (isVerbose())
      {
      System.out.println("Trying Spot: "+tmpSpot+"\t\tTrader Profit: "+tmpTraderProfit);
      }
      */

      if (leftTraderProfit < rightTraderProfit) {
        /** Case 1:
        *                   Right
        *       Left
        *            tmp
        */
        if (tmpTraderProfit < rightTraderProfit) {
          // System.out.println("Case 1");
          if (tmpTraderProfit < tmpTraderProfit1) {
            rightSpot = tmpSpot;
            rightTraderProfit = tmpTraderProfit;
          } else {
            leftSpot = tmpSpot;
            leftTraderProfit = tmpTraderProfit;
          }
        }
        /** Case 2:
        *            tmp
        *                   Right
        *       Left
        */
        else {
          System.out.println("Case 2");
          tmpSpot = leftSpot;
          tmpTraderProfit = leftTraderProfit;

          leftSpot = 2 * leftSpot - rightSpot;
          if (leftSpot < 0) {
            leftSpot = 0;
          }
          leftTraderProfit = calculateTraderProfit(leftSpot);

          rightSpot = tmpSpot;
          rightTraderProfit = tmpTraderProfit;
        }
      } else {
        /** Case 3:
        *       Left
        *                   Right
        *            tmp
        */
        if (tmpTraderProfit < leftTraderProfit) {
          // System.out.println("Case 3");
          if (tmpTraderProfit < tmpTraderProfit1) {
            rightSpot = tmpSpot;
            rightTraderProfit = tmpTraderProfit;
          } else {
            leftSpot = tmpSpot;
            leftTraderProfit = tmpTraderProfit;
          }
        }
        /** Case 4:
        *            tmp
        *       Left
        *                   Right
        */
        else {
          System.out.println("Case 4");
          tmpSpot = rightSpot;
          tmpTraderProfit = rightTraderProfit;

          rightSpot = 2 * rightSpot - leftSpot;
          rightTraderProfit = calculateTraderProfit(rightSpot);

          leftSpot = tmpSpot;
          leftTraderProfit = tmpTraderProfit;
        }
      }
    }
  }

  protected float calculateTraderProfit(float spot) {
    float traderProfit = 0;

    for (Iterator<OptionNode> it = CALL_LIST.iterator(); it.hasNext();) {
      OptionNode soi = (OptionNode) it.next();
      traderProfit += (float) (Math.max(0, spot - soi.getStrike()) * soi.getOpenInterest());
    }

    for (Iterator<OptionNode> it = PUT_LIST.iterator(); it.hasNext();) {
      OptionNode soi = (OptionNode) it.next();
      traderProfit += (float) (Math.max(0, soi.getStrike() - spot) * soi.getOpenInterest());
    }

    return traderProfit * 100;
  }

  protected void addCallPutDataFromPageToLists(String page, String expirationDate) {
    String beginCallSectionStr = "CALL OPTIONS";
    String endCallSectionStr = "PUT OPTIONS";

    String callLines = getValue(page, beginCallSectionStr, endCallSectionStr);
    List<OptionNode> callList = buildNodeListFromTableData(callLines, expirationDate);

    String beginPutSectionStr = "PUT OPTIONS";
    String endPutSectionStr = "Expand to Straddle View";

    String putLines = getValue(page, beginPutSectionStr, endPutSectionStr);
    List<OptionNode> putList = buildNodeListFromTableData(putLines, expirationDate);

    ExpirationDateCallListPutListNode node = new ExpirationDateCallListPutListNode(expirationDate, callList, putList);

    OPTION_LIST.add(node);
  }

  protected List<OptionNode> buildNodeListFromTableData(String lines, String expirationdate) {
    List<OptionNode> list = new LinkedList<OptionNode>();

    String beginColumnStr = "<td class=";
    String beforeValueStr = ">";
    String afterValueStr = "</";

    while (true) {
      String strike = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      if (strike == null) {
        break;
      }

      try {
        Float.parseFloat(strike.trim().replaceAll("[,]", ""));
      } catch (Throwable t) {
        lines = chopOneTableRow(lines);
        continue;
      }

      lines = chopOneTableColumn(lines);

      String optionSymbol = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      String last = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      String change = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      String bid = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      String ask = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      String volume = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      String openInterest = readColumnValue(lines, beginColumnStr, beforeValueStr, afterValueStr);
      lines = chopOneTableColumn(lines);

      OptionNode on = new OptionNode(expirationdate, strike, optionSymbol, last, change, bid, ask, volume, openInterest);
      list.add(on);
    }

    return list;
  }

  protected String readColumnValue(String line, String beginColumnStr, String beforeValueStr, String afterValueStr) {
    if (line == null) {
      return null;
    }

    int beginColumnIndex = line.indexOf(beginColumnStr);
    int afterValueIndex = line.indexOf(afterValueStr, beginColumnIndex);
    int beforeValueIndex = line.lastIndexOf(beforeValueStr, afterValueIndex);

    if (beginColumnIndex == -1 || afterValueIndex == -1 || beforeValueIndex == -1) {
      return null;
    }

    return line.substring(beforeValueIndex + beforeValueStr.length(), afterValueIndex);
  }

  protected String readExpirationDate(String line) {
    String beforeValueStr = "Expire at close";
    String afterValueStr = "</";

    return getValue(line, beforeValueStr, afterValueStr).substring(5);
  }

  protected String chopOneTableColumn(String line) {
    String delim = "</td>";

    return chopper(line, delim);
  }

  protected String chopOneTableRow(String line) {
    String delim = "</tr>";

    return chopper(line, delim);
  }

  protected String chopOneTable(String line) {
    String delim = "</table>";

    return chopper(line, delim);
  }

  protected String chopper(String line, String delim) {
    int indexOfDelim = line.indexOf(delim);

    if (indexOfDelim == -1) {
      return line;
    } else {
      return line.substring(indexOfDelim + delim.length());
    }
  }

  public void printSpotResult(float spot, float lastTrade, float growthRate, float growth) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.US);
    Date now = Calendar.getInstance().getTime();
    String nowStr = sdf.format(now);

    String outStr = nowStr + Utility.rpadSpace(symbol.toUpperCase(), 5) + " Optimal: " + Utility.lpadSpace(Utility.float2str(spot, 2), 6)
        + " Last: "
        + Utility.lpadSpace(Utility.float2str(lastTrade, 2), 6) + " R: " + Utility.lpadSpace(Utility.float2str(growthRate, 2), 6) + "% "
        + Utility.lpadSpace(Utility.float2str(growth, 2), 6);

    if (isDelayedLastTrade) {
      outStr += " (*)";
    }

    if (calculateOneMonthOnly) {
      outStr += " 1mth";
    }

    System.out.println(outStr);
  }
}
