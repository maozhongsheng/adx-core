package com.mk.adx.entity.json.request.renze;


import lombok.Data;

import java.util.List;

/**
 * 广告位信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class RzImp {
    private String posId;//广告位 id
    private int width;// 广告位宽度
    private int height;//广告位高度
    private List<Integer> opType;//广告操作类型0：无限制 1:app 下载 2:H5 (在 app 内 webview 打开目标链接) 3:Deeplink 4:电话广告 5：广点通下载广告,6 微信小程序拉起 7.广点通跳转 8.浏览器打开目标链接
}
