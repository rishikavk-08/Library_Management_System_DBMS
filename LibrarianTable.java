package sample;

public class LibrarianTable {

    String id;
    String first,last,shift,user,pass;

    public LibrarianTable(String id, String first, String last, String shift,String user,String pass) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.shift = shift;
        this.user = user;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getShift() {
        return shift;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
