package com.mk.adx.entity.json.request.xiaomi;

import lombok.Data;

import java.util.List;

/**
 * 小米
 *
 * @author  mzs
 * @version 1.0
 * @date 2022/4/23 18:21
 */
@Data
public class XiaoMiBidRequest {
    private XiaoMiDevice deviceInfo;
    private XiaoMiUser userInfo;
    private XiaoMiApp appInfo;
    private List<XiaoMiImp> impRequests;
    private XiaoMiContent contentInfo;
    private XiaoMiContext context;


    private static final long serialVersionUID = 1L;
}
