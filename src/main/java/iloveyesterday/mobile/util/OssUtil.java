package iloveyesterday.mobile.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

public final class OssUtil {

    private static OSSClient ossClient;

    private static String endpoint = PropertiesUtil.getProperty("aliyun.oss.endpoint");
    private static String accessKeyId = PropertiesUtil.getProperty("aliyun.oss.accessKeyId");
    private static String accessKeySecret = PropertiesUtil.getProperty("aliyun.oss.accessKeySecret");
    private static String bucketName = PropertiesUtil.getProperty("aliyun.oss.bucketName");

    private static OSSClient getOssClient() {
        if (ossClient == null) {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
        return ossClient;
    }

    public static void uploadFile(List<File> fileList) throws Exception {
        try {
            if (!getOssClient().doesBucketExist(bucketName)) {
                // 若Bucket不存在, 创建Bucket
                getOssClient().createBucket(bucketName);
            }

            for (File file : fileList) {
                // 文件存储入OSS，Object的名称为fileKey
                String fileKey = file.getName();
                getOssClient().putObject(bucketName, fileKey, file);
            }
        } catch (OSSException | ClientException e) {
            e.printStackTrace();
            throw new Exception("上传失败");
        }
    }

    public static void uploadFile(File targetFile) throws Exception {
        uploadFile(Lists.newArrayList(targetFile));
    }
}
