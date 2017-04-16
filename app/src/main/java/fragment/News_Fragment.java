package fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.school.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import image.CircleDrawableImageView;

import static android.content.ContentValues.TAG;


/**
 * Created by River on 2017/3/18.
 */

public class News_Fragment extends Fragment{

    //ViewPager用于切换
    private ViewPager viewPager;
    //用于存储viewpager页面
    private ArrayList<View> pageView;
    //底部标题栏(用来隐藏或显示)
    private RelativeLayout bottomly;

    //最上面的标题：资讯、论坛(暂时不需要改动)
    private TextView zixun_textview;
    private TextView luntan_textview;

    // 滚动条图片
    private ImageView scrollbar;
    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;


    //我的关注--新闻类别(layout布局)
    private LinearLayout bbs_select_class_layout;
    //最新发布--新闻排序(layout布局)
    private LinearLayout bbs_select_sort_layout;

    //新闻类别和新闻排序的两个listview(点击layout即从下面四个链表传值过来并显示)
    private ListView listview_class;
    private ListView listview_sort;

    //分别保存资讯的新闻类别、资讯的新闻排序、论坛的新闻类别、论坛的新闻排序的listview的数据
    private ArrayList<Map<String, Object>> class_mData_zixun;
    private ArrayList<Map<String, Object>> sort_mData_zixun;
    private ArrayList<Map<String, Object>> class_mData_luntan;
    private ArrayList<Map<String, Object>> sort_mData_luntan;


    //新闻类别和新闻排序的listview的可见性：0为不可见，1为可见
    private int bbs_class_flag=0;
    private int bbs_sort_flag=0;

    //两个textview--两个layout的组成部分
    private TextView class_textview;
    private TextView sort_textview;

    //两个imageview--两个layout的组成部分
    private ImageView class_imageview;
    private ImageView sort_imageview;

    //资讯和论坛各自的两个textview（传值给layout的那个textview)
    private TextView zixun_class_textview;
    private TextView zixun_sort_textview;

    private TextView luntan_class_textview;
    private TextView luntan_sort_textview;

    //资讯和论坛各自的两个imageview（传值给layout的那个imageview)
    private ImageView zixun_class_imageview;
    private ImageView zixun_sort_imageview;

    private ImageView luntan_class_imageview;
    private ImageView luntan_sort_imageview;

    //在论坛页新建消息的imageview(用于显示或隐藏)
    private ImageView createnew_inbbs;

    //左上角返回主页的imageview
    private ImageView return_imageview;

    //用于新闻的listview：分别是资讯和论坛
    private ListView news_listview_zixun;
    private ListView news_listview_luntan;
    private ArrayList<Map<String, Object>> news_mData_zixun;
    private ArrayList<Map<String, Object>> news_mData_luntan;

    //onCreateView返回的view
    View view;

