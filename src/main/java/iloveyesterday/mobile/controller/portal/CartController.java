package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.ICartService;
import iloveyesterday.mobile.util.LoginUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Resource
    private ICartService cartService;

    @RequestMapping("create_goods.do")
    @ResponseBody
    public ResponseData createGoods(HttpServletRequest request, Long goodsId, Long propertiesId, Long quantity) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return cartService.createByGoods(userId, goodsId, propertiesId, quantity);
    }

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
        return cartService.list(userId, pageNum, pageSize);
    }

    @RequestMapping("check.do")
    @ResponseBody
    public ResponseData check(HttpServletRequest request, Long cartId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return cartService.check(userId, cartId);
    }

    @RequestMapping("check_all.do")
    @ResponseBody
    public ResponseData checkAll(HttpServletRequest request) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return cartService.checkAll(userId);
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(HttpServletRequest request, Long cartId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return cartService.add(userId, cartId);
    }

    @RequestMapping("sub.do")
    @ResponseBody
    public ResponseData sub(HttpServletRequest request, Long cartId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return cartService.sub(userId, cartId);
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, Long cartId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return cartService.delete(userId, cartId);
    }

}
