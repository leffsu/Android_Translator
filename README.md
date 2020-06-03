Android Translator
=====

[ ![Download](https://api.bintray.com/packages/leffsu/translator/translator/images/download.svg) ](https://bintray.com/leffsu/translator/translator/_latestVersion) [![Build Status](https://travis-ci.org/leffsu/Android_Translator.svg?branch=master)](https://travis-ci.org/leffsu/Android_Translator)

Android Translator is a simple library that simplifies your job working with different translations.

Download
--------
Use Gradle:

```gradle
repositories {
  jcenter()
  google()
}

dependencies {
    implementation 'su.leff.translator:translator:0.2.1'
}
```

Or Maven:

```xml
<dependency>
  <groupId>su.leff.translator</groupId>
  <artifactId>translator</artifactId>
  <version>0.2.1</version>
  <type>pom</type>
</dependency>
```

How do I use Translator?
-------------------

Examples of different readers are located in sample.

Java is not supported but you can create your own wrapper around Translator to work in Java.

Simple use cases will look something like this:

```Kotlin
// First of all, you need to supply Translator with a HashMap<Key, Text>:

Translator.loadMap(hashmap)

// If you don't want your app to crash whenever the key is not found, set it up like this:

Translator.setFailSafe(false).loadMap(hashmap)

// To use it with a TextView (it will work as txv.setText(text):
textView.key = "key"

// To use it with an EditText (it will work as txv.setHint(text):
editText.key = "key"

// To get a string from Translator:
Translator.getString("key")
```

Status
------
Translator is still in alpha.

Compatibility
-------------

 * **Minimum Android SDK**: Translator requires a minimum API level of 19.
 * **Compile Android SDK**: Translator requires you to compile against API 29 or later. Other versions are not tested or supported.
 
Getting Help
------------
To report a specific problem or feature request, open a new issue on Github. For questions, suggestions, or
anything else, email me at leff@leff.su or ping me on Telegram t.me/leffsu.

Thanks
------
* **Roman Uvarov https://github.com/Roman1610** for support in my darkest times.

Author
------
Lev Nazarov - @leffsu on GitHub, @leffsu on Telegram

License
-------
Apache 2.0