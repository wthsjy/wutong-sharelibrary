# Refineit-sharelibrary

简化 QQ,QZone,微信，sina微博 分享 类库

## 功能

- QQ QZone分享 
- 微信分享
- 微博分享
- ...
- 持续更新中...

## 更新日志

#### v1.0

- 初始导入


## 如何使用？


Step 1: Gradle  依赖

在 build.gradle 中添加:

```groovy
 compile 'com.wutong.share.library:Refineit-sharelibrary:1.0'
```
===================







Step 2: 在Application 中初始化
----

```java
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initShare();
    }

    private void initShare() {
        RefineitShareConfiguration configuration = new RefineitShareConfiguration.Builder(this)
                .configSina("appKey", "此参数不需要，只做保留")
                .configWeiChat("appKey", "此参数不需要，只做保留")
                .configQQ("appId","此参数不需要，只做保留")
                .build();
        RefineitShareLib.getInstance().init(configuration);

    }
}


```

Step 3: 在 AndroidManifest.xml 中添加权限
----

```xml
 <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!-- 用于调用 JNI -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

Step 3: 在 AndroidManifest.xml 添加必要的Activity 注册
----

```xml
<!-- 新浪微博分享 -->
 <!-- 必须注册在微博授权，分享微博时候用到 -->
        <activity
            android:name="com.sina.weibo.sdk.component.WeiboSdkBrowser"
            android:configChanges="keyboardHidden|orientation"
            android:exported="false"
            android:windowSoftInputMode="adjustResize" />
        <activity android:name="com.wutong.share.library.sina.RefineitShareSinaActivity">
            <intent-filter>
                <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
		
		
 
```
```xml
<!-- 微信分享 -->
    <activity
            android:name=".wxapi.WXEntryActivity"
            android:exported="true"
            android:label="@string/title_activity_wxentry"
            android:launchMode="singleTop"/>
		
		
 
```

```xml
<!-- QQ分享 注意此处替换 "tencent222222" 为  "tencent你申请到的appId"  -->
<activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:configChanges="orientation|keyboardHidden">
        </activity>

        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data android:scheme="tencent222222" />
          
            </intent-filter>
        </activity>

        <activity
            android:name="com.wutong.share.library.qq.RefineitShareQQActivity"
            android:launchMode="singleTop"
            android:screenOrientation="portrait" />
		
 
```

Step 4: 添加微信的类文件  你的包名.wxapi.WXEntryActivity，注意 WXEntryActivity 需要  extends RefineitShareWeChatActivity 
----

Step 5: 调用方法
```java
		//新浪微博分享 只分享文字
		RefineitShareLib.getInstance().shareSinaText(Activity activity, String content);
		//新浪微博分享 只分享图片
        RefineitShareLib.getInstance().shareSinaImage(Activity activity, Bitmap bitmap);
		//新浪微博分享 分享图片与文字并存
		RefineitShareLib.getInstance().shareImagWithText(Activity activity, String content, Bitmap bitmap);
		//新浪微博分享 分享网页
        RefineitShareLib.getInstance().shareSinaWeb(Activity activity, String title, String description,
                             Bitmap thumbBitmap, Bitmap bitmap, String actionUrl);
        
```
```java
		//微信分享  只有文字
		RefineitShareLib.getInstance().shareWeChatText(Context context, boolean isFriendCircle, String title) ;
		//微信分享  只有图片
        RefineitShareLib.getInstance().shareWeChatImage(Context context, boolean isFriendCircle, Bitmap bitmap);
		//微信分享  网页 对象
		RefineitShareLib.getInstance().shareWeChatWeb(Context context, boolean isFriendCircle, 
							String title, String description, String webpageUrl, Bitmap thumbBitmap);
		
      
----
```java
		// 分享到QQ好友，纯图片
		RefineitShareLib.getInstance().shareQQImage(Context context, String localPath) ;
		//分享到QQ好友，默认 支持类型见参数说明
        RefineitShareLib.getInstance().shareQQ(Context context, String title, String 
								targetUrl, String summary, String imageUrl);
		//分享到QQ空间，默认 支持类型见参数说明
		RefineitShareLib.getInstance().shareQQZone(Context context, String title, String targetUrl, 
								String summary, ArrayList<String> imageArrayList);

		
      
----
## 联系方式 QQ: 258176257
