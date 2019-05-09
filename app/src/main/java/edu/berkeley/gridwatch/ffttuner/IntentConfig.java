package edu.berkeley.gridwatch.ffttuner;

/**
 * Created by nklugman on 6/4/15.
 */
public class IntentConfig {

    //CONSTANTS FOR INTENT PASSING / GETTING FROM GRIDWATCH EVENT
    public final static String RESULT_KEY = "RESULT";
    public final static String RESULT_PASSED = "PASSED";
    public final static String RESULT_FAILED = "FAILED";
    public final static String MESSAGE_KEY = "MSG";
    public final static String RECEIVER_KEY = "RECEIVER";
    public final static int ACCELEROMETER = 1001;
    public final static int MICROPHONE = 1002;
    public final static int GPS = 1003;
    public final static int FFT = 1004;
    public final static int ASK_DIALOG = 1005;
    public final static int CELL_INFO = 1006;
    public final static int SSIDs = 1007;
    public final static int FUSED = 1008;
    public final static int TEMP = 1009;
    public final static int AMBIENT = 1010;
    public final static int WIFI_CHANGE = 1011;
    public static final int LIGHT = 1012;
    public static final int MAG = 1013;
    public static final int DUPLICATE = 1014;



    public final static String ACCEL_X = "ACCEL_X";
    public final static String ACCEL_Y = "ACCEL_Y";
    public final static String ACCEL_Z = "ACCEL_Z";
    public final static String ACCEL_MAG = "ACCEL_MAG";


    //ADDITIONAL SENSOR SPECIFIC INTENTS
    public final static String FFT_ARRAY = "FFT_ARRAY";

    public final static String ACCEL_SAMPLES = "ACCEL_SAMPLES";

    public final static String INTENT_EXTRA_CELLPHONE_ID = "cell_id";

    public final static String NOTES_DIALOG_TYPE = "notes_dialog_type";
    public final static String INTENT_NOTES = "notes_val";
    public final static String RESTORATION_TAG = "restoration";
    public final static String OUTAGE_TAG = "outage";

    public final static String FUSED_LAT = "FUSED_LAT";
    public final static String FUSED_LNG = "FUSED_LNG";
    public final static String FUSED_TIME = "FUSED_TIME";
    public final static String FUSED_SPEED = "FUSED_SPEED";
    public final static String FUSED_ACCURACY = "FUSED_ACCURACY";
    public final static String FUSED_PROVIDER = "FUSED_PROVIDER";
    public final static String FUSED_NANOS = "FUSED_NANOS";

    public final static String GW_EVENT_TYPE = "GW_EVENT_TYPE";
    public final static String GPS_PREF_MANUAL = "GPS_PREF_MANUAL";
    public final static String GPS_PREF_AUTO = "GPS_PREF_AUTO";

    public final static String FUSED_ID = "fused_id";

    //CONSTANTS FOR INTENTS THAT GENERATE A NEW EVENT
    public final static String INTENT_NAME = "GridWatch-update-event";
    public final static String INTENT_EXTRA_EVENT_TYPE = "event_type";
    public final static String INTENT_EXTRA_EVENT_INFO = "event_info";
    public final static String INTENT_EXTRA_EVENT_TIME = "event_time";
    public final static String INTENT_EXTRA_EVENT_MANUAL_ON = "event_manual_on";
    public final static String INTENT_EXTRA_EVENT_MANUAL_OFF = "event_manual_off";
    public final static String INTENT_EXTRA_EVENT_MANUAL_WD = "event_manual_wd";
    public final static String INTENT_EXTRA_EVENT_MANUAL_INSTALL = "event_manual_install";
    public final static String INTENT_WIFI_CHANGE = "wifi_change";

    public final static String INTENT_MANUAL_KEY = "manual_state";

    public static final String INTENT_GCM_ALERT = "GCM_ALERT";
    public static final String INTENT_GCM_MSG = "GCM_MSG";
    public static final String INTENT_GCM_TITLE = "GCM_TITLE";


    public final static String INTENT_EXTRA_EVENT_GCM_ALL = "GCM_ALL";
    public final static String INTENT_EXTRA_EVENT_GCM_FFT = "GCM_FFT";
    public final static String INTENT_EXTRA_EVENT_GCM_GPS = "GCM_GPS";
    public final static String INTENT_EXTRA_EVENT_GCM_ACCEL = "GCM_ACCEL";
    public final static String INTENT_EXTRA_EVENT_GCM_MIC = "GCM_MIC";
    public final static String INTENT_EXTRA_EVENT_GCM_TYPE = "event_gcm_type";
    public final static String INTENT_EXTRA_EVENT_GCM_ASK = "GCM_ASK";
    public final static String INTENT_EXTRA_EVENT_GCM_WD = "GCM_WD";
    public final static String INTENT_EXTRA_EVENT_GCM_ASK_RESULT = "gcm_ask_result";
    public final static String INTENT_EXTRA_EVENT_GCM_ASK_INDEX = "gcm_ask_index";
    public final static String INTENT_EXTRA_EVENT_GCM_MAP = "GCM_MAP";
    public final static String INTENT_EXTRA_EVENT_GCM_MAP_UPDATE = "GCM_MAP";
    public final static String INTENT_EXTRA_EVENT_GCM_MAP_POINTS = "GCM_POINTS";
    public final static String INTENT_EXTRA_EVENT_GCM_MAP_POINT_KEY = "GCM_POINTS_KEY";

