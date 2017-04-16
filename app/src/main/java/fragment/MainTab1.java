package fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.school.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import image.CircleDrawableImageView;
import scroll.MyScrollView;


@SuppressLint("NewApi")

//第一页
//ScrollView嵌套底部新闻的viewpager未成功，只设置了初始滚动条的位置
//可以仿照微博，点击即进入

public class MainTab1 extends Fragment
{
	//Fragment创建时调用onCreateView，返回一个view
	private View view;

	//各功能的imageview
	private ImageView lost_and_found_imageview;
	private ImageView second_hand_imageview;
	private ImageView make_friends_imageview;
	private ImageView more_imageview;

	//新闻的listview
	private ListView listView;
	//新闻的内容
	private ArrayList<Map<String, Object>> news_Data;

	//以下是Scroll监听
	//自定义的MyScrollView
	private MyScrollView myScrollView;
	//用来计算新闻标题栏是否被滑动到顶端
	private LinearLayout mBarLayout;


	//下面这些都是用来设置滚动条位置
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

	//TAG
	private final static String TAG="MainTab1";


	//判断ScrollView是否滑到顶部
	private int flag=0;
	private int flag1=0;
	private int maxhei=0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_main_tab_1, container, false);


		//下面这一部分仅用来设置scrollbar初始位置，未实现滑动切换的功能(viewpager)
		scrollbar = (ImageView)view.findViewById(R.id.mainpage_newstitle_scrollbar_imageview);
		// 获取滚动条的宽度
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


		//各主要功能监听（活动跳转而已）
		//为每个功能设置监听事件
		lost_and_found_imageview = (ImageView)view.findViewById(R.id.mainpage_lost_and_found_imageview);
		if(lost_and_found_imageview==null){
			Log.d(TAG,"lost_and_found_imageview is null");
		}else{
			lost_and_found_imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent("android.intent.action.School_Lost_And_Found");
					intent.addCategory("android.intent.category.School_LostAndFound");
					startActivityForResult(intent, 1);
				}
			});
		}

		second_hand_imageview = (ImageView)view.findViewById(R.id.mainpage_second_hand_imageview);
		if(second_hand_imageview==null){
			Log.d(TAG,"second_hand_imageview is null");
		}else{
			second_hand_imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent("android.intent.action.School_Second_Hand");
					intent.addCategory("android.intent.category.School_SecondHand");
					startActivityForResult(intent, 1);
				}
			});
		}

		make_friends_imageview = (ImageView)view.findViewById(R.id.mainpage_make_friends_imageview);
		if(make_friends_imageview==null){
			Log.d(TAG,"make_friends_imageview is null");
		}else{
			make_friends_imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent("android.intent.action.School_Make_Friends");
					intent.addCategory("android.intent.category.School_MakeFriends");
					startActivityForResult(intent, 1);
				}
			});
		}

		more_imageview = (ImageView)view.findViewById(R.id.mainpage_more_imageview);
		if(more_imageview==null){
			Log.d(TAG,"more_imageview is null");
		}else{
			more_imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent("android.intent.action.School_More");
					intent.addCategory("android.intent.category.School_More");
					startActivityForResult(intent, 1);
				}
			});
		}

		//为新闻适配
		listView=(ListView)view.findViewById(R.id.mainpage_news_listview);
		news_Data=news_getData_zixun("资讯_我的关注");
		MyAdapterNews news_adapter = new MyAdapterNews(view.getContext(),news_Data);
		listView.setAdapter(news_adapter);
		//设置listview高度，使其不止显示一行
		setListViewHeightBasedOnChildren(listView);

		//为ScrollView设置监听
		myScrollView = (MyScrollView)view.findViewById(R.id.mainpage_scrollview);
		mBarLayout = (LinearLayout) view.findViewById(R.id.mainpage_newstitle_layout);
		//mTopBarLayout = (LinearLayout) view.findViewById(R.id.top_newstitle_layout);

		myScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
			@Override
			public void onScroll(int scrollY) {
				int mBuyLayout2ParentTop = Math.max(scrollY,mBarLayout.getTop());
				//这个flag1只一次有效，用于获取当前新闻标签的顶部高度，当上滑大于此高度时进行Fragment的更换。
				if(flag1==0){
					maxhei=mBuyLayout2ParentTop;
					flag1=1;
				}
				//mTopBarLayout.layout(0, mBuyLayout2ParentTop, mTopBarLayout.getWidth(), mBuyLayout2ParentTop + mTopBarLayout.getHeight());
				if(mBuyLayout2ParentTop>maxhei){
					flag=1;

					//隐藏底部标题栏
					//LinearLayout bottomly=(LinearLayout)getActivity().findViewById(R.id.bottom_bar);
					//bottomly.setVisibility(View.INVISIBLE);

					FragmentManager fm = getFragmentManager();
					FragmentTransaction transaction = fm.beginTransaction();
					//创建一个WeChatFragment对象
					News_Fragment news_fragment=new News_Fragment();
					//transaction.replace(A, B);-----用B把A替换
					transaction.replace(R.id.fragment_content,news_fragment);
					transaction.commit();

				}
			}
		});


		return view;

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

	/**
	 * 动态设置ListView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if(listView == null) return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
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
