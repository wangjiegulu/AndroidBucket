package com.wangjie.androidbucket.present;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import com.wangjie.androidbucket.mvp.ABActivityViewer;
import com.wangjie.androidbucket.mvp.ABBasePresenter;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/30/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@Deprecated
public class ABFragment extends Fragment implements ABActivityViewer {

    @Override
    public void showToastMessage(String message) {
    }

    @Override
    public void showInfoDialog(String message) {
    }

    @Override
    public void showInfoDialog(String title, String message) {
    }

    @Override
    public void showLoadingDialog(String message) {
    }

    @Override
    public void cancelLoadingDialog() {
    }

    @Override
    public void showInfoDialog(String title, String message, String okButtonText) {
    }

    @Override
    public void registerPresenter(ABBasePresenter presenter) {
    }

    @Override
    public void closeAllTask() {
    }
}
