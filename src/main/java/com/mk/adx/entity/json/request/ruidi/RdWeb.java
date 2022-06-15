package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdWeb {
    private String url;//请求页面的 URL
    private String title;//请求页面的标题
    private String source_url;//页面内容来源网址 URl
    private String keywords;//请求页面 metadata 关键字
    private String domain;//当前站点主域名

}
