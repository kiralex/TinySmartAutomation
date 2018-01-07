import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

public class TinySmartAutomation implements MessageListener {

    private TSARooms_custom tsaCustom;
    private boolean GUIStarted = false;

    public TinySmartAutomation(MoteIF moteIF) {
        moteIF.registerListener(new TinySmartAutomationMsg(), this);
    }



    /**
     * Convert long to float using binary represenation
     * (because Serial message from TOS only allow int8, int16, int32.
     * So, to put a flot, we use memcpy to copy float in int32)
     * @param  source x long to convert to float
     * @return float nomber corresponding to source bytes
     */
    private float convertLongtoFloatBinary(long source) {

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(source);

        ByteBuffer floatInBytes = ByteBuffer.allocate(4);
        buffer.rewind();
        for (int i = 0; i < 4; i++) {
            floatInBytes = (ByteBuffer) floatInBytes.put(i, buffer.get(4 + i));
        }

        return floatInBytes.order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public void messageReceived(int to, Message message) {
        if (this.GUIStarted) {
            TinySmartAutomationMsg msg = (TinySmartAutomationMsg) message;
            long l;
            float f;
            short sensorID;
            Integer roomID;

            sensorID = msg.get_sensorID();
            roomID = (int) msg.get_roomID();

            // Add the room inside gui
            if (!tsaCustom.isRoomInsideFrame(roomID)) {
                tsaCustom.addRoom(roomID);
            }

            if (sensorID != 254) {
                // Add the sensor inside GUI
                try {
                    if (!tsaCustom.isSensorInsideRoom(sensorID, roomID)) {
                        tsaCustom.addSensor(roomID, sensorID);
                    }
                } catch (InvalideRoomException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

                // Bind value into GUI
                l = msg.get_temperature();
                f = convertLongtoFloatBinary(l);
                tsaCustom.sensorArray[roomID][sensorID].setTempBind(f);

                l = msg.get_humidity();
                f = convertLongtoFloatBinary(l);
                tsaCustom.sensorArray[roomID][sensorID].setHumidBind(f);

                l = msg.get_brightness();
                f = convertLongtoFloatBinary(l);
                tsaCustom.sensorArray[roomID][sensorID].setBrightBind(f);

                // Add date
                tsaCustom.sensorLastMsg[roomID][sensorID] = new Date();

                // Bind mean tab value
                // tsaCustom.doMeanRoom(roomID);
            } else if (tsaCustom.meanTab[roomID] != null) {
                // Bind value into GUI
                l = msg.get_temperature();
                f = convertLongtoFloatBinary(l);
                tsaCustom.meanTab[roomID].setTempBind(f);

                l = msg.get_humidity();
                f = convertLongtoFloatBinary(l);
                tsaCustom.meanTab[roomID].setHumidBind(f);

                l = msg.get_brightness();
                f = convertLongtoFloatBinary(l);
                tsaCustom.meanTab[roomID].setBrightBind(f);
            }


        }

    }

    private static void usage() {
        System.err.println("usage: TinySmartAutomation [-comm <source>]");
    }

    public static void main(String[] args) throws Exception {

//        System.out.println("Date : " + new Date());
        String source = null;
        if (args.length == 2) {
            if (!args[0].equals("-comm")) {
                usage();
                System.exit(1);
            }
            source = args[1];
        } else if (args.length != 0) {
            usage();
            System.exit(1);
        }

        PhoenixSource phoenix;

        if (source == null) {
            phoenix = BuildSource.makePhoenix(PrintStreamMessenger.err);
        } else {
            phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
        }

        MoteIF mif = new MoteIF(phoenix);
        TinySmartAutomation serial = new TinySmartAutomation(mif);
        serial.initWindow();

    }

    private void initWindow() {
        tsaCustom = new TSARooms_custom();
        tsaCustom.setVisible(true);
        this.GUIStarted = true;

        tsaCustom.scheduleUpdateGUI();

        System.out.println(" ============================= \n" + "Interface graphique lancée \n "
                + "=============================");
    }

}
