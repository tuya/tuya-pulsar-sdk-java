package com.tuya.open.sdk.util.decrypt;

import org.apache.pulsar.shade.org.apache.commons.codec.binary.Base64;
import org.apache.pulsar.shade.org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * ECB decrypt
 *
 * @author chufeng
 * @date 2023/12/21 16:45
 */
public class AESECBDecryptor extends AESBaseDecryptor{

    protected static final String AES = "AES";

    @Override
    String decryptData(String data, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), AES);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedValue = Base64.decodeBase64(data);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = StringUtils.newStringUtf8(decValue);
        return decryptedValue;
    }

}