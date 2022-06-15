package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:52
 */
@Data
public class JdApp {
    private String bundle;//application bundle 或 package name
    private List<String> cat;//app 所属类别
    private String keywords;//app 关键字标签
    private List<String> pagecat;//当前页面的分类信息标签列表，参照open-RTBv2.5 list 5.1
    private String sdkversion;//sdk 版本，使用SDK 方式接入必填

}
