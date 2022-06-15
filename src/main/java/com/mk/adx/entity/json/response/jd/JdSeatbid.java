package com.mk.adx.entity.json.response.jd;

import lombok.Data;

import java.util.List;

/**
 * 竞价集合对象,若是竞价至少有一个
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/2 16:48
 */
@Data
public class JdSeatbid {
    private List<JdBannerbid> bannerbids;//针对imp 的 bid 信息
    private List<JdNativebid> nativebids;//针对imp 的 bid 信息
    private List<JdVideobid> videobids;//针对imp 的 bid 信息
}
