package com.example.lenovo.logindemo2.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.logindemo2.Database.DataBaseHandler;
import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.activities.BaseActivity;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {
    CircleImageView imageUpdateIV;
    EditText UpdateNameET,updatePhoneET,UpdateEmailET,UpdatePasswordET;
    Button updateBT;
    BaseActivity baseActivity;
    private Uri selectedImagePath;
    byte[] inputData;
    Context context;
    private Bitmap bitmap;
    DataBaseHandler db1;
    public EditProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Toolbar toolbar =(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" Edit Profile");
        //toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        UpdateEmailET=(EditText)v.findViewById(R.id.UpdateEmailET);
        UpdateNameET=(EditText)v.findViewById(R.id.UpdateUserNameET);
        updatePhoneET=(EditText)v.findViewById(R.id.updatePhoneET);
        UpdatePasswordET=(EditText)v.findViewById(R.id.UpdatePasswordET);
        imageUpdateIV=(CircleImageView) v.findViewById(R.id.imageUpdateIV);
        updateBT=(Button)v.findViewById(R.id.updateBT);
        db1=new DataBaseHandler(getActivity());
        baseActivity= (BaseActivity) getActivity();
        context=getActivity();
        updateData();
        return v;
    }
    public void updateData()
    {
        final Bundle bundle=getArguments();
        final int i=bundle.getInt("id");
        byte[] imagepath;

        Boolean status=false;
         imageUpdateIV.setImageBitmap(ImageUtils.getImage(bundle.getByteArray("image")));
        UpdateNameET.setText(String.valueOf(bundle.getString("name")));
        updatePhoneET.setText(String.valueOf(bundle.getString("phone")));
        UpdatePasswordET.setText(String.valueOf(bundle.getString("password")));
        UpdateEmailET.setText(String.valueOf(bundle.getString("email")));
        imageUpdateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });

        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    InputStream iStream =getActivity().getContentResolver().openInputStream(selectedImagePath);
                    inputData = ImageUtils.getBytes(iStream);
                   /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("id", bundle.getInt("id"));
                    editor.put("image", );
                    editor.putBoolean("Logging",true);
                    //editor.clear();
                    editor.commit();*/

                } catch (Exception ioe) {
                    inputData=bundle.getByteArray("image");
                    ioe.printStackTrace();
                }

               if (UpdateNameET.getText().toString().equals("") && updatePhoneET.getText().toString().equals("") &&
                        UpdatePasswordET.getText().toString().equals("")
                        && UpdateEmailET.getText().toString().equals("")) {

                    baseActivity.showToast("Please fill all field");
                } else {
                   String password = UpdatePasswordET.getText().toString();
                   String fname= UpdateNameET.getText().toString();
                   String email=UpdateEmailET.getText().toString();
                   String phone=updatePhoneET.getText().toString();

                   int result;

                result =db1.updateRegisterDB(new LoginPojo(i,fname,password,phone,inputData,email));
                      baseActivity.showToast(i+" "+fname+" "+phone+" "+inputData.toString());

                        if(result!=0){
                        baseActivity.showToast("Successfully Updated");

                                      } else {
                        baseActivity.showToast("Please Try again");

                    }
                }
                           }
        });
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
            Cursor cursor = context.getContentResolver().query(selectedImagePath, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(imagePath, options);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
            imageUpdateIV.setImageBitmap(bitmap);
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
}
