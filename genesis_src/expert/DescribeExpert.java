/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class DescribeExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static final String PROCESS = "process";
/* 13:   */   
/* 14:   */   public DescribeExpert()
/* 15:   */   {
/* 16:17 */     setName("Describe expert");
/* 17:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Object object)
/* 21:   */   {
/* 22:22 */     if (!(object instanceof Entity)) {
/* 23:23 */       return;
/* 24:   */     }
/* 25:25 */     Entity t = (Entity)object;
/* 26:26 */     if ((t.isAPrimed("description")) && ((t instanceof Function)))
/* 27:   */     {
/* 28:27 */       Function d = (Function)t;
/* 29:28 */       Connections.getPorts(this).transmit("viewer", d);
/* 30:29 */       Connections.getPorts(this).transmit("process", d.getSubject());
/* 31:   */     }
/* 32:   */     else
/* 33:   */     {
/* 34:32 */       Connections.getPorts(this).transmit("next", t);
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.DescribeExpert
 * JD-Core Version:    0.7.0.1
 */