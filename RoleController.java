package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class RoleController {

    @FXML
    private Button Member;

    @FXML
    private Button Librarian;

    public void Memberclicked(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("MemberLogin.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Member Page");
            loginStage.setScene(new Scene(root, 701, 467));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void Librarianclicked(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Librarian Login");
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