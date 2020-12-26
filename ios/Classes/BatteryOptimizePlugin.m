#import "BatteryOptimizePlugin.h"
#if __has_include(<battery_optimize/battery_optimize-Swift.h>)
#import <battery_optimize/battery_optimize-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "battery_optimize-Swift.h"
#endif

@implementation BatteryOptimizePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftBatteryOptimizePlugin registerWithRegistrar:registrar];
}
@end
