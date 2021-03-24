# pulsar-client-java

[English](README.md) | [中文版](README_cn.md)
## Overview
The current SDK is to facilitate developers to connect to Tuya’s message center and access pulsar.
For more information, see Tuya [Message Queue](https://developer.tuya.com/en/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd).
## Preparation before use
* AccessID: Obtained from Tuya platform
* AccessKey: Obtained from Tuya platform
* Pulsar address: Choose Pulsar address according to different business areas. For more information about acquiring the address, see Tuya [Message Queue](https://developer.tuya.com/en/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd).

## Example
```java
public static void main(String[] args) throws Exception {
    String url = MqConfigs.CN_SERVER_URL;
    String accessId = "";
    String accessKey = "";

    MqConsumer mqConsumer = MqConsumer.build().serviceUrl(url).accessId(accessId).accessKey(accessKey)
            .maxRedeliverCount(3).messageListener(message -> {
                        System.out.println("------------------------------------------- --------");
                        System.out.println("Message received:" + new String(message.getData()) + ",seq="
                                + message.getSequenceId() + ",time=" + message.getPublishTime() + ",consumed time="
                                + System.currentTimeMillis());
                        String jsonMessage = new String(message.getData());
                        MessageVO vo = JSON.parseObject(jsonMessage, MessageVO.class);
                        System.out.println("the real message data:" + AESBase64Utils.decrypt(vo.getData(), accessKey.substring(8, 24)));
                    }

            );
    mqConsumer.start();
}
```

## Precautions
N/A.

## Technical Support

You can get Tuya developer technical support in the following ways:

* [Tuya Help Center](https://support.tuya.com/en/help)
* [Tuya technical ticket platform](https://iot.tuya.com/council)
