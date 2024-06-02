package mx.unam.dgtic.response;

import java.util.*;

public class ErrorDetail {
    private String status;
    private String title;
    private String detail;
    private Map<String, Object> source;

    public ErrorDetail() {
    }

    public ErrorDetail(String status, String title, String detail, Map<String, Object> source) {
        this.status = status;
        this.title = title;
        this.detail = detail;
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }
}
