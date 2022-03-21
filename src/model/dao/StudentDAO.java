package model.dao;

import model.MybatisUtil;
import model.entity.Student;
import model.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import util.ShowMessageUtil;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentDAO implements Closeable {
    public static int progress;
    private final SqlSession sqlSession;
    private final StudentMapper studentMapper;

    /**
     * �����ڵ����ķ�װ
     *
     * @param autoCommit
     */
    private StudentDAO(boolean autoCommit) {
        sqlSession = MybatisUtil.getSession(autoCommit);
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    public static StudentDAO getInstance(boolean autoCommit) {
        return new StudentDAO(autoCommit);
    }

    /**
     * ����Excle���ٵõ�cell����
     *
     * @param cell
     * @return
     */
    public static String getStringVal(HSSFCell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }

    public static int getprogress() {
        return progress;
    }

    /**
     * �����ݿ��е�ѧ����Ϣд�뵽Excle����
     *
     * @param list
     * @param filepath
     * @throws IOException
     */
    static public int ExportExcle(List<Student> list, String filepath) throws IOException {
        if (list == null || filepath == null || filepath.isEmpty()) {
            ShowMessageUtil.winMessage("������list��pathΪ��");
            return 0;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("ѧ����Ϣ");
        HSSFRow row = sheet.createRow(0);
        String[] s = {"sid", "name", "sex", "birthday", "province", "hobby", "phone"};
        for (int i = 0; i < 7; i++) {
            row.createCell(i).setCellValue(s[i]);
        }
        for (int j = 0; j < list.size(); j++) {
            HSSFRow r = sheet.createRow(j + 1);
            r.createCell(0).setCellValue(list.get(j).getSid());
            r.createCell(1).setCellValue(list.get(j).getName());
            r.createCell(2).setCellValue(list.get(j).getSex());
            r.createCell(3).setCellValue(list.get(j).getBirthday());
            r.createCell(4).setCellValue(list.get(j).getProvince());
            r.createCell(5).setCellValue(list.get(j).getHobby());
            r.createCell(6).setCellValue(list.get(j).getPhone());
        }
        OutputStream out = new FileOutputStream(filepath);
        workbook.write(out);
        System.out.println("����ɹ���");
        out.close();
        workbook.close();
        return list.size();
    }

    /**
     * ���ڶ�ȡExcle�������
     *
     * @param fileName
     * @return List<Student>
     * @throws IOException
     */
    static public List<Student> ReadFromExcel(String fileName) throws IOException {

        InputStream inputStream = new FileInputStream(fileName);

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
        List<Student> list = new ArrayList<>();
        int size = hssfWorkbook.getNumberOfSheets();

        for (int numSheet = 0; numSheet < size; numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null)
                continue;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow row = hssfSheet.getRow(rowNum);
                int minColIx = row.getFirstCellNum();
                int maxColIx = row.getLastCellNum();
                Student student = new Student();

                int i = 1;
                for (int colNum = minColIx; colNum < maxColIx; colNum++) {

                    HSSFCell cell = row.getCell(colNum);
                    if (cell == null) {
                        continue;
                    }
                    if (i == 1) {
                        student.setSid(getStringVal(cell));
                    } else if (i == 2) {
                        student.setName(getStringVal(cell));
                    } else if (i == 3) {
                        student.setSex(getStringVal(cell));
                    } else if (i == 4) {
                        student.setBirthday(getStringVal(cell));
                    } else if (i == 5) {
                        student.setProvince(getStringVal(cell));
                    } else if (i == 6) {
                        student.setHobby(getStringVal(cell));
                    } else if (i == 7) {
                        student.setPhone(getStringVal(cell));
                    }
                    i++;
                }
                list.add(student);
            }

        }
        inputStream.close();
        hssfWorkbook.close();
        return list;
    }

    static public void importFromExcle(String fileName, List<Student> compare_list) throws IOException, SQLException, InterruptedException {
        if (compare_list == null || fileName == null || fileName.isEmpty()) {
            ShowMessageUtil.winMessage("������list��pathΪ��");
            return;
        }
        SqlSession sqlSession = MybatisUtil.getSession(false);//TODO ���������ύ����ֹ��;����
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        int cnt = 0;
        List<Student> listFormExcle;
        try {
            listFormExcle = ReadFromExcel(fileName);
        } catch (Exception e) {
            ShowMessageUtil.winMessage("δ�ҵ�ָ���ļ���");
            return;
        }

        progress = 10;
        continue_flag:
        for (Student student : listFormExcle) {
            //TODO ȷ��sid���ظ�
            for (Student e : compare_list) {
                if (e.getSid().equals(student.getSid()))
                    continue continue_flag;
            }
            cnt++;
            compare_list.add(student);
            studentMapper.addStudent(student);
        }
        sqlSession.commit();//TODO �ύ����
        sqlSession.close();
        if (cnt > 0) {   //TODO �����ύ��һ��
            ShowMessageUtil.winMessage("�ɹ�����" + cnt + "�ˣ�");
        } else { //TODO һ��Ҳû�ύ��
            ShowMessageUtil.winMessage("����ʧ�ܣ��������ļ��Ѿ����ڣ������Ǹ�ʽ����ȷ");
        }
    }

    /**
     * ����TXTΪ��Ӧ�Ķ����б�
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    static private List<Student> ReadFromTxt(String fileName) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "gbk");
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String info;
        List<Student> list = new ArrayList<>();
        while ((info = reader.readLine()) != null) {
            String[] s = info.split(" ");
            if (s.length != 7) {
                ShowMessageUtil.winMessage("�����txt��д��ʽ���������²��飡");
                return null;
            }
            if (!s[2].equals("m") && !s[2].equals("wm")) {
                ShowMessageUtil.winMessage("�Ա������ʽ��������m�������ԣ�wm����Ů�ԣ�");
                return null;
            }
            String sex = s[2].equals("m") ? "��" : "Ů";
            Student student = new Student().setSid(s[0])
                    .setName(s[1]).setSex(sex).setBirthday(s[3])
                    .setProvince(s[4]).setHobby(s[5]).setPhone(s[6]);
            list.add(student);
        }
        return list;
    }

    static public int importFromTXT(String filename, List<Student> compare_list) throws IOException {
        if (compare_list == null || filename == null || filename.isEmpty()) {
            ShowMessageUtil.winMessage("������list��pathΪ��");
            return 0;
        }
        SqlSession sqlSession = MybatisUtil.getSession(false);//TODO ���������ύ����ֹ��;����
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> listFromTXT = ReadFromTxt(filename);
        if (listFromTXT == null)
            return 0;
        progress = 10;
        int cnt = 0;
        continue_flag:
        for (Student student : listFromTXT) {
            for (Student e : compare_list) {
                if (e.getSid().equals(student.getSid()))
                    continue continue_flag;
            }
            cnt++;      //TODO ������һ�����ظ�
            compare_list.add(student);
            studentMapper.addStudent(student);
        }
        sqlSession.commit();//TODO �ύ����
        sqlSession.close();
        if (cnt > 0) {   //TODO �����ύ��һ��
            ShowMessageUtil.winMessage("����ɹ���");
        } else { //TODO һ��Ҳû�ύ��
            ShowMessageUtil.winMessage("����ʧ�ܣ��������ļ��Ѿ����ڣ������Ǹ�ʽ����ȷ");
        }
        return cnt;
    }

    static public int ExportTXT(String fileName, List<Student> list) throws IOException {
        if (list == null || fileName == null || fileName.isEmpty()) {
            ShowMessageUtil.winMessage("������list��pathΪ��");
            return 0;
        }
        FileWriter fileWriter = new FileWriter(fileName);
        for (Student student : list) {
            fileWriter.write(student.toString() + "\r\n");
        }
        fileWriter.close();
        ShowMessageUtil.winMessage("����TXT�ɹ���");
        return list.size();
    }

    public List<Student> getAllStudent() {
        return studentMapper.getAllStudent();
    }

    public int addStudent(Student student) {
        for (Student stu : Student.students) {
            if (stu.getSid().equals(student.getSid())) {
                return 0;
            }
        }
        Student.students.add(student);
        return studentMapper.addStudent(student);
    }

    public Student getStudentBySid(String sid) {
        return studentMapper.getStudentBySid(sid);// ���ض�Ӧ��ѧ��
    }

    public int updateStudentInfo(Student student, String sid) {
        int cnt = studentMapper.updateStudentInfo(sid, student);
        if (cnt != 0) {
            for (Student stu : Student.students) {
                if (stu.getSid().equals(sid)) {
                    stu.setSid(student.getSid());
                    stu.setSex(student.getSex());
                    stu.setName(student.getName());
                    stu.setPhone(student.getPhone());
                    stu.setProvince(student.getProvince());
                    stu.setHobby(student.getHobby());
                    stu.setBirthday(student.getBirthday());
                }
            }
        }
        return cnt;
    }

    public int deleteStudentBySid(String sid) {

        int cnt = studentMapper.deleteStudentBySid(sid);
        if (cnt != 0) {
            //TODO ֻ��ʹ�õ��������ܰ�ȫɾ��
            Iterator<Student> iterator = Student.students.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getSid().equals(sid)) {
                    iterator.remove();
                }
            }
        }
        return cnt;
    }

    @Override
    public void close() {
        if (sqlSession != null)
            sqlSession.close();
    }
}
