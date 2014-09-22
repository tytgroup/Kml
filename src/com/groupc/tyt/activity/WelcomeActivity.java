package com.groupc.tyt.activity;

import com.groupc.tyt.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomelogo);
		ActionBar actionBar = getActionBar(); 
        actionBar.hide();
		ImageView logo = (ImageView) findViewById(R.id.logo2);
        Animation logo_animation = AnimationUtils.loadAnimation(WelcomeActivity.this, 
        		R.anim.alpha_in);
        logo.setAnimation(logo_animation);
        logo_animation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent it = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(it);
		//		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				WelcomeActivity.this.finish();
			}
		});
	}
}
