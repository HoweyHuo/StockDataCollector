package com.howey.stockdatacollector;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/7/22.
 */
public interface iStockDataStore {
    // Set host and port to create connection.
    // Also test if the settings are right by create test connection.
    // Return false if failed to connect
    boolean setHost(String host, int port);

    // Get data about a Symbol from database
    List<StockData> getData(String Symbols, Date StartTime, Date EndTime, String DataType) throws Exception;

    // Add list of Stock Data into database.
    // the function should automatically create database, table if not exists.
    // also, it will perform InsertOrUpdate operation on given data.
    boolean addData(List<StockData> data) throws Exception;
}
