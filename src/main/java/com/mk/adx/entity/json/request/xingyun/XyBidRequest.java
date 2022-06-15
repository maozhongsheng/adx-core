package com.mk.adx.entity.json.request.xingyun;


import lombok.Data;

/**
 * 星云-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class XyBidRequest {
    private String req_id;//请求id，最⼤32位字符串调⽤⽅⾃⾏⽣成，需全局唯⼀
    private String version;//接⼝版本，当前是1.3.3
    private String imei;//设备的imei(安卓需要)
    private String oaid;//设备的oaid(安卓需要)
    private String imei_md5;//设备的imei md5值(安卓需要
    private String oaid_md5;//设备的oaid md5值(安卓需要)
    private String android_id;//设备的android_id(安卓需要)
    private String android_id_md5;//设备的android_idmd5(安卓需要)
    private String caid;//设备的caid(ios需要)
    private String idfv;//设备的idfv(ios需要)
    private String idfv_md5;//设备的idfa md5值(ios需要)
    private String openudid;//open_udid(ios需要)
    private String app_name;//应⽤名称
    private String app_package;//应⽤包名
    private String app_version;//应⽤版本号
    private String ip;//设备外⽹的ip地址
    private String user_agent;//设备实际User-Agent值，WebviewUA
    private String mac;//设备mac地址(取不到传'02:00:00:00:00:00')
    private String model;//设备型号名称
    private String brand;//设备品牌名称
    private String os_type;//设备操作系统类型
    private String os_version;//设备操作系统版本
    private int device_width;//设备宽度
    private int device_height;//设备⾼度
    private int dpi;//设备屏幕图像密度
    private float density;//设备屏幕物理像素密度
    private String network;//⼿机运营商代号
    private String connection_type;//设备联⽹类型
    private float longitude;//地理位置经度
    private float latitude;//地理位置纬度
    private String boot_mark;//iOS：1623815045.970028、Android：ec7f4f33-411a-47bc-8067-744a4e7e0723
    private String update_mark;//iOS：1581141691.570419583、Android：1004697.709999999
}
