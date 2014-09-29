/*   1:    */ package michaelBehr;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Document;
/*   4:    */ import org.w3c.dom.Element;
/*   5:    */ 
/*   6:    */ public class Label
/*   7:    */   implements AnnotationElement
/*   8:    */ {
/*   9:    */   String uid;
/*  10:    */   String subject;
/*  11:    */   Track subjectTrack;
/*  12:    */   String directObject;
/*  13:    */   Track directObjectTrack;
/*  14:    */   String indirectObject;
/*  15:    */   Track indirectObjectTrack;
/*  16:    */   String value;
/*  17:    */   
/*  18:    */   public Label(String argUid, String argSubject, String argDirectObject, String argIndirectObject, String argValue)
/*  19:    */   {
/*  20: 29 */     setUid(argUid);
/*  21: 30 */     setSubject(argSubject);
/*  22: 31 */     setDirectObject(argDirectObject);
/*  23: 32 */     setIndirectObject(argIndirectObject);
/*  24: 33 */     setValue(argValue);
/*  25: 34 */     setDirectObjectTrack(null);
/*  26: 35 */     setIndirectObjectTrack(null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Label(Element labelElement, Track argSubjectTrack)
/*  30:    */   {
/*  31: 44 */     setDirectObjectTrack(null);
/*  32: 45 */     setIndirectObjectTrack(null);
/*  33:    */     
/*  34: 47 */     setSubjectTrack(argSubjectTrack);
/*  35: 48 */     setSubject(argSubjectTrack.getSubject());
/*  36:    */     
/*  37: 50 */     setUid(labelElement.getAttribute("id"));
/*  38: 51 */     setValue(labelElement.getAttribute("labelValue"));
/*  39: 52 */     String maybeDirect = labelElement.getAttribute("direct");
/*  40: 53 */     if (maybeDirect.equals("")) {
/*  41: 54 */       setDirectObject(null);
/*  42:    */     } else {
/*  43: 56 */       setDirectObject(maybeDirect);
/*  44:    */     }
/*  45: 58 */     String maybeIndirect = labelElement.getAttribute("indirect");
/*  46: 59 */     if (maybeIndirect.equals("")) {
/*  47: 60 */       setIndirectObject(null);
/*  48:    */     } else {
/*  49: 62 */       setIndirectObject(maybeIndirect);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Element toElement(Document doc)
/*  54:    */   {
/*  55: 67 */     Element out = doc.createElement("label");
/*  56: 68 */     out.setAttribute("id", getUid());
/*  57: 69 */     out.setAttribute("labelValue", getValue());
/*  58: 70 */     if (getDirectObject() != null) {
/*  59: 71 */       out.setAttribute("direct", getDirectObject());
/*  60:    */     }
/*  61: 73 */     if (getIndirectObject() != null) {
/*  62: 74 */       out.setAttribute("indirect", getDirectObject());
/*  63:    */     }
/*  64: 76 */     return out;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final String getUid()
/*  68:    */   {
/*  69: 86 */     return this.uid;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final void setUid(String argUid)
/*  73:    */   {
/*  74: 95 */     this.uid = argUid;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final String getSubject()
/*  78:    */   {
/*  79:104 */     return this.subject;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final void setSubject(String argSubject)
/*  83:    */   {
/*  84:113 */     this.subject = argSubject;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final String getDirectObject()
/*  88:    */   {
/*  89:122 */     return this.directObject;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final void setDirectObject(String argDirectObject)
/*  93:    */   {
/*  94:131 */     this.directObject = argDirectObject;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final String getIndirectObject()
/*  98:    */   {
/*  99:140 */     return this.indirectObject;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final void setIndirectObject(String argIndirectObject)
/* 103:    */   {
/* 104:149 */     this.indirectObject = argIndirectObject;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final String getValue()
/* 108:    */   {
/* 109:159 */     return this.value;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final void setValue(String argValue)
/* 113:    */   {
/* 114:168 */     this.value = argValue;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final Track getDirectObjectTrack()
/* 118:    */   {
/* 119:178 */     return this.directObjectTrack;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final void setDirectObjectTrack(Track argDirectObjectTrack)
/* 123:    */   {
/* 124:187 */     this.directObjectTrack = argDirectObjectTrack;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final Track getIndirectObjectTrack()
/* 128:    */   {
/* 129:196 */     return this.indirectObjectTrack;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public final void setIndirectObjectTrack(Track argIndirectObjectTrack)
/* 133:    */   {
/* 134:205 */     this.indirectObjectTrack = argIndirectObjectTrack;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public final Track getSubjectTrack()
/* 138:    */   {
/* 139:215 */     return this.subjectTrack;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public final void setSubjectTrack(Track argSubjectTrack)
/* 143:    */   {
/* 144:224 */     this.subjectTrack = argSubjectTrack;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String toString()
/* 148:    */   {
/* 149:233 */     int sbSize = 1000;
/* 150:234 */     String variableSeparator = "  ";
/* 151:235 */     StringBuffer sb = new StringBuffer(1000);
/* 152:    */     
/* 153:237 */     sb.append("uid=").append(this.uid);
/* 154:238 */     sb.append("  ");
/* 155:239 */     sb.append("subject=").append(this.subject);
/* 156:240 */     sb.append("  ");
/* 157:241 */     sb.append("directObject=").append(this.directObject);
/* 158:242 */     sb.append("  ");
/* 159:243 */     sb.append("directObjectTrack=").append(this.directObjectTrack);
/* 160:244 */     sb.append("  ");
/* 161:245 */     sb.append("indirectObject=").append(this.indirectObject);
/* 162:246 */     sb.append("  ");
/* 163:247 */     sb.append("indirectObjectTrack=").append(this.indirectObjectTrack);
/* 164:248 */     sb.append("  ");
/* 165:249 */     sb.append("value=").append(this.value);
/* 166:    */     
/* 167:251 */     return sb.toString();
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.Label
 * JD-Core Version:    0.7.0.1
 */