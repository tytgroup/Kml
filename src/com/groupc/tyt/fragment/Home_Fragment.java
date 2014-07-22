package com.groupc.tyt.fragment;



import java.util.ArrayList;
import java.util.HashMap;

import com.groupc.tyt.R;

import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;


public class Home_Fragment extends Fragment {
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_first,container,false);
		/*images=new int[]{R.drawable.img1, R.drawable.img2,R.drawable.img3,
		        R.drawable.img4,R.drawable.img5,R.drawable.img6};
		GridView gridview = (GridView)  getActivity().findViewById(R.id.gridView1);
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 6; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", images[i]);
			lstImageItem.add(map);
		}
		SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), 
                lstImageItem,// 数据源
                R.layout.items,// 显示布局
                new String[]{"ItemImage","ItemText"}, 
                new int[] { R.id.itemImage }); 
        gridview.setAdapter(saImageItems);*/

       return v;
		
	}
	
public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	super.onCreateOptionsMenu(menu, inflater);
	getActivity().getMenuInflater().inflate(R.menu.options_menu, menu);  
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchableInfo info=searchManager.getSearchableInfo(getActivity().getComponentName());
        searchView.setSearchableInfo(info);
        searchView.setIconifiedByDefault(false); 
        return;
    }
}
