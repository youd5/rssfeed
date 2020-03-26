package com.example.rssfeed;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.rssfeed.util.NseItem;
import com.example.rssfeed.util.NseParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WatchlistActivity extends ListActivity {

    private ProgressBar pDialog;

    String[] listItem;

    private static String TAG_TITLE = "CH_SYMBOL";
    private static String TAG_PUB_DATE = "mTIMESTAMP";
    private static String TAG_LTP = "CH_LAST_TRADED_PRICE";
    private static String TAG_PERCENT = "pChange";

    List<NseItem> nseItems = new ArrayList<>();
    NseParser nseParser = new NseParser();
    ArrayList<HashMap<String, String>> watchListItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        new LoadNseFeedItems().execute("empty");

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                HashMap<String, String> map = new HashMap<String, String>();
                map = (HashMap<String, String>) parent.getAdapter().getItem(position);
                String value=map.get("CH_SYMBOL");

                String symbolParam = "&symbol=" + value;
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String toDate = "&to=" + LocalDate.now().format(formatter1).toString();
                String nseLink =
                        "https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=01-01-2020" + toDate + symbolParam;
                startActivity(new Intent(WatchlistActivity.this, ChartActivity.class).putExtra("nseLink", nseLink));

            }
        });
    }


    public class LoadNseFeedItems extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(WatchlistActivity.this, null, android.R.attr.progressBarStyleLarge);

            RelativeLayout relativeLayout = findViewById(R.id.relativeLayout1);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            pDialog.setLayoutParams(lp);
            pDialog.setVisibility(View.VISIBLE);
            relativeLayout.addView(pDialog);
        }




        @Override
        protected String doInBackground(String... args) {
            String nse_url = "https://www.nseindia.com/api/equity-stockIndices?index=NIFTY%2050";//args[0];
            // list of rss items
            nseItems = nseParser.getNseFeedItems2(nse_url);
            listItem = getResources().getStringArray(R.array.watchlist);

            for(NseItem item:nseItems){
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                if(!hasItemInCuratedList(item.CH_SYMBOL)) //skip items not present in the curated List
                    continue;

                // adding each child node to HashMap key => value
                map.put("CH_SYMBOL", item.CH_SYMBOL);
                map.put("mTIMESTAMP", item.mTIMESTAMP); // If you want parse the date
                map.put("CH_LAST_TRADED_PRICE",item.CH_LAST_TRADED_PRICE.toString());
                map.put("pChange", "1 Yr Return: ("+ item.pChange.toString() +")");

                // adding HashList to ArrayList
                watchListItemList.add(map);
            }

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            WatchlistActivity.this,
                            watchListItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_TITLE, TAG_LTP, TAG_PUB_DATE, TAG_PERCENT},
                            new int[]{R.id.title, R.id.ltp, R.id.pub_date, R.id.percentchange});

                    setListAdapter(adapter);
                }
            });



            return null;
        }

        protected void onPostExecute(String args) {

            pDialog.setVisibility(View.GONE);
        }


    }

    private boolean hasItemInCuratedList(String ch_symbol) {
        for(String item: listItem){
            if(ch_symbol.equals(item)) return true;
        }
        return false;
    }
}
