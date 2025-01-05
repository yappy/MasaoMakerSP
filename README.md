# MasaoMakerSP

[![Auto Build and Check](https://github.com/yappy/MasaoMakerSP/actions/workflows/build.yml/badge.svg)](https://github.com/yappy/MasaoMakerSP/actions/workflows/build.yml)

まさおメーカーSP

まさおコンストラクションとまさおメーカー SP のオリジナルコードを
できるだけそのまま現代の技術で動かすのを目的としています。

Java Applet は新しい Java では既に非推奨→廃止されており、
ブラウザ用の Java Plug-in も無くなっているため、基本的に実行環境がありません。
非推奨 API の使用を回避しつつ、Java デスクトップアプリケーションの形で
実行可能にします。

![Screen Shot 2.8](ss2.png)
![Screen Shot 3.0](ss3.png)

## 実行方法

以下よりダウンロード可能です。

リリースページ: <https://github.com/yappy/MasaoMakerSP/releases> \
最新版: <https://github.com/yappy/MasaoMakerSP/releases/latest>

v0.1.3-alpha よりインストーラパッケージに対応しました。
実行に必要な Java ランタイムも同時にインストールされます。

* `*.msi`: Windows 用インストーラ
* `*.deb`: Linux Debian (Ubuntu) 用インストーラ
* `*.zip`: Java ランタイムなしインストーラなし版 (全 OS 用)

## ステータス

* :heavy_check_mark: まさおコンストラクション 2.8 の実行
* :heavy_check_mark: まさおコンストラクション 3.0 の実行
* :x: まさおメーカー SP の実行

<https://github.com/yappy/MasaoMakerSP/issues/3>

## ディレクトリ構成 (ここから開発者向け)

* `gradle/`, `gradlew`, `gradlew.bat`
* `settings.gradle`
* `app/`
  * Java Application プロジェクト。
* `mcport/`
  * MasaoConstruction のデスクトップアプリケーション向け移植。
  * Java Library プロジェクト。
* `original/`
  * 移植元ファイルをなるべくそのままの形で保持している。
  * `mc2`
  * `mc3`
  * `mmsp`
* `scripts/`
  * 主にオリジナルデータに対して処理をかけるスクリプト群。
  * `dis/`
    * MasaoConstruction に対してディスアセンブルをかける。
  * `sound/`
    * MasaoConstruction 3.0 の効果音データを無難なフォーマットに変換する。
* `technote/`
  * 技術的なノート。

## Install Java

### Windows

Oracle と OpenJDK とライセンスの問題で大変ややこしいことになっている。
winget で入れるのがアップデート管理も楽でよいと思うが、
検索すると似たようなものが大量に出てくる。。
どれもソースコードは同じでどれもほぼ同じだと思うので好きなものを入れればいいのでは
ないでしょうか。

Windows 10 の場合は Windows Terminal を入れないと winget が盛大に文字化けしたかも
しれない。。文字コードの設定をいじるか、気合でエスパーする。

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
Microsoft Build of OpenJDK with Hotspo… Microsoft.OpenJDK.17           17.0.13.11          winget
Microsoft Build of OpenJDK with Hotspo… Microsoft.OpenJDK.21           21.0.5.11           winget
# 公式っぽいけどプロジェクトが終了して 17 までしか出ていない
ojdkbuild OpenJDK 17                    ojdkbuild.openjdk.17.jdk       17.0030.6…          winget

# 多分どれもそんなに変わらないけど、まあ Microsoft を信用してみてもいいのでは
$ winget install Microsoft.OpenJDK.17
```

### Linux

昔は JRE だけでも行けた気がするけど気のせいだったかもしれない。
最近の Java はよく分からないので諦めない心が重要。

```sh
# 確認環境 (debian bookworm) では openjdk-17-jre-headless
# 実行だけならこれでできる
sudo apt install default-jre
# だめだったらこっち
sudo apt install default-jdk

java -version
```

## ビルド方法

Gradle 8.11.1

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
# または PC/VM 再起動
./gradlew --no-daemon build
```

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

```sh
# 確認
git clean -nxd
# 全部消してきれいにする
git clean -fxd
```

## jpackage

最近の JDK に含まれている `jpackage` ツールにより、
Windows Installer (*.msi) や Linux Debian package (*.deb) を作れます。

```sh
# jpackage コマンドラインを見るためには --info を指定
./gradlew jpackage --info
```

### jpackage - Windows

Windows の場合、インストーラ作成のために Wix が必要。
しかし Wix は 3 から 4 で大きく変わってしまったため、3 のインストールが必要。
Java 24 で新しい Wix に対応予定らしい…。
Windows の機能の有効化が必要と言われた場合、管理者権限で実行する。

```bat
> winget search wix
名前                                 ID                            バージョン  一致               ソース
--------------------------------------------------------------------------------------------------------
WiX Toolset Command-Line Tools       WiXToolset.WiXCLI             5.0.2.0     Command: wix       winget
IsWiX                                IsWiX.IsWiX                   5.0.53.0    Tag: wix           winget
WiX Toolset Additional Tools         WiXToolset.WiXAdditionalTools 5.0.2       Tag: wix           winget
WiX Toolset                          WiXToolset.WiXToolset         3.14.1.8722 Tag: wix           winget

> winget install WiXToolset.WiXToolset

このパッケージには次の依存関係が必要です:
  - Windows の機能
      NetFx3
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
    * コンパイル済み `*.class` のみ。ソース公開はなし。
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
