package com.youtube.sorcjc.incidencias;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.widget.Spinner;

public class Global {

    public static void saveInSharedPreferences(Activity activity, String key, String value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("global_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveInSharedPreferences(Activity activity, String key, int value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("global_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getFromSharedPreferences(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences("global_preferences", Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static int getIntFromSharedPreferences(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences("global_preferences", Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 1);
    }

    public static int getSpinnerSelectedIndex(Spinner spinner)
    {
        final String selectedString = spinner.getSelectedItem().toString();

        int index = 0;
        for (int i=0;i<spinner.getCount();i++)
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(selectedString)){
                index = i;
                break;
            }

        return index;
    }

//    public static String getBase64FromBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//        byte[] byteFormat = stream.toByteArray();
//        // Get the base 64 string
//        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
//    }


    public static void showInformationDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
