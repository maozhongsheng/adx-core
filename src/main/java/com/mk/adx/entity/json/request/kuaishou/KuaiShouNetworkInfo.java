package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 网络状态信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouNetworkInfo {
    private String ip;//客户端公网 ip 地址，服务端通过 API接入必须填写，不可以填服务端地 址；如果是客户端直接通过 API 接口 请求广告，不要填写这个字段。
    private String mac;//设备 mac 地址
    private String macMd5;//md5 加密后的设备 mac 地址，建议优先填写 mac
    private int connectionType;//网络连接类型，选填 0: 无法探测; 1:蜂窝数据接入，未知网络类型; 2: 蜂 窝数据 2G 网络; 3: 蜂窝数据 3G 网 络; 4: 蜂窝数据 4G 网络; 5: 蜂窝数据5G 网络; 6: LTE 网络; 100: Wi-Fi 网 络接入; 101: 以太网接入
    private int operatorType;//运营商类型，选填 0: 未知运营商; 1:中国移动; 2: 中国电信; 3: 中国联通;99: 其他运营商
}
