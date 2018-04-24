package iloveyesterday.mobile.controller.portal;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("delivery_msg.do")
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
}
