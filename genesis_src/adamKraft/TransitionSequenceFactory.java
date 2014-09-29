/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ 
/*  8:   */ public class TransitionSequenceFactory
/*  9:   */ {
/* 10:   */   public static final int MAX_FRAME_SPAN = 60;
/* 11:   */   protected static TransitionSequenceFactory instance;
/* 12:   */   
/* 13:   */   public static TransitionSequenceFactory getInstance()
/* 14:   */   {
/* 15:14 */     if (instance == null) {
/* 16:15 */       instance = new TransitionSequenceFactory();
/* 17:   */     }
/* 18:17 */     return instance;
/* 19:   */   }
/* 20:   */   
/* 21:20 */   private List<Transition> history = new ArrayList();
/* 22:21 */   private Map<TransitionSequence, Integer> frequencyMap = new HashMap();
/* 23:   */   
/* 24:   */   public void processTransition(String t)
/* 25:   */   {
/* 26:24 */     processTransition(Transition.parse(t));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void processTransition(Transition t)
/* 30:   */   {
/* 31:28 */     this.history.add(t);
/* 32:29 */     while ((this.history.size() > 0) && (((Transition)this.history.get(0)).frameNumber < t.frameNumber - 60)) {
/* 33:30 */       this.history.remove(0);
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.TransitionSequenceFactory
 * JD-Core Version:    0.7.0.1
 */