package is.dream.media.handler.ffmpeg.untils;

import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.handler.ffmpeg.entity.MusicMetaInfo;
import is.dream.media.handler.ffmpeg.entity.VideoMetaInfo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 19:56
 */
public class MediaUtil {

    /**
     * 可以处理的视频格式
     */
    public final static String[] VIDEO_TYPE = { "MP4", "WMV" };
    /**
     * 可以处理的图片格式
     */
    public final static String[] IMAGE_TYPE = { "JPG", "JPEG", "PNG", "GIF" };


    /**
     *  视频帧抽取时的默认时间点，第10s（秒）
     * （Time类构造参数的单位:ms）
     */
    private static final Time DEFAULT_TIME = new Time(0, 0, 10);
    /**
     * 视频帧抽取的默认宽度值，单位：px
     */
    private static int DEFAULT_WIDTH = 320;
    /**
     * 视频帧抽取的默认时长，单位：s（秒）
     */
    private static int DEFAULT_TIME_LENGTH = 10;
    /**
     * 抽取多张视频帧以合成gif动图时，gif的播放速度
     */
    private static int DEFAULT_GIF_PLAYTIME = 110;
    /**
     * 视频时长正则匹配式
     * 用于解析视频及音频的时长等信息时使用；
     *
     * (.*?)表示：匹配任何除\r\n之外的任何0或多个字符，非贪婪模式
     *
     */
    private static String durationRegex = "Duration: (\\d*?):(\\d*?):(\\d*?)\\.(\\d*?), start: (.*?), bitrate: (\\d*) kb\\/s.*";
    private static Pattern durationPattern;
    /**
     * 视频流信息正则匹配式
     * 用于解析视频详细信息时使用；
     */
    private static String videoStreamRegex = "Stream #\\d:\\d[\\(]??\\S*[\\)]??: Video: (\\S*\\S$?)[^\\,]*, (.*?), (\\d*)x(\\d*)[^\\,]*, (\\d*) kb\\/s, (\\d*[\\.]??\\d*) fps";
    private static Pattern videoStreamPattern;
    /**
     * 音频流信息正则匹配式
     * 用于解析音频详细信息时使用；
     */
    private static String musicStreamRegex = "Stream #\\d:\\d[\\(]??\\S*[\\)]??: Audio: (\\S*\\S$?)(.*), (.*?) Hz, (.*?), (.*?), (\\d*) kb\\/s";;
    private static Pattern musicStreamPattern;

    /**
     * 静态初始化时先加载好用于音视频解析的正则匹配式
     */
    static {
        durationPattern = Pattern.compile(durationRegex);
        videoStreamPattern = Pattern.compile(videoStreamRegex);
        musicStreamPattern = Pattern.compile(musicStreamRegex);
    }


