/*   1:    */ package michaelBehr;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Document;
/*   4:    */ import org.w3c.dom.Element;
/*   5:    */ 
/*   6:    */ public class Segment
/*   7:    */   implements AnnotationElement
/*   8:    */ {
/*   9:    */   String uid;
/*  10:    */   String subject;
/*  11:    */   int startFrame;
/*  12:    */   int endFrame;
/*  13:    */   Label[] labels;
/*  14:    */   
/*  15:    */   public Segment(String argUid, String argSubject, int argStartFrame, int argEndFrame, Label[] argLabels)
/*  16:    */   {
/*  17: 20 */     setUid(argUid);
/*  18: 21 */     setSubject(argSubject);
/*  19: 22 */     setStartFrame(argStartFrame);
/*  20: 23 */     setEndFrame(argEndFrame);
/*  21: 24 */     setLabels(argLabels);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Segment(Element segmentElement, Track argSubjectTrack)
/*  25:    */     throws AnnotationParseException
/*  26:    */   {
/*  27: 32 */     setSubject(argSubjectTrack.getSubject());
/*  28:    */     
/*  29: 34 */     setUid(segmentElement.getAttribute("id"));
/*  30: 35 */     setStartFrame(Integer.parseInt(segmentElement.getAttribute("startFrame")));
/*  31: 36 */     setEndFrame(Integer.parseInt(segmentElement.getAttribute("endFrame")));
/*  32:    */     
/*  33: 38 */     Element[] labelListElements = AnnotationUtils.getTypedChildren(segmentElement, "labelList");
/*  34: 39 */     if (labelListElements.length != 1) {
/*  35: 40 */       throw new AnnotationParseException("Wrong number of labelLists in a segment");
/*  36:    */     }
/*  37: 42 */     Element labelListElement = labelListElements[0];
/*  38: 43 */     Element[] labelElements = AnnotationUtils.getTypedChildren(labelListElement, "label");
/*  39: 44 */     Label[] newLabels = new Label[labelElements.length];
/*  40: 45 */     for (int i = 0; i < labelElements.length; i++) {
/*  41: 46 */       newLabels[i] = new Label(labelElements[i], argSubjectTrack);
/*  42:    */     }
/*  43: 48 */     setLabels(newLabels);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Element toElement(Document doc)
/*  47:    */   {
/*  48: 52 */     Element out = doc.createElement("segmentList");
/*  49: 53 */     out.setAttribute("id", getUid());
/*  50: 54 */     out.setAttribute("startFrame", Integer.toString(getStartFrame()));
/*  51: 55 */     out.setAttribute("endFrame", Integer.toString(getEndFrame()));
/*  52:    */     
/*  53: 57 */     out.appendChild(AnnotationUtils.makeListElement("labelList", 
/*  54: 58 */       getLabels(), doc));
/*  55: 59 */     return out;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public double getFPS()
/*  59:    */   {
/*  60: 63 */     return 30.0D;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public double getTimeFromStart()
/*  64:    */   {
/*  65: 67 */     return getStartFrame() / getFPS();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final String getUid()
/*  69:    */   {
/*  70: 77 */     return this.uid;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final void setUid(String argUid)
/*  74:    */   {
/*  75: 86 */     this.uid = argUid;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final int getStartFrame()
/*  79:    */   {
/*  80: 95 */     return this.startFrame;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final void setStartFrame(int argStartFrame)
/*  84:    */   {
/*  85:104 */     this.startFrame = argStartFrame;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final int getEndFrame()
/*  89:    */   {
/*  90:113 */     return this.endFrame;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public final void setEndFrame(int argEndFrame)
/*  94:    */   {
/*  95:122 */     this.endFrame = argEndFrame;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final Label[] getLabels()
/*  99:    */   {
/* 100:131 */     return this.labels;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final void setLabels(Label[] argLabels)
/* 104:    */   {
/* 105:140 */     this.labels = argLabels;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public final String getSubject()
/* 109:    */   {
/* 110:150 */     return this.subject;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final void setSubject(String argSubject)
/* 114:    */   {
/* 115:159 */     this.subject = argSubject;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String toString()
/* 119:    */   {
/* 120:167 */     int sbSize = 1000;
/* 121:168 */     String variableSeparator = "  ";
/* 122:169 */     StringBuffer sb = new StringBuffer(1000);
/* 123:    */     
/* 124:171 */     sb.append("uid=").append(this.uid);
/* 125:172 */     sb.append("  ");
/* 126:173 */     sb.append("subject=").append(this.subject);
/* 127:174 */     sb.append("  ");
/* 128:175 */     sb.append("startFrame=").append(this.startFrame);
/* 129:176 */     sb.append("  ");
/* 130:177 */     sb.append("endFrame=").append(this.endFrame);
/* 131:178 */     sb.append("  ");
/* 132:179 */     sb.append("labels=").append(this.labels);
/* 133:    */     
/* 134:181 */     return sb.toString();
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.Segment
 * JD-Core Version:    0.7.0.1
 */