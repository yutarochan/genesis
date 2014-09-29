/*  1:   */ package matthewFay.StoryAlignment;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Comparator;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import matthewFay.Utilities.Pair;
/*  8:   */ 
/*  9:   */ public class RankedSequenceAlignmentSet<Pattern, Datum>
/* 10:   */   extends ArrayList<SequenceAlignment>
/* 11:   */ {
/* 12:   */   public int getMaxLength()
/* 13:   */   {
/* 14:18 */     int maxLength = 0;
/* 15:19 */     for (SequenceAlignment alignment : this) {
/* 16:20 */       maxLength = alignment.size() > maxLength ? alignment.size() : maxLength;
/* 17:   */     }
/* 18:22 */     return maxLength;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void removeGlobalAlignment()
/* 22:   */   {
/* 23:   */     SequenceAlignment alignment;
/* 24:   */     int elementIterator;
/* 25:29 */     for (Iterator localIterator = iterator(); localIterator.hasNext(); elementIterator < alignment.size())
/* 26:   */     {
/* 27:29 */       alignment = (SequenceAlignment)localIterator.next();
/* 28:30 */       elementIterator = 0;
/* 29:31 */       continue;
/* 30:32 */       if ((((Pair)alignment.get(elementIterator)).a == null) && (((Pair)alignment.get(elementIterator)).a == null)) {
/* 31:33 */         alignment.remove(elementIterator);
/* 32:   */       } else {
/* 33:35 */         elementIterator++;
/* 34:   */       }
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void globalAlignment()
/* 39:   */   {
/* 40:50 */     int maxDatumDistance = 0;
/* 41:   */     
/* 42:52 */     SequenceAlignment currentAlignment = null;
/* 43:   */     
/* 44:54 */     int[] patternIterators = new int[size()];
/* 45:   */     
/* 46:56 */     int alignmentIterator = 0;
/* 47:   */     
/* 48:58 */     boolean finished = false;
/* 49:59 */     for (; !finished; alignmentIterator < size())
/* 50:   */     {
/* 51:61 */       for (alignmentIterator = 0; alignmentIterator < size(); alignmentIterator++)
/* 52:   */       {
/* 53:62 */         currentAlignment = (SequenceAlignment)get(alignmentIterator);
/* 54:64 */         while ((patternIterators[alignmentIterator] < currentAlignment.size()) && 
/* 55:65 */           (((Pair)currentAlignment.get(patternIterators[alignmentIterator])).b == null)) {
/* 56:66 */           patternIterators[alignmentIterator] += 1;
/* 57:   */         }
/* 58:68 */         if (patternIterators[alignmentIterator] > maxDatumDistance) {
/* 59:69 */           maxDatumDistance = patternIterators[alignmentIterator];
/* 60:   */         }
/* 61:   */       }
/* 62:74 */       for (alignmentIterator = 0; alignmentIterator < size(); alignmentIterator++)
/* 63:   */       {
/* 64:75 */         currentAlignment = (SequenceAlignment)get(alignmentIterator);
/* 65:76 */         while (patternIterators[alignmentIterator] < maxDatumDistance)
/* 66:   */         {
/* 67:77 */           currentAlignment.add(patternIterators[alignmentIterator], new Pair(null, null));
/* 68:78 */           patternIterators[alignmentIterator] += 1;
/* 69:   */         }
/* 70:   */       }
/* 71:83 */       finished = true;
/* 72:84 */       alignmentIterator = 0; continue;
/* 73:85 */       if (patternIterators[alignmentIterator] < ((SequenceAlignment)get(alignmentIterator)).size())
/* 74:   */       {
/* 75:86 */         finished = false;
/* 76:87 */         patternIterators[alignmentIterator] += 1;
/* 77:   */       }
/* 78:84 */       alignmentIterator++;
/* 79:   */     }
/* 80:   */   }
/* 81:   */   
/* 82:   */   public void sort()
/* 83:   */   {
/* 84:97 */     Collections.sort(this, new Comparator()
/* 85:   */     {
/* 86:   */       public int compare(SequenceAlignment o1, SequenceAlignment o2)
/* 87:   */       {
/* 88::1 */         if (o1.score < o2.score) {
/* 89::2 */           return 1;
/* 90:   */         }
/* 91::3 */         if (o1.score > o2.score) {
/* 92::4 */           return -1;
/* 93:   */         }
/* 94::5 */         return 0;
/* 95:   */       }
/* 96:   */     });
/* 97:   */   }
/* 98:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.RankedSequenceAlignmentSet
 * JD-Core Version:    0.7.0.1
 */