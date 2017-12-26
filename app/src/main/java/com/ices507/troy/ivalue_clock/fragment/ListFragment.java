package com.ices507.troy.ivalue_clock.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ices507.troy.ivalue_clock.entity.Record;
import com.ices507.troy.ivalue_clock.utils.RecordsDBHelper;

import java.util.Date;

/**
 * Created by troy on 17-12-7.
 *
 * @Description:
 * @Modified By:
 */

public class ListFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecordsDBHelper recordsDBHelper = RecordsDBHelper.getInstance(getActivity(), RecordsDBHelper.DB_VERSION);
        recordsDBHelper.openReadLink();
        recordsDBHelper.deleteAll();
        recordsDBHelper.closeLink();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
