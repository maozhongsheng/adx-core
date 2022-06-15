package com.mk.adx.entity.json.request.jingdong;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 描述广告位对象-曝光对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:48
 */
@Data
public class JdImp {
    private String id;//展示id，唯一标识一个展示；由媒体侧生成，请确保全局唯一
    private String tagid;//广告位 id，在媒体系统中唯一标识一个广告位。（确保与对接平台上登记的tagID保持一致，否则无法返回广告）
    private double bidfloor;//底价，单位：千次分（人民币）。竞价广告时必须，包段不需要
    private JdBanner banner;///Banner 广告对象，这种形式返回素材为 html+JS
    @JSONField(name = "native")
    private JdNative NATIVE;//native类型的广告对象,	原生类型（是否使用 click url 宏替换功能，true 表示使用宏，false 表示不使用，注意不要传递字符串形式，json 格式支持bool类型。只有 banner 广告支持 click 宏，详见 3.4.2 节）
    private JdVideo video;//video类型的广告位对象,视频类型（是否使用 click url 宏替换功能，true 表示使用宏，false 表示不使用，注意不要传递字符串形式，json 格式支持bool类型。只有 banner 广告支持 click 宏，详见 3.4.2 节）
//    private boolean clicktracking;//是否使用 click url 宏替换功能，true 表示使用宏，false 表示不使用，注意不要传递字符串形式，json 格式支持bool类型。只有 banner 广告支持 click 宏，详见 3.4.2 节
    private boolean isdeeplink;//是否支持呼起京东app或广告主app。只有native广告支持，true表示返回deeplinkurl,false则不返回。注意不要传递字符串形式
    private boolean isul;//iOS流量使用，只在 isdeeplink传 true 时有效，iOS请求如需返回universallink url，传 true，否则不传。注意不要传递字符串形式。
    private int secure;//要求创意中只包含 https（曝光监测、点击、素材 url 等均为 https）的值为1，其他情况返回 http 的创意
    private JdPmp pmp;//管理协议规则对象
}
