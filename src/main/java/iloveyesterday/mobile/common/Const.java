package iloveyesterday.mobile.common;

public interface Const {

    // session key
    String CURRENT_USER = "currentUser";
    String CURRENT_ADMIN = "currentAdmin";

    String EMAIL = "email";
    String USERNAME = "username";

    interface Directory {
        String USER = "user";
        String CATEGORY = "category";
        String PRODUCT = "product";
    }

    /**
     * 商品状态
     */
    interface ProductStatus {
        // 正常
        int ON_SALE = 1;
        // 下架
        int NOT_ON_SALE = 2;
        // 删除
        int DELETE = 3;
    }

    /**
     * 商品评论状态
     */
    interface ReviewStatus {
        // 公开
        int PUBLIC = 1;
        // 匿名
        int PRIVATE = 2;
        // 删除
        int DELETE = 3;
    }

    /**
     * 购物车状态
     */
    interface CartStatus {
        // 选中
        Long CHECKED = 1L;
        // 未选中
        Long UNCHECKED = 0L;
    }

    /**
     * 订单状态
     */
    interface OrderStatus {
        // 已取消
        int CANCELED = 0;
        // 未付款
        int NOT_PAY = 10;
        // 已付款
        int PAYED = 20;
        // 已发货
        int SENT = 40;
        // 交易成功
        int SUCCESS = 50;
        // 交易关闭
        int CLOSED = 60;
    }

    /**
     * 付款方式
     */
    interface PaymentType {
        // 在线支付
        int ONLINE = 1;
    }

    /**
     * 用户身份
     */
    interface Role {
        // 管理员
        int ADMIN = 0;
        // 普通用户
        int USER = 1;
    }
}
