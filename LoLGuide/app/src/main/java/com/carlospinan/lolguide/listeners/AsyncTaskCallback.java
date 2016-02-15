package com.carlospinan.lolguide.listeners;

/**
 * @author Carlos Piñan
 */
public interface AsyncTaskCallback<T> extends APICallback<T> {

    void onCancelled();

    T onExecute();

}
