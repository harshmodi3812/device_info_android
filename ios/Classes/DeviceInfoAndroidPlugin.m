#import "DeviceInfoAndroidPlugin.h"
#if __has_include(<device_info_android/device_info_android-Swift.h>)
#import <device_info_android/device_info_android-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "device_info_android-Swift.h"
#endif

@implementation DeviceInfoAndroidPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftDeviceInfoAndroidPlugin registerWithRegistrar:registrar];
}
@end
