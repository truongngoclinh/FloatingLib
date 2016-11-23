package floatinglib.ved.garena.com.floatinglib.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import floatinglib.ved.garena.com.floatinglib.R;
import floatinglib.ved.garena.com.floatinglib.control.FVWebviewActivity;
import floatinglib.ved.garena.com.floatinglib.model.FVInfo;
import floatinglib.ved.garena.com.floatinglib.utils.FVScreenUtils;

/**
 * Created by linhtruong on 11/18/16 - 10:42.
 * Description: floating layout, currently contains only an image
 */

public class FVLayout extends RelativeLayout {

    private static final String TAG = "FVLayout";

    private ImageView mFloatingView;
    private Context mContext;

    public FVLayout(Context context) {
        super(context);

        mContext = context;
        initLayout(context);
    }

    private void initLayout(Context context) {
        RelativeLayout.LayoutParams rootParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(rootParams);

        mFloatingView = new ImageView(context);
        mFloatingView.setScaleType(ImageView.ScaleType.FIT_XY);
        mFloatingView.setOnClickListener(onClickListener);

        this.addView(mFloatingView);

        FVInfo info = FVInfo.getInfo();
        int w = FVScreenUtils.dpToPx(context, info.width);
        int h = FVScreenUtils.dpToPx(context, info.height);

        RelativeLayout.LayoutParams childParams = (LayoutParams) mFloatingView.getLayoutParams();
        childParams.width = w;
        childParams.height = h;

        if (info.drawable > 0) {
            mFloatingView.setImageResource(info.drawable);
        } else {
            mFloatingView.setImageResource(R.drawable.ic_floating_button);
        }

        invalidate();
    }

    public void hideView() {
        this.setVisibility(View.GONE);
    }

    public void showView() {
        this.setVisibility(View.VISIBLE);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
            Intent intent = new Intent(mContext, FVWebviewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    };
}
