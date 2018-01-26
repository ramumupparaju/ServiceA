package com.incon.service.ui.status;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.incon.service.AppUtils;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.apimodel.components.calendar.CalendarDateModel;
import com.incon.service.custom.view.CustomViewPager;
import com.incon.service.custom.view.LinearLayoutPageManager;
import com.incon.service.databinding.CustomTabBinding;
import com.incon.service.databinding.FragmentStatusTabBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.HorizontalCalendarAdapter;
import com.incon.service.ui.status.adapter.StatusTabPagerAdapter;
import com.incon.service.ui.status.base.base.BaseProductOptionsFragment;
import com.incon.service.utils.Logger;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * Created by PC on 12/5/2017.
 */

public class StatusTabFragment extends BaseFragment implements StatusTabContract.View {
    private static final String TAG = StatusTabFragment.class.getSimpleName();
    private FragmentStatusTabBinding binding;
    private View rootView;
    private StatusTabPresenter newRequestPresenter;
    private StatusTabPagerAdapter adapter;
    private int currentTabPosition;
    private Typeface defaultTypeFace;
    private Typeface selectedTypeFace;
    private String[] tabTitles;

    private List<AddServiceCenter> serviceCenterList;
    private int serviceCentersSelectedPosition = -1;
    private List<AddUser> usersListOfServiceCenters;
    private int usersSelectedPosition = -1;

    private HorizontalCalendar horizontalCalendar;

    ////////for displaying horizontal calendar
    private LinearLayoutPageManager calendarPageManager;
    private float allPixelsDate;
    private ArrayList<CalendarDateModel> datesList;
    private int numberOfDaysToShow = 365;
    private Integer[] monthIndex = null;
    private String[] monthsInAllLanguages = null;
    private Map<String, Integer> months = null;
    private Integer[] dayIndex = null;
    private String[] dayInAllLanguages = null;
    private Map<String, Integer> days = null;
    ////////////////////////////////////////

