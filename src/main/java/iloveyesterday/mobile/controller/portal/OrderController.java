package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.util.LoginUtil;
import iloveyesterday.mobile.vo.OrderVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Resource
    private IOrderService orderService;

    /**
     * 根据购物车中的商品生成订单
     *
     * @param shippingId
     * @return
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ResponseData<OrderVo> create(
            HttpServletRequest request, Long shippingId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.create(userId, shippingId, pageNum, pageSize);
    }

    /**
     * 当前用户的订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.list(userId, pageNum, pageSize);
    }

    /**
     * 未付款订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_not_pay.do")
    @ResponseBody
    public ResponseData<PageInfo> listByNotPay(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.listByStatus(userId, Const.OrderStatus.NOT_PAY, pageNum, pageSize);
    }

    /**
     * 已付款订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_payed.do")
    @ResponseBody
    public ResponseData<PageInfo> listByPayed(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.listByStatus(userId, Const.OrderStatus.PAYED, pageNum, pageSize);
    }

    /**
     * 根据订单Id查询订单详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<OrderVo> detail(HttpServletRequest request, Long orderId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.detail(userId, orderId);
    }

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("detail_by_no.do")
    @ResponseBody
    public ResponseData<OrderVo> detailByOrderNo(HttpServletRequest request, Long orderNo) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.detailByOrderNo(userId, orderNo);
    }

    /**
     * 根据商品和收货地址创建订单(立即购买)
     *
     * @param shippingId
     * @return
     */
    @RequestMapping("create_order.do")
    @ResponseBody
    public ResponseData<OrderVo> createOrder(
            HttpServletRequest request,
            Long goodsId, Long propertiesId, int count, Long shippingId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.create(userId, goodsId, propertiesId, count, shippingId);
    }

    /**
     * 确认收货
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("confirm.do")
    @ResponseBody
    public ResponseData<OrderVo> confirm(HttpServletRequest request, Long orderNo) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.confirm(userId, orderNo);
    }

    /**
     * 取消订单(未付款)
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ResponseData<OrderVo> cancel(HttpServletRequest request, Long orderNo) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.cancel(userId, orderNo);
    }

    // todo 已发货(需要快递单号, 商家账号)

    // todo 物流

    // todo 退款

    // todo 处理退款请求(商家)


    /**
     * 查找订单
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            HttpServletRequest request, String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return orderService.search(userId, keyword, pageNum, pageSize);
    }
}
