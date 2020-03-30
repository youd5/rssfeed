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
        arrayList.add(new ThumbnailListItem("Rich Dad Poor Dad", "https://www.amazon.in/dp/B07C7M8SX9/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51wOOMQ%2BF3L._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("The Compound Effect", "https://www.amazon.in/dp/B06XHKBHQL/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51Bz60iDotL._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("The Richest Man in Babylon", "https://www.amazon.in/dp/B084B1PKCW/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/41N2FgMd39L._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Atomic Habits", "https://www.amazon.in/dp/B01N5AX61W/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51Fqn4%2BUodL._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Thinking Fast And Slow", "https://www.amazon.in/dp/B005MJFA2W/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=10a8848761556823eda9d6e923fad69e&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/41A-sTN8tdL._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Anti Fragile", "https://www.amazon.in/dp/B009K6DKTS/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/515oFiycW7L._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Power of Now", "https://www.amazon.in/dp/B002361MLA/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/41coOhxR2ML._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Tools of Titan", "https://www.amazon.in/dp/B01LF32ZNU/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51Rgv4zM3pL._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("The Black Swan", "https://www.amazon.in/dp/B002RI99IM/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=cc67cf9d5cadbca517f33330c60deed2&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51q4Y%2BEKElL._SX140_BO1,204,203,200_.jpg"));
        arrayList.add(new ThumbnailListItem("Who Moved My Cheese", "https://www.amazon.in/dp/B07L426VRK/ref=as_li_ss_tl?ie=UTF8&linkCode=ll1&tag=goodjob0f-21&linkId=10a8848761556823eda9d6e923fad69e&language=en_IN", "https://images-na.ssl-images-amazon.com/images/I/51re92xq-zL._SX140_BO1,204,203,200_.jpg"));

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
