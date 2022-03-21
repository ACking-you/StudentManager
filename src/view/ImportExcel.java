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
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ImportExcel implements ActionListener/*,PropertyChangeListener*/ {
    private final JFrame jf = new JFrame("���뵽���ݿ�");
    private final TextField textpath = new TextField();
    private final Font font2 = new Font("΢���ź�", 0, 20);
    private final Toolkit toolkit = Toolkit.getDefaultToolkit(); // ���ϵͳĬ�Ϲ�����
    private final Dimension sc = toolkit.getScreenSize(); // �����Ļ�ߴ�
    private final JButton imbtn = new JButton("���뵽���ݿ�");
    private final JLabel fname = new JLabel("�����ļ�·����");
    private final Container con = jf.getContentPane(); // ������
    private final JButton open = new JButton("��");
    public String filepath;

    public ImportExcel() {
        if (Student.students == null) {
            ShowMessageUtil.winMessage("���ݿ��ʼ��δ��ɣ�");
            System.exit(0);
        }
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.setLayout(null);
        jf.setSize(650, 400);
        jf.setLocation((sc.width - 650) / 2, (sc.height - 400) / 2);
        jf.setResizable(false);// ���ڴ�С���ɱ�
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        con.setVisible(true);
        con.add(imbtn);
        con.add(open);
        con.add(textpath);
        con.add(fname);
        textpath.setBounds(200, 120, 250, 25);
        textpath.setFont(font2);
        fname.setBounds(60, 120, 150, 25);
        fname.setFont(font2);

        open.setBounds(470, 120, 100, 25);
        open.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        open.addActionListener(this);

        imbtn.setBounds(220, 300, 200, 30);
        imbtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        imbtn.addActionListener(this);
    }

    public static void main(String[] args) {
        ImportExcel importExcel = new ImportExcel();
    }

    public void setVisible(boolean f) {
        jf.setVisible(f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.imbtn) {  //TODO ��ʼ�������
            filepath = textpath.getText();
            try {
                StudentDAO.importFromExcle(filepath, Student.students);
            } catch (IOException | SQLException | InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.open) {  //TODO �õ�ѡ���ļ���·��
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setApproveButtonText("ȷ��");
            fileChooser.setDialogTitle("���ļ�");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".xls") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "Excle������(*.xls)";
                }
            });
            int ret = fileChooser.showOpenDialog(this.open);
            if (ret == JFileChooser.APPROVE_OPTION) {    //TODO ѡ�����ȷ����ť
                File file = fileChooser.getSelectedFile();    //TODO �õ�ѡ����ļ�
                textpath.setText(file.getPath());
            }
        }
    }
}
        
	

