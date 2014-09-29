/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class InternalNarrator
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:11 */   public static Sequence text = new Sequence();
/* 13:13 */   public static Sequence narratorStory = new Sequence();
/* 14:14 */   public static Sequence narratorReflections = new Sequence();
/* 15:15 */   private boolean communicatedWithSpecifier = false;
/* 16:   */   public static String TEXT_IN;
/* 17:   */   public static String STORY_IN;
/* 18:   */   public static String REFLECTIONS_IN;
/* 19:   */   public static String TEXT_OUT;
/* 20:   */   public static String STORY_OUT;
/* 21:   */   
/* 22:   */   public InternalNarrator()
/* 23:   */   {
/* 24:28 */     Connections.getPorts(this).addSignalProcessor(TEXT_IN, "storeText");
/* 25:29 */     Connections.getPorts(this).addSignalProcessor(STORY_IN, "storeUnderstanding");
/* 26:30 */     Connections.getPorts(this).addSignalProcessor(REFLECTIONS_IN, "storeReflections");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void storeText(Object o)
/* 30:   */   {
/* 31:35 */     if ((o instanceof Sequence))
/* 32:   */     {
/* 33:36 */       text = (Sequence)o;
/* 34:37 */       Connections.getPorts(this).transmit(TEXT_OUT, text);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void storeUnderstanding(Object o)
/* 39:   */   {
/* 40:42 */     if ((o instanceof Sequence))
/* 41:   */     {
/* 42:43 */       narratorStory = (Sequence)o;
/* 43:44 */       if ((narratorStory != null) && (narratorReflections != null) && (!this.communicatedWithSpecifier))
/* 44:   */       {
/* 45:45 */         wrapStoryAndReflections(narratorStory, narratorReflections);
/* 46:46 */         this.communicatedWithSpecifier = true;
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void storeReflections(Object o)
/* 52:   */   {
/* 53:52 */     if ((o instanceof Sequence))
/* 54:   */     {
/* 55:53 */       narratorReflections = (Sequence)o;
/* 56:54 */       if ((narratorStory != null) && (narratorReflections != null) && (!this.communicatedWithSpecifier))
/* 57:   */       {
/* 58:55 */         wrapStoryAndReflections(narratorStory, narratorReflections);
/* 59:56 */         this.communicatedWithSpecifier = true;
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void wrapStoryAndReflections(Sequence story, Sequence reflections)
/* 65:   */   {
/* 66:62 */     BetterSignal wrap = new BetterSignal(new Object[] { story, reflections });
/* 67:63 */     Connections.getPorts(this).transmit(STORY_OUT, wrap);
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.InternalNarrator
 * JD-Core Version:    0.7.0.1
 */