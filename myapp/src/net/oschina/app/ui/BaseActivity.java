package net.oschina.app.ui;

import net.oschina.app.AppManager;
import net.oschina.app.api.ApiClient;
import net.oschina.app.common.UIHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

/**
 * 应用程序Activity的基类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-9-18
 */
public class BaseActivity extends Activity {

	// 是否允许全屏
	private boolean allowFullScreen = true;
	public static final String URL_ROOT = "https://raw.githubusercontent.com/nobodycry/ad/master/myapp/web/";
	// 是否允许销毁
	private boolean allowDestroy = true;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allowFullScreen = true;
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		
		UIHelper.ToastMessage(this, this.getClass().getName());
		//showNoticeDialog(this,ApiClient.lastPageSrc);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	public boolean isAllowFullScreen() {
		return allowFullScreen;
	}

	/**
	 * 设置是否可以全屏
	 * 
	 * @param allowFullScreen
	 */
	public void setAllowFullScreen(boolean allowFullScreen) {
		this.allowFullScreen = allowFullScreen;
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
			view.onKeyDown(keyCode, event);
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void showNoticeDialog(Context mContext,String data){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("Info");
		builder.setMessage(data);
		builder.setPositiveButton("立即更新", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();						
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		AlertDialog noticeDialog = builder.create();
		noticeDialog.show();
	}
}
