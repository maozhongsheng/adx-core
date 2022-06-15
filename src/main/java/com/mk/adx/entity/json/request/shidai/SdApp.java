package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class SdApp {
    private String id;//交互App ID
    private String name;//媒体app名称
    private String bundle;//应用程序包或包名称
    private String ver;//app应用版本

}
