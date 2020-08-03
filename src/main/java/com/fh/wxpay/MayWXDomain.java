package com.fh.wxpay;

public class MayWXDomain implements IWXPayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }
    /***
     * 返回微信的域名
     *  @param
     *  @config 配置
     *  @return */
    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        /*
          参数1: 微信制服域名
          参数2：是否主域名  true 或 false
         */
        DomainInfo domainInfo = new DomainInfo("api.mch.weixin.qq.com",true);
        return domainInfo;
    }
}
