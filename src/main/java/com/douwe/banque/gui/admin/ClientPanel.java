package com.douwe.banque.gui.admin;

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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class ClientPanel extends JPanel {

    private JButton nouveauBtn;
    private JButton supprimerBtn;
    private JButton modifierBtn;
    private JButton filtreBtn;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private JTextField nameText;
    private Connection conn;
    private MainMenuPanel parent;
    IcustomerServ icustomerServ = new CustomerServImpl();
    IoperationsServ ioperationsServ = new IoperationsServImpl();

    public ClientPanel(MainMenuPanel parentFrame) {
            setLayout(new BorderLayout());
            this.parent = parentFrame;
            JPanel haut = new JPanel();
            haut.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel lbl;
            haut.add(lbl = new JLabel("LA LISTE DES CLIENTS DE MA BANQUE POPULAIRE"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            JPanel contenu = new JPanel();
            contenu.setLayout(new BorderLayout());
            JPanel bas = new JPanel();
            bas.setLayout(new FlowLayout());
            nouveauBtn = new JButton("Nouveau");
            supprimerBtn = new JButton("Supprimer");
            modifierBtn = new JButton("Modifier");
            filtreBtn = new JButton("Filtrer");
            
            filtreBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String name = nameText.getText();                    
                    List<Customer> rs = icustomerServ.findByName(name);
                    if (rs != null) {
                        tableModel.setRowCount(0);
                        for (Customer customer : rs) {
                            tableModel.addRow(new Object[]{
                                customer.getId(),
                                customer.getName(),
                                customer.getEmailAddress(),
                                customer.getPhoneNumber()
                            });
                        }
                    }
                    else{                   
                        JOptionPane.showMessageDialog(null, "Impossible de filtrer vos données");                       
                    }
                }
                
            });
            nouveauBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    parent.setContenu(new NouveauClientPanel(parent));
                }
            });
            modifierBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int selected = clientTable.getSelectedRow();
                    if (selected >= 0) {
                        parent.setContenu(new NouveauClientPanel(parent, (Integer) tableModel.getValueAt(selected, 0)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucun client n'est selectionné");
                    }
                }
            });
            supprimerBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int selected = clientTable.getSelectedRow();
                    if (selected >= 0) {
                        
                        
                        IusersServ iusersServ = new IusersServImpl();
                        IaccountServ iaccountServ = new AccountServImpl();
                        IcustomerServ icustomerServ = new CustomerServImpl();                      
                        if (icustomerServ.updateStatut((Integer) tableModel.getValueAt(selected, 0)) > 0) {
                            tableModel.removeRow(selected);
                        }
                        Operations operations = new Operations();
                        operations.setAccount(iaccountServ.findById(1));
                        operations.setDateOperation(new Date(new java.util.Date().getTime()));
                        operations.setDescription("Suppression du client " + tableModel.getValueAt(selected, 1));
                        operations.setOperationType(Operation.suppression);
                        operations.setUsers(iusersServ.findById(1));

                        if(ioperationsServ.create(operations)>0);
                      
                        else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du client " + tableModel.getValueAt(selected, 1));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucune donnée n'est selectionnée");
                    }
                    
                }
            });
            bas.add(nouveauBtn);
            bas.add(modifierBtn);
            bas.add(supprimerBtn);
            JPanel filtrePanel = new JPanel();
            filtrePanel.setLayout(new FlowLayout());
            filtrePanel.add(new JLabel("Nom"));
            filtrePanel.add(nameText = new JTextField());
            nameText.setPreferredSize(new Dimension(100, 25));
            filtrePanel.add(filtreBtn);
            contenu.add(BorderLayout.AFTER_LAST_LINE, bas);
            contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
            tableModel = new DefaultTableModel(new Object[]{"id", "Nom", "Adresse Email", "Téléphone"}, 0);
            clientTable = new JTable(tableModel);
            clientTable.removeColumn(clientTable.getColumnModel().getColumn(0));
            contenu.add(BorderLayout.CENTER, new JScrollPane(clientTable));
            add(BorderLayout.CENTER, contenu);           
            List<Customer> rs = icustomerServ.findByStatus(0);
            for( Customer customer: rs){
                tableModel.addRow(new Object[]{customer.getId(),
                    customer.getName(),
                    customer.getEmailAddress(),
                    customer.getPhoneNumber()
                });
            }           
    }
}
