package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.GoodsDetail;
import iloveyesterday.mobile.service.IGoodsService;
import iloveyesterday.mobile.vo.GoodsDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/goods/")
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    @RequestMapping("list.do")
    @ResponseBody
    ResponseData<PageInfo> getListByCategoryId(
            Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return goodsService.getListByCategoryId(Const.Role.USER, categoryId, pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return goodsService.getListByKeyword(Const.Role.USER, keyword, pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<GoodsDetailVo> detail(Long goodsId) {
        return goodsService.detail(goodsId, Const.Role.USER);
    }

    /**
     * 获取商品介绍
     * @param goodsId
     * @return
     */
    @RequestMapping("desc.do")
    @ResponseBody
    public ResponseData<GoodsDetail> goodsDesc(Long goodsId) {
        return goodsService.getDetail(goodsId);
    }

}
