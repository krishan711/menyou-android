package com.menyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity
{
    private SocialHelper socialHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("LOGGED_IN", false))
        {
            gotoAccount();
            return;
        }

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        socialHelper = new SocialHelper();
        socialHelper.initSocial(this, savedInstanceState);

        //noinspection ConstantConditions
        getActionBar().hide();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        socialHelper.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        socialHelper.onStop();
    }

    @OnClick(R.id.btn_login_facebook)
    void onFacebookClicked()
    {
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
        gotoAccount();
    }

    @OnClick(R.id.btn_login_google)
    void onGoogleClicked()
    {
//        socialHelper.startGoogleLogin(new SocialHelper.LoginListener() {
//            @Override
//            public void onFailed()
//            {
//                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_LONG).show();
//                gotoAccount();
//            }
//
//            @Override
//            public void onDetailRequestStarted() { }
//
//            @Override
//            public void onSuccess(String id, String formatted, Object o, Object o1)
//            {
//                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
//                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getBoolean("LOGGED_IN", true);
//                gotoAccount();
//            }
//        });
        gotoAccount();
    }

    @OnClick(R.id.btn_create_account)
    void onCreateAccountClicked()
    {
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
        gotoAccount();
    }

    private void gotoAccount()
    {
        startActivity(new Intent(this, AccountActivity.class));
        finish();
    }

}
