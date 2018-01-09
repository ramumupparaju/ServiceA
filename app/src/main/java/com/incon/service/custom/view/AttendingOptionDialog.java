package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.incon.service.R;
import com.incon.service.apimodel.components.login.ServiceCenterResponse;
import com.incon.service.callbacks.AttendingCallback;
import com.incon.service.databinding.DialogAttendingBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 1/9/2018.
 */

public class AttendingOptionDialog extends Dialog implements View.OnClickListener {
    private final List<AddUser> usersList;
    private final Context context;
    private AttendingCallback attendingCallback;
    private final List<ServiceCenterResponse> serviceCentersList;
    private UpDateStatus upDateStatus;
    private EditText editTextNotes;
    private int usersSelectedPos = 0;
    private DialogAttendingBinding binding;

    public AttendingOptionDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.usersList = builder.usersList;
        this.attendingCallback = builder.callback;
        this.serviceCentersList = builder.serviceCenterResponseList;
    }


    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_attending, null, false);
        View contentView = binding.getRoot();
        editTextNotes = binding.edittextComment;
        binding.includeRegisterBottomButtons.buttonLeft.setText(context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(context.getString(R.string.action_submit));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);
        //loadUsersSpinner();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

/*    private void loadUsersSpinner() {
        String[] stringUsersList = new String[usersList.size()];
        for (int i = 0; i < usersList.size(); i++) {
            stringUsersList[i] = usersList.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                R.layout.view_spinner, stringUsersList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerUsers.setAdapter(arrayAdapter);
        binding.spinnerUsers.setText(stringUsersList[0]); //setting user name with index o

        binding.spinnerUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (usersSelectedPos != position) {
                    usersSelectedPos = position;

                }
            }
        });

    }*/

    @Override
    public void onClick(View view) {
        if (attendingCallback == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.button_left:
                attendingCallback.alertDialogCallback(AttendingCallback.CANCEL);
                break;
            case R.id.button_right:
                //if (validateFields()) {
                attendingCallback.doUpDateStatusApi(upDateStatus);
                attendingCallback.alertDialogCallback(AttendingCallback.OK);
                // }

                break;
            default:
                break;

        }
    }



    public static class AlertDialogBuilder {
        private final Context context;
        private final AttendingCallback callback;
        private String title;
        private List<AddUser> usersList;
        private ArrayList<ServiceCenterResponse> serviceCenterResponseList;

        public AlertDialogBuilder(Context context, AttendingCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AttendingOptionDialog build() {
            AttendingOptionDialog dialog = new AttendingOptionDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }

        public AlertDialogBuilder loadServiceCentersData(ArrayList<ServiceCenterResponse> serviceCenterResponseList) {
            this.serviceCenterResponseList = serviceCenterResponseList;
            return this;
        }

        public AlertDialogBuilder loadUsersList(List<AddUser> usersList) {
            this.usersList = usersList;
            return this;

        }

    }
}
