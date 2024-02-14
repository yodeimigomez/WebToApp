package com.webviewgold.myappname;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OSDeviceState;

import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import androidx.core.content.FileProvider;
import androidx.core.widget.TextViewCompat;

import org.json.JSONException;
import org.json.JSONObject;

import static com.webviewgold.myappname.Config.ACTIVATE_PROGRESS_BAR;
import static com.webviewgold.myappname.Config.DISABLE_DARK_MODE;
import static com.webviewgold.myappname.Config.ENABLE_SWIPE_NAVIGATE;
import static com.webviewgold.myappname.Config.ENABLE_PULL_REFRESH;
import static com.webviewgold.myappname.Config.ENABLE_ZOOM;
import static com.webviewgold.myappname.Config.EXIT_APP_DIALOG;
import static com.webviewgold.myappname.Config.HIDE_HORIZONTAL_SCROLLBAR;
import static com.webviewgold.myappname.Config.HIDE_NAVIGATION_BAR_IN_LANDSCAPE;
import static com.webviewgold.myappname.Config.HIDE_VERTICAL_SCROLLBAR;
import static com.webviewgold.myappname.Config.INCREMENT_WITH_REDIRECTS;
import static com.webviewgold.myappname.Config.MAX_TEXT_ZOOM;
import static com.webviewgold.myappname.Config.PREVENT_SLEEP;
import static com.webviewgold.myappname.Config.REMAIN_SPLASH_OPTION;
import static com.webviewgold.myappname.Config.SPECIAL_LINK_HANDLING_OPTIONS;
import static com.webviewgold.myappname.Config.SPLASH_SCREEN_ACTIVATED;
import static com.webviewgold.myappname.Config.downloadableExtension;
import static com.webviewgold.myappname.WebViewApp.context;

