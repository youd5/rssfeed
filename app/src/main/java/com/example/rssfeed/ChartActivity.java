package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.rssfeed.util.NseItem;
import com.example.rssfeed.util.NseParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_chart);

        String nse_link = getIntent().getStringExtra("rssLink");
        new LoadNseFeedItemsChart().execute(nse_link);


    }


    public class LoadNseFeedItemsChart extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(ChartActivity.this, null, android.R.attr.progressBarStyleLarge);

            RelativeLayout relativeLayout = findViewById(R.id.any_chart_view1);
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
                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("John", 10000));
                    data.add(new ValueDataEntry("Jake", 12000));
                    data.add(new ValueDataEntry("Peter", 18000));
                    pie.data(data);

                    AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                    anyChartView.setChart(pie);

                }
            });



            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.setVisibility(View.GONE);
        }




    }
}
