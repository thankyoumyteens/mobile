package iloveyesterday.mobile.controller.home;

import com.google.common.collect.Maps;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IFileService;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/home/")
public class HomeManageController {

    @Resource
    private IFileService fileService;

    /**
     * 上传图片
     *
     * @param session
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ResponseData<Map> upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 验证是否是图片
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!StringUtils.equals(fileExtensionName, "jpg") &&
                !StringUtils.equals(fileExtensionName, "png")) {
            return ResponseData.error("请上传jpg或png格式图片");
        }
        // 上传
        String targetFileName = fileService.upload(file, path);

        String url = PropertiesUtil.getImageHost() + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ResponseData.success(fileMap);
    }


    @RequestMapping("index.do")
    public String index(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return "login";
        }
        return "index";
    }

    @RequestMapping("login.do")
    public String login() {
        return "login";
    }

    @RequestMapping("categories.do")
    public String categories(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return "login";
        }
        return "categories";
    }

    @RequestMapping("category.do")
    public String category(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return "login";
        }
        return "category";
    }
}
