package iloveyesterday.mobile.controller.portal;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.vo.OrderVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @RequestMapping("create.do")
    @ResponseBody
    public ResponseData<OrderVo> create(HttpSession session, Long shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.create(user.getId(), shippingId);
    }
}
