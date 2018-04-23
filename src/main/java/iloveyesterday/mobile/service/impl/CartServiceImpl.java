package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.CartMapper;
import iloveyesterday.mobile.dao.GoodsMapper;
import iloveyesterday.mobile.dao.GoodsPropertiesMapper;
import iloveyesterday.mobile.pojo.Cart;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.pojo.GoodsProperties;
import iloveyesterday.mobile.service.ICartService;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {

    @Resource
    private CartMapper cartMapper;


    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPropertiesMapper propertiesMapper;

    @Override
    public ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Cart> cartList = cartMapper.selectByUserId(userId);

        List<CartVo> cartVoList = Lists.newArrayList();
        for (Cart cart : cartList) {
            CartVo cartVo = assembleCartVo(cart);
            cartVoList.add(cartVo);
        }
        PageInfo pageResult = new PageInfo(cartList);
        pageResult.setList(cartVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData check(Long userId, Long cartId) {
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        if (cart == null) {
            return ResponseData.error();
        }
        if (cart.getUserId().equals(userId)) {
            Cart cartForUpdate = new Cart();
            cartForUpdate.setId(cartId);
            cartForUpdate.setChecked(cart.getChecked().equals(Const.CartStatus.CHECKED) ? Const.CartStatus.UNCHECKED : Const.CartStatus.CHECKED);
            cartForUpdate.setUpdateTime(new Date());
            int resultCount = cartMapper.updateByPrimaryKeySelective(cartForUpdate);
            if (resultCount > 0) {
                return ResponseData.success();
            }
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData checkAll(Long userId) {
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseData.error();
        }
        for (Cart cart : cartList) {
            if (!cart.getChecked().equals(Const.CartStatus.CHECKED)) {
                Cart cartForUpdate = new Cart();
                cartForUpdate.setId(cart.getId());
                cartForUpdate.setChecked(Const.CartStatus.CHECKED);
                cartForUpdate.setUpdateTime(new Date());
                int resultCount = cartMapper.updateByPrimaryKeySelective(cartForUpdate);
                if (resultCount <= 0) {
                    return ResponseData.error();
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    public ResponseData add(Long userId, Long cartId) {
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        if (cart == null) {
            return ResponseData.error();
        }
        if (cart.getUserId().equals(userId)) {
            GoodsProperties properties = propertiesMapper.selectByPrimaryKey(Long.valueOf(cart.getDetail()));
            if (properties == null) {
                return ResponseData.error("商品已下架");
            }
            Long count = cart.getQuantity() + 1;
            if (count > properties.getStock()) {
                return ResponseData.error("库存不足");
            }
            Cart cartForUpdate = new Cart();
            cartForUpdate.setId(cartId);
            cartForUpdate.setQuantity(count);
            cartForUpdate.setUpdateTime(new Date());
            int resultCount = cartMapper.updateByPrimaryKeySelective(cartForUpdate);
            if (resultCount > 0) {
                return ResponseData.success();
            }
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData sub(Long userId, Long cartId) {
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        if (cart == null) {
            return ResponseData.error();
        }
        if (cart.getUserId().equals(userId)) {
            GoodsProperties properties = propertiesMapper.selectByPrimaryKey(Long.valueOf(cart.getDetail()));
            if (properties == null) {
                return ResponseData.error("商品已下架");
            }
            Long count = cart.getQuantity() - 1;
            if (count <= 0) {
                return ResponseData.error("不能为0");
            }
            if (count > properties.getStock()) {
                return ResponseData.error("库存不足");
            }
            Cart cartForUpdate = new Cart();
            cartForUpdate.setId(cartId);
            cartForUpdate.setQuantity(count);
            cartForUpdate.setUpdateTime(new Date());
            int resultCount = cartMapper.updateByPrimaryKeySelective(cartForUpdate);
            if (resultCount > 0) {
                return ResponseData.success();
            }
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData createByGoods(Long userId, Long goodsId, Long propertiesId, Long quantity) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        GoodsProperties properties = propertiesMapper.selectByPrimaryKey(propertiesId);

        if (goods == null || properties == null) {
            return ResponseData.error("商品不存在");
        }
        // 加入购物车不影响库存
        // 如果已经在购物车中则增加数量
        Cart cartExist = cartMapper.selectByUserIdAndGoodsInfo(userId, goodsId, String.valueOf(propertiesId));
        if (cartExist != null) {
            Long count = cartExist.getQuantity() + quantity;
            if (count > properties.getStock()) {
                return ResponseData.error("库存不足");
            }
            Cart cartForUpdate = new Cart();
            cartForUpdate.setId(cartExist.getId());
            cartForUpdate.setQuantity(count);
            cartForUpdate.setUpdateTime(new Date());
            int resultCount = cartMapper.updateByPrimaryKeySelective(cartForUpdate);
            if (resultCount > 0) {
                return ResponseData.success("成功");
            }
            return ResponseData.error();
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(goodsId);
            cart.setChecked(Const.CartStatus.UNCHECKED);
            cart.setQuantity(quantity);
            cart.setDetail(String.valueOf(propertiesId));

            if (quantity > properties.getStock()) {
                return ResponseData.error("库存不足");
            }

            int resultCount = cartMapper.insert(cart);
            if (resultCount > 0) {
                return ResponseData.success();
            }
            return ResponseData.error();
        }
    }

    @Override
    public ResponseData delete(Long userId, Long cartId) {
        Cart cart = cartMapper.selectByPrimaryKeyAndUserId(cartId, userId);
        if (cart == null) {
            return ResponseData.success();
        }
        int resultCount = cartMapper.deleteByPrimaryKey(cartId);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    private CartVo assembleCartVo(Cart cart) {
        CartVo cartVo = new CartVo();
        Goods product = goodsMapper.selectByPrimaryKey(cart.getProductId());
        GoodsProperties properties = propertiesMapper.selectByPrimaryKey(Long.valueOf(cart.getDetail()));
        if (product == null || properties == null) {
            return null;
        }
        cartVo.setId(cart.getId());
        cartVo.setUserId(cart.getUserId());
        cartVo.setProductId(cart.getProductId());
        cartVo.setChecked(cart.getChecked());
        cartVo.setDetail(JsonUtil.getPropertiesString(properties.getText()));
        cartVo.setMainImage(PropertiesUtil.getImageHost() + product.getMainImage());
        cartVo.setProductName(product.getName());
        cartVo.setQuantity(cart.getQuantity());
        cartVo.setUnitPrice(properties.getPrice());

        return cartVo;
    }
}
