/*  1:   */ package matthewFay.representations;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Set;
/*  8:   */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  9:   */ import matthewFay.Utilities.EntityHelper;
/* 10:   */ import matthewFay.Utilities.HashMatrix;
/* 11:   */ 
/* 12:   */ public class RelationGraph
/* 13:   */ {
/* 14:   */   private HashMatrix<BasicCharacterModel, BasicCharacterModel, List<Entity>> adjacency_matrix;
/* 15:   */   
/* 16:   */   public RelationGraph()
/* 17:   */   {
/* 18:28 */     this.adjacency_matrix = new HashMatrix();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addEvent(Entity event)
/* 22:   */   {
/* 23:32 */     Set<BasicCharacterModel> character_set = new HashSet();
/* 24:33 */     List<Entity> entities = EntityHelper.getAllEntities(event);
/* 25:34 */     for (Entity entity : entities) {
/* 26:35 */       if (CharacterProcessor.isCharacter(entity)) {
/* 27:36 */         character_set.add(CharacterProcessor.getCharacterModel(entity, true));
/* 28:   */       }
/* 29:   */     }
/* 30:39 */     if (character_set.size() == 2)
/* 31:   */     {
/* 32:40 */       List<BasicCharacterModel> character_list = new ArrayList(character_set);
/* 33:41 */       BasicCharacterModel c1 = (BasicCharacterModel)character_list.get(0);
/* 34:42 */       BasicCharacterModel c2 = (BasicCharacterModel)character_list.get(1);
/* 35:   */       List<Entity> elts;
/* 36:44 */       if (!this.adjacency_matrix.contains(c1, c2))
/* 37:   */       {
/* 38:45 */         List<Entity> elts = new ArrayList();
/* 39:46 */         this.adjacency_matrix.put(c1, c2, elts);
/* 40:47 */         this.adjacency_matrix.put(c2, c1, elts);
/* 41:   */       }
/* 42:   */       else
/* 43:   */       {
/* 44:49 */         elts = (List)this.adjacency_matrix.get(c1, c2);
/* 45:   */       }
/* 46:51 */       elts.add(event);
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.representations.RelationGraph
 * JD-Core Version:    0.7.0.1
 */