# FirebaseSignInWithEmailAndPassword
It's an app built with [Kotlin][1] that shows how to [authenticate users with Firebase][2] using [Android Architecture Components][3] and the MVVM Architecture Pattern. For the UI it uses Jetpack Compose, Android's modern toolkit for building native UI.

![alt text](https://i.ibb.co/dMkCMDd/App.png)

Below you can find the docs for each tehnology that is used in this app:

## Firebase Products:
* [Firebase Authentication][2]
* [Cloud Functions for Firebase][13]
* [Cloud Firestore][14]

## Android Architecture Components:
* [ViewModel][5]
* [Navigation][12]

## Dependency Injection:
* [Hilt for Android][6]

## Asynchronous Programming:
* [Kotlin Coroutines][7]
* [Asynchronous Flow][8]

## Other Android Components:
* [Jetpack Compose][9]

---

This repo represents the code for following article writen on the Medium publication:

* [How to authenticate to Firebase using email and password in Jetpack Compose?][10]

See it also on youtube:

* https://youtu.be/2o2dlueLmkM

If you download or clone this repo, in order to make it work, you should follow the instructions given in the official documentation regarding on [how to add Firebase to your project][11].

**License**
---
The code in this project is licensed under the Apache License 2.0.

    Copyright 2018 Google LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

**Disclaimer**
---
* This is not an officially supported Google product.

[1]: https://kotlinlang.org/
[2]: https://firebase.google.com/docs/auth
[3]: https://developer.android.com/topic/libraries/architecture
[5]: https://developer.android.com/topic/libraries/architecture/viewmodel
[6]: https://developer.android.com/training/dependency-injection/hilt-android
[7]: https://kotlinlang.org/docs/coroutines-overview.html
[8]: https://kotlinlang.org/docs/flow.html
[9]: https://developer.android.com/jetpack/compose
[10]: https://medium.com/firebase-tips-tricks/how-to-authenticate-to-firebase-using-email-and-password-in-jetpack-compose-bd70ca56ea91
[11]: https://firebase.google.com/docs/android/setup
[12]: https://developer.android.com/guide/navigation
[13]: https://firebase.google.com/docs/functions
[14]: https://firebase.google.com/docs/firestore
