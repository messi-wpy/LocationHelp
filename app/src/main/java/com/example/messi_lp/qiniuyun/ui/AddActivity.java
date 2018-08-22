package com.example.messi_lp.qiniuyun.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.messi_lp.qiniuyun.CreateRetrofit;
import com.example.messi_lp.qiniuyun.R;
import com.example.messi_lp.qiniuyun.data.AddUrl;
import com.example.messi_lp.qiniuyun.utils.Auth;
import com.example.messi_lp.qiniuyun.utils.Config;
import com.example.messi_lp.qiniuyun.utils.QiNiuInitialize;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private String mToken;
    private String fileName="LinkinPark";
    private Button mButton;
    private Button mPost;
    private Button mAddUrl;
    private TextView mTextView;
    private EditText mEditext;
    private String photoPath;
    private Auth auth;
    private String mImageUrl;
    private ProgressBar mProgressBar;
    private List<String >mUrlList=new ArrayList<>();
    private int  num=1;
    private final static  String TAG="QINIU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mEditext=findViewById(R.id.edit_name);
        mButton=findViewById(R.id.chose_button);
        mPost=findViewById(R.id.post_button);
        mAddUrl=findViewById(R.id.post_url);
        mTextView=findViewById(R.id.text);
        mProgressBar=findViewById(R.id.progress);
        auth= Auth.create(Config.ACCESS_KEY,Config.SECRET_KEY);
        mButton.setOnClickListener(this);
        mPost.setOnClickListener(this);
        mAddUrl.setOnClickListener(this);

        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==1&&resultCode==RESULT_OK){
            try {

                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                //  这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                photoPath = cursor.getString(column_index);
                Log.i("QINIU", "onActivityResult: photopPath :"+photoPath);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.chose_button:{
                mToken=auth.uploadToken(Config.BUCKET_NAME);
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");

                startActivityForResult(galleryIntent, 1);
                break;
            }
            case R.id.post_button:{
                fileName=mEditext.getText().toString();
                mPost.setEnabled(false);
                if(TextUtils.isEmpty(photoPath)||TextUtils.isEmpty(fileName)){
                    Toast.makeText(getApplicationContext(),"请选择照片后并输入名字",Toast.LENGTH_LONG).show();
                    mPost.setEnabled(true);
                    return;
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                        Toast.makeText(this,"需要读取照片上传",Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }

                }



                break;
            }
            case R.id.post_url:{
                Log.i(TAG, "onClick: successs");
                // TODO: 18-8-21 如果点击两次上传两次，名字一样会怎样 
                mAddUrl.setEnabled(false);
                if (mUrlList.size()>0&&!fileName.equals("")) {
                    CreateRetrofit.getmApiService().addUrl(new AddUrl(fileName,mUrlList))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(addUrl ->{
                                mAddUrl.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_LONG).show();
                            },Throwable::printStackTrace,()->{
                                Log.i("qiniu", "上传成功 ");
                            } );
                }else {
                    Log.i(TAG, "onClick:FALSE ");
                    mAddUrl.setEnabled(true);
                }
                break;
            }
            default:break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode==1){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                long time = new Date().getTime();
                String random = Integer.toHexString((int) time);
                QiNiuInitialize.getSingleton().put(photoPath, fileName + random, mToken, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            mPost.setEnabled(true);
                            mImageUrl = Config.URL + key;
                            mUrlList.add(mImageUrl);
                            mTextView.setText("已上传"+num);
                            num++;
                            Log.i("qiniu", "Upload Success   url:" + mImageUrl);
                        } else {
                            Toast.makeText(getApplicationContext(), "错误，请不要输入相同的名字", Toast.LENGTH_LONG).show();
                            Log.i("qiniu", "Upload Fail");
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + response);
                    }
                }, new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        mProgressBar.setProgress((int) (percent * 100));
                        Log.i("QINIU", "progress: " + percent);
                    }
                }, null));

            }
        }
    }

}
