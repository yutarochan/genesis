from cPickle import load

from jpype import *

startJVM(getDefaultJVMPath())

java = JPackage('java')



def readTrace(jsonFile):
    trace = []
    with open(jsonFile) as json:
        for line in json.read().split('\n'):
            if line.strip():
                trace.append(eval(line))
    return trace

def convertToJava(py):
    if isinstance(py, dict):
        m = java.util.HashMap()
        for key in py:
            m.put(key,convertToJava(py[key]))
        return m
    elif isinstance(py,list) or isinstance(py,tuple):
        l = java.util.ArrayList()
        for i in py:
            l.add(convertToJava(i))
        return l
    else:
        return py

def write(fname,jObj):    
    f = java.io.FileOutputStream( fname+".java.serialized" )
    buff = java.io.BufferedOutputStream(f )
    output = java.io.ObjectOutputStream(buff);

    output.writeObject(jObj)
    output.close()

def jsonToJava(fname):
    write(fname[:-4],convertToJava(readTrace(fname)))

if __name__ == "__main__":
    pl = load(open("attntrace.pickle"))
    jl = convertToJava(pl)
