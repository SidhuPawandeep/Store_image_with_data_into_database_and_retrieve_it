package com.example.lenovo.logindemo2.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.logindemo2.Adapter.RecyclerAdapter;
import com.example.lenovo.logindemo2.Database.DataBaseHandler;
import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.activities.BaseActivity;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;

import java.util.ArrayList;

/**
 * Created by lENOVO on 8/30/2017.
 */

public class UserListFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    public ArrayList<LoginPojo> data;
    DataBaseHandler sdb;
    BaseActivity baseActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context;
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_user_list, container, false);
        Toolbar toolbar =(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" ALL User Detail");
        //toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        context=getContext();
        sdb=new DataBaseHandler(context);
        baseActivity=new BaseActivity();
        recyclerView = (RecyclerView)v.findViewById(R.id.first_recycler_view);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadData();
        return v;
    }
    public void loadData(){
        data=new ArrayList<>();
        data=sdb.getAlldatas();
        adapter=new RecyclerAdapter(data,getContext());
        recyclerView.setAdapter(adapter);
    }

}
