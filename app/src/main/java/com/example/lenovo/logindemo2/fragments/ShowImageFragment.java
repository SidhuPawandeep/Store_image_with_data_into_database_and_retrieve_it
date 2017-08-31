package com.example.lenovo.logindemo2.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.lenovo.logindemo2.Adapter.GridLayoutAdapter;
import com.example.lenovo.logindemo2.Database.ImageDatabase;
import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.activities.BaseActivity;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;

import java.util.ArrayList;

/**
 * Created by lENOVO on 8/30/2017.
 */

public class ShowImageFragment extends Fragment {


    private GridView gridView;
    private BaseActivity baseActivity;
    private ImageDatabase db1;
    private Context context;
    GridLayoutAdapter gridAdapter;
    ArrayList<LoginPojo> data;

    public ShowImageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_image, container, false);
        Toolbar toolbar =(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" Display Images");
        //toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        db1=new ImageDatabase(getActivity());
        baseActivity= (BaseActivity) getActivity();
        context=getActivity();
        data=new ArrayList<>();
        Bundle bundle=getArguments();
        int user_id=bundle.getInt("id");
        data=db1.getPojoData1(user_id);
        gridView = (GridView)v.findViewById(R.id.gridViewImage);
        gridAdapter = new GridLayoutAdapter(context, R.layout.grid_item, data);
        gridView.setAdapter(gridAdapter);
        return v;
    }


}
