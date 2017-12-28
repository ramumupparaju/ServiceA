package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.custom.view.AppAlertDialog;
import com.incon.service.databinding.FragmentPaymentBinding;
import com.incon.service.ui.status.adapter.PaymentAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.List;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class PaymentFragment extends BaseTabFragment implements PaymentContract.View {

    private FragmentPaymentBinding binding;
    private View rootView;
    private PaymentAdapter paymentAdapter;
    private PaymentPresenter paymentPresenter;
    private int userId;
    private List<FetchNewRequestResponse> fetchNewRequestResponses;
    private AppAlertDialog detailsDialog;

    @Override
    protected void initializePresenter() {
        paymentPresenter = new PaymentPresenter();
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

            initViews();
            loadBottomSheet();

            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        paymentAdapter = new PaymentAdapter();
        paymentAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.paymentRecyclerview.setAdapter(paymentAdapter);
        binding.paymentRecyclerview.setLayoutManager(linearLayoutManager);
        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
        userId = 1;
        paymentPresenter.fetchPaymentServiceRequests(userId);

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
            paymentAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = paymentAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            paymentAdapter.notifyDataSetChanged();
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
            if (tag == 0) { // customer
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_call_customer_care);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_call;

            } else if (tag == 1) {  // product
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
            } else {  // status update
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_paid);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_accept_request;
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

            FetchNewRequestResponse itemFromPosition = paymentAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, unparsedTag);

            String[] bottomOptions = new String[0];
            int[] topDrawables = new int[0];

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);


            if (firstRowTag == 0) { // customer


                if (secondRowTag == 0) { //call customer care
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
                if (secondRowTag == 0) {  // paid
                    bottomOptions = new String[3];
                    bottomOptions[0] = getString(R.string.bottom_option_cash);
                    bottomOptions[1] = getString(R.string.bottom_option_online);
                    bottomOptions[2] = getString(R.string.bottom_option_card);
                    topDrawables = new int[3];
                    topDrawables[0] = R.drawable.ic_options_features;
                    topDrawables[1] = R.drawable.ic_option_pasthistory;
                    topDrawables[2] = R.drawable.ic_option_pasthistory;
                }

            }
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(bottomOptions.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, bottomOptions, topDrawables, bottomSheetThirdRowClickListener, unparsedTag);
        }
    };

    private View.OnClickListener bottomSheetThirdRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);


            FetchNewRequestResponse itemFromPosition = paymentAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, unparsedTag);

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);
            int thirdRowTag = Integer.parseInt(tagArray[2]);


            if (firstRowTag == 3) { // status update

                if (secondRowTag == 0) { // paid

                    if (thirdRowTag == 0) { // cash
                        AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    } else if (thirdRowTag == 1) {
                        AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    } else if (thirdRowTag == 2) {
                        AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    }

                }

            }


        }

    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    paymentAdapter.clearData();
                    paymentPresenter.fetchPaymentServiceRequests(userId);

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
    public void fetchPaymentServiceRequests(Object o) {
        // TODO have to set data

    }

  /*  @Override
    public void fetchNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {

        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }
        if (fetchNewRequestResponsesList.size() == 0) {
            binding.requestTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            paymentAdapter.setData(fetchNewRequestResponsesList);
            dismissSwipeRefresh();
        }
    }*/
}
