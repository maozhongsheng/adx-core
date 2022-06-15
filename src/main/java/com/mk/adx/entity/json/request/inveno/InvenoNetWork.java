package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

import java.util.List;

@Data
public class InvenoNetWork {
    private String ip;//客户端真实IP，请勿使用服务器IP或内网IP
    private int network_type;//网络类型，必须区分WIFI和移动网络，如果不能区分234G，统一填写4G。取值：0=未知；1=WIFI；2=2G；3=3G；4=4G；5=5G；
    private String carrier_id;//运营商编码，使用真实值，如果不能取到，统一填写未知。取值：unknown=未知；70120=中国移动；70121=中国电信；70123=中国联通；
    private InvenoCellularId cellular_id;//Cellular ID对象列表，基站ID，用于快速用户定位。见Cellular ID对象。
    private List<InvenoWifi> wifi_aps;//WIFI AP对象列表，周边WIFI 热点列表，用于精细用户定位。见WIFI AP对象。
}
