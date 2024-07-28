import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Menu que se ejecuta al iniciar sesion como administrador
 */
public class PantallaAdministrador extends JFrame{
    private JButton gestionarPacientesButton;
    private JButton gestionarPersonalMedicoButton;
    private JPanel menuAdmin;


    public PantallaAdministrador() {
        super("Menu Administrador");
        setContentPane(menuAdmin);
        gestionarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPacientes menuPacientes = new MenuPacientes();
                menuPacientes.iniciar();
                dispose();
            }
        });
        gestionarPersonalMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuMedico menuMedico = new MenuMedico();
                menuMedico.iniciar();
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
