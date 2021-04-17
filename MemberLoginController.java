package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MemberLoginController {

    @FXML
    private Button Register;

    @FXML
    private Button Login;

    public void Registerclicked(ActionEvent event)
    {

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("Registrationform.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Register!");
            loginStage.setScene(new Scene(root, 701, 656));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void Loginclicked(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("MemberLogin2.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Member Login");
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