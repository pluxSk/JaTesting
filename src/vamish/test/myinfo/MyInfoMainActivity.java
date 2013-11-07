package vamish.test.myinfo;

import vamish.test.BaseActivity;
import vamish.test.jatesting.R;
import android.os.Bundle;

public class MyInfoMainActivity extends BaseActivity{

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		initTitleBar(true, true, "我的信息");
		
	}
}
