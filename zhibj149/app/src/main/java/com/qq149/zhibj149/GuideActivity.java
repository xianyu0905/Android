package com.qq149.zhibj149;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.qq149.zhibj149.utils.PrefUtils;

import java.util.ArrayList;

/*2.新手引导界面*/
/*3.新手引导页开发&填充*/
/*4.页面指示器，小圆点开发*/
/*5.按钮---状态选择器*/
public class GuideActivity extends Activity {
    private ViewPager mViewPager;
    //引导页图片id数组
    private int[] mImagesIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    //imageView集合
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout llcontainer;
    //声明小红点
    private ImageView ivRedPoint;
    private Button btnStart;
    //小红点移动距离
    private int mPointDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题，必须在setContentView前面
        setContentView(R.layout.activity_guide);
        mViewPager = findViewById(R.id.vp_guide);

        llcontainer = findViewById(R.id.ll_container);

        ivRedPoint = findViewById(R.id.iv_red_point);
        btnStart = findViewById(R.id.btn_start);
        initDate();//数据初始化
        mViewPager.setAdapter(new GuideAdapter());//设置数据
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 某个页面被选中
                if (position == mImageViewList.size() - 1) {// 最后一个页面显示开始体验的按钮
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // 当页面滑动过程中的回调
                System.out.println("当前位置:" + position + ";移动偏移百分比:"
                        + positionOffset);
                // 更新小红点距离
                int leftMargin = (int) (mPointDis * positionOffset) + position * mPointDis;//计算小红点当前的左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                        ivRedPoint.getLayoutParams();
                params.leftMargin=leftMargin;//更新为最新，修改左边距

                //重新设置布局参数，最新的左边距的布局
                ivRedPoint.setLayoutParams(params);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面状态发生变化的回调
            }
        });

        //计算两个圆点的距离
        //移动的距离=第二个圆点的left值-第一个圆点的left值
        //measure->layout（确定的位置）->draw(activity 的onCreate方法执行结束之后才会走此流程)
        //mPointDis = llcontainer.getChildAt(1).getLeft()
        // -llcontainer.getChildAt(0).getLeft();
        //System.out.println("圆点距离"+mPointDis);
        //监听layout方法结束的事件，位置确定好之后再收获圆点的距离(视图树)
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    //提示加了下面这句，不懂为什么？？？？
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //移除监听，避免重复回顾
               // ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //layout方法执行结束的回调
                mPointDis = llcontainer.getChildAt(1).getLeft()
                        -llcontainer.getChildAt(0).getLeft();
                System.out.println("圆点距离"+mPointDis);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新sp
                PrefUtils.setBoolean(getApplicationContext(),"is_first_enter",false);

                //跳转主页面
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }

    //初始化数据
    private void initDate(){
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0;i<mImagesIds.length;i++){
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImagesIds[i]);//通过设置背景，可以让宽高填充布局
            mImageViewList.add(view);
            //初始化小圆点
            ImageView point = new ImageView(this);
            //设置图片（shape形状，oval圆形）

            point.setImageResource(R.drawable.shape_point_gray);
            //初始化布局参数，宽高包裹内容，父控件是谁，就是声明的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                   LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i>0){
                //从第二个点开始设置左边距,10像素
                params.leftMargin=10;
            }
            //设置布局参数
            point.setLayoutParams(params);
            //给容器添加圆点
            llcontainer.addView(point);
        }
    }

    //适配器
    class GuideAdapter extends PagerAdapter{
        //item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();//3个
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //初始化item布局
        //先拿到一个布局对象，再添加到容器，最后view返回
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view =mImageViewList.get(position);
            container.addView(view);
            return view;
        }
        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
         container.removeView((View) object);
        }
    }
}
