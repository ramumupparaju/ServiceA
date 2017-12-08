package com.incon.service.ui.notifications.fragment.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.databinding.library.baseAdapters.BR;
import com.incon.service.R;
import com.incon.service.apimodel.components.notifications.NotificationsResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.ItemNotificationsFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 10/2/2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter
        <NotificationsAdapter.ViewHolder>  {
    private List<NotificationsResponse> notificationsList = new ArrayList<>();
    private IClickCallback clickCallback;

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotificationsFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_notifications_fragment, parent, false);
        return new  ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, int position) {
        NotificationsResponse notificationsResponse = notificationsList.get(position);
        holder.bind(notificationsResponse);
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }


    public void setData(List<NotificationsResponse> taskResponseList) {
        notificationsList = taskResponseList;
        notifyDataSetChanged();
    }

    public void clearData() {
        notificationsList.clear();
        notifyDataSetChanged();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemNotificationsFragmentBinding binding;

        public ViewHolder(ItemNotificationsFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }


        public void bind(NotificationsResponse topCourse) {
            binding.setVariable(BR.notificationsResponse, topCourse);
            /*binding.textTaskTime.setText(DateUtils.formatTimeDay(System.currentTimeMillis()
                    - topCourse.getId() * 1000));
            */binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
