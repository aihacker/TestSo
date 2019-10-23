#include "com_blue_testso_MyJNI.h"
//
// Created by darcy on 2019/10/22.
//
/*
 * Class:     com_blue_testso_MyJNI
 * Method:    stringFromJNI
 * Signature: ()Ljava/lang/String;
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_blue_testso_MyJNI_stringFromJNI(JNIEnv *env, jclass)
{
    //if use C Language format: return (*env)->NewStringUTF(env,"Kiss dream");
    //if use C++ Language format: return env->NewStringUTF((char *)"hello 52pojie!");
    return env->NewStringUTF((char *)"hello 52pojie!");
}

/*
 * Class:     com_blue_testso_MyJNI
 * Method:    add
 * Signature: (II)I
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_blue_testso_MyJNI_add(JNIEnv *, jclass, jint a, jint b)
{
    return a+b;
}

