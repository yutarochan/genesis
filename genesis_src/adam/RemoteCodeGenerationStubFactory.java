/*   1:    */ package adam;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Connections.NetWireException;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.concurrent.ConcurrentHashMap;
/*  13:    */ 
/*  14:    */ public class RemoteCodeGenerationStubFactory
/*  15:    */   extends WiredBoxStubFactory
/*  16:    */ {
/*  17: 23 */   private Map<String, WiredBox> stubs = new ConcurrentHashMap();
/*  18: 24 */   private RemoteCodeClassLoader remoteCodeClassLoader = new RemoteCodeClassLoader();
/*  19:    */   
/*  20:    */   public WiredBox getStub(String uid)
/*  21:    */     throws Connections.NetWireException
/*  22:    */   {
/*  23: 28 */     if (this.stubs.containsKey(uid)) {
/*  24: 29 */       return (WiredBox)this.stubs.get(uid);
/*  25:    */     }
/*  26: 31 */     synchronized (WireClientEndpoint.getInstance())
/*  27:    */     {
/*  28: 35 */       String name = WireClientEndpoint.getInstance().getRemoteStubClassName(uid);
/*  29: 36 */       byte[] bytecode = WireClientEndpoint.getInstance().getRemoteStub(uid);
/*  30: 37 */       String[] mNames = WireClientEndpoint.getInstance().getRemoteMethods(uid);
/*  31: 38 */       Map<String, List<String>> portMapping = WireClientEndpoint.getInstance().getPortMapping(uid);
/*  32: 39 */       this.remoteCodeClassLoader.addClassImpl(name, bytecode);
/*  33:    */       try
/*  34:    */       {
/*  35: 42 */         Class<?> clazz = this.remoteCodeClassLoader.loadClass(name);
/*  36: 43 */         WiredBox stub = (WiredBox)clazz.newInstance();
/*  37: 44 */         for (String r : mNames) {
/*  38: 45 */           Connections.getPorts(stub).addSignalProcessor(r, r);
/*  39:    */         }
/*  40: 47 */         for (Iterator localIterator = portMapping.keySet().iterator(); localIterator.hasNext(); ((Iterator)???).hasNext())
/*  41:    */         {
/*  42: 47 */           String port = (String)localIterator.next();
/*  43: 48 */           ??? = ((List)portMapping.get(port)).iterator(); continue;String meth = (String)((Iterator)???).next();
/*  44: 49 */           Connections.getPorts(stub).addSignalProcessor(port, meth);
/*  45:    */         }
/*  46: 52 */         this.stubs.put(uid, stub);
/*  47: 53 */         return stub;
/*  48:    */       }
/*  49:    */       catch (ClassFormatError e)
/*  50:    */       {
/*  51: 55 */         throw new Connections.NetWireException(e);
/*  52:    */       }
/*  53:    */       catch (ClassNotFoundException e)
/*  54:    */       {
/*  55: 57 */         throw new Connections.NetWireException(e);
/*  56:    */       }
/*  57:    */       catch (InstantiationException e)
/*  58:    */       {
/*  59: 59 */         throw new Connections.NetWireException(e);
/*  60:    */       }
/*  61:    */       catch (IllegalAccessException e)
/*  62:    */       {
/*  63: 61 */         throw new Connections.NetWireException(e);
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void reset()
/*  69:    */   {
/*  70: 68 */     this.stubs = new ConcurrentHashMap();
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static class RemoteCodeClassLoader
/*  74:    */     extends ClassLoader
/*  75:    */   {
/*  76: 72 */     private Map<String, Class<?>> classes = new HashMap();
/*  77: 73 */     private Map<String, byte[]> bytecodes = new HashMap();
/*  78:    */     
/*  79:    */     private byte[] getClassImpl(String className)
/*  80:    */     {
/*  81: 77 */       return (byte[])this.bytecodes.get(className);
/*  82:    */     }
/*  83:    */     
/*  84:    */     public void addClassImpl(String name, byte[] bytecode)
/*  85:    */     {
/*  86: 80 */       if (!this.bytecodes.containsKey(name)) {
/*  87: 81 */         this.bytecodes.put(name, bytecode);
/*  88:    */       }
/*  89:    */     }
/*  90:    */     
/*  91:    */     public Class<?> loadClass(String className)
/*  92:    */       throws ClassNotFoundException
/*  93:    */     {
/*  94: 85 */       return loadClass(className, true);
/*  95:    */     }
/*  96:    */     
/*  97:    */     public synchronized Class<?> loadClass(String className, boolean resolveIt)
/*  98:    */       throws ClassNotFoundException
/*  99:    */     {
/* 100: 91 */       Class<?> result = (Class)this.classes.get(className);
/* 101: 92 */       if (result != null) {
/* 102: 92 */         return result;
/* 103:    */       }
/* 104:    */       try
/* 105:    */       {
/* 106: 94 */         result = super.findSystemClass(className);
/* 107: 95 */         if (result != null) {
/* 108: 95 */           return result;
/* 109:    */         }
/* 110:    */       }
/* 111:    */       catch (ClassNotFoundException localClassNotFoundException)
/* 112:    */       {
/* 113: 99 */         byte[] classData = getClassImpl(className);
/* 114:100 */         if (classData == null) {
/* 115:101 */           throw new ClassNotFoundException("WiredBox remote proxy class loader could not find the class bytecode!!!");
/* 116:    */         }
/* 117:103 */         result = defineClass(className, classData, 0, classData.length);
/* 118:104 */         if (result == null) {
/* 119:105 */           throw new ClassFormatError("bad bytecode for class " + className + " in WiredBox classloader");
/* 120:    */         }
/* 121:107 */         if (resolveIt) {
/* 122:108 */           resolveClass(result);
/* 123:    */         }
/* 124:110 */         this.classes.put(className, result);
/* 125:    */       }
/* 126:111 */       return result;
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.RemoteCodeGenerationStubFactory
 * JD-Core Version:    0.7.0.1
 */