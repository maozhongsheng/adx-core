package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoUser {
    private String user_id; //用户ID，如果有则需要媒体上传(没有则使用IMEI作为userId)。如果是英威诺产品则需要上传资讯用户ID。
    private int Sex; //用户性别。取值：1=男；2=女
    private int Age; //用户年龄。
}
