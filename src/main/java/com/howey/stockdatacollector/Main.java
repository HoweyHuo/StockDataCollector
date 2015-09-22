package com.howey.stockdatacollector;

import com.howey.stockdatacollector.DataCollector.HistoryDataCollector;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try{
            String path = new File(".").getCanonicalPath();
            System.out.println(path);
            //Activate History Data Collector to collect data based on configuration
           // HistoryDataCollector historyDataCollector = new HistoryDataCollector(watchListPath);

/*
        iStockDataProvider sdp_yahoo=new StockDataProviderYahoo();
        List<String> symbols=new ArrayList<String>();
        symbols.add("MSFT");

            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            Date startTime=dateFormater.parse("2014-02-01");
            Date endTime=dateFormater.parse("2015-02-01");
            List<StockData> dataList=sdp_yahoo.getData(symbols, startTime, endTime, "Historical-Day");
            iStockDataStore sds_mongodb=new StockDataStoreMongoDB("Stock_Data_Collector");
            if(sds_mongodb.setHost("192.168.50.3", 27017)){
                if(sds_mongodb.addData(dataList)){
                    System.out.println("Successfully write Stock data into MongoDB");
                }else{
                    System.err.println("Failed to write data to MongoDB");
                }
            }
            System.out.println(dataList.size());
*/
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
