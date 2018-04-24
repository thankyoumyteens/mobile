package iloveyesterday.mobile.service.impl;

import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Message;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.GoodsMapper;
import iloveyesterday.mobile.dao.OrderItemMapper;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.pojo.OrderItem;
import iloveyesterday.mobile.service.IMessageService;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.RedisPoolUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements IMessageService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public ResponseData sendDeliveryMessage(Long userId, Long orderNo) {
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        List<Long> goodsIdList = Lists.newArrayList();
        List<Long> sellerIdList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            if (!goodsIdList.contains(orderItem.getProductId())) {
                Goods goods = goodsMapper.selectByPrimaryKey(orderItem.getProductId());
                if (!sellerIdList.contains(goods.getSellerId())) {
                    sellerIdList.add(goods.getSellerId());
                }
                goodsIdList.add(orderItem.getProductId());
            }
        }
        for (Long sellerId : sellerIdList) {
            Message<Long> message = new Message<>();
            message.setData(orderNo);
            message.setFrom(userId);
            message.setTo(sellerId);
            message.setMessage("提醒发货");
            message.setStatus(Message.MessageStatus.SENT);
            message.setType(Message.MessageType.DEFAULT);
            // 存储消息
            String messageString = JsonUtil.obj2String(message);
            RedisPoolUtil.set(message.getFrom().toString(), messageString);
            RedisPoolUtil.set(message.getTo().toString(), messageString);
        }
        return ResponseData.success();
    }
}
