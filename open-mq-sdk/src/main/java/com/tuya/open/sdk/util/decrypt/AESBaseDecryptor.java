package com.tuya.open.sdk.util.decrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AESBaseDecryptor {

    protected static final Map<EncryptModelEnum, AESBaseDecryptor> modelMap;
    protected static AESBaseDecryptor defaultDecryptor;
    static {
        modelMap = new HashMap<>();
        modelMap.put(EncryptModelEnum.AES_ECB, new AESECBDecryptor());
        modelMap.put(EncryptModelEnum.AES_GCM, new AESGCMDecryptor());

        defaultDecryptor = new AESECBDecryptor();
    }

    abstract String decryptData(String data, String key) throws Exception;

    public static String decrypt(String data, String secretKey, String encryptModel) throws Exception {
        EncryptModelEnum modelEnum = EncryptModelEnum.getByModel(encryptModel);
        return Optional.ofNullable(modelMap.get(modelEnum)).orElse(defaultDecryptor).decryptData(data, secretKey);
    }

    public static String decrypt(String data, String secretKey) throws Exception {
        return defaultDecryptor.decryptData(data, secretKey);
    }


}
