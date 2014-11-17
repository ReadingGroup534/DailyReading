package com.aiteu.dailyreading.db;

import org.w3c.dom.Text;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;
import android.util.Log;

/**
 * 我的收藏  数据库的建立
 * @author liyangchao
 *
 */
public class MyStoreHelper extends SQLiteOpenHelper{

	public static String DATABASE_NAME = "mystore.db";
	public static int DATABASE_VERSION = 1;
	private String PATH = "path";
	private final static String TAG = "CREATE DB";
	
	public MyStoreHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	

	public MyStoreHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(TAG,"create started");
		String CREATE_TABLE =  "create table article" + "(" 
			   +"article_id   char(22)   default '' not null primary key,"
			   +"active   enum('y','n')    default 'y' not null,"   
			   +"show_time   datetime    default '0000-00-00 00:00:00' not null,"
			   +"title   varchar(255)   default '' not null,"
			   +"author  varchar(255)   default '' not null,"
			   +"article_type  enum('sanwen','gushi','lizhi','other')  default 'other' not null,"
			   +"abstracts   varchar(255)      default '' not null,"
			   +"recommend_star  smallint unsigned    default 0   not null,"
			   +"praise_times  int unsigned  default 0   not null,"
			   +"share_times   int unsigned	 default 0   not null,"
			   +"scan_times    int unsigned  default 0   not null, "  
			   +"physical_path   varchar(255)   default '' not null,"
			+")";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
