package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.OrderItemMapper;
import iloveyesterday.mobile.dao.ReviewMapper;
import iloveyesterday.mobile.dao.UserMapper;
import iloveyesterday.mobile.pojo.OrderItem;
import iloveyesterday.mobile.pojo.Review;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IReviewService;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.ReviewVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("reviewService")
public class ReviewServiceImpl implements IReviewService {

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public ResponseData create(Review review) {
        // 设置为公开评论
        review.setStatus(Const.CommentStatus.PUBLIC);
        int resultCount = reviewMapper.insert(review);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<PageInfo> list(Long productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Review> reviewList = reviewMapper.selectByProductId(productId);

        List<ReviewVo> reviewVoList = Lists.newArrayList();

        for (Review review : reviewList) {
            if (!review.getStatus().equals(Const.CommentStatus.DELETE)) {
                // 去掉处于删除状态的评论
                ReviewVo reviewVo = assembleReviewVo(review);
                reviewVoList.add(reviewVo);
            }
        }

        PageInfo pageResult = new PageInfo(reviewList);
        pageResult.setList(reviewVoList);
        return ResponseData.success(pageResult);
    }

    private ReviewVo assembleReviewVo(Review review) {
        User user = userMapper.selectByPrimaryKey(review.getUserId());
        if (user == null) {
            return null;
        }
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(review.getOrderItemId());
        if (orderItem == null) {
            return null;
        }

        User userForReview = new User();
        if (review.getStatus().equals(Const.CommentStatus.PRIVATE)) {
            // 匿名
            userForReview.setNickname("******");
        }
        if (review.getStatus().equals(Const.CommentStatus.PUBLIC)) {
            userForReview.setId(user.getId());
            userForReview.setNickname(user.getNickname());
        }
        userForReview.setAvatar(PropertiesUtil.getImageHost() + user.getAvatar());

        ReviewVo reviewVo = new ReviewVo();

        reviewVo.setId(review.getId());
        reviewVo.setAuthor(userForReview);
        reviewVo.setCreateTime(review.getCreateTime());
        reviewVo.setImages(review.getImages());
        reviewVo.setOrderCreateTime(orderItem.getCreateTime());
        reviewVo.setProductId(review.getProductId());
        reviewVo.setProductName(orderItem.getProductName());
        reviewVo.setStar(review.getStar());
        reviewVo.setStatus(review.getStatus());
        reviewVo.setUpdateTime(review.getUpdateTime());

        return reviewVo;
    }
}
