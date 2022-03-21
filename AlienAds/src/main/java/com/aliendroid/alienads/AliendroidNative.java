package com.aliendroid.alienads;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.applovin.adview.AppLovinAdView;
import com.applovin.mediation.AppLovinExtras;
import com.applovin.mediation.ApplovinAdapter;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.ads.mediation.facebook.FacebookAdapter;
import com.google.ads.mediation.facebook.FacebookExtras;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.Mrec;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class AliendroidNative {

    public static MaxAdView adViewMax;
    public static AppLovinAdView adViewDiscovery;
    public static IronSourceBannerLayout adViewIron;
    public static Banner startAppBanner;
    public static Mrec startAppMrec;
    private static NativeAd nativeAd;
    public static BannerView unityBanner;

    public static void SmallNativeAdmob(Activity activity, String selectAds, String selectAdsBackup, FrameLayout layNative, String nativeId, String idBannerBackup, String Hpk1,
                                        String Hpk2, String Hpk3, String Hpk4, String Hpk5) {

        switch (selectAds) {
            case "ADMOB":
                AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
                builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {

                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }
                        switch (selectAdsBackup) {
                            case "APPLOVIN-M":
                                if (adViewMax != null) {
                                    adViewMax.destroy();
                                }
                                break;
                            case "MOPUB":

                                break;
                            case "IRON":
                                if (adViewIron != null) {
                                    adViewIron.isDestroyed();
                                }
                                break;
                            case "STARTAPP":
                                if (startAppBanner != null) {
                                    startAppBanner.hideBanner();
                                }
                                break;
                            case "APPLOVIN-D":
                                if (adViewDiscovery != null) {
                                    adViewDiscovery.destroy();
                                }
                                break;
                            case "UNITY":
                                if (unityBanner != null) {
                                    unityBanner.destroy();
                                }
                                break;
                        }
                        nativeAd = nativeAds;
                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(R.layout.admob_small_native, null);
                        populateNativeAdView(nativeAds, adView);
                        layNative.removeAllViews();
                        layNative.addView(adView);
                    }

                });

                VideoOptions videoOptions = new VideoOptions.Builder()
                        .build();

                NativeAdOptions adOptions = new NativeAdOptions.Builder()
                        .setVideoOptions(videoOptions)
                        .build();

                builder.withNativeAdOptions(adOptions);

                Bundle extrasApplovin = new AppLovinExtras.Builder()
                        .setMuteAudio(true)
                        .build();

                Bundle extras = new FacebookExtras()
                        .setNativeBanner(true)
                        .build();
                AdRequest request = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                        .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5)
                        .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                        .addNetworkExtrasBundle(ApplovinAdapter.class, extrasApplovin)
                        .build();
                AdLoader adLoader =
                        builder
                                .withAdListener(
                                        new AdListener() {
                                            @Override
                                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                                switch (selectAdsBackup) {
                                                    case "APPLOVIN-M":
                                                        adViewMax = new MaxAdView(idBannerBackup, activity);
                                                        final boolean isTablet = AppLovinSdkUtils.isTablet(activity);
                                                        final int heightPx = AppLovinSdkUtils.dpToPx(activity, isTablet ? 90 : 50);
                                                        adViewMax.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx));
                                                        layNative.addView(adViewMax);
                                                        adViewMax.loadAd();
                                                        break;
                                                    case "MOPUB":

                                                        break;
                                                    case "IRON":
                                                        adViewIron = IronSource.createBanner(activity, ISBannerSize.BANNER);
                                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                FrameLayout.LayoutParams.WRAP_CONTENT);
                                                        layNative.addView(adViewIron, 0, layoutParams);
                                                        IronSource.loadBanner(adViewIron, idBannerBackup);
                                                        break;
                                                    case "STARTAPP":
                                                        startAppBanner = new Banner(activity);
                                                        RelativeLayout.LayoutParams bannerParameters =
                                                                new RelativeLayout.LayoutParams(
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                                        layNative.addView(startAppBanner, bannerParameters);
                                                        break;
                                                    case "APPLOVIN-D":
                                                        AdRequest.Builder builder = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                                                                .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5);
                                                        Bundle bannerExtras = new Bundle();
                                                        bannerExtras.putString("zone_id", idBannerBackup);
                                                        builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                                                        boolean isTablet2 = AppLovinSdkUtils.isTablet(activity);
                                                        AppLovinAdSize adSize = isTablet2 ? AppLovinAdSize.LEADER : AppLovinAdSize.BANNER;
                                                        adViewDiscovery = new AppLovinAdView(adSize, activity);
                                                        layNative.addView(adViewDiscovery);
                                                        adViewDiscovery.loadNextAd();
                                                        break;
                                                    case "UNITY":
                                                        unityBanner = new BannerView(activity, idBannerBackup, new UnityBannerSize(320, 50));
                                                        unityBanner.load();
                                                        layNative.addView(unityBanner);
                                                        break;
                                                }
                                            }
                                        })
                                .build();
                adLoader.loadAd(request);
                break;
            case "APPLOVIN-M":
                adViewMax = new MaxAdView(nativeId, activity);
                final boolean isTablet = AppLovinSdkUtils.isTablet(activity);
                final int heightPx = AppLovinSdkUtils.dpToPx(activity, isTablet ? 90 : 50);
                adViewMax.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx));
                layNative.addView(adViewMax);
                adViewMax.loadAd();
                break;
            case "MOPUB":

                break;
            case "IRON":
                adViewIron = IronSource.createBanner(activity, ISBannerSize.BANNER);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                layNative.addView(adViewIron, 0, layoutParams);
                IronSource.loadBanner(adViewIron, nativeId);
                break;
            case "STARTAPP":
                startAppBanner = new Banner(activity);
                RelativeLayout.LayoutParams bannerParameters =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layNative.addView(startAppBanner, bannerParameters);
                break;
            case "APPLOVIN-D":
                AdRequest.Builder builder2 = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                        .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5);
                Bundle bannerExtras = new Bundle();
                bannerExtras.putString("zone_id", nativeId);
                builder2.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                boolean isTablet2 = AppLovinSdkUtils.isTablet(activity);
                AppLovinAdSize adSize = isTablet2 ? AppLovinAdSize.LEADER : AppLovinAdSize.BANNER;
                adViewDiscovery = new AppLovinAdView(adSize, activity);
                layNative.addView(adViewDiscovery);
                adViewDiscovery.loadNextAd();
                break;
        }


    }

    public static void SmallNativeGoogleAds(Activity activity, String selectAds, String selectAdsBackup, FrameLayout layNative, String nativeId, String idBannerBackup) {

        switch (selectAds) {
            case "GOOGLE-ADS":
                AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
                builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {

                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }
                        switch (selectAdsBackup) {
                            case "APPLOVIN-M":
                                if (adViewMax != null) {
                                    adViewMax.destroy();
                                }
                                break;
                            case "MOPUB":

                                break;
                            case "IRON":
                                if (adViewIron != null) {
                                    adViewIron.isDestroyed();
                                }
                                break;
                            case "STARTAPP":
                                if (startAppBanner != null) {
                                    startAppBanner.hideBanner();
                                }
                                break;
                            case "APPLOVIN-D":
                                if (adViewDiscovery != null) {
                                    adViewDiscovery.destroy();
                                }
                                break;
                            case "UNITY":
                                if (unityBanner != null) {
                                    unityBanner.destroy();
                                }
                                break;
                        }
                        nativeAd = nativeAds;
                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(R.layout.admob_small_native, null);
                        populateNativeAdView(nativeAds, adView);
                        layNative.removeAllViews();
                        layNative.addView(adView);
                    }

                });

                VideoOptions videoOptions = new VideoOptions.Builder()
                        .build();

                NativeAdOptions adOptions = new NativeAdOptions.Builder()
                        .setVideoOptions(videoOptions)
                        .build();

                builder.withNativeAdOptions(adOptions);
                Bundle extrasApplovin = new AppLovinExtras.Builder()
                        .setMuteAudio(true)
                        .build();

                Bundle extras = new FacebookExtras()
                        .setNativeBanner(true)
                        .build();
                AdRequest adRequest = new AdRequest.Builder()
                        .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                        .addNetworkExtrasBundle(ApplovinAdapter.class, extrasApplovin)
                        .build();
                AdLoader adLoader =
                        builder
                                .withAdListener(
                                        new AdListener() {
                                            @Override
                                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                                switch (selectAdsBackup) {
                                                    case "APPLOVIN-M":
                                                        adViewMax = new MaxAdView(idBannerBackup, activity);
                                                        final boolean isTablet = AppLovinSdkUtils.isTablet(activity);
                                                        final int heightPx = AppLovinSdkUtils.dpToPx(activity, isTablet ? 90 : 50);
                                                        adViewMax.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx));
                                                        layNative.addView(adViewMax);
                                                        adViewMax.loadAd();
                                                        break;
                                                    case "MOPUB":

                                                        break;
                                                    case "IRON":
                                                        adViewIron = IronSource.createBanner(activity, ISBannerSize.BANNER);
                                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                FrameLayout.LayoutParams.WRAP_CONTENT);
                                                        layNative.addView(adViewIron, 0, layoutParams);
                                                        IronSource.loadBanner(adViewIron, idBannerBackup);
                                                        break;
                                                    case "STARTAPP":
                                                        startAppBanner = new Banner(activity);
                                                        RelativeLayout.LayoutParams bannerParameters =
                                                                new RelativeLayout.LayoutParams(
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                                        layNative.addView(startAppBanner, bannerParameters);
                                                        break;
                                                    case "APPLOVIN-D":
                                                        AdRequest.Builder builder = new AdRequest.Builder();
                                                        Bundle bannerExtras = new Bundle();
                                                        bannerExtras.putString("zone_id", idBannerBackup);
                                                        builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                                                        boolean isTablet2 = AppLovinSdkUtils.isTablet(activity);
                                                        AppLovinAdSize adSize = isTablet2 ? AppLovinAdSize.LEADER : AppLovinAdSize.BANNER;
                                                        adViewDiscovery = new AppLovinAdView(adSize, activity);
                                                        layNative.addView(adViewDiscovery);
                                                        adViewDiscovery.loadNextAd();
                                                        break;
                                                    case "UNITY":
                                                        unityBanner = new BannerView(activity, idBannerBackup, new UnityBannerSize(320, 50));
                                                        unityBanner.load();
                                                        layNative.addView(unityBanner);
                                                        break;
                                                }
                                            }
                                        })
                                .build();
                adLoader.loadAd(adRequest);
                break;
            case "APPLOVIN-M":
                adViewMax = new MaxAdView(nativeId, activity);
                final boolean isTablet = AppLovinSdkUtils.isTablet(activity);
                final int heightPx = AppLovinSdkUtils.dpToPx(activity, isTablet ? 90 : 50);
                adViewMax.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx));
                layNative.addView(adViewMax);
                adViewMax.loadAd();
                break;
            case "MOPUB":

                break;
            case "IRON":
                adViewIron = IronSource.createBanner(activity, ISBannerSize.BANNER);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                layNative.addView(adViewIron, 0, layoutParams);
                IronSource.loadBanner(adViewIron, nativeId);
                break;
            case "STARTAPP":
                startAppBanner = new Banner(activity);
                RelativeLayout.LayoutParams bannerParameters =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layNative.addView(startAppBanner, bannerParameters);
                break;
            case "APPLOVIN-D":
                AdRequest.Builder builder2 = new AdRequest.Builder();
                Bundle bannerExtras = new Bundle();
                bannerExtras.putString("zone_id", nativeId);
                builder2.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                boolean isTablet2 = AppLovinSdkUtils.isTablet(activity);
                AppLovinAdSize adSize = isTablet2 ? AppLovinAdSize.LEADER : AppLovinAdSize.BANNER;
                adViewDiscovery = new AppLovinAdView(adSize, activity);
                layNative.addView(adViewDiscovery);
                adViewDiscovery.loadNextAd();
                break;
        }


    }

    public static void MediumNative(Activity activity, String selectAds, String selectAdsBackup, FrameLayout layNative, String nativeId, String idBannerBackup, String Hpk1,
                                    String Hpk2, String Hpk3, String Hpk4, String Hpk5) {

        switch (selectAds) {
            case "ADMOB":
                AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
                builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {

                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }
                        switch (selectAdsBackup) {
                            case "APPLOVIN-M":
                                if (adViewMax != null) {
                                    adViewMax.destroy();
                                }
                                break;
                            case "MOPUB":

                                break;
                            case "IRON":
                                if (adViewIron != null) {
                                    adViewIron.isDestroyed();
                                }
                                break;
                            case "STARTAPP":
                                if (startAppMrec != null) {
                                    startAppMrec.hideBanner();
                                }
                                break;
                            case "APPLOVIN-D":
                                if (adViewDiscovery != null) {
                                    adViewDiscovery.destroy();
                                }
                                break;
                        }
                        nativeAd = nativeAds;
                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(R.layout.admob_big_native, null);
                        populateNativeAdView(nativeAds, adView);
                        layNative.removeAllViews();
                        layNative.addView(adView);
                    }


                });

                VideoOptions videoOptions = new VideoOptions.Builder()
                        .build();

                NativeAdOptions adOptions = new NativeAdOptions.Builder()
                        .setVideoOptions(videoOptions)
                        .build();

                builder.withNativeAdOptions(adOptions);

                Bundle extrasApplovin = new AppLovinExtras.Builder()
                        .setMuteAudio(true)
                        .build();

                Bundle extras = new FacebookExtras()
                        .setNativeBanner(true)
                        .build();
                AdRequest request = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                        .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5)
                        .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                        .addNetworkExtrasBundle(ApplovinAdapter.class, extrasApplovin)
                        .build();
                AdLoader adLoader =
                        builder
                                .withAdListener(
                                        new AdListener() {
                                            @Override
                                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                                switch (selectAdsBackup) {
                                                    case "APPLOVIN-M": {
                                                        adViewMax = new MaxAdView(idBannerBackup, MaxAdFormat.MREC, activity);
                                                        final int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
                                                        final int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
                                                        adViewMax.setLayoutParams(new ConstraintLayout.LayoutParams(widthPx, heightPx));
                                                        layNative.addView(adViewMax);
                                                        adViewMax.loadAd();
                                                        break;
                                                    }
                                                    case "MOPUB":

                                                        break;
                                                    case "IRON":
                                                        adViewIron = IronSource.createBanner(activity, ISBannerSize.RECTANGLE);
                                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                FrameLayout.LayoutParams.WRAP_CONTENT);
                                                        layNative.addView(adViewIron, 0, layoutParams);
                                                        IronSource.loadBanner(adViewIron, idBannerBackup);
                                                        break;
                                                    case "STARTAPP":
                                                        startAppMrec = new Mrec(activity);
                                                        RelativeLayout.LayoutParams bannerParameters =
                                                                new RelativeLayout.LayoutParams(
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                                        layNative.addView(startAppMrec, bannerParameters);
                                                        break;
                                                    case "APPLOVIN-D":
                                                        AdRequest.Builder builder = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                                                                .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5);
                                                        Bundle bannerExtras = new Bundle();
                                                        bannerExtras.putString("zone_id", idBannerBackup);
                                                        builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                                                        adViewDiscovery = new AppLovinAdView(AppLovinAdSize.MREC, activity);
                                                        layNative.addView(adViewDiscovery);
                                                        adViewDiscovery.loadNextAd();
                                                        break;
                                                    case "UNITY":
                                                        unityBanner = new BannerView(activity, idBannerBackup, new UnityBannerSize(320, 50));
                                                        unityBanner.load();
                                                        layNative.addView(unityBanner);
                                                        break;
                                                }
                                            }
                                        })
                                .build();
                adLoader.loadAd(request);
                break;
            case "APPLOVIN-M":
                adViewMax = new MaxAdView(nativeId, MaxAdFormat.MREC, activity);
                final int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
                final int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
                adViewMax.setLayoutParams(new ConstraintLayout.LayoutParams(widthPx, heightPx));
                layNative.addView(adViewMax);
                adViewMax.loadAd();
                break;
            case "MOPUB":

                break;
            case "IRON":
                adViewIron = IronSource.createBanner(activity, ISBannerSize.RECTANGLE);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                layNative.addView(adViewIron, 0, layoutParams);
                IronSource.loadBanner(adViewIron, nativeId);
                break;
            case "STARTAPP":
                startAppMrec = new Mrec(activity);
                RelativeLayout.LayoutParams bannerParameters =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layNative.addView(startAppMrec, bannerParameters);
                break;
            case "APPLOVIN-D":
                AdRequest.Builder builder2 = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                        .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5);
                Bundle bannerExtras = new Bundle();
                bannerExtras.putString("zone_id", nativeId);
                builder2.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                adViewDiscovery = new AppLovinAdView(AppLovinAdSize.MREC, activity);
                layNative.addView(adViewDiscovery);
                adViewDiscovery.loadNextAd();
                break;
            case "UNITY":
                unityBanner = new BannerView(activity, nativeId, new UnityBannerSize(320, 50));
                unityBanner.load();
                layNative.addView(unityBanner);
                break;
        }


    }

    public static void MediumNativeGoogleAds(Activity activity, String selectAds, String selectAdsBackup, FrameLayout layNative, String nativeId, String idBannerBackup
    ) {

        switch (selectAds) {
            case "GOOGLE-ADS":
                AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
                builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {

                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }
                        switch (selectAdsBackup) {
                            case "APPLOVIN-M":
                                if (adViewMax != null) {
                                    adViewMax.destroy();
                                }
                                break;
                            case "MOPUB":

                                break;
                            case "IRON":
                                if (adViewIron != null) {
                                    adViewIron.isDestroyed();
                                }
                                break;
                            case "STARTAPP":
                                if (startAppMrec != null) {
                                    startAppMrec.hideBanner();
                                }
                                break;
                            case "APPLOVIN-D":
                                if (adViewDiscovery != null) {
                                    adViewDiscovery.destroy();
                                }
                                break;
                        }
                        nativeAd = nativeAds;
                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(R.layout.admob_big_native, null);
                        populateNativeAdView(nativeAds, adView);
                        layNative.removeAllViews();
                        layNative.addView(adView);
                    }


                });

                VideoOptions videoOptions = new VideoOptions.Builder()
                        .build();

                NativeAdOptions adOptions = new NativeAdOptions.Builder()
                        .setVideoOptions(videoOptions)
                        .build();

                builder.withNativeAdOptions(adOptions);
                Bundle extrasApplovin = new AppLovinExtras.Builder()
                        .setMuteAudio(true)
                        .build();

                Bundle extras = new FacebookExtras()
                        .setNativeBanner(true)
                        .build();
                AdRequest adRequest = new AdRequest.Builder()
                        .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                        .addNetworkExtrasBundle(ApplovinAdapter.class, extrasApplovin)
                        .build();
                AdLoader adLoader =
                        builder
                                .withAdListener(
                                        new AdListener() {
                                            @Override
                                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                                switch (selectAdsBackup) {
                                                    case "APPLOVIN-M": {
                                                        adViewMax = new MaxAdView(idBannerBackup, MaxAdFormat.MREC, activity);
                                                        final int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
                                                        final int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
                                                        adViewMax.setLayoutParams(new ConstraintLayout.LayoutParams(widthPx, heightPx));
                                                        layNative.addView(adViewMax);
                                                        adViewMax.loadAd();
                                                        break;
                                                    }
                                                    case "MOPUB":

                                                        break;
                                                    case "IRON":
                                                        adViewIron = IronSource.createBanner(activity, ISBannerSize.RECTANGLE);
                                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                FrameLayout.LayoutParams.WRAP_CONTENT);
                                                        layNative.addView(adViewIron, 0, layoutParams);
                                                        IronSource.loadBanner(adViewIron, idBannerBackup);
                                                        break;
                                                    case "STARTAPP":
                                                        startAppMrec = new Mrec(activity);
                                                        RelativeLayout.LayoutParams bannerParameters =
                                                                new RelativeLayout.LayoutParams(
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                                        layNative.addView(startAppMrec, bannerParameters);
                                                        break;
                                                    case "APPLOVIN-D":
                                                        AdRequest.Builder builder = new AdRequest.Builder();
                                                        Bundle bannerExtras = new Bundle();
                                                        bannerExtras.putString("zone_id", idBannerBackup);
                                                        builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                                                        adViewDiscovery = new AppLovinAdView(AppLovinAdSize.MREC, activity);
                                                        layNative.addView(adViewDiscovery);
                                                        adViewDiscovery.loadNextAd();
                                                        break;
                                                    case "UNITY":
                                                        unityBanner = new BannerView(activity, idBannerBackup, new UnityBannerSize(320, 50));
                                                        unityBanner.load();
                                                        layNative.addView(unityBanner);
                                                        break;
                                                }
                                            }
                                        })
                                .build();
                adLoader.loadAd(adRequest);
                break;
            case "APPLOVIN-M":
                adViewMax = new MaxAdView(nativeId, MaxAdFormat.MREC, activity);
                final int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
                final int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
                adViewMax.setLayoutParams(new ConstraintLayout.LayoutParams(widthPx, heightPx));
                layNative.addView(adViewMax);
                adViewMax.loadAd();
                break;
            case "MOPUB":

                break;
            case "IRON":
                adViewIron = IronSource.createBanner(activity, ISBannerSize.RECTANGLE);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                layNative.addView(adViewIron, 0, layoutParams);
                IronSource.loadBanner(adViewIron, nativeId);
                break;
            case "STARTAPP":
                startAppMrec = new Mrec(activity);
                RelativeLayout.LayoutParams bannerParameters =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layNative.addView(startAppMrec, bannerParameters);
                break;
            case "APPLOVIN-D":
                AdRequest.Builder builder2 = new AdRequest.Builder();
                Bundle bannerExtras = new Bundle();
                bannerExtras.putString("zone_id", nativeId);
                builder2.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                adViewDiscovery = new AppLovinAdView(AppLovinAdSize.MREC, activity);
                layNative.addView(adViewDiscovery);
                adViewDiscovery.loadNextAd();
                break;
            case "UNITY":
                unityBanner = new BannerView(activity, nativeId, new UnityBannerSize(320, 50));
                unityBanner.load();
                layNative.addView(unityBanner);
                break;
        }


    }

    private static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }


}
