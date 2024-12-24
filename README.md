# MasaoMakerSP

[![Java CI with Gradle](https://github.com/yappy/MasaoMakerSP/actions/workflows/gradle.yml/badge.svg)](https://github.com/yappy/MasaoMakerSP/actions/workflows/gradle.yml)

まさおメーカーSP

まさおコンストラクションとまさおメーカー SP のオリジナルコードを
できるだけそのまま現代の技術で動かすのを目的としています。

Java Applet は新しい Java では既に非推奨→廃止されており、
ブラウザ用の Java Plug-in も無くなっているため、基本的に実行環境がありません。
古い Java をインストールすれば appletviewer というツールで実行できる
可能性がありますが、逆にそれ以外の方法がありません。

## ビルド方法

Gradle 8.11.1

### Linux

昔は JRE だけでも行けた気がするけど気のせいだったかもしれない。
最近の Java はよく分からないので諦めない心が重要。

```sh
# 確認環境 (debian bookworm) では openjdk-17-jre-headless
sudo apt install default-jre-headless
# だめだったらこっち
sudo apt install default-jdk-headless
java -version
# GUI が入っていないので、実行には headless でないものが必要です
```

最近は指定バージョンがインストールされていればそれを使い、
無ければ自動でダウンロードしてくれるようになったらしい。
いい加減なバージョンを指定すると事故る気がするので Java 17 を明記しておきますが、
以下を自分の JDK version に合わせるとか、自分がその JDK version を
インストールするとか、いい感じにやってください(適当)。

```groovy
// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
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

Oracle と OpenJDK とライセンスの問題で大変ややこしいことになっている。
winget で入れるのがアップデート管理も楽でよいと思うが、
検索すると似たようなものが大量に出てくる。。
どれもソースコードは同じでどれもほぼ同じだと思うので好きなものを入れればいいのでは
ないでしょうか。

```txt
$ winget search jdk

# Amazon 製?
AdoptOpenJDK JDK with Hotspot 17        AdoptOpenJDK.OpenJDK.17        17.0.0.20  Tag: jdk winget
# Eclipse 製?
Eclipse Temurin JDK with Hotspot 17     EclipseAdoptium.Temurin.17.JDK 17.0.13.11 Tag: jdk winget
# Oraclea 製、いわゆる Oracle JDK (ライセンス注意)
Java(TM) SE Development Kit 17          Oracle.JDK.17                  17.0.12.0  Tag: jdk winget
# OpenJDKの有力貢献企業らしい
Liberica JDK 17                         BellSoft.LibericaJDK.17        17.0.13.12          winget
Liberica JDK 17 Full                    BellSoft.LibericaJDK.17.Full   17.0.13.12          winget
# Microsoft 製 OpenJDK
Microsoft Build of OpenJDK with Hotspo… Microsoft.OpenJDK.21           21.0.5.11           winget
# 公式っぽいけどプロジェクトが終了して 17 までしか出ていない
ojdkbuild OpenJDK 17                    ojdkbuild.openjdk.17.jdk       17.0030.6…          winget
```

Windows の場合は gradlew.bat が全て自動でやってくれます。

```bat
gradlew.bat build
```

## 実行方法

Java Applet はコンパイルは (警告を出しながら) 通りますが、
新しい環境では実行方法がありません。。
appletviewer が入っている古い JDK をインストールして実行してください。

それを何とかするのが本プロジェクトの目標です。

以下のコマンドでデフォルトの Java Application が実行されます。

```sh
./gradle run
```

## Future Work

* 案1
  * 最近ポピュラーになってきた (要出典) Java byte code 実行時書き換え
    (javaassist) を使ってスーパークラス java.applet.Applet を
    自作の互換レイヤに書き換えてから JFrame か何かに add する。
  * そもそも java.applet.Applet 自体が Deprecated でコンパイル警告が出ている。
    いつ削除されてもおかしくない。その場合はコンパイル不能になる。
  * 黒魔術
* 案2
  * オリジナルのソースコードに自動変換をかけて自作の互換レイヤに置き換えてから
    コンパイルする。
  * 面白くはないけど有力。
  * Gradle でのやり方がよく分からない。Windows/Linux 両対応の観点からも、
    多分 Java か Groovy で書けば何とかできるとは思う。
* 案3
  * オリジナルのソースコードをベースに普通に書き換える。
  * これでよいのでは…。

スーパー正男本体に関してはコンパイル済みバイトコードしかないので案1しかない。
それか作者様にソースコードを直訴する。

## その他ビルドコマンド

```sh
./gradlew help
./gradlew tasks

./gradlew clean
# とか
```

```sh
# 確認
git clean -nxd
# 全部消してきれいにする
git clean -fxd
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

新しいプロジェクトテンプレートを確認

```sh
mkdir test
cd test
../gradlew init
```

## ライセンスについて

### 正男コンストラクション

以下、およびそれと同一ファイルは福田直人様の著作物です。
title.gif にある「このゲームは、転載自由です。」のメッセージの他は
一般の著作権法に準じます。

* original/
  * mc_c.zip
    * コンパイル済み .class のみ。ソース公開はなし。
  * title.gif
  * pattern.gif
  * ending.gif
  * gameover.gif
  * chizu.gif
  * makerpat.gif
    * こちらはオリジナルには含まれませんが、まさおメーカーがマップを描画するために
      私が pattern.gif の一部を抜き出して並べ替えたものです。
    * 私の二次著作物としては認められないと思います。

### それ以外

MIT license: [LICENSE](./LICENSE)
