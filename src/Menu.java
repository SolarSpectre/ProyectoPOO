import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Menu extends JFrame {
    private JPanel panelMenu;
    private JButton buscarPacientesButton;
    private JButton registrarPacientesButton;

    public Menu(){
        super("Menu");
        setContentPane(panelMenu);
        buscarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Busqueda busqueda = new Busqueda();
                busqueda.iniciar();
                dispose();
            }
        });
        registrarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro();
                registro.iniciar();
                dispose();
            }
        });
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
