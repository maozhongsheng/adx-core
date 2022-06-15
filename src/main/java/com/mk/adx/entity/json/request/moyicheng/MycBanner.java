package com.mk.adx.entity.json.request.moyicheng;

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
public class MycBanner {
    private Integer w;//广告位宽度
    private Integer h;//广告位高度
    private Integer wmin;//最小宽度
    private Integer hmin;//最小高度
    private List<String> mimes;//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif

}
