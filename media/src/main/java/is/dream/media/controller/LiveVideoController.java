package is.dream.media.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import is.dream.common.Result;
import is.dream.media.service.LiveVideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/2 10:28
 */
@RestController
@RequestMapping("/liveVideo")
public class LiveVideoController {

    @Autowired
    private LiveVideoBusinessService liveVideoBusinessService;

    @GetMapping("/addLiveVideo")
    public Result<Object> addLiveVideo(@RequestParam("id") String id, @RequestParam("orderBy") int orderBy) {
        return liveVideoBusinessService.save(id,orderBy);
    }

    @GetMapping("/getLiveVideoList")
    public Result<Object> getLiveVideoList() {
        return liveVideoBusinessService.getLiveVideoList();
    }

    @GetMapping("/getCurrentLiveVideo")
    public Result<Object> getCurrentLiveVideo() {
        return liveVideoBusinessService.getCurrentLiveVideo();
    }

    @GetMapping("/startLiveVideo")
    public Result<Object> startLiveVideo(@RequestParam("orderBy") int orderBy) throws JsonProcessingException{
        return liveVideoBusinessService.startLiveVideo(orderBy,true);
    }

    @GetMapping("/endLiveVideo")
    public Result<Object> endLiveVideo(){
        return liveVideoBusinessService.endLiveVideo();
    }
}
