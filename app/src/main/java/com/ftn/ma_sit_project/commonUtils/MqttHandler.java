package com.ftn.ma_sit_project.commonUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.TextView;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.Hyphens;
import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.Model.UserDTO;
import com.google.gson.Gson;

import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import java.util.UUID;


public class MqttHandler {

    public static Mqtt5BlockingClient client;
    private String str = "";

    private User user = new User("1", "Pera", "pera123", "pera@gmail.com", 0, 0, 0, 0, 0, 0, 0, 0, 0);
    Gson gson = new Gson();
    private String sentPayload;
    private static int points;
    private static boolean isMyTurn = false;

    public TextView textView;
    public Hyphens hyphens;
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
//                    Log.i("mqtt", "payload = " + user.getUsername());
                })
                .send()
                .whenComplete(((mqtt3ConnAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Subscribe Error");
                        throwable.printStackTrace();
                    } else {
//                        Log.i("mqtt", "Subscribed");


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
//                                        Log.i("mqtt", "Published");
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

    public void decideTurnPlayer() {

        double rnd = Math.random();

        client.toAsync().subscribeWith()
                .topicFilter("Mobilne/Turn")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(mqtt5Publish -> {
                    UserDTO user = gson.fromJson(StandardCharsets.UTF_8.decode(mqtt5Publish.getPayload().get()).toString(), UserDTO.class);
                    if (!Objects.equals(user.getUsername(), Data.loggedInUser.getUsername())) {
                        Log.i("mqtt", "opponent:" + user.getTurnNumber());
                        isMyTurn = rnd > user.getTurnNumber();
                        Log.i("mqtt", "turn: " + isMyTurn);
                    } else {
                        Log.i("mqtt", "me: " + user.getTurnNumber());
                    }

                })
                .send()
                .whenComplete((mqtt5SubAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Turn Subscribe Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Subscribed to turn topic");

//                        UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), 0, rnd);
                        UserDTO userDTO = new UserDTO("Pera", 0, rnd + 1);
                        String sent = gson.toJson(userDTO);
                        client.toAsync().publishWith()
                                .topic("Mobilne/Turn")
                                .qos(MqttQos.AT_LEAST_ONCE)
                                .payload(sent.getBytes())
                                .retain(true)
                                .send()
                                .whenComplete((mqtt5PublishResult, throwable1) -> {
                                    if (throwable1 != null) {
                                        Log.i("mqtt", "Turn Publish Error");
                                        throwable1.printStackTrace();
                                    } else {
                                        Log.i("mqtt", "Published to turn topic");
                                    }
                                });
                    }
                });
    }

    public void pointShareSubscribe() {

        client.toAsync().subscribeWith()
                .topicFilter("Mobilne/PointShare")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(mqtt5Publish -> {
                    UserDTO user = gson.fromJson(StandardCharsets.UTF_8.decode(mqtt5Publish.getPayload().get()).toString(), UserDTO.class);
                    if (!Objects.equals(user.getUsername(), Data.loggedInUser.getUsername())) {
                        points = user.getPoints();
                    }
                })
                .send()
                .whenComplete((mqtt5SubAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Point Subscribe Error");
                        throwable.printStackTrace();
                    } else {
//                        Log.i("mqtt", "Subscribed to point share");
                    }
                });
    }

    public void pointSharePublish(int points) {

        UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), points, 0.0);
        sentPayload = gson.toJson(userDTO);

        client.toAsync().publishWith()
                .topic("Mobilne/PointShare")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(sentPayload.getBytes())
                .send()
                .whenComplete((mqtt5PublishResult, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Point Publish Error");
                        throwable.printStackTrace();
                    } else {
//                        Log.i("mqtt", "Published point share");
                    }
                });
    }

    public void textViewShareSubscribe(TextViewStoreCallback textViewStoreCallback) {

        client.toAsync().subscribeWith()
                .topicFilter("Mobilne/TextViewShare")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(mqtt5Publish -> {
                    hyphens = gson.fromJson(StandardCharsets.UTF_8.decode(mqtt5Publish.getPayload().get()).toString(), Hyphens.class);
                    textViewStoreCallback.onCallBack(hyphens);
                })
                .send()
                .whenComplete((mqtt5SubAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "TextView Subscribe Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Subscribed to TextView share");
                        Log.i("mqtt", hyphens+"");
                    }
                });
    }

    public void textViewSharePublish(TextView hyphens) {

        ColorDrawable viewColor = (ColorDrawable) hyphens.getBackground();
        Hyphens hyphens1 = new Hyphens(hyphens.getId(), hyphens.getText().toString(), viewColor.getColor());
        sentPayload = gson.toJson(hyphens1);
        client.toAsync().publishWith()
                .topic("Mobilne/TextViewShare")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(sentPayload.getBytes())
                .send()
                .whenComplete((mqtt5PublishResult, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "TextView Publish Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Published TextView share");
                        Log.i("mqtt", sentPayload+"");
                    }
                });
    }

    public User getP2Username() {
        return gson.fromJson(str, User.class);
    }

    public int getP2Points() {
        return points;
    }

    public boolean getTurnPlayer() {
        return isMyTurn;
    }

    public interface TurnPlayerCallback {
        public void onCallback(boolean isMyTurn);
    }

    public boolean getP2Boolean() {
        return isMyTurn;
    }

    public Hyphens getP2Hyphens(){
//        Hyphens hyphens1 = new Hyphens(2131230830,"a", Color.RED);
        Log.i("mqtt", hyphens+"to je to");
        return hyphens;
    }

    public interface TextViewStoreCallback{
        void onCallBack(Hyphens hyphens);
    }
}
