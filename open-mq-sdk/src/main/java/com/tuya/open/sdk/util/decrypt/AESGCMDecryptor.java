package com.tuya.open.sdk.util.decrypt;

import org.apache.pulsar.shade.org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AESGCMDecryptor extends AESBaseDecryptor{

    protected static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;
    private static final int GCM_NONCE_LENGTH = 12;

    @Override
    String decryptData(String data, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), AES_GCM);
        Cipher cipher = Cipher.getInstance(AES_GCM);
        byte[] message = Base64.decodeBase64(data);
        if (message.length < GCM_NONCE_LENGTH + GCM_TAG_LENGTH) {
            throw new IllegalArgumentException();
        }
        GCMParameterSpec params = new GCMParameterSpec(GCM_TAG_LENGTH * 8, message, 0, GCM_NONCE_LENGTH);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
        byte[] decryptData = cipher.doFinal(message, GCM_NONCE_LENGTH, message.length - GCM_NONCE_LENGTH);
        return new String(decryptData);
    }

}
