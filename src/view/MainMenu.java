package view;

import model.dao.StudentDAO;
import model.entity.Student;
import util.ShowMessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;


/*
 * ��½�ɹ���������
 */
public class MainMenu implements ActionListener {
    static String IMG_PATH = "src/images/background";
    ImageIcon[] imgs = null;
    private JFrame mainmenu;
    private JMenu menuFile1, menuFile2, menuFile3;
    private JMenuBar menuBar1;
    private JMenuItem addStudentInfo, queryStudentInfo, modifyPassword, Exit, Reload;
    private JMenuItem importExcel, importTxt, outputExcel, outputTxt;
    private JMenuItem aboutSystem, help;
    private Login login_gui = null;
    private AddStudentInfo addStudent = null;
    private QueryStudentInfo queryStudent = null;
    private ModifyPasswordInfo modifyPasswordInfo = null;
    private ImportExcel importExcelInfo = null;
    private ImportTxt importTxtInfo = null;
    private OutputExcel outputExcelInfo = null;
    private OutputTxt outputTxtInfo = null;
    private AboutSystem aboutSystemInfo = null;
    private Help helpInfo = null;
    public MainMenu(Login m_login) {
        login_gui = m_login;
        mainmenu();
    }

    public static void main(String[] args) {
        new MainMenu(null);
    }

    public void setVisible(boolean b) {
        mainmenu.setVisible(b);
    }

    private void initImgs() {
        if (imgs == null) {
            File file = new File(IMG_PATH);
            File[] files = file.listFiles();    //TODO �г�����ͼƬ
            imgs = new ImageIcon[files.length];
            for (int i = 0; i < files.length; i++) {
                imgs[i] = new ImageIcon(files[i].getPath());
            }
        }
    }

