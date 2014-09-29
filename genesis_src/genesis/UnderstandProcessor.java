/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Thread;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import java.util.ListIterator;
/*  9:   */ import java.util.Vector;
/* 10:   */ 
/* 11:   */ public class UnderstandProcessor
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   public static final String DERIVED = "derived";
/* 15:   */   
/* 16:   */   UnderstandProcessor()
/* 17:   */   {
/* 18:25 */     Connections.getPorts(this).addSignalProcessor("setInput");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setInput(Object o)
/* 22:   */   {
/* 23:29 */     if ((o instanceof Entity)) {
/* 24:30 */       Connections.getPorts(this).transmit(derive((Entity)o));
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Entity derive(Entity t)
/* 29:   */   {
/* 30:35 */     if (t.functionP())
/* 31:   */     {
/* 32:37 */       if (t.isA("derived"))
/* 33:   */       {
/* 34:38 */         deriveTypes(t, t.getSubject());
/* 35:39 */         return derive(t.getSubject());
/* 36:   */       }
/* 37:42 */       t.setSubject(derive(t.getSubject()));
/* 38:   */     }
/* 39:45 */     else if (t.relationP())
/* 40:   */     {
/* 41:46 */       t.setSubject(derive(t.getSubject()));
/* 42:47 */       t.setObject(derive(t.getObject()));
/* 43:   */     }
/* 44:50 */     else if (t.sequenceP())
/* 45:   */     {
/* 46:52 */       if (t.isA("derived"))
/* 47:   */       {
/* 48:53 */         deriveTypes(t, t.getElement(0));
/* 49:54 */         return derive(t.getElement(0));
/* 50:   */       }
/* 51:57 */       ListIterator<Entity> kids = t.getElements().listIterator();
/* 52:58 */       while (kids.hasNext())
/* 53:   */       {
/* 54:59 */         Entity kid = (Entity)kids.next();
/* 55:60 */         if (kid.isA("parse-link")) {
/* 56:61 */           kids.remove();
/* 57:   */         } else {
/* 58:63 */           kids.set(derive(kid));
/* 59:   */         }
/* 60:   */       }
/* 61:   */     }
/* 62:67 */     return t;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void deriveTypes(Entity derived, Entity t)
/* 66:   */   {
/* 67:79 */     deriveTypesAfterReference((String)t.getPrimedThread().get(0), derived, t);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static void deriveTypesAfterReference(String reference, Entity derived, Entity t)
/* 71:   */   {
/* 72:91 */     Vector<String> target = t.getPrimedThread();
/* 73:92 */     for (int i = 0; i < target.size(); i++) {
/* 74:93 */       if (reference.equals(target.get(i)))
/* 75:   */       {
/* 76:94 */         Vector<String> source = derived.getPrimedThread();
/* 77:95 */         for (int j = 0; j < source.size(); j++) {
/* 78:96 */           if (((String)source.get(i)).equals("derived"))
/* 79:   */           {
/* 80:97 */             target.addAll(i + 1, source.subList(j + 1, source.size()));
/* 81:98 */             return;
/* 82:   */           }
/* 83:   */         }
/* 84:   */       }
/* 85:   */     }
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.UnderstandProcessor
 * JD-Core Version:    0.7.0.1
 */