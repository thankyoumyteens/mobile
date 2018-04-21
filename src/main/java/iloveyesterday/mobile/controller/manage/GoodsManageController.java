package iloveyesterday.mobile.controller.manage;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IGoodsService;
import iloveyesterday.mobile.vo.GoodsAddVo;
import iloveyesterday.mobile.vo.GoodsDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/goods/")
public class GoodsManageController {

    @Resource
    private IGoodsService goodsService;

    @RequestMapping("list.do")
    @ResponseBody
    ResponseData<PageInfo> getListByCategoryId(
            Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return goodsService.getListByCategoryId(Const.Role.ADMIN, categoryId, pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return goodsService.getListByKeyword(Const.Role.ADMIN, keyword, pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<GoodsDetailVo> detail(Long goodsId) {
        return goodsService.detail(goodsId, Const.Role.ADMIN);
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(
            HttpSession session, GoodsAddVo goods) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        // todo 商家上架商品
        if (user.getRole() != Const.Role.ADMIN) {
            return ResponseData.error(ResponseCode.NO_PRIVILEGE.getCode(),
                    ResponseCode.NO_PRIVILEGE.getMsg());
        }
        goods.setSellerId(user.getId());
        return goodsService.add(goods);
    }

}
