# TestSo
so编写示例、JNI示例

使用AndroidStudio 3.X编写so
链接：http://note.youdao.com/noteshare?id=2323e1d89f21cb0c22d93d30b74e1ce8&sub=3ABB2972AEA7438BA39EADA3BA5C88CA)

## 步骤：
### 第一步：在AndroidStudio中配置ndk
### 第二步：编写JNI接口类
```Java
package com.blue.testso;

public class MyJNI {
    static {
        System.loadLibrary("native-lib");
    }
    
    public static native String stringFromJNI();

    public static native int add(int a, int b);

}
```
### 第三步：在JNI接口类文件路径，生成 .class文件
1.手动cmd命令生成法
```cmd
D:\git\TestSo\app\src\main\java\com\blue\testso>start .

D:\git\TestSo\app\src\main\java\com\blue\testso>javac -encoding utf-8 MyJNI.java

D:\git\TestSo\app\src\main\java\com\blue\testso>cd ..

D:\git\TestSo\app\src\main\java\com\blue>cd ..

D:\git\TestSo\app\src\main\java\com>cd ..

D:\git\TestSo\app\src\main\java>javah -jni com.blue.testso.MyJNI

D:\git\TestSo\app\src\main\java>
```


(2.build自动生成法：(来源于引用2)
编写之后，make project，再到工程目录
D:\git\TestSo\app\build\intermediates\javac\debug\classes\com\blue\testso\MyJNI.class)
### 第四步：生成h头文件(用class文件)
返回到"java"目录下，用cmd命令 "javah -jni 包名.类名" 生成.h文件。
注意一定要在java层目录下输入命令，不然不会报错：找不到xxx类
  
(或者用文章2方法：
1.打开Terminal，然后在命令行中先进入到工程的main目录下 cd app/src/main。 
2.输入命令： 
```cmd
javah -d jni -classpath D:\git\TestSo\app\build\intermediates\javac\debug\classes com.blue.testso.MyJNI（注意classes后的空格）)
```
### 第五步：编写c文件
创建 jni 文件夹，并将.h文件移动到此文件夹下。
然后创建一个空的c文件，并对其编写。编写内容：将.h文件中 两个方法相关的代码复制到c文件中，头部引用.h文件
```c
#include "com_blue_testso_MyJNI.h"
/*
 * Class:     com_blue_testso_MyJNI
 * Method:    stringFromJNI
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_blue_testso_MyJNI_stringFromJNI(JNIEnv *, jclass)
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
JNIEXPORT jint JNICALL Java_com_blue_testso_MyJNI_add(JNIEnv *, jclass, jint a, jint b)
{
    return a + b;
}
```
### 第六步：下载CMake和LLDB。(需要注意：CMake 不可下载 3.10.版本 需要下载3.6.版本，不然会报错，具体 查看/下载 ‘点击AS-SDKManager - 右下角 showPackageDetails -查看CMake’)
### 第七步：下载好后 在项目app下的build.gradle下进行相关配置
```groove
apply plugin: 'com.android.application'

android {
    compileSdkVersion 28
    buildToolsVersion "29.0.2"
    defaultConfig {
        applicationId "com.blue.testso"
        minSdkVersion 19
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        // 使用Cmake工具，位置android --> defaultConfig
        externalNativeBuild {
            cmake {
                cppFlags ""
                //生成多个版本的so文件
                abiFilters 'arm64-v8a','armeabi-v7a','x86','x86_64'
            }
        }
    }

    // 配置CMakeLists.txt路径
    externalNativeBuild {
        cmake {
            path "CMakeLists.txt"  // 设置所要编写的c源码位置，以及编译后so文件的名字
            version "3.10.2"
        }
    }
    
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}
```
### 第八步：修改 CMakeLists.txt 内容。
添加CMakeLists.txt文件到app目录下，和build.gradle文件同级目录， 
关于 CMakeLists.txt 如何得来的，可以自己在新建项目的时候 勾选“Include c/c++” 生成的项目就有此文件。

### 第九步：编译 点击"Build - Rebuild Project",编译后在 "app - build - intermediates - cmake - debug - obj "下查看so库

如果不显示build目录，做如下操作：

### 第十步：项目创建jniLibs并调用so库方法
如果想指定jniLibs文件路径可以在app - build.gradle 下配置：
```groove
    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }
```

#### 最后需要注意：
.h文件要移动到 jni文件夹下（如果用参考文章2，可不用）
CMake下载注意版本

#### 参考：
1.《教我..》系列编写so -- 使用AndroidStudio 3.X编写so
https://www.52pojie.cn/thread-1024613-1-1.html
(出处: 吾爱破解论坛)

2.https://blog.csdn.net/xiaozhu0922/article/details/78835144
