# pulsar-client-java

[English](README.md) | [中文版](README_cn.md)
## 概述
当前sdk为了方便开发者连接涂鸦消息中心，接入pulsar。
更多关于[涂鸦消息队列](https://developer.tuya.com/cn/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd) 
## 使用前准备
* ACCESS_ID：由涂鸦平台提供
* ACCESS_KEY：由涂鸦平台提供
* pulsar地址：根据不同的业务区域选择 Pulsar 地址。可以从涂鸦[对接文档](https://developer.tuya.com/cn/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd) 中查询获取。

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

## 注意事项
暂无

## 技术支持

你可以通过以下方式获得Tua开发者技术支持：

- 涂鸦帮助中心: [https://support.tuya.com/zh/help](https://support.tuya.com/zh/help)
- 涂鸦技术工单平台: [https://iot.tuya.com/council](https://iot.tuya.com/council)
