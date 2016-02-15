package com.carlospinan.lolguide.services;

import android.os.AsyncTask;

import com.carlospinan.lolguide.listeners.AsyncTaskCallback;

/**
 * @author Carlos Piñan
 */
public class AsyncTaskService<T> extends AsyncTask<Void, Void, T> {

    private AsyncTaskCallback<T> callback;

    public AsyncTaskService(AsyncTaskCallback<T> callback) {
        this.callback = callback;
        execute();
    }

    @Override
    protected T doInBackground(Void... params) {
        T execute = callback.onExecute();
        return execute;
    }

    @Override
    protected void onPostExecute(T result) {
        if (result != null) {
            callback.onSuccess(result);
        } else {
            callback.onFail(null);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onCancelled();
    }
}
