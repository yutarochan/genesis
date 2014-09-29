/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Set;
/*   6:    */ 
/*   7:    */ public class KeywordAngles
/*   8:    */ {
/*   9:    */   public HashMap<String, HashMap<String, Double>> keyword_angles;
/*  10:    */   
/*  11:    */   public KeywordAngles()
/*  12:    */   {
/*  13: 10 */     this.keyword_angles = new HashMap();
/*  14: 11 */     build_hashmap();
/*  15:    */   }
/*  16:    */   
/*  17:    */   private void build_hashmap()
/*  18:    */   {
/*  19: 15 */     this.keyword_angles.put("American revolution", new HashMap());
/*  20: 16 */     this.keyword_angles.put("Afghanistan-civil-war", new HashMap());
/*  21: 17 */     this.keyword_angles.put("American civil war", new HashMap());
/*  22: 18 */     this.keyword_angles.put("Cambodia-vietnam invasion", new HashMap());
/*  23: 19 */     this.keyword_angles.put("Chad-libyan war", new HashMap());
/*  24: 20 */     this.keyword_angles.put("China border war with india", new HashMap());
/*  25: 21 */     this.keyword_angles.put("China border war with ussr", new HashMap());
/*  26: 22 */     this.keyword_angles.put("China invasion of tibet", new HashMap());
/*  27: 23 */     this.keyword_angles.put("China war with vietnam", new HashMap());
/*  28: 24 */     this.keyword_angles.put("Congo civil conflict", new HashMap());
/*  29: 25 */     this.keyword_angles.put("Cuba bay of pigs invasion", new HashMap());
/*  30: 26 */     this.keyword_angles.put("Czechoslovakia-soviet invasion", new HashMap());
/*  31: 27 */     this.keyword_angles.put("Nigerian civil war", new HashMap());
/*  32: 28 */     this.keyword_angles.put("Persian gulf war", new HashMap());
/*  33: 29 */     this.keyword_angles.put("Romania and ceausescu", new HashMap());
/*  34:    */     
/*  35: 31 */     ((HashMap)this.keyword_angles.get("American revolution")).put("American revolution", Double.valueOf(1.0D));
/*  36: 32 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Afghanistan-civil-war", Double.valueOf(0.172794612276D));
/*  37: 33 */     ((HashMap)this.keyword_angles.get("American revolution")).put("American civil war", Double.valueOf(0.1116669067D));
/*  38: 34 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Cambodia-vietnam invasion", Double.valueOf(0.191406578279D));
/*  39: 35 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Chad-libyan war", Double.valueOf(0.213180582455D));
/*  40: 36 */     ((HashMap)this.keyword_angles.get("American revolution")).put("China border war with india", Double.valueOf(0.335457687903D));
/*  41: 37 */     ((HashMap)this.keyword_angles.get("American revolution")).put("China border war with ussr", Double.valueOf(0.158287205481D));
/*  42: 38 */     ((HashMap)this.keyword_angles.get("American revolution")).put("China invasion of tibet", Double.valueOf(0.248366929784D));
/*  43: 39 */     ((HashMap)this.keyword_angles.get("American revolution")).put("China war with vietnam", Double.valueOf(0.154849146565D));
/*  44: 40 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Congo civil conflict", Double.valueOf(0.125269006586D));
/*  45: 41 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Cuba bay of pigs invasion", Double.valueOf(0.147691799313D));
/*  46: 42 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.159863845458D));
/*  47: 43 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Nigerian civil war", Double.valueOf(0.145744449013D));
/*  48: 44 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Persian gulf war", Double.valueOf(0.163269245072D));
/*  49: 45 */     ((HashMap)this.keyword_angles.get("American revolution")).put("Romania and ceausescu", Double.valueOf(0.109633378265D));
/*  50: 46 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("American revolution", Double.valueOf(0.172794612276D));
/*  51: 47 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Afghanistan-civil-war", Double.valueOf(1.0D));
/*  52: 48 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("American civil war", Double.valueOf(0.498013580635D));
/*  53: 49 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Cambodia-vietnam invasion", Double.valueOf(0.302750097D));
/*  54: 50 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Chad-libyan war", Double.valueOf(0.358713087689D));
/*  55: 51 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("China border war with india", Double.valueOf(0.210850889188D));
/*  56: 52 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("China border war with ussr", Double.valueOf(0.383537542937D));
/*  57: 53 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("China invasion of tibet", Double.valueOf(0.248011650227D));
/*  58: 54 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("China war with vietnam", Double.valueOf(0.241512526075D));
/*  59: 55 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Congo civil conflict", Double.valueOf(0.436376747218D));
/*  60: 56 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Cuba bay of pigs invasion", Double.valueOf(0.460495132631D));
/*  61: 57 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.289801034014D));
/*  62: 58 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Nigerian civil war", Double.valueOf(0.468408716075D));
/*  63: 59 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Persian gulf war", Double.valueOf(0.257438041821D));
/*  64: 60 */     ((HashMap)this.keyword_angles.get("Afghanistan-civil-war")).put("Romania and ceausescu", Double.valueOf(0.199095999518D));
/*  65: 61 */     ((HashMap)this.keyword_angles.get("American civil war")).put("American revolution", Double.valueOf(0.1116669067D));
/*  66: 62 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Afghanistan-civil-war", Double.valueOf(0.498013580635D));
/*  67: 63 */     ((HashMap)this.keyword_angles.get("American civil war")).put("American civil war", Double.valueOf(1.0D));
/*  68: 64 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Cambodia-vietnam invasion", Double.valueOf(0.288467976237D));
/*  69: 65 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Chad-libyan war", Double.valueOf(0.384790808738D));
/*  70: 66 */     ((HashMap)this.keyword_angles.get("American civil war")).put("China border war with india", Double.valueOf(0.144712516405D));
/*  71: 67 */     ((HashMap)this.keyword_angles.get("American civil war")).put("China border war with ussr", Double.valueOf(0.401507010557D));
/*  72: 68 */     ((HashMap)this.keyword_angles.get("American civil war")).put("China invasion of tibet", Double.valueOf(0.256559695392D));
/*  73: 69 */     ((HashMap)this.keyword_angles.get("American civil war")).put("China war with vietnam", Double.valueOf(0.229395024834D));
/*  74: 70 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Congo civil conflict", Double.valueOf(0.500265756552D));
/*  75: 71 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Cuba bay of pigs invasion", Double.valueOf(0.465362321366D));
/*  76: 72 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.235391446014D));
/*  77: 73 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Nigerian civil war", Double.valueOf(0.527561645167D));
/*  78: 74 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Persian gulf war", Double.valueOf(0.263051924643D));
/*  79: 75 */     ((HashMap)this.keyword_angles.get("American civil war")).put("Romania and ceausescu", Double.valueOf(0.137958718708D));
/*  80: 76 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("American revolution", Double.valueOf(0.191406578279D));
/*  81: 77 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Afghanistan-civil-war", Double.valueOf(0.302750097D));
/*  82: 78 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("American civil war", Double.valueOf(0.288467976237D));
/*  83: 79 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Cambodia-vietnam invasion", Double.valueOf(1.0D));
/*  84: 80 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Chad-libyan war", Double.valueOf(0.272979869491D));
/*  85: 81 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("China border war with india", Double.valueOf(0.285256999414D));
/*  86: 82 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("China border war with ussr", Double.valueOf(0.35325684008D));
/*  87: 83 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("China invasion of tibet", Double.valueOf(0.30500206321D));
/*  88: 84 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("China war with vietnam", Double.valueOf(0.809553394152D));
/*  89: 85 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Congo civil conflict", Double.valueOf(0.274285805676D));
/*  90: 86 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Cuba bay of pigs invasion", Double.valueOf(0.272606597797D));
/*  91: 87 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.24837813502D));
/*  92: 88 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Nigerian civil war", Double.valueOf(0.299121970036D));
/*  93: 89 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Persian gulf war", Double.valueOf(0.232947453053D));
/*  94: 90 */     ((HashMap)this.keyword_angles.get("Cambodia-vietnam invasion")).put("Romania and ceausescu", Double.valueOf(0.139503703324D));
/*  95: 91 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("American revolution", Double.valueOf(0.213180582455D));
/*  96: 92 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Afghanistan-civil-war", Double.valueOf(0.358713087689D));
/*  97: 93 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("American civil war", Double.valueOf(0.384790808738D));
/*  98: 94 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Cambodia-vietnam invasion", Double.valueOf(0.272979869491D));
/*  99: 95 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Chad-libyan war", Double.valueOf(1.0D));
/* 100: 96 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("China border war with india", Double.valueOf(0.259985099947D));
/* 101: 97 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("China border war with ussr", Double.valueOf(0.324063235535D));
/* 102: 98 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("China invasion of tibet", Double.valueOf(0.211220986558D));
/* 103: 99 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("China war with vietnam", Double.valueOf(0.223084422501D));
/* 104:100 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Congo civil conflict", Double.valueOf(0.359030661162D));
/* 105:101 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Cuba bay of pigs invasion", Double.valueOf(0.336306514639D));
/* 106:102 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.223371559059D));
/* 107:103 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Nigerian civil war", Double.valueOf(0.400795866552D));
/* 108:104 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Persian gulf war", Double.valueOf(0.246428351744D));
/* 109:105 */     ((HashMap)this.keyword_angles.get("Chad-libyan war")).put("Romania and ceausescu", Double.valueOf(0.179448104876D));
/* 110:106 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("American revolution", Double.valueOf(0.335457687903D));
/* 111:107 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Afghanistan-civil-war", Double.valueOf(0.210850889188D));
/* 112:108 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("American civil war", Double.valueOf(0.144712516405D));
/* 113:109 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Cambodia-vietnam invasion", Double.valueOf(0.285256999414D));
/* 114:110 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Chad-libyan war", Double.valueOf(0.259985099947D));
/* 115:111 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("China border war with india", Double.valueOf(1.0D));
/* 116:112 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("China border war with ussr", Double.valueOf(0.333048726753D));
/* 117:113 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("China invasion of tibet", Double.valueOf(0.457468007396D));
/* 118:114 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("China war with vietnam", Double.valueOf(0.309606194537D));
/* 119:115 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Congo civil conflict", Double.valueOf(0.180366896512D));
/* 120:116 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Cuba bay of pigs invasion", Double.valueOf(0.179395457691D));
/* 121:117 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.225839479831D));
/* 122:118 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Nigerian civil war", Double.valueOf(0.200802262319D));
/* 123:119 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Persian gulf war", Double.valueOf(0.237952821383D));
/* 124:120 */     ((HashMap)this.keyword_angles.get("China border war with india")).put("Romania and ceausescu", Double.valueOf(0.175816353208D));
/* 125:121 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("American revolution", Double.valueOf(0.158287205481D));
/* 126:122 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Afghanistan-civil-war", Double.valueOf(0.383537542937D));
/* 127:123 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("American civil war", Double.valueOf(0.401507010557D));
/* 128:124 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Cambodia-vietnam invasion", Double.valueOf(0.35325684008D));
/* 129:125 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Chad-libyan war", Double.valueOf(0.324063235535D));
/* 130:126 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("China border war with india", Double.valueOf(0.333048726753D));
/* 131:127 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("China border war with ussr", Double.valueOf(1.0D));
/* 132:128 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("China invasion of tibet", Double.valueOf(0.424998027152D));
/* 133:129 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("China war with vietnam", Double.valueOf(0.367263546248D));
/* 134:130 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Congo civil conflict", Double.valueOf(0.36454132524D));
/* 135:131 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Cuba bay of pigs invasion", Double.valueOf(0.334935062814D));
/* 136:132 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.465403369608D));
/* 137:133 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Nigerian civil war", Double.valueOf(0.386449518709D));
/* 138:134 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Persian gulf war", Double.valueOf(0.232596056414D));
/* 139:135 */     ((HashMap)this.keyword_angles.get("China border war with ussr")).put("Romania and ceausescu", Double.valueOf(0.158548869577D));
/* 140:136 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("American revolution", Double.valueOf(0.248366929784D));
/* 141:137 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Afghanistan-civil-war", Double.valueOf(0.248011650227D));
/* 142:138 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("American civil war", Double.valueOf(0.256559695392D));
/* 143:139 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Cambodia-vietnam invasion", Double.valueOf(0.30500206321D));
/* 144:140 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Chad-libyan war", Double.valueOf(0.211220986558D));
/* 145:141 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("China border war with india", Double.valueOf(0.457468007396D));
/* 146:142 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("China border war with ussr", Double.valueOf(0.424998027152D));
/* 147:143 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("China invasion of tibet", Double.valueOf(1.0D));
/* 148:144 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("China war with vietnam", Double.valueOf(0.350039304776D));
/* 149:145 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Congo civil conflict", Double.valueOf(0.215324186616D));
/* 150:146 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Cuba bay of pigs invasion", Double.valueOf(0.223981386811D));
/* 151:147 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.197404914612D));
/* 152:148 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Nigerian civil war", Double.valueOf(0.232130214999D));
/* 153:149 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Persian gulf war", Double.valueOf(0.236358916796D));
/* 154:150 */     ((HashMap)this.keyword_angles.get("China invasion of tibet")).put("Romania and ceausescu", Double.valueOf(0.12583762895D));
/* 155:151 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("American revolution", Double.valueOf(0.154849146565D));
/* 156:152 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Afghanistan-civil-war", Double.valueOf(0.241512526075D));
/* 157:153 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("American civil war", Double.valueOf(0.229395024834D));
/* 158:154 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Cambodia-vietnam invasion", Double.valueOf(0.809553394152D));
/* 159:155 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Chad-libyan war", Double.valueOf(0.223084422501D));
/* 160:156 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("China border war with india", Double.valueOf(0.309606194537D));
/* 161:157 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("China border war with ussr", Double.valueOf(0.367263546248D));
/* 162:158 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("China invasion of tibet", Double.valueOf(0.350039304776D));
/* 163:159 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("China war with vietnam", Double.valueOf(1.0D));
/* 164:160 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Congo civil conflict", Double.valueOf(0.229550660526D));
/* 165:161 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Cuba bay of pigs invasion", Double.valueOf(0.206091277934D));
/* 166:162 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.18375672397D));
/* 167:163 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Nigerian civil war", Double.valueOf(0.233704897965D));
/* 168:164 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Persian gulf war", Double.valueOf(0.21208435211D));
/* 169:165 */     ((HashMap)this.keyword_angles.get("China war with vietnam")).put("Romania and ceausescu", Double.valueOf(0.139904261754D));
/* 170:166 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("American revolution", Double.valueOf(0.125269006586D));
/* 171:167 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Afghanistan-civil-war", Double.valueOf(0.436376747218D));
/* 172:168 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("American civil war", Double.valueOf(0.500265756552D));
/* 173:169 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Cambodia-vietnam invasion", Double.valueOf(0.274285805676D));
/* 174:170 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Chad-libyan war", Double.valueOf(0.359030661162D));
/* 175:171 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("China border war with india", Double.valueOf(0.180366896512D));
/* 176:172 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("China border war with ussr", Double.valueOf(0.36454132524D));
/* 177:173 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("China invasion of tibet", Double.valueOf(0.215324186616D));
/* 178:174 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("China war with vietnam", Double.valueOf(0.229550660526D));
/* 179:175 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Congo civil conflict", Double.valueOf(1.0D));
/* 180:176 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Cuba bay of pigs invasion", Double.valueOf(0.411300070359D));
/* 181:177 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.239084783857D));
/* 182:178 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Nigerian civil war", Double.valueOf(0.45375568502D));
/* 183:179 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Persian gulf war", Double.valueOf(0.261973775624D));
/* 184:180 */     ((HashMap)this.keyword_angles.get("Congo civil conflict")).put("Romania and ceausescu", Double.valueOf(0.160563013881D));
/* 185:181 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("American revolution", Double.valueOf(0.147691799313D));
/* 186:182 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Afghanistan-civil-war", Double.valueOf(0.460495132631D));
/* 187:183 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("American civil war", Double.valueOf(0.465362321366D));
/* 188:184 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Cambodia-vietnam invasion", Double.valueOf(0.272606597797D));
/* 189:185 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Chad-libyan war", Double.valueOf(0.336306514639D));
/* 190:186 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("China border war with india", Double.valueOf(0.179395457691D));
/* 191:187 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("China border war with ussr", Double.valueOf(0.334935062814D));
/* 192:188 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("China invasion of tibet", Double.valueOf(0.223981386811D));
/* 193:189 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("China war with vietnam", Double.valueOf(0.206091277934D));
/* 194:190 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Congo civil conflict", Double.valueOf(0.411300070359D));
/* 195:191 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Cuba bay of pigs invasion", Double.valueOf(1.0D));
/* 196:192 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.248173986286D));
/* 197:193 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Nigerian civil war", Double.valueOf(0.438931077061D));
/* 198:194 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Persian gulf war", Double.valueOf(0.322781296309D));
/* 199:195 */     ((HashMap)this.keyword_angles.get("Cuba bay of pigs invasion")).put("Romania and ceausescu", Double.valueOf(0.139939410436D));
/* 200:196 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("American revolution", Double.valueOf(0.159863845458D));
/* 201:197 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Afghanistan-civil-war", Double.valueOf(0.289801034014D));
/* 202:198 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("American civil war", Double.valueOf(0.235391446014D));
/* 203:199 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Cambodia-vietnam invasion", Double.valueOf(0.24837813502D));
/* 204:200 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Chad-libyan war", Double.valueOf(0.223371559059D));
/* 205:201 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("China border war with india", Double.valueOf(0.225839479831D));
/* 206:202 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("China border war with ussr", Double.valueOf(0.465403369608D));
/* 207:203 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("China invasion of tibet", Double.valueOf(0.197404914612D));
/* 208:204 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("China war with vietnam", Double.valueOf(0.18375672397D));
/* 209:205 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Congo civil conflict", Double.valueOf(0.239084783857D));
/* 210:206 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Cuba bay of pigs invasion", Double.valueOf(0.248173986286D));
/* 211:207 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Czechoslovakia-soviet invasion", Double.valueOf(1.0D));
/* 212:208 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Nigerian civil war", Double.valueOf(0.2649825758D));
/* 213:209 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Persian gulf war", Double.valueOf(0.205141724583D));
/* 214:210 */     ((HashMap)this.keyword_angles.get("Czechoslovakia-soviet invasion")).put("Romania and ceausescu", Double.valueOf(0.180916678307D));
/* 215:211 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("American revolution", Double.valueOf(0.145744449013D));
/* 216:212 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Afghanistan-civil-war", Double.valueOf(0.468408716075D));
/* 217:213 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("American civil war", Double.valueOf(0.527561645167D));
/* 218:214 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Cambodia-vietnam invasion", Double.valueOf(0.299121970036D));
/* 219:215 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Chad-libyan war", Double.valueOf(0.400795866552D));
/* 220:216 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("China border war with india", Double.valueOf(0.200802262319D));
/* 221:217 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("China border war with ussr", Double.valueOf(0.386449518709D));
/* 222:218 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("China invasion of tibet", Double.valueOf(0.232130214999D));
/* 223:219 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("China war with vietnam", Double.valueOf(0.233704897965D));
/* 224:220 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Congo civil conflict", Double.valueOf(0.45375568502D));
/* 225:221 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Cuba bay of pigs invasion", Double.valueOf(0.438931077061D));
/* 226:222 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.2649825758D));
/* 227:223 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Nigerian civil war", Double.valueOf(1.0D));
/* 228:224 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Persian gulf war", Double.valueOf(0.264083858987D));
/* 229:225 */     ((HashMap)this.keyword_angles.get("Nigerian civil war")).put("Romania and ceausescu", Double.valueOf(0.179280289653D));
/* 230:226 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("American revolution", Double.valueOf(0.163269245072D));
/* 231:227 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Afghanistan-civil-war", Double.valueOf(0.257438041821D));
/* 232:228 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("American civil war", Double.valueOf(0.263051924643D));
/* 233:229 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Cambodia-vietnam invasion", Double.valueOf(0.232947453053D));
/* 234:230 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Chad-libyan war", Double.valueOf(0.246428351744D));
/* 235:231 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("China border war with india", Double.valueOf(0.237952821383D));
/* 236:232 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("China border war with ussr", Double.valueOf(0.232596056414D));
/* 237:233 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("China invasion of tibet", Double.valueOf(0.236358916796D));
/* 238:234 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("China war with vietnam", Double.valueOf(0.21208435211D));
/* 239:235 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Congo civil conflict", Double.valueOf(0.261973775624D));
/* 240:236 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Cuba bay of pigs invasion", Double.valueOf(0.322781296309D));
/* 241:237 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.205141724583D));
/* 242:238 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Nigerian civil war", Double.valueOf(0.264083858987D));
/* 243:239 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Persian gulf war", Double.valueOf(1.0D));
/* 244:240 */     ((HashMap)this.keyword_angles.get("Persian gulf war")).put("Romania and ceausescu", Double.valueOf(0.141148065859D));
/* 245:241 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("American revolution", Double.valueOf(0.109633378265D));
/* 246:242 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Afghanistan-civil-war", Double.valueOf(0.199095999518D));
/* 247:243 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("American civil war", Double.valueOf(0.137958718708D));
/* 248:244 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Cambodia-vietnam invasion", Double.valueOf(0.139503703324D));
/* 249:245 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Chad-libyan war", Double.valueOf(0.179448104876D));
/* 250:246 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("China border war with india", Double.valueOf(0.175816353208D));
/* 251:247 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("China border war with ussr", Double.valueOf(0.158548869577D));
/* 252:248 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("China invasion of tibet", Double.valueOf(0.12583762895D));
/* 253:249 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("China war with vietnam", Double.valueOf(0.139904261754D));
/* 254:250 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Congo civil conflict", Double.valueOf(0.160563013881D));
/* 255:251 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Cuba bay of pigs invasion", Double.valueOf(0.139939410436D));
/* 256:252 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Czechoslovakia-soviet invasion", Double.valueOf(0.180916678307D));
/* 257:253 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Nigerian civil war", Double.valueOf(0.179280289653D));
/* 258:254 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Persian gulf war", Double.valueOf(0.141148065859D));
/* 259:255 */     ((HashMap)this.keyword_angles.get("Romania and ceausescu")).put("Romania and ceausescu", Double.valueOf(1.0D));
/* 260:    */   }
/* 261:    */   
/* 262:    */   public double getMaxKeywordAngle()
/* 263:    */   {
/* 264:259 */     if (this.keyword_angles == null) {
/* 265:260 */       build_hashmap();
/* 266:    */     }
/* 267:263 */     double max = 0.0D;
/* 268:    */     Iterator localIterator2;
/* 269:264 */     for (Iterator localIterator1 = this.keyword_angles.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 270:    */     {
/* 271:264 */       String story = (String)localIterator1.next();
/* 272:265 */       localIterator2 = ((HashMap)this.keyword_angles.get(story)).keySet().iterator(); continue;String story2 = (String)localIterator2.next();
/* 273:266 */       double value = ((Double)((HashMap)this.keyword_angles.get(story)).get(story2)).doubleValue();
/* 274:267 */       if (value > max) {
/* 275:268 */         max = value;
/* 276:    */       }
/* 277:    */     }
/* 278:272 */     return max;
/* 279:    */   }
/* 280:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.KeywordAngles
 * JD-Core Version:    0.7.0.1
 */