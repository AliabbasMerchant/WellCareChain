package com.example.max.wellcare;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;

    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }

    public Task<String> createFolder(String name, List<String> parents) {
        return Tasks.call(mExecutor, () -> {
            File folder = new File()
                    .setName(name)
                    .setParents(parents)
                    .setMimeType("application/vnd.google-apps.folder");
            File googleFile = mDriveService.files().create(folder).execute();
            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }
            return googleFile.getId();
        });
    }
    public Task<Void> makePublicReadable(String fileId) {
        return Tasks.call(mExecutor, () -> {
            Permission userPermission = new Permission()
                    .setType("anyone")
                    .setRole("reader");
            mDriveService.permissions().create(fileId, userPermission).execute();
            return null;
        });
    }
    public Task<String> createPermission(String fileId, String emailId, String permissionType) {
        return Tasks.call(mExecutor, () -> {
            Permission userPermission = new Permission()
                    .setType("user")
                    .setRole(permissionType)
                    .setEmailAddress(emailId);
            Permission permission = mDriveService.permissions().create(fileId, userPermission).execute();
            return permission.getId();
        });
    }
    public Task<Void> removePermission(String fileId, String permissionId) {
        return Tasks.call(mExecutor, () -> {
            mDriveService.permissions().delete(fileId, permissionId).execute();
            return null;
        });
    }
    public Task<String> updatePermission(String fileId, String permissionId, String emailId, String permissionType) {
        removePermission(fileId, permissionId);
        Tasks.call(mExecutor, () -> {
            mDriveService.permissions().delete(fileId, permissionId).execute();
            return null;
        });
        return Tasks.call(mExecutor, () -> {
            Permission userPermission = new Permission()
                    .setType("user")
                    .setRole(permissionType)
                    .setEmailAddress(emailId);
            Permission permission = mDriveService.permissions().create(fileId, userPermission).execute();
            return permission.getId();
        });
    }
    public void giveWriteAccessFor5Minutes(String fileId, String emailId) {
        createPermission(fileId, emailId, "write")
            .addOnSuccessListener(permissionId -> {
                Log.e("DriveServiceHelper", "giveWriteAccessFor5Minutes");
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    removePermission(fileId, permissionId);
                    Log.e("DriveServiceHelper", "Permission Revoked");
                }, 2*60*1000);
            })
            .addOnFailureListener(exception -> {});
    }


//    /**
//     * Opens the file identified by {@code fileId} and returns a {@link Pair} of its name and
//     * contents.
//     */
//    public Task<Pair<String, String>> readFile(String fileId) {
//        return Tasks.call(mExecutor, () -> {
//            // Retrieve the metadata as a File object.
//            File metadata = mDriveService.files().get(fileId).execute();
//            String name = metadata.getName();
//
//            // Stream the file contents to a String.
//            try (InputStream is = mDriveService.files().get(fileId).executeMediaAsInputStream();
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                String contents = stringBuilder.toString();
//
//                return Pair.create(name, contents);
//            }
//        });
//    }
//
//    /**
//     * Updates the file identified by {@code fileId} with the given {@code name} and {@code
//     * content}.
//     */
//    public Task<Void> saveFile(String fileId, String name, String content) {
//        return Tasks.call(mExecutor, () -> {
//            // Create a File containing any metadata changes.
//            File metadata = new File().setName(name);
//
//            // Convert content to an AbstractInputStreamContent instance.
//            ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);
//
//            // Update the metadata and contents.
//            mDriveService.files().update(fileId, metadata, contentStream).execute();
//            return null;
//        });
//    }
//
//    /**
//     * Returns a {@link FileList} containing all the visible files in the user's My Drive.
//     *
//     * <p>The returned list will only contain files visible to this app, i.e. those which were
//     * created by this app. To perform operations on files not created by the app, the project must
//     * request Drive Full Scope in the <a href="https://play.google.com/apps/publish">Google
//     * Developer's Console</a> and be submitted to Google for verification.</p>
//     */
//    public Task<FileList> queryFiles() {
//        return Tasks.call(mExecutor, () ->
//                mDriveService.files().list().setSpaces("drive").execute());
//    }
//
//    /**
//     * Returns an {@link Intent} for opening the Storage Access Framework file picker.
//     */
//    public Intent createFilePickerIntent() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("text/plain");
//
//        return intent;
//    }
//
//    /**
//     * Opens the file at the {@code uri} returned by a Storage Access Framework {@link Intent}
//     * created by {@link #createFilePickerIntent()} using the given {@code contentResolver}.
//     */
//    public Task<Pair<String, String>> openFileUsingStorageAccessFramework(
//            ContentResolver contentResolver, Uri uri) {
//        return Tasks.call(mExecutor, () -> {
//            // Retrieve the document's display name from its metadata.
//            String name;
//            try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
//                if (cursor != null && cursor.moveToFirst()) {
//                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                    name = cursor.getString(nameIndex);
//                } else {
//                    throw new IOException("Empty cursor returned for file.");
//                }
//            }
//
//            // Read the document's contents as a String.
//            String content;
//            try (InputStream is = contentResolver.openInputStream(uri);
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                content = stringBuilder.toString();
//            }
//
//            return Pair.create(name, content);
//        });
//    }
}
