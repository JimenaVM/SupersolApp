package com.gonzalo.supersolapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzalo.supersolapp.R;

/**
 * Created by gonzalopro on 10/24/16.
 */
public class CreditoFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_credito,container,false);

        return root;
    }
}
