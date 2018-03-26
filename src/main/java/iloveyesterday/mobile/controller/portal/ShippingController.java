package iloveyesterday.mobile.controller.portal;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Region;
import iloveyesterday.mobile.pojo.Shipping;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IShippingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Resource
    private IShippingService shippingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData<Shipping> add(HttpSession session, Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return shippingService.add(user.getId(), shipping);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<List<Shipping>> list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return shippingService.list(user.getId());
    }

    @RequestMapping("region.do")
    @ResponseBody
    public ResponseData<List<Region>> region(@RequestParam(value = "parentId", defaultValue = "1") Double parentId) {
        return shippingService.region(parentId);
    }
}
