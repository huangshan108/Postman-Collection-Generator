import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class GroupButtonUtils {

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}