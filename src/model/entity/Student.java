package model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Student {
    public static List<Student> students = null;
    private String sid;
    private String name;
    private String sex;
    private String birthday;
    private String province;
    private String hobby;
    private String phone;

    public String toString() {
        return "ѧ��:" + sid + "\t"
                + "����:" + name + "\t"
                + "�Ա�:" + sex + "\t"
                + "����:" + birthday + "\t"
                + "ʡ��:" + province + "\t"
                + "����:" + hobby + "\t"
                + "�绰:" + phone;
    }
}
