package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 媒体广告位信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouImpInfo {
    private int posId;//广告位 id，由快手平台分配
    private int adNum;//该广告位请求的广告数，选填，默认 为 1，信息流场景最大支持一次请求5条广告
    private int action;//广告位触发行为，1 上滑 2 下滑 3 左 滑 4 右滑
    private int width;//广告位宽度
    private int height;//广告位高度
    private int adStyle;//1.0.11 版本起必填，需要与平台创建广告位时选择的类型一致。 1：信息流、2：激励视频、3：全屏 视频、4：开屏、6：draw 视频
    private int cpmBidFloor;//cpm 底价, 单位:分/千次展现, 0 为不限制，需要广告位支持动态底价

}