    @Override
    protected void initializePresenter() {
        newRequestPresenter = new StatusTabPresenter();
        newRequestPresenter.setView(this);
        setBasePresenter(newRequestPresenter);
    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_status));
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_status_tab, container, false);
            rootView = binding.getRoot();
            initViews();
            initCalender();
        }
        setTitle();

        return rootView;
    }

    private void initViews() {
        binding.spinnerServiceCenters.setVisibility(View.GONE);
        binding.spinnerUsers.setVisibility(View.GONE);

        int userType = SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_TYPE, DEFAULT_VALUE);
        if (userType == UserConstants.SUPER_ADMIN_TYPE) {
            binding.spinnerServiceCenters.setVisibility(View.VISIBLE);
            loadServiceCentersSpinner();
        } else if (userType == UserConstants.ADMIN_TYPE) {
            int serviceCenterId = SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs
                    .SERVICE_CENTER_ID, DEFAULT_VALUE);
            doAllUsersInServiceCenterApi(serviceCenterId);
        } else {
            initViewPager();
        }
       // initCalendarView(null);

       // initCalender();
    }

    private void initCalender() {

          /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor( Color.WHITE, Color.WHITE)
                .colorTextTop(Color.WHITE, Color.parseColor("#eb1c24"))
                .colorTextMiddle(Color.WHITE, Color.parseColor("#eb1c24"))
                .colorTextBottom(Color.WHITE, Color.parseColor("#eb1c24"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();



        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString();
                AppUtils.shortToast(getActivity(), selectedDateStr + " selected!");
            }

        });
    }



   /* private void initCalendarView(CalendarDateModel calendarDateModel) {
        getFutureDatesFromCurrentDay(); //getting calendar list


        calendarPageManager = new LinearLayoutPageManager(getActivity(), true);
        binding.calendarRecyclerView.addOnScrollListener(scrollListener);
        calendarPageManager.setScrollEnabled(true);
        binding.calendarRecyclerView.setLayoutManager(calendarPageManager);
        HorizontalCalendarAdapter horizontalCalendarAdapter = new HorizontalCalendarAdapter();
//        horizontalCalendarAdapter.setClickCallback(calendarClickCallback);
        horizontalCalendarAdapter.setCalendarDates(datesList);
        binding.calendarRecyclerView.setAdapter(horizontalCalendarAdapter);

        //setting selected symptomSelectedDate to a horizontal calendar
        if (calendarDateModel != null) {
            Logger.d(TAG, calendarDateModel.toString());

        }

    }
*/
    /**
     * updates selected position to nearest symptomSelectedDate
     */
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            synchronized (this) {
                /*if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollListToPositionDate(recyclerView);
                    if (!isUserUpdatedSomeThing) {
                        loadLogSymptomsOntheScrolledDate();
                    } else {
                        showInputUpdateDialog();
                    }
                }*/
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //Note: RecyclerView is Reversed
            allPixelsDate += dx;
            int lastVisibleItemPosition = calendarPageManager.findFirstVisibleItemPosition();
            /*CalendarDateModel date = datesList.get(lastVisibleItemPosition + 3);

            String scrolledDate = DateUtils.getDateFromDayMonthYear(date.getYear(),
                    date.getNameOfMonth(), date.getDayOfMonth());

            symptomsData.setCreatedDate(scrolledDate);
            Calendar calendar = Calendar.getInstance(DateFormatterConstants.DATE_FORMAT_LOCALE);
            if (scrolledDate.equalsIgnoreCase(DateUtils.convertDateToOtherFormat(
                    calendar.getTime(), YYYY_MM_DD))) {
                binding.dayName.setVisibility(View.VISIBLE);
            } else {
                binding.dayName.setVisibility(View.GONE);
            }*/ //TODO have to call api

        }
    };

    /* this if most important, if expectedPositionDate < 0
     recyclerView will return to nearest item*/
    private void scrollListToPositionDate(RecyclerView recyclerView) {
        int expectedPositionDate = Math.round((allPixelsDate) / calendarPageManager.getItemSize());
        float targetScrollPosDate = expectedPositionDate * calendarPageManager.getItemSize();
        float missingPxDate = targetScrollPosDate - allPixelsDate;
        if (missingPxDate != 0) {
            recyclerView.smoothScrollBy((int) missingPxDate, 0);
        }
    }

    /**
     * adds future 3 days to the calendar
     */
    private void getFutureDatesFromCurrentDay() {
        datesList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < 3; i++) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            CalendarDateModel date = new CalendarDateModel();
            date.setDayOfMonth(day);
            date.setDayOfWeek(getDays()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            date.setNameOfMonth(getMonths()[calendar.get(Calendar.MONTH)]);
            date.setYear("" + calendar.get(Calendar.YEAR));
            datesList.add(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Collections.reverse(datesList);
        getPastDatesFromCurrentDay();
    }

    /**
     * adds past 365 days to the calendar
     */
    private void getPastDatesFromCurrentDay() {
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < numberOfDaysToShow; i++) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            CalendarDateModel date = new CalendarDateModel();
            date.setDayOfMonth(day);
            date.setDayOfWeek(getDays()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            date.setNameOfMonth(getMonths()[calendar.get(Calendar.MONTH)]);
            date.setYear("" + calendar.get(Calendar.YEAR));
            datesList.add(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    /**
     * gets the months list
     */
    private String[] getMonths() {
        months = Calendar.getInstance().getDisplayNames(
                Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        monthIndex = Arrays.copyOf(
                months.values().toArray(), months.values().toArray().length, Integer[].class);
        monthsInAllLanguages = Arrays.copyOf(
                months.keySet().toArray(), months.keySet().toArray().length, String[].class);
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[monthIndex[i]] = monthsInAllLanguages[i].toUpperCase();
        }
        return months;
    }

    /**
     * gets the days list
     */
    private String[] getDays() {
        days = Calendar.getInstance().getDisplayNames(
                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        dayIndex = Arrays.copyOf(
                days.values().toArray(), days.values().toArray().length, Integer[].class);
        dayInAllLanguages = Arrays.copyOf(
                days.keySet().toArray(), days.keySet().toArray().length, String[].class);
        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[dayIndex[i] - 1] = dayInAllLanguages[i].toUpperCase().substring(0, 1);
        }
        return days;
    }

    private void doAllUsersInServiceCenterApi(int serviceCenterId) {
        newRequestPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

    private void loadUsersSpinner(List<AddUser> usersListOfServiceCenters) {
        binding.spinnerUsers.setVisibility(View.VISIBLE);

        List<String> usersArray = new ArrayList<>(usersListOfServiceCenters.size());

        if (usersListOfServiceCenters == null || usersListOfServiceCenters.size() == 0) {
            usersSelectedPosition = MINUS_ONE;
            binding.spinnerUsers.setText("");
            binding.spinnerUsers.setOnItemClickListener(null);
        } else {
            for (AddUser usersListOfServiceCenter : usersListOfServiceCenters) {
                usersArray.add(usersListOfServiceCenter.getName());
            }

            usersSelectedPosition = 0;
            binding.spinnerUsers.setText(usersArray.get(0));
            binding.spinnerUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (usersSelectedPosition != position) {
                        usersSelectedPosition = position;
                        refreshFragmentByPosition(usersSelectedPosition);

                    }

                    //For avoiding double tapping issue
                    if (binding.spinnerUsers.getOnItemClickListener() != null) {
                        binding.spinnerUsers.onItemClick(parent, view, position, id);
                    }
                }
            });
            refreshFragmentByPosition(MINUS_ONE); // load all requests based on service center
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, usersArray);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerUsers.setAdapter(arrayAdapter);
    }

    private void refreshFragmentByPosition(int usersSelectedPosition) {
        ((HomeActivity) getActivity()).setUserId(usersSelectedPosition ==
                MINUS_ONE ? usersSelectedPosition : usersListOfServiceCenters.get
                (usersSelectedPosition).getId());
        BaseProductOptionsFragment fragmentFromPosition = (BaseProductOptionsFragment) adapter.getFragmentFromPosition(currentTabPosition);
        fragmentFromPosition.doRefresh(false);

    }

    private void loadServiceCentersSpinner() {
        serviceCentersSelectedPosition = 0;
        // getting service centers list
        serviceCenterList = ConnectApplication.getAppContext().getServiceCenterList();
        List<String> serviceArray = new ArrayList<>(serviceCenterList.size());
        for (AddServiceCenter serviceCenterResponse : serviceCenterList) {
            serviceArray.add(serviceCenterResponse.getName());
        }

        ((HomeActivity) getActivity()).setServiceCenterId(serviceCenterList.get(serviceCentersSelectedPosition).getId());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, serviceArray);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerServiceCenters.setAdapter(arrayAdapter);
        binding.spinnerServiceCenters.setText(serviceArray.get(0));
        binding.spinnerServiceCenters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (serviceCentersSelectedPosition != position) {
                    serviceCentersSelectedPosition = position;

                    usersSelectedPosition = -1;
                    usersListOfServiceCenters.clear();
                    loadUsersSpinner(usersListOfServiceCenters);
                    Integer serviceCenterId = serviceCenterList.get(serviceCentersSelectedPosition).getId();
                    ((HomeActivity) getActivity()).setServiceCenterId(serviceCenterId);
                    doAllUsersInServiceCenterApi(serviceCenterId);
                }

                //For avoiding double tapping issue
                if (binding.spinnerServiceCenters.getOnItemClickListener() != null) {
                    binding.spinnerServiceCenters.onItemClick(parent, view, position, id);
                }
            }
        });
        doAllUsersInServiceCenterApi(serviceCenterList.get(serviceCentersSelectedPosition).getId());
    }

    private void initViewPager() {
        AssetManager assets = getActivity().getAssets();
        defaultTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");

        selectedTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Bold.ttf");

        tabTitles = getResources().getStringArray(R.array.status_tab);

        final CustomViewPager customViewPager = binding.viewPager;
        final TabLayout tabLayout = binding.tabLayout;
        setTabIcons();
        changeTitleFont(0);

        adapter = new StatusTabPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        customViewPager.setAdapter(adapter);


        customViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currentTabPosition = tab.getPosition();
                customViewPager.setCurrentItem(currentTabPosition);
                changeTitleFont(currentTabPosition);
                refreshFragmentByPosition(usersSelectedPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void changeTitleFont(int position) {
        for (int i = 0; i < tabTitles.length; i++) {
            View view = binding.tabLayout.getTabAt(i).getCustomView();
            CustomTabBinding customTabView = DataBindingUtil.bind(view);
            customTabView.tabTv.setTypeface(i == position
                    ? selectedTypeFace
                    : defaultTypeFace);
        }
    }

    private CustomTabBinding getCustomTabView() {
        return DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.custom_tab, null, false);
    }

    private void setTabIcons() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 0; i < tabTitles.length; i++) {
            CustomTabBinding customTabView = getCustomTabView();
            customTabView.tabTv.setText(tabTitles[i]);
            tabLayout.addTab(tabLayout.newTab().setCustomView(customTabView.getRoot()));
        }
    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters) {

        //adding all to the users list
        AddUser allUsersData = new AddUser();
        allUsersData.setId(-1);
        allUsersData.setName(LABEL_ALL);
        usersListOfServiceCenters.add(0, allUsersData);
        this.usersListOfServiceCenters = usersListOfServiceCenters;

        if (adapter == null) // prevents double creation of tab items
            initViewPager();

        loadUsersSpinner(usersListOfServiceCenters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newRequestPresenter.disposeAll();
    }
}
