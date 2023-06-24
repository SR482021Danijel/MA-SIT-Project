package com.ftn.ma_sit_project.commonUtils;

import android.util.Log;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.User;
import com.google.gson.Gson;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;


import java.nio.charset.StandardCharsets;
import java.util.Objects;

import java.util.UUID;


public class MqttHandler {

    public static Mqtt5BlockingClient client;
    private String str = "";
    Gson gson = new Gson();
    private String sentPayload;

    public void connect() {
        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("broker.hivemq.com")
                .buildBlocking();

        try {
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMatchmaking() {

        client.toAsync().subscribeWith()
                .topicFilter("test/Mobilne/Request")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(publish -> {
                    User user = gson.fromJson(StandardCharsets.UTF_8.decode(publish.getPayload().get()).toString(), User.class);
                    if (!Objects.equals(user.getUsername(), Data.loggedInUser.getUsername())) {
                        str = StandardCharsets.UTF_8.decode(publish.getPayload().get()).toString();
                    }
                    Log.i("mqtt", "payload = " + user.getUsername());
                })
                .send()
                .whenComplete(((mqtt3ConnAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Subscribe Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Subscribed");


                        User sentUser = new User();
//                        sentUser.setUsername(Data.loggedInUser.getUsername());
                        sentUser.setUsername("Pera");
                        sentPayload = gson.toJson(sentUser);
                        client.toAsync()
                                .publishWith()
                                .topic("test/Mobilne/Request")
                                .qos(MqttQos.AT_LEAST_ONCE)
                                .retain(true)
                                .payload(sentPayload.getBytes())
                                .send()
                                .whenComplete(((mqtt3ConnAck2, throwable2) -> {
                                    if (throwable2 != null) {
                                        Log.i("mqtt", "Publish Error");
                                        throwable2.printStackTrace();
                                    } else {
                                        Log.i("mqtt", "Published");
                                    }
                                }));
                    }
                }));
    }

    public void disconnect() {
        client.toAsync().disconnect().whenComplete(((mqtt3ConnAck, throwable) -> {
            if (throwable != null) {
                Log.i("mqtt", "Disconnect Failed");
            } else {
                Log.i("mqtt", "Disconnected");
            }
        }));
    }

    public void pointShareSubscribe() {

        client.toAsync().subscribeWith()
                .topicFilter("Mobilne/PointShare")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(mqtt5Publish -> {

                })
                .send()
                .whenComplete((mqtt5SubAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Point Subscribe Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Subscribed to point share");
                    }
                });
    }

    public void pointSharePublish(){

        client.toAsync().publishWith()
                .topic("Mobilne/PointShare")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload("".getBytes())
                .send()
                .whenComplete((mqtt5PublishResult, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Point Publish Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Published point share");
                    }
                });
    }

    public User getValue() {
        return gson.fromJson(str, User.class);
    }
}
