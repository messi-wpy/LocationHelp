package com.example.messi_lp.qiniuyun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.messi_lp.qiniuyun.Check;
import com.example.messi_lp.qiniuyun.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Check {

    private RecyclerView mRecyclerView;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        initRecycler();
    }

    private void initRecycler() {
        for (int i = 0; i < 20; i++) {
            list.add("Address");
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter(list,this));
    }

}
