package com.incon.service.ui.status.base.base;

public abstract class BaseTabFragment extends BaseProductOptionsFragment {

    public boolean isFirstTimeLoading = false;

    public abstract void onSearchClickListerner(String searchableText, String searchType);
}