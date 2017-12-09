import javax.swing.*;
import java.awt.*;

/**
 * Class represent one Sensor tab into JabbedPane
 */
public class CustomJPanel extends JPanel {
    // Label which is just fixed text
    private JLabel tempLabel;
    private JLabel humidLabel;
    private JLabel lightLabel;

    // Label will be bind with sensor number
    private JLabel tempBind;
    private JLabel humidBind;
    private JLabel lightBind;

    public CustomJPanel() {
        this.initComponents();
    }

    /**
     * Use to init all components feature, and link together component
     */
    private void initComponents () {
        tempLabel = new JLabel();
        humidLabel = new JLabel();
        lightLabel = new JLabel();

        tempBind = new JLabel();
        humidBind = new JLabel();
        lightBind = new JLabel();

        //======== current panel ========
        {
            this.setMinimumSize(new Dimension(150, 102));
            //---- tempLabel ----
            tempLabel.setText("Temp\u00e9rature");

            //---- humidLabel ----
            humidLabel.setText("Humidit\u00e9");

            //---- lightLabel ----
            lightLabel.setText("Luminosit\u00e9");

            //---- tempBindR3S1 ----
            tempBind.setText("N/A");
            tempBind.setForeground(Color.magenta);

            //---- humidBindR3S1 ----
            humidBind.setText("N/A");
            humidBind.setForeground(Color.magenta);

            //---- lightBindR3S1 ----
            lightBind.setText("N/A");
            lightBind.setForeground(Color.magenta);

            GroupLayout panelLayout = new GroupLayout(this);
            this.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                    panelLayout.createParallelGroup()
                            .addGroup(panelLayout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(tempLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(humidLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lightLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panelLayout.createParallelGroup()
                                            .addComponent(humidBind, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lightBind, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tempBind, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {humidBind, lightBind, tempBind});
            panelLayout.setVerticalGroup(
                    panelLayout.createParallelGroup()
                            .addGroup(panelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(tempLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tempBind))
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(humidLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(humidBind, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(lightLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lightBind, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {humidBind, humidLabel, lightLabel, tempLabel, lightBind, tempBind});
        }
    }

}
