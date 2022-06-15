package com.mk.adx.entity.json.request.baixun;


import lombok.Data;

import java.util.List;

/**
 * 百寻
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class BaiXunBidRequest {
    private String id;//唯一请求 ID
    private List<BaiXunImp> imp;
    private BaiXunSite site;
    private BaiXunApp app;
    private BaiXunDevice device;
    private BaiXunUser user;
    private String cat;//行业代码（参考附录：行业分类）
    private String bcat;//屏蔽的行业，多个以逗号分隔
    private String badv;//屏蔽的域名，多个以逗号分隔
    private int https;//https 环境 0-不限 1-只支持 https
    private int deeplink;// 0-未知 1-支持 deeplink 2-不支持
    private String String;// 拓展信息


}
