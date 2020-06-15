//package is.dream.media.controller;
//
//import is.dream.common.Result;
//import is.dream.common.exception.BusinessException;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
///**
// * @author chendongzhao
// * @version 1.0
// * @date 2020/6/15 20:33
// */
//@RestController
//@RequestMapping("/media")
//public class MediaController {
//
//    @PostMapping("/upload")
//    @ResponseBody
//    public Result<Object> upload(@ModelAttribute("file") MultipartFile file) throws BusinessException {
//
//        if (file == null || file.isEmpty()) {
//            throw new BusinessException("1","上传视频不可为空");
//        }
//
//        String originalFilename = file.getOriginalFilename();
//        Video video = new Video();
//        video.setvId(UUID.randomUUID().toString());
//        video.setvName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
//        video.setvSuffix(originalFilename.substring(originalFilename.lastIndexOf(".")));
//
//        // 判断文件类型
//        List<String> list = new ArrayList<>();
//        list.add(".mp4");
//        list.add(".rmvb");
//        list.add(".wmv");
//        list.add(".avi");
//        list.add(".mov");
//        list.add(".flv");
//        if (!list.contains(video.getvSuffix())) {
//            throw new FormatNotSupportedException("文件格式不正确");
//        }
//
//        File upload = new File(videoConfig.getLocalPath());
//        if (!upload.exists()) {
//            upload.mkdirs();
//        }
//        try {
//            file.transferTo(new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix()));
//            video.setUploadTime(new Date());
//            video.setvStatus(VideoStatus.UPLOAD_SUCCESS.toString()); // 状态: 上传成功
//            videoService.insert(video);
//        } catch (Exception e) {
//            // 删除不完整上传视频
//            log.warn("{}删除不完整视频中", video.getvId());
//            SystemUtils.deleteLocalFiles(new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix()));
//            if (e.getMessage().contains("Incorrect string value")) {
//                throw new UploadException("文件名有特殊字符 请修改后重新上传", e);
//            }
//            throw new UploadException("文件上传出错", e);
//        }
//
//        // 加入任务队列
//        transService.trans(video);
//        log.info("{}视频上传成功", video.getvId());
//        return new HttpResult(HttpStatus.OK.value(), "上传成功", "");
//    }
//}
