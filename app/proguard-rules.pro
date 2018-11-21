-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-dontobfuscate
-dontoptimize
-repackageclasses ''

-dontwarn com.fasterxml.**
-dontwarn com.smartnsoft.weathr.**
-dontwarn javax.annotation.**
-dontwarn okhttp3.internal.platform.*
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn rx.internal.util.**

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }

-keep class android.support.v7.app.** { *; }
-keep interface android.support.v7.app.** { *; }

-keep class android.support.v8.** { *; }
-keep interface android.support.v8.** { *; }

-keep class android.support.v13.app.** { *; }
-keep interface android.support.v13.app.** { *; }

-keep class com.smartnsoft.weathr.** { *; }

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**