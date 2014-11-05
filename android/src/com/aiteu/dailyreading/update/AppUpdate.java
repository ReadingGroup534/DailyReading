package com.aiteu.dailyreading.update;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Xml;

/**
 * 更新检测
 * @author liwei
 *
 */
public class AppUpdate extends Activity{
	private static final String TAG = AppUpdate.class.getSimpleName();
	
	private Context mContext = null;
	
	public AppUpdate(Context context){
		this.mContext = context;
	}
	
	public void check(){
		
	}
	
    /**
     * 获取当前程序的版本号  
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	private String getVersionName() throws Exception{  
        //获取packagemanager的实例   
        PackageManager packageManager = getPackageManager();  
        //getPackageName()是你当前类的包名，0代表是获取版本信息  
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);  
        return packInfo.versionName;   
    }  
    
	/**
	 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{
		XmlPullParser  parser = Xml.newPullParser();  
		parser.setInput(is, "utf-8");//设置解析的数据源 
		int type = parser.getEventType();
		UpdataInfo info = new UpdataInfo();//实体
		while(type != XmlPullParser.END_DOCUMENT ){
			switch (type) {
			case XmlPullParser.START_TAG:
				if("version".equals(parser.getName())){
					info.setVersion(parser.nextText());	//获取版本号
				}else if ("url".equals(parser.getName())){
					info.setUrl(parser.nextText());	//获取要升级的APK文件
				}else if ("description".equals(parser.getName())){
					info.setDescription(parser.nextText());	//获取该文件的信息
				}
				break;
			}
			type = parser.next();
		}
		return info;
	}
    
}
