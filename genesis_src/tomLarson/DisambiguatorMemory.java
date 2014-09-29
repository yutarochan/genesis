/*  1:   */ package tomLarson;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Thread;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public class DisambiguatorMemory
/*  9:   */ {
/* 10:   */   private Map<String, ThreadTree> map;
/* 11:   */   
/* 12:   */   public DisambiguatorMemory()
/* 13:   */   {
/* 14:23 */     this.map = new HashMap();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean containsVerb(String verb)
/* 18:   */   {
/* 19:28 */     return this.map.keySet().contains(verb.toLowerCase());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addThread(String verb, Thread t)
/* 23:   */   {
/* 24:39 */     verb = verb.toLowerCase();
/* 25:40 */     if (containsVerb(verb)) {
/* 26:41 */       ((ThreadTree)this.map.get(verb)).addThread(t);
/* 27:   */     } else {
/* 28:44 */       this.map.put(verb, ThreadTree.makeThreadTree(t));
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public ThreadTree getThreadTree(String verb)
/* 33:   */   {
/* 34:49 */     verb = verb.toLowerCase();
/* 35:50 */     if (containsVerb(verb)) {
/* 36:51 */       return (ThreadTree)this.map.get(verb);
/* 37:   */     }
/* 38:53 */     return new ThreadTree();
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.DisambiguatorMemory
 * JD-Core Version:    0.7.0.1
 */