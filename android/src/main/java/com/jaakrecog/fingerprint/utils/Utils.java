package com.jaakrecog.fingerprint.utils;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[inputStream.read()];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }

    public static void saveWsqToCache(Context context,String fileName){
        File file = new File(context.getCacheDir(), fileName);
        Uri uri = FileProvider.getUriForFile(context,"com.plugin.fileprovider", file);


    }

}

