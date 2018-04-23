package iloveyesterday.mobile.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import iloveyesterday.mobile.common.AlipayConfig;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.service.IPayService;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay/")
public class PayController {

    @Resource
    private IPayService payService;

    @Resource
    private IOrderService orderService;

    /**
     * 生成支付宝付款页面
     *
     * @param response
     * @param session
     * @param orderNo
     * @throws IOException
     */
    @RequestMapping("alipay.do")
    public void doAlipay(
            HttpServletResponse response, HttpSession session, Long orderNo) throws IOException {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        if (user == null) {
            response.getWriter().write("请登陆");
        } else {
            ResponseData data = payService.createForm(user.getId(), orderNo);
            if (data.isSuccess()) {
                response.getWriter().write(data.getData().toString());//直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                response.getWriter().write(data.getMsg());
            }
        }
    }

    @RequestMapping("test_callback.do")
    public void testCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, InterruptedException {
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        // 成功 跳转
        response.getWriter().write(
                "支付成功, 3秒后跳转到首页, 手动跳转请点击 " +
                        "<a href='" + PropertiesUtil.getProperty("server.root", "/") + "'>链接</a>");
        String html = "<script>" +
                "setTimeout(function () {\n" +
                "window.location.href = '" +
                PropertiesUtil.getProperty("server.root", "/") +
                "'\n" +
                "}, 3000)" +
                "</script>";
        response.getWriter().write(html);
    }

    // http://localhost:8088/mobile/pay/alipay_return.do?total_amount=1899.00&timestamp=2018-04-22+14%3A03%3A42&sign=O%2BeRkU4YvufprduVj%2FmwK2dD9KaLtlPsVnJvyqZdcdknLbAodQ9pDx90TWGqLEK7hse8tZLurNc1qEvorn89eFq6cEHVMT%2Fy917kWsgOOhHC2d7A1PnBAWS24wTfwz1s8hC7NzDFNgfGgxb4ySN7Kdyul3VABvwYwygft8el0t3MC%2FYtep59dGllJ2jqAvJ3pelrq86EHSMOm%2BOW7IvziYWbyXYF2U0nNo%2Fr4TOBghMUwMOJSODRKsZDPHoJYqDieE%2BMhyEr5w1eOu7wURt9xCpV5jBFsPa8ypEGJnka7EG713A0fcZE7NvxF9P%2BbsQ3SuZeMYKAyploZjsiIRmwdQ%3D%3D&trade_no=2018042221001004210200253768&sign_type=RSA2&auth_app_id=2016091500519782&charset=UTF-8&seller_id=2088102175794211&method=alipay.trade.wap.pay.return&app_id=2016091500519782&out_trade_no=1524376999965&version=1.0

    /**
     * 支付宝前台回跳
     *
     * @param session
     * @param request
     * @param response
     */
    @RequestMapping("alipay_return.do")
    public void alipayReturn(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            // 计算得出通知验证结果
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            String host = PropertiesUtil.getProperty(
                    "server.root", "/");
            if (verify_result) {
                // 验证成功
                User user = (User) session.getAttribute(Const.CURRENT_USER);
                if (user == null) {
                    user = new User();
                }
                // 将订单状态设置成已付款
                ResponseData data = orderService.payed(Long.valueOf(out_trade_no));
                if (data.isSuccess()) {
                    // 记录支付信息
                    data = payService.save(user.getId(), Long.valueOf(out_trade_no), trade_no);
                    if (data.isSuccess()) {
                        // 成功
                    } else {
                        // 记录错误
//                        request.setAttribute("errMsg", data.getMsg());
//                        request.getRequestDispatcher("../alipay_error.jsp").forward(request, response);
                    }
                    // 跳转到支付成功页
                    response.sendRedirect(host + "alipay_success.jsp");
                } else {
                    // 修改订单状态失败
                    request.setAttribute("errMsg", data.getMsg());
                    request.getRequestDispatcher("../alipay_error.jsp").forward(request, response);
                }
                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                // 验证失败
                request.setAttribute("errMsg", "验证失败");
                request.getRequestDispatcher("../alipay_error.jsp").forward(request, response);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付结果异步通知
     *
     * @param request
     * @param response
     * @param session
     */
    @RequestMapping("alipay_notify.do")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

            if (verify_result) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                ResponseData data = payService.alipayNotify(out_trade_no, trade_no, trade_status);

                if (trade_status.equals("TRADE_FINISHED")) {
                    // 交易结束，不可退款
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    // 交易支付成功
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                response.getWriter().println("success");    //请不要修改或删除

                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                // 验证失败
                response.getWriter().println("fail");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询交易状态
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("query.do")
    @ResponseBody
    public ResponseData alipayQuery(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return payService.query(user.getId(), orderNo);
    }

}
