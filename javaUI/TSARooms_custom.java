import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TSARooms_custom extends JFrame {
    private static int INITIAL_ROOM_NUMBER = 10;
    private static int INITIAL_SENSOR_BY_ROOM_NUMBER = 10;

    private JPanel frame1;
    // Array of JTabbedPane which is the rooms container
    private JTabbedPane[] roomSensorsArray;
    // Array of JPane = sensor. sensorArray[1][2] sensor 2 of room 1
    public CustomJPanel[][] sensorArray;
    // Store last date of send msg of sensor
    public Date[][] sensorLastMsg;

    // array which store mean tab of rooms
    public CustomJPanel[] meanTab;

    /***************************
     * METHOD IMPLEMENTATION
     **************************/

    TSARooms_custom() {
        this.meanTab = new CustomJPanel[INITIAL_ROOM_NUMBER];
        this.sensorArray = new CustomJPanel[INITIAL_ROOM_NUMBER][INITIAL_SENSOR_BY_ROOM_NUMBER];
        this.roomSensorsArray = new JTabbedPane[INITIAL_ROOM_NUMBER];
        this.sensorLastMsg = new Date[INITIAL_ROOM_NUMBER][INITIAL_SENSOR_BY_ROOM_NUMBER];
        initComponents();
        this.validate();
    }

    /**
     * True if c is in the main window
     * @param sensorID index of sensor inside sensorArray sublist
     * @param roomID index of room inside roomSensorsArray
     * @return boolean
     */
    public boolean isSensorInsideRoom(int sensorID, int roomID) throws InvalideRoomException {
        CustomJPanel sensor;
        JTabbedPane room;

        // Check if sensor is create in this array
        if (this.sensorArray[roomID][sensorID] != null)
            sensor = this.sensorArray[roomID][sensorID];
        else
            return false;

        // Check if room is create in this array
        if (this.roomSensorsArray[roomID] != null)
            room = this.roomSensorsArray[roomID];
        else
            throw new InvalideRoomException("Pièce " + roomID + " non créer");

        return sensor.getParent() == room;
    }

    public boolean isRoomInsideFrame(int roomID) {
        JTabbedPane room;

        // Check if room is create in this array
        if (this.roomSensorsArray[roomID] != null)
            room = this.roomSensorsArray[roomID];
        else
            return false;

        return room.getParent() == this.frame1;
    }

    /**
     * Make empty tabbedPane with own template feature
     * @param roomNb use in title of JTabbedPane
     * @return JTabbedPane
     */
    private static JTabbedPane createEmptyTabbedPane(int roomNb) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new TitledBorder("Pi\u00e8ce " + roomNb));
        tabbedPane.setMinimumSize(new Dimension(150, 153));
        tabbedPane.setSize(new Dimension(250, 200));
        tabbedPane.setPreferredSize(new Dimension(250, 200));

        tabbedPane.validate();
        return tabbedPane;
    }

    public void addSensor(int roomID, int sensorID) throws InvalideRoomException {
        JTabbedPane room = this.roomSensorsArray[roomID];
        if (isSensorInsideRoom(sensorID, roomID)) {
            System.err.println("La pièce " + roomID + " contient déjà le capteur N° " + sensorID);
        } else {
            // Add new sensor
            this.sensorArray[roomID][sensorID] = new CustomJPanel();
            // Add sensor to room
            room.addTab("Capteur " + sensorID, this.sensorArray[roomID][sensorID]);
        }
    }

    public void addRoom(int roomID) {
        if (isRoomInsideFrame(roomID)) {
            System.err.println("L'application contient déjà la pièce N° " + roomID);
        } else {
            // Add new room to array
            this.roomSensorsArray[roomID] = createEmptyTabbedPane(roomID);
            // Make mean tab link to room
            this.meanTab[roomID] = new CustomJPanel();
            // Add tab to room
            this.roomSensorsArray[roomID].addTab("Moyenne", meanTab[roomID]);
            // Add room to the GUI
            frame1.add(this.roomSensorsArray[roomID]);
        }
    }

    private void initComponents() {
        JScrollPane scrollPane1 = new JScrollPane();
        frame1 = new JPanel();

        //======== this ========
        setTitle("TinySmartAutomation");
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
                        new TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0), "A. DERNIAME & S. POIRIER",
                                TitledBorder.CENTER, TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12), Color.red),
                        frame1.getBorder()));
                frame1.addPropertyChangeListener(e -> {
                    if ("border".equals(e.getPropertyName()))
                        throw new RuntimeException();
                });

                frame1.setLayout(new GridLayout(10, 0));

            }
            scrollPane1.setViewportView(frame1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(contentPaneLayout.createParallelGroup().addComponent(scrollPane1,
                GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE));
        contentPaneLayout.setVerticalGroup(contentPaneLayout.createParallelGroup().addComponent(scrollPane1,
                GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE));
        pack();
        setLocationRelativeTo(getOwner());
    }
    private void updateGUI () {
        boolean roomEmpty = true;
        for (int roomID = 0; roomID < INITIAL_ROOM_NUMBER; roomID++) {
            for (int sensorID = 0; sensorID < INITIAL_SENSOR_BY_ROOM_NUMBER; sensorID++) {
                Date d = this.sensorLastMsg[roomID][sensorID];
                if (d != null) {
                    roomEmpty = false;
                    long MAX_DURATION = MILLISECONDS.convert(2, SECONDS);
                    long duration = new Date().getTime() - d.getTime();
                    if (duration >= MAX_DURATION) {
                        // Sensor not send msg during 2 seconds
                        this.removeSensor(roomID, sensorID);
                        roomEmpty = true;
                    }
                }
            }

            if (this.roomSensorsArray[roomID] != null && roomEmpty) {
                this.removeRoom(roomID);
            }
        }
    }

    private void removeRoom(int roomID) {
        // remove mean tab
        CustomJPanel jp = this.meanTab[roomID];
        this.roomSensorsArray [roomID].remove(jp);
        this.meanTab[roomID] = null;

        // remove room tabpanne
        frame1.remove(this.roomSensorsArray[roomID]);
        this.roomSensorsArray[roomID] = null;

        // repain gui
        frame1.repaint();
    }

    private void removeSensor(int roomID, int sensorID) {
        this.roomSensorsArray [roomID].remove(this.sensorArray[roomID][sensorID]);
        this.sensorLastMsg[roomID][sensorID] = null;
        this.sensorArray[roomID][sensorID] = null;
    }

    public void scheduleUpdateGUI() {
        int interval = 200;  // iterate every 200 millisec.
        java.util.Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateGUI(); // supprime un capteur/ une pièce de l'interface si nécéssaire
            }
        }, 0, interval);
    }
}
