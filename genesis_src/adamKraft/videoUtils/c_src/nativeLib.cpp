#include "adamKraft_videoUtils_MovieReader.h"
#include <cv.h>
#include <highgui.h>
#include <iostream>
#include <string>
#include <map>

using namespace std;

static map<jint,CvCapture*> capMap;

#ifdef __cplusplus
extern "C" {
#endif

//TODO use preprocessor to isolate the winblows-specific hacks so they don't 
//negatively impact Linux version of the library

//for the civilized world of operating systems ending in x
//export LD_RUN_PATH=/usr/local/lib; g++  -o ../opt/libmovie.so -shared -Wl,-soname,libmovie.so -I/usr/lib/jvm/java-6-openjdk/jre/../include -I/usr/lib/jvm/java-6-openjdk/jre/../include/linux  `pkg-config opencv --cflags --libs` -fPIC -shared -lc nativeLib.cpp

//Losedows options:
//1: opencv 2.2 dynamic link:
//C:\Documents and Settings\Administrator\workspace2\Gauntlet\source\adamKraft\videoUtils\c_src>cl -I"C:\Program Files\Java\jdk1.6.0_23\include" -I"C:\Program Files\Java\jdk1.6.0_23\include\win32" -I"C:\OpenCV2.2\include\opencv" -I"C:\OpenCV2.2\include" -LD nativeLib.cpp -Femovielib.dll -link C:\OpenCV2.2\lib\opencv_core220d.lib C:\OpenCV2.2\lib\opencv_ffmpeg220d.lib C:\OpenCV2.2\lib\opencv_highgui220d.lib C:\OpenCV2.2\lib\opencv_imgproc220d.lib C:\OpenCV2.2\lib\opencv_ml220d.lib
//2: opencv 2.4 dynamic link: (can be modded for debug libs)
//cl -I"C:\Program Files\Java\jdk1.6.0_35\include" -I"C:\Program Files\Java\jdk1.6.0_35\include\win32" -I"C:\opencv\build\include\opencv" -I"C:\opencv\build\include" -LD nativeLib.cpp -Femovielib.dll -link C:\opencv\build\x86\vc10\lib\opencv_core240.lib C:\opencv\build\x86\vc10\lib\opencv_highgui240.lib C:\opencv\build\x86\vc10\lib\opencv_imgproc240.lib C:\opencv\build\x86\vc10\lib\opencv_ml240.lib 
//3: opencv 2.4 static link (note: needs non-debug libs)
//cl -I"C:\Program Files\Java\jdk1.6.0_35\include" -I"C:\Program Files\Java\jdk1.6.0_35\include\win32" -I"C:\opencv\build\include\opencv" -I"C:\opencv\build\include" -LD nativeLib.cpp -Femovielib.dll -link C:\opencv\build\x86\vc10\staticlib\opencv_core240.lib C:\opencv\build\x86\vc10\staticlib\opencv_highgui240.lib C:\opencv\build\x86\vc10\staticlib\opencv_imgproc240.lib C:\opencv\build\x86\vc10\staticlib\opencv_ml240.lib C:\opencv\build\x86\vc10\staticlib\libjasper.lib C:\opencv\build\x86\vc10\staticlib\libjpeg.lib C:\opencv\build\x86\vc10\staticlib\libpng.lib C:\opencv\build\x86\vc10\staticlib\libtiff.lib C:\opencv\build\x86\vc10\staticlib\zlib.lib "C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib\ComCtl32.Lib" "C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib\gdi32.lib" "C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib\ole32.lib" "C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib\vfw32.lib" "C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib\user32.lib" "C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib\oleaut32.lib"





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
	  //capMap[id]->retrieve(image);
	  IplImage* ipl = cvRetrieveFrame(capMap[id]);
	  cv::Mat image(ipl);
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
  //cv::Mat image;
  //capMap[id]->retrieve(image);
  IplImage *ipl = cvRetrieveFrame(capMap[id]);
}

JNIEXPORT void JNICALL Java_adamKraft_videoUtils_MovieReader_nativeSeekToFrame
   (JNIEnv *env, jobject jobj, jint id, jint frame){
  //cv::Mat image;
  //capMap[id]->retrieve(image);
  jclass cls;
  cls=env->GetObjectClass(jobj);
  jfieldID errorID;
  errorID=env->GetFieldID(cls,"error","Z");
  cvSetCaptureProperty(capMap[id],CV_CAP_PROP_POS_FRAMES,frame);
  if(cvGetCaptureProperty(capMap[id],CV_CAP_PROP_POS_FRAMES)==frame){
  	env->SetBooleanField(jobj,errorID,0);
  }else{
  	env->SetBooleanField(jobj,errorID,1);
  }
}

JNIEXPORT jboolean JNICALL Java_adamKraft_videoUtils_MovieReader_nativePrefetchFrame
  (JNIEnv *env, jobject jobj, jint id){
        try{
	  jboolean jb = cvGrabFrame(capMap[id]);//capMap[id]->grab();
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
  //cout << "mark 1" <<endl;
  jclass cls;
  cls=env->GetObjectClass(jobj);
  jfieldID errorID;
  errorID=env->GetFieldID(cls,"error","Z");
  try{
    //cv::VideoCapture *cap;
    CvCapture *cap;
    if(cam){
      cap = cvCaptureFromCAM(dev);//new cv::VideoCapture(dev);
    }else{
      jboolean isCopy;
      const char* nameChars = env->GetStringUTFChars(
                fname, &isCopy);
      //cout <<"mark 1.5"<<endl;
      cout <<nameChars<<endl;
      //string nameString(nameChars);
      //nameString = string(nameString,nameString.size());
      //cout <<nameString<<endl;
      //cout <<nameString.c_str()<<endl;
      //cout <<"mark!"<<endl;
      cap = cvCaptureFromFile(nameChars);//new cv::VideoCapture("test.mov");
      //cout <<"mark 1.6"<<endl;
      //cap->open(nameString);
      //cap->open("test.mov");
      //cout <<"mark 1.75"<<endl;
	  //cout<<nameChars<<endl;
      env->ReleaseStringUTFChars(fname,nameChars);
    }
    //cout << "mark 2" <<endl;
    capMap[id] = cap;
    //cout << "mark 3" <<endl;
  }catch(cv::Exception e){
    env->SetBooleanField(jobj,errorID,1);
  }
}

JNIEXPORT void JNICALL Java_adamKraft_videoUtils_MovieReader_nativeCaptureFree
  (JNIEnv *env, jobject obj, jint id){
  //delete capMap[id];
  cvReleaseCapture(&(capMap[id]));
  capMap.erase(id);
}

#ifdef __cplusplus
}
#endif
