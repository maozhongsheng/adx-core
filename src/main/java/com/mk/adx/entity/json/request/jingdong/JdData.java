package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;
import java.util.List;

/**
 * data对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/21 11:16
 */
@Data
public class JdData {
    private String id;//数据提供商ID，若使用 segment字段， 则 id必填，id由京东侧分配，各数据提供商ID 不重复
    private String name;//数据提供商名称，建议填写媒体名称
    private List<JdSegment> segment;//用户标签词表
}
