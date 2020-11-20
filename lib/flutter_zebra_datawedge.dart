import 'dart:async';

import 'package:flutter/services.dart';

class FlutterZebraDataWedge {
  static const EventChannel _eventChannel =
      const EventChannel('data_wedge_event');

  static Stream<dynamic> _dataWedgeStream =
      _eventChannel.receiveBroadcastStream('getDataWedgeStream');

  /// Listen for DataWedge events
  /// with the name "io.mway.data_wedge_intent.ACTION"
  ///
  /// Be sure that your DataWedge Profile is setup correctly (see README.md)
  ///
  /// Response:
  /// ```json
  /// {
  /// decodedSource:String,
  /// decodedData:String,
  /// decodedLabelType:String
  /// }```
  ///
  /// Exception can appear and will bereturned as String in an event
  /// TODO(rework as this is bad solution)
  static StreamSubscription listenForDataWedgeEvent(
      void Function(dynamic event) listener) {
    return _dataWedgeStream.listen(listener);
  }
}
