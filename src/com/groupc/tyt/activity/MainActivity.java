package com.groupc.tyt.activity;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.fragment.FavorFragment;
import com.groupc.tyt.fragment.HomeFragment;
import com.groupc.tyt.fragment.LoginFragment;
import com.groupc.tyt.fragment.SetFragment;
import com.groupc.tyt.fragment.MeFragment;
import com.groupc.tyt.util.DummyTabContent;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;



public class MainActivity extends FragmentActivity {

	TabHost tabHost;
	TabWidget tabWidget; 
	LinearLayout bottom_layout;
	int CURRENT_TAB = 0;	
	HomeFragment homeFragment;
	FavorFragment favorFragment;
	//Pub_Fragment pubFragment;
	LoginFragment loginFragment;
	SetFragment setFragment;
	MeFragment meFragment;
    android.support.v4.app.FragmentTransaction ft;
	LinearLayout tabIndicator1;
	LinearLayout tabIndicator2;
	LinearLayout tabIndicator3;
	LinearLayout tabIndicator4;
	LinearLayout tabIndicator5;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
		SpannableString spannableString = new SpannableString("开卖啦");
	    getActionBar().setTitle(spannableString);
        findTabView();
        tabHost.setup();
        
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener(){
			public void onTabChanged(String tabId) {
				
				android.support.v4.app.FragmentManager fm =  getSupportFragmentManager();
				homeFragment = (HomeFragment) fm.findFragmentByTag("home");
				favorFragment = (FavorFragment) fm.findFragmentByTag("favor");
				//pubFragment = (Pub_Fragment) fm.findFragmentByTag("pub");
				loginFragment = (LoginFragment) fm.findFragmentByTag("login");
				setFragment = (SetFragment) fm.findFragmentByTag("set");
				meFragment = (MeFragment) fm.findFragmentByTag("me");
				ft = fm.beginTransaction();
				
				if(homeFragment!=null)
					ft.detach(homeFragment);
				
				if(favorFragment!=null)
					ft.detach(favorFragment);
				
				//if(pubFragment!=null)
					//ft.detach(pubFragment);
				
				if(loginFragment!=null)
					ft.detach(loginFragment);
				
				if(setFragment!=null)
					ft.detach(setFragment);
				
				if(meFragment!=null)
					ft.detach(meFragment);
				
				
				if(tabId.equalsIgnoreCase("home")){
					isTabHome();
					CURRENT_TAB = 1;
					
				}else if(tabId.equalsIgnoreCase("favor")){	
					isTabFavor();
					CURRENT_TAB = 2;
					
				}/*else if(tabId.equalsIgnoreCase("pub")){	
					isTabPub();
					CURRENT_TAB = 3;
				
				}*/else if(tabId.equalsIgnoreCase("login")||tabId.equalsIgnoreCase("me")){	
					isTabMe();
					CURRENT_TAB = 4;
					
				}else if(tabId.equalsIgnoreCase("set")){	
					isTabSet();
					CURRENT_TAB = 5;
				}else{
					switch (CURRENT_TAB) {
					case 1:
						isTabHome();
						break;
					case 2:
						isTabFavor();
						break;
					/*case 3:
						isTabPub();
						break;*/
					case 4:
						isTabMe();
						break;
					case 5:
						isTabSet();
						break;
					default:
						isTabHome();
						break;
					}		
					
				}
					ft.commit();	
			}
        	
        };
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(tabChangeListener);
        initTab();
        tabHost.setCurrentTab(0);

    }
    
     public void isTabHome(){
    	
    	if(homeFragment==null){		
			ft.add(R.id.realtabcontent,new HomeFragment(), "home");						
		}else{
			ft.attach(homeFragment);						
		}
    }
    
    public void isTabFavor(){
    	
    	if(favorFragment==null){
			ft.add(R.id.realtabcontent,new FavorFragment(), "favor");						
		}else{
			ft.attach(favorFragment);						
		}
    }
    
     /*public void isTabPub(){
    	
    	if(pubFragment==null){		
			ft.add(R.id.realtabcontent,new Pub_Fragment(), "pub");						
		}else{
			ft.attach(pubFragment);						
		}
    }*/

     public void isTabMe(){
     	if(User.uid.equals("-1")){
     	if(loginFragment==null){
 			ft.add(R.id.realtabcontent,new LoginFragment(), "login");						
 		}else{
 			ft.attach(loginFragment);	
 		}
     }  
     else{
    	 if(meFragment==null){
 			ft.add(R.id.realtabcontent,new MeFragment(), "me");						
 		}else{
 			ft.attach(meFragment);						
 		}
     }
     }
    
    public void isTabSet(){
    	
    	if(setFragment==null){
			ft.add(R.id.realtabcontent,new SetFragment(), "set");						
		}else{
			ft.attach(setFragment);						
		}
    }
    
   public void findTabView(){
    	
    	 tabHost = (TabHost) findViewById(android.R.id.tabhost);
         tabWidget = (TabWidget) findViewById(android.R.id.tabs);
         LinearLayout layout = (LinearLayout)tabHost.getChildAt(0);
         TabWidget tw = (TabWidget)layout.getChildAt(1);
         
         tabIndicator1 = (LinearLayout) LayoutInflater.from(this)
         		.inflate(R.layout.main_home, tw, false);
         
        
         tabIndicator2 = (LinearLayout) LayoutInflater.from(this)
        		 .inflate(R.layout.main_favor, tw, false);
         
         
         tabIndicator3 = (LinearLayout) LayoutInflater.from(this)
        		 .inflate(R.layout.main_publish, tw, false);
         
         
         tabIndicator4 = (LinearLayout) LayoutInflater.from(this)
        		 .inflate(R.layout.main_info, tw, false);
         
        
         tabIndicator5 = (LinearLayout) LayoutInflater.from(this)
        		 .inflate(R.layout.main_set, tw, false);
         
         
    }
    
   public void initTab(){
    	
        TabHost.TabSpec tSpecHome = tabHost.newTabSpec("home");
        tSpecHome.setIndicator(tabIndicator1);        
        tSpecHome.setContent(new DummyTabContent(getBaseContext()));
        tabHost.addTab(tSpecHome);
        
        TabHost.TabSpec tSpecFavor = tabHost.newTabSpec("favor");
        tSpecFavor.setIndicator(tabIndicator2);        
        tSpecFavor.setContent(new DummyTabContent(getBaseContext()));
        tabHost.addTab(tSpecFavor);
        
        TabHost.TabSpec tSpecPub = tabHost.newTabSpec("pub");
        tSpecPub.setIndicator(tabIndicator3);        
        tSpecPub.setContent(new DummyTabContent(getBaseContext()));
        tabHost.addTab(tSpecPub);
        tabIndicator3.setOnClickListener(new OnClickListener() {
 			public void onClick(View v) {
 				Intent i=new Intent(getBaseContext(),PubActivity.class) ;  
 		         startActivity(i) ; 
 			}});
        TabHost.TabSpec tSpecMe= tabHost.newTabSpec("login");
        tSpecMe.setIndicator(tabIndicator4);      
        tSpecMe.setContent(new DummyTabContent(getBaseContext()));
        tabHost.addTab(tSpecMe);
        
        TabHost.TabSpec tSpecSet= tabHost.newTabSpec("set");
        tSpecSet.setIndicator(tabIndicator5);        
        tSpecSet.setContent(new DummyTabContent(getBaseContext()));
        tabHost.addTab(tSpecSet);
        
    }
    
}
