# DB関連メモ
```
docker run -d --hostname local-oracle-db --name local-oracle-db -p 49161:1521 -p 58080:8080 -e ORACLE_ALLOW_REMOTE=true -e ORACLE_DISABLE_ASYNCH_IO=true wnameless/oracle-xe-11g-r2

docker exec -d local-oracle-db mkdir -p /opt/cheering-local-restaurant/db
docker exec -d local-oracle-db chown -R oracle:dba /opt/cheering-local-restaurant/db

CREATE TABLESPACE RESTAURANT
	DATAFILE '/opt/cheering-local-restaurant/db/data.dbf' SIZE 100M
	AUTOEXTEND ON NEXT 500K MAXSIZE 15G;

CREATE ROLE DB_LOCAL_ADMIN;

GRANT CONNECT
    , CREATE ANY INDEX          , ALTER ANY INDEX               , DROP ANY INDEX
    , CREATE MATERIALIZED VIEW  , ALTER ANY MATERIALIZED VIEW   , DROP ANY MATERIALIZED VIEW
    , CREATE ANY PROCEDURE      , ALTER ANY PROCEDURE           , DROP ANY PROCEDURE         , EXECUTE ANY PROCEDURE
    , CREATE ANY SEQUENCE       , ALTER ANY SEQUENCE            , DROP ANY SEQUENCE
    , CREATE ANY TABLE          , ALTER ANY TABLE               , DROP ANY TABLE
    , CREATE ANY VIEW           , DROP ANY VIEW
    , CREATE PROCEDURE
    , CREATE SEQUENCE
    , CREATE TABLE
    , CREATE VIEW
    , INSERT ANY TABLE    , SELECT ANY TABLE    , UPDATE ANY TABLE    , DELETE ANY TABLE            , LOCK ANY TABLE
    , SELECT ANY SEQUENCE
    TO DB_LOCAL_ADMIN
;

CREATE USER RESTAURANT
	IDENTIFIED BY "Password1234"
	DEFAULT TABLESPACE RESTAURANT
	TEMPORARY TABLESPACE TEMP;

GRANT DB_LOCAL_ADMIN TO RESTAURANT;

ALTER USER RESTAURANT QUOTA UNLIMITED ON RESTAURANT;

-- シーケンスの作成
EXEC RESET_SEQUENCE('RESTOS', 'RESTAURANT_ID', 'RESTOS_SEQ');
EXEC RESET_SEQUENCE('RESTO_HISTORIES', 'RESTAURANT_HISTORY_ID', 'RESTO_HISTORIES_SEQ');
EXEC RESET_SEQUENCE('USERS', 'USER_ID', 'USERS_SEQ');
EXEC RESET_SEQUENCE('LOGIN_REQUESTS', 'ID', 'LOGIN_REQUESTS_SEQ');
EXEC RESET_SEQUENCE('RESTO_TEMP_OPEN_DAYS', 'ID', 'RESTO_TEMP_OPEN_DAYS_SEQ');
EXEC RESET_SEQUENCE('RESTO_TEMP_CLOSE_DAYS', 'ID', 'RESTO_TEMP_CLOSE_DAYS_SEQ');
EXEC RESET_SEQUENCE('RESTO_OPEN_HOURS', 'ID', 'RESTO_OPEN_HOURS_SEQ');
EXEC RESET_SEQUENCE('RESTO_CLOSE_DAYS_WKLY', 'ID', 'RESTO_CLOSE_DAYS_WKLY_SEQ');
EXEC RESET_SEQUENCE('RESTO_CLOSE_DAYS_MO_DOW', 'ID', 'RESTO_CLOSE_DAYS_MO_DOW_SEQ');
EXEC RESET_SEQUENCE('RESTO_CLOSΕ_DAYS_MO_DOM', 'ID', 'RESTO_CLOSΕ_DAYS_MO_DOM_SEQ');
EXEC RESET_SEQUENCE('RESTO_TELNOES', 'ID', 'RESTO_TELNOES_SEQ');
EXEC RESET_SEQUENCE('RESTO_FAXNOES', 'ID', 'RESTO_FAXNOES_SEQ');
EXEC RESET_SEQUENCE('RESTO_EXTERNAL_LINKS', 'ID', 'RESTO_EXTERNAL_LINKS_SEQ');
EXEC RESET_SEQUENCE('RESTO_PHOTOS', 'ID', 'RESTO_PHOTOS_SEQ');
EXEC RESET_SEQUENCE('RESTO_SEARCH_KEYWORDS', 'ID', 'RESTO_SEARCH_KEYWORDS_SEQ');
EXEC RESET_SEQUENCE('MENU', 'MENU_ID', 'MENU_SEQ');
EXEC RESET_SEQUENCE('MENU_HISTORIES', 'MENU_HISTORY_ID', 'MENU_HISTORIES_SEQ');
EXEC RESET_SEQUENCE('MENU_PHOTOS', 'ID', 'MENU_PHOTOS_SEQ');
EXEC RESET_SEQUENCE('ORDERS', 'ORDER_ID', 'ORDERS_SEQ');
EXEC RESET_SEQUENCE('ORDER_DETAILS', 'ORDER_DETAIL_ID', 'ORDER_DETAILS_SEQ');

CREATE SEQUENCE RESTOS_SEQ;
CREATE SEQUENCE RESTO_HISTORIES_SEQ;
CREATE SEQUENCE USERS_SEQ;
CREATE SEQUENCE LOGIN_REQUESTS_SEQ;
CREATE SEQUENCE RESTO_TEMP_OPEN_DAYS_SEQ;
CREATE SEQUENCE RESTO_TEMP_CLOSE_DAYS_SEQ;
CREATE SEQUENCE RESTO_OPEN_HOURS_SEQ;
CREATE SEQUENCE RESTO_CLOSE_DAYS_WKLY_SEQ;
CREATE SEQUENCE RESTO_CLOSE_DAYS_MO_DOW_SEQ;
CREATE SEQUENCE RESTO_CLOSΕ_DAYS_MO_DOM_SEQ;
CREATE SEQUENCE RESTO_TELNOES_SEQ;
CREATE SEQUENCE RESTO_FAXNOES_SEQ;
CREATE SEQUENCE RESTO_EXTERNAL_LINKS_SEQ;
CREATE SEQUENCE RESTO_PHOTOS_SEQ;
CREATE SEQUENCE RESTO_SEARCH_KEYWORDS_SEQ;
CREATE SEQUENCE MENU_SEQ;
CREATE SEQUENCE MENU_HISTORIES_SEQ;
CREATE SEQUENCE MENU_PHOTOS_SEQ;
CREATE SEQUENCE ORDERS_SEQ;
CREATE SEQUENCE ORDER_DETAILS_SEQ;

-- 郵便番号データ桁数確認用
SELECT
  MAX(NVL(LENGTH(JAPANESE_LOCAL_GOVERMENT_CODE), 0)) AS JAPANESE_LOCAL_GOVERMENT_CODE
  , MAX(NVL(LENGTH(OLD_ZIP_CODE), 0)) AS OLD_ZIP_CODE
  , MAX(NVL(LENGTH(ZIP_CODE), 0)) AS ZIP_CODE
  , MAX(NVL(LENGTH(PREFECTURE_NAME_KANA), 0)) AS PREFECTURE_NAME_KANA
  , MAX(NVL(LENGTH(CITY_NAME_KANA), 0)) AS CITY_NAME_KANA
  , MAX(NVL(LENGTH(TOWN_NAME_KANA), 0)) AS TOWN_NAME_KANA
  , MAX(NVL(LENGTH(PREFECTURE_NAME), 0)) AS PREFECTURE_NAME
  , MAX(NVL(LENGTH(CITY_NAME), 0)) AS CITY_NAME
  , MAX(NVL(LENGTH(TOWN_NAME), 0)) AS TOWN_NAME
  , MAX(NVL(LENGTH(TOWN_DEVIDED_FLAG), 0)) AS TOWN_DEVIDED_FLAG
  , MAX(NVL(LENGTH(ISSUED_PER_KOAZA_FLAG), 0)) AS ISSUED_PER_KOAZA_FLAG
  , MAX(NVL(LENGTH(CHOME_TOWN_FLAG), 0)) AS CHOME_TOWN_FLAG
  , MAX(NVL(LENGTH(HAS_MULTIPLE_TOWN_FLAG), 0)) AS HAS_MULTIPLE_TOWN_FLAG
  , MAX(NVL(LENGTH(UPDATE_DISPLAY_FLAG), 0)) AS UPDATE_DISPLAY_FLAG
  , MAX(NVL(LENGTH(CHANGE_REASON_FLAG), 0)) AS CHANGE_REASON_FLAG 
FROM
  ZIP_CODES;

/*
-- 表領域の一覧を取得する
SELECT * FROM DBA_TABLESPACES;
-- データファイルの一覧を取得する
SELECT * FROM DBA_DATA_FILES;

-- （★リセット用）ユーザーの削除
DROP USER RESTAURANT CASCADE;

-- （★リセット用）表領域の削除
DROP TABLESPACE RESTAURANT
INCLUDING CONTENTS
AND DATAFILES
CASCADE CONSTRAINTS;
*/
```

