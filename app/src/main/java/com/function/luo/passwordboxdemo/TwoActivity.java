package com.function.luo.passwordboxdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.function.luo.ui.ActivationCode;
import com.function.luo.ui.ActivationCodeBox;

import butterknife.BindView;
import butterknife.ButterKnife;




/**
 * Created by luo on 2019/7/29.
 */

public class TwoActivity extends Activity {

    @BindView(R.id.activationCode)
    ActivationCodeBox activationCode;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.container)
    LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        //激活码输入完成
        activationCode.setOnActivationCodeListener(new ActivationCodeBox.OnActivationCodeListener() {
            @Override
            public void verificationCode(String code) {
                KeyboardUtils.hideSoftInput(TwoActivity.this);
                //回调进入 Activity
                if (code.equals("1234")){
                    //激活成功
                    ToastUtils.showShort("激活成功");
                    activationCode.showTip(false,"");
                }else{
                    //激活失败
                    ToastUtils.showShort("激活失败");
                    activationCode.showTip(true,"激活失败");
                }
            }
        });



        //返回上一页
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwoActivity.this.finish();
            }
        });

    }


    /**
     * 页面跳转
     * @param mContext
     */
    public static void launch(Context mContext) {

        Intent intent = new Intent(mContext, TwoActivity.class);

        mContext.startActivity(intent);
    }



    public interface ActivationCodeListenter{
        void onListener(String code);
        void finishActivity();
    }
}
