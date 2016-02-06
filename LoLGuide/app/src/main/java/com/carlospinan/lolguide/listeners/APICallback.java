package com.carlospinan.lolguide.listeners;

/**
 * @author Carlos Piñan
 */
public interface APICallback<T> {

    void onSuccess(T response);

    void onFail(Throwable throwable);
}
