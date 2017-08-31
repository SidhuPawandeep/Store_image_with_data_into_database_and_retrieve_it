package com.example.lenovo.logindemo2.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.logindemo2.Adapter.SliderPagerAdapter;
import com.example.lenovo.logindemo2.Database.ImageDatabase;
import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.activities.BaseActivity;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by lENOVO on 8/31/2017.
 */

public class ViewPagerFragment extends Fragment {
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int page_position = 0;
    private BaseActivity baseActivity;
    private ImageDatabase db1;
    private Context context;
    ArrayList<LoginPojo> data;
    public ViewPagerFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_view_pager, container, false);

        vp_slider = (ViewPager)v.findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout)v.findViewById(R.id.ll_dots);
        //toolbar.setNavigationIcon(R.mipmap.logo);
       // toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        db1=new ImageDatabase(getActivity());
        baseActivity= (BaseActivity) getActivity();
        context=getActivity();
        data=new ArrayList<>();
        init();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == data.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);



        return v;
    }
    private void init() {
        Toolbar toolbar =(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" Display Images");
       // toolbar.getSupportActionBar().hide();


       //Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own

        Bundle bundle=getArguments();
        int user_id=bundle.getInt("id");
        data=db1.getPojoData1(user_id);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), data);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void addBottomDots(int currentPage) {
        dots = new TextView[data.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }


}
