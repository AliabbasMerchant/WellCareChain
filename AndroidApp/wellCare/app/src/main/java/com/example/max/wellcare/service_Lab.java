package com.example.max.wellcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;

import java.math.BigInteger;


public class service_Lab extends Fragment{
    private static final String TAG = "service_Lab";
    public service_Lab() {
        // Required empty public constructor
    }
    TextView scannedData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    View view;
    Button scanButton,payButton,allowAccessButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_service__lab, container, false);

//        SharedPreferences sp = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        allowAccessButton = view.findViewById(R.id.button_allowAccess);
//        allowAccessButton.setOnClickListener(v -> {
//            String presFolderId = sp.getString("presFolderId", "");
//            Gson gson = new Gson();
//            String json = sp.getString("googleDriveService", "");
//            Drive googleDriveService = gson.fromJson(json, Drive.class);
//            DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
//            mDriveServiceHelper.createPermission(presFolderId, chemistEmailId, "writer")
//                    .addOnSuccessListener(id -> permissionId = id)
//                    .addOnFailureListener(exception ->
//                            Log.e(TAG, "Couldn't allow access.", exception));
//        });
//        payButton = view.findViewById(R.id.button_pay);
//        payButton.setOnClickListener(v -> {
//            scanQR(v, PAY_REQUEST_CODE);
//            if(!permissionId.equals("null") && !permissionId.equals("")) {
//                String presFolderId = sp.getString("presFolderId", "");
//                Gson gson = new Gson();
//                String json = sp.getString("googleDriveService", "");
//                Drive googleDriveService = gson.fromJson(json, Drive.class);
//                DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
//                mDriveServiceHelper.removePermission(presFolderId, permissionId)
//                        .addOnSuccessListener(aVoid -> {})
//                        .addOnFailureListener(exception ->
//                                Log.e(TAG, "Couldn't revoke access.", exception));
//                BigInteger chemistId = new BigInteger(payQR.substring(0, payQR.indexOf(',')));
//                BigInteger fees = new BigInteger(payQR.substring(payQR.indexOf(',')+1));
//                String _patientId = sp.getString("patientId", "0");
//                BigInteger patientId = new BigInteger(_patientId);
//
//                Thread thread = new Thread(() -> {
//                    BlockchainHelper.payToChemist(patientId, chemistId, fees);
//                    Toast.makeText(getContext(), "Paid the Chemist!", Toast.LENGTH_SHORT).show();
//                    view.findViewById(R.id.button_pay).setEnabled(false);
//                    view.findViewById(R.id.button_allowAccess).setEnabled(false);
//                });
//                thread.start();
//            }
//        });
        return view;
    }
}