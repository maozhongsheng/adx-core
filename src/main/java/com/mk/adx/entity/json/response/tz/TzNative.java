package com.mk.adx.entity.json.response.tz;

import lombok.Data;
import java.util.List;

/**
 * 原生对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/7/1 17:26
 */
@Data
public class TzNative {
    private String ver;//原生广告协议版本号
    private TzIcon icon;//Icon数据
    private TzLogo logo;//Logo数据
    private List<TzImage> images;//大图数据，数据元素形式如icon
    private TzVideo video;//信息流视频广告
    private String title;//广告标题
    private String desc;//广告描述内容
    private String desc2;//补充广告描述文本
}
