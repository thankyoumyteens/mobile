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
     * 用户身份
     */
    interface Role {
        // 管理员
        int ADMIN = 0;
        // 普通用户
        int USER = 1;
    }
}
