package iloveyesterday.mobile.controller.manage;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Category;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Resource
    private ICategoryService categoryService;

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<List<Category>> list(@RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        return categoryService.getChildrenParallelCategory(parentId);
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(HttpSession session, String name, String img, @RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (user.getRole() != Const.Role.ADMIN) {
            return ResponseData.error(ResponseCode.NO_PRIVILEGE.getCode(),
                    ResponseCode.NO_PRIVILEGE.getMsg());
        }
        return categoryService.addCategory(name, img, parentId);
    }

    @RequestMapping("rename.do")
    @ResponseBody
    public ResponseData rename(HttpSession session, Long categoryId, String name) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (user.getRole() != Const.Role.ADMIN) {
            return ResponseData.error(ResponseCode.NO_PRIVILEGE.getCode(),
                    ResponseCode.NO_PRIVILEGE.getMsg());
        }
        return categoryService.updateCategoryName(categoryId, name);
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public ResponseData delete(HttpSession session, Long categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (user.getRole() != Const.Role.ADMIN) {
            return ResponseData.error(ResponseCode.NO_PRIVILEGE.getCode(),
                    ResponseCode.NO_PRIVILEGE.getMsg());
        }
        return categoryService.delete(categoryId);
    }
}
