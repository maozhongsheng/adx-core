package com.mk.adx.entity.json.request.yidianzixun;

import lombok.Data;

import java.util.List;

/**
 * 设备对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:53
 */
@Data
public class YdzxDevice {
    private YdzxGeo geo;//地理位置对象
    private String ua;//浏览器的User-Agent属性字符串
    private String ip;//设备的IPv4地址
    private String ipv6;//设备的IPv6地址
    private int devicetype;//设备类型：0: 未知 1: 手机 2: 平板 3: PC 4: 智能电视
    private String os;//操作系统,取值如下：Windows: "windows",Android: "android",iPhone: "ios",苹果电脑: "mac"
    private String osv;//操作系统版本号,如"4.1", "xp"等
    private int w;//物理屏幕宽度的像素
    private int h;//物理屏幕高度的像素
    private String make;//制造厂商,如“apple”“samsung”“huawei“，默认为空String
    private String model;//型号,如”iphonea1530”，默认为空String
    private int ppi;//屏幕大小
    private float pxrate;//物理像素和设备独立像素的比例
    private int connectiontype;//联网方式：0：未知 1：Ethernet 2：Wifi 3：蜂窝网络 4：蜂窝网络2G 5：蜂窝网络3G 6：蜂窝网络4G 7：蜂窝网络5G
    private int operatortype;//运营商：0：未知 1：中国移动 2：电信 3：联通 4：广电 99：其他
    private String language;//浏览语言ISO-639-1-alpha-2
    private List<String> appinstalled;//已安装app定向
    private List<String> appuninstalled;//未安装app定向
    private String did;//硬件设备ID，对Android而言是imei，对ios而言是idfa原值和md5值必传其一
    private String didmd5;//
    private String didsha1;//
    private String dpid;//平台定义的设备ID，对Android而言是androidid，对ios而言是idfvAndroid 必填，原值和md5值必传其一
    private String dpidmd5;//
    private String dpidsha1;//
    private String oaid;//OAIDAndroid O 版本及以上必传，原值和md5值必传其一
    private String oaidmd5;//
    private String oaidsha1;//
    private String mac;//MAC地址Android 必填，原值和md5值必传其一
    private String mac_md5;//
    private String mac_sha1;//
    private String bootMark;//系统启动标识，bootid，每次开机都会产生的一个唯一 ID
    private String updateMark;//系统更新标识，用 C++代码获取的是data/data 目录的存取执行的时间+修改的时间，形成的字符串
    private String vercodeofag;//应用市场版本号。与下载类广告的转化路径有关。华为设备必填。
    private String vercodeofhms;//HMS Core 版本号，实现被推广应用的静默安装依赖 HMS Core 能力。华为设备必填

}
