/*  1:   */ package m2;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashSet;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Set;
/*  9:   */ import java.util.Vector;
/* 10:   */ 
/* 11:   */ public class InputTracker
/* 12:   */ {
/* 13:21 */   private static List<String> sentences = new ArrayList();
/* 14:22 */   private static List<Entity> frames = new ArrayList();
/* 15:23 */   private static Set<Entity> topFrames = new HashSet();
/* 16:   */   
/* 17:   */   public static synchronized void addSentence(String english)
/* 18:   */   {
/* 19:36 */     sentences.add(english);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static synchronized void addFrame(Entity frame)
/* 23:   */   {
/* 24:45 */     if (frame.getElements().size() > 0) {
/* 25:46 */       frames.add((Entity)frame.getElements().get(0));
/* 26:   */     } else {
/* 27:49 */       frames.add(frame);
/* 28:   */     }
/* 29:51 */     if (frames.size() > sentences.size()) {
/* 30:52 */       System.err.println("[InputTracker] See sam: WRONG NUMBER OF FRAMES!!!!!!!!!!!!!!!!");
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static synchronized Entity getFrame(String english)
/* 35:   */   {
/* 36:57 */     int index = sentences.indexOf(english);
/* 37:58 */     if ((index == -1) || (frames.size() <= index)) {
/* 38:59 */       return null;
/* 39:   */     }
/* 40:61 */     return (Entity)frames.get(index);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static synchronized String getSentence(Entity frame)
/* 44:   */   {
/* 45:68 */     int index = frames.indexOf(frame);
/* 46:69 */     if ((index == -1) || (sentences.size() <= index)) {
/* 47:70 */       return null;
/* 48:   */     }
/* 49:72 */     return (String)sentences.get(index);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static synchronized void addTopLevelFrame(Entity frame) {}
/* 53:   */   
/* 54:   */   public static synchronized boolean containsTopLevelFrame(Entity frame)
/* 55:   */   {
/* 56:87 */     return false;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.InputTracker
 * JD-Core Version:    0.7.0.1
 */