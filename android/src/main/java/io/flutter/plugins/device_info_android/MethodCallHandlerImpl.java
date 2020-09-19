package io.flutter.plugins.device_info_android;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {

    private final Context context;

    public MethodCallHandlerImpl(Context contentResolver) {
        this.context = contentResolver;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("getandroidSimDetails")) {
            TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

            Integer phoneCount = null;
            Integer activeSubscriptionInfoCount = null;
            Integer activeSubscriptionInfoCountMax = null;
            String androidVersionName = Build.VERSION.RELEASE;

            String imei = null;

            JSONArray branchArray = new JSONArray();
            JSONObject object = new JSONObject();

            SubscriptionManager subscriptionManager = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                try {
                    subscriptionManager = (SubscriptionManager)context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

                    activeSubscriptionInfoCount = subscriptionManager != null ? subscriptionManager.getActiveSubscriptionInfoCount() : 0;
                    activeSubscriptionInfoCountMax = subscriptionManager != null ? subscriptionManager.getActiveSubscriptionInfoCountMax() : 0;
                    List<SubscriptionInfo> subscriptionInfos = subscriptionManager.getActiveSubscriptionInfoList();
                    for (SubscriptionInfo subscriptionInfo : subscriptionInfos) {
                        CharSequence carrierName = subscriptionInfo.getCarrierName();
                        String number = subscriptionInfo.getNumber();
                        int simSlotIndex = subscriptionInfo.getSimSlotIndex();

                        String androidID = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                            imei = null;
                        } else {
                            imei = manager.getImei();
                            androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);;
                        }

                        JSONObject simData = new JSONObject();

                        simData.put("carrierName", carrierName.toString());
                        simData.put("simSlotIndex", simSlotIndex);
                        simData.put("phoneNumber", number);
                        simData.put("androidVersionName",androidVersionName);
                        simData.put("IMEI", imei);
                        simData.put("androidID",androidID);

                        object = simData;
                        branchArray.put(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Map<String, Object> buildSimInfo = new HashMap<>();
            buildSimInfo.put("simCardDetails",branchArray.toString());
            result.success(buildSimInfo);
        } else {
            result.notImplemented();
        }
    }

    String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }
}
