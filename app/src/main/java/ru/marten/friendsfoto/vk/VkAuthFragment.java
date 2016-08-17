package ru.marten.friendsfoto.vk;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ru.marten.friendsfoto.gui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VkAuthFragment extends Fragment {


    public VkAuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_vk_auth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView login_view = (WebView) view.findViewById(R.id.login_webview);
        login_view.getSettings().setJavaScriptEnabled(true);
        login_view.setWebViewClient(new CustomWebViewClient(getActivity()));
        login_view.loadUrl(VkAuthHelper.getAuthRequest());

    }

    private class CustomWebViewClient extends WebViewClient {

        Activity activity;

        public CustomWebViewClient(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!TextUtils.isEmpty(url)) {
                if (url.contains(VkAuthSchema.REDIRECT_URI_VALUE + "#")) {
                    SharedPreferences preferences = activity.getSharedPreferences(VkDataManager.VK_PREFS, activity.MODE_PRIVATE);
                    Editor editor = preferences.edit();
                    editor.putString(VkAuthSchema.PREFS_AUTH_DATA, VkAuthHelper.parseAuthResponse(url).toString());
                    editor.commit();
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    view.loadUrl(url);
                }
            }
            return true;
        }
    }

}
