package mx.unam.dgtic.response;

import java.util.*;

public class ApiResponse {
    private Map<String, Object> meta;
    private List<Data> data;
    private List<ErrorDetail> errors;
    private Links links;

    public ApiResponse() {
    }

    public ApiResponse(Map<String, Object> meta, List<Data> data, List<ErrorDetail> errors, Links links) {
        this.meta = meta;
        this.data = data;
        this.errors = errors;
        this.links = links;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
