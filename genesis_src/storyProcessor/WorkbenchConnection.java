/*  1:   */ package storyProcessor;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import java.util.Vector;
/*  8:   */ import translator.Translator;
/*  9:   */ import utils.Mark;
/* 10:   */ 
/* 11:   */ public class WorkbenchConnection
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   private static WorkbenchConnection instance;
/* 15:   */   
/* 16:   */   public static WorkbenchConnection getWorkbenchConnection()
/* 17:   */   {
/* 18:20 */     if (instance == null) {
/* 19:21 */       instance = new WorkbenchConnection();
/* 20:   */     }
/* 21:23 */     return instance;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void transmit(Entity x)
/* 25:   */   {
/* 26:27 */     Connections.getPorts(this).transmit(x);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void test()
/* 30:   */   {
/* 31:31 */     Mark.say(
/* 32:   */     
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:41 */       new Object[] { "Running insertion test" });
/* 42:   */     try
/* 43:   */     {
/* 44:33 */       transmit((Entity)Translator.getTranslator().translate("Start story titled \"Unnamed\".").getElements().get(0));
/* 45:34 */       transmit((Entity)Translator.getTranslator().translate("John loves Mary.").getElements().get(0));
/* 46:35 */       transmit((Entity)Translator.getTranslator().translate("John marries Mary.").getElements().get(0));
/* 47:36 */       transmit((Entity)Translator.getTranslator().translate("John marries Mary because John likes money.").getElements().get(0));
/* 48:   */     }
/* 49:   */     catch (Exception e)
/* 50:   */     {
/* 51:39 */       e.printStackTrace();
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.WorkbenchConnection
 * JD-Core Version:    0.7.0.1
 */