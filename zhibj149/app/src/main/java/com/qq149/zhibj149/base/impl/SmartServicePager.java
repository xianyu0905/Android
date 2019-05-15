package com.qq149.zhibj149.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.qq149.zhibj149.base.BasePager;

/**
 * 智慧服务
 * @author zhuww
 * @description: 149
 * @date :2019/5/14 21:28
 */
public class SmartServicePager extends BasePager {

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("智慧服务初始化啦。。。");
        //要给帧布局填充布局对象
        TextView view = new TextView(mActivity);
        view.setText("智慧服务");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);
    }
}
