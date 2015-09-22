package com.howey.stockdatacollector.DataCollector;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/3.
 */
public class StockWatchList {
    private static final Logger LOGGER= LoggerFactory.getLogger(StockWatchList.class);
    public ArrayList<String> List=new ArrayList<String>();

    /*
*   Read Stock Watch List Configuration file and Load information from it.
*   the StockWatchList should be provided by parameter of constructor
 */
    public static StockWatchList LoadFromJSON(String jsonPath){
        Gson gson=new Gson();
        LOGGER.info("Trying to read Stock WatchList JSON fie '" + jsonPath +  "'");
        try{
            BufferedReader br = new BufferedReader(new FileReader(jsonPath));
            return gson.fromJson(br, StockWatchList.class);
        }catch (IOException ex){
            LOGGER.error("Failed to open Stock Watch List file: " + jsonPath, ex);
            return null;
        }catch (Exception ex){
            LOGGER.error("Failed to Parse StockWatchList Object: " + jsonPath, ex);
            return null;
        }
    }
}
