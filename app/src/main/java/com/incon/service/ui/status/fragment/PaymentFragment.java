package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppAlertDialog;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.databinding.FragmentPaymentBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.PaymentAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;
import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class PaymentFragment extends BaseTabFragment implements ServiceCenterContract.View {

    private FragmentPaymentBinding binding;
    private View rootView;
    private ServiceCenterPresenter paymentPresenter;
    private PaymentAdapter paymentAdapter;
    private int serviceCenterId = DEFAULT_VALUE;
    private int userId = DEFAULT_VALUE;
    private AppEditTextDialog terminateDialog;

    private AssignDialog assignDialog;
    private AppAlertDialog detailsDialog;
    private ShimmerFrameLayout shimmerFrameLayout;



    @Override
    protected void initializePresenter() {
        paymentPresenter = new ServiceCenterPresenter();
        paymentPresenter.setView(this);
        setBasePresenter(paymentPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment,
                    container, false);
            rootView = binding.getRoot();
            shimmerFrameLayout = rootView.findViewById(R.id
                    .effect_shimmer);

            initViews();
            loadBottomSheet();

        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        serviceRequest = new ServiceRequest();
        serviceRequest.setStatus(AppUtils.ServiceRequestTypes.PAYMENT.name());

        paymentAdapter = new PaymentAdapter();
        paymentAdapter.setClickCallback(iClickCallback);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.paymentRecyclerview.setAdapter(paymentAdapter);
        binding.paymentRecyclerview.setLayoutManager(linearLayoutManager);
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

        if (serviceCenterId == -1 || serviceCenterId == DEFAULT_VALUE) {
            serviceRequest.setServiceIds(null);
        } else {
            serviceRequest.setServiceIds(String.valueOf(serviceCenterId));
        }

        if (userId == -1 || userId == DEFAULT_VALUE) {
            serviceRequest.setAssignedUser(null);
        } else {
            serviceRequest.setAssignedUser(userId);
        }
        getServiceRequestApi();
       // paymentPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
    }

    private void getServiceRequestApi() {
        binding.paymentRecyclerview.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        paymentPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
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
                paymentAdapter.clearSelection();

            }
        });

    }


    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            paymentAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = paymentAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            paymentAdapter.notifyDataSetChanged();
            productSelectedPosition = position;
            createBottomSheetFirstRow();
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow() {

        int length;
        int[] drawablesArray;
        String[] textArray;
        int[] tagsArray;
        length = 4;
        textArray = new String[length];
        textArray[0] = getString(R.string.bottom_option_customer);
        textArray[1] = getString(R.string.bottom_option_product);
        textArray[2] = getString(R.string.bottom_option_service_center);
        textArray[3] = getString(R.string.bottom_option_status_update);


        tagsArray = new int[length];
        tagsArray[0] = R.id.CUSTOMER;
        tagsArray[1] = R.id.PRODUCT;
        tagsArray[2] = R.id.SERVICE_CENTER;
        tagsArray[3] = R.id.STATUS_UPDATE;

        drawablesArray = new int[length];
        drawablesArray[0] = R.drawable.ic_option_customer;
        drawablesArray[1] = R.drawable.ic_option_product;
        drawablesArray[2] = R.drawable.ic_option_find_service_center;
        drawablesArray[3] = R.drawable.ic_option_delete;

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRowLine.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.firstRow.removeAllViews();
        bottomSheetPurchasedBinding.firstRow.setWeightSum(length);
        setBottomViewOptions(bottomSheetPurchasedBinding.firstRow, textArray, drawablesArray, tagsArray, bottomSheetFirstRowClickListener);

    }

    // bottom sheet click event
    private View.OnClickListener bottomSheetFirstRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            String[] textArray = new String[0];
            int[] drawablesArray = new int[0];
            int[] tagsArray = new int[0];
            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);

            if (tag == R.id.CUSTOMER) {
                int length = 1;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_call_customer_care);

                tagsArray = new int[length];
                tagsArray[0] = R.id.CUSTOMER_CALL_CUSTOMER_CARE;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_call;
            } else if (tag == R.id.PRODUCT) {
                int length = 2;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_warranty_details);
                textArray[1] = getString(R.string.bottom_option_past_history);

                tagsArray = new int[length];
                tagsArray[0] = R.id.PRODUCT_WARRANTY_DETAILS;
                tagsArray[1] = R.id.PRODUCT_PAST_HISTORY;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_warranty;
                drawablesArray[1] = R.drawable.ic_option_pasthistory;
            } else if (tag == R.id.SERVICE_CENTER) {
                int length = 1;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_Call);

                tagsArray = new int[length];
                tagsArray[0] = R.id.SERVICE_CENTER_CALL;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_call;
            } else if (tag == R.id.STATUS_UPDATE) {
                int length = 2;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_paid);
                textArray[1] = getString(R.string.bottom_option_terminate);

                tagsArray = new int[length];
                tagsArray[0] = R.id.STATUS_UPDATE_PAID;
                tagsArray[1] = R.id.STATUS_UPDATE_TERMINATE;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_accept_request;
                drawablesArray[1] = R.drawable.ic_option_accept_request;


            }

            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(textArray.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, textArray, drawablesArray, tagsArray, bottomSheetSecondRowClickListener);
        }
    };


    // bottom sheet top view click event
    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();

            FetchNewRequestResponse itemFromPosition = paymentAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, tag);

            String[] textArray = new String[0];
            int[] drawablesArray = new int[0];
            int[] tagsArray = new int[0];


            if (tag == R.id.CUSTOMER_CALL_CUSTOMER_CARE) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;
            } else if (tag == R.id.PRODUCT_WARRANTY_DETAILS) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

                // TODO have to get details from back end

                   /* String purchasedDate = DateUtils.convertMillisToStringFormat(
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
                return;*/

            } else if (tag == R.id.PRODUCT_PAST_HISTORY) {

            } else if (tag == R.id.SERVICE_CENTER_CALL) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;
            } else if (tag == R.id.STATUS_UPDATE_PAID) {
                int length = 3;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_cash);
                textArray[1] = getString(R.string.bottom_option_online);
                textArray[2] = getString(R.string.bottom_option_card);

                tagsArray = new int[length];
                tagsArray[0] = R.id.STATUS_UPDATE_PAID_CASH;
                tagsArray[1] = R.id.STATUS_UPDATE_PAID_ONLINE;
                tagsArray[2] = R.id.STATUS_UPDATE_PAID_CARD;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_options_features;
                drawablesArray[1] = R.drawable.ic_option_pasthistory;
                drawablesArray[2] = R.drawable.ic_option_pasthistory;

            } else if (tag == R.id.STATUS_UPDATE_TERMINATE) {
                showTerminateDialog();

            } else if (tag == R.id.STATUS_UPDATE_ASSIGN) {
                // showAssignDialog();
               // fetchAssignDialogData();

            }

            // TODO have to remove commented code
           /* if (firstRowTag == 0) { // customer


                if (secondRowTag == 0) { //call customer care
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                    return;
                }

            } else if (firstRowTag == 1) { // product

                if (secondRowTag == 0) { // warrenty details
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    // TODO have to get details from back end
                    *//*String purchasedDate = DateUtils.convertMillisToStringFormat(
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
*//*
                } else if (secondRowTag == 1) { // history
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                }
            } else if (firstRowTag == 2) { // service center
                if (secondRowTag == 0) { // call
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                }
            } else if (firstRowTag == 3) { // status upadates
                if (secondRowTag == 0) {  // paid
                    bottomOptions = new String[3];
                    bottomOptions[0] = getString(R.string.bottom_option_cash);
                    bottomOptions[1] = getString(R.string.bottom_option_online);
                    bottomOptions[2] = getString(R.string.bottom_option_card);
                    topDrawables = new int[3];
                    topDrawables[0] = R.drawable.ic_options_features;
                    topDrawables[1] = R.drawable.ic_option_pasthistory;
                    topDrawables[2] = R.drawable.ic_option_pasthistory;


                } else if (secondRowTag == 1) { // terminate
                    showTerminateDialog();
                }
                // todo have to know

              *//*  else if (secondRowTag == 4) { // assign
                   // showAssignDialog();
                    fetchAssignDialogData();

                }*//*


            }*/


            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(textArray.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, textArray, drawablesArray, tagsArray, bottomSheetThirdRowClickListener);
        }
    };

    /*private void fetchAssignDialogData() {
        paymentPresenter.getUsersListOfServiceCenters(serviceCenterId);


    }*/

    private void showAssignDialog(List<AddUser> userList) {
        assignDialog = new AssignDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {
            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = paymentAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                paymentPresenter.upDateStatus(userId, upDateStatus);
            }


            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
                        break;
                    case AlertDialogCallback.CANCEL:
                        assignDialog.dismiss();
                        break;
                    default:
                        break;
                }

            }
        }).title(getString(R.string.option_assign)).loadUsersList(userList).statusId(ASSIGNED).build();
        assignDialog.showDialog();
        assignDialog.setCancelable(true);
    }


    private void showTerminateDialog() {
        terminateDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
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
                                terminateDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_terminate))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        terminateDialog.showDialog();
        terminateDialog.setCancelable(true);


    }

    private View.OnClickListener bottomSheetThirdRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();


            FetchNewRequestResponse itemFromPosition = paymentAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, tag);

            String[] textArray = new String[0];
            int[] drawablesArray = new int[0];
            int[] tagsArray = new int[0];
            if (tag == R.id.STATUS_UPDATE_PAID_CASH) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            } else if (tag == R.id.STATUS_UPDATE_PAID_ONLINE) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            } else if (tag == R.id.STATUS_UPDATE_PAID_CARD) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            }

            // TODO have to remove commented code

           /* if (firstRowTag == 3) { // status update

                if (secondRowTag == 0) { // paid

                    if (thirdRowTag == 0) { // cash
                        AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    } else if (thirdRowTag == 1) {
                        AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    } else if (thirdRowTag == 2) {
                        AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    }

                }

            }*/


        }

    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    paymentAdapter.clearData();
                    doRefresh(true);

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        paymentPresenter.disposeAll();
    }


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO search click listener
    }


    @Override
    public void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {

        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }

        if (fetchNewRequestResponsesList.size() == 0) {
            binding.paymentTextview.setVisibility(View.VISIBLE);
            binding.paymentRecyclerview.setVisibility(View.GONE);
        } else {
            binding.paymentTextview.setVisibility(View.GONE);
            binding.paymentRecyclerview.setVisibility(View.VISIBLE);
            paymentAdapter.setData(fetchNewRequestResponsesList);

            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {


    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {

    }

}
