package com.wutong.share.library;

/**
 * 分享的广播，以及回调 常量
 * Created by wutong on 2015/8/16.
 */
public class RefineitShareCode {
    /**
     * 分享的广播 action
     */
    public static class IntentAction {
        /**
         * 新浪分享action
         */
        public static final String SINA_SHARE = "intent_action_sina_share";
        /**
         * QQ分享action
         */
        public static final String QQ_SHARE = "intent_action_qq_share";
        /**
         * 微信分享action
         */
        public static final String WECHAT_SHARE = "intent_action_wechat_share";
    }

    /**
     * 分享的回调code，key-value
     */
    public static class CallBackCode {


        /**
         * 广播的intent 中 数据的 key
         */
        public static final String CODE_KEY = "intent_action_share_errcode";
        /**
         * 广播的intent 中 数据的 value  分享成功
         */
        public static final String CODE_VALUE_OK = "intent_action_share_errcode_ok";
        /**
         * 广播的intent 中 数据的 value  分享被取消
         */
        public static final String CODE_VALUE_CANCEL = "intent_action_share_code_cancel";
        /**
         * 广播的intent 中 数据的 value  分享失败
         */
        public static final String CODE_VALUE_FAIL = "intent_action_share_code_fail";
    }

}
