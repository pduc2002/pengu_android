# SIMPLE ANDROID APP
## **1. Introduction**
<p align="center">Simple android coding bruuu~

## **2. Report**

All REPORTS and DEMOs have been divided into three PDFs:
* [Reports for Android 1    - Basic app](https://github.com/ducdottoan2002/BasicHacking/blob/main/Writeup_for_assignment.pdf)

* [Reports for Android 2/3  - Permission](https://github.com/ducdottoan2002/pengu_android/blob/main/Android2_PERMISSION/%5BNT213.N21.ANTT%5D-Android2_Pengu.pdf)

* [Reports for Android 4    - SSL Connection](https://github.com/ducdottoan2002/pengu_android/blob/main/Android4_SSL/%5BNT213.N21.ANTT%5D-Android4_Pengu.pdf)


## **3. Description**
Requirements:

* Android Studio: [Minimum System Requirements & Installation Guide](https://developer.android.com/studio/install?hl=vi)
* Target version: Android 12 (API level 31 )
* Program Language: Java
### **a. Android 1** 
A Login/Logup app uses sqlite database:
* User can sign in, sign up and return into sign in page (sign out)
* Using ProGuard to reduce the size of the APK (Android Package) file, optimize bytecode, and obfuscate the code to make it more difficult to reverse-engineer
* Demo has been captured in reports

### **b. Android 2**
Two basic Android applications have features that control access to each other through Permissions on their components:
* Android version: 11
* The first app is an Android 1 app with additional functions: export all username in SQLite database, `get a random number from service using Bind/uBind Service`, turning into the second one.
* The second app can view all pictures on the device, `generate random number` and only be opened from the first one by clicking `Export all credentials`
* Both apps have system and custom permissions. The service must be started (in File Control app) before `Bind Service`
* [DEMO for Android 2/3  - Permission](https://www.youtube.com/watch?v=Pth7-GTcwIs)

### **c. Android 3**
An app view all picture from MySQL database:
- Use SSL connection to Apache
- Executing SQL SELECT statement through PHP
- [DEMO for Android 4 - SSL Connection](https://youtu.be/N8SYi0AMq2U)