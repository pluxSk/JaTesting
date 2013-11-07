package vamish.test;

import vamish.test.jatesting.R;
import vamish.test.myinfo.MyInfoLoginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseActivity implements OnClickListener {
	private long clickBackCmdTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initTitleBar(true, true, "教务管理系统");
		initControls();
	}

	private void initControls() {
		View MyInfo = (View) findViewById(R.id.main_myinfo);
		MyInfo.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long curTime = System.currentTimeMillis();
			long TIME_OUT = 1000;
			if (curTime - clickBackCmdTime >= TIME_OUT) {
				clickBackCmdTime = curTime;
				showToastMessage("再按一次退出程序哟");
			} else {
				quitActivity();
			}
		}
		return false;
	}

	private void quitActivity() {
		finish();
		System.exit(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_myinfo:
			jumpActivity(MyInfoLoginActivity.class);
			break;
		}
	}

}