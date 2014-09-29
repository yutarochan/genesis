/*   1:    */ package matthewFay;
/*   2:    */ 
/*   3:    */ import adam.RPCBox;
/*   4:    */ import connections.AbstractWiredBox;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Connections.NetWireException;
/*   7:    */ import connections.WiredBox;
/*   8:    */ import java.text.DateFormat;
/*   9:    */ import java.text.SimpleDateFormat;
/*  10:    */ import java.util.Calendar;
/*  11:    */ import java.util.Date;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class ImpactTest
/*  15:    */   extends AbstractWiredBox
/*  16:    */ {
/*  17:    */   public RPCBox rpcImpact;
/*  18:    */   
/*  19:    */   public ImpactTest()
/*  20:    */     throws Connections.NetWireException
/*  21:    */   {
/*  22: 17 */     setName("ImpactCommunicator");
/*  23:    */     try
/*  24:    */     {
/*  25: 19 */       WiredBox impact = Connections.subscribe("IMPACT");
/*  26: 20 */       this.rpcImpact = ((RPCBox)impact);
/*  27:    */     }
/*  28:    */     catch (Exception e)
/*  29:    */     {
/*  30: 22 */       this.rpcImpact = null;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34: 28 */   Date currentTime = new Date();
/*  35: 29 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
/*  36: 30 */   DateFormat df2 = new SimpleDateFormat("HH:mm");
/*  37:    */   
/*  38:    */   public void addTime(int offset)
/*  39:    */   {
/*  40: 33 */     Calendar cal = Calendar.getInstance();
/*  41: 34 */     cal.setTime(this.currentTime);
/*  42: 35 */     cal.add(12, offset);
/*  43: 36 */     this.currentTime = cal.getTime();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getTime(int offset)
/*  47:    */   {
/*  48: 40 */     Calendar cal = Calendar.getInstance();
/*  49: 41 */     cal.setTime(this.currentTime);
/*  50: 42 */     cal.add(12, offset);
/*  51: 43 */     return this.df.format(cal.getTime()) + "T" + this.df2.format(cal.getTime());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void main(String[] args)
/*  55:    */     throws Connections.NetWireException, InterruptedException
/*  56:    */   {
/*  57: 47 */     ImpactTest impact = new ImpactTest();
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:135 */     impact.reset();
/* 146:136 */     impact.meet("Agent-X", "Agent-Y", "Location-7-1");
/* 147:137 */     impact.addTime(10);
/* 148:138 */     impact.give("Agent-X", "IED-7", "Agent-Y");
/* 149:139 */     impact.addTime(10);
/* 150:140 */     impact.travel("Agent-X", "Location-7-1", "Location-7-2");
/* 151:141 */     impact.addTime(10);
/* 152:142 */     impact.meet("Agent-Y", "Agent-Z", "Location-7-2");
/* 153:143 */     impact.addTime(10);
/* 154:144 */     impact.give("Agent-Y", "IED-7", "Agent-Z");
/* 155:145 */     impact.addTime(10);
/* 156:146 */     impact.retrieve("Agent-Z", "Weapon-6", "Location-7-2");
/* 157:147 */     impact.travel("Agent-Z", "Loction-7-2", "Location-7-3");
/* 158:148 */     impact.not_be();
/* 159:    */     
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:160 */     Thread.sleep(10000L);
/* 171:    */   }
/* 172:    */   
/* 173:    */   private Object sendMessage(String message)
/* 174:    */   {
/* 175:174 */     String[] message_obj = new String[2];
/* 176:175 */     message_obj[0] = message;
/* 177:176 */     message_obj[1] = null;
/* 178:    */     
/* 179:178 */     Mark.say(new Object[] {"Sending Command:" });
/* 180:179 */     Mark.say(new Object[] {message });
/* 181:    */     
/* 182:181 */     Object o = null;
/* 183:182 */     if (this.rpcImpact != null) {
/* 184:183 */       o = this.rpcImpact.rpc("rpcMethod", message_obj);
/* 185:    */     } else {
/* 186:185 */       Mark.say(new Object[] {"Message not sent, not connected..." });
/* 187:    */     }
/* 188:188 */     Mark.say(new Object[] {o });
/* 189:    */     
/* 190:190 */     return o;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void reset()
/* 194:    */   {
/* 195:194 */     String message = "reset5(for: envisioning)!";
/* 196:195 */     sendMessage(message);
/* 197:196 */     this.currentTime = new Date();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void build(String subject, String object, String using)
/* 201:    */   {
/* 202:200 */     String mob = "build(subject:human \"" + subject + "\", object:IED \"" + object + "\",using:explosive_material \"" + using + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 203:201 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 204:202 */     sendMessage(mob);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void detonate(String subject, String object)
/* 208:    */   {
/* 209:206 */     String mob = "detonate(subject:human \"" + subject + "\", object:IED \"" + object + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 210:207 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 211:208 */     sendMessage(mob);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void give(String subject, String object, String to)
/* 215:    */   {
/* 216:212 */     String mob = "give(subject:human \"" + subject + "\", object:tangible_object \"" + object + "\",to:human \"" + to + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 217:213 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 218:214 */     sendMessage(mob);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void makePhoneCall(String subject)
/* 222:    */   {
/* 223:218 */     String mob = "make(subject:human \"" + subject + "\", object: phone_call(article: a),from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 224:219 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 225:220 */     sendMessage(mob);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void meet(String subject, String object, String location)
/* 229:    */   {
/* 230:224 */     String mob = "meet(subject:human \"" + subject + "\", object:human \"" + object + "\", at:location \"" + location + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 231:225 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 232:226 */     sendMessage(mob);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void place(String subject, String object, String location)
/* 236:    */   {
/* 237:230 */     String mob = "place(subject:human \"" + subject + "\", object:tangible_object \"" + object + "\",at:location \"" + location + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 238:231 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 239:232 */     sendMessage(mob);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void raiseAlert(String subject)
/* 243:    */   {
/* 244:236 */     String mob = "raise(subject:human \"Agent-15\", object: alert(article: a), from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 245:237 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 246:238 */     sendMessage(mob);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void retrieve(String subject, String object, String location)
/* 250:    */   {
/* 251:242 */     String mob = "retrieve(subject:human \"" + subject + "\", object:tangible_object \"" + object + "\",from:location \"" + location + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 252:243 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 253:244 */     sendMessage(mob);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void selfDetonate(String object)
/* 257:    */   {
/* 258:248 */     String mob = "self-detonate(subject:IED \"" + object + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 259:249 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 260:250 */     sendMessage(mob);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void threaten(String subject, String object)
/* 264:    */   {
/* 265:254 */     String mob = "threaten(subject:human \"" + subject + "\", object:human \"" + object + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 266:255 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 267:256 */     sendMessage(mob);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void travel(String subject, String origin, String location)
/* 271:    */   {
/* 272:260 */     String mob = "travel(subject:human \"" + subject + "\", from:location \"" + origin + "\", to:location \"" + location + "\", from:time \"" + getTime(0) + "\", to:time \"" + getTime(10) + "\").";
/* 273:261 */     mob = "envision5(object:expression [=(" + mob + ")])!";
/* 274:262 */     sendMessage(mob);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void not_be()
/* 278:    */   {
/* 279:266 */     String mob = "not_be(subject:expression [what], adjective: permitted)?";
/* 280:267 */     sendMessage(mob);
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.ImpactTest
 * JD-Core Version:    0.7.0.1
 */