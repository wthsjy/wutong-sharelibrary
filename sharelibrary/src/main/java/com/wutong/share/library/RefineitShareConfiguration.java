package com.wutong.share.library;

import android.content.Context;

import com.wutong.share.library.sina.RefineitShareSinaActivity;

/**
 * 分享参数配置类
 * Created by wutong on 2015/8/11.
 */
public class RefineitShareConfiguration {
    private Context context;
    private String sinaAppKey;
    private String sinaAppSecret;
    private String sinaScope;

    private String weichatAppKey;
    private String weichatAppSecret;

    private String qqAppId;
    private String qqAppSecret;

    private RefineitShareConfiguration(final Builder builder) {
        this.context = builder.context;
        this.sinaAppKey = builder.sinaAppKey;
        this.sinaAppSecret = builder.sinaAppSecret;
        this.sinaScope = builder.sinaScope;

        this.weichatAppKey = builder.weichatAppKey;
        this.weichatAppSecret = builder.weichatAppSecret;

        this.qqAppId = builder.qqAppId;
        this.qqAppSecret = builder.qqAppSecret;
    }

    public Context getContext() {
        return context;
    }

    public String getSinaAppKey() {
        return sinaAppKey;
    }

    public String getSinaAppSecret() {
        return sinaAppSecret;
    }

    public String getSinaScope() {
        return sinaScope;
    }

    public String getWeichatAppKey() {
        return weichatAppKey;
    }

    public String getWeichatAppSecret() {
        return weichatAppSecret;
    }

    public String getQqAppId() {
        return qqAppId;
    }

    public String getQqAppSecret() {
        return qqAppSecret;
    }

    public static class Builder {
        private Context context;
        private String sinaAppKey;
        private String sinaAppSecret;
        private String sinaScope;
        private String weichatAppKey;
        private String weichatAppSecret;
        private String qqAppId;
        private String qqAppSecret;

        public Builder(Context context) {
            this.context = context;
        }


        /**
         * 新浪参数
         *
         * @param appKey    appKey
         * @param appSecret appSecret
         * @param scope     scope  可以不传，已设置默认值
         * @return Builder
         */
        public Builder configSina(String appKey, String appSecret, String... scope) {
            this.sinaAppKey = appKey;
            this.sinaAppSecret = appSecret;
            this.sinaScope = (scope != null && scope.length > 0) ? scope[0] : RefineitShareSinaActivity.SCOPE;
            return this;
        }

        /**
         * 微信参数
         *
         * @param appKey    appKey
         * @param appSecret appSecret
         * @return Builder
         */
        public Builder configWeiChat(String appKey, String appSecret) {
            this.weichatAppKey = appKey;
            this.weichatAppSecret = appSecret;
            return this;
        }
        /**
         * QQ参数
         *
         * @param qqAppId    qqAppId
         * @param appSecret appSecret
         * @return Builder
         */
        public Builder configQQ(String qqAppId, String appSecret) {
            this.qqAppId = qqAppId;
            this.qqAppSecret = appSecret;
            return this;
        }
        public RefineitShareConfiguration build() {
            return new RefineitShareConfiguration(Builder.this);
        }


    }
}
