package com.example.carl.ormlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.carl.ormlitedemo.bean.User;
import com.example.carl.ormlitedemo.dao.UserDao;
import com.example.carl.ormlitedemo.db.DatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.message);
        userDao = new UserDao(this);
        // 删除全部
        userDao.deleteAll();
        mTextView.append("\n#######Begin to Insert#########\n");
        // 插入数据
        userDao.insertTest();
        display();
        // mTextView.append("\n#######Begin to Update#########\n");

        mTextView.append("\n#######Begin to Delete#########\n");
        // 删除数据
        userDao.delete("name3");
        display();
        mTextView.append("\n#######Begin to Search#########\n");
        // 查找数据
        mTextView.append(userDao.search("name1").toString());
    }

    /**
     * 显示所有的
     */
    private void display() {
        List<User> users = userDao.queryAll();
        for (User user : users) {
            mTextView.append(user.toString());
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        DatabaseHelper.getHelper(this).close();
    }
}
