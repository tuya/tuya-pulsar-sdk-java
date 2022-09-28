package com.tuya.open.sdk.util.encrypt;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;

public enum EncryptProtocolEnum {
    AES_GCM("gcm", "2.1.0", AESGCMUtil.class);

    private String key;
    private String sdkVersion;
    private Class clazz;

    EncryptProtocolEnum(String key, String sdkVersion, Class clazz) {
        this.key = key;
        this.sdkVersion = sdkVersion;
        this.clazz = clazz;
    }

    public String getKey() {
        return this.key;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public static AESBaseUtil getEncryptUtil() {
        try {
            return (AESBaseUtil) AES_GCM.clazz.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            log.error(LOGGER_PREFIX + "throw err: {}\n{}", e.getMessage(), JSONObject.toJSONString(e));
        }
        return null;
    }

    private static final Logger log = LoggerFactory.getLogger(EncryptProtocolEnum.class);
    private static final String LOGGER_PREFIX = "aes_base_util:";
}
