package logintest.android.com.util;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.File;

public class ImageRotation {
	Context context;  Uri imageUri;  String imagePath;
	public ImageRotation(Context context, Uri imageUri, String imagePath) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.imageUri = imageUri;
		this.imagePath = imagePath;
	}
	
	public  int getCameraPhotoOrientation(){
	     int rotate = 0;
	     try {
	         context.getContentResolver().notifyChange(imageUri, null);
	         File imageFile = new File(imagePath);
	         ExifInterface exif = new ExifInterface(
	                 imageFile.getAbsolutePath());
	         int orientation = exif.getAttributeInt(
	                 ExifInterface.TAG_ORIENTATION,
	                 ExifInterface.ORIENTATION_NORMAL);

	         switch (orientation) {
	         case ExifInterface.ORIENTATION_ROTATE_270:
	             rotate = 270;
	             break;
	         case ExifInterface.ORIENTATION_ROTATE_180:
	             rotate = 180;
	             break;
	         case ExifInterface.ORIENTATION_ROTATE_90:
	             rotate = 90;
	             break;
	         }

	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	    return rotate;
	 }
}
