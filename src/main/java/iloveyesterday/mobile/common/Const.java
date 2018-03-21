package iloveyesterday.mobile.common;

public interface Const {

    String CURRENT_USER = "currentUser";
    String CURRENT_ADMIN = "currentAdmin";

    String EMAIL = "email";
    String USERNAME = "username";

    interface Role {
        int ADMIN = 0;
        int USER = 1;
    }
}
