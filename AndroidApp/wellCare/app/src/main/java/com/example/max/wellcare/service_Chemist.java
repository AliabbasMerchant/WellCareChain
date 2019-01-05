package com.example.max.wellcare;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;


public class service_Chemist extends Fragment{

    TextView scannedData;
    public service_Chemist() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private static final int SCAN_REQUEST_CODE = 0;
    private static final int PAY_REQUEST_CODE = 1;
    private static final int ALLOW_REQUEST_CODE = 2;



    public void scanQR(View v,int requestCode)
    {
        Intent intent = new Intent(getActivity(), QR_Reader_Activity.class);
        intent.putExtra("ScannedData","NONE");
        startActivityForResult(intent,requestCode);
        Log.i("DEBUG_UI","Button Clicked");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SCAN_REQUEST_CODE) {
            if (data.hasExtra("ScanRes") ) {
                StringBuffer t = new StringBuffer(data.getExtras().getString("ScanRes"));
                Toast.makeText(getActivity(), t.toString()+"SCAN", Toast.LENGTH_SHORT).show();
                payButton.setEnabled(true);
                allowAccessButton.setEnabled(true);
            }
        }

        else if (resultCode == RESULT_OK && requestCode == PAY_REQUEST_CODE) {
            if (data.hasExtra("ScanRes") ) {
                StringBuffer t = new StringBuffer(data.getExtras().getString("ScanRes"));
                Toast.makeText(getActivity(), t.toString() + "PAY", Toast.LENGTH_SHORT).show();
            }
        }
    }


    View view;
    Button scanButton,payButton,allowAccessButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_service__chemist, container, false);
         scanButton = (Button) view.findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(v,SCAN_REQUEST_CODE);
            }
        });

        allowAccessButton = (Button) view.findViewById(R.id.button_allowAccess);
        allowAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Allow Access to the Data
            }
        });

        payButton = (Button) view.findViewById(R.id.button_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(v,PAY_REQUEST_CODE);
            }
        });


        return view;
    }


}






