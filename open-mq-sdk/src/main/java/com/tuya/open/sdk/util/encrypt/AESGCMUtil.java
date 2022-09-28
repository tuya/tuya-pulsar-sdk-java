package com.tuya.open.sdk.util.encrypt;

import org.apache.pulsar.shade.org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.security.Key;

public class AESGCMUtil extends AESBaseUtil{
    protected static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;
    private static final int GCM_NONCE_LENGTH = 12;

    public AESGCMUtil() {
        this.setAlgo(AES_GCM);
    }

    public AESGCMUtil(String ALGO, byte[] keyValue) {
        super(ALGO, keyValue);
        this.setAlgo(AES_GCM);
    }

    @Override
    String decryptData(String data) throws Exception {
        Key key = this.generateKey();
        Cipher cipher = Cipher.getInstance(this.algo);
        byte[] message = Base64.decodeBase64(data);
        if (message.length < GCM_NONCE_LENGTH + GCM_TAG_LENGTH) throw new IllegalArgumentException();
        GCMParameterSpec params = new GCMParameterSpec(GCM_TAG_LENGTH * 8, message, 0, GCM_NONCE_LENGTH);
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        byte[] decryptData = cipher.doFinal(message, GCM_NONCE_LENGTH, message.length - GCM_NONCE_LENGTH);
        return new String(decryptData);
    }

}
