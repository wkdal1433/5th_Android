package com.example.kakao_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private View loginButton, logoutButton;
    private TextView nickName;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login);
        logoutButton = findViewById(R.id.logout);
        nickName = findViewById(R.id.nickname);
        profileImage = findViewById(R.id.profile);

        //로그인 버튼
        Function2<OAuthToken,Throwable,Unit> callback = new Function2<OAuthToken,Throwable,Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken !=null){
                    //TBD
                }
                if(throwable !=null){
                    //TBD
                }
                updateKakaoLoginUi();
                return null;
            }
        };

        //로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,callback);
                }
            }
        });
        //로그아웃 버튼
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });


        updateKakaoLoginUi();
    }

    private void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null){

                    Log.i(TAG,"invoke: id = "+user.getId());
                    Log.i(TAG,"invoke: email = "+user.getKakaoAccount().getEmail());
                    Log.i(TAG,"invoke: nickname = "+user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG,"invoke: gender = "+user.getKakaoAccount().getGender());
                    Log.i(TAG,"invoke: age = "+user.getKakaoAccount().getAgeRange());

                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    //user.getKakaoAccount().getProfile().getThumbnailImageUrl()
                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileImage);


                    loginButton.setVisibility(View.GONE);
                    logoutButton.setVisibility(View.VISIBLE);
                }else{
                    nickName.setText(null);
                    profileImage.setImageBitmap(null);
                    logoutButton.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });
    }
}