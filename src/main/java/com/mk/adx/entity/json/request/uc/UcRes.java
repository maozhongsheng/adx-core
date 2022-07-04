package com.mk.adx.entity.json.request.uc;

import lombok.Data;

/**
 * UC⻚⾯资源信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcRes {
    private String src_url;//资源来源 URL
    private String res_url;//资源URL
    private String res_title;//资源标题
}
