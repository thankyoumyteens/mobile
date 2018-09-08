package iloveyesterday.mobile.controller.portal;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseCode;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Favorite;
import iloveyesterday.mobile.pojo.User;
import iloveyesterday.mobile.service.IFavoriteService;
import iloveyesterday.mobile.util.LoginUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/favorite/")
public class FavoriteController {

    @Resource
    private IFavoriteService favoriteService;

    /**
     * 添加收藏
     *
     * @param request
     * @param favorite
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ResponseData addToFavorite(HttpServletRequest request, Favorite favorite) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        Long userId = user.getId();
        favorite.setUserId(userId);
        return favoriteService.add(favorite);
    }

    @RequestMapping("count.do")
    @ResponseBody
    public ResponseData count(HttpServletRequest request, int type) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return favoriteService.count(user.getId(), type);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ResponseData<PageInfo> list(
            HttpServletRequest request, int type,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = LoginUtil.getCurrentUser(request);
        if (user == null) {
            return ResponseData.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        return favoriteService.list(user.getId(), type, pageNum, pageSize);
    }

}
