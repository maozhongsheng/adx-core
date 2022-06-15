package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * 广告主对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:30
 */
@Data
public class JmAdvertiser {
    private String id;//广告主 ID 长度小于 100
    private String industry;//广告主所属的一级行业
    private String sub_industry;//广告主所属的二级行业
    private List<String> category_name;//行业分类中文名称

}
