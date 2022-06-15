package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

import java.util.List;

/**
 * native类型的广告对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:50
 */
@Data
public class MycNative {
    private String request;//请求信息，遵循NATIVE请求接口
    private String ver;//版本,默认1.1
    private List<MycAsset> assets;

}
