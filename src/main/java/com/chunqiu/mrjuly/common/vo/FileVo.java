package com.chunqiu.mrjuly.common.vo;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Description:
 * @Date: 2018/3/15
 * @Author: wcf
 */
public class FileVo {
    private MultipartFile file;     //上传的文件
    private File localFile;         //即将保存的本地文件
    private String fileName;        //文件名

    private String diskAddress;


    public String getDiskAddress() {
        return diskAddress;
    }

    public void setDiskAddress(String diskAddress) {
        this.diskAddress = diskAddress;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
