package com.example.rssfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<String> rssLinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNse = findViewById(R.id.buttonnse);
        Button btnChart = findViewById(R.id.buttonchart);
        btnNse.setOnClickListener(this);
        btnChart.setOnClickListener(this);


        rssLinks.add("https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=01-01-2020&to=20-03-2020&symbol=EDELWEISS");
        rssLinks.add("https://www.nseindia.com/api/historical/cm/equity?series=[%22EQ%22]&from=01-01-2020&to=20-03-2020&symbol=EDELWEISS");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonnse:
                startActivity(new Intent(MainActivity.this, NseFeedActivity.class).putExtra("rssLink", rssLinks.get(0)));
                break;
            case R.id.buttonchart:
                startActivity(new Intent(MainActivity.this, ChartActivity.class).putExtra("rssLink", rssLinks.get(1)));
                break;
        }
    }
}
