package com.groupc.tyt.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class User {

	public static String uid = "-1";
	public static String name;
	public static String passWord;
	public static String uno;
	public static String phone;
	public static String email;
	public static String szx;
	public static String tx;
//	public static boolean isvip;
	public static boolean rzjg;
	public static double jf;
	public static double hydj;
	public static double xydj;

	public static void saveLoginInfo(Context context) {
		// 获取SharedPreferences对象
		@SuppressWarnings("static-access")
		SharedPreferences sharedPre = context.getSharedPreferences("config",
				context.MODE_PRIVATE);
		// 获取Editor对象
		Editor editor = sharedPre.edit();
		editor.putString("uid", uid);
		editor.putString("name", name);
//		editor.putString("passWord",passWord);
		editor.putString("uno", uno);
		editor.putString("phone", phone);
		editor.putString("email", email);
//		editor.putString("szx", szx);
		editor.putString("tx", tx);
//		editor.putBoolean("isvip", isvip);
		editor.putBoolean("rzjg", rzjg);
		editor.putFloat("jf", (float)jf);
		editor.putFloat("hydj", (float)hydj);
		editor.putFloat("xydj", (float)xydj);		
		editor.commit();
	}
	
	public static void getLoginInfo(Context context) {
		// 获取SharedPreferences对象
		@SuppressWarnings("static-access")
		SharedPreferences sharedPre = context.getSharedPreferences("config",
				context.MODE_PRIVATE);

      uid=sharedPre.getString("uid", "-1");
      
      name=sharedPre.getString("name", "");
      uno=sharedPre.getString("uno", "");
      phone=sharedPre.getString("phone", "");
      email=sharedPre.getString("email", "");
      tx=sharedPre.getString("tx", "");
      rzjg=sharedPre.getBoolean("rzjg", false);
      jf=(double)sharedPre.getFloat("jf", 0);
      hydj=(double)sharedPre.getFloat("hydj", 0);
      xydj=(double)sharedPre.getFloat("xydj", 0);
	}
	public static void logOut(Context context){
		@SuppressWarnings({ "static-access" })
		SharedPreferences sharedPre = context.getSharedPreferences("config",
				context.MODE_PRIVATE);
		//清除
		sharedPre.edit().clear().commit();
	}
	
}
