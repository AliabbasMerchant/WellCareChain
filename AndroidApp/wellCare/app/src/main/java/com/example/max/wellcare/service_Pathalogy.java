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

import com.google.api.services.drive.Drive;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.math.BigInteger;

import static android.app.Activity.RESULT_OK;


public class service_Pathalogy extends Fragment {
    private static final String TAG = "service_Pathalogy";
    public service_Pathalogy() {
        // Required empty public constructor
    }
    TextView scannedData;
    String qrText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private static final int SCAN_REQUEST_CODE = 0;

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
//                Toast.makeText(getActivity(), t.toString()+"SCAN", Toast.LENGTH_SHORT).show();
                payButton.setEnabled(true);
                scannedData.setText(t.toString() + "Pathology");
            }
        }
    }

    View view;
    Button scanButton,payButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_service__pathalogy, container, false);

        scannedData = (TextView) view.findViewById(R.id.extraData);
        scanButton = (Button) view.findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(v,SCAN_REQUEST_CODE);
            }
        });

        SharedPreferences sp = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        payButton = (Button) view.findViewById(R.id.button_pay);
        payButton.setOnClickListener(v -> {
            String msg = qrText.substring(0, qrText.indexOf(','));
            BigInteger pathologyId = new BigInteger(qrText.substring(qrText.indexOf(',')+1, qrText.lastIndexOf(',')));
            BigInteger fees = new BigInteger(qrText.substring(qrText.lastIndexOf(',') + 1));
            Thread thread = new Thread(() -> {
                try {
                    Credentials credentials = WalletUtils.loadCredentials(
                            "aliabbas",
                            new java.io.File("Cred.json"));
                    String pathologyEmail = BlockchainHelper.getPathologyEmail(pathologyId, credentials);
                    String reportsFolderId = sp.getString("reportsFolderId", "");
                    String _patientId = sp.getString("patientId", "0");
                    BigInteger patientId = new BigInteger(_patientId);
                    Gson gson = new Gson();
                    String json = sp.getString("googleDriveService", "");
                    Drive googleDriveService = gson.fromJson(json, Drive.class);
                    DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                    mDriveServiceHelper.giveWriteAccessForXMillis(reportsFolderId, pathologyEmail, 2*60*1000);
                    BlockchainHelper.payToPathology(patientId, pathologyId, fees, msg, credentials);
                    Toast.makeText(getContext(), "Paid the Pathology!", Toast.LENGTH_SHORT).show();
                    view.findViewById(R.id.button_pay).setEnabled(false);
                } catch (Exception e) {
                    Log.e(TAG, "getCredentials: error Credentials are null", e);
                    e.printStackTrace();
                }
            });
            thread.start();
        });
        return view;
    }


}
