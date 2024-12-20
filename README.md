# MasaoMakerSP

まさおメーカーSP

## ビルド方法

Gradle 8.11.1

### Linux

昔は JRE だけでも行けた気がするけど気のせいだったかもしれない。
最近の Java はよく分からないので諦めない心が重要。

```sh
# 確認環境 (debian bookworm) では openjdk-17-jdk-headless
sudo apt install default-jdk-headless
java -version
```

(準備がうまくいっていれば) gradlew スクリプトが全て自動でやってくれます。

```sh
./gradlew build

# 起動高速化のためにデーモンを立ち上げっぱなしにするが、
# JDK のインストール構成をいじると追従できなくなることがあるようなので
# 怪しい場合はこちら
./gradlew --no-daemon build
```

### Windows

インストーラパッケージは JRE 8 で止まっている。
ググって JRE 8 のインストーラを使うか openjdk 11 or later を展開して
環境変数 PATH や JAVA_HOME を設定するとよいと思う。

Windows の場合は gradlew.bat が全て自動でやってくれます。

```bat
gradlew.bat build
```

## その他ビルドコマンド

```sh
./gradlew help
./gradlew tasks

./gradlew clean
# とか
```

## Gradle のアップデート

```sh
./gradlew wrapper --gradle-version=X.Y.Z
git diff
```

新しくなった Gradle による wrapper に更新

```sh
./gradlew wrapper
git diff
```
