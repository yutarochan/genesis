/*  1:   */ package bridge.infrastructure.wires;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class AbstractAdapter
/*  6:   */   extends Connectable
/*  7:   */ {
/*  8:12 */   public static final Object INPUT = Wire.INPUT;
/*  9:13 */   public static final Object OUTPUT = Wire.OUTPUT;
/* 10:   */   Object rawInput;
/* 11:   */   Object adaptedInput;
/* 12:   */   boolean adapted;
/* 13:   */   
/* 14:   */   public Object adapt(Object rawInput)
/* 15:   */   {
/* 16:19 */     return rawInput;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean acceptibleInput(Object input)
/* 20:   */   {
/* 21:20 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public AbstractAdapter()
/* 25:   */   {
/* 26:26 */     this.rawInput = null;
/* 27:27 */     this.adaptedInput = null;
/* 28:28 */     this.adapted = false;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setInput(Object input, Object port)
/* 32:   */   {
/* 33:32 */     if (port == INPUT)
/* 34:   */     {
/* 35:33 */       if (acceptibleInput(input))
/* 36:   */       {
/* 37:34 */         this.rawInput = input;
/* 38:35 */         this.adapted = false;
/* 39:36 */         this.adaptedInput = null;
/* 40:37 */         transmit(OUTPUT);
/* 41:   */       }
/* 42:   */       else
/* 43:   */       {
/* 44:39 */         System.err.println(getClass().getName() + ": Didn't know what to do with " + input);
/* 45:   */       }
/* 46:   */     }
/* 47:   */     else {
/* 48:42 */       System.err.println(getClass().getName() + ": Input port " + port.toString() + " not recognized.");
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getOutput(Object port)
/* 53:   */   {
/* 54:47 */     if (port == OUTPUT)
/* 55:   */     {
/* 56:48 */       if (!this.adapted)
/* 57:   */       {
/* 58:49 */         this.adaptedInput = adapt(this.rawInput);
/* 59:50 */         this.rawInput = null;
/* 60:51 */         this.adapted = true;
/* 61:   */       }
/* 62:53 */       return this.adaptedInput;
/* 63:   */     }
/* 64:55 */     System.err.println(getClass().getName() + ": Output port " + port.toString() + " not recognized.");
/* 65:56 */     return null;
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.infrastructure.wires.AbstractAdapter
 * JD-Core Version:    0.7.0.1
 */