package com.example.francoislf.mynews.Controllers;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.francoislf.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    @BindView(R.id.fragment_root) LinearLayout mLinearLayout;
    @BindView(R.id.fragment_page_title) TextView mTextView;


    public static final String KEY_POSITION = "KEY_POSITION";

    public PageFragment() {
        // Required empty public constructor
    }

    // Methods to create new instance of fragment
    public static PageFragment newInstance(int position){

        PageFragment fragment = new PageFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);

        int position = getArguments().getInt(KEY_POSITION, 1);

        Log.i("TAGI", "position " + position);

        mTextView.setText("PAGE NUMERO : " + position);
        mLinearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        return view;
    }

}
