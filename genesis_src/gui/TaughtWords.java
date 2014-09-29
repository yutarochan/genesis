/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Thread;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import memory.ThreadMemory;
/*  7:   */ 
/*  8:   */ public class TaughtWords
/*  9:   */   implements ThreadMemory
/* 10:   */ {
/* 11:15 */   private HashMap<String, Bundle> threadBundles = new HashMap();
/* 12:   */   private static TaughtWords taughtWords;
/* 13:   */   
/* 14:   */   public static TaughtWords getTaughtWords()
/* 15:   */   {
/* 16:20 */     if (taughtWords == null) {
/* 17:21 */       taughtWords = new TaughtWords();
/* 18:   */     }
/* 19:23 */     return taughtWords;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void add(String word, Thread thread)
/* 23:   */   {
/* 24:35 */     Bundle bundle = (Bundle)this.threadBundles.get(word);
/* 25:36 */     if (bundle == null)
/* 26:   */     {
/* 27:37 */       bundle = new Bundle();
/* 28:38 */       this.threadBundles.put(word, bundle);
/* 29:   */     }
/* 30:41 */     bundle.add(thread);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Bundle lookup(String word)
/* 34:   */   {
/* 35:45 */     Bundle bundle = (Bundle)this.threadBundles.get(word);
/* 36:46 */     if ((bundle == null) || (bundle.isEmpty()))
/* 37:   */     {
/* 38:47 */       Thread thread = new Thread("thing");
/* 39:48 */       thread.addType("unknownWord");
/* 40:49 */       thread.addType(word);
/* 41:50 */       return new Bundle(thread);
/* 42:   */     }
/* 43:52 */     return bundle;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Bundle lookup(String word, String pos)
/* 47:   */   {
/* 48:56 */     return lookup(word);
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TaughtWords
 * JD-Core Version:    0.7.0.1
 */