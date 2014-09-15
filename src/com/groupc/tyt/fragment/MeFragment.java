package com.groupc.tyt.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.groupc.tyt.R;
import com.groupc.tyt.TytApplication;
import com.groupc.tyt.activity.SplashActivity;
import com.groupc.tyt.activity.SplashActivity2;
import com.groupc.tyt.activity.WantedActivity;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeFragment extends Fragment {
	private ListView listView;
	private ImageView img_tx;
	private String imgUrl;
	private TextView txt_name,txt_xydj,txt_hydj,txt_jf;
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
		txt_name=(TextView)v.findViewById(R.id.textView1);
		txt_xydj=(TextView)v.findViewById(R.id.textView5);
		txt_hydj=(TextView)v.findViewById(R.id.textView6);
		txt_jf=(TextView)v.findViewById(R.id.textView7);
		listView = (ListView) v.findViewById(R.id.listviewme);
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
			txt_xydj.setText(""+User.xydj);
			txt_hydj.setText(""+User.hydj);
			txt_jf.setText(""+User.jf);
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
		List<String> items = new ArrayList<String>();
		items.add("已申请的交易");
		items.add("已发布的交易");
		items.add("十万火急求买");
		items.add("购买积分");
		items.add("我有更好的建议（采纳送积分)");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, items);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
                    switch(position){
                    case 0:
                            Intent localIntent=new Intent(getActivity(),SplashActivity.class);
                            startActivity(localIntent);
                            break;
                    case 1:
                    	Intent localIntent2=new Intent(getActivity(),SplashActivity2.class);
                        startActivity(localIntent2);
                        break;
                    case 2:
                    	Intent localIntent3=new Intent(getActivity(),WantedActivity.class);
                        startActivity(localIntent3);
                    	break;
                    case 3:
                    	Toast.makeText(getActivity(), "功能开发中", Toast.LENGTH_SHORT).show();
                    	break;
                    case 4:
                    	Toast.makeText(getActivity(), "功能开发中", Toast.LENGTH_SHORT).show();
                    	break;
                    }
                    
            }
    });
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
}