# メール関連

## メール送信のために必要なパラメーター

起動時の引数として指定する（機微な情報なので、HashiCorp Vaultを使った方法に切り換える想定）

```
spring.mail.host（Yahoo!メールの場合: smtp.mail.yahoo.co.jp、GMailの場合: smtp.gmail.com）
spring.mail.port（Yahoo!メール・GMail両方: 587）
spring.mail.username
spring.mail.password（GMailの場合は2段階認証を有効にしてアプリパスワードを設定しないとブロックされた）
spring.mail.properties.mail.smtp.auth（Yahoo!メール・GMail両方: true）
spring.mail.properties.mail.smtp.starttls.enable（Yahoo!メールの場合: false、GMailの場合: true）

mail.from.address
mail.test.to.address
```

# HashiCorp Vault

https://learn.hashicorp.com/vault/operations/ops-deployment-guide

https://www.vaultproject.io/docs/concepts/integrated-storage

https://www.vaultproject.io/docs/configuration/storage/raft

## インストール

[Ansible](https://docs.ansible.com/) を使ってインストール・セットアップする。

Consulをバックエンドのストレージにすると、サーバーのメモリを消費するので、単一サーバー上で`Integrated Storage`を使った構成にする。

Ansibleをインストール・セットアップする。

```shell
sudo yum -y install ansible
sudo cp /etc/ansible/ansible.cfg ~/.ansible.cfg
sudo chown $USER:$USER ~/.ansible.cfg
sed -i -e "s/#log_path = \/var\/log\/ansible\.log/log_path = ~\/ansible\.log/g" ~/.ansible.cfg
```

`ansible-playbook --version`コマンドを実行し、バージョン情報および設定ファイルのパスが「/home/$USER/.ansible.cfg」で表示されることを確認する。

以下のコマンドを実行してVaultをインストール・セットアップ・サービス起動する。

※サーバー全体のセットアップ手順も作った後で、コマンド実行の手順はそちらへ移動する。

```shell
cd ./ansible
ansible-playbook --connection=local --inventory 127.0.0.1, --limit 127.0.0.1 site.yml -vv
```

## Vaultサーバー初期化

### デフォルトの設定で初期化する場合

https://learn.hashicorp.com/vault/getting-started/deploy#initializing-the-vault

**実運用環境では非推奨とのこと。（`Unseal Key`を1人で保管するのはリスクがあるため。）**

```shell
export VAULT_ADDR='http://127.0.0.1:8200'

vault operator init
```

出力される5個の`Unseal Key`と1個の`Initial Root Token`を控えておく。
**失くしたり忘れたりすると復号化できなくなるので注意。**

復号化するには5個のうち3個の`Unseal Key`が必要。

```shell
export VAULT_ADDR='http://127.0.0.1:8200'

vault operator unseal

Unseal Key (will be hidden): # ←1個の`Unseal Key`を入力
Key                Value
---                -----
Seal Type          shamir
Initialized        true
Sealed             true # ← `vault operator unseal`を3回実行し、毎回異なる`Unseal Key`を入力して処理に成功すると、`false`に変わる。
#（後略）
```

復号化した後は`Initial Root Token`でログインできるようになる。

```shell
vault login <`Initial Root Token`の値>
```

### 安全性を高めた方法で初期化する場合

https://www.vaultproject.io/docs/concepts/pgp-gpg-keybase.html
https://www.vaultproject.io/docs/commands/operator/init
https://gnupg.org/gph/en/manual.html

`Unseal Key`をGPGにより暗号化する方法に加え、`Initial Root Token`もGPGにより暗号化することによりさらに安全性を高めてみる。

#### 新しい鍵のペア（keypair）を作成する

`Unseal Key`用に3個、`Initial Root Token`用に1個の計4個作成する。

`Unseal Key`用の鍵の個数は任意（ただし、1〜2個だと安全性を高めていることにはならないはず。）

ユーザーID例

- chloresto-usk-1
- chloresto-usk-2
- chloresto-usk-3
- chloresto-irt

```shell
gpg --gen-key
```

すべてデフォルトで作成する

- 鍵の種類: (1) RSA と RSA (デフォルト)
- 鍵長: 2048
- 鍵の有効期間: 0

鍵をbase64形式でエクスポートする。

```shell
gpg --export <ユーザーID> | base64 > <任意のファイル名>.asc
```

例）

```shell
gpg --export chloresto-usk-1 | base64 > chloresto-usk-1.asc
gpg --export chloresto-usk-2 | base64 > chloresto-usk-2.asc
gpg --export chloresto-usk-3 | base64 > chloresto-usk-3.asc
gpg --export chloresto-irt | base64 > chloresto-irt.asc
```

#### GPG鍵を指定してVaultサーバーを初期化する

コマンドを実行するディレクトリと、鍵ファイルを配置しているディレクトリが同じであること。

```shell
export VAULT_ADDR='http://127.0.0.1:8200'

vault operator init -key-shares=<生成する`Unseal Key`の個数（マスター キーの分割数）> -key-threshold=<マスター キーを復元するために必要な`Unseal Key`の個数（`-key-shares`の数値以下であること）> -pgp-keys="<`Unseal Key`用ユーザーの鍵ファイルをカンマ区切りで列挙>" -root-token-pgp-key=<`Initial Root Token`用ユーザーの鍵ファイル>
```

例）

```shell
vault operator init -key-shares=3 -key-threshold=2 -pgp-keys="chloresto-usk-1.asc,chloresto-usk-2.asc,chloresto-usk-3.asc" -root-token-pgp-key=chloresto-irt.asc
```

出力される`Unseal Key`と`Initial Root Token`を控えておく。
**失くしたり忘れたりすると復号化できなくなるので注意。**

例の場合、復号化するには3個のうち2個の`Unseal Key`が必要。
1個目から順番に個別のGPGユーザーの鍵と紐づけられている。

#### `Unseal Key`を復号化する

```shell
echo "`vault operator init〜`で出力された`Unseal Key`" | base64 --decode | gpg -dq
```

GPG秘密鍵のパスワード入力が求められるので、対応するユーザーのパスワードを入力する。

正しいパスワードを入力し、ターミナルに出力された文字列が`Unseal Key`となる。

#### Vaultを復号化する

```shell
export VAULT_ADDR='http://127.0.0.1:8200'

vault operator unseal

Unseal Key (will be hidden): # ←1個の`Unseal Key`を入力
Key                Value
---                -----
Seal Type          shamir
Initialized        true
Sealed             true # ← `vault operator unseal`を2回実行し、毎回異なる`Unseal Key`を入力して処理に成功すると、`false`に変わる。
#（後略）
```

#### `Initial Root Token`を復号化する

```shell
echo "`vault operator init〜`で出力された`Initial Root Token`" | base64 --decode | gpg -dq
```

GPG秘密鍵のパスワード入力が求められるので、対応するユーザーのパスワードを入力する。

正しいパスワードを入力し、ターミナルに出力された文字列が`Initial Root Token`となる。

#### ログインする

```shell
vault login <復号化された`Initial Root Token`>
```

## シークレット エンジン（Secrets Engine）を有効化する

```shell
vault secrets enable -path=<有効にしたい任意のパス> kv
```

例）

```shell
vault secrets enable -path=secret kv
```

`vault secrets list`を実行し、指定したパスが一覧に出力されることを確認する。

## 機密情報のセット

プロジェクト ディレクトリの`valut`ディレクトリ内の環境別ディレクトリへ移動して各jsonファイルから値をロードさせる。

### 開発環境（dev）の場合

```shell
cd ./vault/dev
vault kv put secret/chloresto @mail.json
```

## アプリケーション用ポリシーの作成

```shell
vault policy write chloresto-read-policy chloresto-read-policy.hcl
```

## アプリケーション用トークンの作成

```shell
vault token create -policy=chloresto-read-policy
```

## データのリセット

**※暗号化されたデータがすべて消えてしまうので要注意。開発環境でやり直す時や、実運用環境での最終手段として使う。**

```shell
# 管理者として実行する。
systemctl stop vault
rm -rf /var/vault/data/*
systemctl start vault
```

Vaultサーバーの初期化を再実行する。
