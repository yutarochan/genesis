/*   1:    */ package adam;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Connections.NetWireError;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import javassist.CannotCompileException;
/*   9:    */ import javassist.ClassPool;
/*  10:    */ import javassist.CtClass;
/*  11:    */ import javassist.CtMethod;
/*  12:    */ import javassist.CtNewMethod;
/*  13:    */ import javassist.NotFoundException;
/*  14:    */ 
/*  15:    */ public class LibUtil
/*  16:    */ {
/*  17:    */   public static WiredBox magic(WiredBox in)
/*  18:    */   {
/*  19: 31 */     return in;
/*  20:    */   }
/*  21:    */   
/*  22: 33 */   private static boolean extMode = false;
/*  23:    */   
/*  24:    */   public static boolean isLib()
/*  25:    */   {
/*  26: 35 */     return extMode;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void setLibMode()
/*  30:    */   {
/*  31: 38 */     extMode = true;
/*  32:    */   }
/*  33:    */   
/*  34: 40 */   private static int ser = 0;
/*  35:    */   
/*  36:    */   public static synchronized String getClassName()
/*  37:    */   {
/*  38: 42 */     return "NonJavaWiredBoxConnector_" + ser++;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static WiredBox cppHelper(String[] methodNames, CppCollector c)
/*  42:    */   {
/*  43: 45 */     Object[] wiredBoxMethods = new Object[methodNames.length];
/*  44: 46 */     int i = 0;
/*  45: 47 */     String[] arrayOfString = methodNames;int j = methodNames.length;
/*  46: 47 */     for (int i = 0; i < j; i++)
/*  47:    */     {
/*  48: 47 */       String meth = arrayOfString[i];
/*  49: 48 */       wiredBoxMethods[i] = { meth };
/*  50: 49 */       i++;
/*  51:    */     }
/*  52: 51 */     return makeWiredBox(methodNames, wiredBoxMethods, methodNames, c);
/*  53:    */   }
/*  54:    */   
/*  55: 53 */   private static String NORMAL_METHOD = "public Object $METHOD(Object[] params){return this.collector.callNormalMethod(\"$METHOD\",(Object[])params[0]);}";
/*  56: 54 */   private static String CALL_OUT = "public void $HANDLER_NAME(Object blob){$CALL_LIST}";
/*  57: 55 */   private static String COLLECT_CALL = "this.collector.call(blob,\"$METHOD\");";
/*  58:    */   
/*  59:    */   public static WiredBox makeWiredBox(String[] inPorts, Object[] wiredBoxMethods, String[] normalMethods, Collector collector)
/*  60:    */   {
/*  61: 57 */     HashMap<String, String[]> portMeths = new HashMap();
/*  62: 58 */     for (int i = 0; i < inPorts.length; i++) {
/*  63: 59 */       portMeths.put(inPorts[i], (String[])wiredBoxMethods[i]);
/*  64:    */     }
/*  65: 61 */     String superClassName = Receiver.class.getName();
/*  66: 62 */     ClassPool pool = ClassPool.getDefault();
/*  67: 63 */     CtClass stubClass = pool.makeClass(getClassName());
/*  68: 64 */     stubClass.setInterfaces(new CtClass[] { pool.makeClass(WiredBox.class.getName()) });
/*  69:    */     try
/*  70:    */     {
/*  71: 67 */       stubClass.setSuperclass(pool.get(superClassName));
/*  72: 68 */       HashMap<String, String> portHandler = new HashMap();
/*  73: 69 */       int idx = 0;
/*  74: 70 */       String[] arrayOfString1 = inPorts;int j = inPorts.length;
/*  75: 70 */       for (int i = 0; i < j; i++)
/*  76:    */       {
/*  77: 70 */         String port = arrayOfString1[i];
/*  78: 71 */         String hname = "__generated__handle_" + idx++;
/*  79: 72 */         String callList = "";
/*  80: 73 */         for (String method : (String[])portMeths.get(port)) {
/*  81: 74 */           callList = callList + COLLECT_CALL.replace("$METHOD", method);
/*  82:    */         }
/*  83: 76 */         String callCode = CALL_OUT.replace("$CALL_LIST", callList).replace("$HANDLER_NAME", hname);
/*  84: 77 */         portHandler.put(port, hname);
/*  85: 78 */         stubClass.addMethod(CtNewMethod.make(callCode, stubClass));
/*  86:    */       }
/*  87: 80 */       for (String method : normalMethods)
/*  88:    */       {
/*  89: 81 */         m = CtNewMethod.make(NORMAL_METHOD.replace("$METHOD", method), stubClass);
/*  90:    */         
/*  91: 83 */         stubClass.addMethod(m);
/*  92:    */       }
/*  93: 85 */       Class<?> clazz = stubClass.toClass();
/*  94: 86 */       Receiver stub = (Receiver)clazz.newInstance();
/*  95: 87 */       stub.collector = collector;
/*  96: 88 */       CtMethod m = inPorts;int k = inPorts.length;
/*  97: 88 */       for (j = 0; j < k; j++)
/*  98:    */       {
/*  99: 88 */         String port = m[j];
/* 100: 89 */         Connections.getPorts(stub).addSignalProcessor(port, (String)portHandler.get(port));
/* 101:    */       }
/* 102: 91 */       return stub;
/* 103:    */     }
/* 104:    */     catch (CannotCompileException e)
/* 105:    */     {
/* 106: 93 */       throw new Connections.NetWireError(e);
/* 107:    */     }
/* 108:    */     catch (InstantiationException e)
/* 109:    */     {
/* 110: 95 */       throw new Connections.NetWireError(e);
/* 111:    */     }
/* 112:    */     catch (IllegalAccessException e)
/* 113:    */     {
/* 114: 97 */       throw new Connections.NetWireError(e);
/* 115:    */     }
/* 116:    */     catch (NotFoundException e)
/* 117:    */     {
/* 118: 99 */       throw new Connections.NetWireError(e);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:104 */   private static boolean genesisLoaded = false;
/* 123:    */   
/* 124:    */   public static void loadGenesisLib()
/* 125:    */   {
/* 126:106 */     if (!genesisLoaded)
/* 127:    */     {
/* 128:107 */       System.loadLibrary("Genesis");
/* 129:108 */       genesisLoaded = true;
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static Collector newCppCollector()
/* 134:    */   {
/* 135:113 */     loadGenesisLib();
/* 136:114 */     return new CppCollector();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static Collector castToCollector(CppCollector c)
/* 140:    */   {
/* 141:118 */     return c;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static WiredBox temporary(Collector foo)
/* 145:    */   {
/* 146:122 */     return makeWiredBox(new String[] { "input" }, new Object[] { { "someMethod" } }, new String[] { "someOtherMethod" }, foo);
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.LibUtil
 * JD-Core Version:    0.7.0.1
 */