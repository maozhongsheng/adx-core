package com.mk.adx.entity.json.request.tengxun;

import lombok.Data;

/**
 * 腾讯-优量汇-媒体相关信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/26 14:21
 */
@Data
public class TengXunMedia {
    private String app_id;//在联盟平台创建媒体时分配的应用ID。不填写或填写错误将不返回广告
    private String app_bundle_id;//应用包名。不填写或填写错误将不返回广告。请求填写包名要与注册媒体时填写保持一致，不一致将无广告返回
}
