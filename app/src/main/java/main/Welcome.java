package main;

/**
 * Created by 须儿胡 on 2017/4/15.
 * 说明：1.未详细设计启动页面
 * 2.未判断是否是用户第一次启动应用（是否加载新手引导页）
 * 3.未实现跳过启动动画的功能
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.hdu.school.R;

import image.LoadImagesTask;


//启动页，可在layout/welcome.xml中修改。此处仅为显示一张网络图片
public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //默认
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);          //隐藏系统自带标题栏
        //确定布局
        setContentView(R.layout.page_welcome);

        //隐藏标题栏
        getSupportActionBar().hide();

        //加载网络图片需要网络权限
        //显示图片
        String http = "http://b.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=7e7b569179310a55c424d9f28f7e2494/7aec54e736d12f2e307562024fc2d56285356864.jpg";
        ImageView imageView = (ImageView) findViewById(R.id.page_welcome_imageview);
        new LoadImagesTask(imageView).execute(http);

        //休眠1.5s并转到第一个页面
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动新的活动
                Intent intent = new Intent("android.intent.action.Main_FragmentActivity");
                intent.addCategory("android.intent.category.Main_FragmentActivity");
                startActivityForResult(intent,1);
                finish();
            }
        },5500);
    }
}