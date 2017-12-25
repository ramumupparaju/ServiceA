package com.incon.service.callbacks;

/**
 * ResponseCallback is a bridge between Presenter and DataManager. Where DataManager interacts
 * with the presenter after a network call processing
 */
public interface IEditClickCallback extends IClickCallback {
    void onEditClickPosition(int position);
}
