package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rssfeed.util.NseItem;
import com.example.rssfeed.util.NseParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WatchlistActivity extends ListActivity {

    private ProgressBar pDialog;

    TextView textView;
    String[] listItem;

    private static String TAG_TITLE = "CH_SYMBOL";
    private static String TAG_PUB_DATE = "mTIMESTAMP";
    private static String TAG_LTP = "CH_LAST_TRADED_PRICE";

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

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                HashMap<String, String> map = new HashMap<String, String>();
                map = (HashMap<String, String>) parent.getAdapter().getItem(position);
                String value=map.get("CH_SYMBOL");
                String nseLink =
                        "https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=01-01-2020&to=23-03-2020&symbol=" + value;
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

            for(NseItem item:nseItems){
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put("CH_SYMBOL", item.CH_SYMBOL);
                map.put("mTIMESTAMP", item.mTIMESTAMP); // If you want parse the date
                map.put("CH_LAST_TRADED_PRICE",item.CH_LAST_TRADED_PRICE.toString());

                // adding HashList to ArrayList
                watchListItemList.add(map);
            }

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            WatchlistActivity.this,
                            watchListItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_TITLE, TAG_LTP, TAG_PUB_DATE},
                            new int[]{R.id.title, R.id.ltp, R.id.pub_date});

                    setListAdapter(adapter);
                }
            });



            return null;
        }

        protected void onPostExecute(String args) {

            pDialog.setVisibility(View.GONE);
        }


    }
}
