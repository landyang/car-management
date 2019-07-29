package com.example.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author HaN
 * @create 2019-04-09 20:35
 */
public class FileUtil {
    /**
     * 文件上传（抛出异常）
     *
     * @param file     文件流
     * @param filePath 存储路径
     * @param fileName 文件名称
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 删除文件
     *
     * @param fileAbsolutePath 文件绝对路径
     */
    public static boolean removeFile(String fileAbsolutePath) {
        File file = new File(fileAbsolutePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //删除文件成功
                return true;
            } else {
                //删除文件失败
                return false;
            }
        } else {
            //删除文件失败，文件不存在
            return false;
        }
    }

    /**
     * 获取UUID通用唯一识别码名称
     *
     * @param originalFileName
     */
    public static String getUUIDName(String originalFileName) {
        String id = UUID.randomUUID().toString().replace("-", "");
        String fileName = id + originalFileName.substring(originalFileName.lastIndexOf("."));
        return fileName;
    }
}
