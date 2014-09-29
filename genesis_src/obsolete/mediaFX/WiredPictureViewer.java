/*  1:   */ package obsolete.mediaFX;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import gui.PictureViewer;
/*  7:   */ import java.awt.BorderLayout;
/*  8:   */ import java.awt.Color;
/*  9:   */ import java.io.File;
/* 10:   */ import java.net.MalformedURLException;
/* 11:   */ import java.net.URI;
/* 12:   */ import java.net.URL;
/* 13:   */ import javax.swing.JFrame;
/* 14:   */ import javax.swing.JLabel;
/* 15:   */ import javax.swing.JPanel;
/* 16:   */ import utils.Mark;
/* 17:   */ 
/* 18:   */ public class WiredPictureViewer
/* 19:   */   extends JPanel
/* 20:   */   implements WiredBox
/* 21:   */ {
/* 22:   */   public static final String URL = "url";
/* 23:24 */   public static String SET_TIME = "set time";
/* 24:   */   public static final String TIME = "time";
/* 25:28 */   private String TEST_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
/* 26:   */   private String name;
/* 27:   */   private String url;
/* 28:   */   private PictureViewer pictureViewer;
/* 29:   */   
/* 30:   */   public String getUrl()
/* 31:   */   {
/* 32:37 */     return this.url;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getName()
/* 36:   */   {
/* 37:41 */     return this.name;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public WiredPictureViewer(String name, String url)
/* 41:   */   {
/* 42:45 */     this();
/* 43:46 */     processURL(url);
/* 44:47 */     this.name = name;
/* 45:48 */     add(new JLabel(name, 0), "South");
/* 46:   */   }
/* 47:   */   
/* 48:   */   public WiredPictureViewer(String url)
/* 49:   */   {
/* 50:52 */     this();
/* 51:53 */     processURL(url);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public WiredPictureViewer()
/* 55:   */   {
/* 56:57 */     setLayout(new BorderLayout());
/* 57:58 */     add(getPictureViewer(), "Center");
/* 58:59 */     Connections.getPorts(this).addSignalProcessor("url", "processURL");
/* 59:60 */     setBackground(Color.WHITE);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void processURL(Object o)
/* 63:   */   {
/* 64:64 */     if ((o instanceof String))
/* 65:   */     {
/* 66:65 */       this.url = ((String)o);
/* 67:66 */       Mark.say(new Object[] {"Playing url", this.url });
/* 68:67 */       this.pictureViewer.setImage(this.url);
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public PictureViewer getPictureViewer()
/* 73:   */   {
/* 74:72 */     if (this.pictureViewer == null) {
/* 75:73 */       this.pictureViewer = new PictureViewer();
/* 76:   */     }
/* 77:75 */     return this.pictureViewer;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public static void main(String[] ignore)
/* 81:   */     throws MalformedURLException, InterruptedException
/* 82:   */   {
/* 83:80 */     File file = new File("c:/output/test.jpg");
/* 84:   */     
/* 85:82 */     String url = file.toURI().toURL().toExternalForm();
/* 86:   */     
/* 87:84 */     JFrame frame = new JFrame();
/* 88:   */     
/* 89:86 */     frame.setBounds(50, 50, 800, 400);
/* 90:87 */     frame.setVisible(true);
/* 91:   */     
/* 92:89 */     frame.setDefaultCloseOperation(3);
/* 93:   */     
/* 94:91 */     Mark.say(new Object[] {url });
/* 95:   */     
/* 96:93 */     WiredPictureViewer player = new WiredPictureViewer("Hello World", url);
/* 97:   */     
/* 98:   */ 
/* 99:   */ 
/* :0:97 */     frame.add(player);
/* :1:   */   }
/* :2:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mediaFX.WiredPictureViewer
 * JD-Core Version:    0.7.0.1
 */