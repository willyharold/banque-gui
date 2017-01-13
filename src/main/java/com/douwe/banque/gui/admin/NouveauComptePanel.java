package com.douwe.banque.gui.admin;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import com.douwe.banque.data.Operation;
import com.douwe.banque.data.Operations;
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
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class NouveauComptePanel extends JPanel {

    private JTextField numberText;
    private JTextField balanceText;
    private JComboBox<AccountType> typeText;
    private JTextField customerText;
    private JButton btnEnregistrer;
    private int id = -1;
    private Connection conn;
    private MainMenuPanel parent;
    private IcustomerServ icustomerServ = new CustomerServImpl();
    private IusersServ iusersServ = new IusersServImpl();
    private IoperationsServ ioperationsServ = new IoperationsServImpl();
    private IaccountServ iaccountServ = new AccountServImpl();

    public NouveauComptePanel(MainMenuPanel parentFrame, int account_id) {
        this(parentFrame);
        this.id = account_id;
        if (this.id > 0) {
            btnEnregistrer.setText("Modifier");
                Account accounts = new Account();
                accounts = iaccountServ.findByAccount(id);
                if(accounts!=null){
                    numberText.setText(accounts.getAccountNumber());
                    balanceText.setText("" + accounts.getBalance());
                    balanceText.setEnabled(false);
                    customerText.setEnabled(false);
                    customerText.setText(accounts.getCustomer().getName());
                    typeText.setSelectedItem(accounts.getType().toString());
                }
        }
    }

    public NouveauComptePanel(MainMenuPanel parentFrame) {
        this.parent = parentFrame;
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        haut.add(lbl = new JLabel("AJOUT D'UN NOUVEAU COMPTE DANS MA BANQUE POPULAIRE"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
        builder.append("Numéro Compte", numberText = new JTextField());
        builder.append("Solde initial", balanceText = new JTextField());
        builder.append("Type de Compte", typeText = new JComboBox<AccountType>());
        typeText.addItem(AccountType.deposit);
        typeText.addItem(AccountType.saving);
        builder.append("Titulaire", customerText = new JTextField());
        builder.append(btnEnregistrer = new JButton("Enrégistrer"));
        add(BorderLayout.CENTER, builder.getPanel());
        btnEnregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (id > 0) {
                    
                        // here I'm updating an account
                        String number = numberText.getText();
                        AccountType type = (AccountType) typeText.getSelectedItem();
                        if ((number == null) || ("".equals(number))) {
                            JOptionPane.showMessageDialog(null, "Le numéro du compte n'est pas specifie");
                            return;
                        }
                        if (type == null) {
                            JOptionPane.showMessageDialog(null, "Le type du compte n'est pas specifie");
                            return;
                        }
                        Account account =iaccountServ.findById(id);
                        account.setType(type);
                        account.setAccountNumber(number);
                        if(iaccountServ.update(account)>0){
                        
                        parent.setContenu(new ComptePanel(parent));
                    } else{
                        JOptionPane.showMessageDialog(null, "Impossible de mettre à jour le compte");
                    }

                } else {
                    try {
                        // I need to get the customer_id
                        String customer = customerText.getText();
                        String number = numberText.getText();
                        String balanceT = balanceText.getText();
                        AccountType type = (AccountType) typeText.getSelectedItem();
                        double balance;
                        if ((customer == null) || ("".equals(customer))) {
                            JOptionPane.showMessageDialog(null, "Le nom du client n'est pas specifie");
                            return;
                        }
                        if ((number == null) || ("".equals(number))) {
                            JOptionPane.showMessageDialog(null, "Le numéro du compte n'est pas specifie");
                            return;
                        }
                        if ((balanceT == null) || ("".equals(balanceT))) {
                            JOptionPane.showMessageDialog(null, "Le solde initial du compte n'est pas specifie");
                            return;
                        }
                        if (type == null) {
                            JOptionPane.showMessageDialog(null, "Le type du compte n'est pas specifie");
                            return;
                        }
                        try {
                            balance = Double.valueOf(balanceT);
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Le solde compte doit être un nombre positif");
                            return;
                        }
                        if (balance < 0) {
                            JOptionPane.showMessageDialog(null, "Le solde compte doit être un nombre positif");
                            return;
                        }
                        Account account = new Account();
                        account.setAccountNumber(number);
                        account.setBalance(balance);
                        account.setDateCreation(new Date(new java.util.Date().getTime()));
                        account.setType(type);
                        List<Customer> customers = new LinkedList<Customer>();
                        customers = icustomerServ.findByName(customer);
                        if(customers!=null){
                            account.setCustomer(customers.get(0));
                            iaccountServ.create(account);
                            Operations operations = new Operations();
                            operations.setOperationType(Operation.ouverture);
                            operations.setDateOperation(new Date(new java.util.Date().getTime()));
                            operations.setDescription("Ouverture du compte " + number);
                            operations.setUsers(iusersServ.findById(1));
                            operations.setAccount(iaccountServ.findById(1));
                            ioperationsServ.create(operations);
//                        conn = DriverManager.getConnection("jdbc:sqlite:banque.db");
//                        PreparedStatement pst = conn.prepareStatement("select id from customer where name = ?");
//                        pst.setString(1, customer);
//                        ResultSet rs = pst.executeQuery();
//                        if (rs.next()) {
//                            int customer_id = rs.getInt("id");
//                            conn.setAutoCommit(false);
//                            PreparedStatement st = conn.prepareStatement("insert into account(accountNumber,balance,dateCreation,type, customer_id) values(?,?,?,?,?)");
//                            st.setString(1, number);
//                            st.setDouble(2, balance);
//                            st.setDate(3, new Date(new java.util.Date().getTime()));
//                            st.setInt(4, type.ordinal());
//                            st.setInt(5, customer_id);
//                            st.executeUpdate();
//                            st.close();
//                            PreparedStatement pst3 = conn.prepareStatement("insert into operations(operationType, dateOperation,description, account_id, user_id) values (?,?,?,?,?)");
//                            pst3.setInt(1, Operation.ouverture.ordinal());
//                            pst3.setDate(2, new Date(new java.util.Date().getTime()));
//                            pst3.setString(3, "Ouverture du compte " + number);
//                            pst3.setInt(4, 1);
//                            pst3.setInt(5, 1);
//                            pst3.executeUpdate();
//                            pst3.close();
//                            conn.commit();
                        } else {
                            JOptionPane.showMessageDialog(null, "Le client spécifié n'existe pas");
                            return;
                        }
                        
                        parent.setContenu(new ComptePanel(parent));
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Impossible d'enregistrer le compte");
                    }
                    
                }
                
            }
        });
    }
}
