package com.mk.adx.entity.json.request.tz;

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
public class TzNative {
    private String request;//原生广告的标准规范的请求
    private List<Integer> api;//此类型支持的接口类型，参考3.3
    private List<Integer> battr;//创意属性
    private Integer w;//广告位宽度
    private Integer h;//广告位高度
    private List<String> native_field;//属性集合，参考3.8
    private Integer image_nums;//允许图片数量 1: 单张图片； 2: 2张图片； 3: 3张图片
    private String ver;//版本
    private Integer desc_max;//描述最大字数
    private Integer iw;//icon宽度
    private Integer ih;//icon高度
    private Integer title_max;//标题最大字数
//    private List<String> allowstyle;//允许展示的模版ID,如有使用线下约定
//    private List<String> posid;//位置数组,请求数组的子集
//    private Integer page_index;//页编号

}
