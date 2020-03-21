package com.example.rssfeed.util;

public class NseItem {

    public String CH_SYMBOL;
    public String mTIMESTAMP;
    public Double CH_LAST_TRADED_PRICE;


    public NseItem(String CH_SYMBOL, Double CH_LAST_TRADED_PRICE, String mTIMESTAMP) {
        this.CH_SYMBOL = CH_SYMBOL;
        this.mTIMESTAMP = mTIMESTAMP;
        this.CH_LAST_TRADED_PRICE = CH_LAST_TRADED_PRICE;
    }

}
