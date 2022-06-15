package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

import java.util.List;

/**
 * 管理协议规则对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class SdPmp {
    private Integer private_auction;//标识在Deal对象中指明席位的竞拍合格标准，0标识接受所有竞拍，1标识竞拍受deals属性中描述的规则的限制
    private List<SdDeal> deals;//一组Deal对象， 用于传输适用于本次展示的交易信息

}
