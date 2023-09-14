package net.bloodpressure.nice.instrument.free.ad;

import static net.bloodpressure.nice.instrument.free.constant.MyAppApiConfig.INTERSTITIAL_ID;

import android.app.Activity;
import android.content.Context;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;

import net.bloodpressure.nice.instrument.free.App;
import net.bloodpressure.nice.instrument.free.util.MyUtil;

public class InterstitialAd {
    public static ATInterstitial mInterstitialAd;
    private static Context context;
    private App.OnShowAdCompleteListener listener;

    public InterstitialAd(Context context, App.OnShowAdCompleteListener listener){
        this.listener = listener;
        InterstitialAd.context = context;
    }

    public void loadAd() {
        if (mInterstitialAd == null) {
            mInterstitialAd = new ATInterstitial(context, INTERSTITIAL_ID);
        }

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                listener.onAdLoaded();
            }

            @Override
            public void onInterstitialAdLoadFail(AdError adError) {
                //注意：禁止在此回调中执行广告的加载方法进行重试，否则会引起很多无用请求且可能会导致应用卡顿
                //AdError，请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_test?id=aderror
                listener.onFailedToLoad();
                MyUtil.MyLog("广告加载出错："+adError);

            }

            @Override
            public void onInterstitialAdClicked(ATAdInfo atAdInfo) {
                listener.onClicked();
            }

            @Override
            public void onInterstitialAdShow(ATAdInfo atAdInfo) {
                //ATAdInfo可区分广告平台以及获取广告平台的广告位ID等
                //请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_sdk_callback_access?id=callback_info
                listener.onShowAdComplete();
            }

            @Override
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                listener.TurnoffAds();
            }

            @Override
            public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) {
                //建议在此回调中调用load进行广告的加载，方便下一次广告的展示（不需要调用isAdReady()）
                mInterstitialAd.load();
            }

            @Override
            public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {
            }

            @Override
            public void onInterstitialAdVideoError(AdError adError) {
                //AdError，请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_test?id=aderror

            }
        });

        mInterstitialAd.load();
    }
    public static void showAd() {
   /*
     为了统计场景到达率，相关信息可查阅 "https://docs.toponad.com/#/zh-cn/android/NetworkAccess/scenario/scenario"
     在满足广告触发条件时调用“进入广告场景”方法，比如：
     ** 广告场景是在清理结束后弹出广告，则在清理结束时调用；
     * 1、先调用 "entryAdScenario"
     * 2、在调用 "isAdReady" 是否可展示
     * 3、最后调用 "show" 展示
     * 4、scenario 传入场景id后台场景管理才会有数据显示
     */
        ATInterstitial.entryAdScenario(INTERSTITIAL_ID, null);
        if (mInterstitialAd.isAdReady()) {
            mInterstitialAd.show((Activity) context);
        }
    }
}
