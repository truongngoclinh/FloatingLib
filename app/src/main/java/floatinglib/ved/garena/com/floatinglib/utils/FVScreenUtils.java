package floatinglib.ved.garena.com.floatinglib.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by linhtruong on 11/18/16 - 22:46.
 * Description: screen methods support
 */

public class FVScreenUtils {

    public static int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density + 0.5f);
    }
}
