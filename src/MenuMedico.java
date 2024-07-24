import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MenuMedico extends JFrame{
    private JButton verReportesButton;
    private JButton buscarPersonalMedicoButton;
    private JPanel menuMedico;

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
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
