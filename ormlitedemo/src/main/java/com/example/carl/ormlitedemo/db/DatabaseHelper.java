/**
 * @author zhoushengtao
 * @since 2013-7-16 下午7:09:08
 */

package com.example.carl.ormlitedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.carl.ormlitedemo.bean.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG = "DatabaseHelper";
	// 数据库名称
	private static final String DATABASE_NAME = "HelloOrmlite.db";
	// 数据库version
	private static final int DATABASE_VERSION = 1;

	private Map<String, Dao> daos = new HashMap<String, Dao>();

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// 可以用配置文件来生成 数据表，有点繁琐，不喜欢用
		// super(context, DATABASE_NAME, null, DATABASE_VERSION,
		// R.raw.ormlite_config);
	}

	private static DatabaseHelper instance;

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelper getHelper(Context context) {
		context = context.getApplicationContext();
		if (instance == null) {
			synchronized (DatabaseHelper.class) {
				if (instance == null)
					instance = new DatabaseHelper(context);
			}
		}

		return instance;
	}

//	/**
//	 * @param context
//	 * @param databaseName
//	 * @param factory
//	 * @param databaseVersion
//	 */
//	public DatabaseHelper(Context context, String databaseName,
//			CursorFactory factory, int databaseVersion) {
//		super(context, databaseName, factory, databaseVersion);
//	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			// 建立User表
			TableUtils.createTable(connectionSource, User.class);

		} catch (SQLException e) {
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, User.class, true);
		} catch (SQLException e) {
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized Dao getDao(@SuppressWarnings("rawtypes") Class clazz) throws SQLException {
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className)) {
			dao = daos.get(className);
		}
		if (dao == null) {
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void close() {
		super.close();

		for (String key : daos.keySet()) {
			@SuppressWarnings("unused")
			Dao dao = daos.get(key);
			dao = null;
		}
	}

}
