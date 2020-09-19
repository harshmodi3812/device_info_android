
import 'dart:async';

import 'package:device_info_android/AndroidDeviceInfo.dart';
import 'package:flutter/services.dart';

class DeviceInfoAndroid {
  static const MethodChannel _channel =
      const MethodChannel('device_info_android');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<AndroidDeviceInfo> get androidSimDetails async {
    return AndroidDeviceInfo.fromMap((await _channel.invokeMethod('getandroidSimDetails')).cast<String, dynamic>());
  }
}
