package view;

import model.dao.StudentDAO;
import model.entity.Student;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.ShowMessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


public class QueryStudentInfo extends JFrame implements ActionListener {

    private final JScrollPane panel;
    private final JButton next;
    private final JButton previous;
    private final JButton first;
    private final JButton last;
    private final JButton query;
    private final JButton queryAll;
    private final JButton modifyBtn;
    private final JButton deleteBtn;
    private final JButton exportExcelBtn;
    private final JButton backBtn;
    private final JLabel studentId;
    private final JLabel studentName;
    private final JLabel studentSex;
    private final JLabel studentProvince;
    private final JTextField studentIdContent;
    private final JTextField studentNameContent;
    private final JTextField studentSexContent;
    private final JTextField studentProvinceContent;
    private final JLabel label1;
    private final JLabel label2;    //	1.��ʾ��ҳ���͵�ǰҳ�� 2.ÿҳ��ʾ��
    private final JTable table;
    public int currentPage = 1;        // ��ǰҳ
    public int totalPage = 0;            // ��ҳ��
    public int totalRowCount = 0;        // ������
    public int pageCount;            // ÿҳ��ʾ��Ŀ
    public int column = 0;
    public int restCount;            // ���һҳ��Ŀ
    public Object[][] resultData;    // �������ά����
    /*JTable����Ϣ��ر���*/
    public List<Student> students = null;
    public String[] columnNames = {"ѧ��", "����", "�Ա�", "����", "ʡ��", "�Ȱ��ļ��������", "�绰"};
    public DefaultTableModel model = null;//Ĭ�ϵı�����ģ��
    /*���������˵�����*/
    String[] array = {"20", "30", "40", "50", "60"};
    JComboBox box = new JComboBox(array);//������array�ŵ������˵���

