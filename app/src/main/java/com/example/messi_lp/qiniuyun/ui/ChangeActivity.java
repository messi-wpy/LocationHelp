package com.example.messi_lp.qiniuyun.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messi_lp.qiniuyun.CreateRetrofit;
import com.example.messi_lp.qiniuyun.R;
import com.example.messi_lp.qiniuyun.data.AddUrl;
import com.example.messi_lp.qiniuyun.data.Delate;
import com.example.messi_lp.qiniuyun.data.Detail;
import com.example.messi_lp.qiniuyun.data.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText urlText;
    private EditText nameText;
    private EditText jingText;
    private EditText weiText;
    private EditText detailText;
    private String mName;
    private final static String TAG="QINIU";
    private Button saveButton;
    private Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        mName=getIntent().getStringExtra("name");
        urlText = findViewById(R.id.change_url);
        nameText = findViewById(R.id.change_name);
        jingText = findViewById(R.id.change_jing);
        weiText = findViewById(R.id.change_wei);
        detailText = findViewById(R.id.change_detail);

        saveButton = findViewById(R.id.change_save);
        saveButton.setOnClickListener(this);
        delButton = findViewById(R.id.change_del);
        delButton.setOnClickListener(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        CreateRetrofit.getmApiService().getDetail(mName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detail -> {
                    Log.i(TAG, "onResume: "+detail.getPlat().getName()+detail.getPlat().getInfo());
                    nameText.setText(detail.getPlat().getName());
                    detailText.setText(detail.getPlat().getInfo());
                    jingText.setText(detail.getPlat().getPoints().get(1).toString());
                    weiText.setText(detail.getPlat().getPoints().get(0).toString());
                    StringBuilder url= new StringBuilder();
                    for (int i=0;i<detail.getPlat().getUrl().size();i++){
                        if (i==detail.getPlat().getUrl().size()-1){
                            url.append(detail.getPlat().getUrl().get(i));
                            continue;
                        }
                        url.append(detail.getPlat().getUrl().get(i)).append('\n');
                    }

                    urlText.setText(url.toString());
                    },Throwable::printStackTrace,()->{});

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_save:{
                List<String>list;
                List<Double>doubles=new ArrayList<>();
                doubles.add(Double.valueOf(weiText.getText().toString()));
                doubles.add(Double.valueOf(jingText.getText().toString()));
                list= Arrays.asList(urlText.getText().toString().split("\n"));
                Update update=new Update(nameText.getText().toString(),
                        detailText.getText().toString(),list,doubles);
                CreateRetrofit.getmApiService().updata(update)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(b -> {
                            Toast.makeText(getApplicationContext(),b.getMsg(),Toast.LENGTH_LONG).show();
                        },Throwable::printStackTrace);
                break;
            }
            case R.id.change_del:{
                CreateRetrofit.getmApiService().delete(new Delate(mName))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(delate->{
                            Toast.makeText(getApplicationContext(),delate.getMsg(),Toast.LENGTH_LONG).show();
                            finish();
                        },Throwable::printStackTrace);
                break;
            }
            default:break;
        }
    }
}
