package com.mk.adx.entity.json.request.zhimeng;

import lombok.Data;

/**
 * 位置信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/12/8 14:26
 */
@Data
public class ZhimengImpInfo {

    private String pos_id;//展示位置ID（可在平台媒体管理中查看内容位ID）
    private String web_name;//站点名称
    private String web_domain;//站点域名
    private String app_name;//app名称
    private String package_name;//app包名
    private int width;//位置宽度
    private int height;//位置高度

}
