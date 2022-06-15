package com.mk.adx.entity.json.response;

import java.io.Serializable;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/27  13:54
 */
public class ResponseFeedBack implements Serializable {
    private Integer code;
    private String msg;

    public ResponseFeedBack() {
        this.code = 1;
        this.msg = "success";
    }


    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseFeedBack)) {
            return false;
        } else {
            ResponseFeedBack other = (ResponseFeedBack) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }


                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResponseFeedBack;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $code = this.getCode();
//        int result = result * 59 + ($code == null ? 43 : $code.hashCode());
//        Object $msg = this.getMsg();
//        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
//        Object $data = this.getData();
//        result = result * 59 + ($data == null ? 43 : $data.hashCode());
//        return result;
//    }

    public String toString() {
        return "ResponseResult(code=" + this.getCode() + ", msg=" + this.getMsg() + ")";
    }

    public ResponseFeedBack(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
