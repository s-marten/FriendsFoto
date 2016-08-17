package ru.marten.friendsfoto.gui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.marten.friendsfoto.friends.FriendsListFragment;
import ru.marten.friendsfoto.vk.VkDataManager;


public class ActivityMain extends AppCompatActivity {

    private static final int LOGIN_REQ = 111;
    VkDataManager vkDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivityForResult(new Intent(this, ActivityLogin.class), LOGIN_REQ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQ) {
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            } else {
                vkDataManager = VkDataManager.getInstance(ActivityMain.this);
                getSupportFragmentManager().beginTransaction().replace(R.id.friends_list_container, new FriendsListFragment()).commit();
            }
        }
    }


}
