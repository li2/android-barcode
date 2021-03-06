[![](https://jitpack.io/v/li2/android-barcode.svg)](https://jitpack.io/#li2/android-barcode)

##  Barcode Library

A library to wrap [zxing-android](https://github.com/journeyapps/zxing-android-embedded) to scan barcode in Rx.


## Usage

```kotlin
doWithCameraPermission {
    BarcodeScanner.scan(this).subscribe { content ->
        if (content.isNotEmpty()) {
            // todo
        }
    }
}
```

Note: `doWithCameraPermission`  is an extension function to request camera runtime permission, which is not part of this library, you can check [Utils.kt](https://github.com/li2/android-barcode/blob/master/app/src/main/java/me/li2/android/barcodesample/Utils.kt) for more details.


<img width="300" alt="barcode_scanner_ui" src="screenshots/barcode_scanner_ui.png">


## Download

```gradle
implementation 'com.github.li2:android-barcode:latest_version'
```


## License

```
    Copyright (C) 2020 Weiyi Li

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```