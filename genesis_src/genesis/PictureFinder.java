/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import images.ImageAnchor;
/*  11:    */ import java.io.File;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.net.URISyntaxException;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Vector;
/*  18:    */ import lexicons.WorkingVocabulary;
/*  19:    */ 
/*  20:    */ public class PictureFinder
/*  21:    */   extends AbstractWiredBox
/*  22:    */ {
/*  23: 19 */   private ArrayList<File> files = new ArrayList();
/*  24: 21 */   private WorkingVocabulary workingVocabulary = WorkingVocabulary.getWorkingVocabulary();
/*  25:    */   
/*  26:    */   public PictureFinder()
/*  27:    */   {
/*  28: 24 */     Connections.getPorts(this).addSignalProcessor("input");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void input(Object object)
/*  32:    */   {
/*  33: 28 */     if (this.files.size() == 0) {
/*  34: 29 */       initializeFileArray();
/*  35:    */     }
/*  36: 31 */     if ((object instanceof Entity))
/*  37:    */     {
/*  38: 32 */       ArrayList<Vector> classVectors = getClasses((Entity)object);
/*  39: 33 */       if ((classVectors == null) || (classVectors.isEmpty())) {
/*  40: 34 */         return;
/*  41:    */       }
/*  42: 36 */       for (Iterator<Vector> iterator = classVectors.iterator(); iterator.hasNext();)
/*  43:    */       {
/*  44: 37 */         Vector<String> classes = (Vector)iterator.next();
/*  45: 39 */         for (int i = classes.size() - 1; i >= 0; i--)
/*  46:    */         {
/*  47: 40 */           boolean done = false;
/*  48: 41 */           for (int j = 0; j < this.files.size(); j++)
/*  49:    */           {
/*  50: 42 */             String className = (String)classes.get(i);
/*  51: 43 */             String fileName = stripExtension(((File)this.files.get(j)).getName());
/*  52: 44 */             if (className.toLowerCase().equals(fileName.toLowerCase()))
/*  53:    */             {
/*  54: 46 */               Connections.getPorts(this).transmit(this.files.get(j));
/*  55: 47 */               done = true;
/*  56: 48 */               break;
/*  57:    */             }
/*  58:    */           }
/*  59: 51 */           if (done) {
/*  60:    */             break;
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private String stripExtension(String s)
/*  68:    */   {
/*  69: 58 */     int index = s.indexOf('.');
/*  70: 59 */     if (index >= 0) {
/*  71: 60 */       return s.substring(0, index);
/*  72:    */     }
/*  73: 62 */     return s;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void initializeFileArray()
/*  77:    */   {
/*  78: 66 */     URL url = ImageAnchor.class.getResource("house.jpg");
/*  79:    */     
/*  80: 68 */     File[] candidates = new File[0];
/*  81:    */     try
/*  82:    */     {
/*  83: 71 */       candidates = new File(url.toURI()).getParentFile().listFiles();
/*  84:    */     }
/*  85:    */     catch (URISyntaxException e)
/*  86:    */     {
/*  87: 74 */       e.printStackTrace();
/*  88:    */     }
/*  89: 77 */     for (int i = 0; i < candidates.length; i++) {
/*  90: 78 */       if (candidates[i].isFile()) {
/*  91: 79 */         this.files.add(candidates[i]);
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   private ArrayList<Vector> getClasses(Entity thing)
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100: 86 */       ArrayList<Entity> things = digOutThing(thing);
/* 101: 87 */       ArrayList<Vector> result = new ArrayList();
/* 102: 88 */       for (int i = 0; i < things.size(); i++) {
/* 103: 89 */         result.add(((Entity)things.get(i)).getTypes());
/* 104:    */       }
/* 105: 91 */       return result;
/* 106:    */     }
/* 107:    */     catch (RuntimeException e)
/* 108:    */     {
/* 109: 94 */       System.err.println("Blew out of getClasses");
/* 110: 95 */       e.printStackTrace();
/* 111:    */     }
/* 112: 97 */     return new ArrayList();
/* 113:    */   }
/* 114:    */   
/* 115:    */   private ArrayList<Entity> digOutThing(Entity thing)
/* 116:    */   {
/* 117:101 */     if (thing.entityP())
/* 118:    */     {
/* 119:102 */       String word = thing.getType();
/* 120:103 */       this.workingVocabulary.add(word);
/* 121:    */       
/* 122:105 */       ArrayList<Entity> l = new ArrayList();
/* 123:106 */       l.add(thing);
/* 124:107 */       return l;
/* 125:    */     }
/* 126:109 */     if (thing.functionP()) {
/* 127:110 */       return digOutThing(((Function)thing).getSubject());
/* 128:    */     }
/* 129:112 */     if (thing.relationP())
/* 130:    */     {
/* 131:113 */       ArrayList<Entity> l = new ArrayList();
/* 132:114 */       ArrayList<Entity> subjects = digOutThing(((Relation)thing).getSubject());
/* 133:115 */       ArrayList<Entity> objects = digOutThing(((Relation)thing).getObject());
/* 134:116 */       l.addAll(subjects);
/* 135:117 */       l.addAll(objects);
/* 136:118 */       return l;
/* 137:    */     }
/* 138:120 */     if (thing.sequenceP())
/* 139:    */     {
/* 140:121 */       Sequence sequence = (Sequence)thing;
/* 141:122 */       Vector elements = sequence.getElements();
/* 142:123 */       ArrayList<Entity> l = new ArrayList();
/* 143:124 */       int i = sequence.getElements().size() - 1;
/* 144:124 */       if (i >= 0)
/* 145:    */       {
/* 146:125 */         Entity element = (Entity)elements.get(i);
/* 147:126 */         ArrayList<Entity> result = digOutThing(element);
/* 148:127 */         l.addAll(result);
/* 149:128 */         return l;
/* 150:    */       }
/* 151:    */     }
/* 152:131 */     return new ArrayList();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static void main(String[] ignore)
/* 156:    */   {
/* 157:135 */     Entity thing = new Entity("Dog");
/* 158:136 */     thing.addType("Bouvier");
/* 159:137 */     Entity thing2 = new Entity("Mountain");
/* 160:138 */     Relation relation = new Relation(thing, thing2);
/* 161:139 */     PictureFinder finder = new PictureFinder();
/* 162:140 */     finder.input(thing);
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.PictureFinder
 * JD-Core Version:    0.7.0.1
 */