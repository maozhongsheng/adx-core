package com.mk.adx.entity.json.request.szyd;


import lombok.Data;

import java.util.List;

/**
 * algorix
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class SzydBidRequest {
    private String token	;//由商务提供
    private List<SzydImp> imp;
    private SzydApp app;
    private SzydDevice device;
    private String id;//请求标识 ID，由媒体⽣成，请确保全局唯⼀


}
