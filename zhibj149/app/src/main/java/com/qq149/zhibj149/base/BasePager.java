package com.qq149.zhibj149.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qq149.zhibj149.R;

/**
 * 五个标签页的基类
 * @author zhuww
 * @description: 149
 * @date :2019/5/14 21:15
 */
public class BasePager {

    public Activity mActivity;
    public TextView tvTitle;
    public ImageButton btnMenu;
    public FrameLayout flContent;//空的帧布局对象，要动态添加布局
    public View mRootView;//当前布局对象

    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    //初始化布局
    public View initView(){
      View view = View.inflate(mActivity, R.layout.base_page,null) ;
        tvTitle = view.findViewById(R.id.tv_title);
        btnMenu = view.findViewById(R.id.btn_menu);
        flContent = view.findViewById(R.id.fl_content);

       return view;
    }
    //初始化数据
    public void initData(){

    }
}
