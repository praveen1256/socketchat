package logintest.android.com.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Praveen on 20-10-2016.
 */

public class Utils {
    public static String getBase64Pic(String picturePath, Bitmap newPictureBitmap) {
        String encodedBitmap = null;
        if (newPictureBitmap != null) {
            String extension = picturePath.substring(picturePath.lastIndexOf(".") + 1);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
                newPictureBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            } else if (extension.equalsIgnoreCase("png")) {
                newPictureBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            } else {
                newPictureBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encodedBitmap = Base64.encodeToString(byteArray, Base64.URL_SAFE);
        }
        return encodedBitmap;
    }

    public static Bitmap decodeImage(String base64) {
        byte[] b = Base64.decode(base64, Base64.URL_SAFE);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

}
