package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.GoodsComment;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.ICommentService;
import iloveyesterday.mobile.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/comment/")
public class CommentController {

    @Resource
    private ICommentService commentService;

    /**
     * 填写评论
     *
     * @param session
     * @param comment
     * @return
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ResponseData create(HttpSession session, GoodsComment comment) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        comment.setUserId(user.getId());
        return commentService.create(comment);
    }

    /**
     * 填写评论
     *
     * @param session
     * @param str
     * @return
     */
    @RequestMapping("create_list.do")
    @ResponseBody
    public ResponseData createList(HttpSession session, String str) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        List<GoodsComment> commentList = JsonUtil.string2Obj(str, List.class, GoodsComment.class);
        if (CollectionUtils.isEmpty(commentList)) {
            return ResponseData.error("请正确填写");
        }
        for (GoodsComment comment : commentList) {
            comment.setUserId(user.getId());
        }
        return commentService.createByList(commentList);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(
            Long goodsId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return commentService.list(goodsId, pageNum, pageSize);
    }

    /**
     * 获取当前登陆用户的评论
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("user_comments.do")
    @ResponseBody
    public ResponseData<PageInfo> userComments(
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
        return commentService.getUserComments(user.getId(), pageNum, pageSize);
    }

    /**
     * 根据评星筛选评价
     *
     * @param goodsId
     * @param level    好评/中评/差评
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_level.do")
    @ResponseBody
    public ResponseData<PageInfo> listByLevel(
            Long goodsId,
            int level,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return commentService.listByLevel(goodsId, level, pageNum, pageSize);
    }

    /**
     * 筛选有图评论
     *
     * @param goodsId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_img.do")
    @ResponseBody
    public ResponseData<PageInfo> listWithImages(
            Long goodsId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return commentService.listWithImages(goodsId, pageNum, pageSize);
    }

    /**
     * 筛选有文字评论
     *
     * @param goodsId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_text.do")
    @ResponseBody
    public ResponseData<PageInfo> listWithText(
            Long goodsId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return commentService.listWithText(goodsId, pageNum, pageSize);
    }
}
