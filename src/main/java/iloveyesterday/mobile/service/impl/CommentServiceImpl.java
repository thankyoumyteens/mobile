package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.GoodsCommentMapper;
import iloveyesterday.mobile.dao.GoodsPropertiesMapper;
import iloveyesterday.mobile.dao.OrderItemMapper;
import iloveyesterday.mobile.dao.UserMapper;
import iloveyesterday.mobile.pojo.GoodsComment;
import iloveyesterday.mobile.pojo.GoodsProperties;
import iloveyesterday.mobile.pojo.OrderItem;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.ICommentService;
import iloveyesterday.mobile.util.DateTimeUtil;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.CommentVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsComment> commentList = commentMapper.selectByGoodsId(goodsId);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (GoodsComment comment : commentList) {
            CommentVo commentVo = assembleCommentVo(comment);
            if (commentVo == null) continue;
            commentVoList.add(commentVo);
        }

        PageInfo pageResult = new PageInfo(commentList);
        pageResult.setList(commentVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> getUserComments(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsComment> commentList = commentMapper.selectByUserId(userId);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (GoodsComment comment : commentList) {
            CommentVo commentVo = assembleCommentVo(comment);
            if (commentVo == null) continue;
            commentVoList.add(commentVo);
        }

        PageInfo pageResult = new PageInfo(commentList);
        pageResult.setList(commentVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> listByLevel(Long goodsId, int level, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        int starLeft = 0;
        int starRight = 0;

        switch (level) {
            case Const.CommentLevel.GOOD:
                // 4-5星是好评
                starLeft = 4;
                starRight = 5;
                break;
            case Const.CommentLevel.NORMAL:
                // 3星是中评
                starLeft = 3;
                starRight = 3;
                break;
            case Const.CommentLevel.BAD:
                // 1-2星是差评
                starLeft = 1;
                starRight = 2;
                break;
            default:
                return list(goodsId, pageNum, pageSize);
        }

        List<GoodsComment> commentList = commentMapper.selectByGoodsId(goodsId);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (GoodsComment comment : commentList) {
            if (comment.getStar() > starRight || comment.getStar() < starLeft) {
                continue;
            }
            CommentVo commentVo = assembleCommentVo(comment);
            if (commentVo == null) continue;
            commentVoList.add(commentVo);
        }

        PageInfo pageResult = new PageInfo(commentList);
        pageResult.setList(commentVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> listWithImages(Long goodsId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsComment> commentList = commentMapper.selectByGoodsId(goodsId);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (GoodsComment comment : commentList) {
            if (StringUtils.isBlank(comment.getImages())) {
                continue;
            }
            CommentVo commentVo = assembleCommentVo(comment);
            if (commentVo == null) continue;
            commentVoList.add(commentVo);
        }

        PageInfo pageResult = new PageInfo(commentList);
        pageResult.setList(commentVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> listWithText(Long goodsId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsComment> commentList = commentMapper.selectByGoodsId(goodsId);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (GoodsComment comment : commentList) {
            if (StringUtils.isBlank(comment.getText())) {
                continue;
            }
            CommentVo commentVo = assembleCommentVo(comment);
            if (commentVo == null) continue;
            commentVoList.add(commentVo);
        }

        PageInfo pageResult = new PageInfo(commentList);
        pageResult.setList(commentVoList);
        return ResponseData.success(pageResult);
    }

    private CommentVo assembleCommentVo(GoodsComment comment) {
        // 不返回处于删除状态的评论
        if (comment.getStatus().equals(Const.CommentStatus.DELETE)) return null;

        CommentVo commentVo = new CommentVo();
        User user = userMapper.selectByPrimaryKey(comment.getUserId());
        if (user == null) return null;
        CommentVo.Author author = new CommentVo.Author();
        author.setUserId(user.getId());
        author.setNickname(user.getNickname());
        author.setAvatar(PropertiesUtil.getImageHost() + user.getAvatar());
        commentVo.setAuthor(author);
        commentVo.setCommentId(comment.getId());
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(comment.getOrderItemId());
        if (orderItem == null) return null;
        commentVo.setGoodsId(orderItem.getProductId());
        commentVo.setGoodsName(orderItem.getProductName());
        commentVo.setImages(getSubImagesWithUrl(comment.getImages()));
        commentVo.setStar(comment.getStar());
        commentVo.setText(comment.getText());
        commentVo.setOrderCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        commentVo.setCreateTime(DateTimeUtil.dateToStr(comment.getCreateTime()));
        commentVo.setUpdateTime(DateTimeUtil.dateToStr(comment.getUpdateTime()));
        GoodsProperties properties = propertiesMapper.selectByPrimaryKey(orderItem.getPropertiesId());
        commentVo.setProperties(JsonUtil.getPropertiesString(properties.getText()));
        return commentVo;
    }

    private String getSubImagesWithUrl(String subImages) {
        if (StringUtils.isBlank(subImages)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String[] imgUrlList = subImages.split(",");
        for (String img : imgUrlList) {
            sb.append(PropertiesUtil.getImageHost()).append(img).append(",");
        }
        sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        return sb.toString();
    }
}
