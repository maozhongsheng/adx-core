package com.mk.adx.entity.json.request.yueke;

import com.mk.adx.entity.json.request.zhimeng.ZhimengDeviceInfo;
import lombok.Data;

import java.util.List;

/**
 * 知盟-竞价请求对象-总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/12/08 13:49
 */
@Data
public class YuekeBidRequest {

    private String id;//请求ID
    private String version;//协议版本号
    private List<YuekeImp> imp;//位置信息
    private YuekeSite site;//位置信息
    private YuekeApp app;//位置信息
    private YuekeDevice device;//位置信息
    private YuekeUser user;//位置信息
    private int at;//位置信息
    private int test;//位置信息
    private int tmax;//位置信息
    private YuekeExt ext;//位置信息


    private static final long serialVersionUID = 1L;

}
