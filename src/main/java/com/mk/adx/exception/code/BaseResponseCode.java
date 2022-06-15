package com.mk.adx.exception.code;

/**
 * @ClassName: BaseResponseCode
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public enum BaseResponseCode implements ResponseCodeInterface{
    /**
     * 这个要和前段约定好
     *code=0：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     *code=4010001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     *code=4010002：(凭证过期) 客户端请求刷新凭证接口
     *code=4030001：没有权限禁止访问
     *code=400xxxx：系统主动抛出的业务异常
     *code=5000001：系统异常
     *
     */
    SUCCESS(0,"操作成功"),
//    SYSTEM_ERROR(5000001,"系统异常请稍后再试"),
//    DATA_ERROR(4000001,"传入的数据异常"),
//    VALIDATOR_ERROR(4000002,"参数校验异常"),
//    ACCOUNT_ERROR(4000003,"该账号不存在，请移步注册"),
//    ACCOUNT_LOCK_TIP(4000004,"该账号已经被锁定，请联系平台运营人员"),
//    ACCOUNT_PASSWORD_ERROR(4000005,"账号密码不正确，请重新登录"),
//    TOKEN_NOT_NULL(4010001,"认证token不能为空，请重新登录获取"),
//    TOKEN_ERROR(4010001,"Token 认证失败，请重新登录获取最新的token"),
//    ACCOUNT_LOCK(4010001,"该账号被锁定,请联系系统管理员"),
//    ACCOUNT_HAS_DELETED_ERROR(4010001,"该账号已被删除，请联系系统管理员"),
//    TOKEN_PAST_DUE(4010002,"token失效,请刷新token"),
//    NOT_PERMISSION(4030001,"没有权限访问该资源"),





    //请求成功返回码单个/批量广告请求
    SINGLE_SUCCESS(21000,"请求单条广告返回成功"),
    MULTI_ALL_SUCCESS(22001,"请求多条广告返回成功"),
    MULTI_PART_SUCCESS(22002,"请求多条广告，部分返回成功"),
    //非有效广告请求
    PROVIDER(20001,"广告非有道提供"),
    FAIL_REQUEST(20002,"标识此请求为失败上报，SDK 在遇到错误（如广告渲染超时（默认 10s））时会向服务端进行上报"),
    FAIL_REQUESTS(20003,"广告位当前不存在有效的样式（样式被删除或者暂停了）"),
    //不合法的广告请求
    NULL_SDK_REQUEST(40001,"SdkRequest 为空。在检查 SDK 广告请求有效性时失败"),
    NULL_SLOTID(40002,"广告位 id 为空"),
    INVALID_ADCOUNT(40003,"请求广告数目非法：<=0"),
    //http 请求转换为 sdk 请求异常
    INVALID_ENCODETYPE(40101,"ENCODED_TYPE_KEY(ydet，加密方式) 所对应的值非法"),
    DESIGNATED_STYLE_NAME_NOT_FOUND(40102,"广告请求中指定的样式名，在广告位的样式缓存里找不到。请检查广告位 id 是否正确传入，若正确，可能为缓存未同步，同步时间不超过 15 分钟"),
    INVALID_SDK_STATUS(40103,"检查广告位状态失败，不出广告。可能原因：（在 SdkSlot 表里有两个状态列：SDK_STATUS 和 SDK_OP_STATUS）1. 开发者将广告位的状态（opstatus）设为暂停或者删除状态, 导致广告位本身不存在、不可用等；2. 虽然广告位本身是有效的，但广告位状态（status）未通过审核"),
    NOT_IN_CACHE(40104,"缓存中不存在需要获取的广告位信息。缓存同步时间不超过 15 分钟"),
    WRONG_KEY(40105,"无法取到加密后的请求字段，请求’s’ 字段为空。即 ENCODED_REQUEST_KEY 所对应的加密内容为空"),
    ERROR_DECODE(40106,"在解析 HttpServletRequest 参数时发生错误。如：不合法的加密类型；加密类型存在但加密内容为空；对密文解密失败等"),
    ERROR_CREATE_ADREQUEST(40107,"根据 HttpServletRequest 构建 SdkAdRequest 失败"),
    //品牌广告异常
    BRAND_URL_ERROR(41001,"品牌广告的原始 link 字段（RAW_LINK）值为空"),
    BRAND_IMPR_ERROR(41002,"品牌广告的广告跟踪字段（imprTracker）值为空"),
    BRAND_STYLE_ERROR(41003," 品牌广告的广告样式名称字段（STYLE_NAME）值为空"),
    // 智选广告异常
    ZX_NO_ADITEM(41004,"智选返回空广告"),
    ZX_NO_INTERSECTSTYLE(41005,"智选在渲染广告时，广告与广告位支持样式交集为空"),
    ZX_NO_ADSTYLE(41006,"智选取到的广告没有样式（style）"),
    ZX_LACK_ELEMENT(41007,"智选取到的广告缺乏元素信息，无法渲染广告，不能将其放入广告体中"),
    ZX_NULL_TRACKER(41008,"智选在渲染广告时，没有展示跟踪链接数组 impTracker 或者点击跟踪链接数组 clkTracker"),
    ZX_TIMEOUT(41015,"智选返回广告超时"),
    ZX_BID_ERROR(41016,"智选返回广告异常"),
    //YEX 广告异常
    YEX_NULL_SELECTEDAD(41009,"yex 返回空广告提名品牌结果为空且无效果广告来填充"),
    YEX_NULL_AD(41010,"yex 没有返回广告"),
    YEX_CPC_TIMEOUT(41011,"yex 通过 OpenRTB 提名效果广告超时"),
    YEX_CPC_EXEC_FAILED(41012,"yex 通过 OpenRTB 提名效果广告出现执行异常"),
    YEX_BRAND_TIMEOUT(41013,"yex 通过 OpenRTB 提名品牌广告超时"),
    YEX_BRAND_EXEC_FAILED(41014,"yex 通过 OpenRTB 提名品牌广告出现执行异常"),
    //请求批量广告失败
    MULTI_FAILED(42001,"请求批量广告失败"),
    //超时
    TIMEOUT_FAILED(43001,"超时, 目前 nginx 最多等待后端服务 900ms"),
    //未定义异常
    UNDEFINED(-1,"未定义异常"),
    PARAMETER_ERROR(201,""),
    ;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应提示
     * @return
     */
    private final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }


}
