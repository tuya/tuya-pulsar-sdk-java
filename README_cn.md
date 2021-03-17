# pulsar-client-java

[English](README.md) | [中文版](README_cn.md)
## 概述
当前sdk为了方便开发者连接涂鸦消息中心，接入pulsar。
更多关于[涂鸦消息队列](https://developer.tuya.com/cn/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd) 
## 使用前准备
* AccessID：由涂鸦平台提供
* AccessKey：由涂鸦平台提供
* pulsar地址：根据不同的业务区域选择 Pulsar 地址。可以从涂鸦[对接文档](https://developer.tuya.com/cn/docs/iot/open-api/message-service/message-service?id=K95zu0nzdw9cd) 中查询获取。

## Example
```
 public static void main(String[] args) throws Exception {
        String url = MqConfigs.CN_SERVER_URL;
        String accessId = "";
        String accessKey = "";

        MqConsumer mqConsumer = MqConsumer.build().serviceUrl(url).accessId(accessId).accessKey(accessKey)
                .maxRedeliverCount(3).messageListener(message -> {
                            System.out.println("---------------------------------------------------");
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

## 注意事项
暂无

## 技术支持

你可以通过以下方式获得Tua开发者技术支持：

- 涂鸦帮助中心: [https://support.tuya.com/zh/help](https://support.tuya.com/zh/help)
- 涂鸦技术工单平台: [https://iot.tuya.com/council](https://iot.tuya.com/council)
