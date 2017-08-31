package com.example.lenovo.logindemo2.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.logindemo2.Database.ImageDatabase;
import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.activities.BaseActivity;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lENOVO on 8/30/2017.
 */

public class AddImageFragment extends Fragment {
    CircleImageView add_imageIV;
    Button addImageBT;
    byte[] inputData;
    Context context;
    private Bitmap bitmap;
    ImageDatabase db1;
    BaseActivity baseActivity;
    private Uri selectedImagePath;
    public AddImageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_image, container, false);
        Toolbar toolbar =(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" Add Images");
        //toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        add_imageIV=(CircleImageView) v.findViewById(R.id.add_imageIV);
       addImageBT =(Button)v.findViewById(R.id.addImageBT);
        add_imageIV.setVisibility(View.GONE);
        db1=new ImageDatabase(getActivity());
        baseActivity= (BaseActivity) getActivity();
        context=getActivity();
        addImage();
        return v;
    }
    public void addImage(){
        final Bundle bundle=getArguments();
        final int i=bundle.getInt("id");
        addImageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                try {
                    InputStream iStream =getActivity().getContentResolver().openInputStream(selectedImagePath);
                    inputData = ImageUtils.getBytes(iStream);
                    addImageDatabase(i);
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }


            }
        });
    }
    public void addImageDatabase(int user_id)
    {
        int id=user_id;
        // LoginPojo obj=new LoginPojo(id,inputData);
        int result =db1.addImage(new LoginPojo(id,inputData));
        // baseActivity.showToast(i+" "+fname+" "+phone+" "+inputData.toString());

        if(result!=0){
            baseActivity.showToast("Successfully Updated");

        } else {
            baseActivity.showToast("Please Try again");

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
            Cursor cursor = context.getContentResolver().query(selectedImagePath, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(imagePath, options);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
            add_imageIV.setImageBitmap(bitmap);
            add_imageIV.setVisibility(View.VISIBLE);
        }
    }

}
