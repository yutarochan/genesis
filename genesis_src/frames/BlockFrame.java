/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Thread;
/*   7:    */ import bridge.utils.StringUtils;
/*   8:    */ import constants.RecognizedRepresentations;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ 
/*  11:    */ public class BlockFrame
/*  12:    */   extends Frame
/*  13:    */ {
/*  14: 14 */   public static String[] blockTypes = { "obstructs", "contains" };
/*  15: 15 */   public static String[] magTypes = { "partial", "complete" };
/*  16: 16 */   public static String FRAMETYPE = (String)RecognizedRepresentations.BLOCK_THING;
/*  17:    */   private Relation blockRelation;
/*  18:    */   
/*  19:    */   public static Relation makeBlockRelation(Entity blocker, Entity blockedThing, String blockType, String mag)
/*  20:    */   {
/*  21: 27 */     if (!StringUtils.testType(blockType, blockTypes))
/*  22:    */     {
/*  23: 28 */       System.err.println("Sorry, " + blockType + " is not a valid block relation.");
/*  24: 29 */       return null;
/*  25:    */     }
/*  26: 31 */     if (!StringUtils.testType(mag, magTypes))
/*  27:    */     {
/*  28: 32 */       System.err.println("Sorry, " + mag + " is not a valid blockage magnitude.");
/*  29: 33 */       return null;
/*  30:    */     }
/*  31: 35 */     Relation result = new Relation(FRAMETYPE, blocker, blockedThing);
/*  32: 36 */     result.addType(blockType);
/*  33: 37 */     result.addFeature(mag);
/*  34: 38 */     return result;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static void setBlockedThing(Relation blockRelation, Entity blockedThing)
/*  38:    */   {
/*  39: 41 */     if (blockRelation.isA(FRAMETYPE))
/*  40:    */     {
/*  41: 42 */       blockRelation.setObject(blockedThing);
/*  42: 43 */       return;
/*  43:    */     }
/*  44: 45 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static Entity getBlockedThing(Relation blockRelation)
/*  48:    */   {
/*  49: 49 */     if (blockRelation.isA(FRAMETYPE)) {
/*  50: 50 */       return blockRelation.getObject();
/*  51:    */     }
/*  52: 52 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  53: 53 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void setBlocker(Relation blockRelation, Entity blocker)
/*  57:    */   {
/*  58: 56 */     if (blockRelation.isA(FRAMETYPE))
/*  59:    */     {
/*  60: 57 */       blockRelation.setSubject(blocker);
/*  61: 58 */       return;
/*  62:    */     }
/*  63: 60 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static Entity getBlocker(Relation blockRelation)
/*  67:    */   {
/*  68: 64 */     if (blockRelation.isA(FRAMETYPE)) {
/*  69: 65 */       return blockRelation.getSubject();
/*  70:    */     }
/*  71: 67 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  72: 68 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static String getBlockType(Relation blockRelation)
/*  76:    */   {
/*  77: 71 */     if (blockRelation.isA(FRAMETYPE)) {
/*  78: 72 */       return blockRelation.getType();
/*  79:    */     }
/*  80: 74 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  81: 75 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static String getMag(Relation blockRelation)
/*  85:    */   {
/*  86: 78 */     if (blockRelation.isA(FRAMETYPE)) {
/*  87: 80 */       return blockRelation.getBundle().getThreadContaining("features").getType();
/*  88:    */     }
/*  89: 82 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  90: 83 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static String getFullBlockType(Relation blockRelation)
/*  94:    */   {
/*  95: 92 */     if (blockRelation.isA(FRAMETYPE)) {
/*  96: 93 */       return getMag(blockRelation) + "--" + getBlockType(blockRelation);
/*  97:    */     }
/*  98: 95 */     System.err.println("Sorry, " + blockRelation + " is not a valid block relation.");
/*  99: 96 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BlockFrame(Entity t)
/* 103:    */   {
/* 104:101 */     if (t.isA(FRAMETYPE)) {
/* 105:102 */       if ((t instanceof Relation)) {
/* 106:103 */         this.blockRelation = ((Relation)t);
/* 107:    */       } else {
/* 108:106 */         System.err.println("BlockFrame construction handed thing, not relation: " + t);
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BlockFrame(BlockFrame f)
/* 114:    */   {
/* 115:111 */     this.blockRelation = ((Relation)f.getThing().clone());
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Entity getThing()
/* 119:    */   {
/* 120:115 */     return this.blockRelation;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isEqual(Object f)
/* 124:    */   {
/* 125:118 */     if ((f instanceof BlockFrame)) {
/* 126:119 */       return this.blockRelation.isEqual(((BlockFrame)f).getThing());
/* 127:    */     }
/* 128:121 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String toString()
/* 132:    */   {
/* 133:126 */     if (this.blockRelation != null) {
/* 134:127 */       return this.blockRelation.toString();
/* 135:    */     }
/* 136:129 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static void main(String[] args)
/* 140:    */   {
/* 141:136 */     Relation rel = makeBlockRelation(new Entity("airplane"), new Entity("man"), "contains", "complete");
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.BlockFrame
 * JD-Core Version:    0.7.0.1
 */