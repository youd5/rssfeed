package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ThumbnailListActivity extends AppCompatActivity {

    private ListView listView;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail_list);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        listView = (ListView) findViewById(R.id.list);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getDrawable(R.drawable.favicon32x32));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ArrayList<ThumbnailListItem> arrayList = new ArrayList<ThumbnailListItem>();
        arrayList.add(new ThumbnailListItem("Who Moved My Cheese", "https://www.amazon.in/dp/0399144463/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=10a8848761556823eda9d6e923fad69e&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51re92xq-zL._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Rich Dad Poor Dad", "https://www.amazon.in/dp/1612680194/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51wOOMQ%2BF3L._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Javascript", "https://www.tutorialspoint.com/javascript/", "https://www.tutorialspoint.com/javascript/images/javascript-mini-logo.jpg"));
        arrayList.add(new ThumbnailListItem("Cprogramming", "https://www.tutorialspoint.com/cprogramming/", "https://www.tutorialspoint.com/cprogramming/images/c-mini-logo.jpg"));
        arrayList.add(new ThumbnailListItem("Cplusplus", "https://www.tutorialspoint.com/cplusplus/", "https://www.tutorialspoint.com/cplusplus/images/cpp-mini-logo.jpg"));
        arrayList.add(new ThumbnailListItem("Android", "https://www.tutorialspoint.com/android/", "https://www.tutorialspoint.com/android/images/android-mini-logo.jpg"));
        arrayList.add(new ThumbnailListItem("Cprogramming", "https://www.tutorialspoint.com/cprogramming/", "https://www.tutorialspoint.com/cprogramming/images/c-mini-logo.jpg"));
        arrayList.add(new ThumbnailListItem("Cplusplus", "https://www.tutorialspoint.com/cplusplus/", "https://www.tutorialspoint.com/cplusplus/images/cpp-mini-logo.jpg"));
        arrayList.add(new ThumbnailListItem("Android", "https://www.tutorialspoint.com/android/", "https://www.tutorialspoint.com/android/images/android-mini-logo.jpg"));

        CustomAdapter customAdapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(ThumbnailListActivity.this, StyledTextActivity.class));
                        break;
                }
            }
        });

    }
}
