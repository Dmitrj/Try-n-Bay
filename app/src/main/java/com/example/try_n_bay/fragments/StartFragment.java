package com.example.try_n_bay.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.try_n_bay.MainActivity;
import com.example.try_n_bay.R;

public class StartFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Button mCatalogButton;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TryNBay.StartFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        mCatalogButton = (Button) view.findViewById(R.id.plus_one_button);
        mCatalogButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d("TryNBay.StartFragment", "onClick");
        if (v.getId() == R.id.plus_one_button) {
            ((MainActivity) getActivity()).mSelectedGoodID = 0;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, new ListGoodsFragment())
                    .addToBackStack("StartFragment")
                    .commit();
        }
    }
}
