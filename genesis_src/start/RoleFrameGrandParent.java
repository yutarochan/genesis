/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ 
/*   5:    */ public class RoleFrameGrandParent
/*   6:    */ {
/*   7: 12 */   public static String ENTITY = "entity";
/*   8: 14 */   protected static int index = 0;
/*   9: 16 */   protected static HashMap<String, String> indexMap = new HashMap();
/*  10: 18 */   protected static HashMap<Integer, RoleFrameGrandParent> roleFrameGrandParents = new HashMap();
/*  11:    */   protected Object head;
/*  12: 22 */   private String translation = "";
/*  13: 24 */   private static HashMap<String, String> markers = new HashMap();
/*  14:    */   
/*  15:    */   public RoleFrameGrandParent() {}
/*  16:    */   
/*  17:    */   public RoleFrameGrandParent(String source, RoleFrameGrandParent... entities)
/*  18:    */   {
/*  19: 31 */     this.translation = translate(source, entities);
/*  20:    */   }
/*  21:    */   
/*  22:    */   private String translate(String source, RoleFrameGrandParent[] entities)
/*  23:    */   {
/*  24: 56 */     String result = "";
/*  25: 57 */     String[] elements = source.split("\\s+");
/*  26: 61 */     for (int i = 0; i < elements.length; i++)
/*  27:    */     {
/*  28: 62 */       String element = elements[i];
/*  29: 63 */       if (element.charAt(0) == '#')
/*  30:    */       {
/*  31: 64 */         index = Integer.parseInt(element.substring(1));
/*  32: 65 */         elements[i] = extractHead(entities[(index - 1)]);
/*  33: 66 */         result = result + extractGuts(entities[(index - 1)]);
/*  34:    */       }
/*  35:    */     }
/*  36: 71 */     this.head = getIndexedWord(elements[(elements.length - 1)]);
/*  37: 72 */     for (int i = 0; i < elements.length - 1; i++)
/*  38:    */     {
/*  39: 73 */       String element = elements[i];
/*  40: 75 */       if (element.charAt(0) == ':')
/*  41:    */       {
/*  42: 76 */         if ((element.equals(":definite")) || (element.equals(":the"))) {
/*  43: 77 */           result = result + makeProperty(this.head, "has_det", "definite");
/*  44: 79 */         } else if ((element.equals(":indefinite")) || (element.equals(":a")) || (element.equals(":an"))) {
/*  45: 80 */           result = result + makeProperty(this.head, "has_det", "indefinite");
/*  46:    */         }
/*  47:    */       }
/*  48:    */       else {
/*  49: 84 */         result = result + makeProperty(this.head, "has_property", elements[i]);
/*  50:    */       }
/*  51:    */     }
/*  52: 87 */     return result;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57: 92 */     return this.translation;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected String decorate(Object s, Object r, Object o)
/*  61:    */   {
/*  62: 96 */     return "[" + s + " " + r + " " + o + "]";
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected String addTriple(Object s, Object r, Object o)
/*  66:    */   {
/*  67:100 */     String result = extractGuts(s);
/*  68:101 */     result = result + decorate(s, r, o);
/*  69:102 */     return result;
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected String makeProperty(Object s, Object r, Object o)
/*  73:    */   {
/*  74:106 */     String result = extractGuts(s);
/*  75:107 */     result = result + decorate(extractHead(s), r, o);
/*  76:108 */     return result;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected String makeTriple(Object s, Object r, Object o)
/*  80:    */   {
/*  81:112 */     String result = extractGuts(s);
/*  82:113 */     result = result + extractGuts(r);
/*  83:114 */     result = result + extractGuts(o);
/*  84:115 */     result = result + decorate(extractHead(s), extractHead(r), extractHead(o));
/*  85:116 */     return result;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String extractHead(Object x)
/*  89:    */   {
/*  90:120 */     if (x == null) {
/*  91:121 */       return null;
/*  92:    */     }
/*  93:123 */     if ((x instanceof RoleFrameGrandParent)) {
/*  94:124 */       return ((RoleFrameGrandParent)x).getHead();
/*  95:    */     }
/*  96:126 */     return getIndexedWord((String)x);
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected String extractGuts(Object x)
/* 100:    */   {
/* 101:130 */     String result = "";
/* 102:131 */     if (x == null) {
/* 103:132 */       return result;
/* 104:    */     }
/* 105:134 */     if (((x instanceof String)) && (((String)x).charAt(0) == '#'))
/* 106:    */     {
/* 107:135 */       String element = (String)x;
/* 108:136 */       int index = Integer.parseInt(element.substring(1));
/* 109:137 */       return ((RoleFrameGrandParent)roleFrameGrandParents.get(Integer.valueOf(index))).toString();
/* 110:    */     }
/* 111:139 */     if ((x instanceof RoleFrameGrandParent)) {
/* 112:140 */       result = result + ((RoleFrameGrandParent)x).toString();
/* 113:142 */     } else if ((x instanceof RoleFrameParent)) {
/* 114:143 */       result = result + ((RoleFrameParent)x).toString();
/* 115:    */     }
/* 116:145 */     return result;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected String getIndexedWord(Object object)
/* 120:    */   {
/* 121:149 */     return getIndexedWord(object, true);
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected String getIndexedWord(Object object, boolean useNewIndex)
/* 125:    */   {
/* 126:153 */     if (object == null) {
/* 127:154 */       return null;
/* 128:    */     }
/* 129:156 */     String key = object.toString();
/* 130:157 */     String value = (String)indexMap.get(key);
/* 131:158 */     String result = key;
/* 132:159 */     if ((value == null) || (useNewIndex))
/* 133:    */     {
/* 134:162 */       int location = key.lastIndexOf('-');
/* 135:163 */       if (location > 0)
/* 136:    */       {
/* 137:164 */         StringBuffer buffer = new StringBuffer(key);
/* 138:165 */         String suffix = buffer.substring(location + 1);
/* 139:    */         try
/* 140:    */         {
/* 141:167 */           Integer.parseInt(suffix);
/* 142:168 */           buffer.replace(location, location + 1, "+");
/* 143:169 */           result = buffer.toString();
/* 144:    */         }
/* 145:    */         catch (NumberFormatException localNumberFormatException) {}
/* 146:    */       }
/* 147:176 */       if (result.indexOf('+') < 0)
/* 148:    */       {
/* 149:178 */         result = result + '+' + Integer.toString(index++);
/* 150:179 */         indexMap.put(key, result);
/* 151:    */       }
/* 152:182 */       return result;
/* 153:    */     }
/* 154:186 */     return value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected String getHead()
/* 158:    */   {
/* 159:191 */     if (this.head == null) {
/* 160:192 */       toString();
/* 161:    */     }
/* 162:195 */     return getIndexedWord(this.head);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setHead(Object head)
/* 166:    */   {
/* 167:199 */     this.head = head;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.RoleFrameGrandParent
 * JD-Core Version:    0.7.0.1
 */