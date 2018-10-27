package com.example.a2laa.egra2atapp.view;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;

import android.widget.Button;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.PrefUtils;
import com.google.gson.Gson;

public class AdminActivity extends AppCompatActivity {


    TextView title;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        App.setContext(this);
        title = findViewById(R.id.toolbar_title);
        String ministryName = getIntent().getStringExtra("ministryName");
        String sectorName = getIntent().getStringExtra("sectorName");
        String serviceJson = getIntent().getStringExtra("service");
        if (serviceJson!=null){
            PrefUtils.storeKeys(this, "service", serviceJson);
            PrefUtils.storeKeys(this, "update", "true");
            title.setText("تعديل إجراء");
        }else{
            title.setText("إضافة إجراء");
            Service service = new Service();
            Gson gson = new Gson();
            String json = gson.toJson(service);
            PrefUtils.storeKeys(this, "service", json);
        }


        PrefUtils.storeKeys(this, "ministryName", ministryName);
        PrefUtils.storeKeys(this, "sectorName", sectorName);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mViewPager.setCurrentItem(3);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0) {
                PlaceFragment fragment = new PlaceFragment();
                return fragment;
            }else{
                if(position == 1) {
                    AttachmentFragment fragment = new AttachmentFragment();
                    return fragment;
                }else {
                    if (position == 2) {
                        Egra2atFragment fragment = new Egra2atFragment();
                        return fragment;
                    }else {
                        if (position == 3) {
                            InfoFragment fragment = new InfoFragment();
                            return fragment;
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
