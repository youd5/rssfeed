package com.example.rssfeed;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Toolbar myToolbar;
    String[] listItem;

    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        listView = (ListView) findViewById(R.id.listviewmain);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getDrawable(R.drawable.favicon32x32));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<HashMap<String, String>> watchListItemList = new ArrayList<>();
        listItem = getResources().getStringArray(R.array.watchlist);


        //Populate watchListItemList
        for(String script: listItem){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TAG_TITLE, script);
            map.put(TAG_LINK, "Google.com"); //placeholder
            map.put(TAG_PUB_DATE, "item.pubdate"); //placeholder
            watchListItemList.add(map);
        }


        ListAdapter adapter = new SimpleAdapter(
                this,
                watchListItemList, R.layout.rss_item_list_row,
                new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE},
                new int[]{R.id.page_url, R.id.title, R.id.pub_date});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                HashMap<String, String> map = new HashMap<String, String>();
                map = (HashMap<String, String>) parent.getAdapter().getItem(position);
                String value=map.get("title");
                String nseLink =
                        "https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=01-01-2020&to=23-03-2020&symbol=" + value;
                startActivity(new Intent(MainActivity.this, ChartActivity.class).putExtra("nseLink", nseLink));

            }
        });



    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonchart:
                startActivity(new Intent(MainActivity.this, WatchlistActivity.class).putExtra("nseLink", ""));
                break;
        }
    }
}
