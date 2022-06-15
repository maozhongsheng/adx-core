package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 地理位置对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class TzGeo {
    private float lat;//纬度从-90.0 + 90.0,-是南
    private float lon;//经度-180.0 + 180.0,-是西方。
    private String llt;//获取位置信息的时间与发起广告请求的时间差，单位为分钟
    private String llp;//定位所用的provider，n(network) 为网络定位，g(gps) 为gps 定位,p(passive) 为其他app 里面的定位信息，f(fused)为系统返回的最佳定位
    private String wifi;//wifi 信息，用户将当前连接或者附近的wifi 的ssid 和mac传送过来
    private int type;//源的位置数据,建议当纬度/经度
    private String country;//城市编码 ISO-3166-1-alpha-3
    private String region;//地区代码ISO-3166-2
    private String metro;//Google metro code（谷歌地铁编码）
    private String city;//城市使用联合国贸易与运输代码位置
    private String zip;//zip或者邮政编码
    private int utcoffset;//本地时间，UTC（世界时间代码）
    private Integer lalo_type;//0 高德, 1 百度, 2 腾讯, 3 谷歌
   // private String province;//省份代码ISO-3166-2
}
