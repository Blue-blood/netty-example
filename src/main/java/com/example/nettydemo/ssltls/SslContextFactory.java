package com.example.nettydemo.ssltls;

import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/19 10:20
 */
public class SslContextFactory {
    private final static String PROTOCOL = "TLS";
    private static SSLContext SERVER_CONTEXT;
    private static SSLContext CLIENT_CONTEXT;

    public static SSLContext getServerContext(String pkPath,String caPath,String password) throws Exception {
        if (SERVER_CONTEXT!=null){
            return SERVER_CONTEXT;
        }
        SERVER_CONTEXT = getContext(pkPath,caPath,password);
        return SERVER_CONTEXT;
    }
    public static SSLContext getClientContext(String pkPath,String caPath,String password) throws Exception {
        if (CLIENT_CONTEXT!=null){
            return CLIENT_CONTEXT;
        }
        CLIENT_CONTEXT = getContext(pkPath,caPath,password);
        return CLIENT_CONTEXT;
    }

    private static SSLContext getContext(String pkPath, String caPath, String password) throws Exception {
        SSLContext context;
        InputStream in =null;
        InputStream tiN = null;
        try {
            KeyManagerFactory kmf = null;
            if (pkPath!=null){
                KeyStore ks =  KeyStore.getInstance("JKS");
                in = new ClassPathResource(pkPath).getInputStream();
                ks.load(in,password.toCharArray());
                kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(ks,password.toCharArray());
            }
            TrustManagerFactory tf = null;
            if (caPath!=null){
                KeyStore ks = KeyStore.getInstance("JKS");
                tiN = new ClassPathResource(caPath).getInputStream();
                ks.load(tiN,password.toCharArray());
                tf = TrustManagerFactory.getInstance("SunX509");
                tf.init(ks);
            }
            context = SSLContext.getInstance(PROTOCOL);
            context.init(kmf.getKeyManagers(),tf.getTrustManagers(),null);
        }catch (Exception e){
            throw new Exception("Failed to initialize Client-side SSLcontext");
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in =null;
            }

            if (tiN!=null){
                try {
                    tiN.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tiN = null;
            }
        }
        return context;
    }
}
