package com.jap.twStockApp.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jap.twStockApp.R;
import com.white.progressview.CircleProgressView;

public class LoadingDialog extends Dialog {
    TextView tv;
    CircleProgressView circleProgressView;
    String s;

    public LoadingDialog(@NonNull Context context, String s) {
        super(context);
        this.s = s;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.loadingfragment);
        this.tv = (TextView) findViewById(R.id.tv);
        this.circleProgressView = (CircleProgressView) findViewById(R.id.progressBar1);
        tv.setText(s);
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }


    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
    }

    public <T extends Number> void setProgressBar(T s) {
        this.circleProgressView.setProgress(s.intValue());
    }

}
