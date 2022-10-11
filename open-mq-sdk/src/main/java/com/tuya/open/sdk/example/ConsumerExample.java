
package com.tuya.open.sdk.example;

import com.tuya.open.sdk.mq.MqConfigs;
import com.tuya.open.sdk.mq.MqConsumer;
import com.tuya.open.sdk.util.encrypt.AESBaseUtil;
import org.apache.pulsar.common.util.ObjectMapperFactory;
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
                    System.out.println("---------------------------------------------------");
                    System.out.println("Message received:" + new String(message.getData()) + ",time="
                            + message.getPublishTime() + ",consumed time=" + System.currentTimeMillis());
                    String payload = new String(message.getData());
                    payloadHandler(payload);
                });
        mqConsumer.start();
    }

    /**
     * This method is used to process your message business
     */
    private static void payloadHandler(String payload) {
        try {
            MessageVO messageVO = ObjectMapperFactory.getThreadLocal().readValue(payload, MessageVO.class);
            //decryption data
            String dataJsonStr = AESBaseUtil.decrypt(messageVO.getData(), ACCESS_KEY.substring(8, 24), messageVO.getEncryptVersion());
            System.out.println("messageVO=" + messageVO.toString() + "\n" + "data after decryption dataJsonStr=" + dataJsonStr);
        } catch (Exception e) {
            logger.error("payload=" + payload + "; your business processing exception, please check and handle. e=", e);
        }
    }
}
