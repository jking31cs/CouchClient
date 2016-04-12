package com.jking31cs;

/**
 * Created by jking31cs on 4/12/16.
 */
public class CouchResponse {
    public Boolean ok;
    public String id;
    public String rev;

    public String error;
    public String reason;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CouchResponse{");
        sb.append("ok=").append(ok);
        sb.append(", id='").append(id).append('\'');
        sb.append(", rev='").append(rev).append('\'');
        sb.append(", error='").append(error).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