    /*
     * ���弰��Ľ���
     */
    public QueryStudentInfo() {
        super("ѧ����Ϣ��ѯͳ��");
//        if(Student.students==null){
//            ShowMessageUtil.winMessage("���ݿ��ʼ��δ��ɣ�");
//            System.exit(0);
//        }
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        this.setSize(1040, 680);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setResizable(false);

        Font font = new Font("����", Font.CENTER_BASELINE, 12);

        studentId = new JLabel("ѧ��");
        studentId.setBounds(100, 30, 40, 30);
        studentId.setFont(font);

        studentIdContent = new JTextField();
        studentIdContent.setBounds(145, 30, 100, 30);


        studentName = new JLabel("ѧ������");
        studentName.setBounds(270, 30, 70, 30);
        studentName.setFont(font);

        studentNameContent = new JTextField();
        studentNameContent.setBounds(341, 30, 100, 30);


        //�������������Ա𡱣���ʡ�ݡ�
        studentProvince = new JLabel("ʡ��");
        studentProvince.setBounds(100, 65, 40, 30);
        studentProvince.setFont(font);

        studentProvinceContent = new JTextField();
        studentProvinceContent.setBounds(145, 65, 100, 30);

        studentSex = new JLabel("ѧ���Ա�");
        studentSex.setBounds(270, 65, 70, 30);
        studentSex.setFont(font);

        studentSexContent = new JTextField();
        studentSexContent.setBounds(341, 65, 100, 30);

        query = new JButton("��ѯ");
        query.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        query.setBounds(500, 30, 95, 30);

        query.setForeground(Color.blue);
        ImageIcon icon1 = new ImageIcon("src/images/query2.png");
        query.setIcon(icon1);

        queryAll = new JButton("��ѯ����ѧ��");
        queryAll.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        queryAll.setBounds(700, 30, 150, 30);
        queryAll.setForeground(Color.blue);

        table = new JTable();
        box.setBounds(890, 105, 100, 20);
        label2 = new JLabel("ÿҳ��ʾ����:");
        label2.setBounds(800, 93, 120, 50);
        panel = new JScrollPane();//���ù�����
        panel.setViewportView(table);
        panel.setBounds(42, 136, 950, 420);


        first = new JButton("��һҳ");
        first.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        first.setBounds(44, 570, 90, 30);
        previous = new JButton("��һҳ");
        previous.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        previous.setBounds(164, 570, 90, 30);
        next = new JButton("��һҳ");
        next.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        next.setBounds(284, 570, 90, 30);
        last = new JButton("���һҳ");
        last.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        last.setBounds(404, 570, 90, 30);


        modifyBtn = new JButton("�޸�");
        modifyBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        modifyBtn.setBounds(524, 570, 90, 30);
        deleteBtn = new JButton("ɾ��");
        deleteBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        deleteBtn.setBounds(644, 570, 90, 30);
        exportExcelBtn = new JButton("������Excel");
        exportExcelBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        exportExcelBtn.setBounds(764, 570, 120, 30);
        backBtn = new JButton("����");
        backBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        backBtn.setBounds(908, 570, 90, 30);



        /*��Ӽ���*/
        previous.addActionListener(this);
        next.addActionListener(this);


        first.addActionListener(this);
        last.addActionListener(this);
        query.addActionListener(this);
        queryAll.addActionListener(this);
        backBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });


        exportExcelBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                OutputExcel excel = new OutputExcel();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
        QueryStudentInfo info = this;
        modifyBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {


                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "���ڱ����ѡ��һ������", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sid = table.getValueAt(selectedRowIndex, 0).toString();
                    String name = table.getValueAt(selectedRowIndex, 1).toString();
                    String sex = table.getValueAt(selectedRowIndex, 2).toString();
                    String birthday = table.getValueAt(selectedRowIndex, 3).toString();
                    String province = table.getValueAt(selectedRowIndex, 4).toString();
                    String hobby = table.getValueAt(selectedRowIndex, 5).toString();
                    String phone = table.getValueAt(selectedRowIndex, 6).toString();

                    EditStudentInfo editStudentInfo = new EditStudentInfo(info, sid, name, sex, birthday, province, hobby, phone);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });


        deleteBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("===========================================>�û�����˲�ѯ�˵��еġ�ɾ������ť");

                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "���ڱ����ѡ��һ������", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sid = table.getValueAt(selectedRowIndex, 0).toString();

                    StudentDAO studentDAO = StudentDAO.getInstance(true);
                    int cnt = studentDAO.deleteStudentBySid(sid);
                    if (cnt == 1) {
                        JOptionPane.showMessageDialog(null, "ɾ��ѧ����Ϣ�ɹ�", "��Ϣ��ʾ", JOptionPane.QUESTION_MESSAGE);
                        showAllStudent();
                    } else {
                        JOptionPane.showMessageDialog(null, "ɾ��ѧ����Ϣʧ��", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    }
                    studentDAO.close();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        label1 = new JLabel();
        label1.setBounds(420, 400, 180, 60);

        this.getContentPane().setLayout(null);

        this.getContentPane().add(box);//��ȡ������壬�ٶ���������
        this.getContentPane().add(label2);
        this.getContentPane().add(panel);
        this.getContentPane().add(previous);
        this.getContentPane().add(next);
        this.getContentPane().add(first);
        this.getContentPane().add(last);
        this.getContentPane().add(label1);


        this.getContentPane().add(studentId);
        this.getContentPane().add(studentIdContent);
        this.getContentPane().add(studentName);
        this.getContentPane().add(studentNameContent);
        this.getContentPane().add(studentSex);
        this.getContentPane().add(studentSexContent);
        this.getContentPane().add(studentProvince);
        this.getContentPane().add(studentProvinceContent);

        this.getContentPane().add(modifyBtn);
        this.getContentPane().add(deleteBtn);
        this.getContentPane().add(exportExcelBtn);
        this.getContentPane().add(backBtn);

        this.getContentPane().add(query);
        this.getContentPane().add(queryAll);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        students = new ArrayList<>();       //TODO �ֶ�ʵ�����
        for (Student student : Student.students) {
            students.add(student);
        }

        /**
         * �¼�����
         */
        /*�����˵��¼�����*/
        box.addActionListener(e -> {
            String Str = (String) box.getSelectedItem();
            pageCount = Integer.parseInt(Str);
            initTable();
            System.out.println(pageCount);

        });

        initTable();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        QueryStudentInfo q = new QueryStudentInfo();
    }

    /**
     * ��ȡ��һҳ
     */
    public int getNextPage() {
        if (this.currentPage != this.totalPage) {
            return ++currentPage;
        }
        return -1;
    }

    /**
     * ��ȡ��һҳ
     */
    public int getPreviousPage() {
        if (this.currentPage != 1) {
            return --currentPage;
        }
        return -1;
    }

    /**
     * ��ȡ���һҳ
     */
    public int getLastPage() {
        currentPage = totalPage;
        return currentPage;
    }

    /**
     * ��ȡ��һҳ
     */
    public int getFirstPage() {
        currentPage = 1;
        return currentPage;
    }

    /**
     * ��ȡ��ҳ��
     */
    public int getTotolPage() {
        return this.totalPage;
    }

    /**
     * ��ȡ��ǰҳ
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * ���ԭʼ���ݼ�
     *
     * @param students
     * @return String sId, String sName, String sSex, String sBirthday,
     * String sProvince, String sHobby, String sPhone
     */
    public Object[][] getData(List<Student> students) {
        if (students.size() > 0) {
            Object[][] data = new Object[students.size()][4];
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                Object[] a = {s.getSid(), s.getName(), s.getSex(), s.getBirthday(), s.getProvince(), s.getHobby(), s.getPhone()};//��List**�����ݸ���Object����
                data[i] = a;//�������ֵ������ά�����һ��
            }
            return data;
        }
        return null;
    }

    /**
     * ��ʼ�������
     *
     * @param data
     */
    public void initResultData(Object[][] data) {
        if (data != null) {
            String Str = (String) box.getSelectedItem();
            pageCount = Integer.parseInt(Str);
            resultData = data;//�ܵĽ����
            column = data[0].length;//�������
            totalRowCount = data.length;//��ĳ���
            totalPage = totalRowCount % pageCount == 0 ? totalRowCount / pageCount : totalRowCount / pageCount + 1;//���������ҳ��
            restCount = totalRowCount % pageCount == 0 ? pageCount : totalRowCount % pageCount;//���һҳ��������
            label1.setText("�ܹ�" + totalRowCount + "��¼|��ǰ��" + currentPage + "ҳ");
        } else {
            restCount = 0;
        }
    }

    /**
     * ��ȡ��ҳ����
     *
     * @return
     */
    public Object[][] getPageData() {
        Object[][] currentPageData = new Object[pageCount][column];//����ÿҳ���ݼ�
        if (this.getCurrentPage() < this.totalPage) {//�����ǰҳ��С����ҳ������ôÿҳ��ĿӦ���ǹ涨����pageCount
            for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage()); i++) {
                for (int j = 0; j < column; j++) {
                    //TODO �ѽ�����ж�Ӧÿҳ��ÿһ������ȫ����ֵ����ǰҳ��ÿһ�е�ÿһ��
                    currentPageData[i % pageCount][j] = resultData[i][j];
                }
            }
        } else {
            //TODO �ڶ�̬�ı����ݽ������ʱ�������ǰҳû�������ˣ���ص�ǰһҳ��һ��������һҳ���ԣ�
            if (pageCount * (this.getCurrentPage() - 1) >= totalRowCount) this.currentPage--;
            for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + restCount; i++) {
                for (int j = 0; j < column; j++) {
                    currentPageData[i % pageCount][j] = resultData[i][j];
                }
            }
        }
        return currentPageData;
    }

    public void showAllStudent() {
        students.clear();
        for (Student student : Student.students) {
            students.add(student);
        }
        initTable();
    }

    /**
     * ��ʼ���������
     */
    public void initTable() {
        Object[][] data = getData(students);
        if (data != null) {
            initResultData(data);
            model = new DefaultTableModel(getPageData(), columnNames);
        } else {
            //����������û�����ݣ���ô���ÿ����������ݼ��е�ÿһ��
            initResultData(data);
            Object[][] nothing = {{}, {}, {}, {}, {}};
            model = new DefaultTableModel(nothing, columnNames);
            totalRowCount = 0;
        }
        table.setModel(model);
        table.setRowHeight(20);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
    }

    /**
     * ��ť�¼�
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] data = new String[4];
        JButton button = (JButton) e.getSource();
        if (button.equals(first)) {
            int i = getFirstPage();
            if (i == -1) return;
        }
        if (button.equals(previous)) {
            int i = getPreviousPage();
            if (i == -1) return;
        }
        if (button.equals(next)) {
            int i = getNextPage();
            if (i == -1) return;
        }
        if (button.equals(last)) {
            int i = getLastPage();
            if (i == -1) return;
        }

        if (button.equals(modifyBtn)) {

        }
        if (button.equals(deleteBtn)) {

        }
        if (button.equals(exportExcelBtn)) {

        }
        if (button.equals(backBtn)) {
            setVisible(false);
        }

        if (button.equals(query)) {
            data[0] = studentIdContent.getText();
            data[1] = studentNameContent.getText();
            data[2] = studentProvinceContent.getText();
            data[3] = studentSexContent.getText();

            if (data[0].isEmpty() && data[1].isEmpty() && data[2].isEmpty() && data[3].isEmpty()) {
                ShowMessageUtil.winMessage("ɸѡ��������Ϊ�գ�");
                return;
            }
            students.clear();
            for (Student student : Student.students) {  //TODO ɸѡ����������Student����students
                if (!data[0].isEmpty() && !student.getSid().equals(data[0])) {
                    continue;
                }
                if (!data[1].isEmpty() && !student.getName().equals(data[1])) {
                    continue;
                }
                if (!data[2].isEmpty() && !student.getProvince().equals(data[2])) {
                    continue;
                }
                if (!data[3].isEmpty() && !student.getSex().equals(data[3])) {
                    continue;
                }
                students.add(student);
            }
            initTable();
        }
        if (button.equals(queryAll)) {
            showAllStudent();
        }
        Object[][] currentPageData = new Object[pageCount][column];//����ÿҳ���ݼ�

        if (this.getCurrentPage() == 1) {
            for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + restCount; i++) {

                for (int j = 0; j < column; j++) {
                    currentPageData[i % pageCount][j] = resultData[i][j];
                }
            }
        } else {
            if (this.getCurrentPage() < this.totalPage) {//�����ǰҳ��С����ҳ������ôÿҳ��ĿӦ���ǹ涨����pageCount
                for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + pageCount; i++) {
                    for (int j = 0; j < column; j++) {
                        //�ѽ�����ж�Ӧÿҳ��ÿһ������ȫ����ֵ����ǰҳ��ÿһ�е�ÿһ��
                        currentPageData[i % pageCount][j] = resultData[i][j];
                    }
                }
            } else {
                //�ڶ�̬�ı����ݽ������ʱ�������ǰҳû�������ˣ���ص�ǰһҳ��һ��������һҳ���ԣ�
                System.out.println(this.getCurrentPage());
                if (pageCount * (this.getCurrentPage() - 1) >= totalRowCount) this.currentPage--;
                for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + restCount; i++) {
					/*if(i==-20){
						System.out.println("i=-20 ");
						i=0;

					}*/
                    for (int j = 0; j < column; j++) {
                        currentPageData[i % pageCount][j] = resultData[i][j];
                    }
                }
            }
        }


        DefaultTableModel model = new DefaultTableModel(currentPageData, columnNames);
        table.setModel(model);
        label1.setText("�ܹ�" + totalRowCount + "��¼|��ǰ��" + currentPage + "ҳ");
    }
}  

