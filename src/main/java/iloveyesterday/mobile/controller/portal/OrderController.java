package iloveyesterday.mobile.controller.portal;

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
@RequestMapping("/order/")
public class OrderController {

    @Resource
    private IOrderService orderService;

    /**
     * 根据购物车中的商品生成订单
     *
     * @param session
     * @param shippingId
     * @return
     */
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

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    /**
     * 未付款订单
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_not_pay.do")
    @ResponseBody
    public ResponseData<PageInfo> listByNotPay(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.listByStatus(user.getId(), Const.OrderStatus.NOT_PAY, pageNum, pageSize);
    }

    /**
     * 已付款订单
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_payed.do")
    @ResponseBody
    public ResponseData<PageInfo> listByPayed(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.listByStatus(user.getId(), Const.OrderStatus.PAYED, pageNum, pageSize);
    }

    /**
     * 根据订单Id查询订单详情
     *
     * @param session
     * @param orderId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<OrderVo> detail(HttpSession session, Long orderId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.detail(user.getId(), orderId);
    }

    /**
     * 根据订单号查询订单详情
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("detail_by_no.do")
    @ResponseBody
    public ResponseData<OrderVo> detailByOrderNo(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.detailByOrderNo(user.getId(), orderNo);
    }

    /**
     * 根据商品和收货地址创建订单(立即购买)
     *
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("create_order.do")
    @ResponseBody
    public ResponseData<OrderVo> createOrder(
            HttpSession session,
            Long goodsId, Long propertiesId, int count, Long shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.create(user.getId(), goodsId, propertiesId, count, shippingId);
    }

    /**
     * 确认收货
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("confirm.do")
    @ResponseBody
    public ResponseData<OrderVo> confirm(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.confirm(user.getId(), orderNo);
    }

    /**
     * 取消订单(未付款)
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ResponseData<OrderVo> cancel(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.cancel(user.getId(), orderNo);
    }

    // todo 已发货(需要快递单号, 商家账号)

    // todo 物流

    // todo 退款

    // todo 处理退款请求(商家)


    /**
     * 查找订单
     *
     * @param session
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            HttpSession session,
            String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return orderService.search(user.getId(), keyword, pageNum, pageSize);
    }
}
