package com.mk.adx.entity.json.request.uc;

import lombok.Data;

import java.util.List;

/**
 * UC总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcBidRequest {
    private UcDevice ad_device_info;  //设备相关信息
    private UcApp ad_app_info;  //代码位id
    private UcGps ad_gps_info;  //代码位id
    private List<UcPos> ad_pos_info;  //代码位id
    private UcPage page_info;  //代码位id
    private UcRes res_info;  //代码位id
    private UcExt ext_info;  //代码位id
    private UcProtocol protocol_version;  //代码位id
}
