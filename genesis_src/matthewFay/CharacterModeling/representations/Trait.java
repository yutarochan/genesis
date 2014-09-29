/*   1:    */ package matthewFay.CharacterModeling.representations;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.swing.JCheckBox;
/*   8:    */ import matthewFay.StoryAlignment.Aligner;
/*   9:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  10:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  11:    */ import matthewFay.Utilities.EntityHelper;
/*  12:    */ import matthewFay.Utilities.Pair;
/*  13:    */ import matthewFay.representations.BasicCharacterModel;
/*  14:    */ import matthewFay.viewers.TraitViewer;
/*  15:    */ import utils.PairOfEntities;
/*  16:    */ 
/*  17:    */ public class Trait
/*  18:    */ {
/*  19:    */   private List<BasicCharacterModel> positive_examples;
/*  20:    */   private List<BasicCharacterModel> negative_examples;
/*  21:    */   private String name;
/*  22:    */   private Entity prime_character_entity;
/*  23:    */   private List<Entity> trait_elements;
/*  24:    */   
/*  25:    */   public static boolean isTraitAssignment(Entity element)
/*  26:    */   {
/*  27: 17 */     if (element.relationP("personality_trait")) {
/*  28: 18 */       return true;
/*  29:    */     }
/*  30: 20 */     return false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getName()
/*  34:    */   {
/*  35: 28 */     return this.name;
/*  36:    */   }
/*  37:    */   
/*  38: 33 */   private boolean dirty = true;
/*  39:    */   
/*  40:    */   public void markDirty()
/*  41:    */   {
/*  42: 35 */     this.dirty = true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Trait(String name)
/*  46:    */   {
/*  47: 39 */     this.name = name;
/*  48:    */     
/*  49: 41 */     this.positive_examples = new ArrayList();
/*  50: 42 */     this.negative_examples = new ArrayList();
/*  51: 43 */     this.trait_elements = new ArrayList();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addPositiveExample(BasicCharacterModel character)
/*  55:    */   {
/*  56: 47 */     this.positive_examples.add(character);
/*  57: 48 */     this.dirty = true;
/*  58: 49 */     if (this.positive_examples.size() == 1)
/*  59:    */     {
/*  60: 50 */       BasicCharacterModel prime_character = (BasicCharacterModel)this.positive_examples.get(0);
/*  61: 51 */       this.prime_character_entity = prime_character.getEntity();
/*  62: 52 */       this.trait_elements.addAll(prime_character.getParticipantEvents());
/*  63: 53 */       return;
/*  64:    */     }
/*  65: 56 */     List<Entity> plot2 = character.getParticipantEvents();
/*  66: 57 */     List<PairOfEntities> bindings = new ArrayList();
/*  67: 58 */     bindings.add(new PairOfEntities(this.prime_character_entity, character.getEntity()));
/*  68:    */     
/*  69: 60 */     SequenceAlignment sa = (SequenceAlignment)Aligner.getAligner().align(this.trait_elements, plot2, bindings).get(0);
/*  70: 61 */     for (Pair<Entity, Entity> pair : sa) {
/*  71: 62 */       if ((pair.a != null) && (pair.b != null)) {
/*  72: 63 */         this.trait_elements.add((Entity)pair.a);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void addNegativeExample(BasicCharacterModel character)
/*  78:    */   {
/*  79: 69 */     this.negative_examples.add(character);
/*  80: 70 */     this.dirty = true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void infer_trait_elements()
/*  84:    */   {
/*  85: 74 */     if (this.positive_examples.size() > 0)
/*  86:    */     {
/*  87: 76 */       for (BasicCharacterModel character : this.positive_examples) {
/*  88: 77 */         if (character.getParticipantEvents().size() > this.trait_elements.size())
/*  89:    */         {
/*  90: 78 */           this.trait_elements.clear();
/*  91: 79 */           BasicCharacterModel prime_character = (BasicCharacterModel)this.positive_examples.get(0);
/*  92: 80 */           this.prime_character_entity = prime_character.getEntity();
/*  93: 81 */           this.trait_elements.addAll(prime_character.getParticipantEvents());
/*  94:    */         }
/*  95:    */       }
/*  96:    */       Iterator localIterator2;
/*  97: 85 */       for (??? = this.negative_examples.iterator(); ???.hasNext(); localIterator2.hasNext())
/*  98:    */       {
/*  99: 85 */         BasicCharacterModel negative_example = (BasicCharacterModel)???.next();
/* 100: 86 */         List<Entity> neg_plot = negative_example.getParticipantEvents();
/* 101:    */         
/* 102: 88 */         List<PairOfEntities> bindings = new ArrayList();
/* 103: 89 */         bindings.add(new PairOfEntities(this.prime_character_entity, negative_example.getEntity()));
/* 104:    */         
/* 105: 91 */         SequenceAlignment sa = (SequenceAlignment)Aligner.getAligner().align(this.trait_elements, neg_plot, bindings).get(0);
/* 106:    */         
/* 107: 93 */         this.trait_elements.clear();
/* 108: 94 */         localIterator2 = sa.iterator(); continue;Pair<Entity, Entity> pair = (Pair)localIterator2.next();
/* 109: 95 */         if ((pair.a != null) && (pair.b == null)) {
/* 110: 96 */           this.trait_elements.add((Entity)pair.a);
/* 111:    */         }
/* 112:    */       }
/* 113:101 */       for (??? = this.positive_examples.iterator(); ???.hasNext(); localIterator2.hasNext())
/* 114:    */       {
/* 115:101 */         BasicCharacterModel positive_example = (BasicCharacterModel)???.next();
/* 116:102 */         List<Entity> pos_plot = positive_example.getParticipantEvents();
/* 117:    */         
/* 118:104 */         List<PairOfEntities> bindings = new ArrayList();
/* 119:105 */         bindings.add(new PairOfEntities(this.prime_character_entity, positive_example.getEntity()));
/* 120:    */         
/* 121:107 */         SequenceAlignment sa = (SequenceAlignment)Aligner.getAligner().align(this.trait_elements, pos_plot, bindings).get(0);
/* 122:    */         
/* 123:109 */         this.trait_elements.clear();
/* 124:110 */         localIterator2 = sa.iterator(); continue;Pair<Entity, Entity> pair = (Pair)localIterator2.next();
/* 125:111 */         if ((pair.a != null) && (pair.b != null)) {
/* 126:112 */           this.trait_elements.add((Entity)pair.a);
/* 127:    */         }
/* 128:    */       }
/* 129:118 */       if (TraitViewer.getTraitViewer().generalize_trait_description.isSelected()) {
/* 130:119 */         this.trait_elements = EntityHelper.generalizeListOfEntities(this.trait_elements, this.prime_character_entity);
/* 131:    */       }
/* 132:122 */       this.dirty = false;
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List<Entity> getElements()
/* 137:    */   {
/* 138:127 */     if (this.dirty) {
/* 139:128 */       infer_trait_elements();
/* 140:    */     }
/* 141:129 */     if (this.dirty) {
/* 142:130 */       return new ArrayList();
/* 143:    */     }
/* 144:131 */     return this.trait_elements;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.CharacterModeling.representations.Trait
 * JD-Core Version:    0.7.0.1
 */