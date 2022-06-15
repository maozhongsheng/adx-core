package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

import java.util.List;

/**
 * banner类型的广告位对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:50
 */
@Data
public class SdBanner {
    private int w;//广告位宽度
    private int h;//广告位高度
    private int wmax;//广告位最大宽度
    private int wmin;//广告位最小宽度
    private int hmax;//广告位最大的高度
    private int hmin;//广告位最小的高度
    private int iamount;//广告位图片数量
    private List<String> mimes;//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif

}
