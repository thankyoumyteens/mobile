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
    ResponseData<PageInfo> getList(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        return goodsService.getListBySellerId(userId, pageNum, pageSize);
    }

    @RequestMapping("list_category.do")
    @ResponseBody
    ResponseData<PageInfo> getListByCategoryId(
            HttpSession session,
            Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        return goodsService.getListByCategoryIdAndSellerId(userId, categoryId, pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            HttpSession session,
            String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        return goodsService.getListByKeywordAndSellerId(userId, keyword, pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<GoodsDetailVo> detail(Long goodsId) {
        return goodsService.detail(goodsId, Const.Role.ADMIN);
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(HttpSession session, GoodsAddVo goods) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        goods.setSellerId(userId);
        return goodsService.add(goods);
    }

    // todo update

    /**
     * 上架商品
     *
     * @param session
     * @param goodsId
     * @return
     */
    @RequestMapping("on_shelves.do")
    @ResponseBody
    public ResponseData onShelves(HttpSession session, Long goodsId) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        return goodsService.changeStatus(userId, goodsId, Const.ProductStatus.ON_SALE);
    }

    /**
     * 下架商品
     *
     * @param session
     * @param goodsId
     * @return
     */
    @RequestMapping("off_shelves.do")
    @ResponseBody
    public ResponseData offShelves(HttpSession session, Long goodsId) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        return goodsService.changeStatus(userId, goodsId, Const.ProductStatus.NOT_ON_SALE);
    }

    /**
     * 删除商品
     *
     * @param session
     * @param goodsId
     * @return
     */
    @RequestMapping("delete.do")
    @ResponseBody
    public ResponseData delete(HttpSession session, Long goodsId) {
        ResponseData data = checkLogin(session);
        if (!data.isSuccess()) {
            return data;
        }
        Long userId = ((User) data.getData()).getId();
        return goodsService.changeStatus(userId, goodsId, Const.ProductStatus.DELETE);
    }

    /**
     * 检查登陆用户
     *
     * @param session
     * @return
     */
    private ResponseData checkLogin(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_SELLER);
        if (user == null) {
            return ResponseData.error(
                    ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg()
            );
        }
        if (user.getRole() != Const.Role.SELLER) {
            return ResponseData.error("请商家登陆");
        }
        return ResponseData.success(user);
    }

}
