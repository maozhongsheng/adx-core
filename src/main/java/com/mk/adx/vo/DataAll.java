package com.mk.adx.vo;

import java.io.Serializable;
import java.util.Date;

public class DataAll implements Serializable {
    private Long id;

    private String req_id;

    private Date req_date;

    private Long ad_id;

    private Long pos_id;

    private Long slot_type;

    private Long media_id;

    private Long dsp_id;

    private String dsp_media_id;

    private String dsp_pos_id;

    private Long pv_s;

    private Long pv_time;

    private Long clikc_s;

    private Long clikc_time;

    private Long v_start_s;

    private Long v_end_s;

    private Long download_start_s;

    private Long download_end_s;

    private Long install_start_s;

    private Long install_end_s;

    private Long deeplink_s;

    private Long ideeplink_s;

    private String ip;

    private Long res_s;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public Date getReq_date() {
        return req_date;
    }

    public void setReq_date(Date req_date) {
        this.req_date = req_date;
    }

    public Long getAd_id() {
        return ad_id;
    }

    public void setAd_id(Long ad_id) {
        this.ad_id = ad_id;
    }

    public Long getPos_id() {
        return pos_id;
    }

    public void setPos_id(Long pos_id) {
        this.pos_id = pos_id;
    }

    public Long getSlot_type() {
        return slot_type;
    }

    public void setSlot_type(Long slot_type) {
        this.slot_type = slot_type;
    }

    public Long getMedia_id() {
        return media_id;
    }

    public void setMedia_id(Long media_id) {
        this.media_id = media_id;
    }

    public Long getDsp_id() {
        return dsp_id;
    }

    public void setDsp_id(Long dsp_id) {
        this.dsp_id = dsp_id;
    }

    public String getDsp_media_id() {
        return dsp_media_id;
    }

    public void setDsp_media_id(String dsp_media_id) {
        this.dsp_media_id = dsp_media_id;
    }

    public String getDsp_pos_id() {
        return dsp_pos_id;
    }

    public void setDsp_pos_id(String dsp_pos_id) {
        this.dsp_pos_id = dsp_pos_id;
    }

    public Long getPv_s() {
        return pv_s;
    }

    public void setPv_s(Long pv_s) {
        this.pv_s = pv_s;
    }

    public Long getPv_time() {
        return pv_time;
    }

    public void setPv_time(Long pv_time) {
        this.pv_time = pv_time;
    }

    public Long getClikc_s() {
        return clikc_s;
    }

    public void setClikc_s(Long clikc_s) {
        this.clikc_s = clikc_s;
    }

    public Long getClikc_time() {
        return clikc_time;
    }

    public void setClikc_time(Long clikc_time) {
        this.clikc_time = clikc_time;
    }

    public Long getV_start_s() {
        return v_start_s;
    }

    public void setV_start_s(Long v_start_s) {
        this.v_start_s = v_start_s;
    }

    public Long getV_end_s() {
        return v_end_s;
    }

    public void setV_end_s(Long v_end_s) {
        this.v_end_s = v_end_s;
    }

    public Long getDownload_start_s() {
        return download_start_s;
    }

    public void setDownload_start_s(Long download_start_s) {
        this.download_start_s = download_start_s;
    }

    public Long getDownload_end_s() {
        return download_end_s;
    }

    public void setDownload_end_s(Long download_end_s) {
        this.download_end_s = download_end_s;
    }

    public Long getInstall_start_s() {
        return install_start_s;
    }

    public void setInstall_start_s(Long install_start_s) {
        this.install_start_s = install_start_s;
    }

    public Long getInstall_end_s() {
        return install_end_s;
    }

    public void setInstall_end_s(Long install_end_s) {
        this.install_end_s = install_end_s;
    }

    public Long getDeeplink_s() {
        return deeplink_s;
    }

    public void setDeeplink_s(Long deeplink_s) {
        this.deeplink_s = deeplink_s;
    }

    public Long getIdeeplink_s() {
        return ideeplink_s;
    }

