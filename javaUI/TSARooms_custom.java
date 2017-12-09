import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
        /*
         * Created by JFormDesigner on Thu Dec 07 09:33:27 CET 2017
         */


/**
 * @author unknown
 */
public class TSARooms_custom extends JFrame {
    public static int INITIAL_ROOM_NUMBER = 10;
    public static int INITIAL_SENSOR_BY_ROOM_NUMBER = 10;

    // Parent container
    private JScrollPane scrollPane1;
    private JPanel frame1;
    // Array of JTabbedPane which is the rooms container
    public ArrayList<JTabbedPane> roomSensorsArray;
    // Array of JPane = sensor. sensorArray[1][2] sensor 2 of room 1
    public ArrayList<ArrayList<CustomJPanel>> sensorArray;


    public TSARooms_custom() {
        initDummySensorArray();
        initDummyRoomSensorsArray();
        initComponents();

        this.addSensor(1,1);
        this.addTab(1);
    }


    /**
     * Make empty tabbedPane with own template feature
     * @param roomNb use in title of JTabbedPane
     * @return
     */
    private JTabbedPane createEmptyTabbedPane (int roomNb) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new TitledBorder("Pi\u00e8ce " + roomNb));
        tabbedPane.setMinimumSize(new Dimension(150, 153));
        return tabbedPane;
    }


    public void addSensor(int roomID, int sensorID) {
        CustomJPanel sensor = this.sensorArray.get(roomID).get(sensorID);
        JTabbedPane room = this.roomSensorsArray.get(roomID);
        if (sensor.getParent() == room) {
            System.err.println("La pièce " + roomID + " contient déjà le capteur N° " + sensorID);
        } else {
            room.addTab("Capteur " + sensorID, sensor);
        }
    }

    public void addTab(int roomID) {
        JTabbedPane room = this.roomSensorsArray.get(roomID);
        if (room.getParent() == frame1) {
            System.err.println("L'application contient déjà la pièce N° " + roomID);
        } else {
            frame1.add(room);
        }
    }

    private void initDummySensorArray () {
        this.sensorArray = new ArrayList<>();
        for (int i = 0; i < INITIAL_ROOM_NUMBER; i++) {
            // For intiate rooms array with dummy elements
            ArrayList<CustomJPanel> dummyList = new ArrayList<>();

            for (int j = 0; j < INITIAL_SENSOR_BY_ROOM_NUMBER; j++)
                // For initiate sensors subarray with dummy elements
                dummyList.add(new CustomJPanel());

            this.sensorArray.add(dummyList);
        }
    }

    private void initDummyRoomSensorsArray() {
        this.roomSensorsArray = new ArrayList<>();
        for (int i =0; i < INITIAL_ROOM_NUMBER; i++) {
            this.roomSensorsArray.add(createEmptyTabbedPane(i));
        }
    }
    private void initComponents() {
        scrollPane1 = new JScrollPane();
        frame1 = new JPanel();

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
                        new TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                                "JFormDesigner Evaluation", TitledBorder.CENTER,
                                TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                                Color.red), frame1.getBorder())); frame1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                frame1.setLayout(new GridLayout(3, 0));

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
    }
}
