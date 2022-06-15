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
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class TzBidRequest {
    @NotBlank(message = "竞价请求id不能为空")
    private String id;//竞价请求id
    @NotEmpty(message = "描述广告位对象-曝光对象不能为空")
    @Valid
    private List<TzImp> imp;//描述广告位对象-曝光对象0+-**/
    private TzSite site;//网站对象
    @NotNull(message="移动app对象不能为空")
    @Valid
    private TzApp app;//移动app对象
    @NotNull(message="设备对象不能为空")
    @Valid
    private TzDevice device;//设备对象
    // @NotBlank(message = "协议版本号不能为空")
    private String media_version;//媒体或ADX协议版本号,爱奇艺
    private List<String> wseat;//商家白名单,白名单的商家才允许买家出价，seatIds （商家 id） 需与 DSP 沟通，若空则没有限制
    private TzUser user;//用户对象
    private TzContent content;//content对象
    private Integer test;//是否测试环境, ,0：正式，1：测试，默认为 0
    private Integer at;//拍卖类型,1：第一价格，2：第二高价，默认是 2
    private boolean is_https;//是否 https, true 的话，返回的物料地址和上报地址,都必须是 https 的，否则会被过滤
    private TzAdv adv; //配置数据
    private TzKafka tzkafka;//kafka推送数据
    private static final long serialVersionUID = 1L;

}
