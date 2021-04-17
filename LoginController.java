package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private Button LoginButton;

    @FXML
    private Label Loginlabel;

    @FXML
    private TextField usernamefield;

    @FXML
    private PasswordField passwordfield;
    Connection myConn = null;
    Statement myStmt = null;
    ResultSet myRe = null;

    @Override
    public void initialize(URL url, ResourceBundle resources){

    }
    public void loginbuttonclicked(ActionEvent event) throws SQLException {
//        String user = "librarian";
//        String pass = "dbms";

        if(!usernamefield.getText().isBlank() && !passwordfield.getText().isBlank())
        {
            boolean flag = validateLogin();
            if (flag) {
                Loginlabel.setText("Login successful!");
                Gotolibrarian();
                Stage stage = (Stage) LoginButton.getScene().getWindow();
                stage.close();
            }
            else {
                Loginlabel.setText("Invalid login! Please enter correct username and password.");
            }
        }
        else
        {
            Loginlabel.setText("Please enter username and password.");
        }
    }

    public void Cancelbuttononaction(ActionEvent event)
    {
       Stage stage = (Stage) CancelButton.getScene().getWindow();
       stage.close();
    }

    public void Gotolibrarian()
    {
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

    public boolean validateLogin() {

        String usr = usernamefield.getText();//emailid and password are retrieved from event
        String pwd = passwordfield.getText();
        String reg = "SELECT * FROM librarians WHERE BINARY username = ? and BINARY password = ?";
            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");
                //myStmt = myConn.createStatement();
                //myRe=myStmt.executeQuery("SELECT count(1) FROM librarian_login WHERE username='" + usernamefield.getText() + "' AND password='" + passwordfield.getText() + "'");
                PreparedStatement preparedStatement1 = myConn.prepareStatement(reg);

                preparedStatement1.setString(1, usr);
                preparedStatement1.setString(2, pwd);
                myRe= preparedStatement1.executeQuery();
                if(myRe.next())
                {
                    return true;
                }

            }
            catch(Exception e) { //SQL Exception is caught
            e.printStackTrace();

            }
        return false;
    }

}


