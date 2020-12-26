package me.shokri.battery_optimize;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** BatteryOptimizePlugin */
public class BatteryOptimizePlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "battery_optimize");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("isIgnoringBatteryOptimizations")) {
      boolean reading = isIgnoringBatteryOptimizations();
      result.success(reading);
      return;
    }
    else if (call.method.equals("openBatteryOptimizationSettings")) {
      String reading = openBatteryOptimizationSettings();
      result.success(reading);
      return;
    }
    else if (call.method.equals("stopOptimizingBatteryUsage")) {
      String reading = stopOptimizingBatteryUsage();
      result.success(reading);
      return;
    }
    else {
      result.notImplemented();
    }
  }

  boolean isIgnoringBatteryOptimizations() {
    String packageName = mRegistrar.activeContext().getPackageName();
    mPowerManager = (PowerManager) (mRegistrar.activeContext().getSystemService(POWER_SERVICE));

    if(mPowerManager.isIgnoringBatteryOptimizations(packageName)) {
      return true;
    } else {
      return false;
    }
  }

  String openBatteryOptimizationSettings() {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
    mRegistrar.activeContext().startActivity(intent);
    return "Success";
  }

  String stopOptimizingBatteryUsage() {
    Intent intent = new Intent();
    String packageName = mRegistrar.activeContext().getPackageName();
    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
    intent.setData(Uri.parse("package:" + packageName));
    mRegistrar.activeContext().startActivity(intent);
    return "Success";
  }
}