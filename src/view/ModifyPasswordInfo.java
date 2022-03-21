package view;

import model.dao.UserDAO;
import model.entity.Student;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.MD5Util;
import util.ShowMessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class ModifyPasswordInfo implements ActionListener {
    private final JPasswordField old_text = new JPasswordField();
    private final JPasswordField new_text = new JPasswordField();
    private final JPasswordField query_text = new JPasswordField();
    private final JLabel old_password = new JLabel("ԭ����:");
    private final JLabel new_password = new JLabel("������:");
    private final JLabel query_password = new JLabel("ȷ������:");
    private final JFrame frame = new JFrame("������������Ϣ");
    private final JButton modify = new JButton("�޸�");
    private final JButton exit = new JButton("ȡ��");
    private final JButton back = new JButton("����");
    Font fnt = new Font("Serief", Font.PLAIN + Font.BOLD, 17);


    public ModifyPasswordInfo() {
        if (Student.students == null) {
            ShowMessageUtil.winMessage("���ݿ��ʼ��δ��ɣ�");
            System.exit(0);
        }
        this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        old_password.setBounds(40, 0, 100, 80);
        new_password.setBounds(40, 90, 100, 80);
        query_password.setBounds(40, 195, 100, 80);
        old_text.setBounds(120, 28, 200, 30);
        new_text.setBounds(120, 118, 200, 30);
        query_text.setBounds(120, 223, 200, 30);
        modify.setBounds(19, 300, 80, 30);
        exit.setBounds(139, 300, 80, 30);
        back.setBounds(259, 300, 80, 30);
        modify.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        exit.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        back.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        old_password.setFont(fnt);
        new_password.setFont(fnt);
        query_password.setFont(fnt);
        this.frame.add(old_password);
        this.frame.add(new_password);
        this.frame.add(query_password);
        this.frame.add(old_text);
        this.frame.add(new_text);
        this.frame.add(query_text);
        this.frame.add(modify);
        this.frame.add(exit);
        this.frame.add(back);
        this.frame.setLayout(null);
        this.frame.setSize(450, 430);
        this.frame.setLocation(400, 100);
        // this.frame.getContentPane().setBackground(Color.green);
        this.frame.setVisible(true);
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
            }
        });
        this.modify.addActionListener(this);
        this.exit.addActionListener(this);
        this.back.addActionListener(this);
    }

    public static void main(String[] args) {
        ModifyPasswordInfo modifyPasswordInfo = new ModifyPasswordInfo();
    }

    public void setVisible(boolean f) {
        frame.setVisible(f);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.modify) {// �ж��Ƿ����޸Ĳ���

            String old_psw = MD5Util.stringToMD5(new String(old_text.getPassword()));
            String new_psw = new String(new_text.getPassword());
            String query_psw = new String(query_text.getPassword());
            if (!(old_psw.equals(Login.password_cache))) {
                JOptionPane.showMessageDialog(null, "ԭ���벻��ȷ", "��Ϣ��ʾ",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!(new_psw.equals(query_psw))) {
                JOptionPane.showMessageDialog(null, "�������벻һ�£�����", "��Ϣ��ʾ",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                try (UserDAO ud = UserDAO.getInstance(true)) {
                    try {
                        ud.modifyPassword(new_psw);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Login.password_cache = MD5Util.stringToMD5(new_psw);
                JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��Ϣ��ʾ",
                        JOptionPane.PLAIN_MESSAGE);
                old_text.setText("");
                new_text.setText("");
                query_text.setText("");
                setVisible(false);
            }
        }
        if (e.getSource() == this.exit) { // �ж��Ƿ���ȡ������
            old_text.setText("");
            new_text.setText("");
            query_text.setText("");
            frame.setVisible(false);
        }
        if (e.getSource() == this.back) { // �ж��Ƿ��Ƿ��ز���
            old_text.setText("");
            new_text.setText("");
            query_text.setText("");
            frame.setVisible(false);
        }
    }
}
