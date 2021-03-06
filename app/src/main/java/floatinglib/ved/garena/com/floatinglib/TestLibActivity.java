package floatinglib.ved.garena.com.floatinglib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import floatinglib.ved.garena.com.floatinglib.expose.FloatingViewManager;

/**
 * Created by linhtruong on 11/21/16 - 10:02.
 * Description: test APIs
 */

public class TestLibActivity extends Activity {

    private static final String TAG = "TestLibActivity";
    public static String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_lib);

        Log.d(TAG, "onCreate: " + test);

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingViewManager.UrlBuilder builder = FloatingViewManager.UrlBuilder.newBuilder()
                        .homeUrl("http://hotro.garena.vn/app-auth")
                        .redirectUrl("https://hotro.garena.vn/gui-yeu-cau?embedded=1")
                        .build();
                FloatingViewManager.ViewBuilder viewBuilder = FloatingViewManager.ViewBuilder.newBuilder().drawable(R.drawable.ic_help)
                        .height(80).width(80).horizontalMargin(20).verticalMargin(20).onBottom(true).onLeft(true).build();

                FloatingViewManager.showView(TestLibActivity.this, builder, viewBuilder);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FloatingViewManager.hideView(this);
    }
}
