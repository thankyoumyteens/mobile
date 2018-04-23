package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Shipping;

import java.util.List;

public interface IShippingService {
    ResponseData<Shipping> add(Long userId, Shipping shipping);

    ResponseData<List<Shipping>> list(Long userId);

    ResponseData delete(Long userId, Long shippingId);

    ResponseData update(Long userId, Shipping shipping);
}
