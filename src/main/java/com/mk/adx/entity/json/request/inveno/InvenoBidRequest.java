package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

import java.util.List;

/**
 * 英威诺总请求
 */
@Data
public class InvenoBidRequest {
    private String bid; //请求的唯一id，32个字节的字符串，由媒体端生成。生成规则：MD5小写值（设备号+APP包名+毫秒时间戳）
    private String api_version;//API 版本，按照当前接入所参照的API文档版本取值，通常取最新的版本号，如2.2.0。(注意:版本号可能会影响返回广告内容的字段,请正确取值)
    private String ua;//客户端的User Agent。必须是客户端通过系统API获取的真实UA，不能自定义
    private InvenoApp app;//App对象，客户端APP的信息，必须真实来源于客户端。见App对象。
    private InvenoDevice device;//Device对象，用户的设备信息，必须真实来源于客户端。见Device对象。
    private InvenoNetWork network;//Network对象，用户设备的网络环境信息，必须真实来源于客户端。见Network对象。
    private InvenoGps gps;//GPS 对象，用户设备的GPS定位信息，用于辅助触发LBS 广告，建议上传。见GPS对象。
    private InvenoUser user;//User 对象，客户端用户信息，用于辅助触发人群定向广告。见User对象
    private List<InvenoAdspaces> adspaces;//Ad Space对象列表，广告位信息，必须真实来源于客户端，目前仅支持单个广告位。见 Ad Space对象
    private InvenoLocate locate;//Locate对象，用户所在区域信息，必须真实来源于客户端。见Locate对象。
    private Integer from;//(内部使用)请求来自老GATE渠道时，请带上值from值为1，智子3.0原有流量from值为2。
    private InvenoPageInfo page_info;//PageInfo对象，广告请求页面的信息。如果媒体可以获取并带上，有利于广告投放精确性的提升。见 PageInfo对象。

}
