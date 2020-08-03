package com.fh.wxpay;

import java.io.InputStream;

public class MayWxPayy extends WXPayConfig {


    /***
     * 商家注册微信支付之后会生成一个应用ID
     *
     * @return
     **/
    @Override
    String getAppID() {
        return "wxa1e44e130a9a8eee";
    }
    /***
     *  商户ID
     * @return
     * */
    @Override
    String getMchID() {
        return "1507758211";
    }

    /*
       密钥
     */
    @Override
    String getKey() {
        return "feihujiaoyu12345678yuxiaoyang123";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        MayWXDomain qianfengWXDomain  = new MayWXDomain();
        return qianfengWXDomain;
    }
}
