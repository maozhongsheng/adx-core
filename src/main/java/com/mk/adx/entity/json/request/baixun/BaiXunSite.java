package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunSite {
    private String name;//网站名称
    private String domain;//网站域名
    private String page;//当前页面
    private String ref;//referer
}
