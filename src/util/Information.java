package util;


import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import view.AddStudentInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Information extends JFrame implements ActionListener {
    private JButton button1, button2;
    private JLabel promptsentence;
    private Font font;
    private AddStudentInfo addStudentInfo = null;

    public Information(AddStudentInfo addStudentInfo) {
        this.addStudentInfo = addStudentInfo;
        setTitle("��ӳɹ���ʾ��");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(550, 350);
        setLocationRelativeTo(null);//��������ʾ����Ļ����
        setStyle();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//������ϽǵĹرգ�ֻ�رձ����ڣ���Ӱ��ס����
        setVisible(true);//���ô��ڿɼ�
        setResizable(true);//���ô��ڴ�С���Ըı�
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Information(null);
    }

    public void setStyle() {
        setLayout(null);
        promptsentence = new JLabel("���������Ϣ�Ѿ��ɹ����棬�Ƿ������ӣ�");
        font = new Font("����", Font.PLAIN, 20);
        promptsentence.setFont(font);
        promptsentence.setBounds(50, 80, 450, 50);

        button1 = new JButton("��");
        button1.setBounds(150, 150, 70, 40);
        button1.setFont(font);
        button1.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.blue));//���ð�ť�ı�����ɫ

        button2 = new JButton("��");
        button2.setBounds(300, 150, 70, 40);
        button2.setFont(font);
        button2.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.red));//���ð�ť�ı�����ɫ


        add(promptsentence);
        add(button1);
        add(button2);

        button1.addActionListener(this);
        button2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String cmd = e.getActionCommand();
        if (cmd.equals("��")) {
            dispose();
            AddStudentInfo.clearText();
        } else if (cmd.equals("��")) {
            dispose();
            if (addStudentInfo != null) {
                addStudentInfo.setVisible(false);
            }
        }
    }
}
