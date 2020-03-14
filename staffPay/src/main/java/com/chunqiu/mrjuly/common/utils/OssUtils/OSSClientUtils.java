package com.chunqiu.mrjuly.common.utils.OssUtils;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class OSSClientUtils {
    //阿里云API的内或外网域名
    private static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    private static String BACKET_NAME;
    //阿里云API的文件夹名称
    private static String IMAGES;

    private static String VIDEO;
    //初始化属性
    static{
        ENDPOINT = OSSClientConstants.ENDPOINT;
        ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
        BACKET_NAME = OSSClientConstants.BACKET_NAME;
        IMAGES = OSSClientConstants.IMAGES;
        VIDEO = OSSClientConstants.VIDEO;
    }

    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public static OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public  static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
        ossClient.deleteBucket(bucketName);
    }

    /**
     * 创建模拟文件夹
     * @param ossClient oss连接
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"qj_nanjing/"
     * @return  文件夹名
     */
    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
        //文件夹名
        final String keySuffixWithSlash =folder;
        //判断文件夹是否存在，不存在则创建
        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir=object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param bucketName  存储空间
     * @param folder  模拟文件夹名 如"qj_nanjing/"
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
        ossClient.deleteObject(bucketName, folder + key);
    }

    /**
     * 上传图片至OSS
     * @param ossClient  oss连接
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * */
    public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
        String resultStr = null;
        try {
            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            //文件名
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        String FilenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "application/x-bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xla") ||
                FilenameExtension.equalsIgnoreCase(".xlc")||
                FilenameExtension.equalsIgnoreCase(".xlm")||
                FilenameExtension.equalsIgnoreCase(".xls")||
                FilenameExtension.equalsIgnoreCase(".xlt")||
                FilenameExtension.equalsIgnoreCase(".xlw")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase(".tar")) {
            return "application/x-tar";
        }
        if (FilenameExtension.equalsIgnoreCase(".avi")) {
            return "video/avi";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp2")) {
            return "audio/mp2";
        }
        return "application/octet-stream";
    }

    //测试
    public static void main(String[] args) {
        //初始化OSSClient
        OSSClient ossClient = OSSClientUtils.getOSSClient();
        //上传文件
        String files = "C:\\Users\\Administrator\\Desktop\\c42f649f0af52dee318ba80743bf0689.mp4";
        String[] file=files.split(",");
        for(String filename:file){
            File filess = new File(filename);
            OSSClientUtils.uploadObject2OSS(ossClient, filess, BACKET_NAME, IMAGES);
            //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
            String temp[] = filename.replaceAll("\\\\","/").split("/");
            if (temp.length > 1) {
                filename = temp[temp.length - 1];
            }
            //图片url路径
            String url = "https://"+BACKET_NAME+"."+ENDPOINT+"/"+IMAGES+""+filename;
            System.out.println(url);
        }
    }


    //图片调用上传
    public static  String ossUploadPictures(String fileName){
        //初始化OSSClient
        OSSClient ossClient = OSSClientUtils.getOSSClient();
        //上传文件
        String files = fileName;
        String[] file = files.split(",");
        String url = "";
        for(String filename:file){
            String  filenameTwo = "";
            if (!"/images/default2.png".equals(filename)&&!filename.contains("http")){
                File filess = new File(filename);
                OSSClientUtils.uploadObject2OSS(ossClient, filess, BACKET_NAME, IMAGES);
                //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
                String temp[] = filename.replaceAll("\\\\","/").split("/");
                if (temp.length > 1) {
                    filenameTwo = temp[temp.length - 1];
                }
                //删除本地图片
                File f = new File(filename);
                f.delete();
                //图片url路径
                url += "https://"+BACKET_NAME+"."+ENDPOINT+"/"+IMAGES+""+filenameTwo;
                url += ",";
            }else {
                if (!"/images/default2.png".equals(filename)){
                    //图片url路径
                    url += filename;
                    url += ",";
                }
            }
        }
        url= url.substring(0,url.length() - 1);
        return url;
    }

}
