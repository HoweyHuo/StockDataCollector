package com.howey.stockdatacollector.DataCollector;

import org.slf4j.Logger;
import org.slf4j.ILoggerFactory;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/7/31.
 */
public class HistoryDataCollector {
    private static final Logger LOGGER= LoggerFactory.getLogger(HistoryDataCollector.class);

    private volatile boolean running=false;

    private String _watchListPath;
    private StockWatchList _stockWatchList;
    public HistoryDataCollector(String watchListPath){
        LOGGER.info("Start of History Data Collector Service....");
        _watchListPath=watchListPath;
        /* Call start function to start the Data Collection method */
        Start();
    }

    @PostConstruct
    public synchronized void Start(){
        if(running){
            return;
        }
        LOGGER.info("Starting...");
        //Load Stock Watch List file into StockWatchList Object.
        _stockWatchList=StockWatchList.LoadFromJSON(_watchListPath);
        Objects.requireNonNull(_stockWatchList,"Failed to Parse Stock Watch List form Configuration file " + _watchListPath);
        if(_stockWatchList.List.size()==0){
            LOGGER.error("There is no Stock in Stock watch list to monitor");
            return;
        }
        for(String symbol: _stockWatchList.List){
            LOGGER.info(symbol);
        }
        running=true;
    }

    @PreDestroy
    public synchronized void shutdown(){
        if(!running){
            return;
        }
        LOGGER.info("Shutting down...");
        running=false;


    }

    /*
    * the private task class for running worker as  thread.
    */
    private class TaskProcess implements Runnable {
        @Override
        public void run(){

        }
    }

}
