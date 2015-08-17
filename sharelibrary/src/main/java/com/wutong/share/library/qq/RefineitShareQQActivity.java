package com.wutong.share.library.qq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wutong.share.library.R;
import com.wutong.share.library.RefineitShareCode;
import com.wutong.share.library.RefineitShareLib;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/16.
 */
public class RefineitShareQQActivity extends AppCompatActivity implements IUiListener {
    public static final String SHARE_TYPE_QQ_DEFAULT = "share_type_qq_default";
    public static final String SHARE_TYPE_QQ_IMAGE = "share_type_qq_image";
    public static final String SHARE_TYPE_QQ_ZONE = "share_type_qqzone";


    private String type;
    private String title;
    private Tencent mTencent;
    private String targetUrl;
    private String summary;
    private String imageUrl;
    private ArrayList<String> imageArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        targetUrl = getIntent().getStringExtra("targetUrl");
        summary = getIntent().getStringExtra("summary");
        imageUrl = getIntent().getStringExtra("imageUrl");
        imageArrayList = getIntent().getStringArrayListExtra("imageArrayList");

        mTencent = Tencent.createInstance(RefineitShareLib.getInstance().getConfiguration()
                .getQqAppId(), this.getApplicationContext());

        share();
    }


    private void share() {
        if (SHARE_TYPE_QQ_DEFAULT.equals(type)) {
            shareDefault();
        } else if (SHARE_TYPE_QQ_IMAGE.equals(type)) {
            shareImage();
        } else if (SHARE_TYPE_QQ_ZONE.equals(type)) {
            shareQQZone();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ACTIVITY_OK) {
            Tencent.handleResultData(data, this);
        }
    }

    /**
     * 默认类型分享
     */
    private void shareDefault() {

        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);

        if (!TextUtils.isEmpty(summary)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        }

        mTencent.shareToQQ(this, params, this);
    }

    /**
     * 分享纯图片
     */
    private void shareImage() {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        mTencent.shareToQQ(this, params, this);
    }

    /**
     * QQ空间分享
     */
    private void shareQQZone() {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);

        if (!TextUtils.isEmpty(summary)) {
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        }
        if (imageArrayList != null && imageArrayList.size() > 0) {
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageArrayList);
        }


        mTencent.shareToQzone(this, params, this);
    }

    @Override
    public void onComplete(Object o) {
        Toast.makeText(this, getString(R.string.qq_share_success), Toast.LENGTH_SHORT).show();
        sendBroadCast(RefineitShareCode.CallBackCode.CODE_VALUE_OK);
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(this, getString(R.string.qq_share_fail), Toast.LENGTH_SHORT).show();
        sendBroadCast(RefineitShareCode.CallBackCode.CODE_VALUE_FAIL);
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.qq_share_cancel), Toast.LENGTH_SHORT).show();
        sendBroadCast(RefineitShareCode.CallBackCode.CODE_VALUE_CANCEL);
    }


    private void sendBroadCast(String action_value) {
        Intent intent = new Intent();
        intent.setAction(RefineitShareCode.IntentAction.QQ_SHARE);
        intent.putExtra(RefineitShareCode.CallBackCode.CODE_KEY, action_value);
        sendBroadcast(intent);

        finish();
    }
}
