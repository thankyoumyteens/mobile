package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.GoodsComment;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.ICommentService;
import iloveyesterday.mobile.service.IFileService;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.LoginUtil;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment/")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @Resource
    private IFileService fileService;

    /**
     * 填写评论
     *
     * @param comment
     * @return
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ResponseData create(HttpServletRequest request, GoodsComment comment) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        comment.setUserId(userId);
        return commentService.create(comment);
    }

    /**
     * 填写评论
     *
     * @param str
     * @return
     */
    @RequestMapping("create_list.do")
    @ResponseBody
    public ResponseData createList(HttpServletRequest request, String str) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        List<GoodsComment> commentList = JsonUtil.string2Obj(str, List.class, GoodsComment.class);
        if (CollectionUtils.isEmpty(commentList)) {
            return ResponseData.error("请正确填写");
        }
        for (GoodsComment comment : commentList) {
            comment.setUserId(userId);
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
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("user_comments.do")
    @ResponseBody
    public ResponseData<PageInfo> userComments(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return commentService.getUserComments(userId, pageNum, pageSize);
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

    /**
     * 上传图片
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ResponseData<Map> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request) {
        String index = request.getParameter("index");
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 验证是否是图片
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!StringUtils.equals(fileExtensionName, "jpg")) {
            return ResponseData.error("请上传jpg格式图片");
        }
        // 上传
        String targetFileName = fileService.upload(file, path);

        String url = PropertiesUtil.getImageHost() + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        fileMap.put("index", index);
        return ResponseData.success(fileMap);
    }

}
