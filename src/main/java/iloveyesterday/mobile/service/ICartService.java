package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;

public interface ICartService {

    /**
     * 添加商品到购物车(废弃)
     *
     * @param userId
     * @param productId
     * @param quantity  商品数量
     * @param detail
     * @return
     */
    ResponseData create(Long userId, Long productId, Long quantity, String detail);

    /**
     * 获取用户的购物车列表
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize);

    /**
     * 选中购物车中的商品
     *
     * @param userId
     * @param cartId
     * @return
     */
    ResponseData check(Long userId, Long cartId);

    /**
     * 全选
     *
     * @param userId
     * @return
     */
    ResponseData checkAll(Long userId);

    /**
     * 商品数量加一
     *
     * @param userId
     * @param cartId
     * @return
     */
    ResponseData add(Long userId, Long cartId);

    /**
     * 商品数量减一
     *
     * @param userId
     * @param cartId
     * @return
     */
    ResponseData sub(Long userId, Long cartId);

    /**
     * 将商品添加到购物车
     *
     * @param userId
     * @param goodsId      商品id
     * @param propertiesId 商品规格id
     * @param quantity     商品数量
     * @return
     */
    ResponseData createByGoods(Long userId, Long goodsId, Long propertiesId, Long quantity);
}
