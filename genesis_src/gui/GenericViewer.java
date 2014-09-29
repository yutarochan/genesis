/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredViewer;
/*  7:   */ import frames.Frame;
/*  8:   */ import frames.SmartFrameFactory;
/*  9:   */ import java.awt.Color;
/* 10:   */ import java.awt.GridLayout;
/* 11:   */ import java.rmi.server.UID;
/* 12:   */ 
/* 13:   */ public class GenericViewer
/* 14:   */   extends WiredPanel
/* 15:   */ {
/* 16:   */   public GenericViewer()
/* 17:   */   {
/* 18:19 */     Connections.getPorts(this).addSignalProcessor("onSignal");
/* 19:20 */     setLayout(new GridLayout(1, 1));
/* 20:21 */     setBackground(Color.WHITE);
/* 21:22 */     setOpaque(true);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void onSignal(Object signal)
/* 25:   */   {
/* 26:25 */     if ((signal instanceof Entity))
/* 27:   */     {
/* 28:26 */       removeAll();
/* 29:27 */       Entity thing = (Entity)signal;
/* 30:   */       
/* 31:29 */       Frame frame = SmartFrameFactory.translate(thing);
/* 32:   */       WiredViewer wiredPanel;
/* 33:   */       WiredViewer wiredPanel;
/* 34:30 */       if (frame == null) {
/* 35:31 */         wiredPanel = new NewFrameViewer();
/* 36:   */       } else {
/* 37:33 */         wiredPanel = frame.getThingViewer();
/* 38:   */       }
/* 39:35 */       add(wiredPanel);
/* 40:36 */       String port = new UID().toString();
/* 41:37 */       Connections.wire(port, this, wiredPanel);
/* 42:38 */       Connections.getPorts(this).transmit(port, thing);
/* 43:39 */       revalidate();
/* 44:40 */       repaint();
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.GenericViewer
 * JD-Core Version:    0.7.0.1
 */