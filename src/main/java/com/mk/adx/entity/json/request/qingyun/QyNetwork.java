package com.mk.adx.entity.json.request.qingyun;


import lombok.Data;


/**
 * 青云-网络信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class QyNetwork {
    private String ip;//网络 IP
    private int connection_type;//网络链接类型：0 - 未知，1 - WIFI2 - 2G，3 - 3G，4 - 4G，5 - 5G
    private int operator_type;//网络运营商0-未知，1-中国移动，2-中国联通，3-中国电信，4-其它
}
