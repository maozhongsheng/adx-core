package com.mk.adx.entity.json.request.jiaming;

import lombok.Data;

/**
 * 管理协议规则对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class JmPmp {
    private int private_auction;//==Deal规则是否生效,0 =所有投标被接受,1 =报价仅限于指定的协议和条款
    private JmDeals deals;//协议对象

}
