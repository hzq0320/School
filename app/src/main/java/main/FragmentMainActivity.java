package main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.hdu.school.R;

import fragment.MainTab1;
import fragment.MainTab2;
import fragment.MainTab3;


//可能需要修改build.gradle文件：http://blog.csdn.net/u010670151/article/details/52880687

public class FragmentMainActivity extends Activity implements OnClickListener
{


	//三个Fragment
	private static MainTab1 mTab01;
	private static MainTab2 mTab02;
	private static MainTab3 mTab03;


	//底部三个ImageView
	private static ImageView mBottomMain;
	private static ImageView mBottomMessage;
	private static ImageView mBottomSetting;

	//Fragment的管理器
	public static FragmentManager fragmentManager;

	public static final String TAG = "FragmentMainActivity";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initViews();
		fragmentManager = getFragmentManager();


		/*可用
		//获取屏幕高度和宽度
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		int wid = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		int hei = dm.heightPixels; // 屏幕高（dip，如：533dip）
		//输出到日志：
		// Log.e(TAG + " DisplayMetrics(250)", "screenWidthDip=" + hei + "; screenHeightDip=" + wid);
		//显示到屏幕
		Toast.makeText(this, "屏幕的宽为" + wid + "，高为" + hei, Toast.LENGTH_SHORT).show();*/

		//设置当前使用的是第一个Fragment
		setTabSelection(0);

	}


	//初始化工作
	private void initViews()
	{
		mBottomMain = (ImageView) findViewById(R.id.bottom_btn_main);
		mBottomMessage = (ImageView) findViewById(R.id.bottom_btn_message);
		mBottomSetting = (ImageView) findViewById(R.id.bottom_btn_setting);

		mBottomMain.setOnClickListener(this);
		mBottomMessage.setOnClickListener(this);
		mBottomSetting.setOnClickListener(this);
	}

	@Override
	//判断点击行为
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.bottom_btn_main:
			setTabSelection(0);
			break;
		case R.id.bottom_btn_message:
			setTabSelection(1);
			break;
		case R.id.bottom_btn_setting:
			setTabSelection(2);
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	public static void setTabSelection(int index)
	{
		resetBtn();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index)
		{
		case 0:
			((ImageView) mBottomMain.findViewById(R.id.bottom_btn_main))
					.setImageResource(R.drawable.ic_main_selected);
			if (mTab01 == null)
			{
				mTab01 = new MainTab1();
				transaction.add(R.id.fragment_content, mTab01);
			} else
			{
				transaction.show(mTab01);
			}
			break;
		case 1:
			((ImageView) mBottomMessage.findViewById(R.id.bottom_btn_message))
					.setImageResource(R.drawable.ic_message_selected);
			if (mTab02 == null)
			{
				mTab02 = new MainTab2();
				transaction.add(R.id.fragment_content, mTab02);
			} else
			{
				transaction.show(mTab02);
			}
			break;
		case 2:
			((ImageView) mBottomSetting.findViewById(R.id.bottom_btn_setting))
					.setImageResource(R.drawable.ic_setting_selected);
			if (mTab03 == null)
			{
				mTab03 = new MainTab3();
				transaction.add(R.id.fragment_content, mTab03);
			} else
			{
				transaction.show(mTab03);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}


	//重新设置底部三个ImageView
	private static void resetBtn()
	{
		((ImageView) mBottomMain.findViewById(R.id.bottom_btn_main))
				.setImageResource(R.drawable.ic_main_unselected);
		((ImageView) mBottomMessage.findViewById(R.id.bottom_btn_message))
				.setImageResource(R.drawable.ic_message_unselected);
		((ImageView) mBottomSetting.findViewById(R.id.bottom_btn_setting))
				.setImageResource(R.drawable.ic_setting_unselected);
	}


	@SuppressLint("NewApi")
	private static void hideFragments(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
	}

}
