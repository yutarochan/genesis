/*  1:   */ package carynKrakauer;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import javax.swing.JComboBox;
/* 10:   */ import storyProcessor.ReflectionAnalysis;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class SimilarityProcessor
/* 14:   */   extends AbstractWiredBox
/* 15:   */ {
/* 16:   */   ReflectionLevelMemory memory;
/* 17:   */   private ArrayList<JComboBox> m_combo_boxes;
/* 18:   */   private SimilarityViewer similarityViewer;
/* 19:   */   
/* 20:   */   public SimilarityProcessor(SimilarityViewer similarityViewer)
/* 21:   */   {
/* 22:28 */     this.similarityViewer = similarityViewer;
/* 23:29 */     setName("My story processor");
/* 24:30 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 25:   */     
/* 26:   */ 
/* 27:33 */     this.memory = new ReflectionLevelMemory(this);
/* 28:   */     
/* 29:35 */     this.m_combo_boxes = new ArrayList();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public SimilarityProcessor()
/* 33:   */   {
/* 34:39 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void addComboBox(JComboBox box)
/* 38:   */   {
/* 39:43 */     this.m_combo_boxes.add(box);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void removeComboBox(JComboBox box)
/* 43:   */   {
/* 44:47 */     this.m_combo_boxes.remove(box);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void processSignal(Object signal)
/* 48:   */   {
/* 49:52 */     if ((signal instanceof ReflectionAnalysis))
/* 50:   */     {
/* 51:53 */       Mark.say(new Object[] {"GOT REFLECTION ANALYSIS" });
/* 52:54 */       ReflectionAnalysis analysis = (ReflectionAnalysis)signal;
/* 53:   */       
/* 54:56 */       String title = analysis.getStoryName();
/* 55:57 */       Mark.say(new Object[] {"THE TITLE I FOUND WAS: " + title });
/* 56:58 */       if (this.memory.hasReadStory(title)) {
/* 57:59 */         return;
/* 58:   */       }
/* 59:61 */       this.memory.readInStory(analysis);
/* 60:63 */       for (JComboBox box : this.m_combo_boxes) {
/* 61:64 */         box.addItem(title);
/* 62:   */       }
/* 63:   */     }
/* 64:67 */     else if ((signal instanceof Entity))
/* 65:   */     {
/* 66:68 */       Entity t = (Entity)signal;
/* 67:69 */       Mark.say(new Object[] {"got: " + t });
/* 68:70 */       String title = null;
/* 69:71 */       if (t.sequenceP())
/* 70:   */       {
/* 71:72 */         Sequence s = (Sequence)t;
/* 72:   */         
/* 73:74 */         Mark.say(new Object[] {"Story received:" });
/* 74:   */         
/* 75:76 */         title = s.getType();
/* 76:   */         
/* 77:78 */         title = Character.toUpperCase(title.charAt(0)) + title.substring(1);
/* 78:79 */         title = title.replace('_', ' ');
/* 79:80 */         Mark.say(new Object[] {title });
/* 80:81 */         this.memory.putStorySequence(title, s);
/* 81:82 */         Mark.say(new Object[] {"put story sequence." });
/* 82:   */       }
/* 83:   */     }
/* 84:   */   }
/* 85:   */   
/* 86:   */   public ReflectionLevelMemory getMemory()
/* 87:   */   {
/* 88:90 */     return this.memory;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public void updateComparisons()
/* 92:   */   {
/* 93:94 */     this.similarityViewer.updateComparisons();
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.SimilarityProcessor
 * JD-Core Version:    0.7.0.1
 */