package iloveyesterday.mobile.service.impl;

import iloveyesterday.mobile.service.IFileService;
import iloveyesterday.mobile.util.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service("fileService")
@Slf4j
public class FileServiceImpl implements IFileService {

//    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // 扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);

        try {
            // 上传文件
            file.transferTo(targetFile);
//             上传到ftp服务器
//            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 上传到阿里云OSS
            OssUtil.uploadFile(targetFile);
            // 删除暂存的图片
            targetFile.delete();
        } catch (Exception e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

}
