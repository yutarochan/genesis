/*   1:    */ package matthewFay.Utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.List;
/*   7:    */ import translator.Translator;
/*   8:    */ import utils.Mark;
/*   9:    */ import utils.PairOfEntities;
/*  10:    */ 
/*  11:    */ public class Generalizer
/*  12:    */ {
/*  13: 16 */   private static final ArrayList<Entity> transforms = new ArrayList() {};
/*  14: 47 */   private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
/*  15:    */   
/*  16:    */   private Entity getTransform(int i)
/*  17:    */   {
/*  18: 49 */     int mod = i % 26;
/*  19: 50 */     int multi = i / 26 + 1;
/*  20:    */     
/*  21: 52 */     char c = alphabet[mod];
/*  22: 53 */     String s = "";
/*  23: 54 */     for (int count = 0; count < multi; count++) {
/*  24: 55 */       s = s + c;
/*  25:    */     }
/*  26: 57 */     return new Entity(s);
/*  27:    */   }
/*  28:    */   
/*  29: 60 */   private static Entity focus = new Entity("FOCUS");
/*  30:    */   
/*  31:    */   public static Entity getFocus()
/*  32:    */   {
/*  33: 62 */     return focus;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Entity generalize(Entity element)
/*  37:    */   {
/*  38: 66 */     return generalize(element, null);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Entity generalize(Entity element, Entity focus)
/*  42:    */   {
/*  43: 70 */     return generalize(element, focus, EntityHelper.getAllEntities(element));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static Entity generalize(Entity element, Entity focus, Collection<Entity> targets)
/*  47:    */   {
/*  48: 74 */     return generalize(element, focus, targets, true);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static Entity generalize(Entity element, Entity focus, Collection<Entity> targets, boolean copy)
/*  52:    */   {
/*  53: 78 */     if (copy) {
/*  54: 79 */       return generalize(element.deepClone(false), focus, targets, false);
/*  55:    */     }
/*  56: 81 */     List<Entity> entities = EntityHelper.getAllEntities(element);
/*  57: 82 */     if (entities.size() >= transforms.size())
/*  58:    */     {
/*  59: 83 */       Mark.err(new Object[] {"Too many entities for Generalization!" });
/*  60: 84 */       Mark.err(new Object[] {"from: " + element });
/*  61: 85 */       Mark.err(new Object[] {"entities: " + entities });
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 87 */       List<PairOfEntities> bindings = new ArrayList();
/*  66: 88 */       int j = 0;
/*  67: 89 */       for (int i = 0; i < entities.size(); i++)
/*  68:    */       {
/*  69: 90 */         Entity entity = (Entity)entities.get(i);
/*  70: 91 */         if (entity.isEqual(focus)) {
/*  71: 92 */           bindings.add(new PairOfEntities(focus, focus));
/*  72:    */         } else {
/*  73: 94 */           for (Entity e : targets) {
/*  74: 95 */             if (e.isEqual(entity))
/*  75:    */             {
/*  76: 96 */               bindings.add(new PairOfEntities((Entity)entities.get(i), (Entity)transforms.get(j)));
/*  77: 97 */               j++;
/*  78: 98 */               break;
/*  79:    */             }
/*  80:    */           }
/*  81:    */         }
/*  82:    */       }
/*  83:103 */       element = EntityHelper.findAndReplace(element, bindings, true);
/*  84:    */     }
/*  85:105 */     return element;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void main(String[] args)
/*  89:    */     throws Exception
/*  90:    */   {
/*  91:109 */     Translator translator = Translator.getTranslator();
/*  92:110 */     Entity sentence = translator.translate("Macbeth kills Duncan with a knife.").getElement(0);
/*  93:    */     
/*  94:112 */     Mark.say(new Object[] {sentence });
/*  95:    */     
/*  96:114 */     Entity duncan = sentence.getObject().getElement(0).getSubject();
/*  97:115 */     Mark.say(new Object[] {duncan });
/*  98:    */     
/*  99:117 */     Entity gen = generalize(sentence, duncan);
/* 100:118 */     Mark.say(new Object[] {gen });
/* 101:    */     
/* 102:120 */     Entity sentence2 = translator.translate("Claudius kills King Hamlet with a poison.").getElement(0);
/* 103:121 */     Mark.say(new Object[] {sentence2 });
/* 104:122 */     Entity king_hamlet = sentence2.getObject().getElement(0).getSubject();
/* 105:    */     
/* 106:124 */     Entity gen2 = generalize(sentence2, king_hamlet);
/* 107:125 */     Mark.say(new Object[] {gen2 });
/* 108:    */     
/* 109:127 */     Mark.say(new Object[] {"Equal?: " + gen.asString().equals(gen2.asString()) });
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.Generalizer
 * JD-Core Version:    0.7.0.1
 */