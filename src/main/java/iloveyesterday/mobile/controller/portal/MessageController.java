package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/message/")
public class MessageController {

    @Resource
    private IMessageService messageService;

    /**
     * 发送提醒发货消息
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("send_delivery_msg.do")
    @ResponseBody
    public ResponseData sendDeliveryMessage(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        return messageService.sendDeliveryMessage(user.getId(), orderNo);
    }

    /**
     * 获取收到的消息
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> getMessageList(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            user = (User) session.getAttribute(Const.CURRENT_SELLER);
            if (user == null) {
                user = (User) session.getAttribute(Const.CURRENT_ADMIN);
                if (user == null) {
                    return ResponseData.error(
                            ResponseCode.NEED_LOGIN.getCode(),
                            ResponseCode.NEED_LOGIN.getMsg()
                    );
                }
            }
        }
        return messageService.getMessageList(user.getId(), pageNum, pageSize);
    }

}
