package com.mk.adx.entity.json.response.index;


import lombok.Data;

/**
 * Index总返回数据实体
 *
 * @author gj
 * @version 1.0
 * @date 2021/9/2 16:18
 */
@Data
public class IndexVideo {

    private String videoUrl;//视频地址

    private int videoDuration;//视频时长，单位秒

    private int size;//视频大小，单位 byte
}
