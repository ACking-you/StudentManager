package view;

import javax.swing.*;
import java.awt.*;


public class AboutSystem extends JFrame {
    private JLabel jlabel1;
    private JLabel jlabel2;
    private JLabel jLabel3;
    private JLabel jLbel4;
    private Font font;

    public AboutSystem() {
        setTitle("���ڱ�ϵͳ");//������������
        setSize(600, 450);//����������С
        setLocationRelativeTo(null);//��������ʾ����Ļ����
        AboutSystem();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//������ϽǵĹرգ�ֻ�رձ����ڣ���Ӱ��ס����
        setVisible(true);//���ô��ڿɼ�
        setResizable(true);//���ô��ڴ�С���Ըı�
    }

    public static void main(String[] args) {
        new AboutSystem();
    }

    private void AboutSystem() {
        setLayout(null);//�Ծ��Բ��ֵķ�ʽ����
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        font = new Font("΢���ź�", Font.PLAIN, 20);
        jlabel1 = new JLabel("ѧ������ϵͳ");
        jlabel1.setBounds(230, 50, 150, 50);
        jlabel1.setFont(font);
        jlabel2 = new JLabel("�༶��20�ƿ�2��");
        jlabel2.setBounds(200, 100, 450, 50);
        jlabel2.setFont(font);
        jLabel3 = new JLabel("���ߣ�������");
        jLabel3.setBounds(200, 150, 450, 50);
        jLabel3.setFont(font);
        jLbel4 = new JLabel("������ڣ�2022-2-28");
        jLbel4.setBounds(200, 200, 450, 50);
        jLbel4.setFont(font);

        add(jlabel1);
        add(jlabel2);
        add(jLabel3);
        add(jLbel4);
    }
}
