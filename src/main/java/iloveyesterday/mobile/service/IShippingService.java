package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Shipping;

public interface IShippingService {
    ResponseData<Shipping> add(Long userId, Shipping shipping);
}
