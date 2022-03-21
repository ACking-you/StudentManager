package view;

import model.dao.StudentDAO;
import model.entity.Student;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.ShowMessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditStudentInfo extends JFrame implements ActionListener, ItemListener {

    private static JTextField SidText, SnameText, SbirthdayText,
            SphoneText;//1.ѧ��id�����,
    //2.ѧ�����������,3.ѧ���������������,4.ѧ����ϵ�绰�����
    private static ButtonGroup SexButton;//������ť��
    private static JComboBox SprovinceComboBox;//ʡ�������б��
    //2.��ʾ������������,3.��ʾ���Ա�����,4.��ʾ���������ڡ�����,5.��ʾ����ַ������,6.��ʾ����Ȥ���á�����
    //7.��ʾ����ϵ�绰������
    private static JCheckBox hobby1, hobby2, hobby3, hobby4, hobby5, hobby6,
            hobby7, hobby8, hobby9, hobby10, hobby11, hobby12;//��Ȥ��ѡ��
    private final String[] province = {"����ʡ", "����ʡ", "����ʡ", "�ӱ�ʡ", "����ʡ", "����ʡ", "������ʡ",
            "ɽ��ʡ", "ɽ��ʡ", "����ʡ", "����ʡ", "�㽭ʡ", "����ʡ", "����ʡ", "�㶫ʡ", "����ʡ",
            "�Ĵ�ʡ", "����ʡ", "����ʡ", "�ຣʡ", "����ʡ", "����ʡ", "̨��ʡ"};
    String preSid = null;
    private JButton cancel;
    private JButton save;//1.���水ť,2.ȡ����ť,3.���ذ�ť
    private JLabel Sid, Sname, Ssex, Sbirthday, Sprovince, Shobby, Sphone;//1.��ʾ����š�����,
    private JRadioButton SexButton1, SexButton2;//�Ա�ѡ��ť
    private JPanel checkbox;//������Ȥ��ѡ�����
    private Font font1, font2;//��������
    private QueryStudentInfo parent = null;


    public EditStudentInfo(QueryStudentInfo info, String sid, String sname, String ssex, String sbirthday, String sprovince, String hobby, String sphone) {
        if (Student.students == null) {
            ShowMessageUtil.winMessage("���ݿ��ʼ��δ��ɣ�");
            System.exit(0);
        }
        preSid = sid;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// ʹ��windows���
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent = info;
        setTitle("�޸�ѧ����Ϣ");//������������
        setSize(850, 650);//����������С
        setLocationRelativeTo(null);//��������ʾ����Ļ����
        EditStudentInfoInterface(sid, sname, ssex, sbirthday, sprovince, hobby, sphone);//���ֽ��溯��
        setVisible(true);//���ô��ڿɼ�
        setResizable(true);//���ô��ڴ�С���Ըı�
    }

    public static void clearText() {
        // TODO Auto-generated method stub
        SidText.setText("");//��������
        SnameText.setText("");//��������
        SexButton.clearSelection();//��������
        SbirthdayText.setText("");//��������
        SprovinceComboBox.setSelectedIndex(0);//ȡ��ѡ��
        hobby1.setSelected(false);//ȡ��ѡ��
        hobby2.setSelected(false);//ȡ��ѡ��
        hobby3.setSelected(false);//ȡ��ѡ��
        hobby4.setSelected(false);//ȡ��ѡ��
        hobby5.setSelected(false);//ȡ��ѡ��
        hobby6.setSelected(false);//ȡ��ѡ��
        hobby7.setSelected(false);//ȡ��ѡ��
        hobby8.setSelected(false);//ȡ��ѡ��
        hobby9.setSelected(false);//ȡ��ѡ��
        hobby10.setSelected(false);//ȡ��ѡ��
        hobby11.setSelected(false);//ȡ��ѡ��
        hobby12.setSelected(false);//ȡ��ѡ��
        SphoneText.setText("");//��������
    }

    //�ж�Sid�Ƿ�Ϸ�
    public static boolean isNumeric(String str) {
        String pattern = "^B\\d{11}";
        return Pattern.matches(pattern, str);
    }

    public static boolean isMobile(final String sphone) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][0-9]{10}$"); // ��֤�ֻ��Űѹ�������ģʽ����
        m = p.matcher(sphone);//ͨ��ģʽ����õ�ƥ��������
        b = m.matches();//��ƥ��Ŀ����м��
        return b;
    }

    public static void main(String[] args) {
        EditStudentInfo editStudentInfo = new EditStudentInfo(null, "3233", null, "��", "2023-03-23", "ɽ��", "coding", "323333333");
    }

    private void EditStudentInfoInterface(String sid, String sname, String ssex, String sbirthday, String sprovince, String hobby, String sphone) {

        setLayout(null);//�Ծ��Բ��ֵķ�ʽ����

        font1 = new Font("����", Font.PLAIN, 20);
        Font font3 = new Font("Consolas", Font.PLAIN, 18);
        //���ø��������λ��,����
        Sid = new JLabel("ѧ��:");//����Sid������
        Sid.setBounds(250, 30, 150, 50);//�����������ڵ�λ��
        Sid.setFont(font1);//��������
        SidText = new JTextField();//���������
        SidText.setBounds(new Rectangle(350, 40, 150, 30));//����λ��
        SidText.setFont(font1);//��������
        SidText.setText(sid);
        SidText.setEditable(true);

        Sname = new JLabel("����:");
        Sname.setBounds(250, 90, 150, 50);
        Sname.setFont(font1);
        SnameText = new JTextField();
        SnameText.setBounds(new Rectangle(350, 100, 150, 30));
        SnameText.setFont(font1);
        SnameText.setText(sname);

        Ssex = new JLabel("�Ա�:");
        Ssex.setBounds(250, 150, 150, 50);
        Ssex.setFont(font1);
        SexButton = new ButtonGroup();
        SexButton1 = new JRadioButton("��");
        SexButton1.setBounds(350, 160, 50, 30);
        SexButton1.setFont(font1);
        SexButton2 = new JRadioButton("Ů");
        SexButton2.setBounds(450, 160, 50, 30);
        SexButton2.setFont(font1);
        if (ssex.equals("m") || ssex.equals("��")) {
            SexButton1.setSelected(true);
        }


        Sbirthday = new JLabel("��������:");
        Sbirthday.setBounds(250, 210, 150, 50);
        Sbirthday.setFont(font1);
        SbirthdayText = new JTextField();
        SbirthdayText.setBounds(350, 220, 150, 30);
        SbirthdayText.setFont(font1);
        SbirthdayText.setText(sbirthday);

        Sprovince = new JLabel("����ʡ��:");
        Sprovince.setBounds(250, 270, 150, 50);
        Sprovince.setFont(font1);
        SprovinceComboBox = new JComboBox(province);
        SprovinceComboBox.setBounds(350, 280, 150, 30);
        SprovinceComboBox.setFont(font1);

        int flag = 0;
        for (int i = 0; i < province.length; i++) {
            if (province[i].equals(sprovince)) {
                flag = i;
            }
        }
        SprovinceComboBox.addItem(province[flag]);


        Shobby = new JLabel("�Ȱ��ļ��������:");
        Shobby.setBounds(170, 330, 200, 50);
        Shobby.setFont(font1);


        hobby1 = new JCheckBox("C++");
        hobby1.setFont(font3);
        hobby2 = new JCheckBox("C");

        hobby2.setFont(font3);
        hobby3 = new JCheckBox("Java");

        hobby3.setFont(font3);
        hobby4 = new JCheckBox("Python");

        hobby4.setFont(font3);
        hobby5 = new JCheckBox("Go");

        hobby5.setFont(font3);
        hobby6 = new JCheckBox("JavaScript");

        hobby6.setFont(font3);
        hobby7 = new JCheckBox("Rust");

        hobby7.setFont(font3);
        hobby8 = new JCheckBox("C#");

        hobby8.setFont(font3);
        hobby9 = new JCheckBox("PHP");

        hobby9.setFont(font3);
        hobby10 = new JCheckBox("Swift");

        hobby10.setFont(font3);
        hobby11 = new JCheckBox("Ruby");

        hobby11.setFont(font3);
        hobby12 = new JCheckBox("MATLAB");

        hobby12.setFont(font3);
        checkbox = new JPanel();
        checkbox.setLayout(new GridLayout(3, 4));
        checkbox.add(hobby1);
        checkbox.add(hobby2);
        checkbox.add(hobby3);
        checkbox.add(hobby4);
        checkbox.add(hobby5);
        checkbox.add(hobby6);
        checkbox.add(hobby7);
        checkbox.add(hobby8);
        checkbox.add(hobby9);
        checkbox.add(hobby10);
        checkbox.add(hobby11);
        checkbox.add(hobby12);
        checkbox.setBounds(350, 335, 500, 130);

        Sphone = new JLabel("��ϵ�绰:");
        Sphone.setBounds(250, 460, 150, 50);
        Sphone.setFont(font1);
        SphoneText = new JTextField();
        SphoneText.setBounds(350, 470, 150, 30);
        SphoneText.setFont(font1);
        SphoneText.setText(sphone);

        font2 = new Font("�꿬��", Font.PLAIN, 15);
        save = new JButton("����");
        save.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.blue));//���ð�ť�ı�����ɫ
        save.setBounds(new Rectangle(300, 520, 70, 30));
        save.setFont(font2);
        cancel = new JButton("ȡ��");
        cancel.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.red));
        cancel.setBounds(new Rectangle(400, 520, 70, 30));
        cancel.setFont(font2);


        //�������齫��ӵ���������
        add(Sid);
        add(SidText);

        add(Sname);
        add(SnameText);


        SexButton.add(SexButton1);
        SexButton.add(SexButton2);
        add(Ssex);
        add(SexButton1);
        add(SexButton2);


        add(Sbirthday);
        add(SbirthdayText);

        add(Sprovince);
        add(SprovinceComboBox);

        add(Shobby);
        add(checkbox);

        add(Sphone);
        add(SphoneText);

        add(save);
        add(cancel);


        //Ϊ�����������¼�������
        SidText.addActionListener(this);
        SnameText.addActionListener(this);
        SbirthdayText.addActionListener(this);
        SprovinceComboBox.addItemListener(this);
        hobby1.addItemListener(this);
        hobby2.addItemListener(this);
        hobby3.addItemListener(this);
        hobby4.addItemListener(this);
        hobby5.addItemListener(this);
        hobby6.addItemListener(this);
        hobby7.addItemListener(this);
        hobby8.addItemListener(this);
        hobby9.addItemListener(this);
        hobby10.addItemListener(this);
        hobby11.addItemListener(this);
        hobby12.addItemListener(this);
        SphoneText.addActionListener(this);
        save.addActionListener(this);
        cancel.addActionListener(this);

        save.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                String sid, sname, sbirthday, sphone;
                sid = SidText.getText();//�õ�������е����ݱ�������
                sname = SnameText.getText();//�õ�������е����ݱ�������
                sbirthday = SbirthdayText.getText();//�õ�������е����ݱ�������
                sphone = SphoneText.getText();//�õ�������е����ݱ�������

                if (sid.equals("")) {
                    JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);//������ʾ��
                    return;
                }
                if (!isNumeric(sid)) {
                    JOptionPane.showMessageDialog(null, "ѧ�Ÿ�ʽ����ȷ��", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (sname.equals("")) {
                    JOptionPane.showMessageDialog(null, "��������Ϊ�գ�", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!SexButton1.isSelected() && !SexButton2.isSelected()) {//isSelected(),�����ť��ѡ�з���true
                    JOptionPane.showMessageDialog(null, "��ѡ���Ա�", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (sbirthday.equals("")) {
                    JOptionPane.showMessageDialog(null, "�������ڲ���Ϊ�գ�", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (isDate(sbirthday) == false) {
                    JOptionPane.showMessageDialog(null, "�������ڸ�ʽ���Ϸ�,���������룡", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!hobby1.isSelected() && !hobby2.isSelected() &&
                        !hobby3.isSelected() && !hobby4.isSelected() &&
                        !hobby5.isSelected() && !hobby6.isSelected() &&
                        !hobby7.isSelected() && !hobby8.isSelected() &&
                        !hobby9.isSelected() && !hobby10.isSelected() &&
                        !hobby11.isSelected() && !hobby12.isSelected()) {
                    JOptionPane.showMessageDialog(null, "��Ȥ���ò���Ϊ�գ�", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (sphone.equals("")) {
                    JOptionPane.showMessageDialog(null, "�ֻ����벻��Ϊ�գ�", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (isMobile(sphone) == false || sphone.length() != 11) {
                    JOptionPane.showMessageDialog(null, "�ֻ������ʽ����ȷ,�����������룡", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                Student student = new Student();
                student.setSid(sid);
                student.setName(sname);

                if (SexButton1.isSelected()) {//��ȡ��ѡ��ťѡ�е����,���浽student��
                    student.setSex(SexButton1.getText());
                } else {
                    student.setSex(SexButton2.getText());
                }
                student.setProvince((String) SprovinceComboBox.getSelectedItem());//�õ������б��е����ݱ��浽student��


                student.setBirthday(sbirthday);
                student.setPhone(sphone);
                StringBuffer hobby = new StringBuffer();


                if (hobby1.isSelected()) {
                    hobby.append(hobby1.getText() + ",");
                }
                if (hobby2.isSelected()) {
                    hobby.append(hobby2.getText() + ",");
                }
                if (hobby3.isSelected()) {
                    hobby.append(hobby3.getText() + ",");
                }
                if (hobby4.isSelected()) {
                    hobby.append(hobby4.getText() + ",");
                }
                if (hobby5.isSelected()) {
                    hobby.append(hobby5.getText() + ",");
                }
                if (hobby6.isSelected()) {
                    hobby.append(hobby6.getText() + ",");
                }
                if (hobby7.isSelected()) {
                    hobby.append(hobby7.getText() + ",");
                }
                if (hobby8.isSelected()) {
                    hobby.append(hobby8.getText() + ",");
                }
                if (hobby9.isSelected()) {
                    hobby.append(hobby9.getText() + ",");
                }
                if (hobby10.isSelected()) {
                    hobby.append(hobby10.getText() + ",");
                }
                if (hobby11.isSelected()) {
                    hobby.append(hobby11.getText() + ",");
                }
                if (hobby12.isSelected()) {
                    hobby.append(hobby12.getText());
                }

                if (hobby.charAt(hobby.length() - 1) == ',') {//�жϸ�ѡ�����һ��ѡ����ʲô,Ȼ��ȥ����,��
                    hobby.deleteCharAt(hobby.length() - 1);
                }
                student.setHobby(hobby.toString());

                StudentDAO addstudentdao = StudentDAO.getInstance(true);
                int cnt = addstudentdao.updateStudentInfo(student, preSid);//�����ݲ������ݿ�
                if (cnt == 1) {
                    JOptionPane.showMessageDialog(null, "����ѧ����Ϣ�ɹ�", "��Ϣ��ʾ", JOptionPane.QUESTION_MESSAGE);
                    if (parent != null)
                        parent.showAllStudent();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "����ѧ����Ϣʧ��", "��Ϣ��ʾ", JOptionPane.WARNING_MESSAGE);
                    if (parent != null)
                        parent.showAllStudent();
                    dispose();
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

        });
    }

    //�жϳ������ڵĺϷ���
    public boolean isDate(String date) {
        /**
         * �ж����ڸ�ʽ�ͷ�Χ
         */
        String rexp = "^((\\d{2}(([02468][048])"
                + "|([13579][26]))[\\-\\/\\s]?"
                + "((((0?[13578])|(1[02]))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(3[01])))"
                + "|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))"
                + "|(0?2[\\-\\/\\s]?((0?[1-9])"
                + "|([1-2][0-9])))))|(\\d{2}(([02468][1235679])"
                + "|([13579][01345789]))[\\-\\/\\s]?"
                + "((((0?[13578])|(1[02]))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(3[01])))"
                + "|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))"
                + "|(0?2[\\-\\/\\s]?((0?[1-9])"
                + "|(1[0-9])|(2[0-8]))))))";

        Pattern pat = Pattern.compile(rexp);   //�ѹ�������ģʽ����

        Matcher mat = pat.matcher(date);    //ͨ��ģʽ����õ�ƥ��������

        boolean dateType = mat.matches();  //��ƥ��Ŀ����м��

        return dateType;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == cancel) {
            clearText();
            setVisible(false);
        }
    }

}
