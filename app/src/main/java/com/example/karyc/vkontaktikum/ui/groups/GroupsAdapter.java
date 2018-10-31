package com.example.karyc.vkontaktikum.ui.groups;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karyc.vkontaktikum.core.Group;
import com.example.karyc.vkontaktikum.databinding.GroupItemBinding;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    interface GroupsAdapterListener {
        void onButtonLeaveGroup(final long id);
    }

    private final ArrayList<Group> groups = new ArrayList<>();
    public GroupsAdapter.GroupsAdapterListener listener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        GroupItemBinding binding = GroupItemBinding.inflate(inflater);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.getRoot().setLayoutParams(lp);

        return new GroupsHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupsHolder groupsHolder = (GroupsHolder) holder;
        GroupsAdapter.GroupsClickHandler clickHandler = new GroupsAdapter.GroupsClickHandler();
        groupsHolder.binding.setOnItemClick(clickHandler);
        groupsHolder.binding.setItem(groups.get(position));
        groupsHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }


    public void setGroups(ArrayList<Group> groupsArrayList) {
        groups.clear();
        groups.addAll(groupsArrayList);
        notifyDataSetChanged();
    }

    class GroupsHolder extends RecyclerView.ViewHolder {
        GroupItemBinding binding;

        GroupsHolder(View view, GroupItemBinding binding) {
            super(view);
            this.binding = binding;
        }
    }

    public class GroupsClickHandler {
//        public void buttonGetInfo(Group group) {
//            if (listener != null) {
//                listener.onGroupGetInfo(group.getId());
//            }
//        }

        public void buttonLeaveGroup(Group group) {
            if (listener != null) {
                listener.onButtonLeaveGroup(group.getId());
            }
        }
    }
}