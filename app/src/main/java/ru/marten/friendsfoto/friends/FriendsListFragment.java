package ru.marten.friendsfoto.friends;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ru.marten.friendsfoto.gui.R;
import ru.marten.friendsfoto.vk.VkDataManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment implements VkDataManager.friendsListListener{

    private ListView friendsListView;
    private FriendsListAdapter friendsListAdapter;
    private VkDataManager vkDataManager;



    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vkDataManager = VkDataManager.getInstance(getActivity());
        vkDataManager.setFriendsListener(this);

        return inflater.inflate(R.layout.fragment_friends_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("marten", "marten array size = " + vkDataManager.getFriendsArraySize());
        friendsListView = (ListView) view.findViewById(R.id.friends_list);
        vkDataManager.updateFriendsList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        friendsListAdapter = new FriendsListAdapter(vkDataManager.getFriendsArray());
        friendsListView.setAdapter(friendsListAdapter);
    }

    @Override
    public void onArrayChanged(ArrayList<Friend> friendArrayList) {
        final ArrayList<Friend> array =friendArrayList;

       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               friendsListAdapter = new FriendsListAdapter(array);
               friendsListView.setAdapter(friendsListAdapter);
           }
       });

    }


}
