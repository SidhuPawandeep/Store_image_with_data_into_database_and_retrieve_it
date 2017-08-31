package com.example.lenovo.logindemo2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.logindemo2.Database.DataBaseHandler;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.R;

/**
 * Created by lENOVO on 8/29/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public DataBaseHandler db;
     LoginPojo pj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        db = new DataBaseHandler(this);
        pj=new LoginPojo();
    }
    public void showLogs(String message) {
        Log.e("message", message);
    }

    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
