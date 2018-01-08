package com.incon.service.ui.termsandcondition;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.incon.service.R;
import com.incon.service.databinding.ActivityTermsAndConditionsBinding;
import com.incon.service.ui.BaseActivity;


/**
 * Created on 08 Jun 2017 3:59 PM.
 *
 */
public class TermsAndConditionActivity extends BaseActivity implements
        TermsAndConditionContract.View {

    private static final String TAG = TermsAndConditionActivity.class.getName();
    private TermsAndConditionPresenter eualPresenter;
    private ActivityTermsAndConditionsBinding binding;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_terms_and_conditions;
    }

    @Override
    protected void initializePresenter() {
        eualPresenter = new TermsAndConditionPresenter();
        eualPresenter.setView(this);
        setBasePresenter(eualPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);
        binding.eulaWebview.loadUrl(TERMS_CONDITIONS_URL);
        binding.eulaWebview.setWebViewClient(webViewClient);

    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress(getString(R.string.progress_loading));
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hideProgress();
            binding.buttonRegister.setVisibility(View.VISIBLE);
        }
    };

    public void onRegisterClick() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
