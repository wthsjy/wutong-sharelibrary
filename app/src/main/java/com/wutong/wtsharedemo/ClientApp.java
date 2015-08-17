package com.wutong.wtsharedemo;

import android.app.Application;

import com.wutong.share.library.RefineitShareConfiguration;
import com.wutong.share.library.RefineitShareLib;

/**
 * Created by Administrator on 2015/8/11.
 */
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initShare();
    }

    private void initShare() {
        RefineitShareConfiguration configuration = new RefineitShareConfiguration.Builder(this)
                .configSina("712352211", "此参数不需要，只做保留")
                .configWeiChat("wx1c08de68f28d82e6", "此参数不需要，只做保留")
                .configQQ("222222","此参数不需要，只做保留")
                .build();
        RefineitShareLib.getInstance().init(configuration);

    }
}
