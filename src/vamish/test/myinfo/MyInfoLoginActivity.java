package vamish.test.myinfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import vamish.test.BaseActivity;
import vamish.test.jatesting.R;
import vamish.test.jatesting.R.layout;
import vamish.test.util.CheckCode;

public class MyInfoLoginActivity extends BaseActivity{
	private EditText username;
	private EditText password;
	private EditText checkcode;
	
	private ImageView checkicon;
	
	private Button confirm;
	
	HttpClient hClient;
	
	Handler handler = new Handler(){
		public void handlerMessage(Message msg){
			if(msg.what == 0x123){
				checkcode.setText("123");
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initTitleBar(true, true, "登录");
		initControl();
		
		checkicon = (ImageView) findViewById(R.id.checknumicon);
		byte [] data = CheckCode.getImageViewArray();
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		checkicon.setImageBitmap(bitmap);
	}

	private void initControl() {
		// TODO 自动生成的方法存根
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				username = (EditText) findViewById(R.id.username);
				password = (EditText) findViewById(R.id.password);
				checkcode = (EditText) findViewById(R.id.checknum);

				
				final String accountNum = username.getText().toString();
				final String accountPsw = password.getText().toString();
				final String accountCek = checkcode.getText().toString();
				
				if (accountNum.equals("")){
					showToastMessage("学号不能为空");
					return;
				}
				
				if (accountPsw.equals("")){
					showToastMessage("密码不能为空");
					return;
				}
				
				if (accountCek.equals("")){
					showToastMessage("验证码不能为空");
					return;
				}

				
				new Thread (){
					@Override
					public void run(){
						HttpGet get = new HttpGet("http://jwgl2.jmu.edu.cn/common/login.aspx");
						try {
							HttpResponse hResponse = hClient.execute(get);
							HttpEntity entity = hResponse.getEntity();
							if ( entity != null ){
								BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
								String line = null;
								
								while ( ( line = br.readLine() ) != null ){
									Message msg = new Message();
									msg.what = 0x123;
									msg.obj = line;
									handler.sendMessage(msg);
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}.start();
				
				new Thread (){
					@Override
					public void run (){
						try{
							HttpPost post = new HttpPost("http://jwgl2.jmu.edu.cn/common/login.aspx");
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("TxtUserName", accountNum));
							params.add(new BasicNameValuePair("TxtPassword", accountPsw));
							params.add(new BasicNameValuePair("TxtVerifCode", accountCek));
							post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
							HttpResponse response = hClient.execute(post);
							
							if (response.getStatusLine().getStatusCode() == 302){
								jumpActivity(MyInfoMainActivity.class);
								
								showToastMessage("SUCCESSED!");
							}
							}catch(Exception e){
								e.printStackTrace();
						}
					}
				}.start();
				
				new Thread(){
					@Override
					public void run(){
						try {
							HttpGet hGet = new HttpGet("http://jwgl2.jmu.edu.cn/Student/default.aspx");
							HttpResponse hrResponse = hClient.execute(hGet);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}.start();
			}
			
		});
	}
}