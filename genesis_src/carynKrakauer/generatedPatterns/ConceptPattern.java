/*   1:    */ package carynKrakauer.generatedPatterns;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import bridge.reps.entities.Thread;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import matthewFay.StoryAlignment.Aligner;
/*  11:    */ import matthewFay.StoryAlignment.Alignment;
/*  12:    */ import matthewFay.StoryAlignment.NWSequenceAlignmentScorer;
/*  13:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  14:    */ import matthewFay.Utilities.Pair;
/*  15:    */ 
/*  16:    */ public class ConceptPattern
/*  17:    */ {
/*  18:    */   private ArrayList<PlotEvent> events;
/*  19:    */   private ArrayList<Entity> things;
/*  20:    */   private String asString;
/*  21:    */   private HashMap<PlotEvent, PlotEvent> relations;
/*  22:    */   private HashMap<Entity, Entity> relationsThings;
/*  23:    */   
/*  24:    */   public ConceptPattern(ArrayList<PlotEvent> events)
/*  25:    */   {
/*  26: 28 */     this.events = events;
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31: 33 */     this.things = new ArrayList();
/*  32: 34 */     for (PlotEvent event : events) {
/*  33: 35 */       this.things.add(event.getThing());
/*  34:    */     }
/*  35: 41 */     String[] strings = new String[events.size()];
/*  36: 42 */     int i = 0;
/*  37: 43 */     for (PlotEvent event : events)
/*  38:    */     {
/*  39: 44 */       strings[i] = event.getString();
/*  40: 45 */       i++;
/*  41:    */     }
/*  42: 48 */     this.asString = "";
/*  43: 49 */     for (String string : strings) {
/*  44: 50 */       this.asString = (this.asString + string + " ");
/*  45:    */     }
/*  46: 56 */     this.relations = new HashMap();
/*  47: 57 */     this.relationsThings = new HashMap();
/*  48: 58 */     for (i = 0; i < events.size(); i++)
/*  49:    */     {
/*  50: 59 */       PlotEvent e1 = (PlotEvent)events.get(i);
/*  51: 60 */       Entity t = e1.getThing();
/*  52: 61 */       for (int j = i + 1; j < events.size(); j++)
/*  53:    */       {
/*  54: 62 */         PlotEvent e2 = (PlotEvent)events.get(j);
/*  55:    */         
/*  56: 64 */         boolean foundRelation = false;
/*  57: 65 */         Entity t2 = e2.getThing();
/*  58: 66 */         for (Entity parent : t.getAncestors()) {
/*  59: 67 */           if ((parent.getClass().equals(Sequence.class)) && 
/*  60: 68 */             (parent.getElement(0).getType().equals(t2.getType())))
/*  61:    */           {
/*  62: 69 */             this.relations.put(e2, e1);
/*  63: 70 */             this.relationsThings.put(t2, t);
/*  64:    */           }
/*  65:    */         }
/*  66: 74 */         if (!foundRelation) {
/*  67: 75 */           for (Entity parent : t2.getAncestors()) {
/*  68: 76 */             if ((parent.getClass().equals(Sequence.class)) && 
/*  69: 77 */               (parent.getElement(0).getType().equals(t.getType())))
/*  70:    */             {
/*  71: 78 */               this.relations.put(e1, e2);
/*  72: 79 */               this.relationsThings.put(t, t2);
/*  73:    */             }
/*  74:    */           }
/*  75:    */         }
/*  76:    */       }
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean canAlign(ConceptPattern other, int size)
/*  81:    */   {
/*  82:104 */     if (this == other) {
/*  83:105 */       return true;
/*  84:    */     }
/*  85:119 */     Aligner betterAligner = Aligner.getAligner();
/*  86:120 */     Alignment<Entity, Entity> alignment = (Alignment)betterAligner.align(this.things, other.asThings()).get(0);
/*  87:123 */     if (alignment.size() != size) {
/*  88:124 */       return false;
/*  89:    */     }
/*  90:127 */     for (Pair<Entity, Entity> pair : alignment) {
/*  91:128 */       if (this.relationsThings.containsKey(pair.a)) {
/*  92:129 */         if (other.relationsThings.containsKey(pair.b))
/*  93:    */         {
/*  94:131 */           Entity leadsTo1 = (Entity)this.relationsThings.get(pair.a);
/*  95:132 */           Entity leadsTo2 = (Entity)other.relationsThings.get(pair.b);
/*  96:    */           
/*  97:134 */           boolean matchFound = false;
/*  98:135 */           for (Pair<Entity, Entity> pair2 : alignment) {
/*  99:136 */             if (pair != pair2) {
/* 100:139 */               if (pair2.a == leadsTo1)
/* 101:    */               {
/* 102:140 */                 if (((Entity)pair2.b).getType().equals(leadsTo2.getType()))
/* 103:    */                 {
/* 104:141 */                   matchFound = true;
/* 105:142 */                   break;
/* 106:    */                 }
/* 107:145 */                 Thread threadA = (Thread)((Entity)pair2.b).getBundle().firstElement();
/* 108:146 */                 Thread threadB = (Thread)leadsTo2.getBundle().firstElement();
/* 109:149 */                 if ((threadA.size() > 2) && (threadB.size() > 2))
/* 110:    */                 {
/* 111:150 */                   if ((((String)threadA.get(threadA.size() - 2)).equals(threadB.get(threadB.size() - 2))) || 
/* 112:151 */                     (((String)threadA.lastElement()).equals(threadB.get(threadB.size() - 2))) || 
/* 113:152 */                     (((String)threadA.get(threadA.size() - 2)).equals(threadB.lastElement())))
/* 114:    */                   {
/* 115:154 */                     matchFound = true;
/* 116:155 */                     break;
/* 117:    */                   }
/* 118:    */                 }
/* 119:    */                 else {
/* 120:159 */                   return false;
/* 121:    */                 }
/* 122:    */               }
/* 123:    */             }
/* 124:    */           }
/* 125:165 */           if (!matchFound) {
/* 126:166 */             return false;
/* 127:    */           }
/* 128:    */         }
/* 129:    */         else
/* 130:    */         {
/* 131:171 */           return false;
/* 132:    */         }
/* 133:    */       }
/* 134:    */     }
/* 135:176 */     return true;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean canAlignOld(ConceptPattern other, int size)
/* 139:    */   {
/* 140:180 */     NWSequenceAlignmentScorer aligner = new NWSequenceAlignmentScorer(this.things, other.asThings());
/* 141:181 */     Alignment<Entity, Entity> alignment = aligner.align(this.things, other.asThings());
/* 142:182 */     if (alignment.size() == size) {
/* 143:183 */       return true;
/* 144:    */     }
/* 145:186 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String asString()
/* 149:    */   {
/* 150:195 */     return this.asString;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public ArrayList<PlotEvent> getEvents()
/* 154:    */   {
/* 155:199 */     return this.events;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<Entity> asThings()
/* 159:    */   {
/* 160:203 */     return this.things;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getSize()
/* 164:    */   {
/* 165:207 */     return this.events.size();
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.generatedPatterns.ConceptPattern
 * JD-Core Version:    0.7.0.1
 */