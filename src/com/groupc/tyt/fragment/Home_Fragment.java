package com.groupc.tyt.fragment;



import java.util.ArrayList;
import java.util.HashMap;
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
	private ListView homelist;
    private ShopListAdapter shopListAdapter;
	
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
		homelist = (ListView)v.findViewById(R.id.homelist);
		
		bicycle.setOnClickListener(new ImageButton.OnClickListener(){
        public void onClick(View v) {
        	
        }});
		ArrayList<HashMap<String, String>> testlist = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		
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
