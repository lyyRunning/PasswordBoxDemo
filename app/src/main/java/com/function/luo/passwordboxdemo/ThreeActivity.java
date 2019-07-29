package com.function.luo.passwordboxdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by luo on 2019/7/29.
 * 自定义边框
 */

public class ThreeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_three);
    }

    public static void launch(Context mContext) {

        Intent intent = new Intent(mContext,ThreeActivity.class);
        mContext.startActivity(intent);
    }
}
