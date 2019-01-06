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

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.math.BigInteger;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class service_Chemist extends Fragment {
    private static final String TAG = "service_Chemist";

    public service_Chemist() {
        // Required empty public constructor

    }

    TextView scannedData;
    String chemistEmailId, permissionId, payQR;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static final int SCAN_REQUEST_CODE = 0;
    private static final int PAY_REQUEST_CODE = 1;

    public void scanQR(View v, int requestCode) {
        Intent intent = new Intent(getActivity(), QR_Reader_Activity.class);
        intent.putExtra("ScannedData", "NONE");
        startActivityForResult(intent, requestCode);
        Log.i("DEBUG_UI", "Button Clicked");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SCAN_REQUEST_CODE) {
            if (data.hasExtra("ScanRes")) {
                StringBuffer t = new StringBuffer(data.getExtras().getString("ScanRes"));
//                Toast.makeText(getActivity(), t.toString()+"SCAN", Toast.LENGTH_SHORT).show();
                payButton.setEnabled(true);
                allowAccessButton.setEnabled(true);
                scannedData.setText(t.toString() + "Chemist");
                chemistEmailId = t.toString();
            }
        } else if (resultCode == RESULT_OK && requestCode == PAY_REQUEST_CODE) {
            if (data.hasExtra("ScanRes")) {
                StringBuffer t = new StringBuffer(data.getExtras().getString("ScanRes"));
                Toast.makeText(getActivity(), t.toString() + "PAY", Toast.LENGTH_SHORT).show();
                payQR = t.toString();
            }
        }
    }


    View view;
    Button scanButton, payButton, allowAccessButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_service__chemist, container, false);

        scannedData = (TextView) view.findViewById(R.id.extraData);

        scanButton = (Button) view.findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(v, SCAN_REQUEST_CODE);
            }
        });
        SharedPreferences sp = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        allowAccessButton = view.findViewById(R.id.button_allowAccess);
        allowAccessButton.setOnClickListener(v -> {
            String presFolderId = sp.getString("presFolderId", "");
            Gson gson = new Gson();
            String json = sp.getString("googleDriveService", "");
            Drive googleDriveService = gson.fromJson(json, Drive.class);
            DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
            mDriveServiceHelper.createPermission(presFolderId, chemistEmailId, "writer")
                    .addOnSuccessListener(id -> permissionId = id)
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Couldn't allow access.", exception));
        });
        payButton = view.findViewById(R.id.button_pay);
        payButton.setOnClickListener(v -> {
            scanQR(v, PAY_REQUEST_CODE);
            if (!permissionId.equals("null") && !permissionId.equals("")) {
                String presFolderId = sp.getString("presFolderId", "");
                Gson gson = new Gson();
                String json = sp.getString("googleDriveService", "");
                Drive googleDriveService = gson.fromJson(json, Drive.class);
                DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                mDriveServiceHelper.removePermission(presFolderId, permissionId)
                        .addOnSuccessListener(aVoid -> {
                        })
                        .addOnFailureListener(exception ->
                                Log.e(TAG, "Couldn't revoke access.", exception));
                BigInteger chemistId = new BigInteger(payQR.substring(0, payQR.indexOf(',')));
                BigInteger fees = new BigInteger(payQR.substring(payQR.indexOf(',') + 1));
                String _patientId = sp.getString("patientId", "0");
                BigInteger patientId = new BigInteger(_patientId);

                Thread thread = new Thread(() -> {

                    try {
                        Credentials credentials = WalletUtils.loadCredentials(
                                "aliabbas",
                                new java.io.File("Cred.json"));
                        BlockchainHelper.payToChemist(patientId, chemistId, fees, credentials);
                        Toast.makeText(getContext(), "Paid the Chemist!", Toast.LENGTH_SHORT).show();
                        view.findViewById(R.id.button_pay).setEnabled(false);
                        view.findViewById(R.id.button_allowAccess).setEnabled(false);
                    } catch (Exception e) {
                        Log.e(TAG, "getCredentials: error Credentials are null", e);
                        e.printStackTrace();
                    }

                });
                thread.start();
            }
        });
        return view;
    }


}






