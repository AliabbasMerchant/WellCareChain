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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private String name, emailAddress, folderId, presFolderId, infoFolderId, reportsFolderId, docRepFolderId;
    private long patientId;
    private DriveServiceHelper mDriveServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.registerButton).setOnClickListener(view -> register());
        requestSignIn();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please Register!", Toast.LENGTH_SHORT).show();
    }

    private void register() {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Registering...");
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
            folderId = pref.getString("folderId", "root");
            presFolderId = pref.getString("presFolderId", "root");
            infoFolderId = pref.getString("infoFolderId", "root");
            reportsFolderId = pref.getString("reportsFolderId", "root");
            docRepFolderId = pref.getString("docRepFolderId", "root");
            registerOnBlockchain();
            editor.putBoolean("registered", true);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
    private void registerOnBlockchain() {
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
            patientId = BlockchainHelper.register(name, emailAddress, driveURL, presURL, infoURL, reportsURL);
            editor.putLong("patientId", patientId);
            editor.putString("address", sAddress);
            editor.putString("privateKey", sPrivateKeyInHex);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == Activity.RESULT_OK && resultData != null) {
            handleSignInResult(resultData);
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    /**
     * Starts a sign-in activity using {@link #REQUEST_CODE_SIGN_IN}.
     */
    private void requestSignIn() {
        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
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
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, ImmutableSet.of(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("WellCareChain")
                                    .build();

                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));
    }

}