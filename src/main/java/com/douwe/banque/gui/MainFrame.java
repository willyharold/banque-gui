package com.douwe.banque.gui;

import com.douwe.banque.data.Operation;
import com.douwe.banque.gui.common.LoginPanel;
import com.douwe.banque.gui.common.UserInfo;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class MainFrame extends JFrame {

    private HeaderPanel headerPanel;
    private JPanel contentPanel;

    public MainFrame() {
        setTitle("Ma banque à moi tout seul");
        getContentPane().setLayout(new BorderLayout(10, 10));
        headerPanel = new HeaderPanel() {
            @Override
            public void deconnexion() {
                Connection conn;
                contentPanel.removeAll();
                contentPanel.add(BorderLayout.CENTER, new LoginPanel() {
                    @Override
                    public void success() {
                        contentPanel.removeAll();
                        contentPanel.add(BorderLayout.CENTER, new MainMenuPanel());
                        contentPanel.validate();
                        headerPanel.setEnabledHeader(true);
                    }
                });
                try {
                    conn = DriverManager.getConnection("jdbc:sqlite:banque.db");
                    PreparedStatement pst3 = conn.prepareStatement("insert into operations(operationType, dateOperation,description, account_id, user_id) values (?,?,?,?,?)");
                    pst3.setInt(1, Operation.deconnexion.ordinal());
                    pst3.setDate(2, new Date(new java.util.Date().getTime()));
                    pst3.setString(3, "Deconnexion de l'utilisateur  " + UserInfo.getUsername());
                    pst3.setInt(4, 1);
                    pst3.setInt(5, 1);
                    pst3.executeUpdate();
                    pst3.close();
                    conn.close();
                } catch (SQLException ex) {
                    
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                contentPanel.validate();
            }
        };
        getContentPane().add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JPanel login = new LoginPanel() {
            @Override
            public void success() {
                contentPanel.removeAll();
                contentPanel.add(BorderLayout.CENTER, new MainMenuPanel());
                contentPanel.validate();
                headerPanel.setEnabledHeader(true);
            }
        };
        contentPanel.add(login, BorderLayout.CENTER);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
