package iloveyesterday.mobile.service.impl;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.ShippingMapper;
import iloveyesterday.mobile.pojo.Shipping;
import iloveyesterday.mobile.service.IShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("shippingService")
public class ShippingServiceImpl implements IShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    @Override
    public ResponseData<Shipping> add(Long userId, Shipping shipping) {
        shipping.setUserId(userId);
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount > 0) {
            return ResponseData.success(shipping);
        }
        return ResponseData.error();
    }
}
