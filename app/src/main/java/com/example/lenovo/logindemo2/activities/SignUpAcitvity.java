package com.example.lenovo.logindemo2.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpAcitvity extends BaseActivity {

    EditText SignUpNameET,SignUpPhoneET,SignEmailET,signUpPasswordET;
    CircleImageView imageSignUpIV;
    private int PICK_IMAGE_REQUEST = 1;
    Button signUpBT;
    private Bitmap bitmap;
    private Uri selectedImagePath;
    android.support.v7.widget.Toolbar toolbar;

    byte[] inputData;@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_sign_up_acitvity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Sign Up!");
        //toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        SignUpNameET=(EditText)findViewById(R.id.SignUpNameET);
        SignUpPhoneET=(EditText)findViewById(R.id.SignUpPhoneET);
        SignEmailET=(EditText)findViewById(R.id.SignEmailET);
        signUpPasswordET=(EditText)findViewById(R.id.signUpPasswordET);
        imageSignUpIV=(CircleImageView) findViewById(R.id.imageSignIV);
        signUpBT=(Button)findViewById(R.id.signUpBT);
        signUpMethod();
    }
    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {

            openGallery();

        }
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && null != data) {
            selectedImagePath = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getApplication().getContentResolver().query(selectedImagePath, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(imagePath, options);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
            imageSignUpIV.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    openGallery();

                }
                break;

            default:
                break;
        }
    }
   public void signUpMethod() {
        imageSignUpIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          checkPermission();

                
            }
        });
        signUpBT.setOnClickListener(new View.OnClickListener() {
            String fname, phone, email, password;

            @Override
            public void onClick(View v) {
                fname = SignUpNameET.getText().toString();
                phone = SignUpPhoneET.getText().toString();
                try {
                    InputStream iStream =
                    getContentResolver().openInputStream(selectedImagePath);
                     inputData = ImageUtils.getBytes(iStream);
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }
               // byte[] image=getByteImage(bitmap);
                                email = SignEmailET.getText().toString();
                password = signUpPasswordET.getText().toString();
                if (SignUpNameET.getText().toString().equals("") && SignUpPhoneET.getText().toString().equals("") &&
                        SignEmailET.getText().toString().equals("")
                        && signUpPasswordET.getText().toString().equals("")) {

                    showToast("Please fill all field");
                } else {

                    if (!db.checkUser(email)) {

                            db.adddata(new LoginPojo(fname, email,phone, password,inputData));
                            showToast("Successfully Registered");
                           Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("Email", email);
                           startActivity(intent);



                    } else {
                        showToast("User ALready Exist");

                    }
                }
            }
        });
    }/*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageSignUpIV.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

    public byte[] getByteImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }
    }

