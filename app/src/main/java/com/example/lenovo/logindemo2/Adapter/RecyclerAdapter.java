package com.example.lenovo.logindemo2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.logindemo2.Database.DataBaseHandler;
import com.example.lenovo.logindemo2.Interfaces.RecylerViewListener;
import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.activities.BaseActivity;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lENOVO on 8/30/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHold> {

        ArrayList<LoginPojo> data = new ArrayList<>();
        DataBaseHandler sdb1;
        Context baseActivity;


public RecyclerAdapter(ArrayList<LoginPojo> data, Context baseActivity) {
        this.data = data;
        this.baseActivity = baseActivity;
        }

public RecyclerAdapter.ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new ViewHold(v, baseActivity, data);
        }


public void onBindViewHolder(final RecyclerAdapter.ViewHold holder, final int pos) {
final LoginPojo obj = data.get(pos);
        baseActivity = new BaseActivity();
        sdb1 = new DataBaseHandler(holder.mContext);
        holder.id.setText((obj.getId() + ""));
        holder.name.setText(obj.getName());
        holder.email.setText(obj.getEmail());
        holder.phone.setText(obj.getPhoneNo());
    try {
        holder.userimageIV.setImageBitmap(ImageUtils.getImage(obj.getImage()));
    }
    catch (Exception e)
    {
        holder.userimageIV.setImageResource(R.drawable.businessman);
    }
      }

public int getItemCount() {
        return data.size();
        }

public class ViewHold extends RecyclerView.ViewHolder {
    public TextView id, name, email,phone;
    public Button deleteBT;
    public Context mContext;
    private RecylerViewListener clickListener;
    CircleImageView userimageIV;
    ArrayList<LoginPojo> pojo = new ArrayList<LoginPojo>();

    public ViewHold(View items, Context mContext, ArrayList<LoginPojo> pojo) {
        super(items);
        this.pojo = pojo;
        this.mContext = mContext;
        id = (TextView) items.findViewById(R.id.get_idTV);
        name = (TextView) items.findViewById(R.id.get_nameTV);
        email = (TextView) items.findViewById(R.id.get_emailTv);
        phone=(TextView)items.findViewById(R.id.get_phoneTv);
        userimageIV=(CircleImageView) items.findViewById(R.id.userListIV);

}


    public void setClickListener(RecylerViewListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
}
