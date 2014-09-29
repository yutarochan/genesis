/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import utils.Mark;
/*   5:    */ 
/*   6:    */ public class RoleFrameParent
/*   7:    */   extends RoleFrameGrandParent
/*   8:    */ {
/*   9: 15 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  10: 17 */   private String translation = "";
/*  11: 19 */   private static HashMap<String, String> markers = new HashMap();
/*  12:    */   
/*  13:    */   public RoleFrameParent() {}
/*  14:    */   
/*  15:    */   public RoleFrameParent(String source, RoleFrameGrandParent... entities)
/*  16:    */   {
/*  17: 25 */     initialize();
/*  18: 26 */     this.translation = translate(source, entities);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String toString()
/*  22:    */   {
/*  23: 30 */     return this.translation;
/*  24:    */   }
/*  25:    */   
/*  26:    */   private void record(RoleFrameGrandParent[] inputs)
/*  27:    */   {
/*  28: 34 */     if (inputs == null) {
/*  29: 35 */       return;
/*  30:    */     }
/*  31: 37 */     for (int i = 0; i < inputs.length; i++) {
/*  32: 38 */       roleFrameGrandParents.put(Integer.valueOf(i + 1), inputs[i]);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void initialize()
/*  37:    */   {
/*  38: 44 */     if (markers.size() != 0) {
/*  39: 45 */       return;
/*  40:    */     }
/*  41: 50 */     markers.put(":subject", "subject");
/*  42: 51 */     markers.put(":object", "object");
/*  43: 52 */     markers.put(":action", "verb");
/*  44: 53 */     markers.put(":verb", "verb");
/*  45:    */     
/*  46:    */ 
/*  47: 56 */     markers.put(":present", "present");
/*  48: 57 */     markers.put(":past", "past");
/*  49: 58 */     markers.put(":future", "future");
/*  50: 59 */     markers.put(":passive", "passive");
/*  51: 60 */     markers.put(":progressive", "progressive");
/*  52: 61 */     markers.put(":ing", "progressive");
/*  53: 62 */     markers.put(":negative", "not");
/*  54: 63 */     markers.put(":not", "not");
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58: 67 */     markers.put(":instrument", "with");
/*  59: 68 */     markers.put(":coagent", "with");
/*  60: 69 */     markers.put(":with", "with");
/*  61: 70 */     markers.put(":conveyance", "by");
/*  62: 71 */     markers.put(":by", "by");
/*  63: 72 */     markers.put(":source", "from");
/*  64: 73 */     markers.put(":from", "from");
/*  65: 74 */     markers.put(":out_OF", "out_of");
/*  66: 75 */     markers.put(":direction", "toward");
/*  67: 76 */     markers.put(":toward", "toward");
/*  68: 77 */     markers.put(":via", "via");
/*  69: 78 */     markers.put(":destination", "to");
/*  70: 79 */     markers.put(":to", "to");
/*  71: 80 */     markers.put(":in", "in");
/*  72: 81 */     markers.put(":during", "during");
/*  73: 82 */     markers.put(":while", "while");
/*  74: 83 */     markers.put(":when", "when");
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78: 87 */     markers.put(":before", "before");
/*  79: 88 */     markers.put(":after", "after");
/*  80: 89 */     markers.put(":while", "while");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String translate(String source, RoleFrameGrandParent[] entities)
/*  84:    */   {
/*  85: 94 */     String result = "";
/*  86: 95 */     Object subject = null;
/*  87: 96 */     this.head = null;
/*  88: 97 */     Object object = null;
/*  89: 98 */     String[] elements = source.split("\\s+");
/*  90:101 */     for (int i = 0; i < elements.length; i++)
/*  91:    */     {
/*  92:102 */       String element = elements[i];
/*  93:103 */       if (element.charAt(0) == '#')
/*  94:    */       {
/*  95:104 */         index = Integer.parseInt(element.substring(1));
/*  96:105 */         elements[i] = extractHead(entities[(index - 1)]);
/*  97:106 */         result = result + extractGuts(entities[(index - 1)]);
/*  98:    */       }
/*  99:    */     }
/* 100:111 */     for (int i = 0; i < elements.length; i++)
/* 101:    */     {
/* 102:112 */       String element = elements[i];
/* 103:114 */       if (element.charAt(0) == ':')
/* 104:    */       {
/* 105:115 */         if (element.equals(":subject"))
/* 106:    */         {
/* 107:116 */           subject = elements[(++i)];
/* 108:    */         }
/* 109:118 */         else if ((element.equals(":verb")) || (element.equals(":action")))
/* 110:    */         {
/* 111:119 */           this.head = elements[(++i)];
/* 112:    */         }
/* 113:121 */         else if (element.equals(":object"))
/* 114:    */         {
/* 115:122 */           object = elements[(++i)];
/* 116:    */         }
/* 117:125 */         else if ((element.equals(":with")) || (element.equals(":instrument")) || (element.equals(":coagent")))
/* 118:    */         {
/* 119:126 */           result = result + makeTriple(this.head, markers.get(element), elements[(++i)]);
/* 120:    */         }
/* 121:128 */         else if (element.equals(":in"))
/* 122:    */         {
/* 123:129 */           result = result + makeTriple(this.head, "in", elements[(++i)]);
/* 124:    */         }
/* 125:132 */         else if ((element.equals(":source")) || (element.equals(":from")) || (element.equals(":out_of")) || (element.equals(":via")) || 
/* 126:133 */           (element.equals(":direction")) || (element.equals(":toward")) || (element.equals(":destination")) || (element.equals(":to")))
/* 127:    */         {
/* 128:134 */           result = result + makeTriple(this.head, markers.get(element), elements[(++i)]);
/* 129:    */         }
/* 130:137 */         else if ((element.equals(":during")) || (element.equals(":in")))
/* 131:    */         {
/* 132:138 */           Mark.say(new Object[] {"Head is", this.head });
/* 133:139 */           result = result + makeTriple(this.head, markers.get(element), elements[(++i)]);
/* 134:    */         }
/* 135:141 */         else if ((element.equals(":before")) || (element.equals(":after")) || (element.equals(":while")))
/* 136:    */         {
/* 137:142 */           this.head = markers.get(element);
/* 138:143 */           object = elements[(++i)];
/* 139:144 */           result = result + makeProperty(markers.get(element), "is_clausal", "Yes");
/* 140:    */         }
/* 141:147 */         else if (element.equals(":present"))
/* 142:    */         {
/* 143:148 */           result = result + makeTriple(this.head, "has_tense", "present");
/* 144:    */         }
/* 145:150 */         else if (element.equals(":past"))
/* 146:    */         {
/* 147:151 */           result = result + makeTriple(this.head, "has_tense", "past");
/* 148:    */         }
/* 149:153 */         else if (element.equals(":future"))
/* 150:    */         {
/* 151:154 */           result = result + makeTriple(this.head, "has_tense", "present");
/* 152:155 */           result = result + makeTriple(this.head, "has_modal", "will");
/* 153:    */         }
/* 154:157 */         else if (element.equals(":passive"))
/* 155:    */         {
/* 156:158 */           result = result + makeTriple(this.head, "has_voice", "passive");
/* 157:    */         }
/* 158:160 */         else if ((element.equals(":progressive")) || (element.equals(":ing")))
/* 159:    */         {
/* 160:161 */           result = result + makeTriple(this.head, "is_progressive", "Yes");
/* 161:    */         }
/* 162:163 */         else if ((element.equals(":negative")) || (element.equals(":not")))
/* 163:    */         {
/* 164:164 */           result = result + makeTriple(this.head, "has_polarity", "not");
/* 165:    */         }
/* 166:    */       }
/* 167:169 */       else if (subject == null) {
/* 168:170 */         subject = element;
/* 169:172 */       } else if (this.head == null) {
/* 170:173 */         setRelation(element);
/* 171:175 */       } else if (object == null) {
/* 172:176 */         object = element;
/* 173:    */       }
/* 174:    */     }
/* 175:179 */     result = makeTriple(subject, this.head, object) + result;
/* 176:180 */     return result;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setRelation(Object verb)
/* 180:    */   {
/* 181:184 */     this.head = verb;
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.RoleFrameParent
 * JD-Core Version:    0.7.0.1
 */