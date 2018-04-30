package iloveyesterday.mobile.controller.manage;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.vo.OrderVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Resource
    private IOrderService orderService;

    @RequestMapping("list_by_seller.do")
    @ResponseBody
    public ResponseData<PageInfo> listBySeller(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_SELLER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.listBySeller(user.getId(), pageNum, pageSize);
    }

    /**
     * 发货
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("send.do")
    @ResponseBody
    public ResponseData<OrderVo> send(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_SELLER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.send(user.getId(), orderNo);
    }

}
