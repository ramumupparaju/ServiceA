package com.incon.service.ui.settings.userdesignation.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.apimodel.components.userslistofservicecenters.UsersListOfServiceCenters;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.ItemUsersListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by INCON TECHNOLOGIES on 12/25/2017.
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private List<UsersListOfServiceCenters> usersList = new ArrayList<>();
    private IClickCallback clickCallback;

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public UsersListAdapter(List<UsersListOfServiceCenters> usersList) {
        this.usersList = usersList;
    }

    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUsersListBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_users_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UsersListAdapter.ViewHolder holder, int position) {
        UsersListOfServiceCenters usersListOfServiceCenter = usersList.get(position);
        holder.bind(usersListOfServiceCenter, position);

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setData(List<UsersListOfServiceCenters> usersList) {
        this.usersList = usersList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemUsersListBinding binding;

        public ViewHolder(ItemUsersListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(UsersListOfServiceCenters usersListOfServiceCenters, int position) {
            binding.setVariable(BR.modelResponse, usersListOfServiceCenters);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }
}
