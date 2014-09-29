/*  1:   */ package dylanHolmes;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import java.util.Arrays;
/*  6:   */ import java.util.List;
/*  7:   */ import links.words.WordNet;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class EntityGister
/* 11:   */ {
/* 12:   */   protected WordNet wordNet;
/* 13:   */   
/* 14:   */   public EntityGister()
/* 15:   */   {
/* 16:26 */     this.wordNet = new WordNet();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List<Bundle> sentenceToThreads(String sentence)
/* 20:   */   {
/* 21:31 */     List<Bundle> bundles = Arrays.asList(new Bundle[0]);
/* 22:32 */     List<String> words = Arrays.asList(sentence.split("\\s+"));
/* 23:33 */     for (String word : words) {
/* 24:34 */       bundles.add(this.wordNet.lookup(word));
/* 25:   */     }
/* 26:36 */     return bundles;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Entity match(Entity pattern, List<Bundle> bundles)
/* 30:   */   {
/* 31:44 */     return new Entity();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Entity ruleNearMiss(Entity pattern_1, Entity pattern_2)
/* 35:   */   {
/* 36:49 */     return pattern_1;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void main(String[] args)
/* 40:   */   {
/* 41:58 */     EntityGister g = new EntityGister();
/* 42:   */     
/* 43:60 */     String w = "dog";
/* 44:61 */     Bundle threads = g.wordNet.lookup(w);
/* 45:   */     
/* 46:63 */     Mark.say(new Object[] {threads });
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.EntityGister
 * JD-Core Version:    0.7.0.1
 */