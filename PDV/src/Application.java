import javax.swing.*;
import java.awt.*;
import br.com.erick.UI.*;

public class Application{
    public static void main(String[] args){

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new ApplicationFrame().setVisible(true);
        });
    }
}
