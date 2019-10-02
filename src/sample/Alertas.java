package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.management.Notification;
import java.awt.event.ActionEvent;

public class Alertas {

    public static void MostrarAlerta(String msg, NotificationType Type, String Title){
        TrayNotification Notificacion = new TrayNotification();
        Notificacion.setAnimationType(AnimationType.POPUP);
        Notificacion.setTitle(Title);
        Notificacion.setMessage(msg);
        Notificacion.setNotificationType(Type);
        Notificacion.showAndDismiss(Duration.millis(1000));
    }
}
