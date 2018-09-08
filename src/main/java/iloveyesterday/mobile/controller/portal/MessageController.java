package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IMessageService;
import iloveyesterday.mobile.util.LoginUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/message/")
public class MessageController {

    @Resource
    private IMessageService messageService;

    /**
     * 发送提醒发货消息
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("send_delivery_msg.do")
    @ResponseBody
    public ResponseData sendDeliveryMessage(HttpServletRequest request, Long orderNo) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return messageService.sendDeliveryMessage(userId, orderNo);
    }

    /**
     * 获取收到的消息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> getMessageList(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return messageService.getMessageList(userId, pageNum, pageSize);
    }

}
