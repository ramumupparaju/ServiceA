package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.incon.service.R;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.callbacks.MoveToOptionCallback;
import com.incon.service.databinding.DialogMovetoBinding;
import com.incon.service.dto.updatestatus.UpDateStatus;

import java.util.List;

/**
 * Created by MY HOME on 12-Jan-18.
 */

public class MoveToOptionDialog  extends Dialog implements View.OnClickListener {
    private final Context context;
    private UpDateStatus upDateStatus;
    private DialogMovetoBinding binding;
    private final MoveToOptionCallback moveToOptionCallback;
    private int statusSelectedPosition = 0;
    private final List<Status> statusList;

    public MoveToOptionDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.statusList = builder.statusList;
        this.moveToOptionCallback = builder.callback;
        upDateStatus = new UpDateStatus();
    }
    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_moveto, null, false);
        View contentView = binding.getRoot();
        binding.includeRegisterBottomButtons.buttonLeft.setText(context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(context.getString(R.string.action_submit));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);
        loadMoveToSpinner();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    private void loadMoveToSpinner() {
        String[] tabArrayList = new String[statusList.size()];
        for (int i = 0; i < statusList.size(); i++) {
            tabArrayList[i] = String.valueOf(statusList.get(i).getId());
        }
        Context context = binding.getRoot().getContext();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                R.layout.view_spinner, tabArrayList);

        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerOptions.setAdapter(arrayAdapter);
        binding.spinnerOptions.setAdapter(arrayAdapter);
        binding.spinnerOptions.setText(tabArrayList[0]); //setting user name with index o

        binding.spinnerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (statusSelectedPosition != position) {
                    statusSelectedPosition = position;
                }

                //For avoiding double tapping issue
                if (binding.spinnerOptions.getOnItemClickListener() != null) {
                    binding.spinnerOptions.onItemClick(parent, view, position, id);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (moveToOptionCallback == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.button_left:
                moveToOptionCallback.alertDialogCallback(MoveToOptionCallback.CANCEL);
                break;
            case R.id.button_right:

                upDateStatus.setStatus(new Status(statusList.get(statusSelectedPosition).getId()));
                moveToOptionCallback.doUpDateStatusApi(upDateStatus);
                moveToOptionCallback.alertDialogCallback(MoveToOptionCallback.OK);

                break;
            default:
                break;
        }

    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final MoveToOptionCallback callback;
        private List<Status> statusList;
        public AlertDialogBuilder(Context context, MoveToOptionCallback callback) {
            this.context = context;
            this.callback = callback;
        }
        public AlertDialogBuilder loadStatusList(List<Status> statusList) {
            this.statusList = statusList;
            return this;
        }

        public MoveToOptionDialog build() {
            MoveToOptionDialog dialog = new MoveToOptionDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }

}
