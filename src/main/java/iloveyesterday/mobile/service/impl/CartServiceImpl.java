package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.CartMapper;
import iloveyesterday.mobile.dao.ProductMapper;
import iloveyesterday.mobile.pojo.Cart;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.service.ICartService;
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
    private ProductMapper productMapper;

    @Override
    public ResponseData create(Long userId, Long productId, Long quantity, String detail) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ResponseData.error();
        }
        // 加入购物车不影响库存
        // 如果已经在购物车中则增加数量
        Cart cartExist = cartMapper.selectByUserIdANdProductIdAndDetail(userId, productId, detail);
        if (cartExist != null) {
            Long count = cartExist.getQuantity() + quantity;
            if (count > product.getStock()) {
                return ResponseData.error("库存不足");
            }
            Cart cartForUpdate = new Cart();
            cartForUpdate.setId(cartExist.getId());
            cartForUpdate.setQuantity(count);
            cartForUpdate.setUpdateTime(new Date());
            int resultCount = cartMapper.updateByPrimaryKeySelective(cartForUpdate);
            if (resultCount > 0) {
                return ResponseData.success();
            }
            return ResponseData.error();
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setChecked(Const.CartStatus.CHECKED);
            cart.setQuantity(quantity);
            cart.setDetail(detail);

            if (quantity > product.getStock()) {
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
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if (product == null) {
                return ResponseData.error();
            }
            Long count = cart.getQuantity() + 1;
            if (count > product.getStock()) {
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
            Long count = cart.getQuantity() - 1;
            if (count <= 0) {
                return ResponseData.error("不能为0");
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

    private CartVo assembleCartVo(Cart cart) {
        CartVo cartVo = new CartVo();
        Product product = productMapper.selectByPrimaryKey(cart.getProductId());
        if (product == null) {
            return null;
        }
        cartVo.setId(cart.getId());
        cartVo.setUserId(cart.getUserId());
        cartVo.setProductId(cart.getProductId());
        cartVo.setChecked(cart.getChecked());
        cartVo.setDetail(cart.getDetail());
        cartVo.setMainImage(PropertiesUtil.getImageHost() + product.getMainImage());
        cartVo.setCreateTime(cart.getCreateTime());
        cartVo.setProductName(product.getName());
        cartVo.setQuantity(cart.getQuantity());
        cartVo.setUnitPrice(product.getPrice());
        cartVo.setUpdateTime(cart.getUpdateTime());

        return cartVo;
    }
}
