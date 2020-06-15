package is.dream.media.handler.ffmpeg.entity;

import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 19:52
 */
@Data
public class MusicMetaInfo extends MetaInfo{

    /**
     * 音频时长 ,单位：毫秒
     */
    private Long duration;
    /**
     * 比特率，单位：Kb/s
     * 指音频每秒传送（包含）的比特数
     */
    private Integer bitRate;

    /**
     * 采样频率，单位：Hz
     * 指一秒钟内对声音信号的采样次数
     */
    private Long sampleRate;
}
