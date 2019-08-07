package com.function.luo.passwordboxdemo;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.function.luo.ui.ActivationCode;


/**
 * Created by LYY on 2018/11/30.
 * 页面自定义弹窗
 */

public class WindowDialog {
    private Display display;
    private Dialog dialog;
    private LinearLayout container;
    private ActivationCode activationCode;

    /**
     * 激活码对话框
     * @param activity
     * @param activationCodeListenter
     */
    public void showActivationCodeDialog(final Activity activity , final ActivationCodeListenter activationCodeListenter) {

        //获取屏幕对象
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

        // 获取Dialog布局
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_show_activation_code,null);

        // 获取自定义Dialog布局中的控件
        container = (LinearLayout) view.findViewById(R.id.container);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        activationCode = (ActivationCode) view.findViewById(R.id.activationCode);
        // 定义Dialog布局和参数
        dialog = new Dialog(activity, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        // 调整dialog背景大小
        container.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.show();
        //  取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog!=null){
                    dialog.dismiss();
                    dialog=null;
                }
            }

        });
        //激活码输入完成
        activationCode.setInputCompleteListener(new ActivationCode.InputCompleteListener() {
            @Override
            public void inputComplete(String code) {
                KeyboardUtils.hideSoftInput(activity);
                //回调进入 Activity
                if(activationCodeListenter!=null){
                    activationCodeListenter.onListener(code);
                }
            }

        });

    }
    public interface ActivationCodeListenter{
        void onListener(String code);
        void finishActivity();
    }
    public void showTip(boolean isShow, String content) {
        activationCode.showTip(isShow,content);
    }

    public void dismiss() {
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }


}
