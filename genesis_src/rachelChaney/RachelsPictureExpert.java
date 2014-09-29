/*   1:    */ package rachelChaney;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.FilenameFilter;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.net.URI;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Vector;
/*  19:    */ import lexicons.WorkingVocabulary;
/*  20:    */ import links.words.WordNet;
/*  21:    */ import utils.PathFinder;
/*  22:    */ 
/*  23:    */ public class RachelsPictureExpert
/*  24:    */   extends AbstractWiredBox
/*  25:    */ {
/*  26: 22 */   private WordNet wordNet = null;
/*  27: 24 */   private List<String> files = new ArrayList();
/*  28: 26 */   private WorkingVocabulary workingVocabulary = WorkingVocabulary.getWorkingVocabulary();
/*  29:    */   
/*  30:    */   public RachelsPictureExpert()
/*  31:    */   {
/*  32: 29 */     Connections.getPorts(this).addSignalProcessor("input");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void input(Object object)
/*  36:    */   {
/*  37: 34 */     if (this.files.size() == 0) {
/*  38: 35 */       initializeFileArray();
/*  39:    */     }
/*  40: 37 */     if ((object instanceof Entity))
/*  41:    */     {
/*  42: 38 */       Entity t = (Entity)object;
/*  43: 39 */       ArrayList<Vector> classVectors = getClasses(t);
/*  44: 40 */       if ((classVectors == null) || (classVectors.isEmpty())) {
/*  45: 41 */         return;
/*  46:    */       }
/*  47: 44 */       for (Iterator<Vector> iterator = classVectors.iterator(); iterator.hasNext();)
/*  48:    */       {
/*  49: 45 */         Vector<String> classes = (Vector)iterator.next();
/*  50: 46 */         boolean done = false;
/*  51: 48 */         for (int i = classes.size() - 1; i >= 1; i--)
/*  52:    */         {
/*  53: 49 */           String className = (String)classes.get(i - 1) + (String)classes.get(i);
/*  54: 52 */           for (int j = 0; j < this.files.size(); j++)
/*  55:    */           {
/*  56: 53 */             String fileName = stripExtension((String)this.files.get(j));
/*  57: 55 */             if (className.toLowerCase().equals(fileName.toLowerCase()))
/*  58:    */             {
/*  59: 57 */               Connections.getPorts(this).transmit("viewer", this.files.get(j));
/*  60: 58 */               done = true;
/*  61: 59 */               break;
/*  62:    */             }
/*  63:    */           }
/*  64: 65 */           if (done) {
/*  65:    */             break;
/*  66:    */           }
/*  67:    */         }
/*  68: 73 */         if ((!done) && (classes.size() > 0) && (!((String)classes.lastElement()).equals("root"))) {
/*  69: 77 */           Connections.getPorts(this).transmit("question2.gif");
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   private String stripExtension(String s)
/*  76:    */   {
/*  77: 84 */     int index = s.indexOf('.');
/*  78: 85 */     if (index >= 0) {
/*  79: 86 */       return s.substring(0, index);
/*  80:    */     }
/*  81: 88 */     return s;
/*  82:    */   }
/*  83:    */   
/*  84: 92 */   private String pictureNames = "pictureNames.serial";
/*  85:    */   
/*  86:    */   private void initializeFileArray()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:103 */       for (URL url : PathFinder.listFiles("images", ".jpg"))
/*  91:    */       {
/*  92:105 */         String name = new File(url.toURI().getPath()).getName();
/*  93:    */         
/*  94:107 */         this.files.add(name);
/*  95:    */       }
/*  96:    */     }
/*  97:    */     catch (Exception localException) {}
/*  98:    */   }
/*  99:    */   
/* 100:    */   private class JpgFilter
/* 101:    */     implements FilenameFilter
/* 102:    */   {
/* 103:    */     private JpgFilter() {}
/* 104:    */     
/* 105:    */     public boolean accept(File dir, String name)
/* 106:    */     {
/* 107:122 */       if ((name != null) && (name.indexOf(".jpg") > 0)) {
/* 108:123 */         return true;
/* 109:    */       }
/* 110:125 */       return false;
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   private ArrayList<Vector> getClasses(Entity thing)
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:133 */       ArrayList<Entity> things = digOutThings(thing);
/* 119:134 */       ArrayList<Vector> result = new ArrayList();
/* 120:135 */       for (int i = 0; i < things.size(); i++)
/* 121:    */       {
/* 122:136 */         Vector<?> v = (Vector)((Entity)things.get(i)).getTypes().clone();
/* 123:137 */         v.remove("name");
/* 124:138 */         result.add(v);
/* 125:    */       }
/* 126:141 */       return result;
/* 127:    */     }
/* 128:    */     catch (RuntimeException e)
/* 129:    */     {
/* 130:144 */       System.err.println("Blew out of getClasses");
/* 131:145 */       e.printStackTrace();
/* 132:    */     }
/* 133:147 */     return new ArrayList();
/* 134:    */   }
/* 135:    */   
/* 136:    */   private ArrayList<Entity> digOutThings(Entity thing)
/* 137:    */   {
/* 138:151 */     if (thing.sequenceP())
/* 139:    */     {
/* 140:152 */       Sequence sequence = (Sequence)thing;
/* 141:153 */       Vector<?> elements = sequence.getElements();
/* 142:154 */       ArrayList<Entity> l = new ArrayList();
/* 143:155 */       for (int i = 0; i < sequence.getElements().size(); i++)
/* 144:    */       {
/* 145:156 */         Entity element = (Entity)elements.get(i);
/* 146:157 */         ArrayList<Entity> result = digOutThings(element);
/* 147:158 */         l.addAll(result);
/* 148:    */       }
/* 149:160 */       return l;
/* 150:    */     }
/* 151:162 */     if (thing.relationP())
/* 152:    */     {
/* 153:163 */       ArrayList<Entity> l = new ArrayList();
/* 154:164 */       ArrayList<Entity> subjects = digOutThings(((Relation)thing).getSubject());
/* 155:165 */       ArrayList<Entity> objects = digOutThings(((Relation)thing).getObject());
/* 156:166 */       l.addAll(subjects);
/* 157:167 */       l.addAll(objects);
/* 158:168 */       return l;
/* 159:    */     }
/* 160:170 */     if (thing.functionP()) {
/* 161:171 */       return digOutThings(((Function)thing).getSubject());
/* 162:    */     }
/* 163:174 */     String word = thing.getType();
/* 164:175 */     this.workingVocabulary.add(word);
/* 165:    */     
/* 166:    */ 
/* 167:178 */     ArrayList<Entity> l = new ArrayList();
/* 168:179 */     l.add(thing);
/* 169:180 */     return l;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static void main(String[] ignore) {}
/* 173:    */   
/* 174:    */   public WordNet getWordNet()
/* 175:    */   {
/* 176:204 */     if (this.wordNet == null) {
/* 177:205 */       this.wordNet = new WordNet();
/* 178:    */     }
/* 179:207 */     return this.wordNet;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean accept(File arg0, String arg1)
/* 183:    */   {
/* 184:211 */     return false;
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     rachelChaney.RachelsPictureExpert
 * JD-Core Version:    0.7.0.1
 */