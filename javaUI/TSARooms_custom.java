import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TSARooms_custom extends JFrame {
    public static int INITIAL_ROOM_NUMBER = 10;
    public static int INITIAL_SENSOR_BY_ROOM_NUMBER = 10;

    // Parents container
    private JScrollPane scrollPane1;
    private JPanel frame1;
    // Array of JTabbedPane which is the rooms container
    public JTabbedPane[] roomSensorsArray;
    // Array of JPane = sensor. sensorArray[1][2] sensor 2 of room 1
    public CustomJPanel[][] sensorArray;
    // Store last date of send msg of sensor
    public Date[][] sensorLastMsg;

    // array which store mean tab of rooms
    public CustomJPanel[] meanTab;

    /***************************
     * METHOD IMPLEMENTATION
     **************************/

    public TSARooms_custom() {
        this.meanTab = new CustomJPanel[INITIAL_ROOM_NUMBER];
        this.sensorArray = new CustomJPanel[INITIAL_ROOM_NUMBER][INITIAL_SENSOR_BY_ROOM_NUMBER];
        this.roomSensorsArray = new JTabbedPane[INITIAL_ROOM_NUMBER];
        this.sensorLastMsg = new Date[INITIAL_ROOM_NUMBER][INITIAL_SENSOR_BY_ROOM_NUMBER];
        initComponents();
    }

    /**
     * True if c is in the main window
     * @param sensorID index of sensor inside sensorArray sublist
     * @param roomID index of room inside roomSensorsArray
     * @return
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
     * @return
     */
    private JTabbedPane createEmptyTabbedPane(int roomNb) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new TitledBorder("Pi\u00e8ce " + roomNb));
        tabbedPane.setMinimumSize(new Dimension(150, 153));

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
                        new TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0), "JFormDesigner Evaluation",
                                TitledBorder.CENTER, TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12), Color.red),
                        frame1.getBorder()));
                frame1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                    public void propertyChange(java.beans.PropertyChangeEvent e) {
                        if ("border".equals(e.getPropertyName()))
                            throw new RuntimeException();
                    }
                });

                frame1.setLayout(new GridLayout(3, 0));

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

    public void doMeanRoom(int roomID) {
        float temp = 0, humid = 0, bright = 0;
        int tempCpt = 0, humidCpt = 0, brightCpt = 0;

        for (int i = 0; i < INITIAL_SENSOR_BY_ROOM_NUMBER; i++) {
            CustomJPanel cj = this.sensorArray[roomID][i];
            // Temp
            try {
                if (cj.getTempValue() > -99) {
                    temp += cj.getTempValue();
                    tempCpt++;
                }
            } catch (NumberFormatException ex) {
                // Not a float
            }

            // Humid
            try {
                if (cj.getHumidValue() > -99) {
                    humid += cj.getHumidValue();
                    humidCpt++;
                }
            } catch (NumberFormatException ex) {
                // Not a float
            }

            // Bright
            try {
                if (cj.getBrightValue() > -99) {
                    bright += cj.getBrightValue();
                    brightCpt++;
                }
            } catch (NumberFormatException ex) {
                // Not a float
            }
        }

        // Set value into mean tab
        meanTab[roomID].setTempBind(temp / tempCpt);
        meanTab[roomID].setHumidBind(humid / humidCpt);
        meanTab[roomID].setBrightBind(bright / brightCpt);
    }

    private void updateGUI () {
        for (int roomID = 0; roomID < INITIAL_ROOM_NUMBER; roomID++) {
            for (int sensorID = 0; sensorID < INITIAL_SENSOR_BY_ROOM_NUMBER; sensorID++) {
                Date d = this.sensorLastMsg[roomID][sensorID];
                if (d != null) {
                    long MAX_DURATION = MILLISECONDS.convert(5, SECONDS);
                    long duration = new Date().getTime() - d.getTime();
                    if (duration >= MAX_DURATION) {
                        // Sensor not send msg during 5 seconds
                        this.removeSensor(roomID, sensorID);
                    }
                }
            }
        }
    }

    private void removeSensor(int roomID, int sensorID) {
        this.roomSensorsArray [roomID].remove(this.sensorArray[roomID][sensorID]);
        this.sensorArray[roomID][sensorID] = null;
    }

    public void scheduleUpdateGUI() {
        int interval = 1000;  // iterate every sec.
        java.util.Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateGUI();
            }
        }, 10, interval);
    }
}
