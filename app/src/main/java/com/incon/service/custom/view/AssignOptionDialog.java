package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.login.ServiceCenterResponse;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.databinding.DialogAssignBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.COMMA_SEPARATOR;

/**
 * Created by MY HOME on 28-Dec-17.
 */

public class AssignOptionDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private final AssignOptionCallback assignOptionCallback;
    private DialogAssignBinding binding;
    private final List<AddUser> usersList;
    private final List<ServiceCenterResponse> serviceCentersList;
    private UpDateStatus upDateStatus;
    private EditText editTextNotes;

    private int usersSelectedPos = 0;

    public AssignOptionDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.usersList = builder.usersList;
        this.assignOptionCallback = builder.callback;
        this.serviceCentersList = builder.serviceCenterResponseList;
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_assign, null, false);
        View contentView = binding.getRoot();
        editTextNotes = binding.edittextComment;
        binding.includeRegisterBottomButtons.buttonLeft.setText(context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(context.getString(R.string.action_submit));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);
        loadUsersSpinner();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    private void loadUsersSpinner() {
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

    }

    public void setUsersData(List<AddUser> usersList) {
        this.usersList.clear();
        usersSelectedPos = 0;
        this.usersList.addAll(usersList);
        loadUsersSpinner();
    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final AssignOptionCallback callback;
        private String title;
        private List<AddUser> usersList;
        private ArrayList<ServiceCenterResponse> serviceCenterResponseList;

        public AlertDialogBuilder(Context context, AssignOptionCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AssignOptionDialog build() {
            AssignOptionDialog dialog = new AssignOptionDialog(this);
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

    @Override
    public void onClick(View view) {
        if (assignOptionCallback == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.button_left:
                assignOptionCallback.alertDialogCallback(AssignOptionCallback.CANCEL);
                break;
            case R.id.button_right:
                //if (validateFields()) {
                assignOptionCallback.doUpDateStatusApi(upDateStatus);
                assignOptionCallback.alertDialogCallback(AssignOptionCallback.OK);
                // }

                break;
            default:
                break;
        }
    }

    private boolean validateFields() {

        int selectedPriority = binding.radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedPriority);
        {
            upDateStatus.setComments(binding.edittextComment.getText().toString());
            upDateStatus.setPriority(Integer.valueOf(radioButton.getTag().toString()));
            return true;

        }
    }

}
