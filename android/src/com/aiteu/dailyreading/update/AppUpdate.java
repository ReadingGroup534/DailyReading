package com.aiteu.dailyreading.update;

import java.io.File;
import java.io.FileOutputStream;
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
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 更新检测
 * 
 * @author liwei
 * 
 */
public class AppUpdate {
	private static final String TAG = AppUpdate.class.getSimpleName();

	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;

	private Context mContext = null;
	private UpdataInfo info;
	private ProgressBar mProgress;
	private int progress;
	private boolean cancelUpdate = false;
	private Dialog mDownloadDialog;
	private String mSavePath; // 下载保存路径
	private String path = "192.168.2.103:8080/xxxxx.xml";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD:
				mProgress.setProgress(progress); // 设置进度条
				break;
			case DOWNLOAD_FINISH:
				installApk(); // 安装文件
			default:
				break;
			}
		};
	};

	public AppUpdate(Context context) {
		this.mContext = context;
		info = new UpdataInfo();
	}

	public void check() {
		if (isUpdate()) {
			ShowUpdateDialog();
		} else {
			Toast.makeText(mContext, R.string.no_update, Toast.LENGTH_LONG)
					.show();
		}
	}

	private void ShowUpdateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新
		builder.setPositiveButton(R.string.soft_update_updatebtn,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 显示下载对话框
						showDownloadDialog();
					}
				});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
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
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
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
		new DownLoadAPKThread().start();
	}

	/**
	 * 获取当前程序的版本号
	 * 
	 * @return
	 */
	private int getVersionName(Context context) {
		int versionCode = 0;

		// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					"com.aiteu.dailyreading", 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return versionCode;
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate() {

		// 获取当前软件版本
		int currentVersionCode = getVersionName(mContext);

		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
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

	private class DownLoadAPKThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";

					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();

					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, info.getName());
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓冲区
					byte[] buff = new byte[1024];
					// 写文件
					do {
						int readNumber = is.read(buff);
						count += readNumber;
						progress = (int) (((float) count / length) * 100);
						mHandler.sendEmptyMessage(DOWNLOAD);

						if (readNumber <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}

						// 写入文件
						fos.write(buff, 0, readNumber);
					} while (!cancelUpdate); // 点击取消停止下载
					fos.close();
					is.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}

	}

	private void installApk() {
		// TODO Auto-generated method stub
		File apkfile = new File(mSavePath, info.getName());
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

}
