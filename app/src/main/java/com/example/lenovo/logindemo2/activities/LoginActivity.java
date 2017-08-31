package com.example.lenovo.logindemo2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.logindemo2.R;



public class LoginActivity extends BaseActivity {
    EditText loginEmailET,loginPasswordET;
    TextView forgetPwdTV,notAccountTV;
    Button loginBT;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        String email=intent.getStringExtra("Email");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome!");
        //toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        loginEmailET=(EditText)findViewById(R.id.loginEmailET);
        loginPasswordET=(EditText)findViewById(R.id.loginPasswordET);
        forgetPwdTV=(TextView)findViewById(R.id.forgetPwdTV);
        notAccountTV=(TextView)findViewById(R.id.notAccountTV);
        loginBT=(Button)findViewById(R.id.loginBT);
        loginEmailET.setText(email);
        loginMethod();
    }
    public void loginMethod() {
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginEmailET.getText().toString().equals("") && !loginPasswordET.getText().toString().equals("")) {
                   String mail=loginEmailET.getText().toString();
                    String pass=loginPasswordET.getText().toString();
                    if(!db.checkLoginUser(mail,pass)){
                        showToast("User not exist");
                    }else {
                        Intent i=new Intent(getApplicationContext(),SuccessLoginActivity.class);
                        i.putExtra("Email",mail);
                        i.putExtra("pwd",pass);
                        startActivity(i);
                        showToast("Success");
                    }
                    showToast("Ready to login");
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all field", Toast.LENGTH_LONG).show();
                }

            }
        });
        forgetPwdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);*/
                showToast("Forget Password");
            }
        });
      notAccountTV.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent i=new Intent(getApplicationContext(),SignUpAcitvity.class);
              startActivity(i);
              showToast("SignUp Screen");
          }
      });

    }
}
