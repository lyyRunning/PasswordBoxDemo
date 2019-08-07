package com.function.luo.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.function.luo.passwordboxdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : haitao
 * date   : 2019/7/23
 * desc   :自定义激活码的布局
 */

public class ActivationCodeBox extends RelativeLayout {
    private Context context;
    private TextView[] textViews;
    private static int MAXlength = 4;
    private TextView tvTip;
    private String inputContent;

    private EditText etCode;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;
    private   Animation shake;
    public ActivationCodeBox(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public ActivationCodeBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    public ActivationCodeBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        loadView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ActivationCodeBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        loadView();
    }
    private void loadView(){
        //抖动效果
        shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.activation_code_box, this);
        initView(view);
        initEvent();
    }
    private void initView(View view){
        textViews = new TextView[MAXlength];
        textViews[0] = (TextView) view.findViewById(R.id.tv_code1);
        textViews[1] = (TextView) view.findViewById(R.id.tv_code2);
        textViews[2] = (TextView) view.findViewById(R.id.tv_code3);
        textViews[3] = (TextView) view.findViewById(R.id.tv_code4);
        etCode = (EditText) view.findViewById(R.id.et_code);
        tvTip = (TextView) view.findViewById(R.id.tv_tip);

    }
    private void initEvent(){
        //验证码输入
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

                inputContent = etCode.getText().toString();
                if (inputCompleteListener != null) {
                    if (inputContent.length() >= MAXlength) {
                        inputCompleteListener.inputComplete(inputContent);
                    }
                }
                for (int i = 0; i < MAXlength; i++) {
                    if (i < inputContent.length()) {
                        textViews[i].setText(String.valueOf(inputContent.charAt(i)));
                    } else {
                        textViews[i].setText("");
                    }
                }


            }
        });

    }

    /**
     * 设置高亮颜色
     */
    private void setColor(boolean isShow, int color){

        for (int i = 0; i <MAXlength ; i++) {
            textViews[i].setTextColor(color);
            if(isShow){
                textViews[i].setBackground(context.getResources().getDrawable(R.drawable.box_red));
                //抖动
                textViews[i].startAnimation(shake);
            }else {
                textViews[i].setBackground(context.getResources().getDrawable(R.drawable.box_blue));

            }
        }

    }
    /**
     * 获得手机号验证码
     * @return 验证码
     */
    public String getPhoneCode(){
        StringBuilder sb = new StringBuilder();
        for (String code : codes) {
            sb.append(code);
        }
        return sb.toString();
    }

    /**
     * 显示提示语
     * @param isShow 是否显示提示语 true 代码验证码错误；false 验证码失败
     * @param content 提示语内容
     */
    public void showTip(boolean isShow,String content){
        tvTip.setVisibility(isShow?VISIBLE:INVISIBLE);
        tvTip.setText(content);
        if(isShow){
            setColor(isShow,context.getColor(R.color.color_ffe93a3a));

        }else{
            setColor(isShow,context.getColor(R.color.btn_press_bg_color));
            etCode.setText("");
            codes.clear();

        }
    }

    private InputCompleteListener inputCompleteListener;
    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }
    public interface InputCompleteListener {
        void inputComplete(String code);
    }
}
