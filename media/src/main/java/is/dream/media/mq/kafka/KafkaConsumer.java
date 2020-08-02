package is.dream.media.mq.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.dream.media.config.VideoConfig;
import is.dream.media.handler.ffmpeg.untils.MediaUtil;
import is.dream.media.service.AsyncService;
import is.dream.media.service.LiveVideoBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/2 8:44
 */
@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private LiveVideoBusinessService liveVideoBusinessService;

    @Autowired
    private VideoConfig videoConfig;

    @Autowired
    private AsyncService asyncService;

    @KafkaListener(topics = KafkaProducer.TOPIC_LIVE_VIDEO, groupId = KafkaProducer.TOPIC_GROUP_LIVE_VIDEO)
    public void liveVideo(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue((String) msg, Map.class);
            log.info("topic 消费了： Topic:" + topic + ",Message:" + msg);
            int orderBy = Integer.parseInt(map.get("orderBy"));
            asyncService.hlsLive(map.get("filePath"),videoConfig.getPushUrl(),orderBy);
            ack.acknowledge();
        }
    }

}
