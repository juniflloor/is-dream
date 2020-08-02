package is.dream.media.mq.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/2 8:44
 */
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_LIVE_VIDEO = "topic.live.video";

    public static final String TOPIC_GROUP_LIVE_VIDEO = "group.live.video";

    public void send(Object obj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(obj);
        log.info("准备发送消息为：{}", jsonString);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_LIVE_VIDEO, jsonString);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(TOPIC_LIVE_VIDEO + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                log.info(TOPIC_LIVE_VIDEO + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }
}
