import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Thu Dec 07 09:33:27 CET 2017
 */



/**
 * @author unknown
 */
public class TSARooms extends JFrame {
    public TSARooms() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - stephane poirier
        scrollPane1 = new JScrollPane();
        frame1 = new JPanel();
        tabbedPane1 = new JTabbedPane();
        panel2 = new JPanel();
        labelTemp = new JLabel();
        labelHumid = new JLabel();
        labelLight = new JLabel();
        tempBindR1S1 = new JLabel();
        humidBindR1S1 = new JLabel();
        lightBindR1S1 = new JLabel();
        panel4 = new JPanel();
        labelTemp4 = new JLabel();
        labelHumid4 = new JLabel();
        labelLight4 = new JLabel();
        tempBindR1S2 = new JLabel();
        humidBindR1S2 = new JLabel();
        lightBindR1S2 = new JLabel();
        tabbedPane2 = new JTabbedPane();
        panel3 = new JPanel();
        labelTemp2 = new JLabel();
        labelHumid2 = new JLabel();
        labelLight2 = new JLabel();
        tempBindR2S1 = new JLabel();
        humidBindR2S1 = new JLabel();
        lightBindR2S1 = new JLabel();
        panel5 = new JPanel();
        tabbedPane3 = new JTabbedPane();
        panel6 = new JPanel();
        labelTemp3 = new JLabel();
        labelHumid3 = new JLabel();
        labelLight3 = new JLabel();
        tempBindR3S1 = new JLabel();
        humidBindR3S1 = new JLabel();
        lightBindR3S1 = new JLabel();
        panel7 = new JPanel();

