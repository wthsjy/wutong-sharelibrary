package com.wutong.share.library.wechat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * 微信分享工具类
 * Created by wutong on 2015/8/7 0007.
 */
public class WeChatShareUtils {
    private static final String TAG = "WeChatShareUtils";

    public void shareWeChatText(Context context, boolean isFriendCircle, String title) {
        try {
            Intent intent = new Intent(context, Class.forName(context.getPackageName()
                    + ".wxapi.WXEntryActivity"));
            intent.putExtra("isFriendCircle", isFriendCircle);
            intent.putExtra("title", title);
            intent.putExtra("type", RefineitShareWeChatActivity.SHARE_TYPE_TEXT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "找不到：" + context.getPackageName() + ".wxapi.WXEntryActivity"
                    + "  请先集成微信分享的 WXEntryActivity");
        }


    }

    public void shareWeChatImage(Context context, boolean isFriendCircle, String localImagePath) {
        try {
            Intent intent = new Intent(context, Class.forName(context.getPackageName()
                    + ".wxapi.WXEntryActivity"));
            intent.putExtra("isFriendCircle", isFriendCircle);
            intent.putExtra("localImagePath", localImagePath);
            intent.putExtra("type", RefineitShareWeChatActivity.SHARE_TYPE_IMAGE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "找不到：" + context.getPackageName()
                    + ".wxapi.WXEntryActivity" + "  请先集成微信分享的 WXEntryActivity");
        }


    }


    public void shareWeChatWeb(Context context, boolean isFriendCircle, String title,
                               String description, String webpageUrl, Bitmap thumbBitmap) {
        try {
            Intent intent = new Intent(context, Class.forName(context.getPackageName()
                    + ".wxapi.WXEntryActivity"));
            intent.putExtra("isFriendCircle", isFriendCircle);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putExtra("webpageUrl", webpageUrl);
            intent.putExtra("bitmap", thumbBitmap);
            intent.putExtra("type", RefineitShareWeChatActivity.SHARE_TYPE_WEB);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "找不到：" + context.getPackageName()
                    + ".wxapi.WXEntryActivity" + "  请先集成微信分享的 WXEntryActivity");
        }
    }
}
