package iloveyesterday.mobile.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import iloveyesterday.mobile.common.AlipayConfig;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.OrderMapper;
import iloveyesterday.mobile.dao.PayInfoMapper;
import iloveyesterday.mobile.pojo.Order;
import iloveyesterday.mobile.pojo.PayInfo;
import iloveyesterday.mobile.service.IPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("payService")
public class PayServiceImpl implements IPayService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PayInfoMapper payInfoMapper;


    @Override
    public ResponseData createForm(Long userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ResponseData.error("订单不存在");
        }
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNo().toString();
        // 订单名称，必填
        String subject = "订单" + order.getOrderNo();
        // 付款金额，必填
        String total_amount = order.getPayment().toString();
        // 商品描述，可空
        String body = "";
        // 超时时间 可空
        String timeout_express = "2m";
        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL,
                AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
                AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);

        // form表单生产
        String form;
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            return ResponseData.success(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData save(Long userId, Long orderNo, String alipayOrderNo) {
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setPayPlatform(Const.PaymentPlatform.ALIPAY);
        payInfo.setPlatformNumber(alipayOrderNo);
        payInfo.setUserId(userId);
        int resultCount = payInfoMapper.insert(payInfo);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }
}
