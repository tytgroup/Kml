package com.groupc.tyt.fragment;

import com.groupc.tyt.R;
import com.groupc.tyt.activity.MainActivity;
import com.groupc.tyt.activity.WelcomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SetFragment extends Fragment {

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.set,container,false);
		ImageView logo = (ImageView) v.findViewById(R.id.thx);
        Animation logo_animation = AnimationUtils.loadAnimation(getActivity(), 
        		R.anim.alpha_in);
        logo.setAnimation(logo_animation);
        logo_animation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}public void onAnimationEnd(Animation animation) {
				
			}
        });
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
}