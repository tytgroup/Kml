package com.groupc.tyt.fragment;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.Reg_Activity;

import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

public class Login_Fragment extends Fragment {
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login,container,false);
		}



public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	super.onCreateOptionsMenu(menu, inflater);
	menu.add(0, 1,0,"зЂВс").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    
        return;
    }
public boolean onOptionsItemSelected(MenuItem item) {
    // TODO Auto-generated method stub
	switch(item.getItemId()){  
    case  1:  
    	 Intent i=new Intent(getActivity().getBaseContext(),Reg_Activity.class) ;  
         startActivity(i) ;  
         break ; 
	}
	   return true;
    
}

}