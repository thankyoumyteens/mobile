package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Resource
    private ICartService cartService;

    @RequestMapping("create.do")
    @ResponseBody
    public ResponseData create(HttpSession session, Long productId, Long quantity, String detail) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return cartService.create(user.getId(), productId, quantity, detail);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(HttpSession session,
                                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return cartService.list(user.getId(), pageNum, pageSize);
    }

    @RequestMapping("check.do")
    @ResponseBody
    public ResponseData check(HttpSession session, Long cartId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return cartService.check(user.getId(), cartId);
    }

    @RequestMapping("check_all.do")
    @ResponseBody
    public ResponseData checkAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return cartService.checkAll(user.getId());
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(HttpSession session, Long cartId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return cartService.add(user.getId(), cartId);
    }

    @RequestMapping("sub.do")
    @ResponseBody
    public ResponseData sub(HttpSession session, Long cartId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return cartService.sub(user.getId(), cartId);
    }
}
