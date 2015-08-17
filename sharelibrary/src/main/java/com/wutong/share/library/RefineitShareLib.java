package com.wutong.share.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.wutong.share.library.qq.QQShareUtils;
import com.wutong.share.library.sina.RefineitShareSinaActivity;
import com.wutong.share.library.wechat.WeChatShareUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/10 0010.
 */
public class RefineitShareLib {
    private static final String TAG = "RefineitShareLib";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "RefineitShareConfiguration 不能为 null";
    private static final String LOG_INIT_CONFIG = "RefineitShareConfiguration 初始化成功";
    private static final java.lang.String WARNING_RE_INIT_CONFIG = "警告：RefineitShareConfiguration 重复初始化";
    private static final String ERROR_CONFIG_WITH_NULL = "RefineitShareConfiguration 没有初始化";


    private volatile static RefineitShareLib instance;
    private RefineitShareConfiguration configuration;


    public static RefineitShareLib getInstance() {
        if (instance == null) {
            synchronized (RefineitShareLib.class) {
                if (instance == null) {
                    instance = new RefineitShareLib();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param configuration configuration
     */
    public void init(RefineitShareConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (this.configuration == null) {
            Log.d(TAG, LOG_INIT_CONFIG);
            this.configuration = configuration;
        } else {
            Log.d(TAG, WARNING_RE_INIT_CONFIG);
        }

    }

    public RefineitShareConfiguration getConfiguration() {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_CONFIG_WITH_NULL);
        }
        return configuration;
    }

    /**
     * 新浪微博分享 只分享文字
     *
     * @param activity activity
     * @param content  文字
     */
    public void shareSinaText(Activity activity, String content) {
        RefineitShareSinaActivity.shareText(activity, content);
    }

    /**
     * 新浪微博分享 只分享图片
     *
     * @param activity activity
     * @param bitmap   图片
     */
    public void shareSinaImage(Activity activity, Bitmap bitmap) {
        RefineitShareSinaActivity.shareImage(activity, bitmap);
    }

    /**
     * 新浪微博分享 分享图片与文字并存
     * @param activity activity
     * @param content 内容
     * @param bitmap 图片
     */
    public void shareImagWithText(Activity activity, String content, Bitmap bitmap) {
        RefineitShareSinaActivity.shareImagWithText(activity, content, bitmap);
    }

    /**
     * 新浪微博分享 分享网页
     *
     * @param activity    activity
     * @param title       标题
     * @param description 描述
     * @param thumbBitmap 缩略图
     * @param bitmap      图片对象，如不需要可传null
     * @param actionUrl   跳转地址
     */
    public void shareSinaWeb(Activity activity, String title, String description,
                             Bitmap thumbBitmap, Bitmap bitmap, String actionUrl) {
        RefineitShareSinaActivity.shareWeb(activity, title, description,
                thumbBitmap, bitmap, actionUrl);
    }


    /**
     * 微信分享  只有文字
     *
     * @param context        context
     * @param isFriendCircle 是否发布到朋友圈
     * @param title          文字
     */
    public void shareWeChatText(Context context, boolean isFriendCircle, String title) {
        WeChatShareUtils utils = new WeChatShareUtils();
        utils.shareWeChatText(context, isFriendCircle, title);

    }

    /**
     * 微信分享  只有图片
     *
     * @param context        context
     * @param isFriendCircle 是否发布到朋友圈
     * @param bitmap         图片
     */
    public void shareWeChatImage(Context context, boolean isFriendCircle, Bitmap bitmap) {
        WeChatShareUtils utils = new WeChatShareUtils();
        utils.shareWeChatImage(context, isFriendCircle, bitmap);

    }

    /**
     *微信分享  网页 对象
     * @param context context
     * @param isFriendCircle  是否发布到朋友圈
     * @param title 标题
     * @param description 描述
     * @param webpageUrl 网页跳转链接
     * @param thumbBitmap 缩略图
     */
    public void shareWeChatWeb(Context context, boolean isFriendCircle, String title, String description, String webpageUrl, Bitmap thumbBitmap) {
        WeChatShareUtils utils = new WeChatShareUtils();
        utils.shareWeChatWeb(context, isFriendCircle, title, description, webpageUrl, thumbBitmap);

    }

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
        QQShareUtils utils = new QQShareUtils();
        utils.shareQQ(context, title, targetUrl, summary, imageUrl);
    }


    /**
     * 分享到QQ好友，默认 支持类型见参数说明
     *
     * @param context   context
     * @param localPath 是本地 的存储路径
     */
    public void shareQQImage(Context context, String localPath) {
        QQShareUtils utils = new QQShareUtils();
        utils.shareQQImage(context, localPath);
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
        QQShareUtils utils = new QQShareUtils();
        utils.shareQQZone(context, title, targetUrl, summary, imageArrayList);
    }

}
