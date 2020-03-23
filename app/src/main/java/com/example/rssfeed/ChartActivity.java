package com.example.rssfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
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

        String nse_link = getIntent().getStringExtra("nseLink");
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

                    AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                    anyChartView.setProgressBar(findViewById(R.id.progress_bar));

                    Cartesian cartesian = AnyChart.line();

                    cartesian.animation(true);

                    cartesian.padding(10d, 20d, 5d, 20d);

                    cartesian.crosshair().enabled(true);
                    cartesian.crosshair()
                            .yLabel(true)
                            // TODO ystroke
                            .yStroke((Stroke) null, null, null, (String) null, (String) null);

                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

                    cartesian.title("Trend of EOD price of Script");

                    cartesian.yAxis(0).title("Price in INR");
                    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);


                    List<DataEntry> seriesData = new ArrayList<>();

                    for(NseItem item: nseItems){
                        String date = item.mTIMESTAMP;
                        Double ltp = item.CH_LAST_TRADED_PRICE;
                        seriesData.add(new ValueDataEntry(date, ltp));
                    }

                    Set set = Set.instantiate();
                    set.data(seriesData);
                    Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

                    Line series1 = cartesian.line(series1Mapping);
                    series1.name("Edelweiss");
                    series1.hovered().markers().enabled(true);
                    series1.hovered().markers()
                            .type(MarkerType.CIRCLE)
                            .size(4d);
                    series1.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER)
                            .offsetX(5d)
                            .offsetY(5d);

                    cartesian.legend().enabled(true);
                    cartesian.legend().fontSize(13d);
                    cartesian.legend().padding(0d, 0d, 10d, 0d);

                    anyChartView.setChart(cartesian);

                }
            });



            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.setVisibility(View.GONE);
        }

    }
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
