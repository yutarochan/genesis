/*  1:   */ package susanSong;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class PersonalityConstructor
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static final String STORY_ELEMENT_INPUT_PORT = "story element input port";
/* 13:   */   public static final String CONCEPT_PATTERN_INPUT_PORT = "concept pattern input port";
/* 14:   */   public static final String CONCEPT_PATTERN_OUTPUT_PORT = "concept pattern output port";
/* 15:   */   
/* 16:   */   public PersonalityConstructor()
/* 17:   */   {
/* 18:18 */     Connections.getPorts(this).addSignalProcessor("story element input port", "processStoryElements");
/* 19:19 */     Connections.getPorts(this).addSignalProcessor("concept pattern input port", "processConceptPatterns");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void processConceptPatterns(Object signal)
/* 23:   */   {
/* 24:23 */     if ((signal instanceof Entity))
/* 25:   */     {
/* 26:24 */       Entity t = (Entity)signal;
/* 27:25 */       if (t.sequenceP())
/* 28:   */       {
/* 29:26 */         Sequence s = (Sequence)t;
/* 30:27 */         for (Entity concept : s.getElements()) {
/* 31:28 */           Connections.getPorts(this).transmit("concept pattern output port", concept);
/* 32:   */         }
/* 33:   */       }
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void processStoryElements(Object signal) {}
/* 38:   */   
/* 39:   */   public static void main(String[] ignore)
/* 40:   */     throws Exception
/* 41:   */   {}
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     susanSong.PersonalityConstructor
 * JD-Core Version:    0.7.0.1
 */