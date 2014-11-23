package com.groupc.tyt.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groupc.tyt.R;
import com.groupc.tyt.constant.ConstantDef;
import com.groupc.tyt.constant.User;
import com.groupc.tyt.util.TytDialog;

public class RegisterFirstStepActivity extends Activity{

	private String uno,name,psd,phone,email;
	private EditText stunum;
	private EditText usrname;
	private EditText usrpsw;
	private EditText usrpsw2;
	private EditText usrphone;
	private EditText usremail;
	private Button btn_nextstep;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();

		SpannableString spannableString = new SpannableString("注册");
		getActionBar().setTitle(spannableString);
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.register_firststep_activity); 
		stunum = (EditText)findViewById(R.id.stunum);
		usrname = (EditText)findViewById(R.id.usrname);
		usrpsw = (EditText)findViewById(R.id.usrpsw);
		usrpsw2 = (EditText)findViewById(R.id.usrpsw2);
		usrphone = (EditText)findViewById(R.id.usrphone);
		usremail = (EditText)findViewById(R.id.email);
		btn_nextstep= (Button)findViewById(R.id.btn_nextstep);
		
		btn_nextstep.setOnClickListener(new Button.OnClickListener(){//创建监听  
            public void onClick(View v) {    
            	uno=stunum.getText().toString();
            	name = usrname.getText().toString();
            	psd = usrpsw.getText().toString();
            	phone = usrphone.getText().toString();
            	email = usremail.getText().toString();
            	if (registerIsSuccess()) {
            		User.name=name;
            		User.uno=uno;
            		User.passWord=psd;
            		User.phone=phone;
            		User.email=email;
            		startActivity(new Intent(RegisterFirstStepActivity.this,RegisterSecondStepActivity.class));
            		finish();
            }    
            }
        });   
	}
	
	private boolean registerIsSuccess(){
		
    	//获取用户输入的信息
    	
    	String password1=usrpsw.getText().toString();
    	String password2=usrpsw2.getText().toString();
    	String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        
    	if("".equals(uno)){
    		Toast.makeText(RegisterFirstStepActivity.this, "请输入学号!", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if("".equals(name)){
        		//用户输入用户名为空
        		Toast.makeText(RegisterFirstStepActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
        		return false;
    	}else if("".equals(password1)){
    		//密码不能为空
    		Toast.makeText(RegisterFirstStepActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if(!password1.equals(password2)){
    		Toast.makeText(RegisterFirstStepActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if(!phone.matches(telRegex)){
    		Toast.makeText(RegisterFirstStepActivity.this, "请输入正确手机号!", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	return true;
    }
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	        	final TytDialog dialog = new TytDialog(RegisterFirstStepActivity.this,R.style.TytDialogStyle2);
	             dialog.show();
				 dialog.setTitleValue("温馨提示");
				 dialog.setBody("您确定要退出注册吗？");
				 dialog.setLeftButton("我确定", new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ConstantDef.currenttab=0;
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
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			final TytDialog dialog = new TytDialog(RegisterFirstStepActivity.this,R.style.TytDialogStyle2);
             dialog.show();
			 dialog.setTitleValue("温馨提示");
			 dialog.setBody("您确定要退出注册吗？");
			 dialog.setLeftButton("我确定", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ConstantDef.currenttab=0;
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
