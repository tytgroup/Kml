package com.groupc.tyt.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.groupc.tyt.R;
import com.groupc.tyt.TytApplication;
import com.groupc.tyt.activity.AdviceActivity;
import com.groupc.tyt.activity.LoginActivity;
import com.groupc.tyt.activity.SplashActivity;
import com.groupc.tyt.activity.SplashActivity2;
import com.groupc.tyt.adapter.AnimateFirstDisplayListener;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MeFragment extends Fragment implements OnClickListener{
	private ImageView img_tx;
	private String imgUrl;
	private TextView txt_name,txt_xydj,txt_hydj,txt_jf;
	private LinearLayout haveApply,havePublish,advice;
	private Button logout;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	String saveDirPath = Environment.getExternalStorageDirectory().getPath()+"/TytImage/";
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.me,container,false);
		img_tx=(ImageView)v.findViewById(R.id.imageView1);
		txt_name=(TextView)v.findViewById(R.id.textView8);
		txt_xydj=(TextView)v.findViewById(R.id.textView5);
		txt_hydj=(TextView)v.findViewById(R.id.textView6);
		txt_jf=(TextView)v.findViewById(R.id.textView7);
		haveApply=(LinearLayout) v.findViewById(R.id.haveapplyed);
		havePublish=(LinearLayout) v.findViewById(R.id.havepublish);
		advice=(LinearLayout) v.findViewById(R.id.advice);
		logout=(Button) v.findViewById(R.id.logout);
		if(User.uid.equals("-1")){
			Toast.makeText(getActivity(), "您还没有登陆！", Toast.LENGTH_SHORT).show();
		}
		else{
			File imgDir = new File(saveDirPath+User.tx+".jpg");
			if(!imgDir.exists()){
			TytApplication.initImageLoader(getActivity());
			ini();
			imgUrl=ConstantDef.BaseImageUil+User.tx+".jpg";
			imageLoader.displayImage(imgUrl, img_tx, options, animateFirstListener);
			}
			else{
				Bitmap bitmap = getLoacalBitmap(saveDirPath+User.tx+".jpg");
				img_tx.setImageBitmap(bitmap);
			}
			txt_name.setText(User.name);
			int xydj=(int)User.xydj/50;
			if(User.xydj%50>0)
				xydj++;
			txt_xydj.setText("等级"+xydj);
			txt_hydj.setText("等级"+(int)User.hydj);
			txt_jf.setText("等级"+(int)User.jf);
		}
		init();	
		return v;
	}
	
	 public static Bitmap getLoacalBitmap(String url) {
         try {
              FileInputStream fis = new FileInputStream(url);
              return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }
	 }
	private void init() {
		haveApply.setOnClickListener(this);
		havePublish.setOnClickListener(this);
		advice.setOnClickListener(this);
		logout.setOnClickListener(this);
   }
    public void ini(){
    	imageLoader = ImageLoader.getInstance();
      options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.im_chatroom_msg_loading)
	.showImageForEmptyUri(R.drawable.ic_launcher)
	.showImageOnFail(R.drawable.im_chatroom_msg_failed)
	.resetViewBeforeLoading(true)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.considerExifParams(true)
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//缩放图片
	.delayBeforeLoading(2000)//延时
	.displayer(new RoundedBitmapDisplayer(70))
	.displayer(new FadeInBitmapDisplayer(100))
	.build();
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id=v.getId();
		switch(id){
        case R.id.haveapplyed:
                Intent localIntent=new Intent(getActivity(),SplashActivity.class);
                startActivity(localIntent);
                ConstantDef.currenttab=2;
                break;
        case R.id.havepublish:
        	Intent localIntent2=new Intent(getActivity(),SplashActivity2.class);
            startActivity(localIntent2);
            ConstantDef.currenttab=2;
            break;
        case R.id.advice:
        	Intent localIntent3=new Intent(getActivity(),AdviceActivity.class);
            startActivity(localIntent3);
            ConstantDef.currenttab=2;
        	break;
        case R.id.logout:
        	User.uid="-1";
        	Intent localIntent4=new Intent(getActivity(),LoginActivity.class);
            startActivity(localIntent4);
            ConstantDef.currenttab=0;
        	break;

        }
	}
}