package com.mk.adx.entity.json.request.yidianzixun;

import lombok.Data;

import java.util.List;

/**
 * 网站对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/8/2 13:52
 */
@Data
public class YdzxSite {
    private List<String> domain;//交互网站的域名
    private String page;//当前页面URL
    private String ref;//当前页面referer
}
