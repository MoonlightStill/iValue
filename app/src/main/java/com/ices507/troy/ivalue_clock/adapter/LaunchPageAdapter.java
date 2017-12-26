package com.ices507.troy.ivalue_clock.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ices507.troy.ivalue_clock.R;

import java.util.ArrayList;

/**
 * Created by troy on 17-12-13.
 *
 * @Description:
 * @Modified By:
 */

public class LaunchPageAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<View> mViewList = new ArrayList<View>();

    public LaunchPageAdapter(Context context, int[] imageArray) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        for(int i = 0;i < imageArray.length; i ++) {
            View view = mInflater.inflate(R.layout.item_lunch, null);
            ImageView ivLaunch = (ImageView) view.findViewById(R.id.iv_launch);
            RadioGroup rgIndicate = (RadioGroup) view.findViewById(R.id.rg_indicate);
            Button btnStart = (Button) view.findViewById(R.id.btn_start);
            ivLaunch.setImageResource(imageArray[i]);
            for(int j = 0;j < imageArray.length;j ++) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setButtonDrawable(R.drawable.ic_launcher_background);
                radioButton.setPadding(10, 10, 10, 10);
                rgIndicate.addView(radioButton);
            }
            ((RadioButton) rgIndicate.getChildAt(i)).setChecked(true);
            if (i == imageArray.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "欢迎使用", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            mViewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}
