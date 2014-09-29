/*  1:   */ package leonidFedorov;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ import utils.Mark;
/*  6:   */ 
/*  7:   */ public class MarkedRule
/*  8:   */   extends LinkedList<Entity>
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -5803572135229566374L;
/* 11:14 */   private String reflectionName = null;
/* 12:16 */   private boolean debug = false;
/* 13:   */   
/* 14:   */   public MarkedRule() {}
/* 15:   */   
/* 16:   */   public MarkedRule(String name)
/* 17:   */   {
/* 18:26 */     setReflectionName(name);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public MarkedRule(String name, LinkedList<? extends Entity> ll)
/* 22:   */   {
/* 23:30 */     super(ll);
/* 24:31 */     setReflectionName(name);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public final String getReflectionName()
/* 28:   */   {
/* 29:35 */     if (this.reflectionName != null) {
/* 30:36 */       return this.reflectionName;
/* 31:   */     }
/* 32:39 */     return "";
/* 33:   */   }
/* 34:   */   
/* 35:   */   public final void setReflectionName(String name)
/* 36:   */   {
/* 37:44 */     this.reflectionName = name;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean addRule(Entity rule)
/* 41:   */   {
/* 42:48 */     if ((rule instanceof Entity))
/* 43:   */     {
/* 44:50 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Adding new rule to", getReflectionName(), rule });
/* 45:51 */       return super.add(rule);
/* 46:   */     }
/* 47:54 */     Mark.err(new Object[] {"Attempting to add something that is not a Thing" });
/* 48:55 */     return false;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.MarkedRule
 * JD-Core Version:    0.7.0.1
 */