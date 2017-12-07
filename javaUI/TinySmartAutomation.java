import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;


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
    // serial.sendPackets();

    TinySmartAutomation.initWindow();

  }

  public static void initWindow() {
//    JFrame frame;
//    JPanel pnPanel0;
//    JPanel pnPanelRooms;
//    JPanel pnPanelRoom1;
//    JLabel lbLabelRoomS1;
//    JTextField tfTextTemp;
//    JTextField tfTextHumid;
//    JTextField tfTextLight;
//    JLabel lbLabel2;
//    JLabel lbLabel7;
//    JLabel lbLabel9;
//
//
//    pnPanel0 = new JPanel();
//    GridBagLayout gbPanel0 = new GridBagLayout();
//    GridBagConstraints gbcPanel0 = new GridBagConstraints();
//    pnPanel0.setLayout( gbPanel0 );
//
//    pnPanelRooms = new JPanel();
//    pnPanelRooms.setBorder( BorderFactory.createTitledBorder( "Mes pièces" ) );
//    pnPanelRooms.setAlignmentX( 1.0 );
//    pnPanelRooms.setAlignmentY( 0.521 );
//    pnPanelRooms.setBackground( new Color( 232,230,232 ) );
//    pnPanelRooms.setToolTipText( "Mes pièces :" );
//    GridBagLayout gbPanelRooms = new GridBagLayout();
//    GridBagConstraints gbcPanelRooms = new GridBagConstraints();
//    pnPanelRooms.setLayout( gbPanelRooms );
//
//    pnPanelRoom1 = new JPanel();
//    pnPanelRoom1.setBorder( BorderFactory.createTitledBorder( "Pièce 1" ) );
//    pnPanelRoom1.setAutoscrolls( true );
//    pnPanelRoom1.setBackground( new Color( 232,230,232 ) );
//    pnPanelRoom1.setForeground( new Color( 232,230,232 ) );
//    GridBagLayout gbPanelRoom1 = new GridBagLayout();
//    GridBagConstraints gbcPanelRoom1 = new GridBagConstraints();
//    pnPanelRoom1.setLayout( gbPanelRoom1 );
//
//    lbLabelRoomS1 = new JLabel( "Capteur 1 :"  );
//    gbcPanelRoom1.gridx = 0;
//    gbcPanelRoom1.gridy = 0;
//    gbcPanelRoom1.gridwidth = 4;
//    gbcPanelRoom1.gridheight = 3;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 1;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( lbLabelRoomS1, gbcPanelRoom1 );
//    pnPanelRoom1.add( lbLabelRoomS1 );
//
//    tfTextTemp = new JTextField( );
//    gbcPanelRoom1.gridx = 8;
//    gbcPanelRoom1.gridy = 0;
//    gbcPanelRoom1.gridwidth = 5;
//    gbcPanelRoom1.gridheight = 2;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 0;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( tfTextTemp, gbcPanelRoom1 );
//    pnPanelRoom1.add( tfTextTemp );
//
//    tfTextHumid = new JTextField( );
//    gbcPanelRoom1.gridx = 8;
//    gbcPanelRoom1.gridy = 2;
//    gbcPanelRoom1.gridwidth = 5;
//    gbcPanelRoom1.gridheight = 2;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 0;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( tfTextHumid, gbcPanelRoom1 );
//    pnPanelRoom1.add( tfTextHumid );
//
//    tfTextLight = new JTextField( );
//    gbcPanelRoom1.gridx = 8;
//    gbcPanelRoom1.gridy = 4;
//    gbcPanelRoom1.gridwidth = 5;
//    gbcPanelRoom1.gridheight = 2;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 0;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( tfTextLight, gbcPanelRoom1 );
//    pnPanelRoom1.add( tfTextLight );
//
//    lbLabel2 = new JLabel( "Temperature"  );
//    gbcPanelRoom1.gridx = 5;
//    gbcPanelRoom1.gridy = 0;
//    gbcPanelRoom1.gridwidth = 3;
//    gbcPanelRoom1.gridheight = 2;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 1;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( lbLabel2, gbcPanelRoom1 );
//    pnPanelRoom1.add( lbLabel2 );
//
//    lbLabel7 = new JLabel( "Humidité"  );
//    gbcPanelRoom1.gridx = 5;
//    gbcPanelRoom1.gridy = 2;
//    gbcPanelRoom1.gridwidth = 3;
//    gbcPanelRoom1.gridheight = 2;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 1;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( lbLabel7, gbcPanelRoom1 );
//    pnPanelRoom1.add( lbLabel7 );
//
//    lbLabel9 = new JLabel( "Luminosité"  );
//    gbcPanelRoom1.gridx = 5;
//    gbcPanelRoom1.gridy = 4;
//    gbcPanelRoom1.gridwidth = 3;
//    gbcPanelRoom1.gridheight = 2;
//    gbcPanelRoom1.fill = GridBagConstraints.BOTH;
//    gbcPanelRoom1.weightx = 1;
//    gbcPanelRoom1.weighty = 1;
//    gbcPanelRoom1.anchor = GridBagConstraints.NORTH;
//    gbPanelRoom1.setConstraints( lbLabel9, gbcPanelRoom1 );
//    pnPanelRoom1.add( lbLabel9 );
//    gbcPanelRooms.gridx = 0;
//    gbcPanelRooms.gridy = 0;
//    gbcPanelRooms.gridwidth = 28;
//    gbcPanelRooms.gridheight = 7;
//    gbcPanelRooms.fill = GridBagConstraints.BOTH;
//    gbcPanelRooms.weightx = 1;
//    gbcPanelRooms.weighty = 0;
//    gbcPanelRooms.anchor = GridBagConstraints.NORTH;
//    gbPanelRooms.setConstraints( pnPanelRoom1, gbcPanelRooms );
//    pnPanelRooms.add( pnPanelRoom1 );
//    JScrollPane scpPanelRooms = new JScrollPane( pnPanelRooms );
//    gbcPanel0.gridx = 0;
//    gbcPanel0.gridy = 0;
//    gbcPanel0.gridwidth = 36;
//    gbcPanel0.gridheight = 29;
//    gbcPanel0.fill = GridBagConstraints.BOTH;
//    gbcPanel0.weightx = 1;
//    gbcPanel0.weighty = 0;
//    gbcPanel0.anchor = GridBagConstraints.NORTH;
//    gbPanel0.setConstraints( scpPanelRooms, gbcPanel0 );
//    pnPanel0.add( scpPanelRooms );
//
//
//    frame = new JFrame("Oscilloscope");
//    frame.setSize(main.getPreferredSize());
//    frame.getContentPane().add(pnPanel0);
//    frame.setVisible(true);


    //////////////////////
    //////////////////////
    //////////////////////
    //////////////////////
    //////////////////////





    // JFrame frame;
    // JPanel main = new JPanel(new BorderLayout());

    // main.setMinimumSize(new Dimension(500, 250));
    // main.setPreferredSize(new Dimension(800, 400));

    // // The frame part
    // frame = new JFrame("Oscilloscope");
    // frame.setSize(main.getPreferredSize());
    // frame.getContentPane().add(main);
//     frame.setVisible(true);
    // frame.addWindowListener(new WindowAdapter() {
    //   public void windowClosing(WindowEvent e) {
    //     System.exit(0);
    //   }
    // });

    TSARooms tsa = new TSARooms();
    tsa.setVisible(true);

    System.out.println("hélène ! pouit ! panda !");
  }

}
