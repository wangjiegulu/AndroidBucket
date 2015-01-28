package com.wangjie.androidbucket.present;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.wangjie.androidbucket.mvp.ABActivityViewer;
import com.wangjie.androidbucket.mvp.ABBasePresenter;

/**
 * Created by wangjie on 6/15/14.
 */
public class ABSupportFragment extends Fragment implements ABActivityViewer{

    private ABBasePresenter presenter;

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInfoDialog(String message) {
        showInfoDialog(null, message);
    }

    @Override
    public void showInfoDialog(String title, String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void showLoadingDialog(String message) {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void showInfoDialog(String title, String message, String okButtonText) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okButtonText, null)
                .show();
    }

    @Override
    public void registerController(ABBasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void closeAllTask() {
        presenter.closeAllTask();
    }
}
