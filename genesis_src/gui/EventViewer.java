/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import connections.WiredViewer;
/*  8:   */ import java.awt.Color;
/*  9:   */ import java.awt.GridLayout;
/* 10:   */ import java.rmi.server.UID;
/* 11:   */ 
/* 12:   */ public class EventViewer
/* 13:   */   extends WiredViewer
/* 14:   */ {
/* 15:   */   public EventViewer()
/* 16:   */   {
/* 17:15 */     Connections.getPorts(this).addSignalProcessor("view");
/* 18:16 */     setLayout(new GridLayout(1, 0));
/* 19:17 */     setBackground(Color.WHITE);
/* 20:18 */     setOpaque(true);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void view(Object signal)
/* 24:   */   {
/* 25:21 */     if ((signal instanceof Sequence))
/* 26:   */     {
/* 27:22 */       removeAll();
/* 28:23 */       Sequence event = (Sequence)signal;
/* 29:24 */       for (Entity kid : event.getElements())
/* 30:   */       {
/* 31:25 */         WiredPanel wiredPanel = new GenericViewer();
/* 32:26 */         add(wiredPanel);
/* 33:27 */         UID uid = new UID();
/* 34:28 */         Connections.wire(uid.toString(), this, wiredPanel);
/* 35:29 */         Connections.getPorts(this).transmit(uid.toString(), kid);
/* 36:   */       }
/* 37:31 */       revalidate();
/* 38:32 */       repaint();
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.EventViewer
 * JD-Core Version:    0.7.0.1
 */