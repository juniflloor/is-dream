package is.dream.media.controller;

import is.dream.common.utils.TimeUtils;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.Video;
import is.dream.dao.inter.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/27 16:19
 */
@RestController
@RequestMapping("/videoDuration")
public class VideoDurationController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoDao videoDao;

    @GetMapping("/upload")
    public void videoDuration(){

        List<Video> videoList = videoService.getMostViewVideo();
        videoList.forEach(video ->{

            long tempDuration = 0l;
            try{
                tempDuration =  Long.parseLong(video.getDuration());
            } catch (Exception e) {
                return;
            }
            String duration = TimeUtils.formatDateTime(tempDuration);
            videoDao.updateDuration(duration,video.getId());
        });
    }
}
