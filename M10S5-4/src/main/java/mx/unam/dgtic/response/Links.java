package mx.unam.dgtic.response;



public class Links {
    private String self;
    private String first;
    private String last;
    private String prev;
    private String next;

    public Links() {
    }

    public Links(String self, String first, String last, String prev, String next) {
        this.self = self;
        this.first = first;
        this.last = last;
        this.prev = prev;
        this.next = next;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
