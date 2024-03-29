package com.chunqiu.mrjuly.modules.basic;

import com.chunqiu.mrjuly.common.enums.RedisKeyCode;
import com.chunqiu.mrjuly.common.utils.CacheRedisUtil;
import com.chunqiu.mrjuly.common.utils.DateUtils;
import com.chunqiu.mrjuly.common.utils.PathUtil;
import com.chunqiu.mrjuly.common.utils.ueditor.PublicMsg;
import com.chunqiu.mrjuly.common.vo.FileVo;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.common.vo.Ueditor;
import com.chunqiu.mrjuly.common.annotation.Log;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RequestMapping("${adminPath}/upload")
@Controller
public class UploadController {
    @Value("${ueditor.imageUrlPrefix}")
    private String imageUrlPrefix;
    @Autowired
    private CacheRedisUtil redisUtil;

    /**
     * 上传图片
     * @param request
     * @return
     */
    @Log("上传图片")
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public Json uploadImg(HttpServletRequest request) {
        Json json = new Json();
        json.setSuccess(false);

        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {

                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 5) {
                        json.setMsg("图片大小不得超过5Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")) {
                            json.setMsg("图片格式不正确");
                            return json;
                        } else {
                            String newFileName = UUID.randomUUID().toString();
                            String newFilePath = path + "/" + newFileName + extenstion;
                            File localFile = new File(newFilePath);

                            FileVo vo = new FileVo();
                            vo.setFile(file);
                            vo.setLocalFile(localFile);
                            vo.setFileName(source + "/" + date + "/" + newFileName + extenstion);

                            fileList.add(vo);
                        }
                    }
                }
            }

            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());

                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传图片");
        return json;
    }


    /**
     * 上传图片
     * @param request
     * @return
     */
    @Log("上传图片")
    @RequestMapping(value = "/uploadImg5", method = RequestMethod.POST)
    @ResponseBody
    public Json uploadImg5(HttpServletRequest request) throws IOException {
        Json json = new Json();
        json.setSuccess(false);

        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {

                MultipartFile file = multiRequest.getFile(iter.next());

                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 5) {
                        json.setMsg("图片大小不得超过5Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")) {
                            json.setMsg("图片格式不正确");
                            return json;
                        } else {
                            BufferedImage bufferedImage =ImageIO.read(file.getInputStream()); // 通过MultipartFile得到InputStream，从而得到BufferedImage
                            Integer width = bufferedImage.getWidth();
                            Integer height = bufferedImage.getHeight();
                           /* if (!Objects.equals(width,height)){
                                json.setMsg("图片宽高比例必须为350:350!");
                                return json;
                            }*/

                            String newFileName = UUID.randomUUID().toString();
                            String newFilePath = path + "/" + newFileName + extenstion;
                            File localFile = new File(newFilePath);

                            FileVo vo = new FileVo();
                            vo.setFile(file);
                            vo.setLocalFile(localFile);
                            vo.setFileName(source + "/" + date + "/" + newFileName + extenstion);

                            fileList.add(vo);
                        }
                    }
                }
            }

            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());

                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传图片");
        return json;
    }

    @RequestMapping(value="/config")
    @ResponseBody
    public String config() {
        return PublicMsg.UEDITOR_CONFIG;
    }

    @RequestMapping(value="/imgUpload")
    @ResponseBody
    public Ueditor imgUpload(HttpServletRequest request) {
        Json json = this.uploadImg(request);

        Ueditor ueditor = new Ueditor();
        if(json.success){
            ueditor.setState("SUCCESS");
            String fileName = ((List<String>)json.getData()).get(0);
            ueditor.setUrl(imageUrlPrefix + fileName);
            ueditor.setTitle(fileName);
            ueditor.setOriginal(fileName);
        }else {
            ueditor.setState("ERROR");
        }
        return ueditor;
    }



    /**************************1.5版本**************************/
    /**
     * 上传图片
     * @param request
     * @return
     */
    @Log("上传图片")
    @RequestMapping(value = "/ossUploadImg", method = RequestMethod.POST)
    @ResponseBody
    public Json ossUploadImg(HttpServletRequest request) throws IOException {
        Json json = new Json();
        json.setSuccess(false);

        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {

                MultipartFile file = multiRequest.getFile(iter.next());

                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 5) {
                        json.setMsg("图片大小不得超过5Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")) {
                            json.setMsg("图片格式不正确");
                            return json;
                        } else {
                            String newFileName = UUID.randomUUID().toString();
                            String newFilePath = path + "/" + newFileName + extenstion;
                            File localFile = new File(newFilePath);
                            FileVo vo = new FileVo();
                            vo.setFile(file);
                            vo.setLocalFile(localFile);
                            vo.setDiskAddress(source + "/" + date + "/" + newFileName + extenstion);
                            vo.setFileName(newFilePath);
                            fileList.add(vo);
                        }
                    }
                }
            }

            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());
                        src.add(vo.getDiskAddress());
                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传图片");
        return json;
    }


    /**
     * 上传图片或者视频
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/springUpload",method = RequestMethod.POST)
    @ResponseBody
    public Json springUpload(HttpServletRequest request) throws Exception {
        int type = 0;
        Json json = new Json();
        json.setSuccess(false);
        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String result = null;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<FileVo>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 鐢盋ommonsMultipartFile缁ф壙鑰屾潵,鎷ユ湁涓婇潰鐨勬柟娉�
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 50) {
                        json.setMsg("文件大小不得超过50Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        if (extenstion.equals(".jpg") || extenstion.equals(".jpeg") || extenstion.equals(".bmp") || extenstion.equals(".png")){
                            //上传文件是图片
                            type = 1;
                        }else if (extenstion.equals(".rm")||extenstion.equals(".rmvb")||extenstion.equals(".mov")||extenstion.equals(".mtv")||extenstion.equals(".avi")||extenstion.equals(".3gp")||extenstion.equals(".amv")||extenstion.equals(".dmv")||extenstion.equals(".flv")||extenstion.equals(".mp4")){
                            //上传文件是视频
                            type = 2;
                        }
                        json.setType(type);
                        if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")&&!extenstion.equals(".rm")&&!extenstion.equals(".rmvb")&&!extenstion.equals(".mov")&&!extenstion.equals(".mtv")&&!extenstion.equals(".avi")&&!extenstion.equals(".3gp")&&!extenstion.equals(".amv")&&!extenstion.equals(".dmv")&&!extenstion.equals(".flv")&&!extenstion.equals(".mp4")) {
                            json.setMsg("文件格式不正确");
                            return json;
                        } else {
                            String newFileName = UUID.randomUUID().toString();
                            String newFilePath = path + "/" + newFileName + extenstion;
                            File localFile = new File(newFilePath);
                            FileVo vo = new FileVo();
                            vo.setFile(file);
                            vo.setLocalFile(localFile);
                            vo.setDiskAddress(source + "/" + date + "/" + newFileName + extenstion);
                            if (type == 2){
                                if (newFilePath.contains("data")){
                                    vo.setFileName(source + "/" + date + "/" + newFileName + extenstion);
                                }else {
                                    vo.setFileName(newFilePath);
                                }
                            }else {
                                vo.setFileName(newFilePath);
                            }
                            fileList.add(vo);
                        }
                    }
                }
            }

            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<String>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());
                        src.add(vo.getDiskAddress());
                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传图片或者视频！");
        return json;
    }

    /**
     * 上传视频
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/springUploadVideo",method = RequestMethod.POST)
    @ResponseBody
    public Json springUploadVideo(HttpServletRequest request) throws Exception {
        Json json = new Json();
        json.setSuccess(false);
        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String result = null;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<FileVo>();

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 50) {
                        json.setMsg("文件大小不得超过50Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        if (!extenstion.equals(".rm")&&!extenstion.equals(".rmvb")&&!extenstion.equals(".mov")&&!extenstion.equals(".mtv")&&!extenstion.equals(".avi")&&!extenstion.equals(".3gp")&&!extenstion.equals(".amv")&&!extenstion.equals(".dmv")&&!extenstion.equals(".flv")&&!extenstion.equals(".mp4")) {
                            json.setMsg("请上传视频！");
                            return json;
                        } else {
                            String newFileName = UUID.randomUUID().toString();
                            String newFilePath = path + "/" + newFileName + extenstion;
                            File localFile = new File(newFilePath);
                            FileVo vo = new FileVo();
                            vo.setFile(file);
                            vo.setLocalFile(localFile);
                            vo.setDiskAddress(source + "/" + date + "/" + newFileName + extenstion);
                            if (newFilePath.contains("data")){
                                vo.setFileName(source + "/" + date + "/" + newFileName + extenstion);
                            }else {
                                vo.setFileName(newFilePath);
                            }
                            fileList.add(vo);
                        }
                    }
                }
            }

            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<String>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());
                        src.add(vo.getDiskAddress());
                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传图片或者视频！");
        return json;
    }


    /**
     * 删除文件
     * @param file
     */
    @RequestMapping(value="/deleteFile")
    @ResponseBody
    public void deleteFile(String file) {
        if (file != null){
            File f = new File(file);
            f.delete();
        }
    }


    /**
     * 广告上传广告
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/springUploadAll",method = RequestMethod.POST)
    @ResponseBody
    public Json springUploadAll(HttpServletRequest request) throws Exception {
        int type = 0;
        Json json = new Json();
        json.setSuccess(false);
        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String result = null;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<FileVo>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String oldFileName = file.getOriginalFilename();
                    String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                    if (extenstion.equals(".jpg") || extenstion.equals(".jpeg") || extenstion.equals(".bmp") || extenstion.equals(".png")){
                        //上传文件是图片
                        type = 1;
                        Integer size = redisUtil.getByClass(RedisKeyCode.UPLOAD_ADVERTISEMENT_P_SIZE, Integer.class);
                        size = size == null ? 10 : size;
                        if (file.getSize() > 1024 * 1024 * size) {
                            json.setMsg("图片大小不得超过" + size + "Mb");
                            return json;
                        }
                    }else if (extenstion.equals(".rm")||extenstion.equals(".rmvb")||extenstion.equals(".mov")||extenstion.equals(".mtv")||extenstion.equals(".avi")||extenstion.equals(".3gp")||extenstion.equals(".amv")||extenstion.equals(".dmv")||extenstion.equals(".flv")||extenstion.equals(".mp4")){
                        //上传文件是视频
                        type = 2;
                        Integer size = redisUtil.getByClass(RedisKeyCode.UPLOAD_ADVERTISEMENT_V_SIZE, Integer.class);
                        size = size == null ? 10 : size;
                        if (file.getSize() > 1024 * 1024 * size) {
                            json.setMsg("视频大小不得超过" + size + "Mb");
                            return json;
                        }
                    }
                    json.setType(type);
                    if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")&&!extenstion.equals(".rm")&&!extenstion.equals(".rmvb")&&!extenstion.equals(".mov")&&!extenstion.equals(".mtv")&&!extenstion.equals(".avi")&&!extenstion.equals(".3gp")&&!extenstion.equals(".amv")&&!extenstion.equals(".dmv")&&!extenstion.equals(".flv")&&!extenstion.equals(".mp4")) {
                        json.setMsg("文件格式不正确");
                        return json;
                    }
                    String newFileName = UUID.randomUUID().toString();
                    String newFilePath = path + "/" + newFileName + extenstion;
                    File localFile = new File(newFilePath);
                    FileVo vo = new FileVo();
                    vo.setFile(file);
                    vo.setLocalFile(localFile);
                    vo.setDiskAddress(source + "/" + date + "/" + newFileName + extenstion);
                    if (type == 2){
                        if (newFilePath.contains("data")){
                            vo.setFileName(source + "/" + date + "/" + newFileName + extenstion);
                        }else {
                            vo.setFileName(newFilePath);
                        }
                    }else {
                        vo.setFileName(newFilePath);
                    }
                    fileList.add(vo);
                }
            }

            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<String>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());
                        src.add(vo.getDiskAddress());
                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传文件");
        return json;
    }


    /**
     * 上传图片
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ossSpringUpload",method = RequestMethod.POST)
    @ResponseBody
    public Json ossSpringUpload(HttpServletRequest request) throws Exception {
        int type = 0;
        Json json = new Json();
        json.setSuccess(false);
        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String result = null;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            List<FileVo> fileList = new ArrayList<FileVo>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    Integer size = redisUtil.getByClass(RedisKeyCode.UPLOAD_ADVERTISEMENT_P_SIZE, Integer.class);
                    size = size == null ? 10 : size;
                    if (file.getSize() > 1024 * 1024 * size) {
                        json.setMsg("图片大小不得超过" + size + "Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        type = 1;
                        json.setType(type);
                        if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")) {
                            json.setMsg("文件格式不正确,请上传图片");
                            return json;
                        }
                        String newFileName = UUID.randomUUID().toString();
                        String newFilePath = path + "/" + newFileName + extenstion;
                        File localFile = new File(newFilePath);
                        FileVo vo = new FileVo();
                        vo.setFile(file);
                        vo.setLocalFile(localFile);
                        vo.setDiskAddress(source + "/" + date + "/" + newFileName + extenstion);
                        vo.setFileName(newFilePath);
                        fileList.add(vo);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<String>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());
                        src.add(vo.getDiskAddress());
                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传图片");
        return json;
    }


    @RequestMapping(value = "/ossSpringUpload1",method = RequestMethod.POST)
    @ResponseBody
    public Json ossSpringUpload1(HttpServletRequest request) throws Exception {
        int type = 0;
        Json json = new Json();
        json.setSuccess(false);
        String source = "/uploadimages/";
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String result = null;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            List<FileVo> fileList = new ArrayList<FileVo>();//用于存储保存的文件对象，当多图上传时，先检测所有图片后，再保存图片文件

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    Integer size = redisUtil.getByClass(RedisKeyCode.UPLOAD_ADVERTISEMENT_V_SIZE, Integer.class);
                    size = size == null ? 10 : size;
                    if (file.getSize() > 1024 * 1024 * size) {
                        json.setMsg("视频大小不得超过" + size + "Mb");
                        return json;
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();//获取后缀名
                        type = 2;
                        json.setType(type);
                        if (!extenstion.equals(".rm")&&!extenstion.equals(".rmvb")&&!extenstion.equals(".mov")&&!extenstion.equals(".mtv")&&!extenstion.equals(".avi")&&!extenstion.equals(".3gp")&&!extenstion.equals(".amv")&&!extenstion.equals(".dmv")&&!extenstion.equals(".flv")&&!extenstion.equals(".mp4")) {
                            json.setMsg("文件格式不正确,请上传视频");
                            return json;
                        }
                        String newFileName = UUID.randomUUID().toString();
                        String newFilePath = path + "/" + newFileName + extenstion;
                        File localFile = new File(newFilePath);
                        FileVo vo = new FileVo();
                        vo.setFile(file);
                        vo.setLocalFile(localFile);
                        vo.setDiskAddress(source + "/" + date + "/" + newFileName + extenstion);
                        if (newFilePath.contains("data")){
                            vo.setFileName(source + "/" + date + "/" + newFileName + extenstion);
                        }else {
                            vo.setFileName(newFilePath);
                        }
                        fileList.add(vo);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(fileList)){
                List<String> src = new ArrayList<String>();
                for (FileVo vo : fileList){
                    try {
                        vo.getFile().transferTo(vo.getLocalFile());
                        src.add(vo.getDiskAddress());
                        src.add(vo.getFileName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                json.setData(src);
                json.setSuccess(true);
                return json;
            }
        }
        json.setMsg("请上传视频");
        return json;
    }

}
