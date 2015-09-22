package com.howey.stockdatacollector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by Administrator on 2015/7/7.
 * Gather Stock data from Yahoo finance.
 * this provider is good for get historical data of sticker
 */
public class StockDataProviderYahoo implements iStockDataProvider {
    /**
     * this function return allowed data type for return
     *
     * @return array of data type string
     */
    public String[] getDataTypeList() {
        String[] dataTypeList = {"Historical-Day"};
        return dataTypeList;
    }

    /**
     * This function implement getData method of iStockDataProvider interface.
     * for Yahoo Stock data provider, it can only provide Historical data and Delay Quote data
     *
     * @param Symbols         List of symbols to get data
     * @param StartTime       Start time of time period to get history data
     * @param EndTime         End time of time period to get history data
     * @param requestDataType This is flexible parameter to pass in required data type for provider to determine
     * @return
     */
    public List<StockData> getData(List<String> Symbols, Date StartTime, Date EndTime, String requestDataType) throws Exception {
        String[] allowedDataTypes = getDataTypeList();
        switch (requestDataType) {
            case "Historical-Day":
                List<StockData> stockDataResult = new ArrayList<StockData>();
                for (String symbol : Symbols) {
                    stockDataResult.addAll(getHistoricalDay(symbol, StartTime, EndTime));
    }
    return stockDataResult;
    default:
            throw new Exception(String.format("request Data Type %s is not supported by Yahoo Stock data provider", requestDataType));
}
}

/**
 * This function will use Yahoo YQL to query historical data for symbol
 * the max time period is 1 year. if time period exceed, this function will throw exception
 * select * from yahoo.finance.historicaldata where symbol = "YHOO" and startDate = "2009-09-11" and endDate = "2010-03-10"
 * https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22YHOO%22%20and%20startDate%20%3D%20%222009-09-11%22%20and%20endDate%20%3D%20%222010-03-10%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys
 *
 * @param Symbol
 * @param StartTime
 * @param EndTime
 * @return
 */
private List<StockData> getHistoricalDay(String Symbol, Date StartTime, Date EndTime) throws Exception {
        List<StockData> stockDataResult = new ArrayList<StockData>();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String charset = StandardCharsets.UTF_8.name();
        String queryTemplate = "select * from yahoo.finance.historicaldata where symbol = \"%s\" and startDate = \"%s\" and endDate = \"%s\"";
        String stockQuery = String.format(queryTemplate, Symbol, dateFormater.format(StartTime), dateFormater.format(EndTime));
        String query= URLEncoder.encode(stockQuery, "utf-8");
        String queryUrl = "https://query.yahooapis.com/v1/public/yql?q=" + query + "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        String response = new urlReader().readUrl(queryUrl, charset);

        JsonElement jsonElement=new JsonParser().parse(response);
        JsonElement resultElement=jsonElement.getAsJsonObject().getAsJsonObject("query").get("results");
        JsonObject result=null;
        if(resultElement.isJsonNull() ){
            return stockDataResult;
        }else{
            result=resultElement.getAsJsonObject();
        }
        JsonArray quotes = result.getAsJsonArray("quote");
        for( JsonElement o : quotes){
            String quote=(o == null ? null : o.toString());
            if(quote!=null){
                JsonObject obj = o.getAsJsonObject();
                StockData stockData = new StockData();
                stockData.Symbol= obj.get("Symbol").getAsString();
                stockData.DataType = "DAY";
                stockData.Source="YAHOO_HISTORY";
                stockData.DateTime=dateFormater.parse(obj.get("Date").getAsString());
                stockData.OpenPrice=obj.get("Open").getAsDouble();
                stockData.ClosePrice=obj.get("Close").getAsDouble();
                stockData.HighPrice=obj.get("High").getAsDouble();
                stockData.LowPrice=obj.get("Low").getAsDouble();
                stockData.TradeVolume=obj.get("Volume").getAsInt();
                stockData.AdjustedClose=obj.get("Adj_Close").getAsDouble();
                stockDataResult.add(stockData);
            }
        }
        return stockDataResult;
    }


}