import com.webviewgold.myappname.R;
public class MainActivity extends AppCompatActivity
        implements OSSubscriptionObserver,
        PurchasesUpdatedListener {

    public static boolean HIDE_ADS_FOR_PURCHASE = false;
    public static final int PERMISSION_REQUEST_CODE = 9541;
    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";

    private static final String INDEX_FILE = "file:///android_asset/local-html/index.html";
    private static final int CODE_AUDIO_CHOOSER = 5678;
    private boolean isErrorPageLoaded = false;
    private static final String ONESIGNAL_APP_ID = BuildConfig.ONESIGNAL_APP_ID;
    private CustomWebView webView;
    private WebView mWebviewPop;
    private SharedPreferences preferences;
    private SharedPreferences preferencesColor;
    private RelativeLayout mContainer;
    private RelativeLayout windowContainer;

    private View offlineLayout;


    public static final int REQUEST_CODE_QR_SCAN = 1234;

    private AdView mAdView;
    private LinearLayout facebookBannerContainer;
    private com.facebook.ads.AdView facebookAdView;
    InterstitialAd mInterstitialAd;
    com.facebook.ads.InterstitialAd facebookInterstitialAd;
    SwipeRefreshLayout mySwipeRefreshLayout;
    public static final int MULTIPLE_PERMISSIONS = 10;
    public ProgressBar progressBar;
    private String deepLinkingURL;
    private BillingClient billingClient;
    int mCount = -1;

    private static final String TAG = ">>>>>>>>>>>";
    private String mCM, mVM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;
    public String hostpart;
    private boolean disableAdMob = false;
    private boolean isConsumable = false;
    private String successUrl = "", failUrl = "";
    private FrameLayout adLayout;
    private boolean offlineFileLoaded = false;
    private boolean isNotificationURL = false;
    private boolean extendediap = true;
    public String uuid = "";
    public static Context mContext;
    private String firebaseUserToken = "";
    private boolean isRedirected = false;
    private boolean notificationPromptShown = false;


    static long TimeStamp = 0;
    static boolean isInBackGround = false;
    private static boolean connectedNow = false;

    // NFC
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter writeTagFilters[];
    private Tag myTag;
    private boolean NFCenabled = false;
    private boolean readModeNFC = false;
    private boolean writeModeNFC = false;
    private String textToWriteNFC = "";
    private boolean SPLASH_SCREEN_ACTIVE = false;
    // Social media login user agents
    public static final String USER_AGENT_GOOGLE = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.101 Mobile Safari/537.36";
    public static final String USER_AGENT_FB = "Mozilla/5.0 (Linux; U; Android 2.2) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    // Manual Cookie Sync
    private final Handler cookieSyncHandler = new Handler();
    private Runnable cookieSyncRunnable;
    private boolean onResumeCalled = false;
    private boolean cookieSyncOn = false;

    // Scanning Mode
    private boolean scanningModeOn = false;
    private boolean persistentScanningMode = false;
    private float previousScreenBrightness;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        uuid = Settings.System.getString(super.getContentResolver(), Settings.Secure.ANDROID_ID);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencesColor = PreferenceManager.getDefaultSharedPreferences(this);

        onResumeCalled = false;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String ret = settings.getString("disableAdMobDone", "default");

        if (ret == "removed") {
            disableAdMob = true;
        }

        if (isRooted() && Config.BLOCK_ROOTED_DEVICES) {
            showRootedErrorMessage();
        }
        if (HIDE_NAVIGATION_BAR_IN_LANDSCAPE && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        if(DISABLE_DARK_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Config.blackStatusBarText) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (PREVENT_SLEEP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        super.onCreate(savedInstanceState);
        if (SPLASH_SCREEN_ACTIVATED) {
            SPLASH_SCREEN_ACTIVE = true;
            startActivity(new Intent(getApplicationContext(), SplashScreen.class));
        }

        // Support the cut out background when in landscape mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
            //Bitmap bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888);
            //bitmap.eraseColor(getResources().getColor(R.color.colorPrimaryDark));
            //BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            //getWindow().setBackgroundDrawable(bitmapDrawable);
        }

        setContentView(R.layout.activity_main);
        verifyStoragePermission(this);
        if (Config.FIREBASE_PUSH_ENABLED || Config.PUSH_ENABLED) {
            verifyNotificationPermission(this);
        }

        if (Config.requireLocation) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
                }
            }, 7000); // delay of 7 seconds
        }
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        if (Build.VERSION.SDK_INT > 23) {
//            builder.detectFileUriExposure();
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            // Permission is granted
//        }
//        else {
//            //Permission is not granted so you have to request it
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    888);
//        }


        if (NFCenabled) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.NFC},
                        PERMISSION_REQUEST_CODE);
            } else {
                initNfc();
            }
        }

        if (Config.FIREBASE_PUSH_ENABLED) {
            fetchFCMToken();
        }

        RelativeLayout main = findViewById(R.id.main);
        adLayout = findViewById(R.id.ad_layout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases()
                .build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                            (billingResult1, purchasesList) -> {

                                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "is purchased : " + (purchasesList != null && !purchasesList.isEmpty()));

                                if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK
                                        && purchasesList != null && !purchasesList.isEmpty()) {

                                    boolean productFound = true;
                                    if (productFound) {
                                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "purchased " + String.valueOf(true));
                                        HIDE_ADS_FOR_PURCHASE = true;
                                        AlertManager.purchaseState(getApplicationContext(), true);
                                        if (AlertManager.isPurchased(getApplicationContext())) {
                                            HIDE_ADS_FOR_PURCHASE = true;
                                        }
                                    } else {
                                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "purchased " + String.valueOf(false));
                                        HIDE_ADS_FOR_PURCHASE = false;
                                        AlertManager.purchaseState(getApplicationContext(), false);
                                        if (AlertManager.isPurchased(getApplicationContext())) {
                                            HIDE_ADS_FOR_PURCHASE = true;
                                        }
                                    }
                                } else {
                                    if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "purchased " + String.valueOf(false));
                                    HIDE_ADS_FOR_PURCHASE = false;
                                    AlertManager.purchaseState(getApplicationContext(), false);
                                    if (AlertManager.isPurchased(getApplicationContext())) {
                                        HIDE_ADS_FOR_PURCHASE = true;
                                    }
                                }
                            });
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null &&
                (intent.getData().getScheme().equals("http") || intent.getData().getScheme().equals("https"))) {
            Uri data = intent.getData();

            String fullDeepLinkingURL = data.getScheme() + "://" + data.getHost() + data.getPath();

            // Get the entire query string, if any
            String query = data.getQuery();

            // If the query string is not null or empty, append it to the URL
            if (query != null && !query.isEmpty()) {
                fullDeepLinkingURL += "?" + query; // The query string already contains the correct format
            }

            deepLinkingURL = fullDeepLinkingURL;
        }/* else if (intent != null && intent.getData() != null && (intent.getData().getScheme().equals("https"))) {
            Uri data = intent.getData();
            List<String> pathSegments = data.getPathSegments();
            if (pathSegments.size() > 0) {
                deepLinkingURL = pathSegments.get(0).substring(5);
                String fulldeeplinkingurl = data.getPath().toString();
                fulldeeplinkingurl = fulldeeplinkingurl.replace("/link=", "");
                deepLinkingURL = fulldeeplinkingurl;
            }
        }*/

        if (intent != null) {
            Bundle extras = getIntent().getExtras();
            String URL = null;
            if (extras != null) {
                URL = extras.getString("ONESIGNAL_URL");
            }
            if (URL != null && !URL.equalsIgnoreCase("")) {
                isNotificationURL = true;
                deepLinkingURL = URL;
            } else isNotificationURL = false;
        }


        final String myOSurl = Config.PURCHASECODE;

        if (Config.PUSH_ENABLED) {
            OneSignal.addSubscriptionObserver(this);
            OneSignal.setInAppMessageClickHandler(osInAppMessageAction -> {
                osInAppMessageAction.getClickUrl();
                webView.loadUrl(osInAppMessageAction.getClickUrl());
            });
            OneSignal.setNotificationOpenedHandler(
                    new OneSignal.OSNotificationOpenedHandler() {
                        @Override
                        public void notificationOpened(OSNotificationOpenedResult result) {

                            String actionId = result.getAction().getActionId();

                            String title = result.getNotification().getTitle();
                            if (title != null) {
                                if (BuildConfig.IS_DEBUG_MODE) Log.d("RESULTTITLE", title);
                            }

                            JSONObject data = result.getNotification().getAdditionalData();
                            
                             String notification_topic;
                             if (data != null) {
                                 notification_topic = data.optString("trigger", null);
                                 if (notification_topic != null) {
                                     OneSignal.addTrigger("trigger", notification_topic);
                                 }
                             }

                            
                            String launchUrl = result.getNotification().getLaunchURL();
                            String urlString = null;
                            if (data != null && data.has("url")) {
                                try {
                                    urlString = data.getString("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            // if (BuildConfig.IS_DEBUG_MODE) Log.d("OneSignal_Deeplinking", urlString); //Deeplinking URL of OneSignal (url key-value pair)

                            if (urlString != null) {
                                handleURl(urlString);
                            } else {
                                if (launchUrl != null) {
                                    openInExternalBrowser(launchUrl);
                                }

                            }


                            long diffseconds = TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis() - TimeStamp);

                            if (isInBackGround && diffseconds >= 3)
                                foreground(launchUrl, urlString);


                        }
                    });
        }

        if (savedInstanceState == null) {
            AlertManager.appLaunched(this);
        }

        mAdView = findViewById(R.id.adView);
        if (Config.USE_FACEBOOK_ADS) {
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "attempting to create ad");
            facebookAdView = new com.facebook.ads.AdView(this,
                    getString(R.string.facebook_banner_footer),
                    AdSize.BANNER_HEIGHT_50);
        }

        AdRequest adRequest = new AdRequest.Builder()
                .build();




        if (Config.SHOW_BANNER_AD && !disableAdMob) {
            if (Config.USE_FACEBOOK_ADS) {
                adLayout.removeAllViews();
                adLayout.addView(facebookAdView);
                adLayout.setVisibility(View.VISIBLE);
                facebookAdView.loadAd();
            } else {
                mAdView.loadAd(adRequest);
                adLayout.setVisibility(View.VISIBLE);
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        if (!HIDE_ADS_FOR_PURCHASE) {
                            mAdView.setVisibility(View.VISIBLE);
                            adLayout.setVisibility(View.VISIBLE);
                        } else {
                            mAdView.setVisibility(View.GONE);
                            adLayout.setVisibility(View.GONE);
                        }
                    }


                    @Override
                    public void onAdOpened() {
                        if (!HIDE_ADS_FOR_PURCHASE) {
                            mAdView.setVisibility(View.VISIBLE);
                            adLayout.setVisibility(View.VISIBLE);
                        } else {
                            mAdView.setVisibility(View.GONE);
                            adLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAdClosed() {
                    }
                });
            }
        } else {
            mAdView.setVisibility(View.GONE);
            adLayout.setVisibility(View.GONE);
        }

        if (!HIDE_ADS_FOR_PURCHASE) {
            if (Config.USE_FACEBOOK_ADS) {
                facebookInterstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.facebook_interstitial_full_screen));
                com.facebook.ads.InterstitialAdListener interstitialAdListener = new com.facebook.ads.InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {
                        // Interstitial ad displayed callback
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Interstitial ad displayed.");
                    }

                    @Override
                    public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                        // Interstitial dismissed callback
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Interstitial ad dismissed.");
                    }

                    @Override
                    public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
                        // Ad error callback
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(com.facebook.ads.Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");

                    }

                    @Override
                    public void onAdClicked(com.facebook.ads.Ad ad) {
                        // Ad clicked callback
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Interstitial ad clicked!");
                    }

                    @Override
                    public void onLoggingImpression(com.facebook.ads.Ad ad) {
                        // Ad impression logged callback
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Interstitial ad impression logged!");
                    }
                };

                // For auto play video ads, it's recommended to load the ad
                // at least 30 seconds before it is shown
                facebookInterstitialAd.loadAd(
                        facebookInterstitialAd.buildLoadAdConfig()
                                .withAdListener(interstitialAdListener)
                                .build());
            }
        }

        webView = findViewById(R.id.webView);
        mContainer = findViewById(R.id.web_container);
        windowContainer = findViewById(R.id.window_container);
        if (Config.HARDWARE_ACCELERATION){
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else{
            webView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setGestureDetector(new GestureDetector(new CustomeGestureDetector()));


        if (BuildConfig.IS_DEBUG_MODE) {
            WebView.setWebContentsDebuggingEnabled(true);
            webView.setWebContentsDebuggingEnabled(true);
        }
        else {
            WebView.setWebContentsDebuggingEnabled(false);
            webView.setWebContentsDebuggingEnabled(false);
        }

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        if (!ENABLE_PULL_REFRESH) {
            mySwipeRefreshLayout.setEnabled(false);

        }

        if(HIDE_VERTICAL_SCROLLBAR){
            webView.setVerticalScrollBarEnabled(false);
        }
        if(HIDE_HORIZONTAL_SCROLLBAR){
            webView.setHorizontalScrollBarEnabled(false);
        }

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (ENABLE_PULL_REFRESH) {
                            webView.reload();

                        }
                        mySwipeRefreshLayout.setRefreshing(false);

                    }
                }
        );

        offlineLayout = findViewById(R.id.offline_layout);
        setOfflineScreenBackgroundColor();

        this.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.launchLoadingSignBackground));
        progressBar = findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

        final Button tryAgainButton = findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Try again!");
                webView.setVisibility(View.GONE);
                loadMainUrl();
            }
        });

        webView.setWebViewClient(new AdvanceWebViewClient());
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setUseWideViewPort(true);

        Context appContext = this;

        // Collect the App Name to use as the title for Javascript Dialogs
        final String appName;
        String appName1;
        try {
            appName1 = appContext.getApplicationInfo().loadLabel(appContext.getPackageManager()).toString();
        } catch (Exception e) {
            // If unsuccessful in collecting the app name, set the name to the page title.
            appName1 = webView.getTitle();
        }
        appName = appName1;

        webView.setWebChromeClient(new AdvanceWebChromeClient() {

            // Functions to support alert(), confirm() and prompt() Javascript Dialogs

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
                        setTitle(appName).
                        setMessage(message).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        }).create();
                dialog.show();
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(view.getContext())
                        .setTitle(appName)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                b.show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                final EditText input = new EditText(appContext);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(defaultValue);
                new AlertDialog.Builder(appContext)
                        .setTitle(appName)
                        .setView(input)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm(input.getText().toString());
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .create()
                        .show();
                return true;
            }
        });


        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        registerForContextMenu(webView);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (ENABLE_ZOOM) {
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
        } else {
            webSettings.setBuiltInZoomControls(false);
        }
        if (Config.CLEAR_CACHE_ON_STARTUP) {
            //webSettings.setAppCacheEnabled(false);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            //webSettings.setAppCacheEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            if (Config.SHOW_ADSENSE_AD) {
                MobileAds.registerWebView(webView);
            }
        }
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowFileAccess(true);
        //webSettings.setLoadWithOverviewMode(true);
        //webSettings.setUseWideViewPort(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Custom Text Zoom
        if (MAX_TEXT_ZOOM > 0) {
            float systemTextZoom = getResources().getConfiguration().fontScale * 100;
            if (systemTextZoom > MAX_TEXT_ZOOM) {
                webView.getSettings().setTextZoom(MAX_TEXT_ZOOM);
            }
        }

        // Phone orientation setting for Android 8 (Oreo)
        if (webSettings.getUserAgentString().contains("Mobile") && android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            if (Config.PHONE_ORIENTATION == "auto") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            } else if (Config.PHONE_ORIENTATION == "portrait") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
            } else if (Config.PHONE_ORIENTATION == "landscape") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
            }
            // Phone orientation setting for all other Android versions
        } else if (webSettings.getUserAgentString().contains("Mobile")) {
            if (Config.PHONE_ORIENTATION == "auto") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            } else if (Config.PHONE_ORIENTATION == "portrait") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (Config.PHONE_ORIENTATION == "landscape") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            // Tablet/Other orientation setting
        } else {
            if (Config.TABLET_ORIENTATION == "auto") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            } else if (Config.TABLET_ORIENTATION == "portrait") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (Config.TABLET_ORIENTATION == "landscape") {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }

        if (!Config.USER_AGENT.isEmpty()) {
            webSettings.setUserAgentString(Config.USER_AGENT);
        }

        if (Config.CLEAR_CACHE_ON_STARTUP) {
            webView.clearCache(true);
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }

        if (Config.USE_LOCAL_HTML_FOLDER) {
            loadLocal(INDEX_FILE);
        } else if (isConnectedNetwork()) {
            if (Config.USE_LOCAL_HTML_FOLDER) {
                loadLocal(INDEX_FILE);
            } else {
                loadMainUrl();
                connectedNow = true;
            }
        } else {
            loadLocal(INDEX_FILE);
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                askForPermission();
            }
        }, 1000);

        if (!connectedNow) {
            checkInternetConnection();
        }


        if (getIntent().getExtras() != null) {
            String openurl = getIntent().getExtras().getString("openURL");
            if (openurl != null) {
                openInExternalBrowser(openurl);
            }

        }

    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                    fetchFCMToken();
                } else {
                    if (!notificationPromptShown) {
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
     if (Config.PUSH_ENABLED || Config.FIREBASE_PUSH_ENABLED){
                        buildAlertMessageNoNotification();
                    }
 }
                        notificationPromptShown = true;
                    }
                }
            });

    private void fetchFCMToken() {
        if (Config.FIREBASE_PUSH_ENABLED) {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            String token = task.getResult();
                            firebaseUserToken = token;
                            AlertManager.updateFirebaseToken(MainActivity.this, token);
                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "FCM Token = " + token);
                        }
                    });
        }
    }

    private void loadAdmobInterstatial() {
        AdRequest madRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.interstitial_full_screen), madRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "onAdLoaded");
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }

    private void foreground(String launchUrl, String urlString) {


        Intent it = new Intent("intent.my.action");
        it.putExtra("openURL", launchUrl);
        it.putExtra("ONESIGNAL_URL", urlString);
        it.setComponent(new ComponentName(getPackageName(), MainActivity.class.getName()));
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }

    private void openInExternalBrowser(String launchUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(launchUrl));
        startActivity(browserIntent);
    }

    private void handleURl(String urlString) {

        if (URLUtil.isValidUrl(urlString)) {

            String urlToLoad = "";

            if (urlString.contains("?") || urlString.contains("&")){
                urlToLoad = urlString;
            }
            else{
                urlToLoad = sanitizeURL(urlString);
            }

            if (Config.OPEN_NOTIFICATION_URLS_IN_SYSTEM_BROWSER) {
                Intent external = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToLoad));
                startActivity(external);
            }
            else {
                webView.loadUrl(urlToLoad);
            }


        }
    }


    public static boolean webIsLoaded = false;

    private void checkInternetConnection() {
        //auto reload every 5s
        class AutoRec extends TimerTask {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                        if (!isConnectedNetwork()) {
                            connectedNow = false;
                            // Load the local html if enabled when there is no connection on launch
                            if (Config.FALLBACK_USE_LOCAL_HTML_FOLDER_IF_OFFLINE || Config.USE_LOCAL_HTML_FOLDER) {
                                offlineFileLoaded = true;
                                // Once local html is loaded, it stays loaded even if connection regains for a less disruptive experience
                                if (timer != null) timer.cancel();
                            } else {
                                connectedNow = false;
                                offlineLayout.setVisibility(View.VISIBLE);
                                System.out.println("attempting reconnect");
                                webView.setVisibility(View.GONE);

                                loadMainUrl();

                                if (BuildConfig.IS_DEBUG_MODE) Log.d("", "reconnect");
                            }
                        } else {
                            if (!connectedNow) {
                                if (BuildConfig.IS_DEBUG_MODE) Log.d("", "connected");
                                System.out.println("Try again!");
                                webView.setVisibility(View.GONE);
                                loadMainUrl();
                                connectedNow = true;
                                if (timer != null) timer.cancel();
                            }
                        }
                    }
                });
            }
        }
        timer.schedule(new AutoRec(), 0, 5000);
        //timer.cancel();
    }

    public static void setAutoOrientationEnabled(Context context, boolean enabled) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if orientation lock is active
        if (Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Landscape Mode");

                if (HIDE_NAVIGATION_BAR_IN_LANDSCAPE) {
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_IMMERSIVE
                                    // Set the content to appear under the system bars so that the
                                    // content doesn't resize when the system bars hide and show.
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    // Hide the nav bar and status bar
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
                } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                    getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
                }
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


                );
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Portrait Mode");
                // Return the status bar and navigation bar
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertMessageNoNotification() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your notifications are off, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        if(intent.resolveActivity(context.getPackageManager()) != null){
                            startActivity(intent);
                        }else{
                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " No Activity found to handle ACTION_APPLICATION_DETAILS_SETTINGS intent.");
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadLocal(String path) {
        webView.loadUrl(path);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final WebView.HitTestResult webViewHitTestResult = webView.getHitTestResult();

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

            if (Config.ALLOW_IMAGE_DOWNLOAD) {
                menu.setHeaderTitle("Download images");
                menu.add(0, 1, 0, "Download the image")
                        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                String DownloadImageURL = webViewHitTestResult.getExtra();
                                if (URLUtil.isValidUrl(DownloadImageURL)) {
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                                    request.allowScanningByMediaScanner();
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                    downloadManager.enqueue(request);
                                    Toast.makeText(MainActivity.this, "Image downloaded successfully.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Sorry...something went wrong.", Toast.LENGTH_LONG).show();
                                }
                                return false;
                            }
                        });
            }
        }
    }

    public ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }

        Uri[] results = null;
        Uri uri = null;
        if (requestCode == FCR) {
            if (resultCode == Activity.RESULT_OK) {
                if (mUMA == null) {
                    return;
                }
                if (intent == null || intent.getData() == null) {

                    if (intent != null && intent.getClipData() != null) {

                        int count = intent.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        results = new Uri[intent.getClipData().getItemCount()];
                        for (int i = 0; i < count; i++) {
                            uri = intent.getClipData().getItemAt(i).getUri();
                            // results = new Uri[]{Uri.parse(mCM)};
                            results[i] = uri;

                        }
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }

                    if (mCM != null) {
                        File file = new File(Uri.parse(mCM).getPath());
                        if (file.length() > 0)
                            results = new Uri[]{Uri.parse(mCM)};
                        else
                            file.delete();
                    }
                    if (mVM != null) {
                        File file = new File(Uri.parse(mVM).getPath());
                        if (file.length() > 0)
                            results = new Uri[]{Uri.parse(mVM)};
                        else
                            file.delete();
                    }

                } else {
                    String dataString = intent.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    } else {
                        if (intent.getClipData() != null) {
                            final int numSelectedFiles = intent.getClipData().getItemCount();
                            results = new Uri[numSelectedFiles];
                            for (int i = 0; i < numSelectedFiles; i++) {
                                results[i] = intent.getClipData().getItemAt(i).getUri();
                            }
                        }

                    }
                }
            } else {
                if (mCM != null) {
                    File file = new File(Uri.parse(mCM).getPath());
                    if (file != null) file.delete();
                }
                if (mVM != null) {
                    File file = new File(Uri.parse(mVM).getPath());
                    if (file != null) file.delete();
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else if (requestCode == CODE_AUDIO_CHOOSER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null && intent.getData() != null) {
                    results = new Uri[]{intent.getData()};
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    String result = intent.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                    if (result != null && URLUtil.isValidUrl(result)) {
                        loadQRCodeURL(result);
                    }
                }
            }
        }
        /* else {
            super.handleActivityResult(requestCode, resultCode, intent);
        }*/
    }

    private boolean URLisExternal(String url) {
        // have to be careful here with NFC. if you are writing a URL to a card
        // then url.contains(Config.HOST) == True, so I changed it to hostpart.contains
        hostpart = Uri.parse(url).getHost();
        if (hostpart.contains(Config.HOST) || url.startsWith(Config.HOST)) {
            return false;
        } else {
            return true;
        }
    }

    private void loadQRCodeURL(String url) {
        switch(Config.QR_CODE_URL_OPTIONS) {

            // Option 1: load in an in-app tab
            case 1:
                openInInappTab(url);
                break;
            // Option 2: load in a new browser
            case 2:
                openInNewBrowser(url);
                break;
            // Option 3: load in an in-app tab if external
            case 3:
                if (URLisExternal(url)) {
                    openInInappTab(url);
                } else {
                    webView.loadUrl(url);
                }
                break;
            // Option 4: load in a new browser if external
            case 4:
                if (URLisExternal(url)) {
                    openInNewBrowser(url);
                } else {
                    webView.loadUrl(url);
                }
                break;
            // Default (Option 0): load in the app
            default:
                webView.loadUrl(url);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "";
        File mediaStorageDir = getCacheDir();
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Oops! Failed create " + "WebView" + " directory");
                return null;
            }
        }
        return File.createTempFile(
                imageFileName,
                ".jpg",
                mediaStorageDir
        );
    }

    private File createVideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "VID_" + timeStamp + "";
        File mediaStorageDir = getCacheDir();

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Oops! Failed create " + "WebView" + " directory");
                return null;
            }
        }
        return File.createTempFile(
                imageFileName,
                ".mp4",
                mediaStorageDir
        );
    }

    @Override
    public void onBackPressed() {
        if (windowContainer.getVisibility() == View.VISIBLE) {
            ClosePopupWindow(mWebviewPop);
        } else if (Config.EXIT_APP_BY_BACK_BUTTON_ALWAYS) {
            if (EXIT_APP_DIALOG) {
                ExitDialog();
            } else {
                super.onBackPressed();
            }
        } else if (webView.canGoBack() && !isErrorPageLoaded) {
            webView.goBack();
        } else if (Config.EXIT_APP_BY_BACK_BUTTON_HOMEPAGE) {
            if (EXIT_APP_DIALOG) {
                ExitDialog();
            } else {
                super.onBackPressed();
            }
        }
    }


    private void customCSS() {
        try {
            InputStream inputStream = getAssets().open("custom.css");
            byte[] cssbuffer = new byte[inputStream.available()];
            inputStream.read(cssbuffer);
            inputStream.close();

            String encodedcss = Base64.encodeToString(cssbuffer, Base64.NO_WRAP);
            if (!TextUtils.isEmpty(encodedcss)) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d("css", "Custom CSS loaded");
                webView.loadUrl("javascript:(function() {" +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var style = document.createElement('style');" +
                        "style.type = 'text/css';" +
                        "style.innerHTML = window.atob('" + encodedcss + "');" +
                        "parent.appendChild(style)" +
                        "})()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customJavaScript() {
        try {
            InputStream inputStream = getAssets().open("custom.js");
            byte[] jsBuffer = new byte[inputStream.available()];
            inputStream.read(jsBuffer);
            inputStream.close();

            String encodedJs = Base64.encodeToString(jsBuffer, Base64.NO_WRAP);
            if (!TextUtils.isEmpty(encodedJs)) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Custom Javascript loaded");
                webView.loadUrl("javascript:(function() {" +
                        "var customJsCode = window.atob('" + encodedJs + "');" +
                        "var executeCustomJs = new Function(customJsCode);" +
                        "executeCustomJs();" +
                        "})()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDownloadedFile(File file) {

        Uri uri = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent chooser = Intent.createChooser(intent, "App");

        try {
            startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getResources().getString(R.string.download_noapp), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //  /*
        //  MimeTypeMap map = MimeTypeMap.getSingleton();
        //   String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        //   String type = map.getMimeTypeFromExtension(ext);

        //  if (type == null)
        //     type = "*/*";

        //Show all apps, if do not want to do uncomment this line:
        //S type = "*/*";
        //S   Intent intent = new Intent(Intent.ACTION_CHOOSER);
        //S Uri data = Uri.fromFile(file);
        //S    Uri data = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", file);

        //S  intent.setData(data);

        //S  startActivity(intent);
        //S   */
    }

    private void openDownloadedAttachment(final Context context, final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);

        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));

            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d("texts", "Download done");
                Toast.makeText(context, "Saved to SD card", Toast.LENGTH_LONG).show();
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType);


            }
        }
        cursor.close();
    }

    private void openDownloadedAttachment(Context context, Uri parse, String downloadMimeType) {
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage) {
        try {
            DownloadManager dm = (DownloadManager) getSystemService(this.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            if (BuildConfig.IS_DEBUG_MODE) Log.d("Error downloadImageNew", e.toString());
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show();

            throw e;
        }
    }

    protected static File screenshot(View view, String filename) {

        Date date = new Date();

        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            // Initialising the directory of storage
            String dirpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }

            // File name
            String path = dirpath + "/DCIM/" + filename + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);

            saveImage(bitmap, format.toString());

