package com.example.karyc.vkontaktikum.ui.groups;

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
import com.example.karyc.vkontaktikum.core.Groups;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public GroupsActivity groupsActivity;
    private final ArrayList<Groups> groups = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.group_item, parent, false);

        return new GroupsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupsHolder groupsHolder = (GroupsHolder) holder;
        groupsHolder.bind(groups.get(position));

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }


    public void setGroups(ArrayList<Groups> groupsArrayList) {
        groups.clear();
        groups.addAll(groupsArrayList);
        notifyDataSetChanged();
    }

    class GroupsHolder extends RecyclerView.ViewHolder {

        private final TextView nameView;
        private final TextView typeView;
        private final ImageView imageGroupView;
        Button buttonDelete;

        public GroupsHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.groupNameView);
            typeView = itemView.findViewById(R.id.groupTypeView);
            imageGroupView = itemView.findViewById(R.id.groupImageView);
            buttonDelete = itemView.findViewById(R.id.groupButtonDelete);
        }

        void bind(final Groups groups) {
            nameView.setText(groups.getName());
            typeView.setText(groups.getType());

            Glide.with(groupsActivity)
                    .load(groups.getPhoto200())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageGroupView);
//            Picasso.get()
//                    .load(groups.getPhoto200())
//                    .into(imageGroupView);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (groupsActivity != null) {
                        groupsActivity.onButtonDeleteGroup(groups.getId());
                    }
                }
            });
        }
    }
}