package floatinglib.ved.garena.com.floatinglib.control;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import floatinglib.ved.garena.com.floatinglib.R;
import floatinglib.ved.garena.com.floatinglib.utils.FVPermissionUtils;

/**
 * Created by linhtruong on 11/18/16 - 16663.
 * Description: translucent activity, handle permission require on 6.0+
 */

public class FVPermissionsActivity extends AppCompatActivity {

    private static final String TAG = "FVPermissionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FVPermissionUtils.isPermissionGranted(this, FVPermissionUtils.Permissions.OVERLAY)) {
            startFloatingService();
        } else {
            FVPermissionUtils.requestPermission(this, FVPermissionUtils.Permissions.OVERLAY);
        }
    }

    private void startFloatingService() {
        Log.d(TAG, "startFloatingService: ");
        startService(new Intent(this, FVService.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        if (FVPermissionUtils.verifyPermissions(this, permissions, grantResults)) {
            onPermissionGranted(requestCode);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        if (requestCode == FVPermissionUtils.REQUEST_PERMISSION_SETTING) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    startFloatingService();
                } else {
                    Snackbar.make(this.findViewById(android.R.id.content), getResources().getString(R.string.dialog_permission_message), Snackbar.LENGTH_LONG);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onPermissionGranted(int requestCode) {
        Log.d(TAG, "onPermissionGranted: ");
        if (requestCode == FVPermissionUtils.Permissions.OVERLAY.getRequestCode()) {
            startFloatingService();
        }
    }
}
