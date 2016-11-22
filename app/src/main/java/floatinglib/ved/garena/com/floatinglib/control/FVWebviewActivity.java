package floatinglib.ved.garena.com.floatinglib.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import floatinglib.ved.garena.com.floatinglib.R;
import floatinglib.ved.garena.com.floatinglib.model.FVPageInfo;
import floatinglib.ved.garena.com.floatinglib.utils.FVConstantValue;
import floatinglib.ved.garena.com.floatinglib.view.FVWebView;

/**
 * Created by linhtruong on 11/21/16 - 10:15.
 * Description: show webview, handle action bar back & close function
 */

public class FVWebviewActivity extends AppCompatActivity {

    private static final String TAG = "FVWebviewActivity";

    private FVWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initToolbar();
        initWebView();
        broadcastViewStatus(FVConstantValue.FV_STATUS.FV_HIDE);
    }

    private void initWebView() {
        Log.d(TAG, "initWebView: ");
        mWebView = (FVWebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new BaseWebViewClient());

        FVPageInfo pageInfo = FVPageInfo.getPageInfo();
        String postParams = pageInfo.buildUrl();
        if (!TextUtils.isEmpty(postParams) || !TextUtils.isEmpty(pageInfo.accessToken) || !TextUtils.isEmpty(pageInfo.timeStamp)) {
            if (!TextUtils.isEmpty(pageInfo.homeUrl)) {
                mWebView.postUrl(pageInfo.homeUrl, postParams.getBytes());
            } else {
                mWebView.postUrl(FVConstantValue.BASE_URL, postParams.getBytes());
            }
        } else {
            if (TextUtils.isEmpty(pageInfo.accessToken)) {
                Snackbar.make(mWebView.getRootView(), getResources().getString(R.string.error_access_token), Snackbar.LENGTH_LONG);
            }
            if (TextUtils.isEmpty(pageInfo.timeStamp)) {
                Snackbar.make(mWebView.getRootView(), getResources().getString(R.string.error_timestamp), Snackbar.LENGTH_LONG);
            }
            if (TextUtils.isEmpty(pageInfo.timeStamp) && TextUtils.isEmpty(pageInfo.accessToken)) {
                Snackbar.make(mWebView.getRootView(), getResources().getString(R.string.error_access_token_and_time_stamp), Snackbar.LENGTH_LONG);
            }

            // load home url
            mWebView.loadUrl(FVConstantValue.BASE_URL);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getResources().getString(R.string.toolbar_tittle));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            broadcastViewStatus(FVConstantValue.FV_STATUS.FV_SHOW);
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close) {
            broadcastViewStatus(FVConstantValue.FV_STATUS.FV_SHOW);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class BaseWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);
            return true;
        }
    }

    private void broadcastViewStatus(String status) {
        Log.d(TAG, "broadcastViewStatus: " + status);
        Intent intent = new Intent(this, FVService.class);
        intent.putExtra(FVConstantValue.FV_ENABLE, status);
        startService(intent);
    }
}