    public final static String INTENT_CONNECT_CHANGE = "NETWORK_CHANGE";
    public final static String INTENT_ONLINE = "ONLINE";
    public final static String INTENT_OFFLINE = "OFFLINE";

    public final static String INTENT_REPORT_CHATROOM = "REPORT_CHATROOM";

    //TO BE USED FOR ALL UI BEING AT THE HOME ACTIVITY
    public final static String INTENT_TO_HOME = "home_passback";

    public final static String INTENT_SHARE = "share";
    public final static String INTENT_EXTRA_BMP_FILEPATH = "bmp_filepath";

    public static final String INTENT_TOKEN_UPDATE = "token_update";


    //FOR DATA DELETION
    public final static String INTENT_DELETE_KEY = "delete_key";
    public final static String INTENT_DELETE = "delete";
    public final static String INTENT_DELETE_MSG = "delete_msg";

    //LOGIN RELATED INTENTS
    public final static String INTENT_PRELOGIN_SENDER = "prelogin_sender";
    public final static String INTENT_PRELOGIN_TYPE_FIRST = "first";
    public final static String INTENT_PRELOGIN_TYPE_CHAT = "chat";
    public final static String INTENT_PRELOGIN_SKIP = "skip";


    public final static String INTENT_EXTRA_EVENT_SUGGESTION = "suggestion";


    public final static int PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 1;
    public final static int PERMISSIONS_REQUEST_ACCESS_INTERNET = 2;
    public final static int PERMISSIONS_REQUEST_ACCESS_WRITE_EXTERNAL_STORAGE = 3;
    public final static int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 4;
    public final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5;
    public final static int PERMISSIONS_REQUEST_ACCESS_WIFI_STATE = 6;
    public final static int PERMISSIONS_REQUEST_ACCESS_WAKE_LOCK = 7;
    public final static int PERMISSIONS_REQUEST_ACCESS_GET_ACCOUNTS = 8;
    public final static int PERMISSIONS_REQUEST_ACCESS_RECEIVE_BOOT_COMPLETED = 9;
    public final static int PERMISSIONS_REQUEST_ACCESS_RECORD_AUDIO = 10;
    public final static int PERMISSIONS_REQUEST_ACCESS_GET_TASKS = 11;
    public final static int PERMISSIONS_REQUEST_ACCESS_SYSTEM_ALERT_WINDOW = 12;
    public final static int PERMISSIONS_REQUEST_ACCESS_VIBRATE = 13;
    public final static int PERMISSIONS_REQUEST_ACCESS_READ_PHONE_STATE = 14;
    public final static int PERMISSIONS_REQUEST_ACCESS_LOC = 15;


    public static final String LIGHT_SAMPLES = "LIGHT_SAMPLES";
    public static final String MAG_SAMPLES = "MAG_SAMPLES";
    public final static String MAG_X = "MAG_X";
    public final static String MAG_Y = "MAG_Y";
    public final static String MAG_Z = "MAG_Z";
    public final static String MAG_MAG = "MAG_MAG";
    public static final String INTENT_CONSENT = "INTENT_CONSENT";

    public static final String DUPLICATE_LOC_LAT = "DUPLICATE_LOC_LAT";
    public static final String DUPLICATE_LOC_LNG = "DUPLICATE_LOC_LNG";
    public static final String DUPLICATE_MESSAGE_KEY = "DUPLICATE_MESSAGE_KEY";
    public static final String DUPLICATE_RESULT_KEY = "DUPLICATE_RESULT_KEY";
    public static final String DUPLICATE_LOC_SPEED = "DUPLICATE_LOC_SPEED";
    public static final String DUPLICATE_LOC_TIME = "DUPLICATE_LOC_TIME";
    public static final String DUPLICATE_LOC_ACCURACY = "DUPLICATE_LOC_ACCURACY";
    public static final String DUPLICATE_LOC_NANOS = "DUPLICATE_LOC_NANOS";
    public static final String DUPLICATE_LOC_PROVIDER = "DUPLICATE_LOC_PROVIDER";
    public static final String INTENT_EXTRA_EVENT_TEST = "INTENT_EXTRA_EVENT_TEST";
    public static final String START = "START";
    public static final String STOP = "STOP";
    public static final String DONE = "DONE";


    /*
    public final static String FUSED_LAT = "FUSED_LAT";
    public final static String FUSED_LNG = "FUSED_LNG";
    public final static String FUSED_TIME = "FUSED_TIME";
    public final static String FUSED_SPEED = "FUSED_SPEED";
    public final static String FUSED_ACCURACY = "FUSED_ACCURACY";
    public final static String FUSED_PROVIDER = "FUSED_PROVIDER";
    public final static String FUSED_NANOS = "FUSED_NANOS";
    */

}
