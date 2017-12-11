package com.incon.service.custom.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppConstants;
import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.databinding.ItemViewCheckboxSpinnerBinding;
import com.incon.service.dto.dialog.CheckedModelSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13 Jun 2017 4:05 PM.
 */
public class AppCheckBoxListAdapter extends RecyclerView.Adapter
        <AppCheckBoxListAdapter.ViewHolder> {
    private List<CheckedModelSpinner> spinnerArrayList = new ArrayList<>();
    private boolean isRadio = false;

    public AppCheckBoxListAdapter(List<CheckedModelSpinner> spinnerArrayList) {
        this.spinnerArrayList = spinnerArrayList;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemViewCheckboxSpinnerBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_view_checkbox_spinner, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CheckedModelSpinner purchasedResponse = spinnerArrayList.get(position);
        holder.bind(purchasedResponse);
    }

    private void clearSelection() {
        for (CheckedModelSpinner checkedModelSpinner : spinnerArrayList) {
            checkedModelSpinner.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return spinnerArrayList.size();
    }

    public String getSelectedItems() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < spinnerArrayList.size(); i++) {
            CheckedModelSpinner checkedModelSpinner = spinnerArrayList.get(i);
            if (checkedModelSpinner.isChecked()) {
                stringBuilder.append(checkedModelSpinner.getName());
                stringBuilder.append(AppConstants.COMMA_SEPARATOR);
            }
        }
        if (TextUtils.isEmpty(stringBuilder.toString())) {
            return "";
        }
        int start = stringBuilder.length() - 1;
        return stringBuilder.toString().substring(0, start);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemViewCheckboxSpinnerBinding binding;

        public ViewHolder(ItemViewCheckboxSpinnerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }


        public void bind(CheckedModelSpinner checkedModelSpinner) {
            binding.setVariable(BR.model, checkedModelSpinner);

            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            boolean checked = binding.checkboxSpinnerFormat.isChecked();
            if (isRadio) {
                clearSelection();
            }
            binding.checkboxSpinnerFormat.setChecked(!checked);
        }

    }

}
