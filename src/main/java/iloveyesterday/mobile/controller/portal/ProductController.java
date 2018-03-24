package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.service.IProductService;
import iloveyesterday.mobile.vo.ProductVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Resource
    private IProductService productService;

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(
            Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return productService.getListByCategoryId(Const.Role.USER, categoryId, pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return productService.getListByKeyword(Const.Role.USER, keyword, pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<ProductVo> detail(Long productId) {
        return productService.detail(productId, Const.Role.USER);
    }
}
