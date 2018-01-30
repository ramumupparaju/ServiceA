package com.incon.service.ui.status.base.base;

import com.incon.service.dto.servicerequest.ServiceRequest;

public abstract class BaseTabFragment extends BaseProductOptionsFragment {

    public ServiceRequest serviceRequest;

    public abstract void onSearchClickListerner(String searchableText, String searchType);
}