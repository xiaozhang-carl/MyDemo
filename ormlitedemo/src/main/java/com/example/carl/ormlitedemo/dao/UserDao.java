package com.example.carl.ormlitedemo.dao;

import android.content.Context;

import com.example.carl.ormlitedemo.bean.User;
import com.example.carl.ormlitedemo.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
	public Dao<User, Integer> userDaoOpe;
	public DatabaseHelper helper;

	public UserDao(Context context) {
		try {
			helper = DatabaseHelper.getHelper(context);
			userDaoOpe = helper.getDao(User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入值测试
	 */
	public void insertTest() {
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setUsername("name" + i);
			user.setPassword("test_pass " + i);
			try {
				userDaoOpe.create(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 插入值测试
	 */
	public void insert(User user) {
		try {
			userDaoOpe.create(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 连续进行插入，如果存在则更新
	 */
	public void LiveCreates(final List<User> lists) {
		for (User user : lists) {
			insert(user);
		}
	}

	/**
	 * 更新
	 * 
	 * @param user
	 *            待更新的user
	 */
	public void update(User user) {
		try {
			userDaoOpe.createOrUpdate(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 按照指定的id 与 username 删除一项
	 * 
	 * @param id
	 * @param username
	 * @return 删除成功返回true ，失败返回false
	 */
	public int delete(String username) {
		try {
			// 删除指定的信息，类似delete User where 'id' = id ;
			DeleteBuilder<User, Integer> deleteBuilder = userDaoOpe
					.deleteBuilder();
			deleteBuilder.where().eq("username", username);

			return deleteBuilder.delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 按照id查询user
	 * 
	 * @param id
	 * @return
	 */
	public User search(String username) {
		try {
			// 查询的query 返回值是一个列表
			// 类似 select * from User where 'username' = username;
			List<User> users = userDaoOpe.queryBuilder().where()
					.eq("username", username).query();
			if (users.size() > 0) {
				return users.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除全部
	 */
	public void deleteAll() {
		try {
			userDaoOpe.delete(queryAll());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有的
	 */
	public List<User> queryAll() {
		List<User> users = null;
		try {
			users = userDaoOpe.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

}
