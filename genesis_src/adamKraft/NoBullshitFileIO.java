/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileNotFoundException;
/*   7:    */ import java.io.FileReader;
/*   8:    */ import java.io.FileWriter;
/*   9:    */ import java.io.IOError;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.Map;
/*  14:    */ 
/*  15:    */ public class NoBullshitFileIO
/*  16:    */ {
/*  17: 17 */   private static Map<String, String> openFiles = new HashMap();
/*  18:    */   private FileWriter steaming;
/*  19:    */   private BufferedWriter pile;
/*  20:    */   private FileReader bull;
/*  21:    */   private BufferedReader shit;
/*  22:    */   private String fname;
/*  23:    */   
/*  24:    */   public NoBullshitFileIO(String fileName)
/*  25:    */   {
/*  26: 20 */     this(new File(fileName));
/*  27:    */   }
/*  28:    */   
/*  29:    */   public NoBullshitFileIO(String fileName, String mode)
/*  30:    */   {
/*  31: 23 */     this(new File(fileName), mode);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public NoBullshitFileIO(File f)
/*  35:    */   {
/*  36: 26 */     this(f, "r");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public NoBullshitFileIO(File f, String mode)
/*  40:    */   {
/*  41: 34 */     if ((mode.toLowerCase().contains("a")) || (mode.toLowerCase().contains("r")))
/*  42:    */     {
/*  43:    */       try
/*  44:    */       {
/*  45: 36 */         this.bull = new FileReader(f);
/*  46:    */       }
/*  47:    */       catch (FileNotFoundException e)
/*  48:    */       {
/*  49: 38 */         throw new IOError(e);
/*  50:    */       }
/*  51: 40 */       this.shit = new BufferedReader(this.bull);
/*  52:    */     }
/*  53: 42 */     if ((mode.toLowerCase().contains("a")) || (mode.toLowerCase().contains("w")))
/*  54:    */     {
/*  55:    */       try
/*  56:    */       {
/*  57: 44 */         this.steaming = new FileWriter(f);
/*  58:    */       }
/*  59:    */       catch (IOException e)
/*  60:    */       {
/*  61: 46 */         throw new IOError(e);
/*  62:    */       }
/*  63: 48 */       this.pile = new BufferedWriter(this.steaming);
/*  64:    */     }
/*  65: 50 */     this.fname = f.getPath();
/*  66: 51 */     synchronized (openFiles)
/*  67:    */     {
/*  68: 52 */       openFiles.put(this.fname, mode);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void write(String s)
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76: 57 */       this.pile.write(s);
/*  77:    */     }
/*  78:    */     catch (IOException e)
/*  79:    */     {
/*  80: 59 */       throw new IOError(e);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String readLine()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88: 64 */       return this.shit.readLine();
/*  89:    */     }
/*  90:    */     catch (IOException e)
/*  91:    */     {
/*  92: 66 */       throw new IOError(e);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String read()
/*  97:    */   {
/*  98: 70 */     String s = "";
/*  99:    */     for (;;)
/* 100:    */     {
/* 101: 72 */       String l = readLine();
/* 102: 73 */       if (l == null) {
/* 103:    */         break;
/* 104:    */       }
/* 105: 74 */       s = s + l + "\n";
/* 106:    */     }
/* 107: 76 */     return s;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Iterable<String> getLines()
/* 111:    */   {
/* 112: 81 */     new Iterable()
/* 113:    */     {
/* 114:    */       public Iterator<String> iterator()
/* 115:    */       {
/* 116: 84 */         new Iterator()
/* 117:    */         {
/* 118:    */           String line;
/* 119:    */           
/* 120:    */           public synchronized boolean hasNext()
/* 121:    */           {
/* 122: 88 */             if (this.line != null) {
/* 123: 89 */               return true;
/* 124:    */             }
/* 125: 91 */             this.line = NoBullshitFileIO.this.readLine();
/* 126: 92 */             return this.line != null;
/* 127:    */           }
/* 128:    */           
/* 129:    */           public synchronized String next()
/* 130:    */           {
/* 131: 97 */             if (this.line != null)
/* 132:    */             {
/* 133: 98 */               String tmp = this.line;
/* 134: 99 */               this.line = null;
/* 135:100 */               return tmp;
/* 136:    */             }
/* 137:102 */             return NoBullshitFileIO.this.readLine();
/* 138:    */           }
/* 139:    */           
/* 140:    */           public synchronized void remove()
/* 141:    */           {
/* 142:108 */             throw new RuntimeException("remove doesn't make sense here.");
/* 143:    */           }
/* 144:    */         };
/* 145:    */       }
/* 146:    */     };
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void close()
/* 150:    */   {
/* 151:    */     try
/* 152:    */     {
/* 153:118 */       if (this.steaming != null) {
/* 154:119 */         this.steaming.close();
/* 155:    */       }
/* 156:121 */       if (this.pile != null) {
/* 157:122 */         this.pile.close();
/* 158:    */       }
/* 159:124 */       if (this.bull != null) {
/* 160:125 */         this.bull.close();
/* 161:    */       }
/* 162:127 */       if (this.shit != null) {
/* 163:128 */         this.shit.close();
/* 164:    */       }
/* 165:    */     }
/* 166:    */     catch (IOException e)
/* 167:    */     {
/* 168:131 */       throw new IOError(e);
/* 169:    */     }
/* 170:    */     finally
/* 171:    */     {
/* 172:133 */       synchronized (openFiles)
/* 173:    */       {
/* 174:134 */         openFiles.remove(this.fname);
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected void finalize()
/* 180:    */   {
/* 181:140 */     close();
/* 182:    */   }
/* 183:    */   
/* 184:    */   private static void doJavaPlatformDependentFileDrudgery(PredicateClosure p, String msg)
/* 185:    */   {
/* 186:148 */     int tries = 0;
/* 187:149 */     while (!p.eval())
/* 188:    */     {
/* 189:150 */       if (tries > 100) {
/* 190:151 */         throw new IOError(new IOException(msg));
/* 191:    */       }
/* 192:153 */       System.gc();
/* 193:    */       try
/* 194:    */       {
/* 195:155 */         Thread.sleep(1L);
/* 196:    */       }
/* 197:    */       catch (InterruptedException e)
/* 198:    */       {
/* 199:157 */         e.printStackTrace();
/* 200:    */       }
/* 201:159 */       tries++;
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static abstract interface PredicateClosure
/* 206:    */   {
/* 207:    */     public abstract boolean eval();
/* 208:    */   }
/* 209:    */   
/* 210:    */   private static class PurgeFile
/* 211:    */     implements NoBullshitFileIO.PredicateClosure
/* 212:    */   {
/* 213:    */     private String name;
/* 214:    */     
/* 215:    */     public PurgeFile(String name)
/* 216:    */     {
/* 217:165 */       this.name = name;
/* 218:    */     }
/* 219:    */     
/* 220:    */     public boolean eval()
/* 221:    */     {
/* 222:169 */       return NoBullshitFileIO.openFiles.get(this.name) == null;
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static void rename(String oldName, final String newName)
/* 227:    */   {
/* 228:186 */     doJavaPlatformDependentFileDrudgery(new PurgeFile(oldName), oldName + " is open");
/* 229:187 */     doJavaPlatformDependentFileDrudgery(new PurgeFile(newName), newName + " is open");
/* 230:188 */     synchronized (openFiles)
/* 231:    */     {
/* 232:189 */       doJavaPlatformDependentFileDrudgery(new PredicateClosure()
/* 233:    */       {
/* 234:    */         public boolean eval()
/* 235:    */         {
/* 236:183 */           return new File(NoBullshitFileIO.this).renameTo(new File(newName));
/* 237:    */         }
/* 238:189 */       }, "old or new file might be being held open, or some other mischief");
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static void delete(String name)
/* 243:    */   {
/* 244:204 */     doJavaPlatformDependentFileDrudgery(new PurgeFile(name), name + " is open");
/* 245:205 */     synchronized (openFiles)
/* 246:    */     {
/* 247:206 */       doJavaPlatformDependentFileDrudgery(new PredicateClosure()
/* 248:    */       {
/* 249:    */         public boolean eval()
/* 250:    */         {
/* 251:201 */           return new File(NoBullshitFileIO.this).delete();
/* 252:    */         }
/* 253:206 */       }, "file might be being held open, or some other mischief");
/* 254:    */     }
/* 255:    */   }
/* 256:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.NoBullshitFileIO
 * JD-Core Version:    0.7.0.1
 */