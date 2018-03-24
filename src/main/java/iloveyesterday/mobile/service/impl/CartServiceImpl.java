package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.CartMapper;
import iloveyesterday.mobile.dao.ProductMapper;
import iloveyesterday.mobile.pojo.Cart;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.service.ICartService;
import iloveyesterday.mobile.vo.CartVo;
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
        // 如果已经在购物车中则增加数量
        Cart cartExist = cartMapper.selectByUserIdANdProductIdAndDetail(userId, productId, detail);
        if (cartExist != null) {
            Long count = cartExist.getQuantity() + quantity;
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
            cart.setChecked(1L);
            cart.setQuantity(quantity);
            cart.setDetail(detail);

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
        cartVo.setCreateTime(cart.getCreateTime());
        cartVo.setProductName(product.getName());
        cartVo.setQuantity(cart.getQuantity());
        cartVo.setUnitPrice(product.getPrice());
        cartVo.setUpdateTime(cart.getUpdateTime());

        return cartVo;
    }
}
