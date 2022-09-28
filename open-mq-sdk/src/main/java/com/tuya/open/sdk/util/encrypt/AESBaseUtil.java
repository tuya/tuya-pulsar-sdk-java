package com.tuya.open.sdk.util.encrypt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Objects;

public abstract class AESBaseUtil {
    private static final Logger log = LoggerFactory.getLogger(EncryptProtocolEnum.class);
    protected static final String AES_STR = "AES";

    protected String algo;
    protected byte[] keyValue;

    abstract String decryptData(String data) throws Exception;

    public static String decrypt(String data, String secretKey) throws Exception {
        AESBaseUtil aes = EncryptProtocolEnum.getEncryptUtil();
        if (Objects.isNull(aes)) throw new Exception("Not found encrypt util");
        aes.setKeyValue(secretKey.getBytes());
        return aes.decryptData(data);
    }

    protected Key generateKey(){
        return new SecretKeySpec(this.keyValue, this.algo);
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public byte[] getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(byte[] keyValue) {
        this.keyValue = keyValue;
    }

    public AESBaseUtil() {
    }

    public AESBaseUtil(String algo, byte[] keyValue) {
        this.algo = algo;
        this.keyValue = keyValue;
    }

}
