package floatinglib.ved.garena.com.floatinglib.model;

import floatinglib.ved.garena.com.floatinglib.utils.FVConstantValue;

/**
 * Created by linhtruong on 11/21/16 - 15:49.
 * Description: @Singleton floating view info
 */

public class FVInfo {

    public int horizontalMargin;
    public int verticalMargin;
    public int width;
    public int height;

    public int onTop;
    public int onBottom;
    public int onLeft;
    public int onRight;

    public int drawable;

    private static FVInfo mInstance = null;

    protected FVInfo() {
        horizontalMargin = FVConstantValue.FVVIEW_MARGIN_Y;
        verticalMargin = FVConstantValue.FVVIEW_MARGIN_X;
        width = FVConstantValue.FVVIEW_WIDTH;
        height = FVConstantValue.FVVIEW_HEIGHT;
        onTop = onBottom = onLeft = onRight = 0;
    }

    public static synchronized FVInfo getInfo() {
        if (mInstance == null) {
            mInstance = new FVInfo();
        }

        return mInstance;
    }

    public void create(int drawable, int horizontalMargin, int verticalMargin, int width, int height, boolean onTop, boolean onBottom, boolean onLeft, boolean onRight) {
        if (drawable > 0) {
            mInstance.drawable = drawable;
        }
        if (horizontalMargin > 0) {
            mInstance.horizontalMargin = horizontalMargin;
        }
        if (verticalMargin > 0) {
            mInstance.verticalMargin = verticalMargin;
        }
        if (width > 0) {
            mInstance.width = width;
        }
        if (height > 0) {
            mInstance.height = height;
        }
        mInstance.onTop = onTop ? 1 : 0;
        mInstance.onBottom = onBottom ? 1 : 0;
        mInstance.onLeft = onLeft ? 1 : 0;
        mInstance.onRight = onRight ? 1 : 0;
    }

    public void clear() {
        mInstance = null;
    }

}
