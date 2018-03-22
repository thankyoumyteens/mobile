package iloveyesterday.mobile.controller.manage;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Resource
    private IProductService productService;

    @RequestMapping("all.do")
    @ResponseBody
    public ResponseData<PageInfo> all(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return productService.getAll(pageNum, pageSize);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(
            Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return productService.getListByCategoryId(Const.Role.ADMIN, categoryId, pageNum, pageSize);
    }

//    @RequestMapping("detail.do")
//    @ResponseBody
//    public ResponseData<ProductVo> detail(Long productId) {
// todo
//    }

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        // todo 商家编辑
        if (user.getRole() != Const.Role.ADMIN) {
            return ResponseData.error(ResponseCode.NO_PRIVILEGE.getCode(),
                    ResponseCode.NO_PRIVILEGE.getMsg());
        }
        // todo
        return productService.add(product);
    }


}
