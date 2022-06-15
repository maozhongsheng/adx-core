package com.mk.adx.entity.json.response.tz;

import lombok.Data;

import java.util.List;

/**
 * 素材对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/16 16:51
 */
@Data
public class TzAdm {
    private List<TzItem> items;//若干组广告素材集合对象
}
