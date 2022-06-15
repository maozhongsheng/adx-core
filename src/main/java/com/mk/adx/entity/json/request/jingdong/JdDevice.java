package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 设备对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:53
 */
@Data
public class JdDevice {
    private String os;//操作系统，只支持 android 和 ios，大小写都可以
    private String osv;//操作系统版本，代码参考 2.5.5
    private String osupdatetime;//系统更新时间，参考 2.5.1
    private String did;//设备的IMEI(至少有一个)
    private String ifa;//IDFA(至少有一个)
    private String ifamd5;//md5（32 位大写）后的IDFA
    private List<JdCaid> caid;//对于ios 的ifa、ifamd5、caid 至少有一个必填， 可同时存在，优先级：ifa>ifamd5>caid
    private String ip;//设备ip 地址
    private String ipenc;//编码或加密后的ip 地址，采用 base64编码或对称方式加密，对于ip 和ipenc 至少一个必填
    private String ua;//user agent(Browser user agent string)请保持原值不要转义或者自定否则会影响广告填充
    private Integer connectiontype;//设备连接类型，取值范围：0:Unknown,1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G，7：5G
    private String make;//制造厂商,如“apple”“samsung”“huawei“，默认为空String
    private String model;//型号,如”iphonea1530”，默认为空String
    private String hwmodel;//硬件型号，代码参考 2.5.7
    private String hwname;//md5 后的设备名称，代码参考 2.5.4
    private String hwmachine;//系统型号，代码参考 2.5.6
    private String hwv;//硬件型号版本，例如 iPhone5S 中的 5S
    private String carrier;//运营商名称，取值 mobile:中国移动，unicom:联通， telecom：电信，代码参考 2.5.8
    private String flashver;//应反作弊需求添加，表示浏览器支持的Flash 版本
    private String language;//应反作弊需求添加，表示浏览器语言，使用ISO-639 标准，代码参考 2.5.3
    private int screenheight;//应反作弊需求添加，表示屏幕的物理高度，以像素为单位
    private int screenwidth;//应反作弊需求添加，表示屏幕的物理宽度，以像素为单位
    private int ppi;//应反作弊需求添加，表示以像素每英寸表示的屏幕尺寸
    private String macidmd5;//应反作弊需求添加，表示设备 mac 地址，使用md5 哈希算法加密
    private JdGeo geo;//应反作弊需求添加，Geo 对象，表示用户或用户设备当前地理位置信息。【注意：要求按照 WGS84 坐标系输出经纬度】
    private JdExt ext;//应反作弊需求添加，表示扩展信息
    private String countrycode;//国家代码，代码参考 2.5.2
    private String sysmemory;//系统内存，代码参考 2.5.9
    private String sysdisksize;//硬盘容量，代码参考 2.5.10
    private String machinetype;//机器类型，IPad，模拟器
    private String jailbreak;//是否越狱，0:否，1:是

}