    //当前的currentitem是0，也就是在资讯页面
    public void initView(){

        //资讯和论坛两个顶部标题的文本（此处未修改）
        zixun_textview = (TextView)view.findViewById(R.id.newsandbbs_zixun);
        luntan_textview = (TextView)view.findViewById(R.id.newsandbbs_luntan);

        //滑动时用来显示的scrollbar（顶部标题之下）
        scrollbar = (ImageView)view.findViewById(R.id.newsandbbs_scrollbar);

        //资讯和论坛各自的两个layout
        bbs_select_class_layout=(LinearLayout)view.findViewById(R.id.newsandbbs_class_layout);
        bbs_select_sort_layout=(LinearLayout)view.findViewById(R.id.newsandbbs_sort_layout);

        //新闻类别的textview和新闻排序的textview
        class_textview=(TextView) bbs_select_class_layout.findViewById(R.id.newsandbbs_class_textview);
        sort_textview=(TextView) bbs_select_sort_layout.findViewById(R.id.newsandbbs_sort_textview);

        //资讯和论坛各自的新闻类别的textview和新闻排序的textview
        zixun_class_textview=new TextView(view.getContext());
        zixun_class_textview.setText("资讯_我的关注");
        zixun_sort_textview=new TextView(view.getContext());
        zixun_sort_textview.setText("资讯_最新发布");

        luntan_class_textview=new TextView(view.getContext());
        luntan_class_textview.setText("论坛_我的关注");
        luntan_sort_textview=new TextView(view.getContext());
        luntan_sort_textview.setText("论坛_最新发布");

        //新闻类别的imageview和新闻排序的imageview
        class_imageview=(ImageView) bbs_select_class_layout.findViewById(R.id.newsandbbs_class_imageview);
        sort_imageview=(ImageView) bbs_select_sort_layout.findViewById(R.id.newsandbbs_sort_imageview);

        //资讯和论坛各自的新闻类别的imageview和新闻排序的imageview
        zixun_class_imageview=new ImageView(view.getContext());
        zixun_class_imageview.setImageResource(R.drawable.ic_test_select);
        zixun_sort_imageview=new ImageView(view.getContext());
        zixun_sort_imageview.setImageResource(R.drawable.ic_test_select);

        luntan_class_imageview=new ImageView(view.getContext());
        luntan_class_imageview.setImageResource(R.drawable.ic_test_select);
        luntan_sort_imageview=new ImageView(view.getContext());
        luntan_sort_imageview.setImageResource(R.drawable.ic_test_select);


        //新闻类别的listview和新闻排序的listview
        listview_class=(ListView)view.findViewById(R.id.newsandbbs_selectclass_listview);
        listview_sort=(ListView)view.findViewById(R.id.newsandbbs_selectsort_listview);

        //初始化完成后开始赋值
        //设置新闻类别和新闻排序的各自的textview和imageview(因为在资讯页面，所以初始化全为资讯的textview和imageview)
        class_textview.setText(zixun_class_textview.getText());
        sort_textview.setText(zixun_sort_textview.getText());
        class_imageview.setImageDrawable(zixun_class_imageview.getDrawable());
        sort_imageview.setImageDrawable(zixun_sort_imageview.getDrawable());

        //设置新建帖子的可见性（因为在资讯页面，所以不可见）
        createnew_inbbs = (ImageView)view.findViewById(R.id.newsandbbs_createnew_imageview);
        createnew_inbbs.setVisibility(View.INVISIBLE);

        //返回主页的imageview
        return_imageview = (ImageView)view.findViewById(R.id.newsandbbs_back_imageview);


        //为资讯页面和论坛页面各自的两个listview赋值（后面页面更改，listview需要重绘，从这里取值）
        class_mData_zixun=select_getData_zixun();
        sort_mData_zixun=sort_getData_zixun();
        class_mData_luntan=select_getData_luntan();
        sort_mData_luntan=sort_getData_luntan();

        //为新闻类别和新闻排序两个listview初始化（初始都是资讯的数据）
        MyAdapterClass adapter_zixun_select = new MyAdapterClass(view.getContext(),class_mData_zixun);
        listview_class.setAdapter(adapter_zixun_select);
        listview_class.setVisibility(View.INVISIBLE);
        MyAdapterClass adapter_zixun_sort = new MyAdapterClass(view.getContext(),sort_mData_zixun);
        listview_sort.setAdapter(adapter_zixun_sort);
        listview_sort.setVisibility(View.INVISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.news_tab, container, false);

        //隐藏底部标题栏
        bottomly=(RelativeLayout) getActivity().findViewById(R.id.bottom_bar);
        bottomly.setVisibility(View.GONE);


        viewPager = (ViewPager)view.findViewById(R.id.newsandbbs_news_viewPager);
        //查找布局文件用LayoutInflater.inflate
        View view1 = inflater.inflate(R.layout.zixun_layout, null);
        View view2 = inflater.inflate(R.layout.luntan_layout, null);

        //初始化（找到各View并简单设置）
        initView();


        pageView =new ArrayList<View>();
        //添加想要切换的界面
        pageView.add(view1);
        pageView.add(view2);


        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){
            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageView.size();
            }
            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageView.get(arg1));
            }
            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageView.get(arg1));
                return pageView.get(arg1);
            }
        };




        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置viewPager的初始界面为第一个界面
        viewPager.setCurrentItem(0);



        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        //bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.ic_scrollbar).getWidth();
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.ic_scrollbar).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //十分之一的屏幕宽度
        int oneinten=screenW/10;

        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW - oneinten) / 2;

        //计算出切换一个界面时，滚动条的位移量 2个初始+1个宽度
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset+oneinten, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);

        //到这里为止是孟元实现的滑动切换viewpage



        //监听顶部的资讯和论坛两个标题，点击标题切换页面（利用viewPager.setCurrentItem，下面那个监听页面变化的函数就会启动）。
        zixun_textview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果当前在论坛页就切换，否则不变
                if(viewPager.getCurrentItem()==1){
                    viewPager.setCurrentItem(0);
                }
            }
        });

        luntan_textview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果当前在资讯页就切换，否则不变
                if(viewPager.getCurrentItem()==0){
                    viewPager.setCurrentItem(1);
                }
            }
        });

        //点击创建新的帖子监听
        createnew_inbbs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果在论坛页面就提示
                //实际上不需要判断，因为一开始设置了不可见，每次到资讯页面也都设置了不可见，不过这样更严谨
                if(viewPager.getCurrentItem()==1){
                    Toast.makeText(view.getContext(),"创建新的帖子",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //返回主页事件监听
        return_imageview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"返回上一个Fragment",Toast.LENGTH_SHORT).show();
                bottomly.setVisibility(View.VISIBLE);
                //FragmentMainActivity.setTabSelection(0);
                FragmentManager fm = getFragmentManager();
                MainTab1 fragment = new MainTab1();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.fragment_content,fragment);
                transaction.commit();

                //FragmentTransaction transaction = fm.beginTransaction();
                //Toast.makeText(view.getContext(),.+"",Toast.LENGTH_SHORT).show();
            }
        });


        //点击新闻类别弹出一个listview
        bbs_select_class_layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //如果在资讯这一页就利用资讯的数据重绘listview
                if(viewPager.getCurrentItem()==0){
                  //  Toast.makeText(view.getContext(),"我是资讯的select",Toast.LENGTH_SHORT).show();
                    //如果还没有点击了（还没有出现listview）
                    if(bbs_class_flag==0){
                        //利用资讯的数据重绘listview
                        MyAdapterClass adapter_zixun_select = new MyAdapterClass(view.getContext(),class_mData_zixun);
                        listview_class.setAdapter(adapter_zixun_select);
                        listview_class.setVisibility(View.VISIBLE);
                        listview_sort.setVisibility(View.INVISIBLE);
                        bbs_sort_flag=0;
                        bbs_class_flag=1;
                    }else{
                        listview_class.setVisibility(View.INVISIBLE);
                        bbs_class_flag=0;
                    }
                }else{
                  //  Toast.makeText(view.getContext(),"我是论坛的select",Toast.LENGTH_SHORT).show();
                    //如果还没有点击了（还没有出现listview）
                    if(bbs_class_flag==0){
                        //利用论坛的数据重绘listview
                        MyAdapterClass adapter_luntan_select = new MyAdapterClass(view.getContext(),class_mData_luntan);
                        listview_class.setAdapter(adapter_luntan_select);
                        listview_class.setVisibility(View.VISIBLE);
                        listview_sort.setVisibility(View.INVISIBLE);
                        bbs_sort_flag=0;
                        bbs_class_flag=1;
                    }else{
                        listview_class.setVisibility(View.INVISIBLE);
                        bbs_class_flag=0;
                    }
                }
            }
        });

        //点击新闻排序弹出一个listview
        bbs_select_sort_layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //如果在资讯这一页就利用资讯的数据重绘listview
                if(viewPager.getCurrentItem()==0){
                   // Toast.makeText(view.getContext(),"我是资讯的sort",Toast.LENGTH_SHORT).show();
                    //如果还没有点击了（还没有出现listview）
                    if(bbs_sort_flag==0){
                        //利用资讯的数据重绘listview
                        MyAdapterClass adapter_zixun_sort = new MyAdapterClass(view.getContext(),sort_mData_zixun);
                        listview_sort.setAdapter(adapter_zixun_sort);
                        listview_sort.setVisibility(View.VISIBLE);
                        listview_class.setVisibility(View.INVISIBLE);
                        bbs_sort_flag=1;
                        bbs_class_flag=0;
                    }else{
                        listview_sort.setVisibility(View.INVISIBLE);
                        bbs_sort_flag=0;
                    }
                }else{
                  //  Toast.makeText(view.getContext(),"我是论坛的sort",Toast.LENGTH_SHORT).show();
                    //如果还没有点击了（还没有出现listview）
                    if(bbs_sort_flag==0){
                        //利用资讯的数据重绘listview
                        MyAdapterClass adapter_luntan_sort = new MyAdapterClass(view.getContext(),sort_mData_luntan);
                        listview_sort.setAdapter(adapter_luntan_sort);
                        listview_sort.setVisibility(View.VISIBLE);
                        listview_class.setVisibility(View.INVISIBLE);
                        bbs_sort_flag=1;
                        bbs_class_flag=0;
                    }else{
                        listview_sort.setVisibility(View.INVISIBLE);
                        bbs_sort_flag=0;
                    }
                }
            }
        });


        //资讯和论坛的新闻的listview
        news_listview_zixun=(ListView)view1.findViewById(R.id.news_listview);
        news_listview_luntan=(ListView)view2.findViewById(R.id.news_listview);

        news_mData_zixun=news_getData_zixun("资讯_我的关注");
        news_mData_luntan=news_getData_luntan("论坛_我的关注");

        MyAdapterNews news_adapter_zixun = new MyAdapterNews(view.getContext(),news_mData_zixun);
        news_listview_zixun.setAdapter(news_adapter_zixun);
        MyAdapterNews news_adapter_luntan = new MyAdapterNews(view.getContext(),news_mData_luntan);
        news_listview_luntan.setAdapter(news_adapter_luntan);
        return view;
    }



    public ArrayList<String> initview(){
        Log.d(TAG,"执行");
        ArrayList<String> data=new ArrayList<String>();
        data.add("测试1");
        return data;
    }




    //返回事件监听(无效)
    public void onBackPressed() {
        Log.d(TAG,"返回按钮");
        if(bbs_class_flag==0&&bbs_sort_flag==0){
            Toast.makeText(view.getContext(),"返回主页",Toast.LENGTH_SHORT).show();
            Log.d(TAG,"返回主页");
        }else{
            listview_class.setVisibility(View.INVISIBLE);
            listview_sort.setVisibility(View.INVISIBLE);
            bbs_class_flag=0;
            bbs_sort_flag=0;
            Log.d(TAG,"返回");
            Toast.makeText(view.getContext(),"返回",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //返回事件监听（有效）
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Log.d(TAG,"返回按钮");
                    if(bbs_class_flag==0&&bbs_sort_flag==0){
                        Toast.makeText(view.getContext(),"返回主页",Toast.LENGTH_SHORT).show();
                        //显示底部标题栏
                        bottomly.setVisibility(View.VISIBLE);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        //创建一个WeChatFragment对象
                        MainTab1 mainTab1=new MainTab1();
                        //MainTab2 mWeixin=new MainTab2();
                        //transaction.replace(A, B);-----用B把A替换
                        transaction.replace(R.id.fragment_content,mainTab1);
                        transaction.commit();


                        Log.d(TAG,"返回主页");
                    }else{
                        listview_class.setVisibility(View.INVISIBLE);
                        listview_sort.setVisibility(View.INVISIBLE);
                        bbs_class_flag=0;
                        bbs_sort_flag=0;
                        Log.d(TAG,"返回");
                        Toast.makeText(view.getContext(),"返回",Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();
                    // handle back button

                    return true;

                }

                return false;
            }
        });
    }



    //标签切换监听
    //监听的实际上是setCurrentItem这个函数，在上面的点击事件中实际上就是setcurrentItem的过程
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            Log.d(TAG,"(onPageSelected)当前页面为"+viewPager.getCurrentItem()+"\t"+arg0);
            Animation animation = null;
            //arg0为切换到的页的编码
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/


                    //当前arg0是0，实际上表示是从1变为了0.也就是原本在论坛页面，现在在资讯页面
                    //那么论坛页面的layout不可见，资讯页面的layout可见
                    //论坛页特有的新建帖子也不可见了
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    createnew_inbbs.setVisibility(View.INVISIBLE);

                    class_textview.setText(zixun_class_textview.getText());
                    sort_textview.setText(zixun_sort_textview.getText());
                    class_imageview.setImageDrawable(zixun_class_imageview.getDrawable());
                    sort_imageview.setImageDrawable(zixun_sort_imageview.getDrawable());

                    //同时论坛的listview也不可见
                    bbs_class_flag=0;
                    bbs_sort_flag=0;
                    listview_class.setVisibility(View.INVISIBLE);
                    listview_sort.setVisibility(View.INVISIBLE);
                    break;

                case 1:

                    //当前arg0是1，实际上表示是从0变为了1.也就是原本在资讯页面，现在在论坛页面
                    //那么资讯页面的layout不可见，论坛页面的layout可见
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    createnew_inbbs.setVisibility(View.VISIBLE);

                    class_textview.setText(luntan_class_textview.getText());
                    sort_textview.setText(luntan_sort_textview.getText());
                    class_imageview.setImageDrawable(luntan_class_imageview.getDrawable());
                    sort_imageview.setImageDrawable(luntan_sort_imageview.getDrawable());

                    //同时资讯的listview也不可见
                    bbs_class_flag=0;
                    bbs_sort_flag=0;
                    listview_class.setVisibility(View.INVISIBLE);
                    listview_sort.setVisibility(View.INVISIBLE);
                    break;
            }
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    //从这里开始自定义listview
    //新闻类别数据获取--资讯
    private ArrayList<Map<String, Object>> select_getData_zixun() {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_我的关注");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_全部学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_计算机学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_理学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_自动化学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_通信工程学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_机械学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_会计学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_人文与法学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_外国语学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_软件工程学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_教务处");
        list.add(map);

        return list;
    }

    //新闻排序数据获取--资讯
    private ArrayList<Map<String, Object>> sort_getData_zixun() {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_最多评论");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_最多阅读");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_最多关注");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "资讯_最多收藏");
        list.add(map);
        return list;
    }


    //新闻类别数据获取--论坛
    private ArrayList<Map<String, Object>> select_getData_luntan() {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_我的关注");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_全部学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_计算机学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_理学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_自动化学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_通信工程学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_机械学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_会计学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_人文与法学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_外国语学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_软件工程学院");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_教务处");
        list.add(map);

        return list;
    }

    //新闻排序数据获取--论坛
    private ArrayList<Map<String, Object>> sort_getData_luntan() {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_最多评论");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_最多阅读");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_最多关注");
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("Image",R.drawable.ic_test_select);
        map.put("Text", "论坛_最多收藏");
        list.add(map);
        return list;
    }


    //新闻适配--资讯
    //传递零个或多个参数
    private ArrayList<Map<String, Object>> news_getData_zixun(String ... school) {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        //获取未知参数个数的第一个参数
        String sch="";
        for(String i : school){
            sch=i;
            break;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第一篇新闻。这是我发布的第一篇新闻。这是我发布的第一篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第二篇新闻。这是我发布的第二篇新闻。这是我发布的第二篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第三篇新闻。这是我发布的第三篇新闻。这是我发布的第三篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第四篇新闻。这是我发布的第四篇新闻。这是我发布的第四篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第五篇新闻。这是我发布的第五篇新闻。这是我发布的第五篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第六篇新闻。这是我发布的第六篇新闻。这是我发布的第六篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第七篇新闻。这是我发布的第七篇新闻。这是我发布的第七篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第八篇新闻。这是我发布的第八篇新闻。这是我发布的第八篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        return list;
    }

    //新闻适配--论坛
    private ArrayList<Map<String, Object>> news_getData_luntan(String ... school) {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        //获取未知参数个数的第一个参数
        String sch="";
        for(String i : school){
            sch=i;
            break;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第一篇新闻。这是我发布的第一篇新闻。这是我发布的第一篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第二篇新闻。这是我发布的第二篇新闻。这是我发布的第二篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第三篇新闻。这是我发布的第三篇新闻。这是我发布的第三篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第四篇新闻。这是我发布的第四篇新闻。这是我发布的第四篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第五篇新闻。这是我发布的第五篇新闻。这是我发布的第五篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第六篇新闻。这是我发布的第六篇新闻。这是我发布的第六篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第七篇新闻。这是我发布的第七篇新闻。这是我发布的第七篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        map=new HashMap<String, Object>();
        map.put("HeadImage",R.drawable.ic_head);
        map.put("NameText", sch);
        map.put("TimeText", "2017年4月7日 下午5:00");
        map.put("GuanZhuImage", R.drawable.ic_add_to_my_favorite);
        map.put("DetailText", "这是我发布的第八篇新闻。这是我发布的第八篇新闻。这是我发布的第八篇新闻。");
        map.put("PingLunLiangText", "100");
        map.put("YueDuLiangText", "100万");
        map.put("LikeImage", R.drawable.ic_like_untouched);
        map.put("NewsImage", R.drawable.ic_head);
        list.add(map);
        return list;
    }


    //新闻类别和新闻排序的listview构成
    public final class SelectViewHolder{
        public LinearLayout linearlayout;
        public ImageView imageview;
        public TextView textview;
    }

    //新闻的listview的构成
    public final class NewsViewHolder{
        public ImageView user_head_imageview;
        public TextView user_name_textview;
        public TextView user_time_textview;
        public ImageView guanzhu_imageview;
        public TextView news_detail_textview;
        public TextView news_pinglun_textview;
        public TextView news_yuedu_textview;
        public ImageView like_imageview;
        public ImageView news_imageview;

    }


    //新闻类别的listview的适配器
    public class MyAdapterClass extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<Map<String, Object>> mData;


        public MyAdapterClass(Context context,ArrayList<Map<String, Object>> bufData){
            //bufData为传的参数--也就是上面的函数所产生的数据
            mData=bufData;
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            SelectViewHolder holder = null;
            if (convertView == null) {

                holder=new SelectViewHolder();
                convertView = mInflater.inflate(R.layout.demo_select, null);
                //新闻类别的imageview
                holder.linearlayout=(LinearLayout)convertView.findViewById(R.id.news_class);
                holder.imageview = (ImageView)convertView.findViewById(R.id.newsandbbs_selectclass_imageview);
                holder.textview = (TextView)convertView.findViewById(R.id.newsandbbs_selectsort_textview);
                convertView.setTag(holder);

            }else {
                holder = (SelectViewHolder)convertView.getTag();
            }


            holder.imageview.setBackgroundResource((Integer)mData.get(position).get("Image"));
            holder.textview.setText((String)mData.get(position).get("Text"));

            holder.linearlayout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"You Clicked "+position,Toast.LENGTH_SHORT).show();
                    /*try{
                        Thread.sleep(20000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }*/
                    //如果选择了一个类别就让上面那个Linearlayout改为该类别同时设置这个listview为不可见
                    if(bbs_class_flag==1){
                        //说明是新闻选择的layout，因此修改新闻选择的layout再隐藏此listview，同时重绘新闻
                        //判断是在哪一页，然后重绘新闻
                        switch (viewPager.getCurrentItem()){
                            case 0:
                                //在资讯页面
                                //修改LinearLayout
                                TextView textview_zixun=(TextView) bbs_select_class_layout.findViewById(R.id.newsandbbs_class_textview);
                                ImageView imageview_zixun=(ImageView) bbs_select_class_layout.findViewById(R.id.newsandbbs_class_imageview);

                                textview_zixun.setText((String)mData.get(position).get("Text"));
                                imageview_zixun.setImageResource((int)mData.get(position).get("Image"));

                                zixun_class_textview.setText((String)mData.get(position).get("Text"));
                                zixun_class_imageview.setImageResource((int)mData.get(position).get("Image"));

                                //重要!!!
                                //还需要修改zixun_select_textview和zixun_select_imageview

                                listview_class.setVisibility(View.INVISIBLE);
                                bbs_class_flag=0;


                                //同时重绘新闻listview
                                news_mData_zixun=news_getData_zixun((String)mData.get(position).get("Text"));
                                MyAdapterNews news_adapter_zixun = new MyAdapterNews(view.getContext(),news_mData_zixun);
                                news_listview_zixun.setAdapter(news_adapter_zixun);
                                break;
                            case 1:
                                //在论坛页面
                                //修改LinearLayout
                                TextView textview_luntan=(TextView) bbs_select_class_layout.findViewById(R.id.newsandbbs_class_textview);
                                ImageView imageview_luntan=(ImageView) bbs_select_class_layout.findViewById(R.id.newsandbbs_class_imageview);

                                textview_luntan.setText((String)mData.get(position).get("Text"));
                                imageview_luntan.setImageResource((int)mData.get(position).get("Image"));

                                luntan_class_textview.setText((String)mData.get(position).get("Text"));
                                luntan_class_imageview.setImageResource((int)mData.get(position).get("Image"));

                                //重要!!!
                                //还需要修改zixun_select_textview和zixun_select_imageview

                                listview_class.setVisibility(View.INVISIBLE);
                                bbs_class_flag=0;

                                //同时重绘新闻listview
                                news_mData_luntan=news_getData_zixun((String)mData.get(position).get("Text"));
                                MyAdapterNews news_adapter_luntan = new MyAdapterNews(view.getContext(),news_mData_luntan);
                                news_listview_luntan.setAdapter(news_adapter_luntan);
                                break;
                            default:
                                break;
                        }


                    }else if(bbs_sort_flag==1){
                        //判断是在哪一页，然后重绘新闻
                        switch (viewPager.getCurrentItem()) {
                            case 0:
                                //在资讯页面
                                //修改LinearLayout
                                TextView textview_zixun=(TextView) bbs_select_sort_layout.findViewById(R.id.newsandbbs_sort_textview);
                                ImageView imageview_zixun=(ImageView) bbs_select_sort_layout.findViewById(R.id.newsandbbs_sort_imageview);

                                textview_zixun.setText((String)mData.get(position).get("Text"));
                                imageview_zixun.setImageResource((int)mData.get(position).get("Image"));

                                zixun_sort_textview.setText((String)mData.get(position).get("Text"));
                                zixun_sort_imageview.setImageResource((int)mData.get(position).get("Image"));

                                //重要!!!
                                //还需要修改zixun_select_textview和zixun_select_imageview

                                listview_sort.setVisibility(View.INVISIBLE);
                                bbs_sort_flag=0;

                                //同时重绘新闻listview
                                news_mData_zixun=news_getData_zixun((String)mData.get(position).get("Text"));
                                MyAdapterNews news_adapter_zixun = new MyAdapterNews(view.getContext(),news_mData_zixun);
                                news_listview_zixun.setAdapter(news_adapter_zixun);
                                break;
                            case 1:
                                //在论坛页面
                                //修改LinearLayout
                                TextView textview_luntan=(TextView) bbs_select_sort_layout.findViewById(R.id.newsandbbs_sort_textview);
                                ImageView imageview_luntan=(ImageView) bbs_select_sort_layout.findViewById(R.id.newsandbbs_sort_imageview);

                                textview_luntan.setText((String)mData.get(position).get("Text"));
                                imageview_luntan.setImageResource((int)mData.get(position).get("Image"));

                                luntan_sort_textview.setText((String)mData.get(position).get("Text"));
                                luntan_sort_imageview.setImageResource((int)mData.get(position).get("Image"));

                                //重要!!!
                                //还需要修改zixun_select_textview和zixun_select_imageview

                                listview_sort.setVisibility(View.INVISIBLE);
                                bbs_sort_flag=0;

                                //同时重绘新闻listview
                                news_mData_luntan=news_getData_zixun((String)mData.get(position).get("Text"));
                                MyAdapterNews news_adapter_luntan = new MyAdapterNews(view.getContext(),news_mData_luntan);
                                news_listview_luntan.setAdapter(news_adapter_luntan);
                                break;
                            default:
                                break;
                        }

                    }

                }
            });
            return convertView;
        }
    }


    //新闻的listview的适配器
    public class MyAdapterNews extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<Map<String, Object>> mData;



        public MyAdapterNews(Context context, ArrayList<Map<String, Object>> bufData){
            mData=bufData;
            Log.d(TAG,mData.size()+"");
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            NewsViewHolder holder = null;
            if (convertView == null) {

                holder=new NewsViewHolder();

                convertView = mInflater.inflate(R.layout.demo_news, null);

                holder.user_head_imageview=(ImageView) convertView.findViewById(R.id.news_userhead_imageview);
                holder.guanzhu_imageview=(ImageView) convertView.findViewById(R.id.news_follow_imageview);
                holder.like_imageview=(ImageView) convertView.findViewById(R.id.news_like_imageview);
                holder.news_imageview=(ImageView) convertView.findViewById(R.id.news_details_imageview);
                holder.user_name_textview = (TextView)convertView.findViewById(R.id.news_username_textview);
                holder.user_time_textview = (TextView)convertView.findViewById(R.id.news_usertime_textview);
                holder.news_detail_textview = (TextView)convertView.findViewById(R.id.news_detail_textview);
                holder.news_pinglun_textview = (TextView)convertView.findViewById(R.id.news_numofcomment_textview);
                holder.news_yuedu_textview = (TextView)convertView.findViewById(R.id.news_numofread_textview);
                convertView.setTag(holder);

            }else {

                holder = (NewsViewHolder)convertView.getTag();
            }

            //holder.user_head_imageview.setBackgroundResource((Integer)mData.get(position).get("HeadImage"));
            //圆形头像
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_head);
            holder.user_head_imageview.setImageDrawable(new CircleDrawableImageView(bitmap));

            holder.user_head_imageview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"点击了第"+(position+1)+"个新闻的头像",Toast.LENGTH_SHORT).show();
                }
            });


            holder.guanzhu_imageview.setBackgroundResource((Integer)mData.get(position).get("GuanZhuImage"));
            holder.guanzhu_imageview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"点击了第"+(position+1)+"个新闻的关注",Toast.LENGTH_SHORT).show();
                }
            });


            holder.like_imageview.setBackgroundResource((Integer)mData.get(position).get("LikeImage"));
            holder.like_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"点击了第"+(position+1)+"个新闻的收藏",Toast.LENGTH_SHORT).show();
                }
            });

            holder.news_imageview.setBackgroundResource((Integer)mData.get(position).get("NewsImage"));
            holder.news_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"点击了第"+(position+1)+"个新闻的图片",Toast.LENGTH_SHORT).show();
                }
            });

            holder.user_name_textview.setText((String)mData.get(position).get("NameText"));
            holder.user_time_textview.setText((String)mData.get(position).get("TimeText"));
            holder.news_detail_textview.setText((String)mData.get(position).get("DetailText"));
            holder.news_detail_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"点击了第"+(position+1)+"个新闻的文本",Toast.LENGTH_SHORT).show();
                }
            });
            holder.news_pinglun_textview.setText((String)mData.get(position).get("PingLunLiangText"));
            holder.news_yuedu_textview.setText((String)mData.get(position).get("YueDuLiangText"));

            return convertView;
        }

    }
}