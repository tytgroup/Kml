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
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

public class Login_Fragment extends Fragment {
	android.support.v4.app.FragmentTransaction ft;
	ImageButton LoginButton;
	Me_Fragment meFragment;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		}
	

public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	View v= inflater.inflate(R.layout.login,container,false);
	/*LoginButton = (ImageButton)getActivity().findViewById(R.id.LoginButton);
	LoginButton.setOnClickListener(new Button.OnClickListener(){

		public void onClick(View arg0) {
			


			
		}
		
	}
	);	*/
	return v;
		
		}



public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	super.onCreateOptionsMenu(menu, inflater);
	menu.add(0, 1,0,"ע��").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    
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