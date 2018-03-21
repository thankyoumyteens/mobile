package iloveyesterday.mobile.controller.home;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home/")
public class HomeManageController {

    @RequestMapping("index.do")
    public String index(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return "login";
        }
        return "index";
    }

    @RequestMapping("login.do")
    public String login() {
        return "login";
    }

    @RequestMapping("categories.do")
    public String categories(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return "login";
        }
        return "categories";
    }

    @RequestMapping("category.do")
    public String category(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_ADMIN);
        if (user == null) {
            return "login";
        }
        return "category";
    }
}
