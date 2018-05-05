package iloveyesterday.mobile.service.impl;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.ShippingMapper;
import iloveyesterday.mobile.pojo.Shipping;
import iloveyesterday.mobile.service.IShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("shippingService")
public class ShippingServiceImpl implements IShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    @Override
    public ResponseData<Shipping> add(Long userId, Shipping shipping) {
        int count = shippingMapper.selectCountByUserId(userId);
        if (count > 5) {
            return ResponseData.error("已达到上限, 请删除不必要的地址");
        }
        shipping.setUserId(userId);
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount > 0) {
            return ResponseData.success(shipping);
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<List<Shipping>> list(Long userId) {
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        return ResponseData.success(shippingList);
    }

    @Override
    public ResponseData delete(Long userId, Long shippingId) {
        int resultCount = shippingMapper.deleteByPrimaryKeyAndUserId(shippingId, userId);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData update(Long userId, Shipping shipping) {
        shipping.setUserId(userId);
        if (!checkShipping(userId, shipping.getId())) {
            return ResponseData.error("收货地址不存在");
        }
        int resultCount = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (resultCount > 0) {
            return ResponseData.success(shipping);
        }
        return ResponseData.error();
    }

    private boolean checkShipping(Long userId, Long shippingId) {
        int resultCount = shippingMapper.selectByUserIdAndShippingId(userId, shippingId);
        return resultCount > 0;
    }
}
