/*  1:   */ package matthewFay.VideoAnalysis;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import matthewFay.Demo;
/*  8:   */ import matthewFay.StoryAlignment.Aligner;
/*  9:   */ import matthewFay.StoryAlignment.Alignment;
/* 10:   */ import matthewFay.StoryAlignment.SequenceAlignment;
/* 11:   */ import matthewFay.StoryAlignment.SortableAlignmentList;
/* 12:   */ import matthewFay.Utilities.EntityHelper;
/* 13:   */ import matthewFay.Utilities.Pair;
/* 14:   */ import minilisp.LList;
/* 15:   */ import utils.Mark;
/* 16:   */ import utils.PairOfEntities;
/* 17:   */ 
/* 18:   */ public class EventPredictor
/* 19:   */ {
/* 20:   */   private Aligner aligner;
/* 21:   */   
/* 22:   */   public EventPredictor()
/* 23:   */   {
/* 24:32 */     this.aligner = new Aligner();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public HashMap<Entity, Float> predictNextEvent(Sequence target, ArrayList<Sequence> patterns)
/* 28:   */   {
/* 29:36 */     HashMap<Entity, Float> predictedEvents = new HashMap();
/* 30:37 */     SortableAlignmentList alignments = this.aligner.alignToPatterns(target, patterns);
/* 31:38 */     for (Alignment<Entity, Entity> alignment : alignments)
/* 32:   */     {
/* 33:39 */       int end = alignment.size() - 1;
/* 34:40 */       int predictionTime = -1;
/* 35:41 */       while (end > 0) {
/* 36:42 */         if ((((Pair)alignment.get(end)).a == null) && (((Pair)alignment.get(end)).b == null))
/* 37:   */         {
/* 38:43 */           end--;
/* 39:   */         }
/* 40:   */         else
/* 41:   */         {
/* 42:45 */           if (((Pair)alignment.get(end)).a != null) {
/* 43:   */             break;
/* 44:   */           }
/* 45:46 */           predictionTime = end;
/* 46:47 */           end--;
/* 47:   */         }
/* 48:   */       }
/* 49:53 */       if (predictionTime >= 0)
/* 50:   */       {
/* 51:54 */         LList<PairOfEntities> bindings = ((SequenceAlignment)alignment).bindings;
/* 52:55 */         Entity basis = ((Entity)((Pair)alignment.get(predictionTime)).b).deepClone(false);
/* 53:   */         
/* 54:57 */         basis = EntityHelper.findAndReplace(basis, bindings);
/* 55:58 */         predictedEvents.put(basis, Float.valueOf(alignment.score));
/* 56:   */       }
/* 57:   */     }
/* 58:61 */     return predictedEvents;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public Entity predictMostLikelyNextEvent(Sequence target, ArrayList<Sequence> patterns)
/* 62:   */   {
/* 63:65 */     HashMap<Entity, Float> predictedEvents = predictNextEvent(target, patterns);
/* 64:66 */     Entity event = null;
/* 65:67 */     float score = (1.0F / -1.0F);
/* 66:68 */     for (Entity t : predictedEvents.keySet()) {
/* 67:69 */       if (((Float)predictedEvents.get(t)).floatValue() > score)
/* 68:   */       {
/* 69:70 */         event = t;
/* 70:71 */         score = ((Float)predictedEvents.get(t)).floatValue();
/* 71:   */       }
/* 72:   */     }
/* 73:74 */     return event;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static void main(String[] args)
/* 77:   */   {
/* 78:78 */     Sequence seqA = Demo.GiveStartStory();
/* 79:79 */     Sequence seqB = Demo.GiveStory();
/* 80:   */     
/* 81:81 */     Aligner aligner = new Aligner();
/* 82:82 */     SortableAlignmentList alignments = aligner.align(seqA, seqB);
/* 83:83 */     Mark.say(new Object[] {alignments.get(0) });
/* 84:84 */     Mark.say(new Object[] {((SequenceAlignment)alignments.get(0)).bindings });
/* 85:   */     
/* 86:86 */     ArrayList<Sequence> patterns = new ArrayList();
/* 87:87 */     patterns.add(seqB);
/* 88:   */     
/* 89:89 */     EventPredictor predictor = new EventPredictor();
/* 90:90 */     for (Entity event : predictor.predictNextEvent(seqA, patterns).keySet()) {
/* 91:91 */       Mark.say(new Object[] {"Prediction: " + event.asString() });
/* 92:   */     }
/* 93:   */   }
/* 94:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.VideoAnalysis.EventPredictor
 * JD-Core Version:    0.7.0.1
 */