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

## 実行方法

appletviewer が入っている古い JRE をインストールして実行してください。。
新しい環境では実行方法がありません。

## Future Work

* 案1
  * 最近ポピュラーになってきた (要出典) Java byte code 実行時書き換え
    (javaassist) を使ってスーパークラス java.applet.Applet を
    自作の互換レイヤに書き換えてから JFrame か何かに add する。
  * そもそも java.applet.Applet 自体が Deprecated でコンパイル警告が出ている。
    いつ削除されてもおかしくない。その後はコンパイル不能になる。
  * やりすぎ。
* 案2
  * オリジナルのソースコードに自動変換をかけて自作の互換レイヤに置き換えてから
    コンパイルする。
  * 面白くはないけど有力。
  * Gradle でのやり方がよく分からない。Windows/Linux 両対応の観点からも、
    多分 Java か Groovy で書けば何とかできるとは思う。

スーパー正男本体に関してはコンパイル済みバイトコードしかないので案1しかない。
それか作者様にソースコードを直訴する。

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
