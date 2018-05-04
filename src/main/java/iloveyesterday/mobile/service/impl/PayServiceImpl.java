package iloveyesterday.mobile.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import iloveyesterday.mobile.common.AlipayConfig;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.OrderMapper;
import iloveyesterday.mobile.dao.PayInfoMapper;
import iloveyesterday.mobile.pojo.Order;
import iloveyesterday.mobile.pojo.PayInfo;
import iloveyesterday.mobile.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("payService")
@Slf4j
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
        if (order.getStatus() == Const.OrderStatus.PAYED) {
            return ResponseData.error("已付款");
        }
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNo().toString();
        // 订单名称，必填
        String subject = "手机商城";
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
            log.error("alipay->createForm", e);
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData save(Long userId, Long orderNo, String alipayOrderNo) {
        PayInfo payInfo = payInfoMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (payInfo != null) {
            return ResponseData.success();
        }
        payInfo = new PayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setPayPlatform(Const.PaymentPlatform.ALIPAY);
        payInfo.setPlatformNumber(alipayOrderNo);
        if (userId == null) {
            userId = orderMapper.selectUserIdByOrderNo(orderNo);
        }
        payInfo.setUserId(userId);
        int resultCount = payInfoMapper.insert(payInfo);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData alipayNotify(String orderNo, String alipayOrderNo, String alipayStatus) {
        int resultCount;
        Order order = orderMapper.selectByOrderNo(Long.valueOf(orderNo));
        if (order.getStatus() == Const.OrderStatus.NOT_PAY) {
            Order orderForUpdate = new Order();
            orderForUpdate.setId(order.getId());
            orderForUpdate.setStatus(Const.OrderStatus.PAYED);
            orderForUpdate.setPaymentTime(new Date());
            orderForUpdate.setUpdateTime(new Date());
            resultCount = orderMapper.updateByPrimaryKeySelective(orderForUpdate);
            if (resultCount <= 0) {
                return ResponseData.error("修改订单状态失败");
            }
        }
        PayInfo payInfo = payInfoMapper.selectByAlipayOrderNo(alipayOrderNo);
        if (payInfo == null) {
            payInfo = new PayInfo();
            payInfo.setOrderNo(Long.valueOf(orderNo));
            payInfo.setPayPlatform(Const.PaymentPlatform.ALIPAY);
            payInfo.setPlatformNumber(alipayOrderNo);
            Long userId = orderMapper.selectUserIdByOrderNo(Long.valueOf(orderNo));
            payInfo.setUserId(userId);
            resultCount = payInfoMapper.insert(payInfo);
            if (resultCount <= 0) {
                return ResponseData.error("insert");
            }
        } else {
            PayInfo payInfoForUpdate = new PayInfo();
            payInfoForUpdate.setId(payInfo.getId());
            payInfoForUpdate.setPlatformStatus(alipayStatus);
            payInfoForUpdate.setUpdateTime(new Date());
            resultCount = payInfoMapper.updateByPrimaryKeySelective(payInfoForUpdate);
            if (resultCount <= 0) {
                return ResponseData.error("update");
            }
        }
        return ResponseData.success();
    }

    @Override
    public ResponseData query(Long userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ResponseData.error("订单不存在");
        }
        try {
            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = orderNo.toString();
            //支付宝交易号
//            String trade_no = payInfo.getPlatformNumber();
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();

            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(out_trade_no);
//            model.setTradeNo(trade_no);
            alipay_request.setBizModel(model);

            AlipayTradeQueryResponse alipay_response = client.execute(alipay_request);
//            System.out.println(alipay_response.getBody());
            String tradeStatus = alipay_response.getTradeStatus();
            if (StringUtils.isNotBlank(tradeStatus) && !StringUtils.equals(tradeStatus, "WAIT_BUYER_PAY")) {
                log.info("alipay:" + tradeStatus);
                if (order.getStatus() == Const.OrderStatus.NOT_PAY) {
                    // 更新订单状态
                    Order orderForUpdate = new Order();
                    orderForUpdate.setId(order.getId());
                    orderForUpdate.setUpdateTime(new Date());
                    if (StringUtils.equals(tradeStatus, "TRADE_SUCCESS")) {
                        orderForUpdate.setStatus(Const.OrderStatus.PAYED);
                        orderForUpdate.setPaymentTime(new Date());
                    }
                    if (StringUtils.equals(tradeStatus, "TRADE_CLOSED")) {
                        orderForUpdate.setStatus(Const.OrderStatus.NOT_PAY);
                    }
                    if (StringUtils.equals(tradeStatus, "TRADE_FINISHED")) {
                        orderForUpdate.setStatus(Const.OrderStatus.PAYED);
                    }
                    orderMapper.updateByPrimaryKeySelective(orderForUpdate);
                    // 记录支付平台信息
                    PayInfo payInfo = payInfoMapper.selectByOrderNoAndUserId(orderNo, userId);
                    if (payInfo == null) {
                        payInfo = new PayInfo();
                        payInfo.setPlatformStatus(tradeStatus);
                        payInfo.setUserId(userId);
                        payInfo.setPayPlatform(Const.PaymentPlatform.ALIPAY);
                        payInfo.setOrderNo(orderNo);
                        payInfo.setPlatformNumber(alipay_response.getTradeNo());
                        payInfoMapper.insert(payInfo);
                    } else {
                        PayInfo payInfoForUpdate = new PayInfo();
                        payInfoForUpdate.setId(payInfo.getId());
                        payInfoForUpdate.setPlatformStatus(tradeStatus);
                        payInfoForUpdate.setUpdateTime(new Date());
                        payInfoMapper.updateByPrimaryKeySelective(payInfoForUpdate);
                    }
                }
            }
            return ResponseData.successMessage(convertAlipayStatus(tradeStatus));
        } catch (AlipayApiException e) {
            log.error("alipay->query", e);
        }
        return ResponseData.error();
    }

    private String convertAlipayStatus(String tradeStatus) {
        if (StringUtils.isBlank(tradeStatus)) {
            return "支付失败";
        }
        switch (tradeStatus) {
            case "WAIT_BUYER_PAY":
                return "交易创建，等待买家付款";
            case "TRADE_CLOSED":
                return "未付款交易超时关闭，或支付完成后全额退款";
            case "TRADE_SUCCESS":
                return "交易支付成功";
            case "TRADE_FINISHED":
                return "交易结束，不可退款";
            default:
                return "支付失败";
        }
    }
}
