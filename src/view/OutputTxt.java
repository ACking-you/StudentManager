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

class OutputTxt implements ActionListener {
    private final JTextArea area = new JTextArea(5, 5);    // �����ı���
    private final JFrame frame = new JFrame("��ѡ���ļ�");
    private final JButton open = new JButton("���ļ�");
    private final JButton output = new JButton("����");
    private final JLabel label = new JLabel("����û�д򿪵��ļ�");
    private final JPanel butPan = new JPanel();
    private String fileName = null;

    public OutputTxt() {
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
        output.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        this.butPan.add(open);    // ������м��밴ť
        this.butPan.add(output);    // ������м��밴ť
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
        this.output.addActionListener(this);
    }

    public static void main(String[] args) {
        OutputTxt outputTxt = new OutputTxt();
    }

    public void setVisible(boolean f) {
        frame.setVisible(f);
    }

    public void actionPerformed(ActionEvent e) {
        int result = 0;    // ���ղ���״̬
        JFileChooser fileChooser = new JFileChooser();    // �ļ�ѡ���
        if (e.getSource() == this.open) {    // ��ʾִ�е��Ǵ򿪲���
            this.area.setText("");    // �򿪽�����������������
            fileChooser.setApproveButtonText("ȷ��");
            fileChooser.setDialogTitle("���ļ�");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".txt") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "�ļ��л��ļ�(*.txt)";
                }
            });
            result = fileChooser.showOpenDialog(this.frame);
            if (result == JFileChooser.APPROVE_OPTION) {    // ѡ�����ȷ����ť
                File file = fileChooser.getSelectedFile();    // �õ�ѡ����ļ�
                this.label.setText("ѡ����ļ�����Ϊ��" + file.getName());
                fileName = file.getPath();
            } else if (result == JFileChooser.CANCEL_OPTION) {
                this.label.setText("û��ѡ���κ��ļ�");
            } else {
                this.label.setText("�������ִ���");
            }
        }
        if (e.getSource() == this.output && fileName != null) {    // �ж��Ƿ��ǵ�������
            try {
                if (new File(fileName).isDirectory()) {
                    fileName = fileName + "\\����txt�ļ�.txt";
                }
                int cnt = StudentDAO.ExportTXT(fileName, Student.students);
                if (cnt > 0) {
                    JOptionPane.showMessageDialog(null, "�ɹ�����" + cnt + "�����ݵ��ļ���", "��Ϣ��ʾ", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "����ʧ��", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
		
	




