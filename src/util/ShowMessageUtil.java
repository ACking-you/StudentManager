package util;

import javax.swing.*;

public class ShowMessageUtil {

    public static void winMessage(String str) {
        JOptionPane.showMessageDialog(null, str, "ע��",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

