package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppAlertDialog;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.StatusDialog;
import com.incon.service.databinding.FragmentRepairBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.RepairAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class RepairFragment extends BaseTabFragment implements RepairContract.View {
    private FragmentRepairBinding binding;
    private View rootView;
    private RepairAdapter repairAdapter;
    private RepairPresenter repairPresenter;
    private List<FetchNewRequestResponse> fetchNewRequestResponses;
    private AppAlertDialog detailsDialog;
    private StatusDialog statusDialog;
    private AppEditTextDialog closeDialog;
    private AppEditTextDialog repairDialog;
    private AppEditTextDialog holdDialog;
    private int serviceCenterId = DEFAULT_VALUE;
    private int userId = DEFAULT_VALUE;
    private List<AddUser> usersList;

    @Override
    protected void initializePresenter() {
        repairPresenter = new RepairPresenter();
        repairPresenter.setView(this);
        setBasePresenter(repairPresenter);
    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repair,
                    container, false);

            initViews();
            loadBottomSheet();

            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        repairAdapter = new RepairAdapter();
        repairAdapter.setClickCallback(iClickCallback);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.requestRecyclerview.setAdapter(repairAdapter);
        binding.requestRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void doRefresh(boolean isForceRefresh) {
        HomeActivity activity = (HomeActivity) getActivity();
        int tempServiceCenterId = activity.getServiceCenterId();
        int tempUserId = activity.getUserId();

        if (serviceCenterId == tempServiceCenterId && tempUserId == userId) {
            //no chnages have made, so no need to make api call checks whether pull to refresh or
            // not

            if (!isForceRefresh)
                return;
        } else {
            serviceCenterId = tempServiceCenterId;
            userId = tempUserId;
        }
        repairPresenter.fetchRepairServiceRequests(serviceCenterId, userId);
    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

    }



    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            repairAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = repairAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            repairAdapter.notifyDataSetChanged();
            productSelectedPosition = position;
            createBottomSheetFirstRow(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow(int position) {
        int length;
        int[] bottomDrawables;
        String[] bottomNames;
        length = 4;
        bottomNames = new String[4];
        bottomNames[0] = getString(R.string.bottom_option_customer);
        bottomNames[1] = getString(R.string.bottom_option_product);
        bottomNames[2] = getString(R.string.bottom_option_service_center);
        bottomNames[3] = getString(R.string.bottom_option_status_update);

        bottomDrawables = new int[4];
        bottomDrawables[0] = R.drawable.ic_option_customer;
        bottomDrawables[1] = R.drawable.ic_option_product;
        bottomDrawables[2] = R.drawable.ic_option_find_service_center;
        bottomDrawables[3] = R.drawable.ic_option_delete;

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.firstRow.removeAllViews();
        bottomSheetPurchasedBinding.firstRow.setWeightSum(length);
        setBottomViewOptions(bottomSheetPurchasedBinding.firstRow, bottomNames, bottomDrawables, bottomSheetFirstRowClickListener, "-1");

    }

    // bottom sheet click event
    private View.OnClickListener bottomSheetFirstRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            Integer tag = Integer.valueOf(unparsedTag);
            String[] bottomOptions;
            int[] topDrawables;
            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, unparsedTag);
            if (tag == 0) {  // customer
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_call_customer_care);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_call;

            } else if (tag == 1) { // product
                bottomOptions = new String[2];
                bottomOptions[0] = getString(R.string.bottom_option_warranty_details);
                bottomOptions[1] = getString(R.string.bottom_option_past_history);
                topDrawables = new int[2];
                topDrawables[0] = R.drawable.ic_options_features;
                topDrawables[1] = R.drawable.ic_option_pasthistory;
            } else if (tag == 2) { // service center
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_Call);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_call;
            } else { // status update
                bottomOptions = new String[6];
                bottomOptions[0] = getString(R.string.bottom_option_repair_done);
                bottomOptions[1] = getString(R.string.bottom_option_hold);
                bottomOptions[2] = getString(R.string.bottom_option_hold);
                bottomOptions[3] = getString(R.string.bottom_option_hold);
                bottomOptions[4] = getString(R.string.bottom_option_assign);
                bottomOptions[5] = getString(R.string.bottom_option_close);
                topDrawables = new int[6];
                topDrawables[0] = R.drawable.ic_option_repair_done;
                topDrawables[1] = R.drawable.ic_option_hold;
                topDrawables[2] = R.drawable.ic_option_assign;
                topDrawables[3] = R.drawable.ic_option_close;
                topDrawables[4] = R.drawable.ic_option_close;
                topDrawables[5] = R.drawable.ic_option_close;
            }

            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(bottomOptions.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, bottomOptions, topDrawables, bottomSheetSecondRowClickListener, unparsedTag);
        }
    };


    // bottom sheet top view click event
    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);

            FetchNewRequestResponse itemFromPosition = repairAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, unparsedTag);

            String[] bottomOptions = new String[0];
            int[] topDrawables = new int[0];

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);

            // customer
            if (firstRowTag == 0) {

                if (secondRowTag == 0) {    //call customer care

                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                    return;
                }

            } else if (firstRowTag == 1) { // product

                if (secondRowTag == 0) { // warrenty details
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    // TODO have to get details from back end
                    /*String purchasedDate = DateUtils.convertMillisToStringFormat(
                            itemFromPosition.getPurchasedDate(), DateFormatterConstants.DD_MM_YYYY);
                    String warrantyEndDate = DateUtils.convertMillisToStringFormat(
                            itemFromPosition.getWarrantyEndDate(), DateFormatterConstants.DD_MM_YYYY);
                    long noOfDays = DateUtils.convertDifferenceDateIndays(
                            itemFromPosition.getWarrantyEndDate(), System.currentTimeMillis());
                    String warrantyConditions = itemFromPosition.getWarrantyConditions();
                    showInformationDialog(getString(
                            R.string.bottom_option_warranty), getString(
                            R.string.purchased_warranty_status_now)
                            + noOfDays + " Days Left "
                            + "\n"
                            + getString(
                            R.string.purchased_purchased_date)
                            + purchasedDate
                            + "\n"
                            + getString(
                            R.string.purchased_warranty_covers_date)
                            + warrantyConditions
                            + "\n"
                            + getString(
                            R.string.purchased_warranty_ends_on) + warrantyEndDate);
                    return;
*/
                } else if (secondRowTag == 1) { // history
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                }
            } else if (firstRowTag == 2) { // service center
                if (secondRowTag == 0) { // call
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                }
            } else if (firstRowTag == 3) { // status upadates
                if (secondRowTag == 0) { // repair done
                    showRepairDone();
                } else if (secondRowTag == 1) { // hold
                    showHoldDialog();
                }
                else if (secondRowTag == 2) { // terminate
                    showTerminateDialog();
                }
                else if (secondRowTag == 3) { // move to
                    showMoveToDialog();
                }

                else if (secondRowTag == 4) { // assign
                    showAssignDialog();
                }
                else { // close
                    showCloseDialog();

                }

            }
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(bottomOptions.length);
        }
    };

    private void showTerminateDialog() {

    }

    private void showMoveToDialog() {

    }

    private void showHoldDialog() {
        holdDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                break;
                            case AlertDialogCallback.CANCEL:
                                holdDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_hold))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        holdDialog.showDialog();
    }

    private void showRepairDone() {
        repairDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                break;
                            case AlertDialogCallback.CANCEL:
                                repairDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_close))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        repairDialog.showDialog();

    }

    private void showCloseDialog() {
        closeDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                break;
                            case AlertDialogCallback.CANCEL:
                                closeDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_close))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        closeDialog.showDialog();
    }

    private void showAssignDialog() {
        statusDialog = new StatusDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {

            }

            @Override
            public void enteredText(String comment) {

            }

            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:

                        break;
                    case AlertDialogCallback.CANCEL:

                        break;
                    default:
                        break;
                }

            }
        }).title(getString(R.string.option_assign))
                .build();
        statusDialog.showDialog();
        statusDialog.setCancelable(true);

    }

    private void showLocationDialog() {

        FetchNewRequestResponse itemFromPosition = repairAdapter.getItemFromPosition(
                productSelectedPosition);

        if (TextUtils.isEmpty(itemFromPosition.getCustomer().getLocation())) {
            AppUtils.shortToast(getActivity(), getString(R.string.error_location));
            return;
        }

        Intent addressIntent = new Intent(getActivity(), RegistrationMapActivity.class);
        addressIntent.putExtra(IntentConstants.LOCATION_COMMA, itemFromPosition.getCustomer().getLocation());
        addressIntent.putExtra(IntentConstants.ADDRESS_COMMA, itemFromPosition.getServiceCenter().getAddress());
        startActivity(addressIntent);


    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    repairAdapter.clearData();

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        repairPresenter.disposeAll();
    }



    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to implement search click listener
    }


    @Override
    public void loadingRepairServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {


        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }

        if (fetchNewRequestResponsesList.size() == 0) {
            binding.repairTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            binding.repairTextview.setVisibility(View.GONE);
            repairAdapter.setData(fetchNewRequestResponsesList);
            dismissSwipeRefresh();
        }
    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {

        if (usersList == null) {
            usersList = new ArrayList<>();
        }

        this.usersList = usersList;
    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {

    }




}
