package com.fh.pay;

import com.fh.common.ServerResponse;
import com.fh.wxpay.MayWxPayy;
import com.fh.wxpay.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("payController")
public class PayController {

    @RequestMapping("pays")
    public ServerResponse pays(String orderId, BigDecimal price) {
        MayWxPayy MayWxPayy = new MayWxPayy();
        try {

                WXPay wxPay = new WXPay(MayWxPayy);

                Map<String, String> data = new HashMap<String, String>();
                data.put("body", "商品支付中心--请点击支付");
                data.put("out_trade_no", orderId);
                data.put("device_info", "WEB");
                data.put("fee_type", "CNY");
                data.put("total_fee", "1");
                data.put("spbill_create_ip", "123.12.12.123");
                data.put("notify_url", "http://www.example.com/wxpay/notify");
                data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
                data.put("product_id", "12");

                //设置失效时间
                SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
                String format = sim.format(DateUtils.addMinutes(new Date(), 2));
                data.put("time_expire", format);
                Map<String, String> res = wxPay.unifiedOrder(data);
                System.out.println(res);

                //判断是否通信成功
                if (!res.get("return_code").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.error("微信支付错误：" + res.get("return_msg"));
                }
                //判断业务是否正确
                if (!res.get("result_code").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.error("微信支付错误：" + res.get("err_code_des"));
                }

                String url = res.get("code_url");
                return ServerResponse.success(url);

        } catch (Exception e) {
            e.printStackTrace();
            return  ServerResponse.error();
        }
    }
    @RequestMapping("queryOrderStatus")
    private ServerResponse queryOrderStatus(String orderId){
        try {
        MayWxPayy config = new MayWxPayy();

        WXPay wxpay = new WXPay(config);
            int  count = 0;
            for(;;) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("out_trade_no", orderId);

                Map<String, String> res = wxpay.orderQuery(data);
                System.out.println(res);
                //判断是否通信成功
                if (!res.get("return_code").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.error("微信支付错误：" + res.get("return_msg"));
                }
                //判断业务是否正确
                if (!res.get("result_code").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.error("微信支付错误：" + res.get("err_code_des"));
                }
                //交易状态
                if (res.get("trade_state").equalsIgnoreCase("SUCCESS")) {
                    return ServerResponse.success();
                }
                count++;
                //每隔3秒调用一次
                Thread.sleep(2000);
                if(count>5){
                    return ServerResponse.error("支付超时");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.success();
    }
}
