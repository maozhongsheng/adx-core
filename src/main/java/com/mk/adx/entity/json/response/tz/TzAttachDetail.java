package com.mk.adx.entity.json.response.tz;

import lombok.Data;

/**
 * 附加信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:32
 */
@Data
public class TzAttachDetail {
    private String button_type;//附加功能显示为 button，该字段为功能类型，包括：1 跳转至详情页、2 打开表单弹框、3 立即下载、4 拨打电话
    private String button_text;//button 上显示的文字，广告主自定义（广告提供可选 list）
    private String attach_url;//button_type 为跳转至链接页面（1）（2）时，返回链接字段
    private String phone_number;//button_type 为拨打电话（4）时，该字段返回号码

}
