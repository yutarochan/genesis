#include "adamKraft_videoUtils_MovieReader.h"
#include <cv.h>
#include <highgui.h>
#include <iostream>
#include <string>
#include <map>

using namespace std;

static map<jint,cv::VideoCapture*> capMap;

#ifdef __cplusplus
extern "C" {
#endif

//for the civilized world of operating systems ending in x
//g++  -o ../opt/libmovie.so -shared -Wl,-soname,libmovie.so -I/usr/lib/jvm/java-6-openjdk/jre/../include -I/usr/lib/jvm/java-6-openjdk/jre/../include/linux `pkg-config opencv --cflags --libs` -fPIC -shared -lc *.cpp

//for the gimmicky pile of crap called windows:
//C:\Documents and Settings\Administrator\workspace2\Gauntlet\source\adamKraft\videoUtils\c_src>cl -I"C:\Program Files\Java\jdk1.6.0_23\include" -I"C:\Program Files\Java\jdk1.6.0_23\include\win32" -I"C:\OpenCV2.2\include\opencv" -I"C:\OpenCV2.2\include" -LD nativeLib.cpp -Femovielib.dll -link C:\OpenCV2.2\lib\opencv_core220d.lib C:\OpenCV2.2\lib\opencv_ffmpeg220d.lib C:\OpenCV2.2\lib\opencv_highgui220d.lib C:\OpenCV2.2\lib\opencv_imgproc220d.lib C:\OpenCV2.2\lib\opencv_ml220d.lib
//god it seriously took me like 16 hours to make it work on windows and less than 3 on linux

JNIEXPORT jbyteArray JNICALL Java_adamKraft_videoUtils_MovieReader_nativeGetNextFrame
  (JNIEnv *env, jobject jobj, jint id){
	jbyteArray jb;
	jclass cls;
	cls=env->GetObjectClass(jobj);
	jfieldID heightID;
	jfieldID widthID;
	jfieldID channelsID;
	jfieldID errorID;
	jfieldID pyrDownID;
	heightID=env->GetFieldID(cls, "height",  "I");
	widthID=env->GetFieldID(cls, "width",  "I");
	channelsID=env->GetFieldID(cls, "channels",  "I");
	errorID=env->GetFieldID(cls,"error","Z");
	pyrDownID=env->GetFieldID(cls, "pyrDown",  "I");
	try{
	  cv::Mat image;
	  capMap[id]->retrieve(image);
	  jint pyrDown = env->GetIntField(jobj,pyrDownID);
	  for(int i=0;i<pyrDown;i++){
	    cv::Mat tmp;
	    cv::pyrDown(image,tmp);
	    image=tmp;
	  }
	  env->SetIntField(jobj,heightID,image.size().height);
	  env->SetIntField(jobj,widthID,image.size().width);
	  env->SetIntField(jobj,channelsID,image.channels());
	  int len = image.size().height*image.size().width*image.channels();
	  jb = env->NewByteArray(len);
	  if(image.isContinuous()){
	    env->SetByteArrayRegion(jb, 
	      0,
	      len,
	      (jbyte *)image.ptr());
	  }else{
	    //cout<<"whoops, not continuous! need to implement non-block transfer too"<<endl;
	    //env->SetBooleanField(jobj,errorID,1);

		//let's hope rows are always contiguous...
		len = image.size().width*image.channels();
		for(int row=0;row<image.size().height;row++){
			env->SetByteArrayRegion(jb, 
			row*len,
			len,
			(jbyte *)image.ptr(row));
		}
	  }
	  return jb;
	}catch(cv::Exception e){
	  env->SetBooleanField(jobj,errorID,1);
	}

	env->SetBooleanField(jobj,errorID,1);
	//env->SetBooleanField(jobj,errorID,1);
	return jb;
}

JNIEXPORT void JNICALL Java_adamKraft_videoUtils_MovieReader_nativeSkipFrame
   (JNIEnv *env, jobject jobj, jint id){
  cv::Mat image;
  capMap[id]->retrieve(image);
}

JNIEXPORT jboolean JNICALL Java_adamKraft_videoUtils_MovieReader_nativePrefetchFrame
  (JNIEnv *env, jobject jobj, jint id){
        try{
	  jboolean jb = capMap[id]->grab();
	  return jb;
	}catch(cv::Exception e){
	  jclass cls;
	  cls=env->GetObjectClass(jobj);
	  jfieldID errorID;
	  errorID=env->GetFieldID(cls,"error","Z");
	  env->SetBooleanField(jobj,errorID,1);
	}
}

JNIEXPORT void JNICALL Java_adamKraft_videoUtils_MovieReader_nativeCaptureAllocate
  (JNIEnv *env, jobject jobj, jint id, jstring fname, jboolean cam, jint dev){
  jclass cls;
  cls=env->GetObjectClass(jobj);
  jfieldID errorID;
  errorID=env->GetFieldID(cls,"error","Z");
  try{
    cv::VideoCapture *cap;
    if(cam){
      cap = new cv::VideoCapture(dev);
    }else{
      jboolean isCopy;
      const char* nameChars = env->GetStringUTFChars(
                fname, &isCopy);
      cap = new cv::VideoCapture(nameChars);
	  //cout<<nameChars<<endl;
      env->ReleaseStringUTFChars(fname,nameChars);
    }
    capMap[id] = cap;
  }catch(cv::Exception e){
    env->SetBooleanField(jobj,errorID,1);
  }
}

JNIEXPORT void JNICALL Java_adamKraft_videoUtils_MovieReader_nativeCaptureFree
  (JNIEnv *env, jobject obj, jint id){
  delete capMap[id];
  capMap.erase(id);
}

#ifdef __cplusplus
}
#endif
