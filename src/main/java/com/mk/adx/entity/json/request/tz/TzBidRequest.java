package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 天卓-竞价请求对象-总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class TzBidRequest {
    @NotBlank(message = "请求唯一ID")
    private String id;//竞价请求id
    @NotEmpty(message = "描述广告位对象-曝光对象不能为空")
    @Valid
    private List<MkImp> imp;//描述广告位对象-曝光对象0+-**/
    @NotNull(message="移动app对象不能为空")
    @Valid
    private TzApp app;//移动app对象
    @NotNull(message="设备对象不能为空")
    @Valid
    private TzDevice device;//设备对象
    @NotBlank(message = "Api版本号不能为空")
    private String api_version;//Api版本号
    private TzUser user;//用户对象
    private Integer test;//是否测试环境, ,0：正式，1：测试，默认为 0
    private TzAdv adv; //配置数据

    private static final long serialVersionUID = 1L;

}
