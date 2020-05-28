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
	, CREATE ANY INDEX			, ALTER ANY INDEX					, DROP ANY INDEX
	, CREATE MATERIALIZED VIEW	, ALTER ANY MATERIALIZED VIEW	, DROP ANY MATERIALIZED VIEW
--	, CREATE ANY PROCEDURE		, ALTER ANY PROCEDURE			, DROP ANY PROCEDURE				, EXECUTE ANY PROCEDURE
	, CREATE ANY SEQUENCE		, ALTER ANY SEQUENCE				, DROP ANY SEQUENCE
	, CREATE ANY TABLE			, ALTER ANY TABLE					, DROP ANY TABLE
--	, CREATE ANY VIEW				, ALTER ANY VIEW					, DROP ANY VIEW
--	, CREATE PROCEDURE
	, CREATE SEQUENCE
	, CREATE TABLE
	, CREATE VIEW
	, INSERT ANY TABLE	, SELECT ANY TABLE	, UPDATE ANY TABLE	, DELETE ANY TABLE			, LOCK ANY TABLE
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
CREATE SEQUENSE RESTOS_SEQ;
CREATE SEQUENSE RESTO_HISTORIES_SEQ;
CREATE SEQUENSE USERS_SEQ;
CREATE SEQUENSE RESTO_LOGIN_REQUESTS_SEQ;
CREATE SEQUENSE RESTO_TEMP_OPEN_DAYS_SEQ;
CREATE SEQUENSE RESTO_TEMP_CLOSE_DAYS_SEQ;
CREATE SEQUENSE RESTO_OPEN_HOURS_SEQ;
CREATE SEQUENSE RESTO_CLOSE_DAYS_WKLY_SEQ;
CREATE SEQUENSE RESTO_CLOSE_DAYS_MO_DOW_SEQ;
CREATE SEQUENSE RESTO_CLOSΕ_DAYS_MO_DOM_SEQ;
CREATE SEQUENSE RESTO_TELNOES_SEQ;
CREATE SEQUENSE RESTO_FAXNOES_SEQ;
CREATE SEQUENSE RESTO_EXTERNAL_LINKS_SEQ;
CREATE SEQUENSE RESTO_PHOTOS_SEQ;
CREATE SEQUENSE RESTO_SEARCH_KEYWORDS_SEQ;
CREATE SEQUENSE MENU_SEQ;
CREATE SEQUENSE MENU_HISTORIES_SEQ;
CREATE SEQUENSE MENU_PHOTOS_SEQ;
CREATE SEQUENSE ORDERS_SEQ;
CREATE SEQUENSE ORDER_DETAILS_SEQ;

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

# HashiCorp Consul

## インストール

https://learn.hashicorp.com/consul/datacenter-deploy/deployment-guide#install-consul

バイナリをダウンロードして、PATHの通ったディレクトリへ配置する。

```
sudo chown root:root ./consul
sudo mv ./consul /usr/local/bin/
```

`consul --version`コマンドを実行してバージョンが返ってくればインストールOK。

続けて以下のコマンドを実行する。

```
consul -autocomplete-install
complete -C /usr/local/bin/consul consul
sudo useradd --system --home /etc/consul.d --shell /bin/false consul
sudo mkdir --parents /opt/consul
sudo chown --recursive consul:consul /opt/consul
```

## インスタンスの起動

以下の手順に従ってサービス化する。

https://learn.hashicorp.com/consul/datacenter-deploy/deployment-guide#configure-systemd

### systemdユニットファイルの作成

`/etc/systemd/system/consul.service`を作成する。

```
[Unit]
Description="HashiCorp Consul - A service mesh solution"
Documentation=https://www.consul.io/
Requires=network-online.target
After=network-online.target
ConditionFileNotEmpty=/etc/consul.d/consul.hcl

[Service]
Type=notify
User=consul
Group=consul
ExecStart=/usr/local/bin/consul agent -config-dir=/etc/consul.d/
ExecReload=/usr/local/bin/consul reload
ExecStop=/usr/local/bin/consul leave
KillMode=process
Restart=on-failure
LimitNOFILE=65536

[Install]
WantedBy=multi-user.target
```

### Consulの設定

以下のコマンドを順に実行する。

```
sudo mkdir --parents /etc/consul.d
sudo touch /etc/consul.d/consul.hcl
sudo chown --recursive consul:consul /etc/consul.d
sudo chmod 640 /etc/consul.d/consul.hcl
```

`consul keygen`を実行して設定ファイルに記述する文字列を取得する。

`/etc/consul.d/consul.hcl` を以下のように編集する。

```
datacenter = "dc1"
data_dir = "/opt/consul"
encrypt = "<`consul keygen`で生成された文字列>"
bind_addr = "<サーバーのプライベートIPアドレス>"
retry_join = ["<サーバーのプライベートIPアドレス>"]
```

※IPアドレスを設定しないと、`Multiple private IPv4 addresses found. Please configure one with 'bind' and/or 〜`が発生した。

### Consulサーバーの設定

以下のコマンドを順に実行する。

```
sudo mkdir --parents /etc/consul.d
sudo touch /etc/consul.d/server.hcl
sudo chown --recursive consul:consul /etc/consul.d
sudo chmod 640 /etc/consul.d/server.hcl
```

`/etc/consul.d/server.hcl`を以下のように編集する。

```
server = true
bootstrap_expect = 1
```

※公式ページは`bootstrap_expect = 3`となっているが、1台だけで稼働させるので1にする。

