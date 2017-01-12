package com.douwe.banque.gui.admin;

import com.douwe.banque.data.Customer;
import com.douwe.banque.data.Operation;
import com.douwe.banque.data.Operations;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.Users;
import com.douwe.banque.gui.MainMenuPanel;
import com.douwe.banque.service.IaccountServ;
import com.douwe.banque.service.IcustomerServ;
import com.douwe.banque.service.Impl.AccountServImpl;
import com.douwe.banque.service.Impl.CustomerServImpl;
import com.douwe.banque.service.Impl.IoperationsServImpl;
import com.douwe.banque.service.Impl.IusersServImpl;
import com.douwe.banque.service.IoperationsServ;
import com.douwe.banque.service.IusersServ;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class NouveauClientPanel extends JPanel {

    private JTextField nameText;
    private JTextField emailText;
    private JTextField phoneText;
    private JButton btnEnregistrer;
    private int id = -1;
    private MainMenuPanel parent;
    private IcustomerServ icustomerServ = new CustomerServImpl();
    private IusersServ iusersServ = new IusersServImpl();
    private IoperationsServ ioperationsServ = new IoperationsServImpl();
    private IaccountServ iaccountServ = new AccountServImpl();

    public NouveauClientPanel(MainMenuPanel parentFrame, int id) {
        this(parentFrame);
        this.id = id;
        if (id > 0) {
                Customer c = new Customer();
                c = icustomerServ.findById(id);
                nameText.setText(c.getName());
                emailText.setText(c.getEmailAddress());
                phoneText.setText(c.getPhoneNumber());
       
        }
    }

    public NouveauClientPanel(MainMenuPanel parentFrame) {
        this.parent = parentFrame;
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        haut.add(lbl = new JLabel("AJOUT D'UN NOUVEAU CLIENT DANS MA BANQUE POPULAIRE"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
        builder.append("Nom", nameText = new JTextField());
        builder.append("Adresse Email", emailText = new JTextField());
        builder.append("Numéro de Téléphone", phoneText = new JTextField());
        builder.append(btnEnregistrer = new JButton("Enrégistrer"));
        add(BorderLayout.CENTER, builder.getPanel());
        btnEnregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (id <= 0) {
                    
                        String name = nameText.getText();
                        String email = emailText.getText();
                        String phone = phoneText.getText();
                        if ((name == null) || ("".equals(name))) {
                            JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                            return;
                        }
                        if ((email == null) || ("".equals(email))) {
                            JOptionPane.showMessageDialog(null, "L'adresse email est obligatoire");
                            return;
                        }
                        if ((phone == null) || ("".equals(phone))) {
                            JOptionPane.showMessageDialog(null, "Le numéro de téléphone est obligatoire");
                            return;
                        }
                        String val = name.replaceAll(" ", "").toLowerCase();
                        Users user = new Users();
                        Customer c = new Customer();
                        user.setPasswd("admin");
                        user.setUsername(val);
                        user.setRole(RoleType.customer);
                        if(iusersServ.create(user) > 0){
                        c.setName(name);
                        c.setEmailAddress(email);
                        c.setPhoneNumber(phone);
                        c.setUsers(iusersServ.findByUsername(val));
                        icustomerServ.create(c);
                        Operations operations = new Operations();
                        operations.setOperationType(Operation.ajout);
                        operations.setDateOperation(new Date(new java.util.Date().getTime()));
                        operations.setDescription("Ajout du client " + name);
                        operations.setAccount(iaccountServ.findById(1));
                        operations.setUsers(iusersServ.findById(1));
                        ioperationsServ.create(operations);

                        operations.setOperationType(Operation.ajout);
                        operations.setDateOperation(new Date(new java.util.Date().getTime()));
                        operations.setDescription("Ajout de l'utilisateur " + val);
                        operations.setAccount(iaccountServ.findById(1));
                        operations.setUsers(iusersServ.findById(1));
                        ioperationsServ.create(operations);
                        JOptionPane.showMessageDialog(null, "Un compte avec login " + val + " et mot de passe 'admin' a été créé");
                        parent.setContenu(new ClientPanel(parent));
                    } else {
                        JOptionPane.showMessageDialog(null, "Impossible de créer le compte");
                    }
                } else {
                    
                        String name = nameText.getText();
                        String email = emailText.getText();
                        String phone = phoneText.getText();
                        if ((name == null) || ("".equals(name))) {
                            JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                            return;
                        }
                        if ((email == null) || ("".equals(email))) {
                            JOptionPane.showMessageDialog(null, "L'adresse email est obligatoire");
                            return;
                        }
                        if ((phone == null) || ("".equals(phone))) {
                            JOptionPane.showMessageDialog(null, "Le numéro de téléphone est obligatoire");
                            return;
                        }
                        Customer c = new Customer();
                        c.setId(id);
                        c.setName(name);
                        c.setEmailAddress(email);
                        c.setPhoneNumber(phone);
                        if(icustomerServ.update(c)>0){                        
                        parent.setContenu(new ClientPanel(parent));
                        }
                    else {
                        JOptionPane.showMessageDialog(null, "Impossible de mettre a jour votre compte");
                    }
                }
                
            }
        });
    }
}