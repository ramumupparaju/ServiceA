package com.incon.service.callbacks;

/**
 * ResponseCallback is a bridge between Presenter and DataManager. Where DataManager interacts
 * with the presenter after a network call processing
 */
public interface IStatusClickCallback extends IClickCallback {
    void onClickStatusButton(int statusType);
    void onClickStatus(int productPosition, int statusPosition);
}