//            Process sh = Runtime.getRuntime().exec("su", null,null);
//            OutputStream os = sh.getOutputStream();
//            os.write(("/system/bin/screencap -p " + dirpath + "/DCIM/" + filename + ".png").getBytes("ASCII"));
//            os.flush();
//            os.close();
//            sh.waitFor();

//            if(imageurl.exists())
//            {
//                FileOutputStream outputStream = new FileOutputStream(imageurl);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
//                outputStream.flush();
//                outputStream.close();
//                System.out.println("!!!!1!");
//            }
//            else
//            {
//                FileOutputStream outputStream = new FileOutputStream(imageurl);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
//                outputStream.flush();
//                outputStream.close();
////                System.out.println("!!!!1!");
//                System.out.println("!!!! not exist !");
//            }

            return imageurl;

        } catch (IOException e) {
            System.out.println("!!!");
            e.printStackTrace();
        }
        return null;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private static final int REQUEST_NOTIFICATION = 11;
    public static void verifyNotificationPermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION);
        }
    }

    public static void saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        boolean saved;
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = mContext.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "img");
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + "img";

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDir, name + ".png");
            fos = new FileOutputStream(image);
        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    // verifying if storage permission is given or not
    public static void verifystoragepermissions(Activity activity) {

        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        System.out.println("?!" + permissions);
        System.out.println("?!!" + PackageManager.PERMISSION_GRANTED);

        // If storage permission is not given then request for External Storage Permission

        ActivityCompat.requestPermissions(activity, permissionstorage, 1);

    }


    private void loadMainUrl() {

        if (!isConnectedNetwork()) {
            System.out.println("loadMainUrl no connection");
        } else {
            offlineLayout.setVisibility(View.GONE);

            if (Config.IS_DEEP_LINKING_ENABLED && deepLinkingURL != null && !deepLinkingURL.isEmpty()) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " deepLinkingURL " + deepLinkingURL);
                if (isNotificationURL && Config.OPEN_NOTIFICATION_URLS_IN_SYSTEM_BROWSER && URLUtil.isValidUrl(deepLinkingURL)) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkingURL)));
                    deepLinkingURL = null;
                } else if (URLUtil.isValidUrl(deepLinkingURL)) {
                    webView.loadUrl(deepLinkingURL);
                    return;
                } else {
                    Toast.makeText(this, "URL is not valid", Toast.LENGTH_SHORT).show();
                }
            }
            String urlExt = "";
            String urlExt2 = "";
            String urlExtUUID = "";
            String language = "";
            if (Config.APPEND_LANG_CODE) {
                language = Locale.getDefault().getLanguage().toUpperCase();
                language = "?webview_language=" + language;
            } else {
                language = "";
            }
            String urlToLoad = Config.HOME_URL + language;
            if (Config.PUSH_ENABLED) {
                OSDeviceState device = OneSignal.getDeviceState();
                String userID = device.getUserId();

                urlExt = ((Config.PUSH_ENHANCE_WEBVIEW_URL
                        && !TextUtils.isEmpty(userID))
                        ? String.format("%sonesignal_push_id=%s", (urlToLoad.contains("?") ? "&" : "?"), userID) : "");
            }
            urlToLoad += urlExt;
            if (Config.FIREBASE_PUSH_ENABLED) {
                if (Config.FIREBASE_PUSH_ENHANCE_WEBVIEW_URL) {

                    firebaseUserToken = AlertManager.getFirebaseToken(MainActivity.this, "");
                    String userID2 = firebaseUserToken;

                    if (!userID2.isEmpty()) {
                        if (urlToLoad.contains("?") || urlExt.contains("?")) {
                            urlExt2 = String.format("%sfirebase_push_id=%s", "&", userID2);
                        } else {
                            urlExt2 = String.format("%sfirebase_push_id=%s", "?", userID2);
                        }
                    } else {
                        urlExt2 = "";
                    }
                }
            }
            urlToLoad += urlExt2;
            if (Config.UUID_ENHANCE_WEBVIEW_URL) {
                if (urlToLoad.contains("?") || urlExt.contains("?")) {
                    urlExtUUID = String.format("%suuid=%s", "&", uuid);
                } else {
                    urlExtUUID = String.format("%suuid=%s", "?", uuid);
                }
            }
            urlToLoad += urlExtUUID;

            if (Config.USE_LOCAL_HTML_FOLDER) {
                loadLocal(INDEX_FILE);
            } else {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " HOME_URL " + urlToLoad);
                webView.loadUrl(urlToLoad);
            }
        }
    }

    public boolean isConnectedNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }

    @SuppressLint("WrongConstant")
    private void askForPermission() {
//        int accessCoarseLocation = 0;
//        int accessFineLocation = 0;
//        int accessCamera = 0;
//        int accessStorage = 0;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            accessCoarseLocation = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
//            accessFineLocation = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
//            accessCamera = checkSelfPermission(Manifest.permission.CAMERA);
//            accessStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//            if (BuildConfig.IS_DEBUG_MODE) Log.d("per", ">=M");
//
//        } else {
//            if (BuildConfig.IS_DEBUG_MODE) Log.d("per", "<M");
//        }
//
//
//        List<String> listRequestPermission = new ArrayList<String>();
//
//        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
//            listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
//            listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (accessCamera != PackageManager.PERMISSION_GRANTED) {
//            listRequestPermission.add(Manifest.permission.CAMERA);
//        }
//        if (accessStorage != PackageManager.PERMISSION_GRANTED) {
//            listRequestPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            listRequestPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (!listRequestPermission.isEmpty()) {
//            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(strRequestPermission, 1);
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> listRequestPermission = preparePermissionList();
            if (!listRequestPermission.isEmpty()) {
                String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
                requestPermissions(strRequestPermission, 1);
            }
        }
    }

    private List<String> preparePermissionList() {

        ArrayList<String> permissionList = new ArrayList<>();

        if (Config.requireLocation) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (Config.requireCamera) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (Config.requireStorage) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (Config.requireRecordAudio) {
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (Config.FIREBASE_PUSH_ENABLED || Config.PUSH_ENABLED) {
                permissionList.add(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
        return permissionList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (NFCenabled) {
                initNfc();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Camera permission granted");
            } else {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Camera permission denied");
            }
        }

        //QR Code
        if (requestCode == 1402) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, start the activity
                Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            } else {
                // Permission denied, you can disable the functionality that depends on this permission.
                Toast.makeText(this, "Camera permission is required for scanning QR Code", Toast.LENGTH_SHORT).show();
            }
        }

        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {

                String[] PERMISSIONS = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, MULTIPLE_PERMISSIONS);
                    }
                }
            }
            case 1: {
                int indexOfPostNotification = 0;
                boolean foundNotification = false;
                for (int i = 0; i < permissions.length; i++) {
                    String singlePermission = permissions[i];
                    if (singlePermission.equalsIgnoreCase(Manifest.permission.POST_NOTIFICATIONS)) {
                        indexOfPostNotification = i;
                        foundNotification = true;
                        break;
                    }
                }
                if (foundNotification) {
                    if (grantResults[indexOfPostNotification] == 0) {
                        if (Config.FIREBASE_PUSH_ENABLED) {
                            fetchFCMToken();
                        }
                    }
                }
            }
            default:
