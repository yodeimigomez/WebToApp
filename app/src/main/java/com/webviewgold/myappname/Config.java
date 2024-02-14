package com.webviewgold.myappname;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Config {
    // ***********************************************************
    // *** THANKS FOR BEING PART OF THE WEBVIEWGOLD COMMUNITY! ***
    // ***********************************************************
    // *** Your Purchase Code of CodeCanyon ***
    // 1. Buy a WebViewGold license (https://www.webviewgold.com/download/android) for each app you publish. If your app is going to be free, a "Regular license" is required. If your app will be sold to your users or if you use the In-App Purchases API, an "Extended license" is required. More info: https://codecanyon.net/licenses/standard?ref=onlineappcreator
    // 2. Grab your Purchase Code (this is how to find it quickly: https://help.market.envato.com/hc/en-us/articles/202822600-Where-Is-My-Purchase-Code-)
    // 3. Great! Just enter it here and restart your app:
    public static final String PURCHASECODE = "xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx";
    // 4. Enjoy your app! :)

    /**
     * Main Configuration Of Your WebViewGold App
     */
    // Domain host and subdomain without any https:// or http:// prefixes (e.g., "www.example.org")
    public static final String HOST = "facturandord.com";

    // Your URL including https:// or http:// prefix and including www. or any required subdomain (e.g., "https://www.example.org")
    public static String HOME_URL = "https://facturandord.com";

    // Set to "false" to disable the progress spinner/loading spinner
    public static final boolean ACTIVATE_PROGRESS_BAR = true;

    // Set the phone orientation to either "portrait", "landscape", or "auto"
    public static final String PHONE_ORIENTATION = "portrait";

    // Set the tablet orientation to either "portrait", "landscape", or "auto"
    public static final String TABLET_ORIENTATION = "portrait";

    // Set a customized UserAgent for WebView URL requests (or leave it empty to use the default Android UserAgent)
    public static final String USER_AGENT = "";

    // Set to "true" to activate Hardware Acceleration; it can improve rendering performance but can also increase memory usage and may cause compatibility issues with some content
    public static final boolean HARDWARE_ACCELERATION = false;

    // Set to "true" if you want to extend URL request by the system language like ?webview_language=LANGUAGE CODE (e.g., ?webview_language=EN for English users)
    public static final boolean APPEND_LANG_CODE = false;

    // Set this to "true" if you want the WebView to automatically refresh its contents when the app comes back to the foreground from the background
    public static final boolean AUTO_REFRESH_ENABLED = false;

    // Set to "true" if you want to use the "local-html" folder fallback if the user is offline
    public static final boolean FALLBACK_USE_LOCAL_HTML_FOLDER_IF_OFFLINE = false;

    // Set to (0) to open external links in-app by default; (1) to ALWAYS open in a new tab (an additional in-app browser); (2) to ALWAYS open in another browser
    public static final int EXTERNAL_LINK_HANDLING_OPTIONS = 0;

    // Set to (0) to open special links in-app; (1) in a new tab (an additional in-app browser); (2) in another browser
    // NOTE: Special links have a "_blank" target or end with "#"; Overrides EXTERNAL_URL_HANDLING_OPTIONS if the link is also an external link
    public static final int SPECIAL_LINK_HANDLING_OPTIONS = 1;

    // Add URL prefixes that you ALWAYS want to open in an in-app tab (e.g., {"https://www.google.com", "https://www.example.com/page"})
    public static String[] ALWAYS_OPEN_IN_INAPP_TAB = new String[]{"https://www.alwaysopeninaninapptab.com"};

    // Set to (0) to open a scanned QR code URL in the app; (1) in an in-app tab; (2) in a new browser; (3) in an in-app tab if external; (4) in a new browser if external
    public static final int QR_CODE_URL_OPTIONS = 0;

    // Set to "true" to clear the WebView cache & cookies on each app startup and do not use cached versions of your web app/website
    public static final boolean CLEAR_CACHE_ON_STARTUP = false;

    // Set to "true" to clear WebView cache & cookies upon full app exit (you might also want to activate CLEAR_CACHE_ON_STARTUP, as system differences could affect reliability)
    public static final boolean CLEAR_CACHE_ON_EXIT = false;

    //Set to "true" to use local "assets/index.html" file instead of URL
    public static final boolean USE_LOCAL_HTML_FOLDER = false;

    //Set to "true" to enable deep linking for App Links & Push (take a look at the documentation for further information)
    public static final boolean IS_DEEP_LINKING_ENABLED = true;

    //Set to "true" to open the notification deep linking URLs in the system browser instead of your app
    public static final boolean OPEN_NOTIFICATION_URLS_IN_SYSTEM_BROWSER = false;

    // Set to "true" to activate the splash screen
    public static final boolean SPLASH_SCREEN_ACTIVATED = true;

    //Set the splash screen timeout time in milliseconds (the loading sign screen will show after this time duration if the home URL still has some loading to do)
    public static final int SPLASH_TIMEOUT = 1300;

    //Set to "true" to show the splash screen until the home URL has finished loading (overrides SPLASH_TIMEOUT)
    public static final boolean REMAIN_SPLASH_OPTION = false;

    //Set the splash screen image size with respect to the device's smallest width/height; range in percentage [0-100]; Caution: value  = 0 will hide the image completely
    public static final double SCALE_SPLASH_IMAGE = 25;

    //Set to "true" for black status bar text; Set to "false" for white status bar text; Use 'colorPrimaryDark' in style.xml to choose the status bar background color
    static boolean blackStatusBarText = false;

    //Set to "true" to prevent the device from going into sleep while the app is active
    public static final boolean PREVENT_SLEEP = false;

    //Set to "true" to enable navigation by swiping left or right to move back or forward a page
    public static final boolean ENABLE_SWIPE_NAVIGATE = false;

    //Set to "true" to enable swiping down to refresh the page
    public static final boolean ENABLE_PULL_REFRESH = true;

    //Set to "true" to enable pinch to zoom
    public static final boolean ENABLE_ZOOM = false;

    //Set to "true" to hide the vertical scrollbar
    public static final boolean HIDE_VERTICAL_SCROLLBAR = false;

    //Set to "true" to hide the horizontal scrollbar
    public static final boolean HIDE_HORIZONTAL_SCROLLBAR = false;

    //Set to "true" to disable dark mode (not working on all launchers)
    public static final boolean DISABLE_DARK_MODE = false;

    //Set to "true" to hide the navigation bar when in landscape mode
    public static final boolean HIDE_NAVIGATION_BAR_IN_LANDSCAPE = false;

    //Set to a value greater than 0 to define a maximum text zoom; Set to (0) to disable this feature
    //Note: Small = 85, Default = 100, Large = 115, Largest = 130
    public static final int MAX_TEXT_ZOOM = 0;

    //Set to "true" to close the app by pressing the hardware back button (instead of going back to the last page)
    public static final boolean EXIT_APP_BY_BACK_BUTTON_ALWAYS = false;

    //Set to "true" to close the app by pressing the hardware back button if the user is on the home page (which does not allow going to a prior page)
    public static final boolean EXIT_APP_BY_BACK_BUTTON_HOMEPAGE = true;

    //Set to "true" to ask the user if they want to exit before exiting the app
    public static final boolean EXIT_APP_DIALOG = true;

    //Set the color of the offline screen background using the Hex Color Code (e.g., "#ffffff" = White)
    public static String OFFLINE_SCREEN_BACKGROUND_COLOR = "#ffffff";

    //Set to "true" to prevent users from taking screenshots or screen recordings in the app
    public static boolean PREVENT_SCREEN_CAPTURE = false;

    // Set to "true" to add the UUID parameter 'uuid=XYZ' to the first URL request
    public static final boolean UUID_ENHANCE_WEBVIEW_URL = false;

    //Set to "true" to block content signed with self-signed SSL (user) certificates & faulty SSL certificates; maybe consider blocking all Non-HTTPS content, see https://www.webviewgold.com/support-center/knowledgebase/how-to-prevent-non-https-connections-in-webviewgold-for-android-switching-usescleartexttraffic-from-true-to-false/
    public static boolean BLOCK_SELF_SIGNED_AND_FAULTY_SSL_CERTS = false;

    // Set to "true" to enable the app's functionality to detect rooted devices and disable itself on them (Note: Root detection is not always reliable and may not work on all devices)
    public static boolean BLOCK_ROOTED_DEVICES = false;

    //Set to "false" to disable link drag and drop
    public static boolean LINK_DRAG_AND_DROP = true;

    //Set to "true" to always present fullscreen videos in landscape mode
    public static final boolean LANDSCAPE_FULLSCREEN_VIDEO = false;

    /**
     * Dialog Options
     */
    public static boolean SHOW_FIRSTRUN_DIALOG = false; //Set to false to disable the First Run Dialog
    public static boolean SHOW_FACEBOOK_DIALOG = false; //Set to false to disable the Follow On Facebook Dialog
    public static boolean SHOW_RATE_DIALOG = true; //Set to false to disable the Rate This App Dialog

    // Set the minimum number of days to be passed after the application is installed before the "Rate this app" dialog is displayed
    public static final int RATE_DAYS_UNTIL_PROMPT = 3;
    // Set the minimum number of application launches before the "Rate this app" dialog will be displayed
    public static final int RATE_LAUNCHES_UNTIL_PROMPT = 3;

    // Set the minimum number of days to be passed after the application is installed before the "Follow on Facebook" dialog is displayed
    public static final int FACEBOOK_DAYS_UNTIL_PROMPT = 2;
    // Set the minimum number of application launches before the "Rate this app" dialog will be displayed
    public static final int FACEBOOK_LAUNCHES_UNTIL_PROMPT = 4;
    // Set the URL of your Facebook page
    public static final String FACEBOOK_URL = "https://www.facebook.com/OnlineAppCreator/";

    // Set to "false" to prevent the "Download images" pop-up box from appearing when long-pressing on an image
    public static final boolean ALLOW_IMAGE_DOWNLOAD = true;


    /**
     * OneSignal Push Notification Options
     */
    //Set to "true" to activate OneSignal Push (set the OneSignal App ID in the build.gradle file)
    public static final boolean PUSH_ENABLED = false;

    //Set to "true" if you want to extend URL request by ?onesignal_push_id=XYZ (set the OneSignal App ID in the build.gradle file)
    public static final boolean PUSH_ENHANCE_WEBVIEW_URL = false;

    //Set to "true" if WebView should be reloaded when the app gets a UserID from OneSignal (set the OneSignal App ID in the build.gradle file)
    public static final boolean PUSH_RELOAD_ON_USERID = false;

    /**
     * Firebase Push Notification Options
     */
    //Set to "true" to activate Firebase Push (download the google-services.json file and replace the existing one via Mac Finder/Windows Explorer)
    public static final boolean FIREBASE_PUSH_ENABLED = false;

    public static final String firebasechanneltopic = "NONE"; //Topic name of Firebase channel

    //Set to "true" if you want to extend URL request by ?firebase_push_id=XYZ (set the OneSignal IDs in the build.gradle file)
    public static final boolean FIREBASE_PUSH_ENHANCE_WEBVIEW_URL = false;


    /**
     * Ad Options
     */

    //Set to "true" if you want to display AdSense web ads (independent of AdMob ads)
    public static final boolean SHOW_ADSENSE_AD = false;

    //Set to "true" if you want to display AdMob banner ads (set the AdMob IDs in the strings.xml file)
    public static final boolean SHOW_BANNER_AD = false;

    //Set to "true" if you want to display AdMob fullscreen interstitial ads after X website clicks (set the AdMob IDs in the strings.xml file)
    public static final boolean SHOW_FULL_SCREEN_AD = false;

    //X website clicks for AdMob interstitial ads (set the AdMob IDs in the strings.xml file)
    public static final int SHOW_AD_AFTER_X = 5;

    //Allow normal URL clicks to increment SHOW_AD_AFTER_X
    public static final boolean INCREMENT_WITH_REDIRECTS = true;

    //Set to "true" to use Facebook ads instead of AdMob ads (set the Ad IDs in the strings.xml file)
    public static boolean USE_FACEBOOK_ADS = false;

    //Add page trigger words here, presence in a URL triggers interstitial ads (e.g., "thanks.html", "welcome.html", "/thank-you/", "next-step.php")
    public static List<String> AD_TRIGGER_URLS = Arrays.asList("thanks.html");

    //Add the file formats that should trigger the file downloader functionality
    public static List<String> downloadableExtension =
            Collections.unmodifiableList(
                    Arrays.asList(".epub", ".pdf", ".pptx", ".docx", ".doc", ".xlsx", ".mp3", ".mp4", ".wav") //Add them here!
            );

    /**
     * Social Media Login Helper Tool
     *
     * Note: To be used if the login link fails to open in-app / doesn't work and other methods like
     * EXTERNAL_URL_HANDLING_OPTIONS, OPEN_SPECIAL_URLS_IN_NEW_TAB and the custom USER_AGENT
     * do not help.
     */

    //Define the URL prefixes that load during Google login for your website; acts as a trigger for the helper
    public static String[] GOOGLE_LOGIN_HELPER_TRIGGERS = {}; //Example: {"https://accounts.google.com", "https://accounts.youtube.com"}

    //Define the URL prefixes that load during Facebook login for your website; acts as a trigger for the helper
    public static String[] FACEBOOK_LOGIN_HELPER_TRIGGERS = {}; //Example: {"https://m.facebook.com", "https://www.facebook.com"}

    //Define the URL for logout to clear login cookies (optional)
    public static String HOME_URL_LOGOUT = "https://www.example.com/logout";

    /**
     * Manual Cookie Sync Tool
     *
     * Note: To be used if you require cookies to be synced UNDER every 30 seconds
     */

    //Set to "true" to enable the Manual Cookie Sync tool
    public static final boolean MANUAL_COOKIE_SYNC = false;

    // Define how often the cookies should sync with the app in milliseconds (ms)
    public static final int COOKIE_SYNC_TIME = 5000;

    // Define the URL prefixes you want to use the Manual Cookie Sync tool on
    // Note: If MANUAL_COOKIE_SYNC_TRIGGERS is empty, it will automatically check every page
    public static String[] MANUAL_COOKIE_SYNC_TRIGGERS = {}; //Example: {"https://example.com/login", "https://example.com/data"}

    /**
     * Android Permission Options
     */
    static boolean requireLocation = false; //Set to "false" if you do NOT require location services/GPS coordinates; don't forget to also remove relevant entries in AndroidManifest.xml if you want to ensure the complete removal of the permission capability for the app
    static boolean requireStorage = false; //Set to "false" if you do NOT require APIs related to downloads or uploads; don't forget to also remove relevant entries in AndroidManifest.xml if you want to ensure the complete removal of the permission capability for the app
    static boolean requireCamera = false; //Set to "false" if you do NOT require APIs related to camera images / camera videos; don't forget to also remove relevant entries in AndroidManifest.xml if you want to ensure the complete removal of the permission capability for the app
    static boolean requireRecordAudio = false; //Set to "false" if you do NOT require APIs related to recording audio; don't forget to also remove relevant entries in AndroidManifest.xml if you want to ensure the complete removal of the permission capability for the app

}
