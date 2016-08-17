package ru.marten.friendsfoto.vk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ru.marten.friendsfoto.friends.Friend;
import ru.marten.friendsfoto.utils.ConnectionUnit;

/**
 * Created by marten on 16.08.16.
 */
public class VkDataManager {

    public static final String VK_PREFS = "vk_prefs";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String AND = "&";

    private Context context;
    private static VkDataManager oAdapter;
    private String token;
    private static ArrayList<Friend> friendsArray = new ArrayList<>();
    private static int friendsCount = -1;
    private friendsListListener friendsListener;

    private VkDataManager(Context context) {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(VK_PREFS, context.MODE_PRIVATE);
        try {
            JSONObject data = new JSONObject(preferences.getString(VkAuthSchema.PREFS_AUTH_DATA, " "));
            token = data.getString(ACCESS_TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static VkDataManager getInstance(Context context) {
        if (oAdapter == null) {
            oAdapter = new VkDataManager(context);
        }
        return oAdapter;
    }

    public String getToken() {
        return token;
    }


    public ArrayList<Friend> getFriendsArray() {

        return friendsArray;
    }

    public int getFriendsArraySize() {

        return friendsArray.size();
    }


    public synchronized void updateFriendsList() {

//        while (friendsCount == -1 || friendsArray.size() < friendsCount) {
            Log.d("marten", "marten friends count = " + friendsCount);
            String parametrs;
            if (friendsCount == -1) {
                parametrs = "count=30&fields=nickname,status,photo_200_orig";
            } else {
                parametrs = "count=30" + "&offset=" + friendsArray.size() + "&fields=nickname,status,photo_200_orig";
            }


            final String request = VkDataSchema.DATA_SERVER +
                    VkApiMethods.FRIENDS_GET.getMethod() + "?" +
                    parametrs + AND +
                    ACCESS_TOKEN + "=" + token + AND +
                    VkAuthSchema.VERSION + "=" + "5.53";

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject rawData = ConnectionUnit.getCommonPacket(request).getJSONObject("response");
                        friendsCount = rawData.getInt("count");
                        Log.d("marten", "marten friends count = " + friendsCount);

                        JSONArray jsonArray = rawData.getJSONArray("items");
                        convertToArray(jsonArray);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
//        }
    }

    private synchronized void convertToArray(final JSONArray array) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Friend friend = new Friend(object.getString("first_name") + " " + object.getString("last_name"),
                            object.optInt("online"),
                            object.optString("status"),
                            object.optString("photo_200_orig"));
                    friendsArray.add(friend);
                }

                friendsListener.onArrayChanged(friendsArray);
            }   catch (JSONException e) {
                e.printStackTrace();
            }
                if (friendsArray.size() < friendsCount) {
                    updateFriendsList();
                }
        }

        }).start();


    }

    public interface friendsListListener {
        void onArrayChanged(ArrayList<Friend> friendArrayList);
    }

    public friendsListListener setFriendsListener(friendsListListener friendsListener) {
        this.friendsListener = friendsListener;
        return this.friendsListener;
    }

}
