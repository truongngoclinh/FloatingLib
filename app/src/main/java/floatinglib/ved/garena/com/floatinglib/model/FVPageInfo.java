package floatinglib.ved.garena.com.floatinglib.model;

import android.text.TextUtils;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;

import floatinglib.ved.garena.com.floatinglib.utils.FVConstantValue;
import floatinglib.ved.garena.com.floatinglib.utils.FVHmacSha1Signature;

/**
 * Created by linhtruong on 11/22/16 - 09:46.
 * Description: @Singleton post url info
 */

public class FVPageInfo {

    private static final String TAG = "FVPageInfo";

    public String accessToken;
    public String timeStamp;
    public String homeUrl;
    public HashMap<String, String> extras;

    private static FVPageInfo mInstance;

    protected FVPageInfo() {
        accessToken = "";
        timeStamp = "";
        extras = new HashMap<>();
        homeUrl = FVConstantValue.BASE_URL;
    }

    public static synchronized FVPageInfo getPageInfo() {
        if (mInstance == null) {
            mInstance = new FVPageInfo();
        }

        return mInstance;
    }

    public void create(String accessToken, String homeUrl, HashMap<String, String> extras) {
        this.accessToken = accessToken;
        this.timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
        if (!TextUtils.isEmpty(homeUrl) && homeUrl.startsWith("http")) {
            this.homeUrl = homeUrl;
        }
        if (extras != null && extras.size() > 0) {
            this.extras.clear();
            this.extras.putAll(extras);
        }
    }

    public String buildUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(FVConstantValue.TOKEN).append("=").append(accessToken)
                .append(FVConstantValue.TIMESTAMP).append("=").append(timeStamp)
                .append(FVConstantValue.CHECKSUM).append("=").append(buildHmac());

        if (extras.size() > 0) {
            for (HashMap.Entry entry : extras.entrySet()) {
               builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        String url = builder.toString();

        Log.d(TAG, "buildUrl: " + url);
        return url;
    }

    public String buildHmac() {
        String hmac = "";

        try {
            hmac = FVHmacSha1Signature.calculateRFC2104HMAC(accessToken + timeStamp, FVConstantValue.URL_VALUE.KEY);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "buildUrl: hmac = " + hmac + "\n token = " + accessToken + " \n timestamp = " + timeStamp);
        return hmac;
    }

    public void clear() {
        mInstance = null;
    }
}
