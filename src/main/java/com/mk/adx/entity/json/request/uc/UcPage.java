package com.mk.adx.entity.json.request.uc;

import lombok.Data;

/**
 * UC⻚⾯信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcPage {
    private String page_url; //⻚⾯URL，⽤于兴趣投放
    private String page_title; //⻚⾯标题，⽤于兴趣投放
    private String page_refer; //⻚⾯refer信息，⽤于兴趣投放
    private String meta_kw; //⻚⾯meta关键字，⽤于兴趣投放
}
