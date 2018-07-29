package com.example.karyc.vkontaktikum;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Friend> friends = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.friend_item, parent, false);

        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

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

    public void setFriends(ArrayList<Friend> friendArrayList){
        friends.clear();
        friends.addAll(friendArrayList);
        notifyDataSetChanged();
    }

    class FriendsHolder extends RecyclerView.ViewHolder {

        private final TextView nameView;
        private final TextView statusView;
        private final ImageView imageProfileView;

        FriendsHolder(View view) {
            super(view);

            nameView = view.findViewById(R.id.nameView);
            statusView = view.findViewById(R.id.statusView);
            imageProfileView = view.findViewById(R.id.imageProfileView);
        }

        void bind(final Friend friend) {
            nameView.setText(friend.getFirstName() + " " + friend.getLastName());
            if(friend.getOnline() == 1){
                statusView.setBackgroundResource(R.drawable.status_background_online);
            } else statusView.setBackgroundResource(R.drawable.status_background_offline);
            Picasso.get()
                    .load(friend.getPhotoProfile())
                    .into(imageProfileView);

        }


    }
}
