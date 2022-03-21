package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * ʹ��ʱ������Label��Ҫ����JPane����JFrame��������ʾ��
 * �������Ĳ��ն���ΪJPanel����JFrame��
 * �ṩgetInstance������JPanel��װ����
 */
//TODO ����Ƴ�Ϊ��ɫ������Ϊ��ɫ
public class LinkLabel extends JLabel {
    static private final Color blue = new Color(0x5EA6C1);
    static private final Color pink = new Color(0xEF3982);
    private URL url; //����

    private LinkLabel(String vText, String vLink) {
        super(vText);
        setForeground(blue);
        try {
            if (!vLink.startsWith("http://") && !vLink.startsWith("https://"))

                vLink = "http://" + vLink;

            this.url = new URL(vLink);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override   //TODO ����Ƴ��¼�
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor
                        .getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                LinkLabel.this.setForeground(blue);
            }

            @Override   //TODO ��������¼�
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor

                        .getPredefinedCursor(Cursor.HAND_CURSOR));
                LinkLabel.this.setForeground(pink);
            }

            @Override   //TODO ������¼�

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException | URISyntaxException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    private LinkLabel(Icon icon, String vLink) {
        super(icon);
        try {
            if (!vLink.startsWith("http://") && !vLink.startsWith("https://"))

                vLink = "http://" + vLink;

            this.url = new URL(vLink);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override   //TODO ����Ƴ��¼�
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor
                        .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override   //TODO ��������¼�
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor

                        .getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override   //TODO ������¼�

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException | URISyntaxException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    //TODO �õ�һ�������ӹ��ܵ�����
    static public JPanel getInstance(String text, String link) {
        JPanel jPanel = new JPanel();
        jPanel.add(new LinkLabel(text, link));//��JPanel��װ��
        return jPanel;
    }

    //TODO �õ�һ�������ӹ��ܵ�ͼƬ
    static public LinkLabel getInstance(Icon icon, String link) {
        return new LinkLabel(icon, link);
    }

    //TODO ���Թ����Ƿ�ʵ��
    public static void main(String[] args) {
        LinkLabel linkLabel = new LinkLabel(new ImageIcon("src/images/weixin.png"), "baidu.com");
        JFrame jFrame = new JFrame();
        jFrame.setFont(new Font("����", Font.PLAIN, 10));

        jFrame.setLayout(null);
        jFrame.getContentPane().add(linkLabel);
        linkLabel.setBounds(0, 0, 300, 300);
        jFrame.setBounds(0, 0, 1000, 1000);
        jFrame.setVisible(true);
    }

}

