package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoCellularId {
    private int MCC;//移动国家代码。（中国的为460）
    private int MNC;//移动网络号码。（中国移动为00，中国联通为01，中国电信为03）
    private int LAC;//位置区域码。取值范围：0~65535
    private int CID;//基站编号，是个16位的数据。
}
