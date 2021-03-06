package iloveyesterday.mobile.controller.manage;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.pojo.GoodsDetail;
import iloveyesterday.mobile.pojo.GoodsProperties;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IFileService;
import iloveyesterday.mobile.service.IGoodsService;
import iloveyesterday.mobile.util.*;
import iloveyesterday.mobile.vo.GoodsDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/manage/goods/")
public class GoodsManageController {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IFileService fileService;

    @RequestMapping("list.do")
    @ResponseBody
    ResponseData<PageInfo> getList(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return goodsService.getListBySellerId(userId, pageNum, pageSize);
    }

    @RequestMapping("list_category.do")
    @ResponseBody
    ResponseData<PageInfo> getListByCategoryId(
            HttpServletRequest request, Long categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return goodsService.getListByCategoryIdAndSellerId(userId, categoryId, pageNum, pageSize);
    }

    /**
     * 根据关键字搜索商品
     *
     * @param request
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ResponseData<PageInfo> search(
            HttpServletRequest request, String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return goodsService.getListByKeywordAndSellerId(userId, keyword, pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ResponseData<GoodsDetailVo> detail(Long goodsId) {
        return goodsService.detail(goodsId, Const.Role.ADMIN);
    }

    /**
     * 添加商品(不含规格和详情)
     *
     * @param request
     * @param goods
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData add(HttpServletRequest request, Goods goods) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        goods.setSellerId(userId);
        return goodsService.add(goods);
    }

    /**
     * 添加或修改商品规格
     *
     * @param properties
     * @return
     */
    @RequestMapping("update_properties.do")
    @ResponseBody
    public ResponseData addOrUpdateProperties(GoodsProperties properties) {
        return goodsService.addOrUpdateProperties(properties);
    }

    // todo update goods

    /**
     * 获取商品详细介绍
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("goods_detail.do")
    @ResponseBody
    public ResponseData<GoodsDetail> getDetail(Long goodsId) {
        return goodsService.getDetail(goodsId);
    }

    /**
     * 编写商品详细介绍
     *
     * @param detail
     * @return
     */
    @RequestMapping("update_detail.do")
    @ResponseBody
    public ResponseData<GoodsDetail> updateDetail(GoodsDetail detail) {
        return goodsService.updateDetail(detail);
    }

    /**
     * 上架商品
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("on_shelves.do")
    @ResponseBody
    public ResponseData onShelves(HttpServletRequest request, Long goodsId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return goodsService.changeStatus(userId, goodsId, Const.ProductStatus.ON_SALE);
    }

    /**
     * 下架商品
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("off_shelves.do")
    @ResponseBody
    public ResponseData offShelves(HttpServletRequest request, Long goodsId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return goodsService.changeStatus(userId, goodsId, Const.ProductStatus.NOT_ON_SALE);
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("delete.do")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, Long goodsId) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        return goodsService.changeStatus(userId, goodsId, Const.ProductStatus.DELETE);
    }

    /**
     * 删除商品规格
     *
     * @param propertiesId
     * @return
     */
    @RequestMapping("delete_property.do")
    @ResponseBody
    public ResponseData deleteProperty(Long propertiesId) {
        return goodsService.deleteProperty(propertiesId);
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
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 验证是否是图片
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!StringUtils.equals(fileExtensionName, "jpg") &&
                !StringUtils.equals(fileExtensionName, "png")) {
            return ResponseData.error("请上传jpg格式图片");
        }
        // 上传
        String targetFileName = fileService.upload(file, path);

        String url = PropertiesUtil.getImageHost() + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ResponseData.success(fileMap);
    }


}
