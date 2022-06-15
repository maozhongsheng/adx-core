package com.mk.adx.entity.json.response.jd;

import com.mk.adx.entity.json.request.jingdong.JdPmp;
import lombok.Data;

/**
 * Bannerbid对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/2 16:48
 */
@Data
public class JdBannerbid {
    private String id;//京东生成的id
    private String adid;//京东生成的素材校验id
    private String adm;//Html+js 文档内容，必须替换 HTML 文档里面的%%WIN_PRICE%%二价宏。
    private String impid;//对应请求中的imp 中的id
    private double price;//竞价价格，单位：分（人民币）非竞价广告无此字段返回
    private String nurl;//告知京东赢得 bid ，并通过宏替换%%WIN_PRICE%%提供二价。非竞价广告无此字段返回
    private String ad_type;//广告类型，1 代表非商品，3 代表商品
    private JdPmp pmp;//私有交易信息
}
