/*   1:    */ package adam;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Connections.NetWireError;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import javassist.CannotCompileException;
/*  13:    */ import javassist.ClassPool;
/*  14:    */ import javassist.CtClass;
/*  15:    */ import javassist.CtNewMethod;
/*  16:    */ import javassist.LoaderClassPath;
/*  17:    */ import javassist.NotFoundException;
/*  18:    */ 
/*  19:    */ public class LocalCodeGenStubFactory
/*  20:    */   extends WiredBoxStubFactory
/*  21:    */ {
/*  22:    */   private static final String WIRE_METHOD_SRC = "public void $NAME(Object o){this.call(\"$BOX_ID\",\"$NAME\",new Object[]{o});}";
/*  23:    */   private static final String NAME_METHOD_SRC = "public java.lang.String getName(){return \"$BOX_ID\";}";
/*  24:    */   private static final String RPC_METHOD_SRC = "public Object rpc(String remoteMethodName,Object[] arguments){return this.rpc(\"$BOX_ID\",remoteMethodName,arguments);}";
/*  25: 49 */   private Map<String, String> classNames = new HashMap();
/*  26: 51 */   private long classIdCounter = 0L;
/*  27:    */   
/*  28:    */   public synchronized String getClassName(String uid)
/*  29:    */   {
/*  30: 57 */     String name = "GeneratedWiredBoxStub_" + getClassId();
/*  31: 58 */     this.classNames.put(uid, name);
/*  32: 59 */     return name;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected long getClassId()
/*  36:    */   {
/*  37: 63 */     return this.classIdCounter++;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public WiredBox getStub(String uid)
/*  41:    */   {
/*  42: 72 */     String[] mNames = WireClientEndpoint.getInstance().getRemoteMethods(uid);
/*  43: 73 */     String endpointClassName = WireClientEndpoint.class.getName();
/*  44: 74 */     ClassPool pool = ClassPool.getDefault();
/*  45:    */     
/*  46: 76 */     ClassLoader loader = WireClientEndpoint.PhoneHomeEndpoint.class.getClassLoader();
/*  47: 77 */     pool.appendClassPath(new LoaderClassPath(loader));
/*  48:    */     
/*  49: 79 */     CtClass stubClass = pool.makeClass(getClassName(uid));
/*  50: 80 */     stubClass.setInterfaces(new CtClass[] { pool.makeClass(WiredBox.class.getName()), 
/*  51: 81 */       pool.makeClass(RPCBox.class.getName()) });
/*  52: 82 */     Map<String, List<String>> portMapping = WireClientEndpoint.getInstance().getPortMapping(uid);
/*  53:    */     try
/*  54:    */     {
/*  55: 85 */       stubClass.setSuperclass(pool.get(WireClientEndpoint.PhoneHomeEndpoint.class.getName()));
/*  56: 86 */       for (String mName : mNames) {
/*  57: 87 */         stubClass.addMethod(CtNewMethod.make(
/*  58: 88 */           "public void $NAME(Object o){this.call(\"$BOX_ID\",\"$NAME\",new Object[]{o});}".replace("$NAME", mName)
/*  59: 89 */           .replace("$ENDPOINT_CLASS_NAME", endpointClassName)
/*  60: 90 */           .replace("$BOX_ID", uid), 
/*  61: 91 */           stubClass));
/*  62:    */       }
/*  63: 93 */       stubClass.addMethod(CtNewMethod.make(
/*  64: 94 */         "public Object rpc(String remoteMethodName,Object[] arguments){return this.rpc(\"$BOX_ID\",remoteMethodName,arguments);}"
/*  65: 95 */         .replace("$BOX_ID", uid), 
/*  66: 96 */         stubClass));
/*  67: 97 */       stubClass.addMethod(CtNewMethod.make("public java.lang.String getName(){return \"$BOX_ID\";}".replace("$BOX_ID", uid), stubClass));
/*  68: 98 */       Class<?> clazz = stubClass.toClass();
/*  69: 99 */       WiredBox stub = (WiredBox)clazz.newInstance();
/*  70:100 */       for (String r : mNames) {
/*  71:101 */         Connections.getPorts(stub).addSignalProcessor(r, r);
/*  72:    */       }
/*  73:103 */       for (Iterator localIterator = portMapping.keySet().iterator(); localIterator.hasNext(); ((Iterator)???).hasNext())
/*  74:    */       {
/*  75:103 */         String port = (String)localIterator.next();
/*  76:104 */         ??? = ((List)portMapping.get(port)).iterator(); continue;String meth = (String)((Iterator)???).next();
/*  77:105 */         Connections.getPorts(stub).addSignalProcessor(port, meth);
/*  78:    */       }
/*  79:109 */       return stub;
/*  80:    */     }
/*  81:    */     catch (CannotCompileException e)
/*  82:    */     {
/*  83:111 */       throw new Connections.NetWireError(e);
/*  84:    */     }
/*  85:    */     catch (InstantiationException e)
/*  86:    */     {
/*  87:113 */       throw new Connections.NetWireError(e);
/*  88:    */     }
/*  89:    */     catch (IllegalAccessException e)
/*  90:    */     {
/*  91:115 */       throw new Connections.NetWireError(e);
/*  92:    */     }
/*  93:    */     catch (NotFoundException e)
/*  94:    */     {
/*  95:117 */       throw new Connections.NetWireError(e);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void reset() {}
/* 100:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.LocalCodeGenStubFactory
 * JD-Core Version:    0.7.0.1
 */