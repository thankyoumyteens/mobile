package iloveyesterday.mobile.service.impl;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.RegionMapper;
import iloveyesterday.mobile.dao.ShippingMapper;
import iloveyesterday.mobile.pojo.Region;
import iloveyesterday.mobile.pojo.Shipping;
import iloveyesterday.mobile.service.IShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("shippingService")
public class ShippingServiceImpl implements IShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    @Resource
    private RegionMapper regionMapper;

    @Override
    public ResponseData<Shipping> add(Long userId, Shipping shipping) {
        shipping.setUserId(userId);
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount > 0) {
            return ResponseData.success(shipping);
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<List<Region>> region(Double parentId) {
        List<Region> regionList = regionMapper.selectByParentId(parentId);
        return ResponseData.success(regionList);
    }

    @Override
    public ResponseData<List<Shipping>> list(Long userId) {
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        return ResponseData.success(shippingList);
    }
}