        //======== this ========
        setTitle("Mes pi\u00e8ces");
        setMinimumSize(new Dimension(250, 400));
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {
            scrollPane1.setPreferredSize(new Dimension(57, 513));
            scrollPane1.setMinimumSize(new Dimension(150, 23));

            //======== frame1 ========
            {
                frame1.setMinimumSize(new Dimension(150, 459));
                frame1.setPreferredSize(new Dimension(57, 513));

                // JFormDesigner evaluation mark
                frame1.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), frame1.getBorder())); frame1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                frame1.setLayout(new GridLayout(3, 0));

                //======== tabbedPane1 ========
                {
                    tabbedPane1.setBorder(new TitledBorder("Pi\u00e8ce 1"));
                    tabbedPane1.setMinimumSize(new Dimension(150, 153));

                    //======== panel2 ========
                    {
                        panel2.setMinimumSize(new Dimension(150, 102));

                        //---- labelTemp ----
                        labelTemp.setText("Temp\u00e9rature");

                        //---- labelHumid ----
                        labelHumid.setText("Humidit\u00e9");

                        //---- labelLight ----
                        labelLight.setText("Luminosit\u00e9");

                        //---- tempBindR1S1 ----
                        tempBindR1S1.setText("text");
                        tempBindR1S1.setForeground(Color.magenta);

                        //---- humidBindR1S1 ----
                        humidBindR1S1.setText("text");
                        humidBindR1S1.setForeground(Color.magenta);

                        //---- lightBindR1S1 ----
                        lightBindR1S1.setText("text");
                        lightBindR1S1.setForeground(Color.magenta);

                        GroupLayout panel2Layout = new GroupLayout(panel2);
                        panel2.setLayout(panel2Layout);
                        panel2Layout.setHorizontalGroup(
                            panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTemp, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelHumid, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelLight, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tempBindR1S1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(humidBindR1S1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(lightBindR1S1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel2Layout.setVerticalGroup(
                            panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelTemp, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tempBindR1S1))
                                    .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelHumid, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(humidBindR1S1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelLight, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lightBindR1S1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(24, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane1.addTab("Capteur 1", panel2);

                    //======== panel4 ========
                    {
                        panel4.setMinimumSize(new Dimension(150, 102));

                        //---- labelTemp4 ----
                        labelTemp4.setText("Temp\u00e9rature");

                        //---- labelHumid4 ----
                        labelHumid4.setText("Humidit\u00e9");

                        //---- labelLight4 ----
                        labelLight4.setText("Luminosit\u00e9");

                        //---- tempBindR1S2 ----
                        tempBindR1S2.setText("text");
                        tempBindR1S2.setForeground(Color.magenta);

                        //---- humidBindR1S2 ----
                        humidBindR1S2.setText("text");
                        humidBindR1S2.setForeground(Color.magenta);

                        //---- lightBindR1S2 ----
                        lightBindR1S2.setText("text");
                        lightBindR1S2.setForeground(Color.magenta);

                        GroupLayout panel4Layout = new GroupLayout(panel4);
                        panel4.setLayout(panel4Layout);
                        panel4Layout.setHorizontalGroup(
                            panel4Layout.createParallelGroup()
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTemp4, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelHumid4, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelLight4, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tempBindR1S2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(humidBindR1S2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(lightBindR1S2, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel4Layout.setVerticalGroup(
                            panel4Layout.createParallelGroup()
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelTemp4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tempBindR1S2))
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelHumid4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(humidBindR1S2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelLight4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lightBindR1S2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(24, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane1.addTab("Capteur 2", panel4);
                }
                frame1.add(tabbedPane1);

                //======== tabbedPane2 ========
                {
                    tabbedPane2.setBorder(new TitledBorder("Pi\u00e8ce 2"));
                    tabbedPane2.setMinimumSize(new Dimension(150, 153));

                    //======== panel3 ========
                    {

                        //---- labelTemp2 ----
                        labelTemp2.setText("Temp\u00e9rature");

                        //---- labelHumid2 ----
                        labelHumid2.setText("Humidit\u00e9");

                        //---- labelLight2 ----
                        labelLight2.setText("Luminosit\u00e9");

                        //---- tempBindR2S1 ----
                        tempBindR2S1.setText("text");
                        tempBindR2S1.setForeground(Color.magenta);

                        //---- humidBindR2S1 ----
                        humidBindR2S1.setText("text");
                        humidBindR2S1.setForeground(Color.magenta);

                        //---- lightBindR2S1 ----
                        lightBindR2S1.setText("text");
                        lightBindR2S1.setForeground(Color.magenta);

                        GroupLayout panel3Layout = new GroupLayout(panel3);
                        panel3.setLayout(panel3Layout);
                        panel3Layout.setHorizontalGroup(
                            panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTemp2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelHumid2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelLight2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tempBindR2S1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(humidBindR2S1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(lightBindR2S1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel3Layout.setVerticalGroup(
                            panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelTemp2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tempBindR2S1))
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelHumid2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(humidBindR2S1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelLight2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lightBindR2S1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane2.addTab("Capteur 1", panel3);

                    //======== panel5 ========
                    {

                        GroupLayout panel5Layout = new GroupLayout(panel5);
                        panel5.setLayout(panel5Layout);
                        panel5Layout.setHorizontalGroup(
                            panel5Layout.createParallelGroup()
                                .addGap(0, 282, Short.MAX_VALUE)
                        );
                        panel5Layout.setVerticalGroup(
                            panel5Layout.createParallelGroup()
                                .addGap(0, 120, Short.MAX_VALUE)
                        );
                    }
                    tabbedPane2.addTab("Capteur2", panel5);
                }
                frame1.add(tabbedPane2);

                //======== tabbedPane3 ========
                {
                    tabbedPane3.setBorder(new TitledBorder("Pi\u00e8ce 3"));
                    tabbedPane3.setMinimumSize(new Dimension(150, 153));

                    //======== panel6 ========
                    {

                        //---- labelTemp3 ----
                        labelTemp3.setText("Temp\u00e9rature");

                        //---- labelHumid3 ----
                        labelHumid3.setText("Humidit\u00e9");

                        //---- labelLight3 ----
                        labelLight3.setText("Luminosit\u00e9");

                        //---- tempBindR3S1 ----
                        tempBindR3S1.setText("text");
                        tempBindR3S1.setForeground(Color.magenta);

                        //---- humidBindR3S1 ----
                        humidBindR3S1.setText("text");
                        humidBindR3S1.setForeground(Color.magenta);

                        //---- lightBindR3S1 ----
                        lightBindR3S1.setText("text");
                        lightBindR3S1.setForeground(Color.magenta);

                        GroupLayout panel6Layout = new GroupLayout(panel6);
                        panel6.setLayout(panel6Layout);
                        panel6Layout.setHorizontalGroup(
                            panel6Layout.createParallelGroup()
                                .addGroup(panel6Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTemp3, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelHumid3, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelLight3, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel6Layout.createParallelGroup()
                                        .addComponent(humidBindR3S1, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lightBindR3S1, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tempBindR3S1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel6Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {humidBindR3S1, lightBindR3S1, tempBindR3S1});
                        panel6Layout.setVerticalGroup(
                            panel6Layout.createParallelGroup()
                                .addGroup(panel6Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelTemp3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tempBindR3S1))
                                    .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelHumid3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(humidBindR3S1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelLight3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lightBindR3S1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel6Layout.linkSize(SwingConstants.VERTICAL, new Component[] {humidBindR3S1, labelHumid3, labelLight3, labelTemp3, lightBindR3S1, tempBindR3S1});
                    }
                    tabbedPane3.addTab("Capteur 1", panel6);

                    //======== panel7 ========
                    {

                        GroupLayout panel7Layout = new GroupLayout(panel7);
                        panel7.setLayout(panel7Layout);
                        panel7Layout.setHorizontalGroup(
                            panel7Layout.createParallelGroup()
                                .addGap(0, 282, Short.MAX_VALUE)
                        );
                        panel7Layout.setVerticalGroup(
                            panel7Layout.createParallelGroup()
                                .addGap(0, 120, Short.MAX_VALUE)
                        );
                    }
                    tabbedPane3.addTab("Capteur2", panel7);
                }
                frame1.add(tabbedPane3);
            }
            scrollPane1.setViewportView(frame1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - stephane poirier
    private JScrollPane scrollPane1;
    private JPanel frame1;
    private JTabbedPane tabbedPane1;
    private JPanel panel2;
    private JLabel labelTemp;
    private JLabel labelHumid;
    private JLabel labelLight;
    private JLabel tempBindR1S1;
    private JLabel humidBindR1S1;
    private JLabel lightBindR1S1;
    private JPanel panel4;
    private JLabel labelTemp4;
    private JLabel labelHumid4;
    private JLabel labelLight4;
    private JLabel tempBindR1S2;
    private JLabel humidBindR1S2;
    private JLabel lightBindR1S2;
    private JTabbedPane tabbedPane2;
    private JPanel panel3;
    private JLabel labelTemp2;
    private JLabel labelHumid2;
    private JLabel labelLight2;
    private JLabel tempBindR2S1;
    private JLabel humidBindR2S1;
    private JLabel lightBindR2S1;
    private JPanel panel5;
    private JTabbedPane tabbedPane3;
    private JPanel panel6;
    private JLabel labelTemp3;
    private JLabel labelHumid3;
    private JLabel labelLight3;
    private JLabel tempBindR3S1;
    private JLabel humidBindR3S1;
    private JLabel lightBindR3S1;
    private JPanel panel7;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public void setTempBindR1S1(String tempBindR1S1) {
        this.tempBindR1S1.setText(tempBindR1S1);
    }

    public void setHumidBindR1S1(String humidBindR1S1) {
        this.humidBindR1S1.setText(humidBindR1S1);
    }

    public void setLightBindR1S1(String lightBindR1S1) {
        this.lightBindR1S1.setText(lightBindR1S1);
    }

    public void setTempBindR2S1(String tempBindR2S1) {
        this.tempBindR2S1.setText(tempBindR2S1);
    }

    public void setHumidBindR2S1(String humidBindR2S1) {
        this.humidBindR2S1.setText(humidBindR2S1);
    }

    public void setLightBindR2S1(String lightBindR2S1) {
        this.lightBindR2S1.setText(lightBindR2S1);
    }

    public void setTempBindR3S1(String tempBindR3S1) {
        this.tempBindR3S1.setText(tempBindR3S1);
    }

    public void setHumidBindR3S1(String humidBindR3S1) {
        this.humidBindR3S1.setText(humidBindR3S1);
    }

    public void setLightBindR3S1(String lightBindR3S1) {
        this.lightBindR3S1.setText(lightBindR3S1);
    }
}
