package floatinglib.ved.garena.com.floatinglib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import floatinglib.ved.garena.com.floatinglib.expose.FloatingViewManager;
import floatinglib.ved.garena.com.floatinglib.utils.FVConstantValue;

/**
 * Created by linhtruong on 11/21/16 - 10:02.
 * Description:
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
                        .accessToken(FVConstantValue.URL_VALUE.TOKEN)
                        .build();
/*
                FloatingViewManager.ViewBuilder viewBuilder = FloatingViewManager.ViewBuilder.newBuilder().drawable(R.drawable.ic_help)
                        .height(150).width(150).horizontalMargin(30).verticalMargin(50).onTop(true).onLeft(true).build();
*/

                FloatingViewManager.showView(TestLibActivity.this, builder);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FloatingViewManager.hideView(this);
    }
}
