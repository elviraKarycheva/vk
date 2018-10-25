package com.example.karyc.vkontaktikum.ui.friends;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karyc.vkontaktikum.core.Friend;
import com.example.karyc.vkontaktikum.databinding.FriendItemBinding;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    interface FriendsAdapterListener {
        void onUserGetInfo(long id);
        void onButtonDeleteFriend(final long id);
    }

    private final ArrayList<Friend> friends = new ArrayList<>();
    public FriendsAdapterListener listener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(parent.getContext());
        FriendItemBinding binding = FriendItemBinding.inflate(inflater);

        return new FriendsHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendsHolder friendsHolder = (FriendsHolder) holder;
        FriendsAdapter.ClickHandler clickHandler = new FriendsAdapter.ClickHandler();
        friendsHolder.binding.setOnItemClick(clickHandler);
        friendsHolder.binding.setItem(friends.get(position));
        friendsHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriends(ArrayList<Friend> friendArrayList) {
        friends.clear();
        friends.addAll(friendArrayList);
        notifyDataSetChanged();
    }

    class FriendsHolder extends RecyclerView.ViewHolder {
        FriendItemBinding binding;

        FriendsHolder(View view, FriendItemBinding binding) {
            super(view);
            this.binding = binding;
        }
    }

    public class ClickHandler {
        public void buttonGetInfo(Friend friend) {
            if (listener != null) {
                listener.onUserGetInfo(friend.getId());
            }
        }

        public void buttonDeleteFriend(Friend friend) {
            if (listener != null) {
                listener.onButtonDeleteFriend(friend.getId());
            }
        }
    }
}
