package io.mway.flutter_zebra_datawedge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import io.flutter.plugin.common.EventChannel;

/**
 * Extended broadcast receiver to be able handled within an StreamHandler
 * https://stackoverflow.com/questions/59022897/how-to-recieve-a-stream-of-data-from-a-native-services-broadcastreciever-using
 */
class SinkBroadcastReceiver extends BroadcastReceiver {

    EventChannel.EventSink sink;

    SinkBroadcastReceiver(EventChannel.EventSink sink){
        this.sink = sink;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle b = intent.getExtras();
        //  This is useful for debugging to verify the format of received intents from DataWedge
        //for (String key : b.keySet())
        //{
        //    Log.v("DataWedgeIntent:", key);
        //}
        if (action.equals("io.mway.flutter_zebra_datawedge.ACTION")) {
            
            //Get data from Intent
            String decodedSource = intent.getStringExtra("com.symbol.datawedge.source");
            String decodedData = intent.getStringExtra("com.symbol.datawedge.data_string");
            String decodedLabelType = intent.getStringExtra("com.symbol.datawedge.label_type");
            
            // create a json object which will be returned to Flutter part
            JSONObject json = new JSONObject();
            try{
                json.put("decodedSource",decodedSource);
                json.put("decodedData",decodedData);
                json.put("decodedLabelType",decodedLabelType);
                sink.success(json.toString());
            }catch(Exception e){
                // catch json exceptions
                sink.success(e.toString());
            }


        }
    }
}
