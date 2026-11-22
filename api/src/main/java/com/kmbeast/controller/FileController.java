package com.kmbeast.controller;

import com.kmbeast.utils.PathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件前端控制器
 * 提供文件上传和下载功能
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final String UPLOAD_DIR = "/file"; // 保存路径
    private static final String FILE_PARAM = "file"; // 上传文件名
    private static final String FILE_NAME_PARAM = "fileName"; // 文件查询参数名

    @Value("${my-server.api-context-path}")
    private String apiContextPath;

    /**
     * 文件上传
     *
     * @param multipartFile 文件流
     * @return 响应结果
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam(FILE_PARAM) MultipartFile multipartFile) {
        return handleFileUpload(multipartFile);
    }

    /**
     * 查看文件资源
     *
     * @param fileName 文件名
     * @param response HTTP响应
     * @throws IOException 文件操作异常
     */
    @GetMapping("/getFile")
    public void getFile(@RequestParam(FILE_NAME_PARAM) String fileName,
                        HttpServletResponse response) throws IOException {
        File file = getUploadedFile(fileName);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * 处理文件上传逻辑
     */
    private Map<String, Object> handleFileUpload(MultipartFile multipartFile) {
        Map<String, Object> response = new HashMap<>();
        if (multipartFile.isEmpty()) {
            response.put("code", 400);
            response.put("msg", "文件不能为空");
            return response;
        }

        long currentTimeMillis = System.currentTimeMillis(); // 取当前时间戳作为文件名
        String fileName = currentTimeMillis + multipartFile.getOriginalFilename();
        try {
            if (saveUploadedFile(multipartFile, fileName)) {
                response.put("code", 200);
                response.put("data", apiContextPath + "/file/getFile?" + FILE_NAME_PARAM + "=" + fileName);
                return response;
            }
        } catch (IOException e) {
            response.put("code", 500);
            response.put("msg", "文件上传失败");
            return response;
        }

        response.put("code", 500);
        response.put("msg", "文件上传失败");
        return response;
    }

    /**
     * 保存上传的文件
     */
    private boolean saveUploadedFile(MultipartFile multipartFile, String fileName) throws IOException {
        File uploadDir = new File(PathUtils.getClassLoadRootPath() + UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return false;
        }

        File destFile = new File(uploadDir, fileName);
        // 如果文件已存在，先删除
        if (destFile.exists() && !destFile.delete()) {
            return false;
        }

        multipartFile.transferTo(destFile);
        return true;
    }

    /**
     * 获取已上传的文件
     */
    private File getUploadedFile(String fileName) {
        File uploadDir = new File(PathUtils.getClassLoadRootPath() + UPLOAD_DIR);
        return new File(uploadDir, fileName);
    }
}