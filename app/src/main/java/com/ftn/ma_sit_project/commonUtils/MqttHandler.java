package com.ftn.ma_sit_project.commonUtils;

import android.util.Log;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class MqttHandler {

    private Mqtt5BlockingClient client;
    private String str;

    public void startMatchmaking() {
        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("broker.hivemq.com")
                .buildBlocking();

        try {
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        client.toAsync().subscribeWith()
                .topicFilter("test/Mobilne/Request")
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(publish -> {
                    str = StandardCharsets.UTF_8.decode(publish.getPayload().get()).toString();
                    Log.i("mqtt", "payload = " + str);
                })
                .send()
                .whenComplete(((mqtt3ConnAck, throwable) -> {
                    if (throwable != null) {
                        Log.i("mqtt", "Subscribe Error");
                        throwable.printStackTrace();
                    } else {
                        Log.i("mqtt", "Subscribed");
                        client.toAsync()
                                .publishWith()
                                .topic("test/Mobilne/Request")
                                .qos(MqttQos.AT_LEAST_ONCE)
                                .retain(true)
                                .payload("1".getBytes())
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
}
