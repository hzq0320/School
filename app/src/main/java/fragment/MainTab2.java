package fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.school.R;


@SuppressLint("NewApi")
public class MainTab2 extends Fragment
{
	//Fragment创建时调用onCreateView，返回一个view
	private View view;

	//可扩展的listview
	ExpandableListView list;

	public static final String TAG = "MainTab2";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_main_tab_2, container, false);

		list=(ExpandableListView)view.findViewById(R.id.messagepage_friends_expandablelistview);
		//创建一个BaseExpandableListAdapter对象
		final ExpandableListAdapter adapter=new BaseExpandableListAdapter() {
			//设置组视图的图片
			int[] logos = new int[]{R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return};
			//设置组视图的显示文字
			private String[] category = new String[]{"系统通知", "陌生人消息", "好友消息", "曾强"};
			//子视图显示文字
			private String[][] subcategory = new String[][]{
					{"1", "2", "3", "4", "5", "6", "7"},
					{"A", "B", "C", "D", "E", "F", "G"},
					{"Z", "X", "Y", "W", "D", "F", "G"},
					{"S", "J", "L", "H", "O", "P", "Q"}

			};
			//子视图图片
			public int[][] sublogos = new int[][]{
					{R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return,
							R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return},
					{R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return,
							R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return},
					{R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return,
							R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return},
					{R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return,
							R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return, R.drawable.ic_return}};
			//定义一个显示文字信息的方法
			TextView getTextView(){
				AbsListView.LayoutParams lp=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150);
				TextView textView=new TextView(view.getContext());
				//设置 textView控件的布局
				textView.setLayoutParams(lp);
				//设置该textView中的内容相对于textView的位置
				textView.setGravity(Gravity.CENTER_VERTICAL);
				//设置txtView的内边距
				textView.setPadding(100, 0, 0, 0);
				//设置文本颜色
				textView.setTextColor(Color.BLACK);
				return textView;
				//return null;

			}
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}
			//取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
									 View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				//定义一个LinearLayout用于存放ImageView、TextView
				LinearLayout ll=new LinearLayout(view.getContext());
				//设置子控件的显示方式为水平
				ll.setOrientation(LinearLayout.HORIZONTAL);
				//定义一个ImageView用于显示列表图片
				ImageView logo=new ImageView(view.getContext());
				logo.setPadding(50, 0, 0, 0);
				//设置logo的大小(50（padding）+46=96)
				AbsListView.LayoutParams lparParams=new AbsListView.LayoutParams(96,46);
				logo.setLayoutParams(lparParams);
				logo.setImageResource(logos[groupPosition]);
				ll.addView(logo);
				TextView textView=getTextView();
				textView.setTextSize(20);
				textView.setText(category[groupPosition]);
				ll.addView(textView);
				return ll;
				//return null;
			}

			//取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			//取得分组数
			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return category.length;
			}
			//取得与给定分组关联的数据
			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return category[groupPosition];
			}
			//取得指定分组的子元素数.
			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return subcategory[groupPosition].length;
			}
			//取得显示给定分组给定子位置的数据用的视图
			@Override
			public View getChildView(int groupPosition, int childPosition,
									 boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				//定义一个LinearLayout用于存放ImageView、TextView
				LinearLayout ll=new LinearLayout(view.getContext());
				//设置子控件的显示方式为水平
				ll.setOrientation(LinearLayout.HORIZONTAL);
				//定义一个ImageView用于显示列表图片
				ImageView logo=new ImageView(view.getContext());
				logo.setPadding(0, 0, 0, 0);
				//设置logo的大小
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(40, 40);
				logo.setLayoutParams(lp);
				logo.setImageResource(sublogos[groupPosition][childPosition]);
				ll.addView(logo);
				TextView textView=getTextView();
				textView.setText(subcategory[groupPosition][childPosition]);
				ll.addView(textView);
				return ll;
				//return null;
			}
			//取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）.
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return subcategory[groupPosition][childPosition];
			}

		};
		list.setAdapter(adapter);
		//为ExpandableListView的子列表单击事件设置监听器
		list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(view.getContext(), "你单击了："
						+adapter.getChild(groupPosition, childPosition), Toast.LENGTH_LONG).show();
				return true;
			}
		});

		return view;


	}


}
