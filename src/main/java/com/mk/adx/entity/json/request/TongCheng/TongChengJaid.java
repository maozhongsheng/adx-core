package com.mk.adx.entity.json.request.TongCheng;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/27 18:08
 */
@Data
public class TongChengJaid {
    private String osv;//操作系统版本
    private String osupdatetime;//系统更新时间
    private String hwmodel;//硬件型号
    private String hwname;//md5后的设备名称
    private String hwmachine;//系统型号
    private String carrier;//运营商名称，取值mobile:中国移动，unicom:联通， telecom：电信
    private String language;//表示浏览器语言，使用 ISO-639标准
    private String countrycode;//国家代码
    private String sysmemory;//系统内存
    private String sysdisksize;//硬盘容量

}
