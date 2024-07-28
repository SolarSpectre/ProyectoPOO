import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menu que se ejecuta al hacer clic en gestionar pacientes
 */
public class MenuPacientes extends JFrame{
    private JPanel menuPacientes;
    private JButton registrarPacientesButton;
    private JButton actualizarPacientesButton;
    private JButton buscarPacientesButton;
    private JButton eliminarRegistroPacientesButton;
    private JButton volverAlMenuPrincipalButton;

    public MenuPacientes() {
        super("Menu Pacientes");
        setContentPane(menuPacientes);
        registrarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro();
                registro.iniciar();
                dispose();
            }
        });
        buscarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Busqueda busqueda = new Busqueda();
                busqueda.iniciar();
                dispose();
            }
        });
        eliminarRegistroPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eliminar eliminar =new Eliminar();
                eliminar.iniciar();
                dispose();
            }
        });
        actualizarPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Actualizar actualizar = new Actualizar();
                actualizar.iniciar();
                dispose();
            }
        });
        volverAlMenuPrincipalButton.addActionListener(new ActionListener() {
            /**
             * Invocado al hacer clic en volver
             *
             * @param e evento clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                PantallaAdministrador pantallaAdministrador = new PantallaAdministrador();
                pantallaAdministrador.iniciar();
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
