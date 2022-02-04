package io.mway.zebra_datawedge;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.IntentFilter;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import java.util.ArrayList;

/** FlutterZebraDatawedgePlugin */
public class ZebraDatawedgePlugin implements FlutterPlugin {

  /**
   * EventChannel which will be used to provide Event to Flutter part
   */
  private EventChannel eventChannel;
  private EventChannel.StreamHandler eventStreamHandler;

  /**
   * Used to save BroadcastReceiver to be able unregister them.
   */
  private ArrayList<SinkBroadcastReceiver> registeredReceivers = new ArrayList();

  @Override
  public void onAttachedToEngine(final @NonNull FlutterPluginBinding flutterPluginBinding) {

    // Create an intent filter
    final IntentFilter filter = new IntentFilter();
    filter.addCategory(Intent.CATEGORY_DEFAULT);
    filter.addAction("io.mway.flutter_zebra_datawedge.ACTION"); // Please use this String in your DataWedge profile configuration


    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),"data_wedge_event");

    eventStreamHandler = new EventChannel.StreamHandler() {
      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        //Create an broadcast receiver
        SinkBroadcastReceiver receiver= new SinkBroadcastReceiver(events);
        // save them to unregister later
        registeredReceivers.add(receiver);

        flutterPluginBinding.getApplicationContext().registerReceiver(receiver, filter);
      }

      @Override
      public void onCancel(Object arguments) {

      }
    };

    eventChannel.setStreamHandler(eventStreamHandler);
  }

  /**
   * Unregister all saved broadcast receiver
   * @param flutterPluginBinding
   */
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding flutterPluginBinding){
    for(SinkBroadcastReceiver receiver: registeredReceivers){
      flutterPluginBinding.getApplicationContext().unregisterReceiver(receiver);
    }
  }

}

