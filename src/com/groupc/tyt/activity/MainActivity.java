package com.groupc.tyt.activity;

import com.groupc.tyt.R;
import com.groupc.tyt.common.UpdateAPK;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.fragment.FavorFragment;
import com.groupc.tyt.fragment.HomeFragment;
import com.groupc.tyt.fragment.SetFragment;
import com.groupc.tyt.fragment.MeFragment;
import com.groupc.tyt.util.DummyTabContent;
import com.groupc.tyt.util.TytDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;

public class MainActivity extends FragmentActivity {
	private Fragment currentFragment;
	TabHost tabHost;
	TabWidget tabWidget;
	LinearLayout bottom_layout;
	int CURRENT_TAB = 0;
	HomeFragment homeFragment=new HomeFragment();
//	FavorFragment favorFragment=new FavorFragment();
	LoginActivity loginActivity;
	SetFragment setFragment=new SetFragment();
//	MeFragment meFragment=new MeFragment();
	LinearLayout tabIndicator1;
	LinearLayout tabIndicator2;
	LinearLayout tabIndicator3;
	LinearLayout tabIndicator4;
	LinearLayout tabIndicator5;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SpannableString spannableString = new SpannableString("开卖啦");
		getActionBar().setTitle(spannableString);

		User.getLoginInfo(getApplicationContext());
		findTabView();
		tabHost.setup();
		initTab();
		tabHost.setOnTabChangedListener(tabChangeListener);

//		changeF();
		new UpdateAPK().checkVersion(this);
	}

	public void findTabView() {

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		LinearLayout layout = (LinearLayout) tabHost.getChildAt(0);
		TabWidget tw = (TabWidget) layout.getChildAt(1);

		tabIndicator1 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.main_home, tw, false);

		tabIndicator2 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.main_favor, tw, false);

		tabIndicator3 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.main_publish, tw, false);

		tabIndicator4 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.main_info, tw, false);

		tabIndicator5 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.main_set, tw, false);

	}

	public void initTab() {

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
				Intent i = new Intent(getBaseContext(), PublishActivity.class);
				startActivity(i);
			}
		});
		TabHost.TabSpec tSpecMe = tabHost.newTabSpec("me");
		tSpecMe.setIndicator(tabIndicator4);
		tSpecMe.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecMe);

		TabHost.TabSpec tSpecSet = tabHost.newTabSpec("set");
		tSpecSet.setIndicator(tabIndicator5);
		tSpecSet.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecSet);

	}

	TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {

			if (tabId.equalsIgnoreCase("home")) {
				currentFragment=homeFragment;
				ConstantDef.currenttab=0;
			} else if (tabId.equalsIgnoreCase("favor")) {
				ConstantDef.currenttab=1;
				currentFragment=new FavorFragment();
			} else if (tabId.equalsIgnoreCase("me")) {
				ConstantDef.currenttab=2;
				if (User.uid.equals("-1")) {
					startActivity(new Intent(getApplicationContext(),
							LoginActivity.class));
				} else {
				currentFragment=new MeFragment();
				}
			} else if (tabId.equalsIgnoreCase("set")) {
				ConstantDef.currenttab=3;
				currentFragment=setFragment;
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, currentFragment).commit();
		}

	};
private void changeF(){

	switch (ConstantDef.currenttab) {
	case 0:
		currentFragment=homeFragment;
		tabHost.setCurrentTab(ConstantDef.currenttab);
		break;
	case 1:
		currentFragment=new FavorFragment();
		tabHost.setCurrentTab(ConstantDef.currenttab);
		break;
	case 2:
		currentFragment=new MeFragment();
		tabHost.setCurrentTab(ConstantDef.currenttab+1);
		break;
	case 3:
		currentFragment=setFragment;
		tabHost.setCurrentTab(ConstantDef.currenttab+1);
		break;
	}
	getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, currentFragment).commitAllowingStateLoss();
}
	protected void onResume() {
		super.onResume();
		changeF();
		Log.e("Mainactivity", "onResume");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			final TytDialog dialog = new TytDialog(MainActivity.this,R.style.TytDialogStyle1);
            dialog.show();
			 dialog.setTitleValue("温馨提示");
			 dialog.setBody("您确定要退出程序吗？");
			 dialog.setLeftButton("我确定", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			 dialog.setRightButton("按错了", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

}
