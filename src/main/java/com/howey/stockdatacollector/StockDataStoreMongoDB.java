package com.howey.stockdatacollector;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.UpdateOptions;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/7/22.
 */
public class StockDataStoreMongoDB implements iStockDataStore {
    private String _host;
    private int _port;
    private MongoClient _mongo;

    private String _dbName;

    public StockDataStoreMongoDB(String DBName){
        _dbName=DBName;
    }
    /**
     * Set parameter for target MongoDB
     * @param host, hostname or ip address
     * @param port, port number to connect to
     * @return, true if connect successfully. false if connection failed
     */
    public boolean setHost(String host, int port) {
        _host=host;
        _port=port;
        return connectHost();
    }

    private boolean connectHost(){
        boolean result=false;
        try{
            if(_mongo==null) {
                _mongo = new MongoClient(_host, _port);
            }
            MongoIterable<String> dbList=_mongo.listDatabaseNames();
            result=true;
        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }

    private MongoDatabase connectDb(){
        if(connectHost()){
            return _mongo.getDatabase(_dbName);
        }
        return null;
    }


    @Override
    public List<StockData> getData(String Symbols, Date StartTime, Date EndTime, String DataType) throws Exception {

        return null;
    }

    @Override
    public boolean addData(List<StockData> dataList) throws Exception {
        // Host not connected, then connect.
        if(connectHost()) {
            MongoDatabase db=connectDb();
            for(StockData data: dataList){
                if(!addData(data,db)){
                    System.err.println("Failed when trying to add data, Please try again!" + data.toString());
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean addData(StockData data, MongoDatabase db) throws Exception{
        String tableName=data.Symbol + "_" + data.DataType + "_" + data.Source;
        MongoCollection dataTable= db.getCollection(tableName);
        org.bson.Document query= new  org.bson.Document();
        query.append("DateTime",data.DateTime);

        org.bson.Document dataDoc= new org.bson.Document();
        dataDoc.append("DateTime",data.DateTime);
        dataDoc.append("OpenPrice",data.OpenPrice);
        dataDoc.append("ClosePrice",data.ClosePrice);
        dataDoc.append("AdjustedClose",data.AdjustedClose);
        dataDoc.append("HighPrice",data.HighPrice);
        dataDoc.append("LowPrice", data.LowPrice);
        dataDoc.append("TradeVolume", data.TradeVolume);

        org.bson.Document updateOps=new org.bson.Document();
        updateOps.append("$set",dataDoc);
        UpdateOptions updateOption=new UpdateOptions();
        updateOption.upsert(true);
        dataTable.updateOne(query,updateOps,updateOption);

        return true;
    }





}
