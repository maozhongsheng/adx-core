package com.mk.adx.entity.json.request.TongCheng;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/27 17:51
 */
@Data
public class TongChengDevice {
    private String ip;//设备ip
    private String idfa;//ios设备需传
    private String imei;//android10以下需传
    private String oaid;//android10以上需传
    private String mac;//设备mac地址
    private int deviceType;//设备类型 0-手机;1-平板;2-PC;3-互联网电视
    private String os;//操作系统ios、android
    private String osv;//操作系统版本
    private int network;//0-未识别,1-wifi,2-2g,3-3g,4-4g,5-5g
    private int operator;//网络运营商 0-未知,1-移动,2-联通,3-电信
    private int width;//设备宽
    private int height;//设备高
    private int pixelRatio;//设备密度
    private int orientation;//屏幕方向 0-未知,1-竖屏,2-横屏
    private String brand;//设备品牌
    private String model;//设备型号
    private String userAgent;//UA
    private double lon;//经度
    private double lat;//纬度
    private int pos;//所在屏数0-未知,15-一五屏,6- 五屏以上
    private String adid;//andoridId,andorid设备必传

}
