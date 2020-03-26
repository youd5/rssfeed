package com.example.rssfeed.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NseParser extends Parser {
    // tags
    private static String TAG_CHANNEL = "channel";
    private static String CH_SYMBOL = "CH_SYMBOL";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_ITEM = "item";
    private static String mTIMESTAMP = "mTIMESTAMP";
    private static String TAG_GUID = "guid";

    public NseParser(){

    }

    /**
     * this method pulls script specific data from nse
     * @param nse_url
     * @return
     */
    public List<NseItem> getNseFeedItems(String nse_url) {
        List<NseItem> itemsList = new ArrayList<NseItem>();
        String nse_feed_json;

        nse_feed_json = this.getXmlFromUrl(nse_url);
        if (nse_feed_json != null) {
            try {
                String key = "data";
                JSONObject jsonObject = new JSONObject(nse_feed_json);
                JSONArray jsonArrayLevel2 = jsonObject.getJSONArray(key);

                 int size = jsonArrayLevel2.length();
                for (int j = size-1; j >=0 ; j--) {
                    JSONObject jsonItem = jsonArrayLevel2.getJSONObject(j);
                    String symbol = jsonItem.getString("CH_SYMBOL");

                    String timestamp = jsonItem.getString("mTIMESTAMP");
                    Double ltp = jsonItem.getDouble("CH_LAST_TRADED_PRICE");
                    Double pChange = 0.0; //placeholder
                    NseItem nseItem = new NseItem(symbol, ltp, timestamp, pChange);
                    itemsList.add(nseItem);
                }
            } catch (Exception e) {
                // Check log for errors
                e.printStackTrace();
            }
        }

        // return item list
        return itemsList;
    }

    public List<NseItem> getNseFeedItems2(String nse_url) {
        List<NseItem> itemsList = new ArrayList<NseItem>();
        String nse_feed_json;

        nse_feed_json = this.getXmlFromUrl(nse_url);
        if (nse_feed_json != null) {
            try {
                String key = "data";
                JSONObject jsonObject = new JSONObject(nse_feed_json);
                JSONArray jsonArrayLevel2 = jsonObject.getJSONArray(key);

                int size = jsonArrayLevel2.length();
                for (int j = size-1; j >=0 ; j--) {
                    JSONObject jsonItem = jsonArrayLevel2.getJSONObject(j);
                    String symbol = jsonItem.getString("symbol");

                    String timestamp = (jsonItem.getString("lastUpdateTime")).substring(0,jsonItem.getString("lastUpdateTime").length() - 8);
                    Double ltp = jsonItem.getDouble("lastPrice");
                    Double pChange = jsonItem.getDouble("perChange365d"); // 365 day change
                    NseItem nseItem = new NseItem(symbol, ltp, timestamp,pChange);
                    itemsList.add(nseItem);
                }
            } catch (Exception e) {
                // Check log for errors
                e.printStackTrace();
            }
        }

        // return item list
        return itemsList;
    }
}
