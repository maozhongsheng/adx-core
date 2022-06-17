package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 移动app对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class TzApp {
    @NotBlank(message = "媒体不能为空")
    private String id;//媒体id
    @NotBlank(message = "应用程序包或包名称不能为空")
    private String bundle;//应用程序包或包名称
    private String ver;//app应用版本
    @NotBlank(message = "应用名称不能为空")
    private String name;//媒体app名称

}
