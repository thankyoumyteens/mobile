package iloveyesterday.mobile.controller.portal;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Category;
import iloveyesterday.mobile.service.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/category/")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<List<Category>> list(@RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        return categoryService.getChildrenParallelCategory(parentId);
    }
}
