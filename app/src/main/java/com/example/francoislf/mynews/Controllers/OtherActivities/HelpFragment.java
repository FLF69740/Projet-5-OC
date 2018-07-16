package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.francoislf.mynews.R;
import com.github.barteksc.pdfviewer.PDFView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpFragment extends Fragment {

    public static final String HELP_DOCUMENT_FILE = "MyNewsDoc.pdf";

    @BindView(R.id.pdf_viewer) PDFView mPDFView;

    public HelpFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        mPDFView.fromAsset(HELP_DOCUMENT_FILE).load();
        return view;
    }

}
