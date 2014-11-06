package com.aiteu.dailyreading.update;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import com.aiteu.dailyreading.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 更新检测
 * @author liwei
 *
 */
public class AppUpdate{
	private static final String TAG = AppUpdate.class.getSimpleName();
	
	private Context mContext = null;
	private UpdataInfo info;
	private ProgressBar mProgress;
	private boolean cancelUpdate = false;
	private Dialog mDownloadDialog;
	private String path = "192.168.2.103:8080/xxxxx.xml";  
	
	public AppUpdate(Context context){
		this.mContext = context;
		info = new UpdataInfo();
	}
	
	public void check(){
		if (isUpdate()) {
			ShowUpdateDialog();
		}else {
			Toast.makeText(mContext, R.string.no_update, Toast.LENGTH_LONG).show();
		}
	}
	
	
    private void ShowUpdateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(mContext);
		 builder.setTitle(R.string.soft_update_title);
	     builder.setMessage(R.string.soft_update_info);
	        // 更新
	        builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which){
	                dialog.dismiss();
	                // 显示下载对话框
	                showDownloadDialog();
	            }
	        });
	        // 稍后更新
	        builder.setNegativeButton(R.string.soft_update_later, new OnClickListener(){
	            @Override
	            public void onClick(DialogInterface dialog, int which){
	                dialog.dismiss();
	            }
	        });
	        Dialog noticeDialog = builder.create();
	        noticeDialog.show();
	}
    
    private void showDownloadDialog() {
		// TODO Auto-generated method stub
    	  // 构造软件下载对话框
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
	}

	private void downloadApk() {
		// TODO Auto-generated method stub
		
	}

	/**
     * 获取当前程序的版本号  
     * @return
     */
	private int getVersionName(Context context){  
		int versionCode = 0;
		
		 // 获取软件版本号，对应AndroidManifest.xml下android:versionCode 
		try {
			versionCode = context.getPackageManager().getPackageInfo("com.aiteu.dailyreading", 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};  
        return versionCode;   
    }  
	
	/**
	 * 检查软件是否有更新版本
	 * @return
	 */
	private boolean isUpdate(){
		
		//获取当前软件版本
		int currentVersionCode = getVersionName(mContext);
      
		try {
			URL url = new URL(path);
			HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	        conn.setConnectTimeout(5000);  
	        InputStream is =conn.getInputStream(); 
	        info = PullFromServer.getUpdataInfo(is);
	        
	        if (info.getVersion() > currentVersionCode) {
				return true;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
    
		
}
