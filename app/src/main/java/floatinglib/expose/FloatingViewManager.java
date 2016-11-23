package floatinglib.ved.garena.com.floatinglib.expose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

import floatinglib.ved.garena.com.floatinglib.TestLibActivity;
import floatinglib.ved.garena.com.floatinglib.control.FVPermissionsActivity;
import floatinglib.ved.garena.com.floatinglib.control.FVService;
import floatinglib.ved.garena.com.floatinglib.model.FVPageInfo;
import floatinglib.ved.garena.com.floatinglib.model.FVInfo;
import floatinglib.ved.garena.com.floatinglib.utils.FVPermissionUtils;

/**
 * Created by linhtruong on 11/18/16 - 10:43.
 * Description: expose APIs for client
 */

public class FloatingViewManager {

    /**
     * Check overlay permission, then start service to enable floating view.
     * Remember to stop service {@link #hideView(Context)}
     *
     * @param context
     * @param builder
     */
    public static void showView(Context context, UrlBuilder builder) {
        TestLibActivity.test = "QBCXSFASDFADS";
        update(builder);
        if (FVPermissionUtils.isPermissionGranted(context, FVPermissionUtils.Permissions.OVERLAY)) {
            context.startService(new Intent(context, FVService.class));
        } else {
            Intent startIntent = new Intent(context, FVPermissionsActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * check overlay permission, then start service to enable floating view.
     * Remember to stop service {@link #hideView(Context)}
     *
     * @param context
     * @param urlBuilder
     * @param viewBuilder
     */
    public static void showView(Context context, UrlBuilder urlBuilder, ViewBuilder viewBuilder) {
        update(urlBuilder);
        update(viewBuilder);
        if (FVPermissionUtils.isPermissionGranted(context, FVPermissionUtils.Permissions.OVERLAY)) {
            context.startService(new Intent(context, FVService.class));
        } else {
            Intent startIntent = new Intent(context, FVPermissionsActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * stop service, remove floating view
     *
     * @param context
     */
    public static void hideView(Context context) {
        context.stopService(new Intent(context, FVService.class));
    }

    /**
     * Custom your FloatingView, instance of {@link android.widget.ImageView}
     * passing through {@link #showView(Context, UrlBuilder, ViewBuilder)}
     */
    public static class ViewBuilder {

        public int horizontalMargin;
        public int verticalMargin;
        public int width;
        public int height;

        public boolean onTop;
        public boolean onBottom;
        public boolean onLeft;
        public boolean onRight;

        public int drawable;

        public ViewBuilder() {
            horizontalMargin = verticalMargin = width = height = 0;
            onTop = onBottom = onLeft = onRight = false;
        }

        public static ViewBuilder newBuilder() {
            return new ViewBuilder();
        }

        /**
         * Set horizontal margin in dp for (gravity = left || right)
         *
         * @param horizontalDpMargin
         * @return
         */
        public ViewBuilder horizontalMargin(int horizontalDpMargin) {
            this.horizontalMargin = horizontalDpMargin;
            return this;
        }

        /**
         * Set vertical margin in dp for (gravity = top || bottom)
         *
         * @param verticalDpMargin
         * @return
         */
        public ViewBuilder verticalMargin(int verticalDpMargin) {
            this.verticalMargin = verticalDpMargin;
            return this;
        }

        /**
         * width in dp
         *
         * @param width
         * @return
         */
        public ViewBuilder width(int width) {
            this.width = width;
            return this;
        }

        /**
         * height in dp
         *
         * @param height
         * @return
         */
        public ViewBuilder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * onTop = true will set view's gravity to TOP
         *
         * @param onTop
         * @return
         */
        public ViewBuilder onTop(boolean onTop) {
            this.onTop = onTop;
            return this;
        }

        /**
         * onBottom = true will set view's gravity to BOTTOM
         *
         * @param onBottom
         * @return
         */
        public ViewBuilder onBottom(boolean onBottom) {
            this.onBottom = onBottom;
            return this;
        }

        /**
         * onLeft = true will set view's gravity to LEFT
         *
         * @param onLeft
         * @return
         */
        public ViewBuilder onLeft(boolean onLeft) {
            this.onLeft = onLeft;
            return this;
        }

        /**
         * onRight = true will set view's gravity to RIGHT
         *
         * @param onRight
         * @return
         */
        public ViewBuilder onRight(boolean onRight) {
            this.onRight = onRight;
            return this;
        }

        /**
         * Provide drawable id to the view
         *
         * @param drawable
         * @return
         */
        public ViewBuilder drawable(int drawable) {
            this.drawable = drawable;
            return this;
        }

        public ViewBuilder build() {
            return this;
        }
    }

    /**
     * Custom your URL, provide {@link #accessToken} default only
     * To change support page url: provide homeUrl
     * To add extra params on post url: provide params as {@link HashMap<String, String>}
     */
    public static class UrlBuilder {

        public String accessToken;
        public String homeUrl;
        public HashMap<String, String> extras;

        private UrlBuilder() {
            this.accessToken = "";
            this.homeUrl = "";
            this.extras = new HashMap<>();
        }

        public static UrlBuilder newBuilder() {
            return new UrlBuilder();
        }

        public UrlBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public UrlBuilder homeUrl(String homeUrl) {
            this.homeUrl = homeUrl;
            return this;
        }

        /**
         * extra params, i.e:
         * "&id=1"
         * key = id
         * value = 1
         *
         * @param extras
         * @return
         */
        public UrlBuilder extras(HashMap<String, String> extras) {
            if (extras != null && extras.size() > 0) {
                this.extras.clear();
                this.extras.putAll(extras);
            }
            return this;
        }

        public UrlBuilder build() {
            return this;
        }
    }

    /**
     * Internal update object
     *
     * @param builder
     */
    private static void update(UrlBuilder builder) {
        FVPageInfo.getPageInfo().create(builder.accessToken, builder.homeUrl, builder.extras);
    }

    /**
     * Internal update object
     *
     * @param builder
     */
    private static void update(ViewBuilder builder) {
        FVInfo.getInfo().create(builder.drawable, builder.horizontalMargin, builder.verticalMargin, builder.width, builder.height, builder.onTop, builder.onBottom, builder.onLeft, builder.onRight);
    }

    /**
     * Safety init
     */
    protected FloatingViewManager() {

    }
}
