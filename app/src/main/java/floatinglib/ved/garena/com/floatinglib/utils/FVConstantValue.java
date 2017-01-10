package floatinglib.ved.garena.com.floatinglib.utils;

/**
 * Created by linhtruong on 11/18/16 - 17:34.
 * Description: constants
 */

public class FVConstantValue {

    public static final String TOKEN = "access_token";
    public static final String TIMESTAMP = "&timestamp";
    public static final String CHECKSUM = "&checksum";
    public static final String SALT = "&salt";
    public static final String REDIRECT_URL = "&redirect_url";

    public static final int FVVIEW_WIDTH = 100;
    public static final int FVVIEW_HEIGHT = 100;
    public static final int FVVIEW_MARGIN_X = 30;
    public static final int FVVIEW_MARGIN_Y = 30;

    public static final String BASE_URL = "http://test.ved.com.vn/hotro/app-auth";

    public static final String FV_ENABLE = "fvenable";

    public interface FV_STATUS {
        String FV_HIDE = "fvhide";
        String FV_SHOW = "fvshow";
    }

    /**
     * for testing only
     */
    public interface URL_VALUE {
        String TOKEN = "e5aedbeb705aabde3ea4297e986eaa28fc4d195bf584647aa8247f665a312775";
        String TIMESTAMP = "37369856";
        String KEY = "LinhDepTrai";
    }
}
