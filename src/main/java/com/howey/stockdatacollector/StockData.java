package com.howey.stockdatacollector;

import java.util.Date;

/**
 * Created by Administrator on 2015/7/7.
 */
public class StockData {
    //this mark where the data is coming from.
    //so data from different source should be stored at different table
    public String Source;

    //stock symbol, MSFT, APPL and so on
    public String Symbol;

    //Type of current data, Year, Month, Weekly, Day, hourly, 30min, 15min, 5min, 1min.
    //Maybe convert this to enum in future depending on the coding needs
    public String DataType;

    //the start time of the data period
    public Date DateTime;

    //Open Price for the period of time
    public double OpenPrice;

    //close price for the period of time
    public double ClosePrice;

    //the highest price within the period
    public double HighPrice;

    //the lowest price within the period
    public double LowPrice;

    //adjusted close price with the extended time, this is used when DataType=DAY
    //should be same as close price if other than DataType=DAY
    public double AdjustedClose;

    //the Trade Volume for the period
    public int TradeVolume;

}
