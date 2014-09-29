/*   1:    */ package michaelBehr;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Document;
/*   4:    */ import org.w3c.dom.Element;
/*   5:    */ 
/*   6:    */ public class Track
/*   7:    */   implements AnnotationElement
/*   8:    */ {
/*   9:    */   String uid;
/*  10:    */   String type;
/*  11:    */   String entityType;
/*  12:    */   String features;
/*  13:    */   int startFrame;
/*  14:    */   int endFrame;
/*  15:    */   Frame[] frames;
/*  16:    */   Segment[] segments;
/*  17:    */   
/*  18:    */   public Track(String argUid, String argType, String argEntityType, int argStartFrame, int argEndFrame, Frame[] argFrames, Segment[] argSegments)
/*  19:    */   {
/*  20: 29 */     setUid(argUid);
/*  21: 30 */     setType(argType);
/*  22: 31 */     setEntityType(argEntityType);
/*  23: 32 */     setStartFrame(argStartFrame);
/*  24: 33 */     setEndFrame(argEndFrame);
/*  25: 34 */     setFrames(argFrames);
/*  26: 35 */     setSegments(argSegments);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Track(Element trackElement)
/*  30:    */     throws AnnotationParseException
/*  31:    */   {
/*  32: 42 */     setUid(trackElement.getAttribute("id"));
/*  33: 43 */     setType(trackElement.getAttribute("type"));
/*  34: 44 */     setStartFrame(Integer.parseInt(trackElement.getAttribute("startFrame")));
/*  35: 45 */     setEndFrame(Integer.parseInt(trackElement.getAttribute("endFrame")));
/*  36: 46 */     String maybeEntityType = trackElement.getAttribute("entityType");
/*  37: 47 */     String maybeFeatures = trackElement.getAttribute("features");
/*  38: 48 */     if (maybeFeatures.equals("")) {
/*  39: 49 */       setFeatures(null);
/*  40:    */     } else {
/*  41: 52 */       setFeatures(maybeFeatures);
/*  42:    */     }
/*  43: 54 */     if (maybeEntityType.equals("")) {
/*  44: 55 */       setEntityType(null);
/*  45:    */     } else {
/*  46: 58 */       setEntityType(maybeEntityType);
/*  47:    */     }
/*  48: 61 */     Element[] frameListElements = AnnotationUtils.getTypedChildren(trackElement, "frameList");
/*  49: 62 */     if (frameListElements.length != 1) {
/*  50: 63 */       throw new AnnotationParseException("Wrong number of frameList elements in a track");
/*  51:    */     }
/*  52: 65 */     Element frameListElement = frameListElements[0];
/*  53: 66 */     Element[] frameElements = AnnotationUtils.getTypedChildren(frameListElement, "frame");
/*  54: 67 */     Frame[] newFrames = new Frame[frameElements.length];
/*  55: 68 */     for (int i = 0; i < frameElements.length; i++) {
/*  56: 69 */       newFrames[i] = new Frame(frameElements[i]);
/*  57:    */     }
/*  58: 71 */     setFrames(newFrames);
/*  59:    */     
/*  60: 73 */     Element[] segmentListElements = AnnotationUtils.getTypedChildren(trackElement, "segmentList");
/*  61: 74 */     if (segmentListElements.length > 1) {
/*  62: 75 */       throw new AnnotationParseException("Too many segmentList elements in a track");
/*  63:    */     }
/*  64: 77 */     if (segmentListElements.length == 1)
/*  65:    */     {
/*  66: 78 */       Element segmentListElement = segmentListElements[0];
/*  67: 79 */       Element[] segmentElements = AnnotationUtils.getTypedChildren(segmentListElement, "segment");
/*  68: 80 */       Segment[] newSegments = new Segment[segmentElements.length];
/*  69: 81 */       for (int i = 0; i < segmentElements.length; i++) {
/*  70: 82 */         newSegments[i] = new Segment(segmentElements[i], this);
/*  71:    */       }
/*  72: 84 */       setSegments(newSegments);
/*  73:    */     }
/*  74: 86 */     else if (segmentListElements.length == 0)
/*  75:    */     {
/*  76: 87 */       setSegments(new Segment[0]);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Element toElement(Document doc)
/*  81:    */   {
/*  82: 92 */     Element out = doc.createElement("trackList");
/*  83: 93 */     out.setAttribute("id", getUid());
/*  84: 94 */     out.setAttribute("type", getType());
/*  85: 95 */     out.setAttribute("startFrame", Integer.toString(getStartFrame()));
/*  86: 96 */     out.setAttribute("endFrame", Integer.toString(getEndFrame()));
/*  87: 97 */     if (getEntityType() != null) {
/*  88: 98 */       out.setAttribute("entityType", getEntityType());
/*  89:    */     }
/*  90:101 */     out.appendChild(AnnotationUtils.makeListElement("frameList", getFrames(), doc));
/*  91:102 */     out.appendChild(AnnotationUtils.makeListElement("segmentList", getSegments(), doc));
/*  92:    */     
/*  93:104 */     return out;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public final String getUid()
/*  97:    */   {
/*  98:113 */     return this.uid;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final void setUid(String argUid)
/* 102:    */   {
/* 103:123 */     this.uid = argUid;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public final String getSubject()
/* 107:    */   {
/* 108:127 */     return getUid();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public final void setSubject(String argSubject)
/* 112:    */   {
/* 113:131 */     setUid(argSubject);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public final String getType()
/* 117:    */   {
/* 118:140 */     return this.type;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public final void setType(String argType)
/* 122:    */   {
/* 123:150 */     this.type = argType;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public final int getStartFrame()
/* 127:    */   {
/* 128:159 */     return this.startFrame;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public final void setStartFrame(int argStartFrame)
/* 132:    */   {
/* 133:169 */     this.startFrame = argStartFrame;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public final int getEndFrame()
/* 137:    */   {
/* 138:178 */     return this.endFrame;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public final void setEndFrame(int argEndFrame)
/* 142:    */   {
/* 143:188 */     this.endFrame = argEndFrame;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public final Frame[] getFrames()
/* 147:    */   {
/* 148:197 */     return this.frames;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public final void setFrames(Frame[] argFrames)
/* 152:    */   {
/* 153:207 */     this.frames = argFrames;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public final Segment[] getSegments()
/* 157:    */   {
/* 158:216 */     return this.segments;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public final void setSegments(Segment[] argSegments)
/* 162:    */   {
/* 163:226 */     this.segments = argSegments;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public final String getEntityType()
/* 167:    */   {
/* 168:235 */     return this.entityType;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public final void setEntityType(String argEntityType)
/* 172:    */   {
/* 173:245 */     this.entityType = argEntityType;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getFeatures()
/* 177:    */   {
/* 178:249 */     return this.features;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setFeatures(String features)
/* 182:    */   {
/* 183:253 */     this.features = features;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String toString()
/* 187:    */   {
/* 188:260 */     int sbSize = 1000;
/* 189:261 */     String variableSeparator = "  ";
/* 190:262 */     StringBuffer sb = new StringBuffer(1000);
/* 191:    */     
/* 192:264 */     sb.append("uid=").append(this.uid);
/* 193:265 */     sb.append("  ");
/* 194:266 */     sb.append("type=").append(this.type);
/* 195:267 */     sb.append("  ");
/* 196:268 */     sb.append("entityType=").append(this.entityType);
/* 197:269 */     sb.append("  ");
/* 198:270 */     sb.append("startFrame=").append(this.startFrame);
/* 199:271 */     sb.append("  ");
/* 200:272 */     sb.append("endFrame=").append(this.endFrame);
/* 201:273 */     sb.append("  ");
/* 202:274 */     sb.append("frames=").append(this.frames);
/* 203:275 */     sb.append("  ");
/* 204:276 */     sb.append("segments=").append(this.segments);
/* 205:    */     
/* 206:278 */     return sb.toString();
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.Track
 * JD-Core Version:    0.7.0.1
 */