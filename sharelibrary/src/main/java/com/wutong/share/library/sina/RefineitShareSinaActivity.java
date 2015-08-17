package com.wutong.share.library.sina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.wutong.share.library.R;
import com.wutong.share.library.RefineitShareCode;
import com.wutong.share.library.RefineitShareLib;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/11.
 */
public class RefineitShareSinaActivity extends AppCompatActivity implements IWeiboHandler.Response {

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <br>
     * <br>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * <br>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <br>
     * 我们通过新浪微博开放平台--管理中心--我的应用--接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <br>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <br>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    private IWeiboShareAPI mWeiboShareAPI;


    private ArrayList<BaseMediaObject> mediaObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaObjects = getIntent().getParcelableArrayListExtra("obj");
        if (mediaObjects == null || mediaObjects.size() == 0) {
            finish();
            return;
        }

        initialize();


        int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
        if (supportApi >= 10351) {
            sendMultiMessage(mediaObjects);
        } else {
            sendSingleMessage(mediaObjects.get(0));
        }
    }

    /**
     * 初始化
     */
    private void initialize() {
        // 创建微博 SDK 接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, RefineitShareLib.getInstance()
                .getConfiguration().getSinaAppKey());
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param baseResponse 微博请求数据对象
     */
    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG)
                        .show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG)
                        .show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this,
                        getString(R.string.weibosdk_demo_toast_share_failed)
                                + "Error Message: " + baseResponse.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
        sendSharebRroadCast(baseResponse.errCode);
        finish();
    }


    /**
     * 发送分享后的回调广播
     */
    private void sendSharebRroadCast(int errCode) {
        Intent intent = new Intent();
        intent.setAction(RefineitShareCode.IntentAction.SINA_SHARE);
        intent.putExtra(RefineitShareCode.CallBackCode.CODE_KEY, getCodeString(errCode));
        sendBroadcast(intent);
    }

    private String getCodeString(int errCode) {
        String value = "";
        if (errCode == WBConstants.ErrorCode.ERR_OK) {
            value = RefineitShareCode.CallBackCode.CODE_VALUE_OK;
        } else if (errCode == WBConstants.ErrorCode.ERR_CANCEL) {
            value = RefineitShareCode.CallBackCode.CODE_VALUE_CANCEL;
        } else {
            value = RefineitShareCode.CallBackCode.CODE_VALUE_FAIL;
        }
        return value;
    }


    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     *
     * @param baseMediaObject obj
     */
    private void sendSingleMessage(BaseMediaObject baseMediaObject) {
        if (!checkIsAppInstalled()) return;
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();

        weiboMessage.mediaObject = baseMediaObject;

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(this, request);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     *
     * @param objects 分享的内容 列表
     */
    private void sendMultiMessage(ArrayList<BaseMediaObject> objects) {
        if (!checkIsAppInstalled()) return;

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        for (BaseMediaObject object : objects) {
            if (object instanceof TextObject) {
                weiboMessage.textObject = (TextObject) object;
            } else if (object instanceof ImageObject) {
                weiboMessage.imageObject = (ImageObject) object;
            } else {
                weiboMessage.mediaObject = object;
            }

        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(this, request);
    }

    /**
     * 是否已经安装了微博客户端
     *
     * @return
     */
    private boolean checkIsAppInstalled() {
        boolean isInstalledWeibo = false;
        // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
        isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        if (!isInstalledWeibo) {
            Toast.makeText(this, "您没有安装微博客户端。", Toast.LENGTH_SHORT).show();
            finish();
        }
        return isInstalledWeibo;
    }


    /**
     * 分享纯文字
     *
     * @param context 内容
     */
    public static void shareText(Activity activity, String context) {
        ArrayList<BaseMediaObject> objects = new ArrayList<>();

        TextObject textObject = new TextObject();
        textObject.text = context;

        objects.add(textObject);

        Intent intent = new Intent(activity, RefineitShareSinaActivity.class);
        intent.putExtra("obj", objects);
        activity.startActivity(intent);

    }

    /**
     * 分享 图片 和 文字 并存
     *
     * @param activity activity
     * @param context  内容
     * @param bitmap   图片
     */
    public static void shareImagWithText(Activity activity, String context, Bitmap bitmap) {
        ArrayList<BaseMediaObject> objects = new ArrayList<>();

        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);

        TextObject textObject = new TextObject();
        textObject.text = context;

        objects.add(imageObject);
        objects.add(textObject);

        Intent intent = new Intent(activity, RefineitShareSinaActivity.class);
        intent.putExtra("obj", objects);
        activity.startActivity(intent);
    }

    /**
     * 分享纯图片
     *
     * @param activity activity
     * @param bitmap   图片
     */
    public static void shareImage(Activity activity, Bitmap bitmap) {
        ArrayList<BaseMediaObject> objects = new ArrayList<>();

        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);

        objects.add(imageObject);

        Intent intent = new Intent(activity, RefineitShareSinaActivity.class);
        intent.putExtra("obj", objects);
        activity.startActivity(intent);
    }

    /**
     *  分享 网页
     * @param activity activity
     * @param title 标题
     * @param description 描述
     * @param thumbBitmap 缩略图
     * @param bitmap 图片
     * @param actionUrl 跳转链接
     */
    public static void shareWeb(Activity activity, String title, String description,
                                Bitmap thumbBitmap, Bitmap bitmap, String actionUrl) {

        ArrayList<BaseMediaObject> objects = new ArrayList<>();

        //网页
        WebpageObject webpageObject = new WebpageObject();
        webpageObject.identify = Utility.generateGUID();
        webpageObject.title = title;
        webpageObject.description = description;
        // 设置 Bitmap 类型的图片到视频对象里
        webpageObject.setThumbImage(thumbBitmap);
        webpageObject.actionUrl = actionUrl;
        webpageObject.defaultText = description;

        objects.add(webpageObject);

        //图片
        if (bitmap != null) {
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(bitmap);
            objects.add(imageObject);
        }


        Intent intent = new Intent(activity, RefineitShareSinaActivity.class);
        intent.putExtra("obj", objects);
        activity.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
