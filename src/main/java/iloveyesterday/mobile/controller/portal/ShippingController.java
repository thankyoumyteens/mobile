package iloveyesterday.mobile.controller.portal;

import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Shipping;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IShippingService;
import iloveyesterday.mobile.util.LoginUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Resource
    private IShippingService shippingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData<Shipping> add(HttpServletRequest request, Shipping shipping) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return shippingService.add(userId, shipping);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<List<Shipping>> list(HttpServletRequest request) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return shippingService.list(userId);
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, Long shippingId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return shippingService.delete(userId, shippingId);
    }


    @RequestMapping("update.do")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, Shipping shipping) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return shippingService.update(userId, shipping);
    }

}
