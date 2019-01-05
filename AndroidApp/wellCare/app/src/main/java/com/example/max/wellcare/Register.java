package com.example.max.wellcare;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Collections;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private String name, emailAddress, folderId, presFolderId, infoFolderId, reportsFolderId, docRepFolderId;
    private BigInteger patientId;
    private DriveServiceHelper mDriveServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.e(TAG, "onCreate: Register");

        requestSignIn();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please Register!", Toast.LENGTH_SHORT).show();
    }

    public void register(View v) {
        try {
            FileOutputStream fOut = openFileOutput("samplefile.txt",
                    MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write("yo");
            osw.flush();
            osw.close();
            Log.e(TAG, "register: file written");
            FileInputStream fIn = openFileInput("samplefile.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char["yo".length()];
            isr.read(inputBuffer);
            String readString = new String(inputBuffer);
            boolean isTheSame = "yo".equals(readString);
            Log.e("File Reading stuff", "success = " + isTheSame);
        } catch (IOException e) {
            Log.e(TAG, "register: Error");
            e.printStackTrace();
        }
        if (mDriveServiceHelper != null) {
            Log.e(TAG, "Registering...");
            Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            mDriveServiceHelper.createFolder("WellCareChain", Collections.singletonList("root"))
                .addOnSuccessListener(fileId -> {
                    editor.putString("folderId", fileId);
                    mDriveServiceHelper.createFolder("Prescription", Collections.singletonList(fileId))
                            .addOnSuccessListener(id -> {
                                editor.putString("presFolderId", id);
                            })
                            .addOnFailureListener(exception ->
                                    Log.e(TAG, "Couldn't create file.", exception));
                    mDriveServiceHelper.createFolder("Emergency Info", Collections.singletonList(fileId))
                            .addOnSuccessListener(id -> {
                                editor.putString("infoFolderId", id);
                                mDriveServiceHelper.makePublicReadable(id)
                                        .addOnSuccessListener(permissionId -> {})
                                        .addOnFailureListener(exception ->
                                                Log.e(TAG, "Couldn't make emergency info public.", exception));
                            })
                            .addOnFailureListener(exception ->
                                    Log.e(TAG, "Couldn't create file.", exception));
                    mDriveServiceHelper.createFolder("Reports", Collections.singletonList(fileId))
                            .addOnSuccessListener(id -> {
                                editor.putString("reportsFolderId", id);
                            })
                            .addOnFailureListener(exception ->
                                    Log.e(TAG, "Couldn't create file.", exception));
                    mDriveServiceHelper.createFolder("Doctor Reports", Collections.singletonList(fileId))
                            .addOnSuccessListener(id -> {
                                editor.putString("docRepFolderId", id);
                            })
                            .addOnFailureListener(exception ->
                                    Log.e(TAG, "Couldn't create file.", exception));
                    editor.commit();
                })
                .addOnFailureListener(exception ->
                        Log.e(TAG, "Couldn't create file.", exception));
            Log.e(TAG, "register: Made google drive folders");
            folderId = pref.getString("folderId", "root");
            presFolderId = pref.getString("presFolderId", "root");
            infoFolderId = pref.getString("infoFolderId", "root");
            reportsFolderId = pref.getString("reportsFolderId", "root");
            docRepFolderId = pref.getString("docRepFolderId", "root");
            registerOnBlockchain();
            editor.putBoolean("registered", true);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            Log.e(TAG, "register: Drive service helper is null");
        }
    }
    private void registerOnBlockchain() {
        Log.e(TAG, "registerOnBlockchain: in");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String driveURL = "https://drive.google.com/open?id=" + folderId;
        String presURL = "https://drive.google.com/open?id=" + presFolderId;
        String infoURL = "https://drive.google.com/open?id=" + infoFolderId;
        String reportsURL = "https://drive.google.com/open?id=" + reportsFolderId;
        JSONObject credentials = BlockchainHelper.generateCredentials();
        try {
            String sPrivateKeyInHex = credentials.getString("address");
            String sAddress = credentials.getString("privateKey");
            BlockchainHelper.sPrivateKeyInHex = sPrivateKeyInHex;
            BlockchainHelper.sAddress = sAddress;

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle("Transfer Ether")
                .setMessage("Please Transfer Ether To Your WellCareChain Account: " + sAddress)
                .setPositiveButton("Ok, I have transferred ether to my account", (dialog, which) -> { })
                .show();

            patientId = BlockchainHelper.register(name, emailAddress, driveURL, presURL, infoURL, reportsURL);
            editor.putString("patientId", patientId.toString());
            editor.putString("address", sAddress);
            editor.putString("privateKey", sPrivateKeyInHex);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "registerOnBlockchain: done");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Log.e(TAG, "onActivityResult: " + requestCode + resultCode + resultData);
        if (resultCode == Activity.RESULT_OK) {
            Log.e(TAG, "onActivityResult: result code matches");
            if(resultData != null) {
                Log.e(TAG, "onActivityResult: resultData is null");
                handleSignInResult(resultData);
            }
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    /**
     * Starts a sign-in activity using {@link #REQUEST_CODE_SIGN_IN}.
     */
    private void requestSignIn() {
        Log.e(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE), new Scope(DriveScopes.DRIVE), new Scope(DriveScopes.DRIVE_APPDATA))
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
        Log.e(TAG, "requestSignIn: Strarting Activity For Result");
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    /**
     * Handles the {@code result} of a completed sign-in activity initiated from {@link
     * #requestSignIn()}.
     */
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnSuccessListener(googleAccount -> {
                emailAddress = googleAccount.getEmail();
                name = googleAccount.getDisplayName();
                Log.e(TAG, "Signed in as " + googleAccount.getEmail());
                Log.e(TAG, "Signed in as " + googleAccount.getDisplayName());

                mDriveServiceHelper = DriveServiceHelper.getGoogleDriveServiceHelper(this, googleAccount);

                SharedPreferences  mPrefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                mPrefs.edit().putString("googleAccount", new Gson().toJson(googleAccount)).commit();
                Log.e(TAG, "handleSignInResult: Sign in completed");
            })
            .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));
    }

}