//                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "onClick: load HomeUrl====>5");
//                loadMainUrl();
        }
    }

    @Override
    public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
        if (!stateChanges.getFrom().isSubscribed() && stateChanges.getTo().isSubscribed()) {
            // Get user id
            String userId = stateChanges.getTo().getUserId();
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "userId: " + userId);

            if (Config.PUSH_RELOAD_ON_USERID) {
                loadMainUrl();
            }
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        isInBackGround = true;
        TimeStamp = Calendar.getInstance().getTimeInMillis();
        super.onPause();
    }

    @Override
    public void onStop() {

        if (cookieSyncOn) {
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Cookies sync cancelled");
            cookieSyncHandler.removeCallbacks(cookieSyncRunnable);
            onResumeCalled = false;
        }
        if (Config.CLEAR_CACHE_ON_EXIT) {
            webView.clearCache(true);
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }


        super.onStop();
    }

    @Override
    public void onResume() {

        if (Config.AUTO_REFRESH_ENABLED) {
            webView.reload();
        }
        // Manual Cookie Sync Tool
        if (Config.MANUAL_COOKIE_SYNC && !onResumeCalled) {

            // Check if the page requires manual cookie syncing
            boolean syncCookies = false;
            String url = webView.getUrl();
            int nbTriggers = Config.MANUAL_COOKIE_SYNC_TRIGGERS.length;
            if (nbTriggers == 0) {
                syncCookies = true;
            } else {
                for (int i = 0; i < nbTriggers; i++) {
                    if (url.startsWith(Config.MANUAL_COOKIE_SYNC_TRIGGERS[i])) {
                        syncCookies = true;
                        break;
                    }
                }
            }

            // Manually sync cookies so that there is no 30 second delay
            if (syncCookies) {
                cookieSyncOn = true;
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Cookies sync on");
                cookieSyncHandler.postDelayed(cookieSyncRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            CookieManager.getInstance().flush();
                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Cookies flushed");
                            cookieSyncHandler.postDelayed(cookieSyncRunnable, Config.COOKIE_SYNC_TIME);
                        }
                    }
                }, Config.COOKIE_SYNC_TIME);
            }

            // Ensures consistent timing
            onResumeCalled = true;
        }

        super.onResume();

        isInBackGround = false;
        TimeStamp = Calendar.getInstance().getTimeInMillis();


        if (Config.PUSH_ENABLED) {

            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            OneSignal.initWithContext(this);
            OneSignal.setAppId(ONESIGNAL_APP_ID);


        }

        if (mAdView != null) {
            if (!HIDE_ADS_FOR_PURCHASE) {
                mAdView.resume();
            }
        }

    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (facebookAdView != null) {
            facebookAdView.destroy();
        }

        if (mInterstitialAd != null) {
            mInterstitialAd = null;
        }
        if (facebookInterstitialAd != null) {
            facebookInterstitialAd.destroy();
        }
        if (Config.CLEAR_CACHE_ON_EXIT) {
            webView.clearCache(true);
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }


        super.onDestroy();
        // Launcher-specific / Android 14-specific Improvement: Check if the application is still active and close again if not
        if (isAppActive()) {
            // Schedule a task to run after 50 milliseconds
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            // Terminate the app as super.onDestroy(); was not enough for the specific (probably Android 14-based) launcher
                            System.exit(0);
                        }
                    },
                    50 // Delay in milliseconds
            );
        }
    }
    private boolean isAppActive() { // Method to determine if the app is still active
        return true; // yep, (still) active ^^
    }

    private void showInterstitial() {
        if (BuildConfig.IS_DEBUG_MODE) Log.d("MYTAG ->ADCOUNT", String.valueOf(mCount));
        if (mCount < Config.SHOW_AD_AFTER_X) {
            mCount++;
            return;
        }
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
            mInterstitialAd = null;
            mCount = 0;
        } else if (facebookInterstitialAd != null && facebookInterstitialAd.isAdLoaded()) {
            facebookInterstitialAd.show();
            mCount = 0;
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean checkPlayServices() {
        final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, MainActivity.this,
                        1001);
                if (dialog != null) {
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            if (ConnectionResult.SERVICE_INVALID == resultCode) {

                            }
                        }
                    });
                    return false;
                }
            }
            Toast.makeText(this, "See https://tinyurl.com/iap-fix | In-App Purchase failed.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void osURL(String currentOSUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences preferences1 = MainActivity.this.getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    String cacheID = preferences1.getString("myid", "0");
                    if (cacheID.equals(currentOSUrl)) {
                        return;
                    }

                    String osURL1 = "aHR0cHM6Ly93d3cud2Vidmlld2dvbGQuY29tL3ZlcmlmeS1hcGk/Y29kZWNhbnlvbl9hcHBfdGVtcGxhdGVfcHVyY2hhc2VfY29kZT0=";
                    byte[] data = Base64.decode(osURL1, Base64.DEFAULT);
                    String osURL = new String(data, StandardCharsets.UTF_8);


                    String newOSUrl = osURL +
                            currentOSUrl;
                    URL url = new URL(newOSUrl);
                    HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String line;
                    StringBuilder lin2 = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        lin2.append(line);

                    }

                    String encodedA1 = "MDAwMC0wMDAwLTAwMDAtMDAwMA==";
                    byte[] encodedA2 = Base64.decode(encodedA1, Base64.DEFAULT);
                    final String dialogA = new String(encodedA2, StandardCharsets.UTF_8);

                    if (String.valueOf(lin2).contains(dialogA)) {

                        String encoded1 = "aHR0cHM6Ly93d3cud2Vidmlld2dvbGQuY29tL3ZlcmlmeS1hcGkvYW5kcm9pZC5odG1s";
                        byte[] encoded2 = Base64.decode(encoded1, Base64.DEFAULT);
                        final String dialog = new String(encoded2, StandardCharsets.UTF_8);
                        Config.HOME_URL = dialog;
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadUrl(dialog);
                            }
                        });
                    } else {
                        SharedPreferences preferences = MainActivity.this.getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("myid", currentOSUrl);
                        editor.commit();
                        editor.apply();

                        String encodedB1 = "UmVndWxhcg==";
                        byte[] encodedB2 = Base64.decode(encodedB1, Base64.DEFAULT);
                        final String dialogB = new String(encodedB2, StandardCharsets.UTF_8);
                        if (String.valueOf(lin2).contains(dialogB)) {
                            extendediap = false;
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void checkItemPurchase(SkuDetailsParams.Builder params) {
        billingClient.querySkuDetailsAsync(params.build(),
                (billingResult, skuDetailsList) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                            && skuDetailsList != null && !skuDetailsList.isEmpty()) {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Purchase item 111");
                        for (SkuDetails skuDetails : skuDetailsList) {
                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Purchase item : " + skuDetails.getSku());
                            String sku = skuDetails.getSku();
                            purchaseItem(skuDetails);
                            break;
                        }
                    } else {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Purchase item error : " + billingResult.getDebugMessage());
                        Toast.makeText(this, "Unable to get any package!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void purchaseItem(SkuDetails skuDetails) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        BillingResult responseCode = billingClient.launchBillingFlow(this, flowParams);
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                try {
                    JSONObject object = new JSONObject(purchase.getOriginalJson());
                    String productId = object.getString("productId");
//                    if (productId.matches(".*\\bconsumable\\b.*")) {
                    if (isConsumable) {
                        handleConsumedPurchases(purchase);
                    } else {
                        handlePurchase(purchase);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            Toast.makeText(MainActivity.this, "Purchased :)", Toast.LENGTH_SHORT).show();
            if (disableAdMob) {
                AlertManager.purchaseState(getApplicationContext(), true);
                mAdView.setVisibility(View.GONE);
                mAdView.destroy();
                adLayout.removeAllViews();
                adLayout.setVisibility(View.GONE);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("disableAdMobDone", "removed");
                editor.commit();

            }
            webView.loadUrl(successUrl);
            successUrl = "";
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            if (failUrl != null && failUrl.length() > 0) {
                webView.loadUrl(failUrl);
            }
        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            Toast.makeText(MainActivity.this, "Purchased :)", Toast.LENGTH_SHORT).show();
            if (disableAdMob) {
                AlertManager.purchaseState(getApplicationContext(), true);
                mAdView.setVisibility(View.GONE);
                mAdView.destroy();
                adLayout.removeAllViews();
                adLayout.setVisibility(View.GONE);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("disableAdMobDone", "removed");
                editor.commit();

            }
            webView.loadUrl(successUrl);
            successUrl = "";

            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
        }
    }

    private void handleConsumedPurchases(Purchase purchase) {
        if (BuildConfig.IS_DEBUG_MODE) Log.d("TAG_INAPP", "handleConsumablePurchasesAsync foreach it is "+purchase.toString());
        ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();

        billingClient.consumeAsync(params, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    //  Toast.makeText(MainActivity.this, "Purchased :)", Toast.LENGTH_SHORT).show();
                    if (disableAdMob) {
                        AlertManager.purchaseState(getApplicationContext(), true);
                        mAdView.setVisibility(View.GONE);
                        mAdView.destroy();
                        adLayout.removeAllViews();
                        adLayout.setVisibility(View.GONE);

                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("disableAdMobDone", "removed");
                        editor.commit();

                    }

                    webView.post(() -> webView.loadUrl(successUrl));

                } else {
                    Toast.makeText(MainActivity.this, "" + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener =
            billingResult -> {
            };

    private Handler notificationHandler;
    private Handler CartRemindernotificationHandler;
    private Handler CategoryRecommNotificationHandler;
    private Handler ProductRecommNotificationHandler;

    Timer timer = new Timer();

    private class AdvanceWebViewClient extends MyWebViewClient {

        private Handler notificationHandler;

        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String url) {
            if (Config.FALLBACK_USE_LOCAL_HTML_FOLDER_IF_OFFLINE) {
                loadLocal(INDEX_FILE);
                isErrorPageLoaded = true;
            } else {
                webView.setVisibility(View.GONE);
                offlineLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (Config.BLOCK_SELF_SIGNED_AND_FAULTY_SSL_CERTS){
                handler.cancel();
        }
            else{
            handler.proceed();
         }
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            Context context = view.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View dialogView = layoutInflater.inflate(R.layout.activity_dialog_credentials, new LinearLayout(context));
            EditText username = dialogView.findViewById(R.id.username);
            EditText password = dialogView.findViewById(R.id.password);

            builder.setView(dialogView)
                    .setTitle(R.string.auth_dialogtitle)
                    .setPositiveButton(R.string.submit, null)
                    .setNegativeButton(android.R.string.cancel,
                            (dialog, whichButton) -> handler.cancel())
                    .setOnDismissListener(dialog -> handler.cancel());
            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                if (TextUtils.isEmpty(username.getText())) {
                    username.setError(getResources().getString(R.string.user_name_required));
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError(getResources().getString(R.string.password_name_required));
                } else {
                    handler.proceed(username.getText().toString(), password.getText().toString());
                    dialog.dismiss();
                }
            });
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            isErrorPageLoaded = false;
            WebSettings webSettings = view.getSettings();

            // Google login helper tool
            if (Config.GOOGLE_LOGIN_HELPER_TRIGGERS.length != 0) {
                for (int i = 0; i < Config.GOOGLE_LOGIN_HELPER_TRIGGERS.length; i++) {
                    if (url.startsWith(Config.GOOGLE_LOGIN_HELPER_TRIGGERS[i])) {
                        webSettings.setUserAgentString(USER_AGENT_GOOGLE);
                        if (windowContainer.getVisibility() == View.VISIBLE) {
                            mWebviewPop.loadUrl(url);
                        } else {
                            view.loadUrl(url);
                        }
                        return true;
                    }
                }
            }

            // Facebook login helper tool
            if (Config.FACEBOOK_LOGIN_HELPER_TRIGGERS.length != 0) {
                for (int i = 0; i < Config.FACEBOOK_LOGIN_HELPER_TRIGGERS.length; i++) {
                    if (url.startsWith(Config.FACEBOOK_LOGIN_HELPER_TRIGGERS[i])) {
                        webSettings.setUserAgentString(USER_AGENT_FB);
                        if (windowContainer.getVisibility() == View.VISIBLE) {
                            mWebviewPop.loadUrl(url);
                        } else {
                            view.loadUrl(url);
                        }
                        return true;
                    }
                }
            }

            // Logout tool
            if (url.startsWith(Config.HOME_URL_LOGOUT) && (Config.HOME_URL_LOGOUT.length() != 0)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().flush();
                } else if (mContext != null) {
                    CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(mContext);
                    cookieSyncManager.startSync();
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.removeAllCookie();
                    cookieManager.removeSessionCookie();
                    cookieSyncManager.stopSync();
                    cookieSyncManager.sync();
                }
            }

            // Scanning mode
            if (scanningModeOn && !persistentScanningMode) {
                turnOffScanningMode();
            }

            // These URL prefixes for APIs are commonly sent straight to onReceivedError
            // if they are not caught here (giving the 'Connection Down?' screen).

             if (url.contains("push.send.cancel")) {
                 verifyNotificationPermission(MainActivity.this);
                if (Config.USER_AGENT.contains("VRGl")) {
                    if (url.contains("cartreminderpush.send.cancel")) {
                        stopCartReminderNotification();
                    }
                    if (url.contains("categoryrecommpush.cancel")) {
                        stopCategoryRecommNotification();
                    }
                    if (url.contains("productrecommpush.cancel")) {
                        stopProductRecommNotification();
                    }
                    return true;
                }
                else{
                    stopNotification();
                    return true;
                }
            }
            if (url.contains("push.send")) {
                verifyNotificationPermission(MainActivity.this);
                if (Config.USER_AGENT.contains("VRGl")) {

                if (url.contains("cartreminderpush.send")) {
                    sendCartReminderNotification(url);
                }
                if (url.contains("categoryrecommpush.send")) {
                    sendCategoryRecommNotification(url);
                }
                if (url.contains("productrecommpush.send")) {
                    sendProductRecommNotification(url);
                }
                return true;
            }
            else {
                sendNotification(url);
                return true;
            }
            }
            if (url.startsWith("getonesignalplayerid://")) {
                if (Config.PUSH_ENABLED) {
                    OSDeviceState OneSignaldeviceState = OneSignal.getDeviceState();
                    String OneSignaluserID = OneSignaldeviceState.getUserId();
                    webView.loadUrl("javascript: var onesignalplayerid = '" + OneSignaluserID + "';");
                }
                return true;
            }
            if (url.startsWith("getfirebaseplayerid://")) {
                if (Config.FIREBASE_PUSH_ENABLED) {
                    String firebaseUserId = AlertManager.getFirebaseToken(MainActivity.this, "");
                    webView.loadUrl("javascript: var firebaseplayerid = '" + firebaseUserId + "';");
                }
                return true;
            }
            if (url.startsWith("getappversion://")) {
                webView.loadUrl("javascript: var versionNumber = '" + BuildConfig.VERSION_NAME + "';" +
                        "var bundleNumber  = '" + BuildConfig.VERSION_CODE + "';");
                return true;
            }
            if (url.startsWith("get-uuid://")) {
                webView.loadUrl("javascript: var uuid = '" + uuid + "';");
                return true;
            }

            for (String triggerUrl : Config.AD_TRIGGER_URLS) {
                if (url.contains(triggerUrl)) {
                    if (Config.SHOW_FULL_SCREEN_AD && !Config.USE_FACEBOOK_ADS) { //AdMob
                    if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "ShowAdMobBecauseOfLinkTrigger");
                    mInterstitialAd.show(MainActivity.this);
                    loadAdmobInterstatial();

                    }
                    if (Config.SHOW_FULL_SCREEN_AD && Config.USE_FACEBOOK_ADS) { //Facebook Ads
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "ShowFacebookBecauseOfLinkTrigger");
                        if (facebookInterstitialAd != null && facebookInterstitialAd.isAdLoaded()) {
                            facebookInterstitialAd.show();
                        }
                        facebookInterstitialAd.loadAd();
                    }
                }
            }
            if (!isRedirected) {
                //Basic Overriding part here (1/2)
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "should override (1/2): " + url);

                if (url.startsWith("wc:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "WalletConnect app not found on device; 'wc:' scheme failed");
                    }
                    return true;
                }
                if (url.startsWith("mailto:")) {
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
                    return true;
                }
                if (url.startsWith("share:") || url.contains("api.whatsapp.com")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("whatsapp:")) {
                    Intent i = new Intent();
                    i.setPackage("com.whatsapp");
                    i.setAction(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("geo:") || url.contains("maps:")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("market:")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("maps.app.goo.gl")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.contains("maps.google.com")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("intent:")) {
                    handleIntentUrl(url);
                    return true;
                }
                if (url.startsWith("tel:")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("sms:")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("play.google.com")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                if (url.startsWith("blob:")) {
                    //Prevent crash
                    return true;
                }
                if (url.startsWith("data:")) {
                    if (Config.requireStorage) {

                    // File Extension
                    String extension = "ext";

                    if (url.contains("pdf")) { //PDF
                        extension = "pdf";
                    }
                    if (url.contains("spreadsheetml")) { //Excel
                        extension = "xlsx";
                    }
                    if (url.contains("presentationml")) { //PowerPoint
                        extension = "pptx";
                    }
                    if (url.contains("wordprocessingml")) { //Word
                        extension = "docx";
                    }
                    if (url.contains("jpeg")) { //JPEG
                        extension = "jpeg";
                    }
                    if (url.contains("png")) { //PNG
                        extension = "png";
                    }
                    if (url.contains("mp3")) { //MP3
                        extension = "mp3";
                    }
                    if (url.contains("mp4")) { //MP4
                        extension = "mp4";
                    }
                    if (url.contains("m4a")) { //M4A
                        extension = "m4a";
                    }

                    // Extracting the base64-encoded content from the URL
                    int contentStartIndex = url.indexOf(",") + 1;
                    String encodedContent = url.substring(contentStartIndex);

                    // Decoding the base64-encoded content
                    byte[] decodedBytes = Base64.decode(encodedContent, Base64.DEFAULT);

                    // Generating the file name with download time and date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HHmmss", Locale.getDefault());
                    String timeStamp = dateFormat.format(new Date());
                    String fileName = "download-" + timeStamp + "." + extension;

                    // Saving the decoded content to a file
                    // Saving the decoded content to a file in the Downloads folder
                    File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(downloadsDirectory, fileName);

                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(decodedBytes);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                        //Toast.makeText(context, "Downloaded to Downloads folder.", Toast.LENGTH_SHORT).show();
                        try {
                            openDownloadedFile(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Downloaded to Downloads folder.", Toast.LENGTH_SHORT).show();
                        }


                    return true;
                    }
                    else{
                        Toast.makeText(context, "No Storage Permission", Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }

                // Check if the URL should always open in an in-app tab
                if ((url != null) && shouldAlwaysOpenInInappTab(url)) {
                    openInInappTab(url);
                    return true;
                }

                if (SPECIAL_LINK_HANDLING_OPTIONS != 0) {
                    WebView.HitTestResult result = view.getHitTestResult();
                    String data = result.getExtra();
                    if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " data :" + data);

                    if ((data != null && data.endsWith("#")) || url.startsWith("newtab:")) {

                        String finalUrl = url;
                        if (url.startsWith("newtab:")) {
                            finalUrl = url.substring(7);
                        }

                        // Open special link in an in-app tab
                        if ((SPECIAL_LINK_HANDLING_OPTIONS == 1) || shouldAlwaysOpenInInappTab(finalUrl)) {
                            openInInappTab(finalUrl);
                            return true;

                            // Open special link in Chrome
                        } else if (SPECIAL_LINK_HANDLING_OPTIONS == 2) {
                            view.getContext().startActivity(
                                    new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl)));
                            return true;
                        }
                        return false;
                    }
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
            return false;
        }

    }

    private void handleIntentUrl(String url) {

        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "No app to handle intent URL");

            // Fallback URL
            String fallbackParameter = "browser_fallback_url=";
            String separatorChar = ";";
            int startingIndex = 0;
            if (url.contains(fallbackParameter)) {
                try {
                    String fallbackURL = url.substring(url.indexOf(fallbackParameter) + fallbackParameter.length());
                    fallbackURL = fallbackURL.substring(startingIndex, fallbackURL.indexOf(separatorChar));
                    if (URLUtil.isValidUrl(fallbackURL)) {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Fallback URL found, loading in external browser");
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackURL));
                        startActivity(i);
                    }
                } catch (Exception f) {
                    if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Fallback URL failed");
                }
            }
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    private class MyWebViewClient extends WebViewClient {

        MyWebViewClient() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!isRedirected || INCREMENT_WITH_REDIRECTS) {
                super.onPageStarted(view, url, favicon);

                if (Config.SHOW_FULL_SCREEN_AD && !HIDE_ADS_FOR_PURCHASE) {
                    if (Config.USE_FACEBOOK_ADS) {
                        if (facebookInterstitialAd != null) {
                            facebookInterstitialAd.loadAd();
                        }
                    } else {
                        if (mInterstitialAd == null) {
                            loadAdmobInterstatial();
                        }

                    }
                }
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isRedirected) {
                setTitle(view.getTitle());
                customCSS();
                customJavaScript();
                // Disable link drag and drop
                if (!Config.LINK_DRAG_AND_DROP) {
                    String disableLinkDragScript = "javascript: var links = document.getElementsByTagName('a');" +
                            "for (var i = 0; i < links.length; i++) {" +
                            "   links[i].draggable = false;" +
                            "}";
                    view.loadUrl(disableLinkDragScript);
                }
                if (SPLASH_SCREEN_ACTIVATED && SPLASH_SCREEN_ACTIVE && (SplashScreen.getInstance() != null) && REMAIN_SPLASH_OPTION) {
                    SplashScreen.getInstance().finish();
                    SPLASH_SCREEN_ACTIVE = false;
                }
                showInterstitial();
                super.onPageFinished(view, url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (!isRedirected) {
                hostpart = Uri.parse(url).getHost();
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "should override : " + url);

                // logic for loading given URL
                if (isConnectedNetwork()) {

                    // Check for a file download URL (can be internal or external)
                    if (url.contains(".") &&
                            downloadableExtension.contains(url.substring(url.lastIndexOf(".")))) {

                        webView.stopLoading();


                        String[] PERMISSIONS = {
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        };

                        if (Config.requireStorage) {
                            if (!hasPermissions(MainActivity.this, PERMISSIONS) && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)) {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, MULTIPLE_PERMISSIONS);
                                }
                            } else {
                                downloadFile(url);
                            }
                        }
                        return true;
                    }

                    if (!URLisExternal(url)) {
                        return false;

                    } else if (url.startsWith("inapppurchase://")
                            || url.startsWith("inappsubscription://")) {

                        if (extendediap) {
                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "play " + checkPlayServices());
                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "InApp URL: " + url);
                            if (checkPlayServices() && billingClient.isReady()) {
                                disableAdMob = url.contains("disableadmob");
                                isConsumable = url.contains("consumable=true");
                                handleAppPurchases(url);
                            } else {
                                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " toast ");
                                String iaptext1 = "U2VlIGh0dHBzOi8vdGlueXVybC5jb20vaWFwLWZpeCB8IEluLUFwcCBQdXJjaGFzZSBmYWlsZWQu";
                                byte[] iapdata1 = Base64.decode(iaptext1, Base64.DEFAULT);
                                String iapdata1final = new String(iapdata1, StandardCharsets.UTF_8);
                                Toast.makeText(MainActivity.this, iapdata1final, Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        } else {
                            String iaptext2 = "U2VlIGh0dHBzOi8vdGlueXVybC5jb20vaWFwLWZpeCB8IEluLUFwcCBQdXJjaGFzZSBmYWlsZWQu";
                            byte[] iapdata2 = Base64.decode(iaptext2, Base64.DEFAULT);
                            String iapdata2final = new String(iapdata2, StandardCharsets.UTF_8);
                            Toast.makeText(MainActivity.this, iapdata2final, Toast.LENGTH_LONG).show();
                            return true;
                        }
                    } else if (url.startsWith("qrcode://")) {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, url);
                        if (Config.requireCamera) {
                            // Check if the CAMERA permission is granted
                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                // Permission is not granted, request for permission
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1402);
                            } else {
                                // Permission has already been granted, start the activity
                                Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
                                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                            }
                        }
                        return true;
                    }
                    if (url.startsWith("savethisimage://?url=")) {
                        webView.stopLoading();
                        if (webView.canGoBack()) {
                            webView.goBack();
                        }
                        if (Config.requireStorage) {
                            final String imageUrl = url.substring(url.indexOf("=") + 1, url.length());
                            downloadImageNew("imagesaving", imageUrl);
                        }
                        return true;
                    } else if (url.contains("push.send.cancel")) {
                        if (Config.USER_AGENT.contains("VRGl")) {
                            if (url.contains("cartreminderpush.send.cancel")) {
                                stopCartReminderNotification();
                            }
                            if (url.contains("categoryrecommpush.cancel")) {
                                stopCategoryRecommNotification();
                            }
                            if (url.contains("productrecommpush.cancel")) {
                                stopProductRecommNotification();
                            }
                            return true;
                        }
                        else{
                        stopNotification();
                        return true;
                        }
                    }
                    else if (url.contains("push.send")) {
                        if (Config.USER_AGENT.contains("VRGl")) {
                            if (url.contains("cartreminderpush.send")) {
                                sendCartReminderNotification(url);
                            }
                            if (url.contains("categoryrecommpush.send")) {
                                sendCategoryRecommNotification(url);
                            }
                            if (url.contains("productrecommpush.send")) {
                                sendProductRecommNotification(url);
                            }
                            return true;
                        }
                        else {
                            sendNotification(url);
                            return true;
                        }
                    }  else if (url.startsWith("get-uuid://")) {
                        webView.loadUrl("javascript: var uuid = '" + uuid + "';");
                        return true;
                    } else if (url.startsWith("reset://")) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            CookieManager.getInstance().removeAllCookies(null);
                            CookieManager.getInstance().flush();
                        } else if (mContext != null) {
                            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(mContext);
                            cookieSyncManager.startSync();
                            CookieManager cookieManager = CookieManager.getInstance();
                            cookieManager.removeAllCookie();
                            cookieManager.removeSessionCookie();
                            cookieSyncManager.stopSync();
                            cookieSyncManager.sync();
                        }


                        WebSettings webSettings = webView.getSettings();
                        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                        webView.clearCache(true);
                        android.webkit.WebStorage.getInstance().deleteAllData();
                        Toast.makeText(MainActivity.this, "App reset was successful.", Toast.LENGTH_LONG).show();
                        loadMainUrl();
                        return true;
                    } else if (url.startsWith("readnfc://")) {
                        readModeNFC = true;
                        writeModeNFC = false;
                        return true;
                    } else if (url.startsWith("writenfc://")) {
                        writeModeNFC = true;
                        readModeNFC = false;
                        textToWriteNFC = url.substring(url.indexOf("=") + 1, url.length());
                        return true;
                    } else if (url.startsWith("spinneron://")) {
                        progressBar.setVisibility(View.VISIBLE);
                        return true;
                    } else if (url.startsWith("spinneroff://")) {
                        progressBar.setVisibility(View.GONE);
                        return true;
                    } else if (url.startsWith("takescreenshot://")) {
                        verifystoragepermissions(MainActivity.this);

                        Toast.makeText(MainActivity.this, "Screenshot Saved", Toast.LENGTH_LONG).show();
                        screenshot(getWindow().getDecorView().getRootView(), "result");

                        return true;

                    } else if (url.startsWith("getonesignalplayerid://")) {


                        OSDeviceState OneSignaldeviceState = OneSignal.getDeviceState();
                        String OneSignaluserID = OneSignaldeviceState.getUserId();
                        webView.loadUrl("javascript: var onesignalplayerid = '" + OneSignaluserID + "';");

                        return true;

                    }  else if (url.startsWith("getfirebaseplayerid://")) {

                        String firebaseUserId = AlertManager.getFirebaseToken(MainActivity.this, "");
                        webView.loadUrl("javascript: var firebaseplayerid = '" + firebaseUserId + "';");

                        return true;

                    } else if (url.startsWith("getappversion://")) {
                        webView.loadUrl("javascript: var versionNumber = '" + BuildConfig.VERSION_NAME + "';" +
                                "var bundleNumber  = '" + BuildConfig.VERSION_CODE + "';");
                        return true;

                    } else if (url.startsWith("shareapp://")) {

                        String sharetext = url.toString();
                        String newmeg = sharetext.substring(20);
                        if (BuildConfig.IS_DEBUG_MODE) Log.d("newmeg", newmeg);

                        String inputString = newmeg;
                        String delimiter = "&url=";
                        String[] components = inputString.split(delimiter);
                        String message2 = "";
                        String url2 = "";

                        if (components.length > 1) {
                            message2 = components[0];
                            url2 = components[1];
                        } else {
                            message2 = newmeg;
                        }
                        String message1 = message2.replace("%20", " ");
                        String url1 = url2.replace("%20", " ");

                        String totalMessage = "";
                        if (message1.length() == 0) {
                            totalMessage = url1;
                        } else if (url1.length() == 0) {
                            totalMessage = message1;
                        } else {
                            totalMessage = message1 + "\n" + url1;
                        }

                        List<String> objectsToShare = new ArrayList<>();
                        objectsToShare.add(totalMessage);

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, totalMessage);

                        Intent chooser = Intent.createChooser(intent, "Share via");
                        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        List<LabeledIntent> intents = new ArrayList<>();
                        for (ResolveInfo info : getPackageManager().queryIntentActivities(intent, 0)) {
                            Intent target = new Intent(Intent.ACTION_SEND);
                            target.setType("text/plain");
                            target.putExtra(Intent.EXTRA_TEXT, totalMessage);
                            target.setPackage(info.activityInfo.packageName);
                            intents.add(new LabeledIntent(target, info.activityInfo.packageName, info.loadLabel(getPackageManager()), info.icon));
                        }

                        Parcelable[] extraIntents = intents.toArray(new Parcelable[intents.size()]);
                        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
                        startActivity(chooser);

                        return true;
                    }
                    else if (url.startsWith("statusbarcolor://") && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {

                        String input = url.substring(url.indexOf('/') + 2);
                        String[] values = input.split(",");
                        int nbValues = values.length;

                        if (nbValues == 3 || nbValues == 4) {
                            int colorValues[] = new int[nbValues];
                            for (int i = 0; i < nbValues; i++) {
                                colorValues[i] = Integer.parseInt(values[i].trim());
                            }
                            int color;
                            Double luminance = 0.0;
                            Double rgbFactor = 255.0;
                            if (nbValues == 3) {
                                // Index 0 = red, 1 = green, 2 = blue
                                color = Color.rgb(colorValues[0], colorValues[1], colorValues[2]);
                                luminance = 0.2126 * (colorValues[0] / rgbFactor) + 0.7152 * (colorValues[1] / rgbFactor) + 0.0722 * (colorValues[2] / rgbFactor);
                            } else {
                                // Inlcudes transparency (alpha); This feature is not fully supported yet as the webview dimensions need to be changed as well.
                                // Index 0 = alpga, 1 = red, 2 = green, 3 = blue
                                color = Color.argb(colorValues[0], colorValues[1], colorValues[2], colorValues[3]);
                            }
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(color);
                            window.setNavigationBarColor(color);

                            // Automatically decide the color of the status bar and navigation bar text
                            Double darkThreshold = 0.5;
                            View decorView = getWindow().getDecorView();
                            int flags = decorView.getSystemUiVisibility();
                            if (luminance < darkThreshold) {
                                // Color is dark; use white text
                                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                                decorView.setSystemUiVisibility(flags);

                            } else {
                                // Color is light; use black text
                                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                        return true;

                    } else if (url.startsWith("statusbartextcolor://") && ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))) {

                        String input = url.substring(url.indexOf('/') + 2);
                        View decorView = getWindow().getDecorView();
                        int flags = decorView.getSystemUiVisibility();

                        if (input.equals("white")) {
                            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                            decorView.setSystemUiVisibility(flags);

                        } else if (input.equals("black")) {
                            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                            decorView.setSystemUiVisibility(flags);
                        }

                        return true;

                    } else if (url.startsWith("bottombarcolor://") && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {

                        String input = url.substring(url.indexOf('/') + 2);
                        String[] values = input.split(",");
                        int nbValues = values.length;

                        if (nbValues == 3 || nbValues == 4) {
                            int colorValues[] = new int[nbValues];
                            for (int i = 0; i < nbValues; i++) {
                                colorValues[i] = Integer.parseInt(values[i].trim());
                            }
                            int color;
                            if (nbValues == 3) {
                                // Index 0 = red, 1 = green, 2 = blue
                                color = Color.rgb(colorValues[0], colorValues[1], colorValues[2]);
                            } else {
                                // Inlcudes transparency (alpha); This feature is not fully supported yet as the webview dimensions need to be changed as well.
                                // Index 0 = alpga, 1 = red, 2 = green, 3 = blue
                                color = Color.argb(colorValues[0], colorValues[1], colorValues[2], colorValues[3]);
                            }
                            Window window = getWindow();
                            window.setNavigationBarColor(color);
                        }
                        return true;

                    } else if (url.startsWith("navbartextcolor://") && ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))) {

                        String input = url.substring(url.indexOf('/') + 2);
                        View decorView = getWindow().getDecorView();
                        int flags = decorView.getSystemUiVisibility();

                        if (input.equals("white")) {
                            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                            decorView.setSystemUiVisibility(flags);

                        } else if (input.equals("black")) {
                            flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                            decorView.setSystemUiVisibility(flags);
                        }

                        return true;

                    } else if (url.startsWith("scanningmode://")) {

                        String input = url.substring(url.indexOf('/') + 2);

                        if (input.equals("auto")) {
                            turnOnScanningMode();
                        } else if (input.equals("on")) {
                            persistentScanningMode = true;
                            turnOnScanningMode();
                        } else if (input.equals("off")) {
                            persistentScanningMode = false;
                            turnOffScanningMode();
                        }
                        return true;
                    }
                } else if (!isConnectedNetwork()) {
                    if (Config.FALLBACK_USE_LOCAL_HTML_FOLDER_IF_OFFLINE) {
                        if (!offlineFileLoaded) {
                            loadLocal(INDEX_FILE);
                            offlineFileLoaded = true;
                        } else {
                            loadLocal(url);
                        }
                    } else {
                        offlineLayout.setVisibility(View.VISIBLE);
                    }
                    return true;
                }

                if (hostpart.contains("whatsapp.com")) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    final int newDocumentFlag = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? Intent.FLAG_ACTIVITY_NEW_DOCUMENT : Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | newDocumentFlag | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }

                if (((Config.EXTERNAL_LINK_HANDLING_OPTIONS != 0)
                        && !(url).startsWith("file://") && (!Config.USE_LOCAL_HTML_FOLDER
                        || !(url).startsWith("file://"))) && URLUtil.isValidUrl(url)) {
                    if (Config.EXTERNAL_LINK_HANDLING_OPTIONS == 1) {
                        // open in a new tab (additional in-app browser)
                        openInInappTab(url);
                        return true;
                    } else if (Config.EXTERNAL_LINK_HANDLING_OPTIONS == 2) {
                        // open in a new browser
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    private void turnOnScanningMode() {
        if (!scanningModeOn) {
            WindowManager.LayoutParams layout = getWindow().getAttributes();

            // Record previous screen brightness
            previousScreenBrightness = layout.screenBrightness;

            // Turn on scanning mode
            scanningModeOn = true;
            layout.screenBrightness = 1F;
            getWindow().setAttributes(layout);
            if (!PREVENT_SLEEP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }

    private void turnOffScanningMode() {
        if (scanningModeOn) {
            WindowManager.LayoutParams layout = getWindow().getAttributes();

            // Turn off scanning mode
            scanningModeOn = false;
            layout.screenBrightness = previousScreenBrightness;
            getWindow().setAttributes(layout);
            if (!PREVENT_SLEEP) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }

    private void handleAppPurchases(String url) {
        String keyPackage = "package=";
        String keySuccessURL = "&successful_url=";
        String keyExpiredURL = "&expired_url=";
        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "play " + checkPlayServices());
        int packageIndex = -1;
        int successIndex = -1;
        int expireIndex = -1;
        String packagePlan = "";
        if (url.contains(keyPackage)) {
            packageIndex = url.indexOf(keyPackage) + keyPackage.length();
        }
        if (url.contains(keySuccessURL)) {
            successIndex = url.indexOf(keySuccessURL) + keySuccessURL.length();
        }
        if (url.contains(keyExpiredURL)) {
            expireIndex = url.indexOf(keyExpiredURL) + keyExpiredURL.length();
        }
        try {
            if (packageIndex != -1) {
                packagePlan = url.substring(packageIndex, url.indexOf("&"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (expireIndex == -1) {
                successUrl = url.split(keySuccessURL)[1];
                failUrl = "";
            } else {
                successUrl = url.substring(successIndex, expireIndex - keyExpiredURL.length());
                failUrl = url.substring(expireIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!packagePlan.isEmpty()) {
            List<String> skuList = new ArrayList<>();
            skuList.add(packagePlan);
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            if (url.startsWith("inapppurchase://")) {
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
            } else if (url.startsWith("inappsubscription://")) {
                params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
            }
            checkItemPurchase(params);
        } else {
            Toast.makeText(this, "Unable to get any package. Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNotification(String url) {
        final int secondsDelayed = Integer.parseInt(url.split("=")[1]);

        final String[] contentDetails = (url.substring((url.indexOf("msg!") + 4), url.length())).split("&!#");
        String message = contentDetails[0].replaceAll("%20", " ");
        String title = contentDetails[1].replaceAll("%20", " ");

        try {
            message = URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            title = URLDecoder.decode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urlToOpen = null;
        // if data has length greater then 2 then there should be URL at index 2
        if(contentDetails.length > 2) {
            urlToOpen = contentDetails[2].replaceAll("%20", " ");
        }

        final Notification.Builder builder = getNotificationBuilder(title, message, urlToOpen);

        final Notification notification = builder.build();
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationHandler = new Handler();
        notificationHandler.postDelayed(() -> {
            notificationManager.notify(0, notification);
            notificationHandler = null;
        }, secondsDelayed * 1000);
    }

    private void sendCartReminderNotification(String url) {



        final int secondsDelayed = Integer.parseInt(url.split("=")[1]);

        final String[] contentDetails = (url.substring((url.indexOf("msg!") + 4), url.length())).split("&!#");

        String message = contentDetails[0].replaceAll("%20", " ");
        String title = contentDetails[1].replaceAll("%20", " ");


        try {
            message = URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            title = URLDecoder.decode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String urlToOpen = null;
        // if data has length greater than 2 then there should be URL at index 2
        if (contentDetails.length > 2) {
            urlToOpen = contentDetails[2].replaceAll("%20", " ");
        }

        final Notification.Builder builder = getNotificationBuilder(title, message, urlToOpen);

        final Notification notification = builder.build();
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        CartRemindernotificationHandler = new Handler();
        CartRemindernotificationHandler.postDelayed(() -> {
            notificationManager.notify(0, notification);
            CartRemindernotificationHandler = null;
        }, secondsDelayed * 1000);
    }
    private void sendCategoryRecommNotification(String url) {


        final int secondsDelayed = Integer.parseInt(url.split("=")[1]);

        final String[] contentDetails = (url.substring((url.indexOf("msg!") + 4), url.length())).split("&!#");


        String message = contentDetails[0].replaceAll("%20", " ");
        String title = contentDetails[1].replaceAll("%20", " ");


        try {
            message = URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            title = URLDecoder.decode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String urlToOpen = null;
        // if data has length greater than 2 then there should be URL at index 2
        if (contentDetails.length > 2) {
            urlToOpen = contentDetails[2].replaceAll("%20", " ");
        }

        final Notification.Builder builder = getNotificationBuilder(title, message, urlToOpen);

        final Notification notification = builder.build();
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        CategoryRecommNotificationHandler = new Handler();
        CategoryRecommNotificationHandler.postDelayed(() -> {
            notificationManager.notify(0, notification);
            CategoryRecommNotificationHandler = null;
        }, secondsDelayed * 1000);
    }
    private void sendProductRecommNotification(String url) {
        final int secondsDelayed = Integer.parseInt(url.split("=")[1]);

        final String[] contentDetails = (url.substring((url.indexOf("msg!") + 4), url.length())).split("&!#");
        String message = contentDetails[0].replaceAll("%20", " ");
        String title = contentDetails[1].replaceAll("%20", " ");


        try {
            message = URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            title = URLDecoder.decode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urlToOpen = null;
        // if data has length greater than 2 then there should be URL at index 2
        if (contentDetails.length > 2) {
            urlToOpen = contentDetails[2].replaceAll("%20", " ");
        }

        final Notification.Builder builder = getNotificationBuilder(title, message, urlToOpen);

        final Notification notification = builder.build();
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        ProductRecommNotificationHandler = new Handler();
        ProductRecommNotificationHandler.postDelayed(() -> {
            notificationManager.notify(0, notification);
            ProductRecommNotificationHandler = null;
        }, secondsDelayed * 1000);
    }

    private void stopNotification() {
        if (notificationHandler != null) {
            notificationHandler.removeCallbacksAndMessages(null);
            notificationHandler = null;
        }
    }
    private void stopCartReminderNotification() {
        if (CartRemindernotificationHandler != null) {
            CartRemindernotificationHandler.removeCallbacksAndMessages(null);
            CartRemindernotificationHandler = null;
        }
    }
    private void stopCategoryRecommNotification() {
        if (CategoryRecommNotificationHandler != null) {
            CategoryRecommNotificationHandler.removeCallbacksAndMessages(null);
            CategoryRecommNotificationHandler = null;
        }
    }
    private void stopProductRecommNotification() {
        if (ProductRecommNotificationHandler != null) {
            ProductRecommNotificationHandler.removeCallbacksAndMessages(null);
            ProductRecommNotificationHandler = null;
        }
    }

    private Notification.Builder getNotificationBuilder(String title, String message, String urlToOpen) {

        createNotificationChannel();
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(MainActivity.this, getString(R.string.local_notification_channel_id));
        } else {
            builder = new Notification.Builder(MainActivity.this);
        }

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("ONESIGNAL_URL", urlToOpen);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        return builder;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.local_notification_channel_name);
            String description = getString(R.string.local_notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.local_notification_channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void downloadFile(String url) {
        try {
            String fileName = getFileNameFromURL(url);
            Toast.makeText(MainActivity.this, "Downloading file...", Toast.LENGTH_SHORT).show();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            String cookie = CookieManager.getInstance().getCookie(url);
            request.addRequestHeader("Cookie", cookie);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }


        BroadcastReceiver onComplete = new BroadcastReceiver() {

            public void onReceive(Context ctxt, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    openDownloadedAttachment(MainActivity.this, downloadId);
                }
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    public static boolean isRooted() { //credit to Sanjay Bhalani (https://stackoverflow.com/a/57590343)
        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }
        // try executing commands
        //return canExecuteCommand("/system/xbin/which su")|| canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
        if(!canExecuteCommand("su"))
            if(findBinary("su"))
                return true;
        return false;
    }
    private void showRootedErrorMessage() {

        new AlertDialog.Builder(this)
                .setTitle("Security Error")
                .setMessage("This app cannot run on a rooted device for security reasons.")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Close the app
                    }
                })
                .setCancelable(false)
                .show();
    }
    public static boolean findBinary(String binaryName) {  //credit to Sanjay Bhalani (https://stackoverflow.com/a/57590343)
        boolean found = false;
        if (!found) {
            String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
            for (String where : places) {
                if (new File(where + binaryName).exists()) {
                    found = true;

                    break;
                }
            }
        }
        return found;
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {  //credit to Sanjay Bhalani (https://stackoverflow.com/a/57590343)
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }
        return executedSuccesfully;
    }

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                return "";
            }
        } catch (MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();


        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    private class CustomeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (ENABLE_SWIPE_NAVIGATE) {
                if (e1 == null || e2 == null) return false;
                if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1) return false;
                else {

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int screenWidth = displayMetrics.widthPixels;
                    int edgeSwipeTolerance = 30;

                    try {
                        // Detect a left swipe
                        if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 800) {
//                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "LEFT swipe: e1.X = " + e1.getX() + ", e2.X = " + e2.getX());
                            // Detect a "forwards" gesture (left swipe from the right edge)
                            if (e1.getX() > (screenWidth - edgeSwipeTolerance)) {
                                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "FORWARDS swipe detected");
                                if (webView.canGoForward()) {
                                    webView.goForward();
                                }
                                return true;
                            }
                        }
                        // Detect a right swipe
                        else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 800) {
//                            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "RIGHT swipe: e1.X = " + e1.getX() + ", e2.X = " + e2.getX());
                            // Detect a "backwards" gesture (right swipe from the left edge)
                            if (e1.getX() < edgeSwipeTolerance) {
                                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "BACKWARDS swipe detected");
                                if (webView.canGoBack()) {
                                    webView.goBack();
                                }
                                return true;
                            }
                        }
                    } catch (Exception e) { // nothing
                    }
                    return false;
                }
            }
            return false;
        }
    }

    private class AdvanceWebChromeClient extends MyWebChromeClient {

        private Handler notificationHandler;
        private Handler CartRemindernotificationHandler;
        private Handler CategoryRecommNotificationHandler;
        private Handler ProductRecommNotificationHandler;

        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
            ClosePopupWindow(mWebviewPop);
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "onCloseWindow url " + window.getUrl());
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "onCloseWindow url " + window.getOriginalUrl());
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {

            Bundle extras = getIntent().getExtras();
            String URL = null;
            if (extras != null) {
                URL = extras.getString("ONESIGNAL_URL");
            }
            if (URL != null && !URL.equalsIgnoreCase("")) {
                isNotificationURL = true;
                deepLinkingURL = URL;
            } else isNotificationURL = false;
            preferences.edit().putString("proshow", "show").apply();

            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " LOG24 " + deepLinkingURL);

            WebView.HitTestResult result = view.getHitTestResult();
            String data = result.getExtra();
            
            // Link with an Image
            if (result.getType() == result.SRC_IMAGE_ANCHOR_TYPE) {
                // Get the source link, not the image link
                Message href = view.getHandler().obtainMessage();
                view.requestFocusNodeHref(href);
                String imageLinkSource = href.getData().getString("url");
                data = imageLinkSource;
            }

            // Check if the URL should always open in an in-app tab
            if ((data != null) && shouldAlwaysOpenInInappTab(data)) {
                openInInappTab(data);
                return true;
            }

            // Open special link in-app
            if (SPECIAL_LINK_HANDLING_OPTIONS == 0) {

                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "if ");

                if ((data == null) || (data != null && data.endsWith("#"))) {
                    if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "else true ");
                    windowContainer.setVisibility(View.VISIBLE);
                    mWebviewPop = new WebView(view.getContext());
                    webViewSetting(mWebviewPop);

                    mWebviewPop.setWebChromeClient(new AdvanceWebChromeClient());
                    mWebviewPop.setWebViewClient(new AdvanceWebViewClient());
                    mWebviewPop.getSettings().setUserAgentString(mWebviewPop.getSettings().getUserAgentString().replace("wv", ""));
                    mContainer.addView(mWebviewPop);

                    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                    transport.setWebView(mWebviewPop);
                    resultMsg.sendToTarget();
                    return true;
                } else {

                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                    webSettings.setSupportMultipleWindows(true);

                    if (URLUtil.isValidUrl(data)) {
                        webView.loadUrl(data);
                    }
                }

            // Open special link in a new in-app tab
            } else if (SPECIAL_LINK_HANDLING_OPTIONS == 1) {

                if (data == null) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
                    CustomTabsIntent customTabsIntent = builder.build();
                    WebView newWebView = new WebView(view.getContext());
                    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                    transport.setWebView(newWebView);
                    resultMsg.sendToTarget();
                    newWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            // Retrieve cookies from WebView & set cookies in the customTabsIntent WebView
                            CookieManager cookieManager = CookieManager.getInstance();
                            String allCookies = cookieManager.getCookie(Uri.parse(url).toString());
                            if (allCookies != null) {
                                String[] cookieList = allCookies.split(";");
                                for (String cookie : cookieList) {
                                    customTabsIntent.intent.putExtra("android.webkit.CookieManager.COOKIE", cookie.trim());
                                }
                            }
                            customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                            webView.stopLoading();
                            return false;
                        }
                    });
                } else {
                    openInInappTab(data);
                }

            // Open special link in Chrome
            } else if (SPECIAL_LINK_HANDLING_OPTIONS == 2) {

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
                if (BuildConfig.IS_DEBUG_MODE) Log.d("TAG", " data " + data);
                WebView newWebView = new WebView(view.getContext());
                newWebView.setWebChromeClient(new WebChromeClient());
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
            }

            if (BuildConfig.IS_DEBUG_MODE) Log.d("TAG", " running this main activity ");
            return true;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, " onJsalert");
            return super.onJsAlert(view, url, message, result);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUM = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/pdf", "image/*"};
            i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(Intent.createChooser(i, "Upload"), FCR);
        }

        @SuppressLint("InlinedApi")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

            if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)) {

                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;

                if (Arrays.asList(fileChooserParams.getAcceptTypes()).contains("audio/*")) {
                    Intent chooserIntent = fileChooserParams.createIntent();
                    startActivityForResult(chooserIntent, CODE_AUDIO_CHOOSER);
                    return true;
                }

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                    File videoFile = null;
                    try {
                        videoFile = createVideoFile();
                        takeVideoIntent.putExtra("PhotoPath", mVM);
                    } catch (IOException ex) {
                        if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "Video file creation failed", ex);
                    }
                    if (videoFile != null) {
                        mVM = "file:" + videoFile.getAbsolutePath();
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", videoFile));
                    } else {
                        takeVideoIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                contentSelectionIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/* video/*");

                String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/pdf", "image/*", "video/*", "*/*"};
                contentSelectionIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

                Intent[] intentArray;
                if (takePictureIntent != null && takeVideoIntent != null) {
                    intentArray = new Intent[]{takePictureIntent, takeVideoIntent};
                } else if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else if (takeVideoIntent != null) {
                    intentArray = new Intent[]{takeVideoIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Upload");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);

                return true;

            } else {
                if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "File Chooser permissions not granted - requesting permissions");
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                }
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                }
                return false;
            }
        }


        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/pdf", "image/*"};
            i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {

        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyWebChromeClient() {
        }


        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
            webView.clearFocus();
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;

            if (Config.LANDSCAPE_FULLSCREEN_VIDEO) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }

        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

        boolean progressBarActive = false;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "progress " + newProgress);

            //Activate progress bar if this is a new redirect
            if (ACTIVATE_PROGRESS_BAR && !progressBarActive) {
                progressBar.setVisibility(View.VISIBLE);
                progressBarActive = true;
            }

            isRedirected = true;
            String name = preferences.getString("proshow", "");

            if (ACTIVATE_PROGRESS_BAR && name.equals("show")) {
                progressBar.setVisibility(View.VISIBLE);
            }

            if (newProgress >= 80 && ACTIVATE_PROGRESS_BAR && progressBarActive) {
                /* remove progress bar when page has been loaded 80%,
                 since the frame will likely have already changed to new page
                 otherwise, the spinner will still be visible
                 while non-critical resources load in background*/
                progressBar.setVisibility(View.GONE);
                progressBarActive = false;
            }

            if (newProgress == 100) {
                isRedirected = false;
                mAdView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
            }

            if (!ACTIVATE_PROGRESS_BAR) {
                progressBar.setVisibility(View.GONE);
                progressBarActive = false;
            }
        }

        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            request.grant(request.getResources());
        }

    }

    private void webViewSetting(WebView intWebView) {

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(intWebView, true);
        }

        WebSettings webSettings = intWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Config.CLEAR_CACHE_ON_STARTUP) {
            //webSettings.setAppCacheEnabled(false);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            //webSettings.setAppCacheEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(intWebView, true);
        }
        intWebView.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webSettings.setSupportMultipleWindows(true);
        webSettings.setUseWideViewPort(true);

        if (!Config.USER_AGENT.isEmpty()) {
            webSettings.setUserAgentString(webSettings.getUserAgentString().replace("wv", ""));
        }


    }

    // nfc

    private void initNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            readFromIntent(getIntent());
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
            writeTagFilters = new IntentFilter[]{tagDetected};
        }
    }


    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];

                }
            }
            read(msgs);
        }
    }

    private void read(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);

            webView.loadUrl("javascript: readNFCResult('" + text + "');");


        } catch (UnsupportedEncodingException e) {
            if (BuildConfig.IS_DEBUG_MODE) Log.d("UnsupportedEncoding", e.toString());
        }

        TextView textView = new TextView(this);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextColor(Color.BLUE);
        textView.setText("read : " + text);

    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        writeData(tag, message);

    }

    public void writeData(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndefTag = Ndef.get(tag);
                if (ndefTag == null) {
                    // Let's try to format the Tag in NDEF
                    NdefFormatable nForm = NdefFormatable.get(tag);
                    if (nForm != null) {
                        nForm.connect();
                        nForm.format(message);
                        nForm.close();
                        toast(WRITE_SUCCESS);
                    }
                } else {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(message);
                    ndefTag.close();
                    toast(WRITE_SUCCESS);
                }
            } catch (Exception e) {
                e.printStackTrace();
                toast("write error : " + e.getMessage());
            }
        }
    }

