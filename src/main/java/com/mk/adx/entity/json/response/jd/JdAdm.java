package com.mk.adx.entity.json.response.jd;

import lombok.Data;

import java.util.List;

/**
 * Adm 对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/2 16:48
 */
@Data
public class JdAdm {
    private List<JdItem> items;//若干组广告素材集合对象
}
