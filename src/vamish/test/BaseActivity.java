package vamish.test;

import vamish.test.jatesting.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {

	// title bar
	private View backView;
	private TextView titleTextView;
	private View menuView;

	//
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initWindow();
	}

	public void initTitleBar(boolean showBackButton, boolean showMenuButton) {
		initTitleBar(showBackButton, showMenuButton, "教管测试版");
	}

	// to be called after setContentView() is called
	public void initTitleBar(boolean showBackButton, boolean showMenuButton, String titleName) {
		backView = (View) findViewById(R.id.title_bar_icon_back);
		backView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeCurrentActivity();
			}
		});
		if (!showBackButton) {
			backView.setVisibility(View.INVISIBLE);
		}

		titleTextView = (TextView) findViewById(R.id.title_bar_name);
		titleTextView.setText(titleName);

		menuView = (View) findViewById(R.id.title_bar_icon_msg);
		menuView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onTitleBarMenuClick();
			}
		});
		if (!showMenuButton) {
			menuView.setVisibility(View.INVISIBLE);
		}
	}

	// 如果某个activity需要title的menuButton，则重写此方法即可
	protected void onTitleBarMenuClick() {

	}

	public void setTitleBarName(String name) {
		titleTextView.setText(name);
	}

	public View getTitleBarMenuButton() {
		return menuView;
	}

	public void hideSoftInput(EditText eText) {
		final InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(eText.getWindowToken(), 0);
	}

	public void initProgressDialog(Activity activity) {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setCanceledOnTouchOutside(false);
	}

	public void showProgressDialog(String showMessage) {
		progressDialog.setMessage(showMessage);
		progressDialog.show();
	}

	public void closeProgressDialog() {
		progressDialog.cancel();
	}

	private void initWindow() {
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			closeCurrentActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void jumpActivity(Class<?> cls) {
		// 查看具体内容
		Intent intent = new Intent(this, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	public void closeCurrentActivity() {
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_out);
	}

	public void showToastMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void showToastMessageLongTime(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	// send error log
	public void onResume() {
		super.onResume();
	//	MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
	//	MobclickAgent.onPause(this);
	}
}
