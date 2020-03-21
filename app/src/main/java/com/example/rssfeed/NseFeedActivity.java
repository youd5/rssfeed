package com.example.rssfeed;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.rssfeed.util.NseItem;
import com.example.rssfeed.util.NseParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NseFeedActivity extends ListActivity {

    private static String TAG_TITLE = "CH_SYMBOL";
    private static String TAG_LINK = "CH_SYMBOL";
    private static String TAG_PUB_DATE = "mTIMESTAMP";
    private static String TAG_LTP = "CH_LAST_TRADED_PRICE";
    private ProgressBar pDialog;
    ArrayList<HashMap<String, String>> nseItemList = new ArrayList<>();

    NseParser nseParser = new NseParser();

    List<NseItem> nseItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nse_feed);

        String nse_link = getIntent().getStringExtra("rssLink");
        new LoadNseFeedItems().execute(nse_link);

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), BrowserActivity.class);
                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString().trim();
                in.putExtra("url", page_url);
                startActivity(in);
            }
        });
    }

    public class LoadNseFeedItems extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(NseFeedActivity.this, null, android.R.attr.progressBarStyleLarge);

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
            String nse_url = args[0];
            // list of rss items
            nseItems = nseParser.getNseFeedItems(nse_url);

            for(NseItem item:nseItems){
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put("CH_SYMBOL", item.CH_SYMBOL);
                map.put("mTIMESTAMP", item.mTIMESTAMP); // If you want parse the date
                map.put("CH_LAST_TRADED_PRICE",item.CH_LAST_TRADED_PRICE.toString());

                // adding HashList to ArrayList
                nseItemList.add(map);
            }

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            NseFeedActivity.this,
                            nseItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE},
                            new int[]{R.id.page_url, R.id.title, R.id.pub_date});

                    // updating listview
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
