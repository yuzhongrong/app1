package com.blockchain.server.user.common.utils;

import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUploadHelper {
    /**
     * 保存文件
     *
     * @param file        文件
     * @param relativeDir 文件类型
     * @throws IOException 存储异常
     */
    public static String saveFile(MultipartFile file, String rootDir, String relativeDir) throws IOException {
        //源文件名
        String name = file.getOriginalFilename();
        //文件后缀名
        String suffix = name.substring(name.lastIndexOf("."));
        //格式化文件路径
        rootDir = formatSeparator(rootDir);
        relativeDir = formatSeparator(relativeDir);
        //申诉文件保存到本地/服务器
        String fileRelativePath = generateRelativePath(rootDir, relativeDir, suffix);
        file.transferTo(new File(rootDir + fileRelativePath));
        return fileRelativePath;
    }

    /**
     * 将base64字符串存储为jpg文件
     *
     * @param imgBase64   base64字符串
     * @param relativeDir 文件相对路径
     * @return 文件名
     * @throws Exception 存储异常
     */
    public static String generateImage(String imgBase64, String rootDir, String relativeDir) throws IOException {   //对字节数组字符串进行Base64解码并生成图片
        if (StringUtils.isEmpty(imgBase64)) {
            throw new RuntimeException("file base64 string is empty");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] b = decoder.decodeBuffer(imgBase64);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {//调整异常数据
                b[i] += 256;
            }
        }
        //格式化文件路径
        rootDir = formatSeparator(rootDir);
        relativeDir = formatSeparator(relativeDir);
        //文件存放路径
        String relativePath = generateRelativePath(rootDir, relativeDir, ".jpg");
        OutputStream out = null;
        try {
            out = new FileOutputStream(rootDir + relativePath);
            out.write(b);
            out.flush();
            return relativePath;
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检验上传文件的格式，防止恶意插入数据
     * @param filereRelativePaths 多个文件路径，以「,」作为分隔符
     * @param count 总数
     */
    public static void verifyFileNameList(String filereRelativePaths,Integer count){
        String[] files = filereRelativePaths.split(",");
        int total = files.length;
        if(total == 0){
            throw new UserException(UserEnums.FILE_UPLOAD_DEFICIENCY);
        }
        if(count!= null && count != total){
            throw new UserException(UserEnums.FILE_UPLOAD_DEFICIENCY);
        }
        for (int i = 0; i < total; i++) {
            verifyFileName(files[i]);
        }
    }

    /**
     * 检验上传文件的格式，防止恶意插入数据
     * @param filereRelativePath 文件的相对路径
     */
    public static void verifyFileName(String filereRelativePath) {
        String regex = "\\d{25}";
        String separator = File.separator;
        int lastSeparatorIndex = filereRelativePath.lastIndexOf(separator);
        int lastPointIndex = filereRelativePath.lastIndexOf(".");
        if (lastSeparatorIndex == -1 || lastPointIndex == -1) {
            throw new UserException(UserEnums.FILE_UPLOAD_FORMAT_ERROR);
        }
        filereRelativePath = filereRelativePath.substring(lastSeparatorIndex + 1,lastPointIndex);
        if (!filereRelativePath.matches(regex)) {
            throw new UserException(UserEnums.FILE_UPLOAD_FORMAT_ERROR);
        }
    }

    /**
     * 文件名命名规则,时间戳+随机数
     *
     * @return 文件名
     */
    private static String createFileName() {
        String timeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +
                RandomStringUtils.random(8, false, true);
        return timeFormat;
    }

    /**
     * 生成文件所在目录，并返回文件的相对地址
     *
     * @param rootPath    文件根目录
     * @param relativeDir 文件相对目录
     * @param suffix      文件后缀名
     * @return 文件相对地址
     */
    private static String generateRelativePath(String rootPath, String relativeDir, String suffix) {
        String separator = File.separator;
        if (relativeDir.startsWith(separator)) {
            relativeDir = relativeDir.substring(1);
        }
        //格式化后缀
        if (!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        String fileDir = rootPath + relativeDir;
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return relativeDir + createFileName() + suffix;
    }

    /**
     * 格式化文件分隔符
     *
     * @param dir 目录
     * @return 格式化后的目录名
     */
    private static String formatSeparator(String dir) {
        String separator = File.separator;
        if (StringUtils.isNotEmpty(dir)) {
            String replace = separator.equals("\\") ? "\\\\" : "/";
            String format = dir.replaceAll("[\\\\/]+", replace);
            if (!format.endsWith(separator)) {
                format += separator;
            }
            return format;
        }
        return "";
    }
}
