package com.function.luo.passwordboxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn_next)
    Button btnNext;
    private WindowDialog windowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showPwdBox();
                break;
            case R.id.btn2:

                TwoActivity.launch(MainActivity.this);
                break;
            case R.id.btn_next:

                //进入下一页
                ThreeActivity.launch(MainActivity.this);

                break;
            default:
        }
    }

    /**
     * 显示密码框
     */
    private void showPwdBox() {


        windowDialog = new WindowDialog();
        windowDialog.showActivationCodeDialog(MainActivity.this
                , new WindowDialog.ActivationCodeListenter() {
                    @Override
                    public void onListener(String code) {
                        //这里验证激活码，true
                        Log.d("LUO", "======" + code);
                        if (code.equals("1234")) {
                            windowDialog.dismiss();
                            ToastUtils.showShort("激活成功");
                        } else {
                            windowDialog.showTip(true, "激活码错误");
                        }

                    }

                    @Override
                    public void finishActivity() {
                        windowDialog.dismiss();

                    }
                });

    }


}
