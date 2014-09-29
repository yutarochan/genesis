/*  1:   */ package start;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import minilisp.LList;
/*  9:   */ import utils.Mark;
/* 10:   */ import utils.PairOfEntities;
/* 11:   */ 
/* 12:   */ public class StartPostprocessor
/* 13:   */   extends AbstractWiredBox
/* 14:   */ {
/* 15:21 */   boolean debug = false;
/* 16:23 */   private HashMap<String, String> objects = new HashMap();
/* 17:   */   private static Entity p0;
/* 18:   */   private static StartPostprocessor startPostprocessor;
/* 19:   */   
/* 20:   */   public static StartPostprocessor getStartPostprocessor()
/* 21:   */   {
/* 22:30 */     if (startPostprocessor == null) {
/* 23:31 */       startPostprocessor = new StartPostprocessor();
/* 24:   */     }
/* 25:33 */     return startPostprocessor;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public StartPostprocessor()
/* 29:   */   {
/* 30:38 */     setName("Story processor");
/* 31:39 */     Connections.getPorts(this).addSignalProcessor("process");
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void process(Object object)
/* 35:   */   {
/* 36:43 */     if ((object instanceof Entity))
/* 37:   */     {
/* 38:44 */       Entity t = (Entity)object;
/* 39:45 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Start postprocessor received", t.asString() });
/* 40:46 */       Connections.getPorts(this).transmit(object);
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   private String resolve(String s, LList<PairOfEntities> bindings)
/* 45:   */   {
/* 46:51 */     for (Object object : bindings)
/* 47:   */     {
/* 48:52 */       PairOfEntities pair = (PairOfEntities)object;
/* 49:53 */       Entity d = pair.getDatum();
/* 50:54 */       Entity p = pair.getPattern();
/* 51:55 */       if (s.equals(p.getType())) {
/* 52:56 */         return pair.getDatum().getType();
/* 53:   */       }
/* 54:   */     }
/* 55:59 */     return null;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.StartPostprocessor
 * JD-Core Version:    0.7.0.1
 */