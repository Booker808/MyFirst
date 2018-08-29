package cn.evun.coreframework.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Md5Util {

    private Md5Util(){
        throw new IllegalAccessError("Md5Util Class");
    }

    public static MessageDigest getDigest(final String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    public static String md5(String originStr){
        MessageDigest digest;
        try{
            digest = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e){
            log.warn("Digest error",e);
            throw new IllegalStateException("MD5 algorithm not available,Fatal(should be in the JDK");
        }
        try{
            byte[] bytes = digest.digest(originStr.getBytes("UTF-8"));
            return String.format("%032x",new BigInteger(1,bytes)).toLowerCase();
        }catch (UnsupportedEncodingException e){
            log.warn("Encoding error",e);
            throw new IllegalStateException("UTF-8 encoding not available. Fatal(should be in the JDK)");
        }
    }
}
