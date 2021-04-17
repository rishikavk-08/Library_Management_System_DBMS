package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchController {


    @FXML
    private TextField Title1;
    @FXML
    private TextField author2;
    @FXML
    private Button s2;
    @FXML
    private TableView<TableBooks> table;

    @FXML
    private TableColumn<TableBooks, Integer> id;

    @FXML
    private TableColumn<TableBooks, String> title;

    @FXML
    private TableColumn<TableBooks, String> author;

    @FXML
    private TableColumn<TableBooks, Integer> edition;

    @FXML
    private TableColumn<TableBooks, Integer> price;

    @FXML
    private TableColumn<TableBooks, String> availability;

    @FXML
    private TableColumn<TableBooks, String> loc;
    @FXML
    private Button s1;
    @FXML
    private TextField SbookID;
    @FXML
    private Button Back;




    public void Backclicked()
    {
        Stage stage = (Stage) Back.getScene().getWindow();
        stage.close();
    }
    Connection myConn = null;
    Statement myStmt = null;
    ResultSet rs = null;
    ObservableList<TableBooks> list = FXCollections.observableArrayList();
    public void Search1(ActionEvent event) {
        table.getItems().clear();
        if(!SbookID.getText().isBlank()  ){
            String bookid=SbookID.getText();
            //System.out.print(bookid);

            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");

                //Statement Statement1 = myConn.createStatement();
                //Statement Statement2 = myConn.createStatement();
                //String reg = "INSERT into Transactions"+"(M_ID,Book_ID,issue_date,return_date)values"+"(?,?,?,?)" ;

                String reg2= "Select * from books where bookid = ?  ";
                PreparedStatement preparedStmt1 = myConn.prepareStatement(reg2);
                preparedStmt1.setString (1, bookid);
                rs = preparedStmt1.executeQuery();


                boolean flag=false;
                while (rs.next()) {
                    flag=true;
                    list.add(new TableBooks(rs.getInt("bookid"),rs.getString("title"),rs.getString("author"),rs.getInt("edition"),rs.getInt("price"),rs.getString("availability"),rs.getString("location")));
                }
                if(!flag){
                    infoBox("Book not found!",null,"Alert");
                }
                try {   //set properties to cells of each column of the table
                    id.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("id"));
                    title.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("title"));
                    author.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("author"));
                    edition.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("edition"));
                    price.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("price"));
                    availability.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("availability"));
                    loc.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("location"));
                    table.setItems(list);
                    rs.close();
                } catch (Exception e) { //Exception is handled
                    e.printStackTrace();


                }
            }

            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoBox("please enter bookID and memberID",null,"ERROR!!");
        }


    }


    //ObservableList<TableBooks> list1 = FXCollections.observableArrayList();
    public void Search2(ActionEvent event) {
        table.getItems().clear();
        if(!Title1.getText().isBlank()  ){
            String tile=Title1.getText();

            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");

                //Statement Statement1 = myConn.createStatement();
                //Statement Statement2 = myConn.createStatement();
                //String reg = "INSERT into Transactions"+"(M_ID,Book_ID,issue_date,return_date)values"+"(?,?,?,?)" ;
                String reg2= "Select * from books where title = ?  ";
                PreparedStatement preparedStmt1 = myConn.prepareStatement(reg2);
                preparedStmt1.setString (1, tile);
                rs = preparedStmt1.executeQuery();
                boolean flag=false;
                while (rs.next()) {
                    flag=true;
                    list.add(new TableBooks(rs.getInt("bookid"),rs.getString("title"),rs.getString("author"),rs.getInt("edition"),rs.getInt("price"),rs.getString("availability"),rs.getString("location")));
                }
                if(!flag){
                    infoBox("Book not found!",null,"Alert");
                }
                try {   //set properties to cells of each column of the table
                    id.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("id"));
                    title.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("title"));
                    author.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("author"));
                    edition.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("edition"));
                    price.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("price"));
                    availability.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("availability"));
                    loc.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("location"));
                    table.setItems(list);
                    rs.close();
                } catch (Exception e) { //Exception is handled
                    e.printStackTrace();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoBox("please enter bookID and memberID",null,"ERROR!!");
        }


    }
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        ButtonType buttonTypeOk = new ButtonType("Ok");//button Ok is created
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);//button Cancel is created
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(false);
        alert.showAndWait();
    }

    public void Search3(ActionEvent event) {
        table.getItems().clear();
        if(!author2.getText().isBlank()  ){
            String aut=author2.getText();

            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");

                //Statement Statement1 = myConn.createStatement();
                //Statement Statement2 = myConn.createStatement();
                //String reg = "INSERT into Transactions"+"(M_ID,Book_ID,issue_date,return_date)values"+"(?,?,?,?)" ;
                String reg2= "Select * from books where author = ?  ";
                PreparedStatement preparedStmt1 = myConn.prepareStatement(reg2);
                preparedStmt1.setString (1, aut);
                rs = preparedStmt1.executeQuery();
                boolean flag=false;
                while (rs.next()) {
                    flag=true;
                    list.add(new TableBooks(rs.getInt("bookid"),rs.getString("title"),rs.getString("author"),rs.getInt("edition"),rs.getInt("price"),rs.getString("availability"),rs.getString("location")));
                }
                if(!flag){
                    infoBox("Book not found!",null,"Alert");
                }
                try {   //set properties to cells of each column of the table
                    id.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("id"));
                    title.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("title"));
                    author.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("author"));
                    edition.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("edition"));
                    price.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("price"));
                    availability.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("availability"));
                    loc.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("location"));
                    table.setItems(list);
                    rs.close();
                } catch (Exception e) { //Exception is handled
                    e.printStackTrace();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoBox("please enter bookID and memberID",null,"ERROR!!");
        }

    }
}