### Consulサービスの起動

以下のコマンドを実行する。

```
sudo systemctl daemon-reload
sudo systemctl enable consul
sudo systemctl start consul
sudo systemctl status consul
```

# HashiCorp Vault

https://learn.hashicorp.com/vault/operations/ops-deployment-guide

## インストール

バイナリをダウンロードして、PATHの通ったディレクトリへ配置する。

```
sudo chown root:root ./vault
sudo mv ./vault /usr/local/bin/
```

`vault --version`コマンドを実行してバージョン情報が返ってくればインストールOK。

続けて以下のコマンドを実行する。

```
vault -autocomplete-install
complete -C /usr/local/bin/vault vault
sudo setcap cap_ipc_lock=+ep /usr/local/bin/vault
sudo useradd --system --home /etc/vault.d --shell /bin/false vault
```

## systemdユニットファイルの作成

`/etc/systemd/system/vault.service`を作成する。

```
[Unit]
Description="HashiCorp Vault - A tool for managing secrets"
Documentation=https://www.vaultproject.io/docs/
Requires=network-online.target
After=network-online.target
ConditionFileNotEmpty=/etc/vault.d/vault.hcl
StartLimitIntervalSec=60
StartLimitBurst=3

[Service]
User=vault
Group=vault
ProtectSystem=full
ProtectHome=read-only
PrivateTmp=yes
PrivateDevices=yes
SecureBits=keep-caps
AmbientCapabilities=CAP_IPC_LOCK
Capabilities=CAP_IPC_LOCK+ep
CapabilityBoundingSet=CAP_SYSLOG CAP_IPC_LOCK
NoNewPrivileges=yes
ExecStart=/usr/local/bin/vault server -config=/etc/vault.d/vault.hcl
ExecReload=/bin/kill --signal HUP $MAINPID
KillMode=process
KillSignal=SIGINT
Restart=on-failure
RestartSec=5
TimeoutStopSec=30
StartLimitInterval=60
StartLimitIntervalSec=60
StartLimitBurst=3
LimitNOFILE=65536
LimitMEMLOCK=infinity

[Install]
WantedBy=multi-user.target
```

## Consulの設定変更

`/etc/consul.d/acl.hcl`を作成する。

```
acl {
 enabled = true
 default_policy = "deny"
 enable_token_persistence = true
}
```

Consulサービスを再起動する

```
sudo systemctl restart consul
```

以下のコマンドを実行して Consul ACLの管理トークンを取得する。

```
consul acl bootstrap
```

`SecretID`の値を控えておく。

環境変数`CONSUL_MGMT_TOKEN`をセットする。

```
export CONSUL_MGMT_TOKEN="<上記のSecretIDの文字列>"
```

`node-policy.hcl`を作成する。

※パスの記載がなかったので、`./node-policy.hcl`とした。

```
agent_prefix "" {
 policy = "write"
}
node_prefix "" {
 policy = "write"
}
service_prefix "" {
 policy = "read"
}
session_prefix "" {
 policy = "read"
}
```

以下のコマンドを実行して`Consul node ACL Policy`を作成する。

```
consul acl policy create \
     -token=${CONSUL_MGMT_TOKEN} \
     -name node-policy \
     -rules @node-policy.hcl
```

以下のコマンドを実行してノード トークンを作成する。

```
consul acl token create \
     -token=${CONSUL_MGMT_TOKEN} \
     -description "node token" \
     -policy-name node-policy
```

`SecretID`の値を控えておく。

以下のコマンドを実行して、すべてのConsulサーバーにノード トークンを設定する。

```
consul acl set-agent-token \
     -token=${CONSUL_MGMT_TOKEN} \
     agent "<Node Token SecretID>"
```

`vault-policy.hcl`を作成する。

※パスの記載がないので`./vault-policy.hcl`とした。

```
key_prefix "vault/" {
 policy = "write"
}
node_prefix "" {
 policy = "write"
}
service "vault" {
 policy = "write"
}
agent_prefix "" {
 policy = "write"
}
session_prefix "" {
 policy = "write"
}
```

以下のコマンドを実行して`Consul Vault ACL Policy`を作成する。

```
consul acl policy create \
     -token=${CONSUL_MGMT_TOKEN} \
     -name vault-policy \
     -rules @vault-policy.hcl
```

以下のコマンドを実行してVaultのサービス トークンを取得する。

```
consul acl token create \
     -token=${CONSUL_MGMT_TOKEN} \
     -description "Token for Vault Service" \
     -policy-name vault-policy
```

`SecretID`の値を控えておく。

## Vaultの設定

以下のコマンドを順に実行する。

```
sudo mkdir --parents /etc/vault.d
sudo touch /etc/vault.d/vault.hcl
sudo chown --recursive vault:vault /etc/vault.d
sudo chmod 640 /etc/vault.d/vault.hcl
```

`/etc/vault.d/vault.hcl`を編集する。

```
listener "tcp" {
 address     = "127.0.0.1:8200"
 tls_disable = 1
}

storage "consul" {
  address = "127.0.0.1:8500"
  path    = "vault/"
  token = "<Consulの設定で取得したVaultのサービス トークン>"
}

api_addr = "http://127.0.0.1:8200"
```

※ローカルで動かすので公式ページとは設定を変えている。

## サービスを起動する

以下のコマンドを実行する。

```
sudo systemctl daemon-reload
sudo systemctl enable vault
sudo systemctl start vault
sudo systemctl status vault
```
