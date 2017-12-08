import net.tinyos.message.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TinySmartAutomation implements MessageListener {

  private MoteIF moteIF;
  private TSARooms tsa;
  private boolean GUIStarted = false;

  public TinySmartAutomation(MoteIF moteIF) {
    this.moteIF = moteIF;
    this.moteIF.registerListener(new TinySmartAutomationMsg(), this);
  }

  public void sendPackets() {
    short roomID = 0;
    TinySmartAutomationMsg payload = new TinySmartAutomationMsg();

    try {
      while (true) {
        System.out.println("Sending packet " + roomID);
        payload.set_roomID(roomID);
        moteIF.send(0, payload);
        roomID++;
        try {
          Thread.sleep(1000);
        } catch (InterruptedException exception) {
        }
      }
    } catch (IOException exception) {
      System.err.println("Exception thrown when sending packets. Exiting.");
      System.err.println(exception);
    }
  }

  /**
   * Convert long to float using binary represenation
   * (because Serial message from TOS only allow int8, int16, int32.
   * So, to put a flot, we use memcpy to copy float in int32)
   * @param  long x long to convert to float
   * @return float nomber corresponding to source bytes
   */
  public float convertLongtoFloatBinary(long source) {

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

      l = msg.get_temperature();
      f = convertLongtoFloatBinary(l);
      String value = Float.toString(f);
      tsa.setTempBindR1S1(value != null ? value + " °C" : "");

      l = msg.get_humidity();
      f = convertLongtoFloatBinary(l);
      value = Float.toString(f);
      tsa.setHumidBindR1S1(value != null ? value + " %" : "");

      l = msg.get_brightness();
      f = convertLongtoFloatBinary(l);
      value = Float.toString(f);
      tsa.setLightBindR1S1(value != null ? value + " Lux" : "");
    }
  }

  private static void usage() {
    System.err.println("usage: TinySmartAutomation [-comm <source>]");
  }

  public static void main(String[] args) throws Exception {
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
//    serial.sendPackets();

  }

  public void initWindow() {

    tsa = new TSARooms();
    tsa.setVisible(true);
    this.GUIStarted = true;
    System.out.println("Interface graphique lancée");
  }

}
