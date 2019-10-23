package com.blue.testso;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * @Author : Blue
 * @File : com.blue.testso.MyJNI.java
 * @Date : 2019/10/22
 * @Desc :  第一步：编写JNI接口类
 *          第二步：在JNI类文件路径中，生成对应的class文件
 *                  .java文件需要是utf8 无bom格式: javac -encoding utf-8 MyJNI.java
 *                  使用命令 "javah -jni 包名.类名" 生成.h文件。注意一定要在java层目录下输入命令，不然不会报错：找不到xxx类
 *                  参考：
 *                      https://blog.csdn.net/xiaozhu0922/article/details/78835144
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class MyJNI {
    //so库内的方法
    public static native String stringFromJNI();

    public static native int add(int a, int b);

}
