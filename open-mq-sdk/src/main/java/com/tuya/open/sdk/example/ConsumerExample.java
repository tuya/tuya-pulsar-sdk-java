
package com.tuya.open.sdk.example;

import com.alibaba.fastjson.JSON;
import com.tuya.open.sdk.mq.AESBase64Utils;
import com.tuya.open.sdk.mq.MqConfigs;
import com.tuya.open.sdk.mq.MqConsumer;
import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerExample {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerExample.class);

    private static String URL = MqConfigs.CN_SERVER_URL;
    private static String ACCESS_ID = "";
    private static String ACCESS_KEY = "";

    public static void main(String[] args) throws Exception {
        MqConsumer mqConsumer = MqConsumer.build().serviceUrl(URL).accessId(ACCESS_ID).accessKey(ACCESS_KEY)
                .messageListener(message -> {
                    MessageId msgId = message.getMessageId();
                    String tid = message.getProperty("tid");
                    long publishTime = message.getPublishTime();
                    String payload = new String(message.getData());
                    logger.debug("###TUYA_PULSAR_MSG => start process message, messageId={}, publishTime={}, tid={}, payload={}",
                        msgId, publishTime, tid, payload);
                    payloadHandler(payload);
                    logger.debug("###TUYA_PULSAR_MSG => finish process message, messageId={}, publishTime={}, tid={}",
                        msgId, publishTime, tid);
                });
        mqConsumer.start();
    }

    /**
     * This method is used to process your message business
     */
    private static void payloadHandler(String payload) {
        try {
            MessageVO messageVO = JSON.parseObject(payload, MessageVO.class);
            //decryption data
            String dataJsonStr = AESBase64Utils.decrypt(messageVO.getData(), ACCESS_KEY.substring(8, 24));
            System.out.println("messageVO=" + messageVO.toString() + "\n" + "data after decryption dataJsonStr=" + dataJsonStr);
        } catch (Exception e) {
            logger.error("payload=" + payload + "; your business processing exception, please check and handle. e=", e);
        }
    }
}
