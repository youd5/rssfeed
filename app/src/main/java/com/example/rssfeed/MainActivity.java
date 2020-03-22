package com.example.rssfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<String> rssLinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRediff = findViewById(R.id.btnRediff);
        Button btnCinemaBlend = findViewById(R.id.btnCinemaBlend);
        Button btnNse = findViewById(R.id.buttonnse);
        Button btnChart = findViewById(R.id.buttonchart);
        btnRediff.setOnClickListener(this);
        btnCinemaBlend.setOnClickListener(this);
        btnNse.setOnClickListener(this);
        btnChart.setOnClickListener(this);

        rssLinks.add("https://m.rediff.com/rss/moviesreviewsrss.xml");
        rssLinks.add("https://www.cinemablend.com/rss_review.php");
        rssLinks.add("https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=30-01-2020&to=20-03-2020&symbol=EDELWEISS");
        rssLinks.add("https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=30-01-2020&to=20-03-2020&symbol=EDELWEISS");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRediff:
                startActivity(new Intent(MainActivity.this, RSSFeedActivity.class).putExtra("rssLink", rssLinks.get(0)));
                break;

            case R.id.btnCinemaBlend:
                startActivity(new Intent(MainActivity.this, RSSFeedActivity.class).putExtra("rssLink", rssLinks.get(1)));
                break;
            case R.id.buttonnse:
                startActivity(new Intent(MainActivity.this, NseFeedActivity.class).putExtra("rssLink", rssLinks.get(2)));
                break;
            case R.id.buttonchart:
                startActivity(new Intent(MainActivity.this, ChartActivity.class).putExtra("rssLink", rssLinks.get(3)));
                break;
        }
    }
}
