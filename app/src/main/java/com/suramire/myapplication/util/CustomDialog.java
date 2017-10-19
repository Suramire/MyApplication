package com.suramire.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.suramire.myapplication.R;

/**
 * Created by Suramire on 2017/9/23.
 */

public class CustomDialog extends ProgressDialog {
    public CustomDialog(Context context)
    {
        super(context);
    }

    public CustomDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dailog_progress);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show()
    {
        super.show();
    }

}
