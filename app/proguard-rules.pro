# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\ObjectAssignRequest\VietNH\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn lombok.**
-dontwarn org.apache.**
-dontwarn com.squareup.**
-dontwarn com.sun.**
-dontwarn *retrofit*
-dontwarn org.**
-dontwarn butterknife.**
-dontwarn retrofit2.**
-dontwarn com.mobsandgeeks.saripaar.Validator.**
-keep class com.mobsandgeeks.saripaar.** {*;}
-keep @com.mobsandgeeks.saripaar.annotation.ValidateUsing class * {*;}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class lombok.** {*;}
-keep class vn.com.vnpt.vinaphone.vnptsoftware.vinaphoneplus.source.model.pojo.respone.** {*;}
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, Annotation, EnclosingMethod