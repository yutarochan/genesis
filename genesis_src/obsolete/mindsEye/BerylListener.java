/*  1:   */ package obsolete.mindsEye;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ 
/* 10:   */ public class BerylListener
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:14 */   private List<Map> buffer = new ArrayList();
/* 14:   */   
/* 15:   */   public BerylListener()
/* 16:   */   {
/* 17:17 */     Connections.getPorts(this).addSignalProcessor("doIt");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void reset()
/* 21:   */   {
/* 22:21 */     this.buffer = new ArrayList();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<Map> fetchVat()
/* 26:   */   {
/* 27:25 */     return this.buffer;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public synchronized void doIt(Object arg)
/* 31:   */   {
/* 32:29 */     List<Map> farg = (List)arg;
/* 33:30 */     if (farg.size() == 0) {
/* 34:31 */       notifyAll();
/* 35:   */     } else {
/* 36:34 */       this.buffer.addAll(farg);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getName()
/* 41:   */   {
/* 42:39 */     return null;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.BerylListener
 * JD-Core Version:    0.7.0.1
 */