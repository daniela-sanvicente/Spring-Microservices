package mx.unam.dgtic.response;

public class Meta {
    private int totalPages;

    public Meta() {
    }

    public Meta(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
