package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * 视频扩展内容
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:37
 */
@Data
public class JmVideoExt {
    private List<String> loadedTracker;//视频加载成功监播
    private List<String> muteTracker;//静音监播
    private List<String> unmuteTracker;//静音解除监播
    private List<String> pauseTracker;//视频暂停监播
    private List<String> video_cache;//视频缓存成功监播
    private List<String> rewards;//获取激励成功
    private List<String> video_close;//关闭广告/关闭视频
    private List<String> error_info;//报错信息

}
