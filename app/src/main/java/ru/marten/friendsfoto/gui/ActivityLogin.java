package ru.marten.friendsfoto.gui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.marten.friendsfoto.vk.VkAuthFragment;


public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login_container, new VkAuthFragment()).commit();

    }

    @Override
    public void onBackPressed() {

        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }
}
