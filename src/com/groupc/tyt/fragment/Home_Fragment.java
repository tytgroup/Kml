package com.groupc.tyt.fragment;



import java.util.List;

import com.groupc.tyt.R;
import com.groupc.tyt.util.ShopService;
import com.groupc.tyt.util.Shop;
import com.groupc.tyt.util.ShopListAdapter;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;


public class Home_Fragment extends Fragment {

	
	private ImageButton bicycle;
	private ImageButton book;
	private ImageButton electronic;
	private ImageButton sport;
	private ImageButton more;
	private ImageButton hotlist;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
    
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_first,container,false);
		
		
		bicycle = (ImageButton)v.findViewById(R.id.imageButton1);
		book = (ImageButton)v.findViewById(R.id.imageButton2);
		electronic = (ImageButton)v.findViewById(R.id.imageButton3);
	    sport  = (ImageButton)v.findViewById(R.id.imageButton4);
		more = (ImageButton)v.findViewById(R.id.imageButton5);
		hotlist = (ImageButton)v.findViewById(R.id.imageButton6);
		
		
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
