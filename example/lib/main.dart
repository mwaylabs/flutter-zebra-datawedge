import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter_zebra_datawedge/flutter_zebra_datawedge.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _data = "waiting...";
  String _labelType = "waiting...";
  String _source = "waiting...";

  @override
  void initState() {
    super.initState();
    initDataWedgeListener();
  }

// create a listener for data wedge package
  Future<void> initDataWedgeListener() async {
    FlutterZebraDataWedge.listenForDataWedgeEvent((response) {
      if (response != null && response is String)
        setState(() {
          Map<String, dynamic>? jsonResponse;
          try {
            jsonResponse = json.decode(response);
          } catch (e) {
            //TODO handling
          }
          if (jsonResponse != null) {
            _data = jsonResponse["decodedData"];
            _labelType = jsonResponse["decodedLabelType"];
            _source = jsonResponse["decodedSource"];
          } else {
            _source = "An error occured";
          }
        });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('DataWedgeIntent Example'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text("Label Type: $_labelType"),
              Text("Soruce: $_source"),
              Text("Data: $_data")
            ],
          ),
        ),
      ),
    );
  }
}
