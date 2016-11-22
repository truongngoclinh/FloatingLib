package floatinglib.ved.garena.com.floatinglib.control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import floatinglib.ved.garena.com.floatinglib.model.FVInfo;
import floatinglib.ved.garena.com.floatinglib.model.FVPageInfo;
import floatinglib.ved.garena.com.floatinglib.utils.FVConstantValue;
import floatinglib.ved.garena.com.floatinglib.utils.FVScreenUtils;
import floatinglib.ved.garena.com.floatinglib.view.FVLayout;

/**
 * Created by linhtruong on 11/18/16 - 10:38.
 * Description: service to start floating view
 */

public class FVService extends Service {

    private static final String TAG = "FVService";

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private FVLayout mFloatingView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        initView(this);
        initWindowManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        String fvStatus = intent.getStringExtra(FVConstantValue.FV_ENABLE);
        if (!TextUtils.isEmpty(fvStatus)) {
            if (fvStatus.equals(FVConstantValue.FV_STATUS.FV_SHOW)) {
                onShowView();
            } else if (fvStatus.equals(FVConstantValue.FV_STATUS.FV_HIDE)) {
                onHideView();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        if (mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
            mFloatingView = null;
        }

        FVInfo.getInfo().clear();
        FVPageInfo.getPageInfo().clear();
    }

    private void initView(Context context) {
        mFloatingView = new FVLayout(context);
    }

    private void initWindowManager(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        FVInfo info = FVInfo.getInfo();
        int sum = info.onTop + info.onBottom + info.onLeft + info.onRight;
        if ((sum != 2 && sum != 1) || (info.onTop + info.onBottom == 2) || (info.onLeft + info.onRight == 2)) { // default
            mParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        } else {
            if (info.onTop == 1) {
                mParams.gravity = Gravity.TOP;
            }
            if (info.onBottom == 1) {
                mParams.gravity = Gravity.BOTTOM;
            }
            if (info.onLeft == 1) {
                mParams.gravity = Gravity.LEFT;
            }
            if (info.onRight == 1) {
                mParams.gravity = Gravity.RIGHT;
            }
        }

        mParams.x = FVScreenUtils.dpToPx(context, info.horizontalMargin);
        mParams.y = FVScreenUtils.dpToPx(context, info.verticalMargin);

        // add view
        mWindowManager.addView(mFloatingView, mParams);
    }

    public void onShowView() {
        if (mFloatingView != null) {
            Log.d(TAG, "onShowView: ");
            mFloatingView.showView();
        }
    }

    public void onHideView() {
        if ((mFloatingView != null) && (mFloatingView.getVisibility() == View.VISIBLE)) {
            Log.d(TAG, "onHideView: ");
            mFloatingView.hideView();
        }
    }
}
