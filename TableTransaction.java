package sample;

public class TableTransaction {

    int tid,bookid,memberid,price;
    String issue,returndate;

    public TableTransaction(int tid, int bookid, int memberid, String issue, String returndate, int price) {
        this.tid = tid;
        this.bookid = bookid;
        this.memberid = memberid;
        this.issue = issue;
        this.returndate = returndate;
        this.price = price;
    }

    public int getTid() {
        return tid;
    }

    public int getBookid() {
        return bookid;
    }

    public int getMemberid() {
        return memberid;
    }

    public int getPrice() {
        return price;
    }

    public String getIssue() {
        return issue;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }
}
