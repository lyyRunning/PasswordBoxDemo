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

public class ActivationCode extends RelativeLayout {
    private Context context;
    private TextView tvCode1;
    private TextView tvCode2;
    private TextView tvCode3;
    private TextView tvCode4;
    private TextView tvTip;
    private EditText etCode;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;
    private Animation shake;
    public ActivationCode(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public ActivationCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    public ActivationCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        loadView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ActivationCode(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        loadView();
    }
    private void loadView(){
        //抖动效果
        shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.activation_code, this);
        initView(view);
        initEvent();
    }
    private void initView(View view){
        tvCode1 = (TextView) view.findViewById(R.id.tv_code1);
        tvCode2 = (TextView) view.findViewById(R.id.tv_code2);
        tvCode3 = (TextView) view.findViewById(R.id.tv_code3);
        tvCode4 = (TextView) view.findViewById(R.id.tv_code4);
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
                if(editable != null && editable.length()>0) {
//                    etCode.setText("");
                    String content = editable.toString();
                    if(content.length()>1) {
                        content = content.substring(content.length() - 1);
                    }
                    if(codes.size() < 4){
                        codes.add(content);
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        etCode.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size()>0) {
                    if(codes.size()==0) {
                        etCode.setText("");
                    }
                    if(codes.size()>0) {
                        codes.remove(codes.size() - 1);
                    }
                    showCode();
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * 显示输入的验证码
     */
    private void showCode(){
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        if(codes.size()>=1){
            code1 = codes.get(0);
        }
        if(codes.size()>=2){
            code2 = codes.get(1);
        }
        if(codes.size()>=3){
            code3 = codes.get(2);
        }
        if(codes.size()>=4){
            code4 = codes.get(3);
        }
        tvCode1.setText(code1);
        tvCode2.setText(code2);
        tvCode3.setText(code3);
        tvCode4.setText(code4);
        if(codes.size()>=4&&OnActivationCodeListener!=null){
            OnActivationCodeListener.verificationCode(getPhoneCode());
        }
    }
    /**
     * 设置高亮颜色
     */
    private void setColor(boolean isShow, int color){

        tvCode1.setTextColor(color);
        tvCode2.setTextColor(color);
        tvCode3.setTextColor(color);
        tvCode4.setTextColor(color);

        if (isShow){
            tvCode1.setBackground(context.getDrawable(R.drawable.line_red));
            tvCode2.setBackground(context.getDrawable(R.drawable.line_red));
            tvCode3.setBackground(context.getDrawable(R.drawable.line_red));
            tvCode4.setBackground(context.getDrawable(R.drawable.line_red));

            //抖动
            tvCode1.startAnimation(shake);
            tvCode2.startAnimation(shake);
            tvCode3.startAnimation(shake);
            tvCode4.startAnimation(shake);
        }else {
            tvCode1.setBackground(context.getDrawable(R.drawable.line_blue));
            tvCode2.setBackground(context.getDrawable(R.drawable.line_blue));
            tvCode3.setBackground(context.getDrawable(R.drawable.line_blue));
            tvCode4.setBackground(context.getDrawable(R.drawable.line_blue));
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
            codes.clear();
            showCode();
        }
    }

    public OnActivationCodeListener OnActivationCodeListener;
    public interface OnActivationCodeListener{
        void verificationCode(String code);
    }

    public OnActivationCodeListener getOnActivationCodeListener() {
        return OnActivationCodeListener;
    }

    public void setOnActivationCodeListener(OnActivationCodeListener OnActivationCodeListener) {
        this.OnActivationCodeListener = OnActivationCodeListener;
    }
}
