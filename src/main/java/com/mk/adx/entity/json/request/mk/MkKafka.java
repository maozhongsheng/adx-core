package com.mk.adx.entity.json.request.mk;

import lombok.Data;

@Data
public class MkKafka {
    private String publish_id ;
    private String media_id;
    private String pos_id;
    private String slot_type;
    private String dsp_id;
    private String dsp_media_id;
    private String dsp_pos_id;
}