    public void mainmenu() {
        initImgs();
        Thread sub_task = new Thread(() -> {
            try (StudentDAO studentDAO = StudentDAO.getInstance(true)) {
                Student.students = studentDAO.getAllStudent();
                if (Student.students == null) {
                    ShowMessageUtil.winMessage("���ݿ���ʱΪ��");
                    Student.students = new ArrayList<>();
                } else {
                    ShowMessageUtil.winMessage("���ݿ����ݶ�ȡ��ɣ�");
                }
            }
        });
        sub_task.start(); //TODO �����̴߳�MySQL���ݿ��л�ȡ���ݲ�����Student.students

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        Color bgk_color = new Color(0xE6F7FA);
        mainmenu = new JFrame("��ӭʹ��ѧ����Ϣ����ϵͳ made by L_B__");
        mainmenu.setSize(1200, 785);
        mainmenu.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        mainmenu.getContentPane().setBackground(bgk_color);
        MyJPanel myJPanel = new MyJPanel();
        mainmenu.getContentPane().add(myJPanel);
        myJPanel.setBounds(0, 220, 1200, 500);
        mainmenu.setResizable(false);//���ڲ��ɱ�

        JLabel welcome_text = new JLabel("��ӭʹ��ѧ����Ϣ����ϵͳ");
        Font font_welcome = new Font("����", Font.BOLD, 40);
        welcome_text.setFont(font_welcome);
        welcome_text.setBounds(350, 50, 500, 50);
        welcome_text.setForeground(Color.DARK_GRAY);
        mainmenu.getContentPane().add(welcome_text);

        JLabel jLabel1 = new JLabel();
        Font font_author = new Font("����", Font.PLAIN, 14);
        jLabel1.setFont(font_author);
        jLabel1.setBounds(20, 20, 200, 20);
        jLabel1.setForeground(Color.gray);
        jLabel1.setText("���ߣ�");
        mainmenu.getContentPane().add(jLabel1);

        JPanel myWebsiteLink = LinkLabel.getInstance(" @ L_B__", "https://acking-you.gitee.io/");
        myWebsiteLink.setBackground(bgk_color);
        myWebsiteLink.setFont(new Font("Arial", Font.PLAIN, 14));
        myWebsiteLink.setBounds(40, 40, 50, 20);
        mainmenu.getContentPane().add(myWebsiteLink);

        LinkLabel myGithub = LinkLabel.getInstance(new ImageIcon("src/images/github.png"), "https://github.com/ACking-you");
        myGithub.setBounds(550, 17, 24, 24);
        mainmenu.getContentPane().add(myGithub);

        LinkLabel myBilibili = LinkLabel.getInstance(new ImageIcon("src/images/bilibili.png"), "https://space.bilibili.com/24264499");
        myBilibili.setBounds(590, 17, 24, 24);
        mainmenu.getContentPane().add(myBilibili);

        JLabel author_text = new JLabel();
        author_text.setText("��ϵͳ�� L_B__ �� 2022.02.28 �������");
        author_text.setFont(new Font("����", Font.PLAIN, 15));
        author_text.setForeground(Color.LIGHT_GRAY);
        author_text.setBounds(450, 100, 400, 20);
        mainmenu.getContentPane().add(author_text);

        JLabel webSite_description = new JLabel();
        webSite_description.setText("������Ϣ�ɽ����ҵĸ�����վ�鿴��");
        webSite_description.setFont(new Font("����", Font.PLAIN, 14));
        webSite_description.setForeground(Color.GRAY);
        webSite_description.setBounds(420, 130, 230, 20);
        mainmenu.getContentPane().add(webSite_description);

        JPanel webSiteLink = LinkLabel.getInstance("acking-you.gitee.io", "https://acking-you.gitee.io/");
        webSiteLink.setBounds(640, 128, 120, 20);
        webSiteLink.setBackground(bgk_color);
        webSiteLink.setFont(new Font("Arial", Font.PLAIN, 15));
        mainmenu.getContentPane().add(webSiteLink);

        JLabel sponsor_text = new JLabel();
        sponsor_text.setText("ϲ���Ļ�����ɨ���ұߵĶ�ά���������^_^");
        sponsor_text.setFont(new Font("����", Font.PLAIN, 16));
        sponsor_text.setForeground(Color.GRAY);
        sponsor_text.setBounds(420, 160, 380, 20);
        mainmenu.getContentPane().add(sponsor_text);


        //TODO wechat�տ���
        JLabel wechat = new JLabel();
        wechat.setIcon(new ImageIcon("src/images/wechat.png"));
        wechat.setBounds(980, 50, 119, 119);
        mainmenu.getContentPane().add(wechat);

        Font cao_font = new Font("�����Ʋ�_GBK", Font.BOLD, 30);
        JLabel first_poem = new JLabel();
        first_poem.setText("��ɽ��·��Ϊ��");
        first_poem.setFont(cao_font);
        first_poem.setForeground(new Color(0xEA5A91));
        first_poem.setBounds(20, 100, 300, 40);
        mainmenu.getContentPane().add(first_poem);

        JLabel second_poem = new JLabel();
        second_poem.setText("ѧ�����Ŀ�����");
        second_poem.setForeground(new Color(0xf19983));
        second_poem.setFont(cao_font);
        second_poem.setBounds(20, 150, 300, 40);
        mainmenu.getContentPane().add(second_poem);

        //====================>��������
        menuFile1 = new JMenu("��������(0)");
        Font font = new Font("����", Font.BOLD, 16);
        menuFile1.setFont(font);
        menuFile1.setIcon(new ImageIcon("src/images/icons/base1.png"));
        menuFile1.setMnemonic('O');

        mainmenu.add(menuFile1);

        menuBar1 = new JMenuBar();

        addStudentInfo = new JMenuItem("����", new ImageIcon("src/images/icons/add.png"));
        addStudentInfo.setMnemonic('A');
        addStudentInfo.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
        menuFile1.add(addStudentInfo);

        queryStudentInfo = new JMenuItem("��ѯ", new ImageIcon("src/images/icons/query.png"));
        queryStudentInfo.setMnemonic('Q');
        queryStudentInfo.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        menuFile1.add(queryStudentInfo);

        modifyPassword = new JMenuItem("�����޸�", new ImageIcon("src/images/icons/modifyPassword.png"));
        modifyPassword.setMnemonic('M');
        modifyPassword.setAccelerator(KeyStroke.getKeyStroke('M', InputEvent.CTRL_DOWN_MASK));
        menuFile1.add(modifyPassword);

        Exit = new JMenuItem("�˳�", new ImageIcon("src/images/icons/exit.png"));
        Exit.setMnemonic('E');
        Exit.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
        menuFile1.add(Exit);

        Reload = new JMenuItem("���µ�¼", new ImageIcon("src/images/icons/reload.png"));
        Reload.setMnemonic('R');
        Reload.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_DOWN_MASK));
        menuFile1.add(Reload);

        menuBar1.add(menuFile1);
        //====================>���뵼��
        menuFile2 = new JMenu("���뵼��(1)");
        menuFile2.setFont(font);
        menuFile2.setIcon(new ImageIcon("src/images/icons/base2.png"));
        importExcel = new JMenuItem("��excel����", new ImageIcon("src/images/icons/import.png"));
        importExcel.setMnemonic('C');
        importExcel.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
        menuFile2.add(importExcel);

        importTxt = new JMenuItem("��txt����", new ImageIcon("src/images/icons/output.png"));
        importTxt.setMnemonic('T');
        importTxt.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_DOWN_MASK));
        menuFile2.add(importTxt);

        outputExcel = new JMenuItem("����excle", new ImageIcon("src/images/icons/import1.png"));
        outputExcel.setMnemonic('L');
        outputExcel.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_DOWN_MASK));
        menuFile2.add(outputExcel);

        outputTxt = new JMenuItem("����txt", new ImageIcon("src/images/icons/output1.png"));
        outputTxt.setMnemonic('X');
        outputTxt.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK));
        menuFile2.add(outputTxt);


        menuBar1.add(menuFile2);


        menuFile3 = new JMenu("����(2)");
        menuFile3.setFont(font);
        menuFile3.setIcon(new ImageIcon("src/images/icons/base3.png"));
        aboutSystem = new JMenuItem("���ڱ�ϵͳ", new ImageIcon("src/images/icons/about.png"));
        aboutSystem.setMnemonic('B');
        aboutSystem.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_DOWN_MASK));
        menuFile3.add(aboutSystem);

        help = new JMenuItem("ϵͳ����", new ImageIcon("src/images/icons/help.png"));
        help.setMnemonic('H');
        help.setAccelerator(KeyStroke.getKeyStroke('H', InputEvent.CTRL_DOWN_MASK));
        menuFile3.add(help);

        menuBar1.add(menuFile3);


        addStudentInfo.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ����ѧ����Ϣ���˵���");
            if (addStudent == null)
                addStudent = new AddStudentInfo();
            else {
                addStudent.setVisible(true);
            }
        });


        queryStudentInfo.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ���ѯѧ����Ϣ���˵���");
            if (queryStudent == null)
                queryStudent = new QueryStudentInfo();
            else {
                queryStudent.setVisible(true);
            }
        });

        modifyPassword.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ��޸����롯�˵���");
            if (modifyPasswordInfo == null)
                modifyPasswordInfo = new ModifyPasswordInfo();
            else {
                modifyPasswordInfo.setVisible(true);
            }
        });


        Exit.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ��˳����˵���");
            System.exit(1);
        });

        Reload.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ����µ�¼'�˵���");
            mainmenu.setVisible(false);
            if (login_gui != null) {
                login_gui.setVisible(true);
                if (aboutSystem != null) aboutSystem.setVisible(false);
                if (addStudentInfo != null) addStudentInfo.setVisible(false);
                if (help != null) help.setVisible(false);
                if (importTxt != null) importTxt.setVisible(false);
                if (importExcel != null) importExcel.setVisible(false);
                if (outputTxtInfo != null) outputTxtInfo.setVisible(false);
                if (outputExcelInfo != null) outputExcelInfo.setVisible(false);
            }
        });

        importExcel.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ����뵽excel���˵���");
            if (importExcelInfo == null)
                importExcelInfo = new ImportExcel();
            else {
                importExcelInfo.setVisible(true);
            }
        });
        importTxt.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ����뵽txt���˵���");
            if (importTxtInfo == null)
                importTxtInfo = new ImportTxt();
            else {
                importTxtInfo.setVisible(true);
            }
        });
        outputExcel.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ�������excel���˵���");
            if (outputExcelInfo == null)
                outputExcelInfo = new OutputExcel();
            else {
                outputExcelInfo.setVisible(true);
            }
        });
        outputTxt.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ�������txt���˵���");
            if (outputTxtInfo == null)
                outputTxtInfo = new OutputTxt();
            else {
                outputTxtInfo.setVisible(true);
            }
        });
        aboutSystem.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ�����ϵͳ���˵���");
            if (aboutSystemInfo == null)
                aboutSystemInfo = new AboutSystem();
            else {
                aboutSystemInfo.setVisible(true);
            }
        });
        help.addActionListener(e -> {
            System.out.println("=======>�û�ѡ���ˡ��������˵���");
            if (helpInfo == null)
                helpInfo = new Help();
            else {
                helpInfo.setVisible(true);
            }
        });


        mainmenu.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                System.exit(1);
            }
        });

        mainmenu.setJMenuBar(menuBar1);
        mainmenu.setVisible(true);
        mainmenu.setLocation(40, 0);
        addStudentInfo.addActionListener(this);
        queryStudentInfo.addActionListener(this);
        modifyPassword.addActionListener(this);
        Exit.addActionListener(this);
        importExcel.addActionListener(this);
        importTxt.addActionListener(this);
        outputExcel.addActionListener(this);
        outputTxt.addActionListener(this);
        aboutSystem.addActionListener(this);
        help.addActionListener(this);

        final Timer timer = new Timer(8000, (e) -> {
            myJPanel.repaint();
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {


    }

    class MyJPanel extends JPanel {
        int index = 0;

        @Override
        public void paint(Graphics g) {
            if (index > 100000)
                index = 0;
            super.paint(g);
            g.drawImage(imgs[index % imgs.length].getImage(), 0, 0, this);
            index++;
        }
    }

}
