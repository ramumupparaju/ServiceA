package com.incon.service.ui.settings.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.incon.service.R;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.dto.settings.SettingsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09 Aug 2017 7:11 PM.
 *
 */
public class SettingsAdapter extends RecyclerView.Adapter {

    private List<SettingsItem> menuItems = new ArrayList<>();
    public static final int ROW_TYPE_HEADER = 0;
    public static final int ROW_TYPE_ITEM = 1;
    private IClickCallback clickCallback;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ROW_TYPE_HEADER) {
            View rowTitleView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_row_profile, parent, false);
            return new ViewHolderProfile(rowTitleView);
        }
        else {
            View rowItemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_row_item, parent, false);
            return new ViewHolderMenuItem(rowItemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SettingsItem menuItem = menuItems.get(position);
        if (holder.getItemViewType() == ROW_TYPE_HEADER) {
            ((ViewHolderProfile) holder).bindType(menuItem);
        }
        else {
            ((ViewHolderMenuItem) holder).bindType(menuItem);
        }
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return menuItems.get(position).getRowType();
    }

    public void setData(List<SettingsItem> menuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(menuItems);
        notifyDataSetChanged();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public class ViewHolderProfile extends RecyclerView.ViewHolder {
        private final TextView textMenuTitle;
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallback.onClickPosition(getAdapterPosition());
            }
        };
        public ViewHolderProfile(View profileView) {
            super(profileView);
            textMenuTitle = (TextView) profileView.findViewById(R.id.text_menu_title);
            profileView.setOnClickListener(onClickListener);
        }
        public void bindType(SettingsItem menuItem) {
            textMenuTitle.setText(menuItem.getText());
        }
    }

    public class ViewHolderMenuItem extends RecyclerView.ViewHolder {
        private final ImageView imageMenuIcon;
        private final TextView textMenuTitle;
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallback.onClickPosition(getAdapterPosition());
            }
        };
        public ViewHolderMenuItem(View itemView) {
            super(itemView);
            imageMenuIcon = (ImageView) itemView.findViewById(R.id.image_menu_icon);
            textMenuTitle = (TextView) itemView.findViewById(R.id.text_menu_title);
            itemView.setOnClickListener(onClickListener);
        }
        public void bindType(SettingsItem menuItem) {
            textMenuTitle.setText(menuItem.getText());
            imageMenuIcon.setImageResource(menuItem.getIcon());
        }
    }
}
