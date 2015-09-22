package com.howey.stockdatacollector;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/7/3.
 */
public interface iStockDataProvider {
    /**
     * This function return history data of a stock.
     * This is the default function that return day by data historic data
     * @param Symbols   List of Stock Symbol to gather Stock data
     * @param StartTime Start time of time period to get history data
     * @param EndTime   End time of time period to get history data
     * @param DataType  This is flexible parameter to pass in required data type for provider to determine
     * @return           A list of history stock data.
     */
    List<StockData> getData(List<String> Symbols, Date StartTime, Date EndTime, String DataType) throws Exception;
    String[] getDataTypeList();
}