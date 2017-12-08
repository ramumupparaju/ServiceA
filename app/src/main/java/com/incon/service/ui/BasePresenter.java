package com.incon.service.ui;

import android.os.Bundle;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends BaseView> {

    private CompositeDisposable compositeDisposable;

    private WeakReference<T> view;

    public void setView(T view) {
        this.view = new WeakReference<>(view);
    }

    public T getView() {
        if (view != null)
            return view.get();
        return null;
    }

    public void initialize(Bundle extras) {
    }

    public BasePresenter() {
        createDisposableContainer();
    }

    public void createDisposableContainer() {
        compositeDisposable = new CompositeDisposable();
    }
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public void disposeAll() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
            createDisposableContainer();
        }
    }

}
