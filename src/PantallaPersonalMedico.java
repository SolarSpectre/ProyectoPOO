import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menu que se ejecuta al iniciar sesion como personal medico
 */
public class PantallaPersonalMedico extends JFrame{
    private JButton citasButton;
    private JButton tratamientosButton;
    private JPanel menuMedicos;

    public PantallaPersonalMedico() {
        super("Menu Personal Medico");
        setContentPane(menuMedicos);
        tratamientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        citasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
