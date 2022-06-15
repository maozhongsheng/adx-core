package com.mk.adx.entity.json.request.jiaming;

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
public class JmBanner {
    private int w;//广告位宽度
    private int h;//广告位高度
    private int wmax;//最大宽度
    private int hmax;//最大高度
    private int wmin;//最小宽度
    private int hmin;//最小高度
    private String id;//横幅广告id
    private List<String> mimes;//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif

}
