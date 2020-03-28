package com.example.rssfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    String[] listItem2;

    private static String TAG_TITLE = "title";
    private static String TAG_ACTIVITY_ON_CLICK = "link";
    private static String TAG_SUBHEADER = "pubDate";

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
        listItem = getResources().getStringArray(R.array.homepagelist);
        listItem2 = getResources().getStringArray(R.array.homepagedetail);



        //Populate watchListItemList
        for(int i = 0; i < listItem.length && i < listItem2.length; i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TAG_TITLE, listItem[i]);
            map.put(TAG_ACTIVITY_ON_CLICK, "ChartActivity"); //placeholder, onclick listener can use the tag link to use the right activity
            map.put(TAG_SUBHEADER, listItem2[i]); //placeholder rename and use better.
            watchListItemList.add(map);
        }


        ListAdapter adapter = new SimpleAdapter(
                this,
                watchListItemList, R.layout.rss_item_list_row,
                new String[]{TAG_ACTIVITY_ON_CLICK, TAG_TITLE, TAG_SUBHEADER},
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
