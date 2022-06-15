package com.mk.adx.entity.json.response;

import com.mk.adx.entity.json.response.tz.TzImage;
import lombok.Data;

import java.util.List;

/**
 *  kafka里存入的返回数据，对应clickhouse字段
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/6 9:55
 */
@Data
public class SspMediaResponseToKafka {

    private String request_id;//请求id，数据唯一标识，不重复
    private Integer ad_cost_time;//上游返回广告消耗时长
    private Integer cost_time;//总消耗时长
    //    private Integer download_start;//下载开始
//    private Integer download_end;//下载结束
//    private Integer install_start;//安装开始
//    private Integer install_end;//安装结束
//    private Integer activation;//激活
//    private Integer deeplink;//唤醒
//    private Integer ideeplink;//无效唤醒
    private Integer price;//消耗，单位分
    //    private Integer video_start;//视频播放开始
//    private Integer video_end;//视频播放结束
    private Integer adx_price;//联盟竞价，单位分
    private Integer bid_price;//媒体竞价，单位分
    //    private Integer dau ;//日活
    private String deal_id ;//交易ID，pd流量合同号
    private String agent_id ;//需要代理商ID
    //--广告素材相关字段--
    private String ad_id;//广告素材id
    private String pos_id;//广告位id
    private String title;//标题
    private String desc;//描述
    private String dsp_id;//dsp_id
    private String dsp_name;//dsp来源名称
    private String click_type;//点击类型
    private List<TzImage> images;//素材对象
    private Integer bid_count;//素材个数
    private String bundle;//包名
    private Long timestamp;//时间戳
    private String publish_id ; //代理商id
    private String media_id;//媒体id
    private String dsp_pos_id;//dsp广告位id
    private String dsp_media_id;//dsp媒体id
    private String slot_type;//广告位类型
    private Integer fill;

    private static final long serialVersionUID = 1L;
}