package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhap extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsername; 
    private JPasswordField txtPassword;

    
    public static Connection getConnection() throws Exception {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        File dbFile = new File("QuanLyThuvien.accdb");
        String url = "jdbc:ucanaccess://" + dbFile.getAbsolutePath();
        return DriverManager.getConnection(url);
    }

    public DangNhap() {
        setTitle("Đăng Nhập");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
ImageIcon icon = new ImageIcon(
    "C:/Users/qn158/Desktop/NgocMai/img/anhnen.png"
);

Image img = icon.getImage();


JPanel background = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
            img,
            0,
            0,
            getWidth(),
            getHeight(),
            this
        );
    }
};

background.setLayout(null);


setContentPane(background);

        JLabel lblTitle = new JLabel("Đăng Nhập", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(180, 0, 0));
        lblTitle.setBounds(100, 20, 230, 30);
        background.add(lblTitle);

        JLabel lblUser = new JLabel("Tài khoản");
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setForeground(new Color(128, 0, 128));
        lblUser.setBounds(70, 90, 90, 25);
        background.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(160, 90, 200, 25);
        background.add(txtUsername);

        JLabel lblPass = new JLabel("Mật Khẩu");
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));
        lblPass.setForeground(new Color(128, 0, 128));
        lblPass.setBounds(70, 140, 90, 25);
        background.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 140, 200, 25);
        background.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(130, 200, 80, 30);
        background.add(btnLogin);

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(230, 200, 80, 30);
        background.add(btnExit);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
                    return;
                }

                if (username.equals("ngocmai") && password.equals("123")) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                    dispose();
                    new QuanLySach().setVisible(true);
                    return;
                }

                try (Connection conn = getConnection()) {
                    String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                        dispose();
                        new QuanLySach().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi kết nối CSDL: " + ex.getMessage());
                }
            }
        });

        btnExit.addActionListener(_ -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangNhap().setVisible(true);
        });
    }
}