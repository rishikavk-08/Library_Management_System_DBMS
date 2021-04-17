package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrationformController {

    @FXML
    private Button CancelButton;

    @FXML
    private Button Register;

    @FXML
    private Label RegisterLabel;

    @FXML
    private PasswordField Password;

    @FXML
    private PasswordField ConfirmPassword;

    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField username;
    Connection myConn;



    public void Registerclicked()
    {
        if(!firstname.getText().isBlank() && !lastname.getText().isBlank() && !address.getText().isBlank() && !email.getText().isBlank() && !phone.getText().isBlank() && !username.getText().isBlank())
        {
            //RegisterLabel.setText("Enter data in all given fields to complete registration");
            String first= firstname.getText();
            String last=lastname.getText();
            String add=address.getText();
            String mail=email.getText();
            String ph= phone.getText();
            String user=username.getText();
            String pass=Password.getText();
            String conf=ConfirmPassword.getText();
            try {
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");
                String reg = "INSERT into member(Firstname,lastname,phone_number,address,emailID,username,password)values"+"(?,?,?,?,?,?,?)" ;
                PreparedStatement preparedStmt = myConn.prepareStatement(reg);
                //preparedStmt.setString (1, "11");
                preparedStmt.setString (1, first);
                preparedStmt.setString   (2, last);
                preparedStmt.setString(3, ph);
                preparedStmt.setString   (4, add);
                preparedStmt.setString   (5, mail);
                preparedStmt.setString   (6, user);
                preparedStmt.setString   (7, pass);
                //preparedStmt.setString   (9, conf);

                int res=preparedStmt.executeUpdate();
                if(res==1){
                    infoBox(" Member added successfully",null,"Success!!");
                }
                else {
                    infoBox("issue in adding member ", null, "ERROR");
                }
            }
            catch(Exception e) {
                e.printStackTrace();

            }
        }
        else  if(firstname.getText().isBlank() || lastname.getText().isBlank() || address.getText().isBlank() || email.getText().isBlank() || phone.getText().isBlank() || username.getText().isBlank()){
            RegisterLabel.setText("Enter all fields");
        }

        else if(Password.getText().equals(ConfirmPassword.getText()) == false)
        {
            RegisterLabel.setText("Enter same password twice in the Password and ConfirmPassword fields");
        }
        else
        {
            RegisterLabel.setText("Congratulations! You are a member now!");
        }
    }

    public void CancelButtonclicked(ActionEvent event)
    {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
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
