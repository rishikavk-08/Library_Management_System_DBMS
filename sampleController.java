
package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class sampleController {

    @FXML
    private Button Logout;

    @FXML
    private Button AddMember;

    @FXML
    private Button DeleteMember;

    @FXML
    private Button DeleteBook;

    @FXML
    private Button AddBook;

    @FXML
    private Button updateMemberButton;

    @FXML
    private Button updatebookDetailsButton;

    @FXML
    private Button memberButton;

    @FXML
    private Button booksButton;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button librarianButton;

    @FXML
    private Button SearchbyId;

    @FXML
    private Button SearchbyTitle;

    @FXML
    private Button SearchbyAuthor;



    @FXML
    private TextField IbookID;
    @FXML
    private TextField ImemberID;
    @FXML
    private TextField doi;
    @FXML
    private Button issueButton;

    @FXML
    private TextField RbookID;
    @FXML
    private TextField RmemberID;
    @FXML
    private TextField dor;
    @FXML
    private Button returnButton;

    @FXML
    private TextField bookIDtext;
    @FXML
    private TextField bookTitleText;
    @FXML
    private TextField bookAuthorText;



    Connection myConn = null;
    Statement myStmt = null;
    ResultSet myRe = null;






    public void IssueBooks(Event event) {
    }
    // insert into member values ("AA51","BB51",29382928,"street51","51@gmail.com","staff",null,"HOD" ,51);
    //insert into transactions values ("T51",51,21,sysdate(),sysdate());

    public void issuebuttonclicked(ActionEvent event) throws SQLException {
        // System.out.printf("got it!!");
        if(!IbookID.getText().isBlank() && !ImemberID.getText().isBlank() ){
            String bookid=IbookID.getText();
            String memberid=ImemberID.getText();
            String date=doi.getText();

            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");

                //Statement Statement1 = myConn.createStatement();
                //Statement Statement2 = myConn.createStatement();
                String reg = "INSERT into Transactions"+"(M_ID,Book_ID,issue_date,return_date,price)values"+"(?,?,?,?,?)" ;
                String reg2= "Select * from books where bookid = ? and availability = 'yes' ";
                PreparedStatement preparedStmt1 = myConn.prepareStatement(reg2);
                preparedStmt1.setString (1, bookid);
                myRe =preparedStmt1.executeQuery();




                if(myRe.next()){
                    PreparedStatement preparedStmt = myConn.prepareStatement(reg);
                    //preparedStmt.setString (1, "T55");
                    preparedStmt.setString (1, bookid);
                    preparedStmt.setString   (2, memberid);
                    preparedStmt.setString(3, date);
                    String dt = date;  // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(dt));
                    c.add(Calendar.DATE, 15);
                    dt = sdf.format(c.getTime());
                    preparedStmt.setString   (4, dt);
                    preparedStmt.setString(5,myRe.getString("price"));
                    int res=preparedStmt.executeUpdate();
                    if(res==1){
                        //System.out.printf("done!");
                        String reg3= "Update books set availability = 'no' where bookid=? ";
                        PreparedStatement preparedStmt3 = myConn.prepareStatement(reg3);
                        preparedStmt3.setString (1, bookid);
                        int result=preparedStmt3.executeUpdate();

                        infoBox(" Return the book before : "+dt,null,"Successfully issues your book");
                    }
                }
                else{
                    infoBox("This book is not available. ",null,"ERROR");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoBox("Please enter bookID and memberID",null,"ERROR!!");
        }
    }


    public void returnbuttonclicked(ActionEvent event) throws SQLException {
        // System.out.printf("got it!!");
        if(!RbookID.getText().isBlank() && !RmemberID.getText().isBlank() ){
            String bookid=RbookID.getText();
            String memberid=RmemberID.getText();
            String date=dor.getText();

            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");

//                //Statement Statement1 = myConn.createStatement();
//                //Statement Statement2 = myConn.createStatement();
//                //String reg = "INSERT into Transactions"+"(T_ID,M_ID,Book_ID,issue_date,return_date)values"+"(?,?,?,?,?)" ;
                String reg2= "Select * from books where bookid = ? and availability = 'no' ";
                PreparedStatement preparedStmt1 = myConn.prepareStatement(reg2);
                preparedStmt1.setString (1, bookid);
                String reg4= "Select * from transactions where Book_ID = ? ";
                PreparedStatement preparedStmt4 = myConn.prepareStatement(reg4);
                preparedStmt4.setString (1, bookid);
                ResultSet R =preparedStmt4.executeQuery();
                java.util.Date d=null;
                if(R.next()){
                    d=R.getDate("return_date");}

//
                //SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");


                //Date d1=new SimpleDateFormat("yyyy/MM/dd")(d));
                Date d2=new SimpleDateFormat("yyyy-MM-dd").parse(date);


                // Get msec from each, and subtract.
                long diff = d2.getTime() - d.getTime();
                long days_difference = (diff / (1000*60*60*24)) % 365;


                myRe =preparedStmt1.executeQuery();
                if(myRe.next()){
                    String reg3= "Update books set availability = 'yes' where bookid=? ";
                    PreparedStatement preparedStmt3 = myConn.prepareStatement(reg3);
                    preparedStmt3.setString (1, bookid);
                    int result=preparedStmt3.executeUpdate();
                    double fine=days_difference*0.5;
                    infoBox(" Please pay. Fine amount is "+fine,null,"Successfully issues your book");
                }

                else{
                    infoBox("This book can not be returned ",null,"ERROR");
                }
            }
            catch(Exception e) {
                infoBox("Please check date format",null,"ERROR");
                e.printStackTrace();
            }
        }
        else{
            infoBox("Please enter bookID and memberID",null,"ERROR!!");
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

    public void Logoutclicked()
    {
        Stage stage = (Stage) Logout.getScene().getWindow();
        stage.close();
    }

    public void AddMemberclicked()
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("Registrationform.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Add member");
            loginStage.setScene(new Scene(root, 701, 656));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void DeleteMemberclicked()
    {
        try
        {
            Stage stage = (Stage) DeleteMember.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("DeleteMember.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Delete member");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void DeleteBookclicked()
    {
        try
        {
            Stage stage = (Stage) DeleteBook.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("DeleteBook.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Delete book");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void AddBookclicked()
    {
        try
        {
            Stage stage = (Stage) AddBook.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Add book");
            loginStage.setScene(new Scene(root, 703, 633));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void UpdateMemberclicked()
    {
        try
        {
            Stage stage = (Stage) updateMemberButton.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("UpdateMember.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Update Member");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void UpdateBookclicked()
    {
        try
        {
            Stage stage = (Stage) updatebookDetailsButton.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("UpdateBook.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Update Book");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void Memberclicked()
    {
        try
        {
            Stage stage = (Stage) memberButton.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowMember.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Member Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void Booksclicked()
    {
        try
        {
            Stage stage = (Stage) booksButton.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowBooks.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Book Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void Transactionclicked()
    {
        try
        {
            Stage stage = (Stage) transactionsButton.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowTransaction.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Transaction Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void Librarianclicked()
    {
        try
        {
            Stage stage = (Stage) librarianButton.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowLibrarian.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Librarian Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void SearchbyIdclicked()
    {
        try
        {
            Stage stage = (Stage) SearchbyId.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowBooks.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Book Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void SearchbyTitleclicked()
    {
        try
        {
            Stage stage = (Stage) SearchbyTitle.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowBooks.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Book Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void SearchbyAuthorclicked()
    {
        try
        {
            Stage stage = (Stage) SearchbyAuthor.getScene().getWindow();
            stage.hide();
            Parent root = FXMLLoader.load(getClass().getResource("ShowBooks.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Book Details");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

}
