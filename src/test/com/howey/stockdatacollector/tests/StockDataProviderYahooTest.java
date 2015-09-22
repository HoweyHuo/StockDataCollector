package com.howey.stockdatacollector.tests;

import com.howey.stockdatacollector.StockData;
import com.howey.stockdatacollector.StockDataProviderYahoo;
import com.howey.stockdatacollector.iStockDataProvider;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;


/**
 * Created by Administrator on 2015/7/13.
 */
public class StockDataProviderYahooTest {

    @Test
    public void testGetDataTypeList() throws Exception {
        iStockDataProvider sdp=new StockDataProviderYahoo();
        String[] dataTypeList=sdp.getDataTypeList();
        assertTrue(dataTypeList.length>0);
    }

    @Test
    public void testGetDataWitNoData() throws Exception {
        iStockDataProvider sdp=new StockDataProviderYahoo();
        List<String> symbols=new ArrayList<String>();
        symbols.add("MSFT");

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=dateFormater.parse("1980-01-01");
        Date endTime=dateFormater.parse("1981-02-01");
        List<StockData> dataList=sdp.getData(symbols, startTime, endTime, "Historical-Day");

        assertEquals(0,dataList.size());

    }

    @Test
    public void testGetDataWithData() throws Exception {
        iStockDataProvider sdp=new StockDataProviderYahoo();
        List<String> symbols=new ArrayList<String>();
        symbols.add("MSFT");

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=dateFormater.parse("1990-01-01");
        Date endTime=dateFormater.parse("1991-02-01");
        List<StockData> dataList=sdp.getData(symbols, startTime, endTime, "Historical-Day");

        assertNotSame(0,dataList.size());

    }

}