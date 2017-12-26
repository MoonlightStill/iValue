package com.ices507.troy.ivalue_clock.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ices507.troy.ivalue_clock.R;
import com.ices507.troy.ivalue_clock.adapter.LaunchPageAdapter;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ViewPager vpContent = (ViewPager) findViewById(R.id.vp_content);
        int[] iamgeArray = {R.drawable.ic_launch1, R.drawable.ic_launch2, R.drawable.ic_launch3};
        LaunchPageAdapter adapter = new LaunchPageAdapter(this,iamgeArray);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
