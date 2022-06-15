package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 管理协议规则对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:52
 */
@Data
public class JdPmp {
    private List<JdDeal> deals;//交易信息

}
