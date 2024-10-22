package com.tuya.open.sdk.util.decrypt;

import java.util.Arrays;

public enum EncryptModelEnum {
    AES_ECB("aes_ecb", "1.0.0"),
    AES_GCM("aes_gcm", "2.0.0");



    private String encryptModel;
    private String encryptVersion;

    EncryptModelEnum(String encryptModel, String encryptVersion) {
        this.encryptModel = encryptModel;
        this.encryptVersion = encryptVersion;
    }

    public String getEncryptModel() {
        return encryptModel;
    }

    public String getEncryptVersion() {
        return encryptVersion;
    }



    public static EncryptModelEnum getByModel(String encryptModel) {
        return Arrays.stream(values()).filter(t -> t.getEncryptModel().equals(encryptModel)).findAny().orElse(null);
    }

}
