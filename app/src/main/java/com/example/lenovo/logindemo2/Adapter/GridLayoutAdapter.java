package com.example.lenovo.logindemo2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lENOVO on 8/31/2017.
 */

public class GridLayoutAdapter extends ArrayAdapter{
       private Context context;
        private int layoutResourceId;
        private ArrayList<LoginPojo> data = new ArrayList();

        public GridLayoutAdapter(Context context, int layoutResourceId, ArrayList<LoginPojo> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.image = (CircleImageView) row.findViewById(R.id.image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            LoginPojo item=data.get(position);
            holder.image.setImageBitmap(ImageUtils.getImage(item.getImage()));
            return row;
        }

        static class ViewHolder {

            CircleImageView image;
        }
    }

