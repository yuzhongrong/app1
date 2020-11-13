package com.blockchain.common.base.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUploadHelper {
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadHelper.class);
    /**
     * 保存文件
     *
     * @param file        文件
     * @param relativeDir 文件类型
     * @throws IOException 存储异常
     */
    protected static String saveFile(MultipartFile file, String rootDir, String relativeDir) throws IOException {
        //源文件名
         LOG.info("准备上传文件:"+file);
        String name = file.getOriginalFilename();
        //文件后缀名
        String suffix = name.substring(name.lastIndexOf("."));
        //判断是否图片
        if (!isPicture(file.getInputStream())) {
            throw new RuntimeException("file is not picture");
        }
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
    protected static String generateImage(String imgBase64, String rootDir, String relativeDir) throws IOException {   //对字节数组字符串进行Base64解码并生成图片
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
     * 文件名命名规则
     *
     * @return 文件名
     */
    private static String createFileName() {
        String timeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +
                RandomStringUtils.random(6, true, true);
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

    /**
     * 判断是否图片文件
     */
    private static boolean isPicture(InputStream input) {
        try {
            Image image = ImageIO.read(input);
            return image != null;
        } catch (IOException ex) {
            return false;
        }
    }

}
