package com.example.messi_lp.qiniuyun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.messi_lp.qiniuyun.CreateRetrofit;
import com.example.messi_lp.qiniuyun.R;
import com.example.messi_lp.qiniuyun.RxBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG="QINIU";
    private RecyclerView mRecyclerView;
    private List<String> list = new ArrayList<>();
    private TextView mtext;
    private int lastTop;
    private int offset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtext=findViewById(R.id.text);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        initRecycler();
        RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> mtext.setText(s));

        CreateRetrofit.getmApiService().getNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locationName -> {
                    if (locationName.getName()!=null) {
                        Log.i(TAG, "get all");
                        list = locationName.getName();
                        mRecyclerView.setAdapter(new MyAdapter(list, this));
                    }
                    Log.i(TAG, "get ");
                },Throwable::printStackTrace,()->{
                    Log.i("QINIU", "getName ok ");
                });
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume: ");

    }


    private void initRecycler() {
        for (int i = 0; i < 20; i++) {
            list.add("空");
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter(list,this));
    }


}
