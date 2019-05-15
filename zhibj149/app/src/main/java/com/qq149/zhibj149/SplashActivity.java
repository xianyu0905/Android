package com.qq149.zhibj149;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.qq149.zhibj149.utils.PrefUtils;

/*第一周-1.闪屏页面开发
* 旋转
* 缩放
* 渐变*/

public class SplashActivity extends Activity {
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //寻找主键ID
        relativeLayout = findViewById(R.id.relativeLayout);
        //**旋转动画**
        /*0到360度旋转，x方式0.5 ，y方式0.5*/
        RotateAnimation animRotate = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,
                0.5f);
        animRotate.setDuration(1000); //动画时间
        animRotate.setFillAfter(true); //保持动画结束

        //**缩放效果**
        ScaleAnimation animaScale =  new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animRotate.setDuration(1000);//动画时间
        animRotate.setFillAfter(true);//保持动画结束

        //**渐变效果**
        AlphaAnimation animAlpha = new AlphaAnimation(0,1);
        animAlpha.setDuration(2000);//动画时间
        animAlpha.setFillAfter(true);//保持动画结束


        //**动画集合**
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animRotate);
        set.addAnimation(animaScale);
        set.addAnimation(animAlpha);

        //根布局，启动
        relativeLayout.startAnimation(set);
        /* 2.闪屏页跳转页面*/
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，跳转页面
                //如果是第一次进入，跳新手引导
                //否则跳主页
                /*SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
                boolean isFistEnter = sp.getBoolean("is_first_enter",true);*/
                boolean isFistEnter=PrefUtils.getBoolean(SplashActivity.this,
                        "is_first_enter",true);
                Intent intent;
                if(isFistEnter){
                    //新手引导
                    intent = new Intent(getApplicationContext(),GuideActivity.class);
                }else{
                    //主页面
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                }
                startActivity(intent);
                finish();//结束当前页面
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
