package com.mk.adx.serializer;


import com.mk.adx.interf.IProtobuf;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @Author fyy
 * @Description
 * @Date 2020/10/28 9:37
 */
public class ProtobufSerializer implements Serializer<IProtobuf> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, IProtobuf data) {
        return data.encode();
    }

    @Override
    public void close() {

    }
}
