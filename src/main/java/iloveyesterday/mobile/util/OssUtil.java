package iloveyesterday.mobile.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

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

    public static boolean uploadFile(List<File> fileList) throws Exception {
        if (StringUtils.isBlank(endpoint) ||
                StringUtils.isBlank(accessKeyId) ||
                StringUtils.isBlank(accessKeySecret)) {
            return false;
        }
        try {
            if (!getOssClient().doesBucketExist(bucketName)) {
                // 若Bucket不存在, 创建Bucket
                getOssClient().createBucket(bucketName);
            }

            String dir = PropertiesUtil.getProperty("aliyun.oss.dir", "");

            for (File file : fileList) {
                // 文件存储入OSS，Object的名称为fileKey
                String fileKey = file.getName();
                getOssClient().putObject(bucketName, dir + fileKey, file);
            }
            return true;
        } catch (OSSException | ClientException e) {
            e.printStackTrace();
            throw new Exception("upload failed");
        }
    }

    public static boolean uploadFile(File targetFile) throws Exception {
        return uploadFile(Lists.newArrayList(targetFile));
    }
}
