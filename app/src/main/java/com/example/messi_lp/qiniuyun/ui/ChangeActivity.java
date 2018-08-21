package com.example.messi_lp.qiniuyun.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.messi_lp.qiniuyun.R;

public class ChangeActivity extends AppCompatActivity {
    private EditText urlText;
    private EditText nameText;
    private EditText jingText;
    private EditText weiText;
    private EditText detailText;

    private Button saveButton;
    private Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        urlText = findViewById(R.id.change_url);
        nameText = findViewById(R.id.change_name);
        jingText = findViewById(R.id.change_jing);
        weiText = findViewById(R.id.change_wei);
        detailText = findViewById(R.id.change_detail);

        saveButton = findViewById(R.id.change_save);
        delButton = findViewById(R.id.change_del);

    }
}
