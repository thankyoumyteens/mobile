package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.GoodsMapper;
import iloveyesterday.mobile.dao.MessageMapper;
import iloveyesterday.mobile.dao.OrderItemMapper;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.pojo.Message;
import iloveyesterday.mobile.pojo.OrderItem;
import iloveyesterday.mobile.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements IMessageService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public ResponseData sendDeliveryMessage(Long userId, Long orderNo) {
        int resultCount;
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
            Message message = new Message();
            message.setData(orderNo.toString());
            message.setFromId(userId);
            message.setToId(sellerId);
            message.setMessage("提醒发货");
            message.setStatus(Const.MessageStatus.SENT);
            message.setType(Const.MessageType.DELIVERY);
            // 存储消息
            resultCount = messageMapper.insert(message);
            if (resultCount <= 0) {
                return ResponseData.error();
            }
        }
        return ResponseData.success();
    }

    @Override
    public ResponseData<PageInfo> getMessageList(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messageList = messageMapper.selectByToId(userId);
        PageInfo pageResult = new PageInfo(messageList);
//        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

}
