package com.incon.service.dto.dialog;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by PC on 9/28/2017.
 */

public class CheckedModelSpinner extends BaseObservable {
    private String name;
    private boolean checked;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyChange();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object checkedModelSpinner) {
        if (checkedModelSpinner instanceof CheckedModelSpinner) {
            CheckedModelSpinner modelSpinner = ((CheckedModelSpinner) checkedModelSpinner);
            if (modelSpinner != null) {
                return  (modelSpinner.getName().equalsIgnoreCase(name));
            }
        }
        return false;
    }
}
