/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import connections.WiredJPanel;
/*  8:   */ import connections.WiredViewer;
/*  9:   */ import java.awt.Container;
/* 10:   */ import javax.swing.JFrame;
/* 11:   */ import javax.swing.JLabel;
/* 12:   */ 
/* 13:   */ public class WiredBlinkingBox
/* 14:   */   extends BlinkingBox
/* 15:   */   implements WiredBox
/* 16:   */ {
/* 17:   */   WiredViewer viewer;
/* 18:   */   WiredJPanel box;
/* 19:   */   
/* 20:   */   public WiredBlinkingBox(WiredJPanel box)
/* 21:   */   {
/* 22:23 */     this();
/* 23:24 */     this.box = box;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public WiredBlinkingBox()
/* 27:   */   {
/* 28:29 */     Connections.getPorts(this).addSignalProcessor("process");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public WiredBlinkingBox(String string, AbstractWiredBox expert, WiredJPanel viewer, BlinkingBoxPanel blinkingBoxPanel)
/* 32:   */   {
/* 33:43 */     this(viewer);
/* 34:44 */     if ((viewer instanceof WiredViewer)) {
/* 35:45 */       this.viewer = ((WiredViewer)viewer);
/* 36:   */     }
/* 37:47 */     setTitle(string);
/* 38:48 */     setGraphic(viewer);
/* 39:49 */     setName(string + " blinker");
/* 40:50 */     viewer.setName(string + " viewer");
/* 41:51 */     blinkingBoxPanel.add(this);
/* 42:52 */     Connections.wire("viewer", expert, this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void process(Object signal)
/* 46:   */   {
/* 47:56 */     setInput(signal);
/* 48:58 */     if (this.viewer != null) {
/* 49:59 */       this.viewer.view(signal);
/* 50:   */     } else {
/* 51:63 */       Connections.getPorts(this).transmit(signal);
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setInput(Object input)
/* 56:   */   {
/* 57:68 */     blink();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setInput(Object input, Object port)
/* 61:   */   {
/* 62:72 */     blink();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void main(String[] args)
/* 66:   */   {
/* 67:76 */     WiredBlinkingBox box = new WiredBlinkingBox();
/* 68:77 */     box.setTitle("Sample title");
/* 69:78 */     box.setMemory(new JLabel("Memory"));
/* 70:79 */     box.setGraphic(new JLabel("Graphic"));
/* 71:80 */     JFrame frame = new JFrame("Testing");
/* 72:81 */     frame.getContentPane().add(box);
/* 73:82 */     frame.setBounds(100, 100, 400, 400);
/* 74:83 */     frame.setDefaultCloseOperation(3);
/* 75:84 */     frame.setVisible(true);
/* 76:85 */     box.setInput(new Object());
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.WiredBlinkingBox
 * JD-Core Version:    0.7.0.1
 */