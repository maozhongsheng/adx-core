package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouUserInfo {
    private int age;//用户年龄
    private String gender;//用户性别，F: 女性 M:男性
    private List<String> interest;//用户兴趣
    private int thirdAge;//第三方用户年龄，0：未知； 1：18-23；2：24-30；3：31-40；4：41-49；5：50+
    private int thirdGender;//第三方用户性别，0：未知； 1：男；2：女
    private String thirdInterest;//第三方用户兴趣,两层数组，第 一层用分号分割，第二层用顿 号分割

}
