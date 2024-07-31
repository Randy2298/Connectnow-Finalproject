package com.my.real.chat.activities.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.my.real.chat.activities.R;
import com.my.real.chat.activities.adapters.UserAdapters;
import com.my.real.chat.activities.managers.Screens;
import com.my.real.chat.activities.managers.Utils;
import com.my.real.chat.activities.models.User;

import java.util.ArrayList;

public class BaseFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public ArrayList<User> mUsers;
    public UserAdapters userAdapters;
    public Screens screens;
    public Activity mActivity;
    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        screens = new Screens(getActivity());
        mContext = getContext();
    }

    private ProgressDialog pd = null;

    public void showProgress() {
        try {
            if (pd == null) {
                pd = new ProgressDialog(mActivity);
            }
            pd.setMessage(getString(R.string.msg_please_wait));
            pd.show();
        } catch (Exception e) {
            Utils.getErrors(e);
        }
    }

    public void hideProgress() {
        try {
            if (pd != null) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {
            Utils.getErrors(e);
        }
    }
}
