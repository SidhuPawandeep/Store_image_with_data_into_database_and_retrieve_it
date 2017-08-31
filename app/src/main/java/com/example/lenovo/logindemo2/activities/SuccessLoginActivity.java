package com.example.lenovo.logindemo2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.logindemo2.R;
import com.example.lenovo.logindemo2.fragments.AddImageFragment;
import com.example.lenovo.logindemo2.fragments.EditProfileFragment;
import com.example.lenovo.logindemo2.fragments.ShowImageFragment;
import com.example.lenovo.logindemo2.fragments.UserListFragment;
import com.example.lenovo.logindemo2.fragments.ViewPagerFragment;
import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;
import com.example.lenovo.logindemo2.utils_classes.ImageUtils;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuccessLoginActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CircleImageView UserimageView;
    TextView User_nameTV, EmailTV;
    Intent intent;
    DrawerLayout drawerLayout;
    LoginPojo data = new LoginPojo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setTitle(" Main Data!");
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg));
        setSupportActionBar(toolbar);
        intent = getIntent();
        String email = intent.getStringExtra("Email");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header_success_login,null, false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         User_nameTV = (TextView)listHeaderView.findViewById(R.id.User_nameTV);
        EmailTV = (TextView) listHeaderView.findViewById(R.id.EmailTV);
        UserimageView = (CircleImageView) listHeaderView.findViewById(R.id.UserimageView);
        getData(email);
        navigationView.addHeaderView(listHeaderView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void getData(final String email) {


        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                data = db.getPojoData(email);
                UserimageView.setImageBitmap(ImageUtils.getImage(data.getImage()));
                User_nameTV.setText(data.getName());
                showToast(data.getName());
                EmailTV.setText(data.getEmail());
                final String password = data.getPassword();
                   }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);


       // Bitmap bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.success_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {
            // Handle the camera action
            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            EditProfileFragment ed = new EditProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",data.getId());
            bundle.putByteArray("image", data.getImage());
            bundle.putString("name", data.getName());
            bundle.putString("phone", data.getPhoneNo());
            bundle.putString("email", data.getEmail());
            bundle.putString("password", data.getPassword());
            ed.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, ed);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_user_list) {

             UserListFragment ed = new UserListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("email", data.getEmail());
            ed.setArguments(bundle);
            android.app.FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.fragment_container, ed);
            fragmentTransaction1.addToBackStack("");
            fragmentTransaction1.commit();
        }
        else if (id == R.id.nav_add_image) {
            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            AddImageFragment ad=new AddImageFragment();
            Bundle bundle = new Bundle();
             bundle.putString("email", data.getEmail());
            bundle.putInt("id",data.getId());
            bundle.putString("password", data.getPassword());
            ad.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, ad);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_show_image) {

            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            ShowImageFragment ad=new ShowImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", data.getId());
            bundle.putString("password", data.getPassword());
            ad.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, ad);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        }
        else if(id== R.id.nav_image_viewPager)
        {
            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            ViewPagerFragment ad=new ViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", data.getId());
            bundle.putString("email",data.getEmail());
            bundle.putString("password", data.getPassword());
            ad.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, ad);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
