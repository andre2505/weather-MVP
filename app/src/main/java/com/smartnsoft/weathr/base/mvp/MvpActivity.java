package com.smartnsoft.weathr.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.smartnsoft.weathr.base.ui.BaseActivity;
import com.smartnsoft.weathr.base.ui.BasePresenter;

/**
 * Created by alandwiprasetyo on 11/22/16.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettachView();
        }
    }
}