    public void setIdeeplink_s(Long ideeplink_s) {
        this.ideeplink_s = ideeplink_s;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getRes_s() {
        return res_s;
    }

    public void setRes_s(Long res_s) {
        this.res_s = res_s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", req_id=").append(req_id);
        sb.append(", req_date=").append(req_date);
        sb.append(", ad_id=").append(ad_id);
        sb.append(", pos_id=").append(pos_id);
        sb.append(", slot_type=").append(slot_type);
        sb.append(", media_id=").append(media_id);
        sb.append(", dsp_id=").append(dsp_id);
        sb.append(", dsp_media_id=").append(dsp_media_id);
        sb.append(", dsp_pos_id=").append(dsp_pos_id);
        sb.append(", pv_s=").append(pv_s);
        sb.append(", pv_time=").append(pv_time);
        sb.append(", clikc_s=").append(clikc_s);
        sb.append(", clikc_time=").append(clikc_time);
        sb.append(", v_start_s=").append(v_start_s);
        sb.append(", v_end_s=").append(v_end_s);
        sb.append(", download_start_s=").append(download_start_s);
        sb.append(", download_end_s=").append(download_end_s);
        sb.append(", install_start_s=").append(install_start_s);
        sb.append(", install_end_s=").append(install_end_s);
        sb.append(", deeplink_s=").append(deeplink_s);
        sb.append(", ideeplink_s=").append(ideeplink_s);
        sb.append(", ip=").append(ip);
        sb.append(", res_s=").append(res_s);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DataAll other = (DataAll) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getReq_id() == null ? other.getReq_id() == null : this.getReq_id().equals(other.getReq_id()))
            && (this.getReq_date() == null ? other.getReq_date() == null : this.getReq_date().equals(other.getReq_date()))
            && (this.getAd_id() == null ? other.getAd_id() == null : this.getAd_id().equals(other.getAd_id()))
            && (this.getPos_id() == null ? other.getPos_id() == null : this.getPos_id().equals(other.getPos_id()))
            && (this.getSlot_type() == null ? other.getSlot_type() == null : this.getSlot_type().equals(other.getSlot_type()))
            && (this.getMedia_id() == null ? other.getMedia_id() == null : this.getMedia_id().equals(other.getMedia_id()))
            && (this.getDsp_id() == null ? other.getDsp_id() == null : this.getDsp_id().equals(other.getDsp_id()))
            && (this.getDsp_media_id() == null ? other.getDsp_media_id() == null : this.getDsp_media_id().equals(other.getDsp_media_id()))
            && (this.getDsp_pos_id() == null ? other.getDsp_pos_id() == null : this.getDsp_pos_id().equals(other.getDsp_pos_id()))
            && (this.getPv_s() == null ? other.getPv_s() == null : this.getPv_s().equals(other.getPv_s()))
            && (this.getPv_time() == null ? other.getPv_time() == null : this.getPv_time().equals(other.getPv_time()))
            && (this.getClikc_s() == null ? other.getClikc_s() == null : this.getClikc_s().equals(other.getClikc_s()))
            && (this.getClikc_time() == null ? other.getClikc_time() == null : this.getClikc_time().equals(other.getClikc_time()))
            && (this.getV_start_s() == null ? other.getV_start_s() == null : this.getV_start_s().equals(other.getV_start_s()))
            && (this.getV_end_s() == null ? other.getV_end_s() == null : this.getV_end_s().equals(other.getV_end_s()))
            && (this.getDownload_start_s() == null ? other.getDownload_start_s() == null : this.getDownload_start_s().equals(other.getDownload_start_s()))
            && (this.getDownload_end_s() == null ? other.getDownload_end_s() == null : this.getDownload_end_s().equals(other.getDownload_end_s()))
            && (this.getInstall_start_s() == null ? other.getInstall_start_s() == null : this.getInstall_start_s().equals(other.getInstall_start_s()))
            && (this.getInstall_end_s() == null ? other.getInstall_end_s() == null : this.getInstall_end_s().equals(other.getInstall_end_s()))
            && (this.getDeeplink_s() == null ? other.getDeeplink_s() == null : this.getDeeplink_s().equals(other.getDeeplink_s()))
            && (this.getIdeeplink_s() == null ? other.getIdeeplink_s() == null : this.getIdeeplink_s().equals(other.getIdeeplink_s()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getRes_s() == null ? other.getRes_s() == null : this.getRes_s().equals(other.getRes_s()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getReq_id() == null) ? 0 : getReq_id().hashCode());
        result = prime * result + ((getReq_date() == null) ? 0 : getReq_date().hashCode());
        result = prime * result + ((getAd_id() == null) ? 0 : getAd_id().hashCode());
        result = prime * result + ((getPos_id() == null) ? 0 : getPos_id().hashCode());
        result = prime * result + ((getSlot_type() == null) ? 0 : getSlot_type().hashCode());
        result = prime * result + ((getMedia_id() == null) ? 0 : getMedia_id().hashCode());
        result = prime * result + ((getDsp_id() == null) ? 0 : getDsp_id().hashCode());
        result = prime * result + ((getDsp_media_id() == null) ? 0 : getDsp_media_id().hashCode());
        result = prime * result + ((getDsp_pos_id() == null) ? 0 : getDsp_pos_id().hashCode());
        result = prime * result + ((getPv_s() == null) ? 0 : getPv_s().hashCode());
        result = prime * result + ((getPv_time() == null) ? 0 : getPv_time().hashCode());
        result = prime * result + ((getClikc_s() == null) ? 0 : getClikc_s().hashCode());
        result = prime * result + ((getClikc_time() == null) ? 0 : getClikc_time().hashCode());
        result = prime * result + ((getV_start_s() == null) ? 0 : getV_start_s().hashCode());
        result = prime * result + ((getV_end_s() == null) ? 0 : getV_end_s().hashCode());
        result = prime * result + ((getDownload_start_s() == null) ? 0 : getDownload_start_s().hashCode());
        result = prime * result + ((getDownload_end_s() == null) ? 0 : getDownload_end_s().hashCode());
        result = prime * result + ((getInstall_start_s() == null) ? 0 : getInstall_start_s().hashCode());
        result = prime * result + ((getInstall_end_s() == null) ? 0 : getInstall_end_s().hashCode());
        result = prime * result + ((getDeeplink_s() == null) ? 0 : getDeeplink_s().hashCode());
        result = prime * result + ((getIdeeplink_s() == null) ? 0 : getIdeeplink_s().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getRes_s() == null) ? 0 : getRes_s().hashCode());
        return result;
    }
}