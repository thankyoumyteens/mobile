package iloveyesterday.mobile.controller.portal;

import iloveyesterday.mobile.common.AlipayConfig;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.service.IPayService;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/pay/")
public class PayController {

    @Resource
    private IPayService payService;

    @Resource
    private IOrderService orderService;

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

    /**
     * 支付宝回调
     *
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("alipay_callback.do")
    public void alipayCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, InterruptedException {
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            user = new User(); // todo 解决办法
        }
        String orderNo = request.getParameter("out_trade_no");
        String alipayOrderNo = request.getParameter("trade_no");
//        String payment = request.getParameter("total_amount");
        ResponseData data = orderService.payed(Long.valueOf(orderNo));
        if (data.isSuccess()) {
            data = payService.save(user.getId(), Long.valueOf(orderNo), alipayOrderNo);
            if (data.isSuccess()) {
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
//                response.sendRedirect(PropertiesUtil.getProperty(
//                        "server.root", "/"));
            } else {
                response.getWriter().write(data.getMsg());
                response.getWriter().write(
                        "跳转到首页请点击 " +
                                "<a href='" + PropertiesUtil.getProperty("server.root", "/") + "'>链接</a>");
            }
        }
    }
}
