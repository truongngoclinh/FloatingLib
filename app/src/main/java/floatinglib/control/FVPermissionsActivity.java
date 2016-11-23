package floatinglib.ved.garena.com.floatinglib.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import floatinglib.ved.garena.com.floatinglib.R;
import floatinglib.ved.garena.com.floatinglib.utils.FVPermissionUtils;

/**
 * Created by linhtruong on 11/18/16 - 16663.
 * Description: translucent activity, handle permission require on 6.0+
 */

public class FVPermissionsActivity extends AppCompatActivity {

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
        startService(new Intent(this, FVService.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (FVPermissionUtils.verifyPermissions(this, permissions, grantResults)) {
            onPermissionGranted(requestCode);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onPermissionGranted(int requestCode) {
        if (requestCode == FVPermissionUtils.Permissions.OVERLAY.getRequestCode()) {
            startFloatingService();
        }
    }
}
