package view;

import model.dao.StudentDAO;
import model.entity.Student;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.ShowMessageUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

class ImportTxt implements ActionListener {
    private final JTextArea area = new JTextArea(5, 5);    // �����ı���
    private final JFrame frame = new JFrame("��ѡ���ļ�");
    private final JButton open = new JButton("���ļ�");
    private final JButton insert = new JButton("����");
    private final JLabel label = new JLabel("����û�д򿪵��ļ�");
    private final JPanel butPan = new JPanel();
    private String fileName = null;

    public ImportTxt() {
        if (Student.students == null) {
            ShowMessageUtil.winMessage("���ݿ��ʼ��δ��ɣ�");
            System.exit(0);
        }
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        open.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        insert.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        this.butPan.add(open);    // ������м��밴ť
        this.butPan.add(insert);    // ������м��밴ť
        this.frame.setLayout(new BorderLayout(3, 3));
        this.frame.add(this.label, BorderLayout.NORTH);
        this.frame.add(this.butPan, BorderLayout.SOUTH);
        this.frame.add(new JScrollPane(this.area), BorderLayout.CENTER);
        this.frame.setSize(600, 600);
        this.frame.setLocation(400, 100);
        frame.getContentPane().setBackground(Color.gray);
        this.frame.setVisible(true);
        this.frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(false);
                    }
                }
        );
        this.open.addActionListener(this);
        this.insert.addActionListener(this);
    }

    public static void main(String[] args) {
        ImportTxt importTxt = new ImportTxt();

    }

    public void setVisible(boolean f) {
        frame.setVisible(f);
    }

    public void actionPerformed(ActionEvent e) {
        File file = null;    // �����ļ�
        int result = 0;    // ���ղ���״̬
        JFileChooser fileChooser = new JFileChooser();    // �ļ�ѡ���
        if (e.getSource() == this.open) {    // ��ʾִ�е��Ǵ򿪲���
            this.area.setText("");    // �򿪽�����������������
            fileChooser.setApproveButtonText("ȷ��");
            fileChooser.setDialogTitle("���ļ�");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".txt") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "�ı��ļ�(*.txt)";
                }
            });
            result = fileChooser.showOpenDialog(this.frame);
            if (result == JFileChooser.APPROVE_OPTION) {    //TODO ѡ�����ȷ����ť
                file = fileChooser.getSelectedFile();    //TODO �õ�ѡ����ļ�
                this.label.setText("ѡ����ļ�����Ϊ��" + file.getName());
                fileName = file.getPath();
            } else if (result == JFileChooser.CANCEL_OPTION) {
                this.label.setText("û��ѡ���κ��ļ�");
            } else {
                this.label.setText("�������ִ���");
            }
        }
        if (e.getSource() == this.insert && fileName != null) {    //TODO �ж��Ƿ�Ϊ�������
            try {
                int cnt = StudentDAO.importFromTXT(fileName, Student.students);
                System.out.println(cnt);
                if (cnt > 0)
                    JOptionPane.showMessageDialog(null, "�ɹ�����" + cnt + "�����ݵ����ݿ���", "��Ϣ��ʾ", JOptionPane.PLAIN_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(null, "����ʧ�ܣ������������Ѿ�����", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}



