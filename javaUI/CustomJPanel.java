import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Class represent one Sensor tab into JabbedPane
 */
public class CustomJPanel extends JPanel {
    // Label which is just fixed text
    private JLabel tempLabel;
    private JLabel humidLabel;
    private JLabel brightLabel;

    // Label will be bind with sensor number
    private JLabel tempBind;
    private JLabel humidBind;
    private JLabel brightBind;

    // Value of the bind label
    private float tempValue;
    private float humidValue;
    private float brightValue;


    private DecimalFormat df;

    public CustomJPanel() {
        this.initComponents();

        // to have 2 decimal
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
    }

    /**
     * Use to init all components feature, and link together component
     */
    private void initComponents () {
        tempLabel = new JLabel();
        humidLabel = new JLabel();
        brightLabel = new JLabel();

        tempBind = new JLabel();
        humidBind = new JLabel();
        brightBind = new JLabel();

        tempValue = -100;
        humidValue = -100;
        brightValue = -100;

        //======== current panel ========
        {
            this.setMinimumSize(new Dimension(150, 102));
            //---- tempLabel ----
            tempLabel.setText("Temp\u00e9rature");

            //---- humidLabel ----
            humidLabel.setText("Humidit\u00e9");

            //---- brightLabel ----
            brightLabel.setText("Luminosit\u00e9");

            //---- tempBindR3S1 ----
            tempBind.setText("N/A");
            tempBind.setForeground(Color.magenta);

            //---- humidBindR3S1 ----
            humidBind.setText("N/A");
            humidBind.setForeground(Color.magenta);

            //---- lightBindR3S1 ----
            brightBind.setText("N/A");
            brightBind.setForeground(Color.magenta);

            GroupLayout panelLayout = new GroupLayout(this);
            this.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                    panelLayout.createParallelGroup()
                            .addGroup(panelLayout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(tempLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(humidLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(brightLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panelLayout.createParallelGroup()
                                            .addComponent(humidBind, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(brightBind, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tempBind, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {humidBind, brightBind, tempBind});
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
                                            .addComponent(brightLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(brightBind, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {humidBind, humidLabel, brightLabel, tempLabel, brightBind, tempBind});
        }
    }

    /**
     * Set the temp value and the temp text into tempBind label
     * @param tempValue
     */
    public void setTempBind (float tempValue) {
        String value = df.format(tempValue);
        this.tempBind.setText(value != null ? value + " °C" : "");
        this.tempValue = tempValue;
    }

    public void setHumidBind(float humidValue) {
        String value = df.format(humidValue);
        this.humidBind.setText(value != null ? value + " %" : "");
        this.humidValue = humidValue;
    }

    public void setBrightBind(float brightValue) {
        String value = df.format(brightValue);
        this.brightBind.setText(value != null ? value + " Lux" : "");
        this.brightValue = brightValue;
    }


    public float getTempValue() {
        return this.tempValue;
    }

    public float getHumidValue() {
        return this.humidValue;
    }

    public float getBrightValue() {
        return this.brightValue;
    }
}
