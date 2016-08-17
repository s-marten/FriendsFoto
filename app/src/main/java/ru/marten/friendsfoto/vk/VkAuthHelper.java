package ru.marten.friendsfoto.vk;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marten on 16.08.16.
 */
 class VkAuthHelper {

    private static final String AND = "&";

    public static String getAuthRequest() {
        String request = VkAuthSchema.AUTH_SERVER + "?" +
                          VkAuthSchema.CLIENT_ID + "=" + VkAuthSchema.ID + AND +
                          VkAuthSchema.DISPLAY + "=" + "mobile" + AND +
                          VkAuthSchema.REDIRECT_URI + "=" + VkAuthSchema.REDIRECT_URI_VALUE + AND +
                          VkAuthSchema.SCOPE + "=" + "friends" + AND +
                          VkAuthSchema.RESPONCE_TYPE + "=" + "token" + AND +
                          VkAuthSchema.VERSION + "=" + "5.53" + AND +
                          VkAuthSchema.STATE + "=" + VkAuthSchema.STATE_RESULT;

        return request;
    }

    /*
     * Get link: https://oauth.vk.com/blank.html#access_token=09722120fe2419...&expires_in=86400&user_id=7143324&state=state_ok
     * where
     * tokens[0] = https://oauth.vk.com/blank.html
     * tokens[1] = access_token
     * tokens[2] = 09722120fe2419... (value of access token)
     * tokens[3] = expires_in
     * tokens[4] = 86400 (value of expires_in)
     * tokens[5] = user_id
     * tokens[6] = 7143324 (value of user_id)
     * tokens[7] = state
     * tokens[8] = state_ok (value of state)
     */
    public static JSONObject parseAuthResponse(String url) {
        String[] tokens = url.split("[#,&,=]");
        JSONObject authResponse =null;

        try {
            authResponse = new JSONObject();
            authResponse.put(tokens[1], tokens[2]);
            authResponse.put(tokens[3],tokens[4]);
            authResponse.put(tokens[5],tokens[6]);
            authResponse.put(tokens[7],tokens[8]);
            return authResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return authResponse;
        }

    }

}
