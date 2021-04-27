# MasaoMakerSP
まさおメーカーSP

## ビルド方法
Gradle を使用しているため、JDK (開発環境) をインストールしなくても
JRE (実行環境) だけでビルド可能です。
(java コマンドが利用可能ならば OK)

JRE 8 以上が必要だそうです。(Gradle 6.8.3)

#### Linux
apt での JRE インストール例。
ビルドするだけなら余分なものの無い、Java 実行環境のコアだけで OK。
```
# 確認環境 (debian buster) では openjdk-11-jre-headless
sudo apt install default-jre-headless
java -version
```

java コマンドが利用可能になれば gradlew スクリプトが全て自動でやってくれます。
(初回実行時に必要なものがダウンロードされます)
```
./gradlew build
```

#### Windows
インストーラパッケージは JRE 8 で止まっている。
ググって JRE 8 のインストーラを使うか openjdk 11 or later を展開して
環境変数 PATH や JAVA_HOME を設定するとよいと思う。

Windows の場合は gradlew.bat が全て自動でやってくれます。
```
gradlew.bat build
```
