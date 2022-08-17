package com.backend.makemoney.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {
  /** 默认大小 50M */
  public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

  /** 默认的文件名最大长度 100 */
  public static final int DEFAULT_FILE_NAME_LENGTH = 100;

  public static final String upload(String baseDir, MultipartFile file) throws IOException {
    // 得到文件名
    String name = file.getOriginalFilename();
    UUID uuid = UUID.randomUUID();
    String str = uuid.toString();
    // 去掉"-"符号
    String fileName =
        str.substring(0, 8)
            + str.substring(9, 13)
            + str.substring(14, 18)
            + str.substring(19, 23)
            + str.substring(24)
            + name.substring(name.lastIndexOf('.'));
    ;
    // 设置文件存储路径
    String path = baseDir + fileName;
    File dest = new File(path);
    // 检测是否存在该目录
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdirs();
    }
    // 写入文件
    file.transferTo(dest);
    return fileName;
  }
}