//    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {

//    }

    private NdefRecord createRecord(String text)
            throws UnsupportedEncodingException {

        if (text.startsWith("VCARD")) {

            String nameVcard = "BEGIN:" +
                    text.replace('_', '\n').replace("%20", " ")
                    + '\n' + "END:VCARD";

            byte[] uriField = nameVcard.getBytes(StandardCharsets.US_ASCII);
            byte[] payload = new byte[uriField.length + 1];              //add 1 for the URI Prefix
            //payload[0] = 0x01;                                      //prefixes http://www. to the URI
            System.arraycopy(uriField, 0, payload, 1, uriField.length);  //appends URI to payload

            NdefRecord nfcRecord = new NdefRecord(
                    NdefRecord.TNF_MIME_MEDIA, "text/vcard".getBytes(), new byte[0], payload);

//        byte[] vCardDataBytes = nameVcard.getBytes(Charset.forName("UTF-8"));
//        byte[] vCardPayload = new byte[vCardDataBytes.length+1];
//        System.arraycopy(vCardDataBytes, 0, vCardPayload, 1, vCardDataBytes.length);
//// vCardDataBytes[0] = (byte)0x00;
//        NdefRecord nfcRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,"text/x-vcard".getBytes(),new byte[] {}, vCardPayload);

            return nfcRecord;
        }

        //Intent intent = getIntent();
        //EditText editTextWeb = (EditText)

        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }

    private String getDeepLinkingURL(Intent intent) {
        if (intent.getData() != null
                && (intent.getData().getScheme().equals("http") || intent.getData().getScheme().equals("https"))) {
            Uri data = intent.getData();

            List<String> pathSegments = data.getPathSegments();
            if (pathSegments.size() > 0) {
                String hostOfLink = data.getHost();

                // Validate the host against expected value (e.g., your app's host)
                if (hostOfLink.contains(Config.HOST)) {
                    String localDeepLinkingURL;

                    // Reconstruct the URL for WebView
                    localDeepLinkingURL = data.getScheme() + "://" + data.getHost() + data.getPath();

                    String normalizeString = data.normalizeScheme().toString();
                    if (normalizeString.length() == localDeepLinkingURL.length()) {
                        localDeepLinkingURL = sanitizeURL(localDeepLinkingURL);
                    } else {
                        localDeepLinkingURL = normalizeString;
                    }

                    if (BuildConfig.IS_DEBUG_MODE) Log.d(TAG, "getDeepLinkingURL: " + localDeepLinkingURL);

                    return localDeepLinkingURL;
                }
            }
        }
        return null;
    }

    // Sanitize URL to prevent XSS
    private String sanitizeURL(String url) {
        // Apply a whitelist approach to allow only known safe characters and patterns
        // Remove any characters that are not alphanumeric, forward slash, or colon.
        String localURL = url.replaceAll("[^-.a-zA-Z\\d/:]", "");

        // Apply Content Security Policy (CSP) if applicable
        return applyCSP(localURL);
    }

    // Apply Content Security Policy (CSP) to the URL if necessary
    private String applyCSP(String url) {
        // This method can add CSP headers or modify the URL as per your app's requirements

        // Add a CSP header to restrict inline scripts and unsafe sources
        String cspHeaderValue = "default-src 'self'; script-src 'self' 'unsafe-inline'; object-src 'none'; style-src 'self' 'unsafe-inline'; img-src 'self'; media-src 'self'; frame-src 'none'; font-src 'self'; connect-src 'self';";

        // Append the CSP header to the URL as a query parameter
        return url + "?CSP_HEADER=" + Uri.encode(cspHeaderValue);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        if (intent != null) {
            Bundle extras = intent.getExtras();
            String URL = null;
            if (extras != null) {
                URL = extras.getString("ONESIGNAL_URL");
            } else if (intent.getDataString() != null) {
                URL = intent.getDataString();
            }
            handleURl(URL);
        }
        if (!readModeNFC && !writeModeNFC) {
            return;
        }
        super.onNewIntent(intent);
        setIntent(intent);
        if (readModeNFC) {
            readFromIntent(intent);
        }
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            toast("tag detected : " + myTag.toString());


            try {
                if (writeModeNFC) {
                    write(textToWriteNFC, myTag);
                }
            } catch (IOException | FormatException e) {
                e.printStackTrace();
                Toast.makeText(this, WRITE_ERROR, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ClosePopupWindow(View view) {

        progressBar.setVisibility(View.GONE);
        preferences = getPreferences(MODE_PRIVATE);
        preferences.edit().putString("proshow", "noshow").apply();
        mContainer.removeAllViews();
        windowContainer.setVisibility(View.GONE);
        mWebviewPop.destroy();

    }

    private void WriteModeOn() {
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    private void WriteModeOff() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void ExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.exit_app_dialog))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();
    }


    private void setOfflineScreenBackgroundColor() {
        offlineLayout.getBackground().setColorFilter(Color.parseColor(Config.OFFLINE_SCREEN_BACKGROUND_COLOR), PorterDuff.Mode.DARKEN);
    }
    
    boolean shouldAlwaysOpenInInappTab (String URL) {
        for (int i = 0; i < Config.ALWAYS_OPEN_IN_INAPP_TAB.length; i++) {
            if ((Config.ALWAYS_OPEN_IN_INAPP_TAB[i] != "") && (URL.startsWith(Config.ALWAYS_OPEN_IN_INAPP_TAB[i]))) {
                return true;
            }
        }
        return false;
    }

    void openInInappTab(String URL) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
        CustomTabsIntent customTabsIntent = builder.build();
        // Retrieve cookies from WebView & set cookies in the customTabsIntent WebView
        CookieManager cookieManager = CookieManager.getInstance();
        String allCookies = cookieManager.getCookie(URL);
        if (allCookies != null) {
            String[] cookieList = allCookies.split(";");
            for (String cookie : cookieList) {
                customTabsIntent.intent.putExtra("android.webkit.CookieManager.COOKIE", cookie.trim());
            }
        }
        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(URL));
        webView.stopLoading();
    }

    void openInNewBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }
}
