package com.example.lenovo.logindemo2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.util.ArrayList;

/**
 * Created by lENOVO on 8/31/2017.
 */

public class SliderPagerAdapter  extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<LoginPojo> image_arraylist;

    public SliderPagerAdapter(Activity activity, ArrayList<LoginPojo> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
        LoginPojo item=image_arraylist.get(position);
         im_slider.setImageBitmap(ImageUtils.getImage(item.getImage()));


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
