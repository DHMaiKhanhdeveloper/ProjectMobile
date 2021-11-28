package com.example.fer_medindex.utils;

import android.content.Context;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class Extensions {
    public static Matrix asMatrixByOrientation(ExifInterface exif) {
        Matrix m = new Matrix();
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90: m.postRotate(90f); break;
            case ExifInterface.ORIENTATION_ROTATE_180: m.postRotate(180f); break;
            case ExifInterface.ORIENTATION_ROTATE_270: m.postRotate(270f); break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL: throw new UnsupportedOperationException();
            case ExifInterface.ORIENTATION_FLIP_VERTICAL: throw new UnsupportedOperationException();
        }
        return m;
    }

    public static File cache(Context c, InputStream in) {
        String filename = String.valueOf(Calendar.getInstance().getTimeInMillis());
        File f = new File(c.getCacheDir(), filename);

        try {
            FileOutputStream out = new FileOutputStream(f);
            try {
                byte[] buf = new byte[1024];
                int len;
                while (0 < (len = in.read(buf))) {
                    out.write(buf, 0, len);
                }
                return f;

            } finally {
                out.flush();
                out.close();
            }

        } catch (FileNotFoundException e) {
            throw new AssertionError();

        } catch (IOException e) {
            throw new AssertionError();
        }
    }

}
