package com.example.max.wellcare;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class service_Emergency extends Fragment {
    public service_Emergency() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_service__emergency, container, false);
        TextView link = v.findViewById(R.id.Link);
        SharedPreferences sp = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String s = sp.getString("infoFolderId", "Not found");
        if(!s.equals("Not found")) {
            link.setText("https://drive.google.com/open?id=".concat(s));
        }
        return v;
    }

}
