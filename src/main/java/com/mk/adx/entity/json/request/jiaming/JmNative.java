package com.mk.adx.entity.json.request.jiaming;

import lombok.Data;

import java.util.List;

/**
 * native类型的广告对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:50
 */
@Data
public class JmNative {
    private String request;//原生广告的标准规范的请求
    private String ver;//版本
    private int w;//广告位宽度
    private int h;//广告位高度
    private int iw;//icon宽度
    private int ih;//icon高度
    private int title_max;//标题最大字数
    private int desc_max;//描述最大字数
    private int image_nums;//允许图片数量 1: 单张图片； 2: 2张图片； 3: 3张图片
    private List<String> allowstyle;//允许展示的模版ID,如有使用线下约定
    private List<String> posid;//位置数组,请求数组的子集
    private int page_index;//页编号

}
