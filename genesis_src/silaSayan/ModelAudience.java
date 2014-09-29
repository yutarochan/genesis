/*  1:   */ package silaSayan;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ 
/*  8:   */ public class ModelAudience
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:   */   private String declaredAudience;
/* 12:14 */   private static String DECLARED_AUDIENCE = "audience declared by user";
/* 13:   */   
/* 14:   */   public ModelAudience()
/* 15:   */   {
/* 16:17 */     setName("Audience Modeller");
/* 17:18 */     Connections.getPorts(this).addSignalProcessor(DECLARED_AUDIENCE, "establishAudience");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void establishAudience(Object o)
/* 21:   */   {
/* 22:24 */     if ((o instanceof String)) {
/* 23:25 */       this.declaredAudience = ((String)o);
/* 24:   */     }
/* 25:27 */     BetterSignal signal = new BetterSignal(new Object[] { "Declared Audience", this.declaredAudience });
/* 26:28 */     Connections.getPorts(this).transmit(signal);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static void loadAudienceModel(int audienceType) {}
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.ModelAudience
 * JD-Core Version:    0.7.0.1
 */