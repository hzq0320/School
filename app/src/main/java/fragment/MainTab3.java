package fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.school.R;


@SuppressLint("NewApi")
public class MainTab3 extends Fragment
{
	//Fragment创建时调用onCreateView，返回一个view
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_main_tab_3, container, false);


		return view;
	}

}
