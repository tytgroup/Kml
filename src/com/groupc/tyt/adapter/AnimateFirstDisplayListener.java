package com.groupc.tyt.adapter;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

public class AnimateFirstDisplayListener implements ImageLoadingListener{

	@Override
	public void onLoadingStarted(String imageUri, View view) {
		// TODO Auto-generated method stub
		Log.e("loadstart", "viewid--->"+view.getId());
	}

	@Override
	public void onLoadingFailed(String imageUri, View view,
			FailReason failReason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingComplete(String imageUri, View view,
			Bitmap loadedImage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		// TODO Auto-generated method stub
		
	}
	
}
