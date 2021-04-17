package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBookController {


    @FXML
    private Button addBook;
    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextField edition;
    @FXML
    private TextField price;
    @FXML
    private TextField av;
    @FXML
    private TextField locn;
    @FXML
    private Button CancelButton;
    Connection myConn;
    ResultSet myRe;

    public void AddBookclick(ActionEvent event) throws SQLException {
         //System.out.printf("got it!!");

        if(!title.getText().isBlank() && !author.getText().isBlank() && !edition.getText().isBlank() && !price.getText().isBlank() &&!locn.getText().isBlank() && !av.getText().isBlank()  ){
            String Title= title.getText();
            String Author=author.getText();
            String Edition=edition.getText();
            String Price=price.getText();
            String loc= locn.getText();
            String avail=av.getText();
            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");
                String reg = "INSERT into books"+"(title,author,edition,price,availability,location)values"+"(?,?,?,?,?,?)" ;
                PreparedStatement preparedStmt = myConn.prepareStatement(reg);
                //preparedStmt.setString (1, "21");
                preparedStmt.setString (1, Title);
                preparedStmt.setString   (2, Author);
                preparedStmt.setString(3, Edition);
                preparedStmt.setString   (4, Price);
                preparedStmt.setString   (5, avail);
                preparedStmt.setString   (6, loc);
                int res=preparedStmt.executeUpdate();
                    if(res==1){
                        infoBox(" Book added successfully",null,"Success!!");
                    }
                    else{
                        infoBox("issue in adding book ",null,"ERROR");
                    }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoBox("please enter all details",null,"ERROR!!");
        }
    }
    public void CancelButtonclicked()
    {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Librarian Page");
            loginStage.setScene(new Scene(root, 703, 547));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
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
}

