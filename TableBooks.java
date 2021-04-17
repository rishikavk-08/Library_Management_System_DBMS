package sample;

public class TableBooks {

    int id,edition,price;
    String title,author,availability,location;

    public TableBooks(int id, String title, String author, int edition, int price, String availability, String location) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.price = price;
        this.availability = availability;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getEdition() {
        return edition;
    }

    public int getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }

    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
