package com.example.karyc.vkontaktikum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends AppCompatActivity {

    private static final String redirectUri = "https://oauth.vk.com/blank.html";
    public static final String SAVED_ACCESS_TOKEN = "SAVED_ACCESS_TOKEN";
    private static final String scope = "friends,wall,photos,audio,video,stories,pages,status,groups,notifications,offline";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);

        if (accessToken == null) {

            setContentView(R.layout.activity_weblogin);
            WebView webView = findViewById(R.id.webviewLogin);
            webView.loadUrl("https://oauth.vk.com/authorize?client_id=6638621&display=mobile&redirect_uri="
                    + redirectUri + "&scope=" + scope + "&response_type=token&v=5.80");
            webView.setWebViewClient(new MyWebViewClient());
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri url = request.getUrl();
            Log.d("sdcs", url.toString());
            String modyfiedUrl = url.toString().replace("#", "?");
            if (modyfiedUrl.startsWith(redirectUri)) {
                Uri newUri = Uri.parse(modyfiedUrl);
                String accessToken = newUri.getQueryParameter("access_token");
                Log.d("token", accessToken);
                SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SAVED_ACCESS_TOKEN, accessToken);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, FriendsActivity.class);
                startActivity(intent);
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

    }


}
