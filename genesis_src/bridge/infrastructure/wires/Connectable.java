/*  1:   */ package bridge.infrastructure.wires;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.LinkedList;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Observable;
/*  8:   */ 
/*  9:   */ public abstract class Connectable
/* 10:   */   extends Observable
/* 11:   */   implements Ported
/* 12:   */ {
/* 13:   */   public Connectable()
/* 14:   */   {
/* 15:11 */     notifyConnectableCreationListeners();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setInput(Object input)
/* 19:   */   {
/* 20:20 */     setInput(input, Wire.INPUT);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object getOutput()
/* 24:   */   {
/* 25:29 */     return getOutput(Wire.OUTPUT);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public abstract void setInput(Object paramObject1, Object paramObject2);
/* 29:   */   
/* 30:   */   public abstract Object getOutput(Object paramObject);
/* 31:   */   
/* 32:   */   public void transmit()
/* 33:   */   {
/* 34:46 */     transmit(Wire.OUTPUT);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void transmit(Object port)
/* 38:   */   {
/* 39:52 */     setChanged();
/* 40:53 */     notifyObservers(port);
/* 41:   */   }
/* 42:   */   
/* 43:57 */   private static List<ConnectableCreationListener> connectableCreationListeners = Collections.synchronizedList(new LinkedList());
/* 44:   */   
/* 45:   */   public static void addConnectableCreationListener(ConnectableCreationListener ccl)
/* 46:   */   {
/* 47:59 */     connectableCreationListeners.add(ccl);
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected void notifyConnectableCreationListeners()
/* 51:   */   {
/* 52:67 */     synchronized (connectableCreationListeners)
/* 53:   */     {
/* 54:68 */       Iterator<ConnectableCreationListener> iCreationListeners = connectableCreationListeners.iterator();
/* 55:69 */       while (iCreationListeners.hasNext())
/* 56:   */       {
/* 57:70 */         ConnectableCreationListener creationListener = (ConnectableCreationListener)iCreationListeners.next();
/* 58:71 */         creationListener.connectableCreated(this);
/* 59:   */       }
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.infrastructure.wires.Connectable
 * JD-Core Version:    0.7.0.1
 */