# pulsar-client-java

[English](README.md) | [中文版](README_cn.md)
## Overview
The current SDK is to facilitate developers to connect to Tuya’s message center and access pulsar.
For more information, see Tuya [Message Queue](https://developer.tuya.com/en/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd).
## Preparation before use
* ACCESS_ID: Obtained from Tuya platform
* ACCESS_KEY: Obtained from Tuya platform
* Pulsar address: Choose Pulsar address according to different business areas. For more information about acquiring the address, see Tuya [Message Queue](https://developer.tuya.com/en/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd).

## Example
```
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
            MessageVO messageVO = JSON.parseObject(payload, MessageVO.class);
            //decryption data
            String dataJsonStr = AESBase64Utils.decrypt(messageVO.getData(), ACCESS_KEY.substring(8, 24));
            System.out.println("messageVO=" + messageVO.toString() + "\n" + "data after decryption dataJsonStr=" + dataJsonStr);
        } catch (Exception e) {
            logger.error("payload=" + payload + "; your business processing exception, please check and handle. e=", e);
        }
    }
}
```

## Precautions
N/A.

## Technical Support

You can get Tua developer technical support in the following ways:

* [Tuya Help Center](https://support.tuya.com/en/help)
* [Tuya technical ticket platform](https://iot.tuya.com/council)
