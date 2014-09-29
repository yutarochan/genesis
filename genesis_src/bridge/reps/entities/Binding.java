/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ public class Binding
/*  4:   */ {
/*  5:   */   Object variable;
/*  6:   */   Object value;
/*  7:   */   
/*  8:   */   public Binding(Object var, Object val)
/*  9:   */   {
/* 10: 9 */     this.variable = var;
/* 11:10 */     this.value = val;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Object getVariable()
/* 15:   */   {
/* 16:14 */     return this.variable;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Object getValue()
/* 20:   */   {
/* 21:18 */     return this.value;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toString()
/* 25:   */   {
/* 26:22 */     return "[" + ((Entity)this.variable).getTypes() + " --- " + ((Entity)this.value).getTypes() + "]";
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Binding
 * JD-Core Version:    0.7.0.1
 */