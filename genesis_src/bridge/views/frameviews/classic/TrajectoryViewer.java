/*  1:   */ package bridge.views.frameviews.classic;
/*  2:   */ 
/*  3:   */ import bridge.adapters.EntityToViewerTranslator;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import java.awt.Color;
/*  7:   */ import java.util.Vector;
/*  8:   */ 
/*  9:   */ public class TrajectoryViewer
/* 10:   */   extends FrameViewer
/* 11:   */ {
/* 12:   */   public TrajectoryViewer() {}
/* 13:   */   
/* 14:   */   public TrajectoryViewer(String t)
/* 15:   */   {
/* 16:36 */     super(t);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TrajectoryViewer(String t, int scrollable)
/* 20:   */   {
/* 21:40 */     super(t, scrollable);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public TrajectoryViewer(String t, Color color)
/* 25:   */   {
/* 26:48 */     super(t, color);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setInput(Entity thing)
/* 30:   */   {
/* 31:55 */     if ((thing.sequenceP()) && (thing.isA("eventSpace")))
/* 32:   */     {
/* 33:56 */       Vector ladders = ((Sequence)thing).getElements();
/* 34:57 */       Vector translations = new Vector();
/* 35:58 */       for (int i = 0; i < ladders.size(); i++)
/* 36:   */       {
/* 37:59 */         Entity element = (Entity)ladders.elementAt(i);
/* 38:60 */         translations.add(EntityToViewerTranslator.translate(element));
/* 39:   */         
/* 40:62 */         super.setInputVector(translations);
/* 41:   */       }
/* 42:64 */       if (translations.isEmpty()) {
/* 43:65 */         super.setInputVector(translations);
/* 44:   */       }
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.views.frameviews.classic.TrajectoryViewer
 * JD-Core Version:    0.7.0.1
 */