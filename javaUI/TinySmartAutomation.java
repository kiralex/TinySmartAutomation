import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.tinyos.message.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;

public class TinySmartAutomation implements MessageListener {

  private MoteIF moteIF;

  public TinySmartAutomation(MoteIF moteIF) {
    this.moteIF = moteIF;
    this.moteIF.registerListener(new TinySmartAutomationMsg(), this);
  }

  public void sendPackets() {
    int counter = 0;
    TinySmartAutomationMsg payload = new TinySmartAutomationMsg();

    try {
      while (true) {
        System.out.println("Sending packet " + counter);
        //payload.set_counter(counter);
        moteIF.send(0, payload);
        counter++;
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
    TinySmartAutomationMsg msg = (TinySmartAutomationMsg) message;
    System.out.println("Received message");
    float f;
    long l;

    l = msg.get_temperature();
    f = convertLongtoFloatBinary(l);
    System.out.println(f);
  }

  private static void usage() {
    System.err.println("usage: TinySmartAutomation [-comm <source>]");
  }

  public static void main(String[] args) throws Exception {
    // String source = null;
    // if (args.length == 2) {
    //   if (!args[0].equals("-comm")) {
    //     usage();
    //     System.exit(1);
    //   }
    //   source = args[1];
    // } else if (args.length != 0) {
    //   usage();
    //   System.exit(1);
    // }

    // PhoenixSource phoenix;

    // if (source == null) {
    //   phoenix = BuildSource.makePhoenix(PrintStreamMessenger.err);
    // } else {
    //   phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
    // }

    // MoteIF mif = new MoteIF(phoenix);
    // TinySmartAutomation serial = new TinySmartAutomation(mif);
    // serial.sendPackets();

    TinySmartAutomation.initWindow();

  }

  public static void initWindow() {
    JFrame frame;
    JPanel main = new JPanel(new BorderLayout());

    main.setMinimumSize(new Dimension(500, 250));
    main.setPreferredSize(new Dimension(800, 400));

    // The frame part
    frame = new JFrame("Oscilloscope");
    frame.setSize(main.getPreferredSize());
    frame.getContentPane().add(main);
    frame.setVisible(true);
    // frame.addWindowListener(new WindowAdapter() {
    //   public void windowClosing(WindowEvent e) {
    //     System.exit(0);
    //   }
    // });

    System.out.println("hélène ! pouit ! panda !");
  }

}