    //ffmpeg -i 1.mp4 -vcodec libx264 -acodec mp3 -map 0 -f ssegment -segment_format mpegts -segment_list playlist.m3u8 -segment_time 10 D:\test\out%03d.ts
    public static void convertM3u8(File sourceFile, File videoFile, File imageFile, String outName){


        String format = getFormat(sourceFile);
        if (!isLegalFormat(format, VIDEO_TYPE)) {
            throw new RuntimeException("无法解析的视频格式：" + format);
        }
        try {
            List<String> commond = new ArrayList<String>();
            commond.add("ffmpeg");
            commond.add("-i");
            commond.add(sourceFile.getAbsolutePath());
            commond.add("-vcodec"); // 指定输出视频文件时使用的编码器
            commond.add("libx264"); // 指定使用x264编码器
            commond.add("-acodec");
            commond.add("mp3");
            commond.add("-map");
            commond.add("0");
            commond.add("-f");
            commond.add("ssegment");
            commond.add("-segment_format");
            commond.add("mpegts");
            commond.add("-segment_list");
            commond.add(videoFile.getAbsolutePath() + "\\" + outName + ".m3u8");
            commond.add("-segment_time");
            commond.add("5");
            commond.add(videoFile.getAbsolutePath() + "\\" + outName + "%03d.ts");
            executeCommand(commond);
        }catch (Exception e) {
            SystemUtils.deleteLocalFiles(sourceFile);
            SystemUtils.deleteLocalFiles(videoFile);
            SystemUtils.deleteLocalFiles(imageFile);
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_TRANS_TARGET_FAIL);
        }
    }

    /**
     * 执行FFmpeg命令
     * @param commonds 要执行的FFmpeg命令
     * @return FFmpeg程序在执行命令过程中产生的各信息，执行出错时返回null
     */
    public static String executeCommand(List<String> commonds) {
        if (CollectionUtils.isEmpty(commonds)) {
            return null;
        }
        LinkedList<String> ffmpegCmds = new LinkedList<>(commonds);
        String cmdStr = Arrays.toString(ffmpegCmds.toArray()).replace(",", "");
        System.out.println("执行命令 ========》" + cmdStr);
        Runtime runtime = Runtime.getRuntime();
        Process ffmpeg = null;
        try {
            // 执行ffmpeg指令
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(ffmpegCmds);
            ffmpeg = builder.start();
            // 取出输出流和错误流的信息
            // 注意：必须要取出ffmpeg在执行命令过程中产生的输出信息，如果不取的话当输出流信息填满jvm存储输出留信息的缓冲区时，线程就回阻塞住
            PrintStream errorStream = new PrintStream(ffmpeg.getErrorStream());
            PrintStream inputStream = new PrintStream(ffmpeg.getInputStream());
            errorStream.start();
            inputStream.start();
            // 等待ffmpeg命令执行完
            ffmpeg.waitFor();

            // 获取执行结果字符串
            String result = errorStream.stringBuffer.append(inputStream.stringBuffer).toString();

            // 输出执行的命令信息
            String resultStr = StringUtils.isEmpty(result) ? "【异常】" : "正常";
            System.out.println("执行结果 ========》" + resultStr);
            return result;

        } catch (Exception e) {
            return null;

        } finally {
            if (null != ffmpeg) {
                ProcessKiller ffmpegKiller = new ProcessKiller(ffmpeg);
                // JVM退出时，先通过钩子关闭FFmepg进程
                runtime.addShutdownHook(ffmpegKiller);
            }
        }
    }

    /**
     * 视频帧抽取（抽取指定时间点、指定宽度值的帧画面）
     * 只需指定视频帧的宽度，高度随宽度自动计算
     *
     * 转换后的文件路径以.gif结尾时，默认截取从指定时间点开始，后10s以内的帧画面来生成gif
     *
     * @param sourceFile 源视频路径
     * @param targetFile 转换后的文件路径
     */
    public static VideoMetaInfo cutVideoFrame(File sourceFile, File targetFile, String startTime, String fileName,int width,int high) {

        String fileOutPutPath = targetFile.getAbsolutePath();
        // 输出路径不是以.gif结尾，抽取并生成一张静态图
        List<String> commond = new ArrayList<String>();
        commond.add("ffmpeg");
        commond.add("-i");
        commond.add(sourceFile.getAbsolutePath());
        commond.add("-ss");
        commond.add(startTime);
        commond.add("-t");
        commond.add("1");
        commond.add("-r");
        commond.add("1");
        commond.add("-q:v");
        commond.add("2");
        commond.add("-s");
        commond.add(width + "*" + high);
        commond.add("-f");
        commond.add("image2");
        commond.add(fileOutPutPath + "\\"+ fileName + ".jpg");
        String parseResult = executeCommand(commond);
        VideoMetaInfo videoMetaInfo = getVideoMetaInfo(parseResult,sourceFile);
        return videoMetaInfo;

    }

    /**
     * 解析视频的基本信息（从文件中）
     *
     * 解析出的视频信息一般为以下格式：
     * Input #0, mov,mp4,m4a,3gp,3g2,mj2, from '6.mp4':
     * Duration: 00:00:30.04, start: 0.000000, bitrate: 19031 kb/s
     * Stream #0:0(eng): Video: h264 (Main) (avc1 / 0x31637661), yuv420p(tv, bt709), 1920x1080, 18684 kb/s, 25 fps, 25 tbr, 25k tbn, 50 tbc (default)
     * Stream #0:1(eng): Audio: aac (LC) (mp4a / 0x6134706D), 48000 Hz, stereo, fltp, 317 kb/s (default)
     *
     * 注解：
     * Duration: 00:00:30.04【视频时长】, start: 0.000000【视频开始时间】, bitrate: 19031 kb/s【视频比特率/码率】
     * Stream #0:0(eng): Video: h264【视频编码格式】 (Main) (avc1 / 0x31637661), yuv420p(tv, bt709), 1920x1080【视频分辨率，宽x高】, 18684【视频比特率】 kb/s, 25【视频帧率】 fps, 25 tbr, 25k tbn, 50 tbc (default)
     * Stream #0:1(eng): Audio: aac【音频格式】 (LC) (mp4a / 0x6134706D), 48000【音频采样率】 Hz, stereo, fltp, 317【音频码率】 kb/s (default)
     *
     * @param parseResult 需要解析的字符串
     * @return 视频的基本信息，解码失败时返回null
     */
    public static VideoMetaInfo getVideoMetaInfo(String parseResult, File videoFile) {

        Matcher durationMacher = durationPattern.matcher(parseResult);
        Matcher videoStreamMacher = videoStreamPattern.matcher(parseResult);
        Matcher videoMusicStreamMacher = musicStreamPattern.matcher(parseResult);

        Long duration = 0L; // 视频时长
        Integer videoBitrate = 0; // 视频码率
        String videoFormat = getFormat(videoFile); // 视频格式
        Long videoSize = videoFile.length(); // 视频大小

        String videoEncoder = ""; // 视频编码器
        Integer videoHeight = 0; // 视频高度
        Integer videoWidth = 0; // 视频宽度
        Float videoFramerate = 0F; // 视频帧率

        String musicFormat = ""; // 音频格式
        Long samplerate = 0L; // 音频采样率
        Integer musicBitrate = 0; // 音频码率


            // 匹配视频播放时长等信息
        if (durationMacher.find()) {
            long hours = (long)Integer.parseInt(durationMacher.group(1));
            long minutes = (long)Integer.parseInt(durationMacher.group(2));
            long seconds = (long)Integer.parseInt(durationMacher.group(3));
            long dec = (long)Integer.parseInt(durationMacher.group(4));
            duration = dec * 100L + seconds * 1000L + minutes * 60L * 1000L + hours * 60L * 60L * 1000L;
            //String startTime = durationMacher.group(5) + "ms";
            videoBitrate = Integer.parseInt(durationMacher.group(6));
        }
        // 匹配视频分辨率等信息
        if (videoStreamMacher.find()) {
            videoEncoder = videoStreamMacher.group(1);
            String s2 = videoStreamMacher.group(2);
            videoWidth = Integer.parseInt(videoStreamMacher.group(3));
            videoHeight = Integer.parseInt(videoStreamMacher.group(4));
            String s5 = videoStreamMacher.group(5);
            videoFramerate = Float.parseFloat(videoStreamMacher.group(6));
        }
        // 匹配视频中的音频信息
        if (videoMusicStreamMacher.find()) {
            musicFormat = videoMusicStreamMacher.group(1); // 提取音频格式
            //String s2 = videoMusicStreamMacher.group(2);
            samplerate = Long.parseLong(videoMusicStreamMacher.group(3)); // 提取采样率
            //String s4 = videoMusicStreamMacher.group(4);
            //String s5 = videoMusicStreamMacher.group(5);
            musicBitrate = Integer.parseInt(videoMusicStreamMacher.group(6)); // 提取比特率
        }

        // 封装视频中的音频信息
        MusicMetaInfo musicMetaInfo = new MusicMetaInfo();
        musicMetaInfo.setFormat(musicFormat);
        musicMetaInfo.setDuration(duration);
        musicMetaInfo.setBitRate(musicBitrate);
        musicMetaInfo.setSampleRate(samplerate);
        // 封装视频信息
        VideoMetaInfo videoMetaInfo = new VideoMetaInfo();
        videoMetaInfo.setFormat(videoFormat);
        videoMetaInfo.setSize(videoSize);
        videoMetaInfo.setBitRate(videoBitrate);
        videoMetaInfo.setDuration(duration);
        videoMetaInfo.setEncoder(videoEncoder);
        videoMetaInfo.setFrameRate(videoFramerate);
        videoMetaInfo.setHeight(videoHeight);
        videoMetaInfo.setWidth(videoWidth);
        videoMetaInfo.setMusicMetaInfo(musicMetaInfo);

        return videoMetaInfo;
    }

    /**
     * 检测视频格式是否合法
     * @param format
     * @param formats
     * @return
     */
    private static boolean isLegalFormat(String format, String formats[]) {
        for (String item : formats) {
            if (item.equals(format.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取指定文件的后缀名
     * @param file
     * @return
     */
    private static String getFormat(File file) {
        String fileName = file.getName();
        String format = fileName.substring(fileName.indexOf(".") + 1);
        return format;
    }


    /**
     * 在程序退出前结束已有的FFmpeg进程
     */
    private static class ProcessKiller extends Thread {
        private Process process;

        public ProcessKiller(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            this.process.destroy();

        }
    }


    /**
     * 用于取出ffmpeg线程执行过程中产生的各种输出和错误流的信息
     */
    static class PrintStream extends Thread {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                if (null == inputStream) {

                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (null != bufferedReader) {
                        bufferedReader.close();
                    }
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {

                }
            }
        }
    }

}
