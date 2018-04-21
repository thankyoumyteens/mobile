package iloveyesterday.mobile.controller.home;

import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/home/")
public class HomeController {

    @RequestMapping("images.do")
    @ResponseBody
    public ResponseData images() {
        List<String> imgList = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            imgList.add(PropertiesUtil.getImageHost() + (i + 1) + ".jpg");
        }
        return ResponseData.success(imgList);
    }

    @RequestMapping("placeholder.do")
    @ResponseBody
    public ResponseData placeholder() {

        return ResponseData.success("iphone X");
    }
}
