package com.wutong.share.library.qq;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * QQ分享
 * Created by wutong on 2015/8/7 0007.
 */
public class QQShareUtils {


    /**
     * 分享到QQ好友，默认 支持类型见参数说明
     *
     * @param context   context
     * @param title     标题，必填
     * @param targetUrl 跳转的页面 必填（好友点击消息后的链接）
     * @param summary   描述，可传空字符串
     * @param imageUrl  图片路径，可传空字符串，网络图片url,或者是本地 的存储路径
     */
    public void shareQQ(Context context, String title, String targetUrl, String summary, String imageUrl) {
        Intent intent = new Intent(context, RefineitShareQQActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("targetUrl", targetUrl);
        intent.putExtra("summary", summary);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("type", RefineitShareQQActivity.SHARE_TYPE_QQ_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }

    /**
     * 分享到QQ好友，纯图片
     *
     * @param context  context
     * @param localUrl 本地图片路径
     */
    public void shareQQImage(Context context, String localUrl) {
        Intent intent = new Intent(context, RefineitShareQQActivity.class);
        intent.putExtra("imageUrl", localUrl);
        intent.putExtra("type", RefineitShareQQActivity.SHARE_TYPE_QQ_IMAGE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 分享到QQ好友，默认 支持类型见参数说明
     *
     * @param context        context
     * @param title          标题，必填
     * @param targetUrl      跳转的页面 必填（好友点击消息后的链接）
     * @param summary        描述，可传空字符串
     * @param imageArrayList 图片路径列表
     */
    public void shareQQZone(Context context, String title, String targetUrl, String summary, ArrayList<String> imageArrayList) {
        Intent intent = new Intent(context, RefineitShareQQActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("targetUrl", targetUrl);
        intent.putExtra("summary", summary);
        intent.putExtra("imageArrayList", imageArrayList);
        intent.putExtra("type", RefineitShareQQActivity.SHARE_TYPE_QQ_ZONE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
