package com.wutong.share.library.wechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.Util;
import com.wutong.share.library.R;
import com.wutong.share.library.RefineitShareCode;
import com.wutong.share.library.RefineitShareLib;

import java.io.File;

/**
 * 微信分享父类
 * Created by wutong on 2015/8/16.
 */
public class RefineitShareWeChatActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final int THUMB_SIZE = 100;
    public static final String SHARE_TYPE_TEXT = "share_type_text";
    public static final String SHARE_TYPE_IMAGE = "share_type_image";
    public static final String SHARE_TYPE_WEB = "share_type_web";

    private IWXAPI api;

    private String type;
    private String title;
    private Bitmap bitmap;
    private String webpageUrl;
    private String localImagePath;
    private String description;
    private boolean isFriendCircle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        webpageUrl = getIntent().getStringExtra("webpageUrl");
        localImagePath = getIntent().getStringExtra("localImagePath");
        description = getIntent().getStringExtra("description");
        bitmap = getIntent().getParcelableExtra("bitmap");
        isFriendCircle = getIntent().getBooleanExtra("isFriendCircle", true);

        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, RefineitShareLib.getInstance().getConfiguration().getWeichatAppKey());
        api.handleIntent(getIntent(), this);

        share();
    }

    /**
     * 分享
     */
    private void share() {
        if (SHARE_TYPE_TEXT.equals(type)) {
            shareWeChatText();
        } else if (SHARE_TYPE_IMAGE.equals(type)) {
            shareWeChatImage();
        } else if (SHARE_TYPE_WEB.equals(type)) {
            shareWeChatWeb();
        }
        //立刻关闭此界面
        finish();

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        String action_value = RefineitShareCode.CallBackCode.CODE_VALUE_FAIL;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.wechat_errcode_success;
                action_value = RefineitShareCode.CallBackCode.CODE_VALUE_OK;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.wechat_errcode_cancel;
                action_value = RefineitShareCode.CallBackCode.CODE_VALUE_CANCEL;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.wechat_errcode_deny;
                break;
            default:
                result = R.string.wechat_errcode_unknown;
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setAction(RefineitShareCode.IntentAction.WECHAT_SHARE);
        intent.putExtra(RefineitShareCode.CallBackCode.CODE_KEY, action_value);
        sendBroadcast(intent);

        finish();

    }

    /**
     * 分享纯文字
     */
    protected void shareWeChatText() {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = title;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = title;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(SHARE_TYPE_TEXT); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isFriendCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    /**
     * 分享纯图片
     */
    protected void shareWeChatImage() {
        File file = new File(localImagePath);
        if (!file.exists()) {
            Log.e("RefineitShareWeChat","找不到目标图片");
            return;
        }
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(localImagePath);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeFile(localImagePath);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = WeChatUtil.bmpToByteArray(thumbBmp, true);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(SHARE_TYPE_IMAGE);
        req.message = msg;
        req.scene = isFriendCircle ? SendMessageToWX.Req.WXSceneTimeline
                : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    /**
     * 分享 网页对象
     */
    protected void shareWeChatWeb() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = WeChatUtil.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(SHARE_TYPE_WEB);
        req.message = msg;
        req.scene = isFriendCircle ? SendMessageToWX.Req.WXSceneTimeline
                : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
