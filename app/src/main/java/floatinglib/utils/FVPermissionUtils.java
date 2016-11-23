package floatinglib.ved.garena.com.floatinglib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import floatinglib.ved.garena.com.floatinglib.R;

/**
 * Created by linhtruong on 11/18/16 - 16:28.
 * Description: handle permission requests on 6.0 and above versions.
 */

public class FVPermissionUtils {

    public static final int REQUEST_PERMISSION_SETTING = 0x1739;
    public static boolean sShownRationalDialogOnRequest = false;

    public static boolean isPermissionGranted(Context context, Permissions permission) {
        return ContextCompat.checkSelfPermission(context, permission.getPermission())
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests the permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     *
     * @param activity
     */
    public static void requestPermission(final Activity activity, final Permissions permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.getPermission())) {
            sShownRationalDialogOnRequest = true;
            showRationaleDialog(activity, R.string.dialog_permission_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity, new String[]{permission.getPermission()},
                            permission.getRequestCode());
                }
            });
        } else {
            // Permission has not been granted yet. Request it directly.
            sShownRationalDialogOnRequest = false;
            ActivityCompat.requestPermissions(activity, new String[]{permission.getPermission()},
                    permission.getRequestCode());
        }
    }

    /**
     * Requests the permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     *
     * @param fragment
     */
    public static void requestPermission(final Fragment fragment, final Permissions permission) {
        if (fragment.shouldShowRequestPermissionRationale(permission.getPermission())) {
            sShownRationalDialogOnRequest = true;
            showRationaleDialog(fragment.getContext(), R.string.dialog_permission_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fragment.requestPermissions(new String[]{permission.getPermission()},
                            permission.getRequestCode());
                }
            });
        } else {
            // Permission has not been granted yet. Request it directly.
            sShownRationalDialogOnRequest = false;
            fragment.requestPermissions(new String[]{permission.getPermission()},
                    permission.getRequestCode());
        }
    }

    private static void showRationaleDialog(Context context, @StringRes int posTextRes,
                                            DialogInterface.OnClickListener callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_permission_message);
        builder.setPositiveButton(posTextRes, callback);
        builder.setNegativeButton(R.string.dialog_permission_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     */
    public static boolean verifyPermissions(final Activity activity, String[] permissions, int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int i = 0; i < permissions.length; i++) {
            int result = grantResults[i];
            if (result != PackageManager.PERMISSION_GRANTED) {
                boolean shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permissions[i]);
                if (!shouldShowRationale) {
                    // it means user has selected "never ask again"
                    if (sShownRationalDialogOnRequest) {
                        return false;
                    }
                    sShownRationalDialogOnRequest = false;
                    showRationaleDialog(activity, R.string.dialog_permission_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        }
                    });
                }
                return false;
            }
        }
        return true;
    }

    // Dangerous permission enum
    public enum Permissions {

        // CAMERA
        CAMERA(Manifest.permission.CAMERA, 1),

        // STORAGE
        READ_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE, 2),
        WRITE_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, 3),

        // OVERLAY
        OVERLAY(Manifest.permission.SYSTEM_ALERT_WINDOW, 2),
        ;

        private final String permission;
        private final int requestCode;

        Permissions(String value, int requestCode) {
            this.permission = value;
            this.requestCode = requestCode;
        }

        public String getPermission() {
            return permission.toString();
        }

        public int getRequestCode() {
            return requestCode;
        }
    }
}
