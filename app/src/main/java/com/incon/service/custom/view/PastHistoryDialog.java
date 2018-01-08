package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.PassHistoryCallback;
import com.incon.service.databinding.DialogPastHistoryBinding;
import com.incon.service.ui.status.adapter.PastHistoryAdapter;

import java.util.List;

/**
 * Created by MY HOME on 28-Dec-17.
 */

public class PastHistoryDialog extends Dialog implements View.OnClickListener {
    private PassHistoryCallback passHistoryCallback;
    private final Context context;
    private DialogPastHistoryBinding binding;
    private PastHistoryAdapter pastHistoryAdapter;
    private List<FetchNewRequestResponse> fetchNewRequestResponses;
    private final String title; // required


    public PastHistoryDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.title = builder.title;
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_past_history, null, false);
        View contentView = binding.getRoot();
        binding.textListTitle.setText(title);
        pastHistoryAdapter = new PastHistoryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.pasthistoryRecyclerview.setAdapter(pastHistoryAdapter);
        binding.pasthistoryRecyclerview.setLayoutManager(linearLayoutManager);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }


    @Override
    public void onClick(View view) {
        if (passHistoryCallback == null) {
            return;
        }
    }

    public static class AlertDialogBuilder {

        private final Context context;
        private final PassHistoryCallback callback;
        private String title;


        public AlertDialogBuilder(Context context, PassHistoryCallback callback) {
            this.context = context;
            this.callback = callback;
        }
        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PastHistoryDialog build() {
            PastHistoryDialog dialog = new PastHistoryDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }

    }

}
