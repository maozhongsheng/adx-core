package com.mk.adx.entity.json.request.tz;

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
public class TzBanner {
    private Integer w;//广告位宽度
    private Integer h;//广告位高度
    private List<Integer> btype;//横幅广告通过类型
    private List<Integer> battr;//创意属性
    private List<Integer> api;//此类型支持的接口类型，参考3.3
    private String id;//横幅广告id
    private Integer pos;//广告位位置，参考3.1
    private List<String> mimes;//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif
    private Integer wmax;//最大宽度
    private Integer hmax;//最大高度
    private Integer wmin;//最小宽度
    private Integer hmin;//最小高度

}
