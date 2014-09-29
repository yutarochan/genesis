/*   1:    */ package openmind.generate;
/*   2:    */ 
/*   3:    */ public class Corrections
/*   4:    */ {
/*   5:    */   public static String baseCorrections(String var)
/*   6:    */   {
/*   7:  8 */     var = correctUnderscore(correctApostrophe(var));
/*   8:  9 */     return var;
/*   9:    */   }
/*  10:    */   
/*  11:    */   public static String correctApostrophe(String var)
/*  12:    */   {
/*  13: 14 */     return 
/*  14:    */     
/*  15:    */ 
/*  16:    */ 
/*  17: 18 */       var.toLowerCase().replaceAll("\\s+s\\s+", "'s ").replaceAll("\\s+t\\s+", "'t ");
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static String correctUnderscore(String var)
/*  21:    */   {
/*  22: 24 */     return var.replaceAll(" ", "_");
/*  23:    */   }
/*  24:    */   
/*  25:    */   private static String[] decomposeVerbPhrase(String verb)
/*  26:    */   {
/*  27: 30 */     String[] vp = new String[2];
/*  28: 31 */     if (verb.contains("_"))
/*  29:    */     {
/*  30: 32 */       vp[0] = verb.substring(0, verb.indexOf("_"));
/*  31: 33 */       vp[1] = verb.substring(verb.indexOf("_") + 1);
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35: 35 */       vp[0] = verb;
/*  36: 36 */       vp[1] = "null";
/*  37:    */     }
/*  38: 38 */     return vp;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private static String[] correctPluralBe(String[] vp)
/*  42:    */   {
/*  43: 44 */     if (vp[0].contains("are")) {
/*  44: 44 */       vp[0] = vp[0].replace("are", "is");
/*  45:    */     }
/*  46: 45 */     return vp;
/*  47:    */   }
/*  48:    */   
/*  49:    */   private static String[] correctAmbiguousGet(String[] vp)
/*  50:    */   {
/*  51: 52 */     if (vp[0].contains("get")) {
/*  52: 53 */       if (vp[1].contains("_"))
/*  53:    */       {
/*  54: 54 */         String tmp = vp[1].substring(0, vp[1].indexOf("_"));
/*  55: 55 */         if ((tmp.equals("the")) || (tmp.equals("a"))) {
/*  56: 56 */           vp[0] = vp[0].replace("get", "obtain");
/*  57:    */         } else {
/*  58: 58 */           vp[0] = vp[0].replace("get", "become");
/*  59:    */         }
/*  60:    */       }
/*  61:    */       else
/*  62:    */       {
/*  63: 61 */         vp[0] = vp[0].replace("get", "become");
/*  64:    */       }
/*  65:    */     }
/*  66: 64 */     return vp;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static String[] correctNegationContractions(String[] vp)
/*  70:    */   {
/*  71: 71 */     String[] neg_vp = new String[3];
/*  72: 72 */     if ((vp[0].contains("n't")) || (vp[0].contains("'ve")))
/*  73:    */     {
/*  74: 73 */       String[] tmp_vp = decomposeVerbPhrase(vp[1]);
/*  75: 74 */       neg_vp[0] = tmp_vp[0];
/*  76: 75 */       neg_vp[1] = tmp_vp[1];
/*  77: 76 */       neg_vp[2] = "true";
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81: 78 */       neg_vp[0] = vp[0];
/*  82: 79 */       neg_vp[1] = vp[1];
/*  83: 80 */       neg_vp[2] = "false";
/*  84:    */     }
/*  85: 82 */     return neg_vp;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static String[] applyNonVerbExtractionFix(String[] vp)
/*  89:    */   {
/*  90: 89 */     vp[1] = (vp[0] + "_" + vp[1]);
/*  91: 90 */     vp[0] = "is";
/*  92: 91 */     return vp;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static String[] applyVerbCorrections(String verb)
/*  96:    */   {
/*  97: 97 */     verb = baseCorrections(verb);
/*  98: 98 */     String[] vp = decomposeVerbPhrase(verb);
/*  99: 99 */     vp = correctNegationContractions(correctAmbiguousGet(correctPluralBe(vp)));
/* 100:100 */     return vp;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.generate.Corrections
 * JD-Core Version:    0.7.0.1
 */