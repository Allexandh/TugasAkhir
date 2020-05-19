package umn.ac.id.tugasakhir;

public class Chat {
    public String guideid, userid, message;
    public Integer order;

    public Chat() {

    }

    public Chat(String guideid, String userid, String message, Integer order) {
        this.guideid = guideid;
        this.userid = userid;
        this.message = message;
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getGuideid() {
        return guideid;
    }
    public void setGuideid(String guideid) {
        this.guideid = guideid;
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


}
