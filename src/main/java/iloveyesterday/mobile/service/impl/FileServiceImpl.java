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

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // 扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("uploading file,file name: {},path: {},uploaded file name: {}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);

        try {
            // 上传文件
            file.transferTo(targetFile);
            // 上传到阿里云OSS
            if (OssUtil.uploadFile(targetFile)) {
                // 删除暂存的图片
                targetFile.delete();
                log.info("file uploaded");
            }
        } catch (Exception e) {
            log.error("upload file error", e);
            return null;
        }
        return targetFile.getName();
    }

}
