package com.example.karyc.vkontaktikum.ui.friends;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.Friend;

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

        View itemView = inflater.inflate(R.layout.friend_item, parent, false);

        return new FriendsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendsHolder friendsHolder = (FriendsHolder) holder;
        friendsHolder.bind(friends.get(position));
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
        private final TextView nameView;
        private final TextView statusView;
        private final ImageView imageProfileView;
        Button buttonDelete;

        FriendsHolder(View view) {
            super(view);

            nameView = view.findViewById(R.id.nameView);
            statusView = view.findViewById(R.id.statusView);
            imageProfileView = view.findViewById(R.id.imageProfileView);
            buttonDelete = view.findViewById(R.id.buttonDelete);
        }

        void bind(final Friend friend) {
            nameView.setText(friend.getFirstName() + " " + friend.getLastName());
            if (friend.getOnline() == 1) {
                statusView.setBackgroundResource(R.drawable.status_background_online);
            } else statusView.setBackgroundResource(R.drawable.status_background_offline);

            Glide.with(imageProfileView)
                    .load(friend.getPhotoProfile())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageProfileView);

            imageProfileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onUserGetInfo(friend.getId());
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onButtonDeleteFriend(friend.getId());
                    }
                }
            });
        }
    }
}
