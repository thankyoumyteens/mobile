package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.GoodsCommentMapper;
import iloveyesterday.mobile.dao.GoodsPropertiesMapper;
import iloveyesterday.mobile.dao.OrderItemMapper;
import iloveyesterday.mobile.dao.UserMapper;
import iloveyesterday.mobile.pojo.GoodsComment;
import iloveyesterday.mobile.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("commentService")
public class CommentServiceImpl implements ICommentService {

    @Resource
    private GoodsCommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private GoodsPropertiesMapper propertiesMapper;


    @Override
    public ResponseData create(GoodsComment comment) {
        if (comment.getStatus() != Const.CommentStatus.PUBLIC &&
                comment.getStatus() != Const.CommentStatus.PRIVATE) {
            comment.setStatus(Const.CommentStatus.PUBLIC);
        }
        int resultCount = commentMapper.insert(comment);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<PageInfo> list(Long goodsId, int pageNum, int pageSize) {
        return null;
    }
}
