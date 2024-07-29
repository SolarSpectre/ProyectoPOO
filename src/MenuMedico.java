import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Menu de Gestion de Medicos y Reportes Estadisticos
 */
public class MenuMedico extends JFrame{
    private JButton verReportesButton;
    private JButton buscarPersonalMedicoButton;
    private JPanel menuMedico;
    private JButton volverAlMenuPrincipalButton;

    public MenuMedico() {
        super("Menu Medicos");
        setContentPane(menuMedico);
        verReportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VerReportes verReportes = new VerReportes();
                    verReportes.iniciar();
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        buscarPersonalMedicoButton.addActionListener(new ActionListener() {
            /**
             * Invocado al hacer clic
             *
             * @param e el evento clic
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarMedico agregarMedico = new AgregarMedico();
                agregarMedico.iniciar();
                dispose();
            }
        });
        volverAlMenuPrincipalButton.addActionListener(new ActionListener() {
            /**
             * Boton para volver al menu principal
             *
             * @param e el evento a procesar
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
