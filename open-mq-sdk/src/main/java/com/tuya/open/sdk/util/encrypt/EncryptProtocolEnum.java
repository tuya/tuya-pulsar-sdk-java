package com.tuya.open.sdk.util.encrypt;

import com.tuya.open.sdk.PulsarSdkVersion;
import org.apache.pulsar.shade.org.eclipse.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;

public enum EncryptProtocolEnum {
    AES_GCM("aes_gcm",  PulsarSdkVersion.getVersion(), "v2", AESGCMUtil.class);

    private String key;
    private String sdkVersion;
    private String encryptVersion;
    private Class clazz;

    EncryptProtocolEnum(String key, String sdkVersion, String encryptVersion, Class clazz) {
        this.key = key;
        this.sdkVersion = sdkVersion;
        this.clazz = clazz;
        this.encryptVersion = encryptVersion;
    }

    public String getKey() {
        return this.key;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public static AESBaseUtil getEncryptUtil(String encryptVersion) {
        try {
            EncryptProtocolEnum finalEnum = AES_GCM;
            if (StringUtil.isNotBlank(encryptVersion)) {
                for (EncryptProtocolEnum encryptProtocolEnum : EncryptProtocolEnum.values()) {
                    if (encryptProtocolEnum.encryptVersion.equals(encryptVersion)) {
                        finalEnum = encryptProtocolEnum;
                    }
                }
            }
            return (AESBaseUtil) finalEnum.clazz.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            log.error(LOGGER_PREFIX + "throw err: {}\n{}", e.getMessage(), e.toString());
        }
        return null;
    }

    public static AESBaseUtil getEncryptUtil() {
        return getEncryptUtil(AES_GCM.encryptVersion);
    }

    private static final Logger log = LoggerFactory.getLogger(EncryptProtocolEnum.class);
    private static final String LOGGER_PREFIX = "aes_base_util:";
}
