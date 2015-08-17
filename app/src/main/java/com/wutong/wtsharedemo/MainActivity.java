package com.wutong.wtsharedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wutong.share.library.RefineitShareCode;
import com.wutong.share.library.RefineitShareLib;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private MyBroadCastReciver myBroadCastReciver = new MyBroadCastReciver();
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        registerBroadCast();
    }

    /**
     * 注册分享回调广播，如果不需要对回调进行处理，则可以不注册广播
     */
    public void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RefineitShareCode.IntentAction.SINA_SHARE);
        filter.addAction(RefineitShareCode.IntentAction.QQ_SHARE);
        filter.addAction(RefineitShareCode.IntentAction.WECHAT_SHARE);
        registerReceiver(myBroadCastReciver, filter);
    }

    /**
     * 新浪分享样例,各个方法使用请 看方法注释，（双击在方法名，并使用快捷键Ctrl+Q）
     *
     * @param view
     */
    public void sina(View view) {

//        RefineitShareLib.getInstance().shareSinaText(this, "睿风分享测试样例");
//        RefineitShareLib.getInstance().shareSinaImage(this, bitmap);
        RefineitShareLib.getInstance().shareSinaWeb(this, "title", "content", bitmap, null, "http://www.baidu.com");
//        RefineitShareLib.getInstance().shareImagWithText(this, "content", bitmap);

    }

    /**
     * 微信分享样例,各个方法使用请 看方法注释，（双击在方法名，并使用快捷键Ctrl+Q）
     *
     * @param view
     */
    public void wechat(View view) {
//        RefineitShareLib.getInstance().shareWeChatText(this, false, "无他的飒飒的");
//        RefineitShareLib.getInstance().shareWeChatImage(this, false, bitmap);
        RefineitShareLib.getInstance().shareWeChatWeb(this, false, "标题", "描述", "http://www.baidu.com", bitmap);
    }


    /**
     * QQ空间分享样例,各个方法使用请 看方法注释，（双击在方法名，并使用快捷键Ctrl+Q）
     *
     * @param view
     */
    public void qqZone(View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("/sdcard/a.jpg");
        arrayList.add("http://pic1.ooopic.com/uploadfilepic/sheji/2009-08-12/OOOPIC_SHIJUNHONG_2009081248f16747c1659ceb.jpg");


        RefineitShareLib.getInstance().shareQQZone(this, "title",
                "http://www.baidu.com", "描述",
                arrayList);
    }

    /**
     * QQ分享样例,各个方法使用请 看方法注释，（双击在方法名，并使用快捷键Ctrl+Q）
     *
     * @param view
     */
    public void qq(View view) {
        RefineitShareLib.getInstance().shareQQ(this, "title",
                "http://www.baidu.com", "描述",
                "http://pic1.ooopic.com/uploadfilepic/sheji/2009-08-12/OOOPIC_SHIJUNHONG_2009081248f16747c1659ceb.jpg");
//        RefineitShareLib.getInstance().shareQQImage(this,"/sdcard/a.jpg");



    }

    private class MyBroadCastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //分享的action
            String action = intent.getAction();
            //分享的状态value
            String value = intent.getStringExtra(RefineitShareCode.CallBackCode.CODE_KEY);

            if (RefineitShareCode.IntentAction.SINA_SHARE.equals(action)) {
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                //TODO 此处按具体场景对value 进行判断，value有以下3个值
                //TODO RefineitShareCode.CallBackCode.CODE_VALUE_OK，
                //TODO RefineitShareCode.CallBackCode.CODE_VALUE_FAIL,
                //TODO RefineitShareCode.CallBackCode.CODE_VALUE_CANCEL


            } else if (RefineitShareCode.IntentAction.QQ_SHARE.equals(action)) {
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            } else if (RefineitShareCode.IntentAction.WECHAT_SHARE.equals(action)) {
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
