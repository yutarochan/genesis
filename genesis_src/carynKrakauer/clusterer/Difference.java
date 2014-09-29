/*  1:   */ package carynKrakauer.clusterer;
/*  2:   */ 
/*  3:   */ import carynKrakauer.aligner.ReflectionAlignmentUtil;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import matthewFay.StoryAlignment.Alignment;
/*  6:   */ import matthewFay.Utilities.Pair;
/*  7:   */ import storyProcessor.ReflectionDescription;
/*  8:   */ 
/*  9:   */ public class Difference
/* 10:   */ {
/* 11:   */   float value;
/* 12:   */   ArrayList<ReflectionDescription> added;
/* 13:   */   ArrayList<ReflectionDescription> removed;
/* 14:   */   Story from;
/* 15:   */   Story to;
/* 16:   */   Alignment<ReflectionDescription, ReflectionDescription> alignment;
/* 17:   */   
/* 18:   */   public Difference(Story from, Story to)
/* 19:   */   {
/* 20:23 */     this.from = from;
/* 21:24 */     this.to = to;
/* 22:   */     
/* 23:26 */     this.alignment = ReflectionAlignmentUtil.AlignReflections(this.from.refDesc, this.to.refDesc);
/* 24:   */     
/* 25:28 */     this.added = new ArrayList();
/* 26:29 */     this.removed = new ArrayList();
/* 27:   */     
/* 28:31 */     float total = 0.0F;
/* 29:32 */     float difference = 0.0F;
/* 30:34 */     for (Pair<ReflectionDescription, ReflectionDescription> pair : this.alignment)
/* 31:   */     {
/* 32:35 */       if (pair.a == null)
/* 33:   */       {
/* 34:36 */         this.added.add((ReflectionDescription)pair.a);
/* 35:37 */         difference += 1.0F;
/* 36:   */       }
/* 37:39 */       else if (pair.b == null)
/* 38:   */       {
/* 39:40 */         this.removed.add((ReflectionDescription)pair.b);
/* 40:41 */         difference += 1.0F;
/* 41:   */       }
/* 42:43 */       total += 1.0F;
/* 43:   */     }
/* 44:46 */     this.value = (difference / total);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public float getValue()
/* 48:   */   {
/* 49:50 */     return this.value;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.clusterer.Difference
 * JD-Core Version:    0.7.0.1
 */