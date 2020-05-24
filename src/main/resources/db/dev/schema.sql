-- Project Name : 地元の飲食店 テイクアウト注文支援サービス
-- Date/Time    : 2020/05/24 15:35:31
-- Author       : vagrant
-- RDBMS Type   : Oracle Database
-- Application  : A5:SQL Mk-2

-- メニュー掲載終了日
create table MENU_APPEAR_END_DAYS (
  MENU_HISTORY_ID NUMBER(11,0) not null
  , APPEAR_END_DATE DATE not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint MENU_APPEAR_END_DAYS_PKC primary key (MENU_HISTORY_ID)
) ;

-- メニュー説明
create table MENU_MEMOS (
  MENU_HISTORY_ID NUMBER(11,0) not null
  , MEMO VARCHAR2(100 CHAR) not null
  , constraint MENU_MEMOS_PKC primary key (MENU_HISTORY_ID)
) ;

-- メニュー名
create table MENU_NAMES (
  MENU_HISTORY_ID NUMBER(11,0) not null
  , MENU_NAME VARCHAR2(30 CHAR) not null
  , constraint MENU_NAMES_PKC primary key (MENU_HISTORY_ID)
) ;

-- メニュー写真
create table MENU_PHOTOS (
  ID NUMBER(11,0) not null
  , MENU_HISTORY_ID NUMBER(11,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , FILE_NAME VARCHAR2(255) not null
  , constraint MENU_PHOTOS_PKC primary key (ID)
) ;

alter table MENU_PHOTOS add constraint MENU_PHOTOS_IX1
  unique (MENU_HISTORY_ID,DISPLAY_ORDER) ;

alter table MENU_PHOTOS add constraint MENU_PHOTOS_IX2
  unique (MENU_HISTORY_ID,FILE_NAME) ;

-- メニュー価格
create table MENU_PRICES (
  MENU_HISTORY_ID NUMBER(11,0) not null
  , TAX_INCLUDED_PRICE NUMBER(4,0) not null
  , constraint MENU_PRICES_PKC primary key (MENU_HISTORY_ID)
) ;

-- 注文明細
create table ORDER_DETAILS (
  ORDER_DETAIL_ID NUMBER(11,0) not null
  , ORDER_ID NUMBER(11,0) not null
  , MENU_ID NUMBER(10,0) not null
  , MENU_NAME VARCHAR2(30 CHAR) not null
  , TAX_INCLUDED_PRICE NUMBER(4,0) not null
  , QUANTITY NUMBER(3,0) not null
  , constraint ORDER_DETAILS_PKC primary key (ORDER_DETAIL_ID)
) ;

create index ORDER_DETAILS_IX1
  on ORDER_DETAILS(ORDER_ID);

-- 注文回答メッセージ
create table ORDER_REPLY_MESSAGES (
  ORDER_ID NUMBER(11,0) not null
  , MESSAGE VARCHAR2(200 CHAR) not null
  , constraint ORDER_REPLY_MESSAGES_PKC primary key (ORDER_ID)
) ;

-- 注文キャンセル済み
create table ORDERS_CANCELED (
  ORDER_ID NUMBER(11,0) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint ORDERS_CANCELED_PKC primary key (ORDER_ID)
) ;

-- 注文提供済み
create table ORDERS_PROVIDED (
  ORDER_ID NUMBER(11,0) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint ORDERS_PROVIDED_PKC primary key (ORDER_ID)
) ;

-- 注文回答済み
create table ORDERS_REPLIED (
  ORDER_ID NUMBER(11,0) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint ORDERS_REPLIED_PKC primary key (ORDER_ID)
) ;

-- 飲食店アカウント
create table RESTO_ACCOUNTS (
  RESTAURANT_ID NUMBER(7,0) not null
  , USER_ID NUMBER(7,0) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint RESTO_ACCOUNTS_PKC primary key (RESTAURANT_ID)
) ;

create unique index RESTO_ACCOUNTS_IX1
  on RESTO_ACCOUNTS(USER_ID);

-- 飲食店住所
create table RESTO_ADDRESSES (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , ZIP_CODE VARCHAR2(7) not null
  , PREFECTURE_NAME VARCHAR2(5 CHAR) not null
  , CITY_NAME VARCHAR2(30 CHAR) not null
  , TOWN_NAME VARCHAR2(100 CHAR) not null
  , AFTER_TOWN_NAME VARCHAR2(100 CHAR) not null
  , constraint RESTO_ADDRESSES_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

create index RESTO_ADDRESSES_IX1
  on RESTO_ADDRESSES(PREFECTURE_NAME,CITY_NAME);

-- 飲食店定休日_月次曜日
create table RESTO_CLOSE_DAYS_MO_DOW (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , WEEK_OF_MONTH NUMBER(1,0) not null
  , DAY_OF_WEEK NUMBER(1,0) not null
  , constraint RESTO_CLOSE_DAYS_MO_DOW_PKC primary key (ID)
) ;

alter table RESTO_CLOSE_DAYS_MO_DOW add constraint RESTO_CLOSE_DAYS_MO_DOW_IX1
  unique (RESTAURANT_HISTORY_ID,WEEK_OF_MONTH,DAY_OF_WEEK) ;

-- 飲食店定休日_週次
create table RESTO_CLOSE_DAYS_WKLY (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DAY_OF_WEEK NUMBER(1,0) not null
  , constraint RESTO_CLOSE_DAYS_WKLY_PKC primary key (ID)
) ;

alter table RESTO_CLOSE_DAYS_WKLY add constraint RESTO_CLOSE_DAYS_WKLY_IX1
  unique (RESTAURANT_HISTORY_ID,DAY_OF_WEEK) ;

-- 飲食店定休日_月次日
create table "RESTO_CLOSΕ_DAYS_MO_DOM" (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DAY_OF_MONTH NUMBER(2,0) not null
  , constraint "RESTO_CLOSΕ_DAYS_MO_DOM_PKC" primary key (ID)
) ;

alter table "RESTO_CLOSΕ_DAYS_MO_DOM" add constraint "RESTO_CLOSΕ_DAYS_MO_DOM_IX1"
  unique (RESTAURANT_HISTORY_ID,DAY_OF_MONTH) ;

-- 飲食店外部リンク
create table RESTO_EXTERNAL_LINKS (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , URL VARCHAR2(2048) not null
  , constraint RESTO_EXTERNAL_LINKS_PKC primary key (ID)
) ;

alter table RESTO_EXTERNAL_LINKS add constraint RESTO_EXTERNAL_LINKS_IX1
  unique (RESTAURANT_HISTORY_ID,DISPLAY_ORDER) ;

alter table RESTO_EXTERNAL_LINKS add constraint RESTO_EXTERNAL_LINKS_IX2
  unique (RESTAURANT_HISTORY_ID,URL) ;

-- 飲食店FAX番号
create table RESTO_FAXNOES (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , FAX_NO VARCHAR2(16) not null
  , constraint RESTO_FAXNOES_PKC primary key (ID)
) ;

alter table RESTO_FAXNOES add constraint RESTO_FAXNOES_IX1
  unique (RESTAURANT_HISTORY_ID,DISPLAY_ORDER) ;

alter table RESTO_FAXNOES add constraint RESTO_FAXNOES_IX2
  unique (RESTAURANT_HISTORY_ID,FAX_NO) ;

-- 飲食店ジャンル
create table RESTO_GENRES (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , FOOT_GENRE_ID NUMBER(6,0) not null
  , constraint RESTO_GENRES_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

create index RESTO_GENRES_IX1
  on RESTO_GENRES(FOOT_GENRE_ID);

-- 飲食店ログイン要求
create table RESTO_LOGIN_REQUESTS (
  ID NUMBER(11,0) not null
  , MAIL_ADDRESS VARCHAR2(255 CHAR) not null
  , ACCESS_TOKEN VARCHAR2(36) not null
  , TOKEN_EXPIRATION_DATETIME TIMESTAMP not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint RESTO_LOGIN_REQUESTS_PKC primary key (ID)
) ;

alter table RESTO_LOGIN_REQUESTS add constraint RESTO_LOGIN_REQUESTS_IX1
  unique (ACCESS_TOKEN) ;

create index RESTO_LOGIN_REQUESTS_IX2
  on RESTO_LOGIN_REQUESTS(MAIL_ADDRESS);

-- 飲食店メッセージ
create table RESTO_MESSAGES (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , MESSAGE_FROM_RESTAURANT VARCHAR2(200 CHAR) not null
  , constraint RESTO_MESSAGES_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

-- 飲食店名
create table RESTO_NAMES (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , RESTAURANT_NAME VARCHAR2(50 CHAR) not null
  , constraint RESTO_NAMES_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

-- 飲食店営業時間
create table RESTO_OPEN_HOURS (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , START_TIME DATE not null
  , END_TIME DATE not null
  , constraint RESTO_OPEN_HOURS_PKC primary key (ID)
) ;

alter table RESTO_OPEN_HOURS add constraint RESTO_OPEN_HOURS_IX1
  unique (RESTAURANT_HISTORY_ID,START_TIME) ;

-- 飲食店注文可能
create table RESTO_ORDER_AVAILABILITY (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , constraint RESTO_ORDER_AVAILABILITY_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

-- 飲食店注文設定
create table RESTO_ORDER_SETTINGS (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , EARLIEST_READY_MINUTES NUMBER(2,0) not null
  , ORDER_AVAILABLE_FUTURE_DAYS NUMBER(2,0) not null
  , constraint RESTO_ORDER_SETTINGS_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

-- 飲食店写真
create table RESTO_PHOTOS (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , FILE_NAME VARCHAR2(255) not null
  , constraint RESTO_PHOTOS_PKC primary key (ID)
) ;

alter table RESTO_PHOTOS add constraint RESTO_PHOTOS_IX1
  unique (RESTAURANT_HISTORY_ID,DISPLAY_ORDER) ;

alter table RESTO_PHOTOS add constraint RESTO_PHOTOS_IX2
  unique (RESTAURANT_HISTORY_ID,FILE_NAME) ;

-- 飲食店検索キーワード
create table RESTO_SEARCH_KEYWORDS (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , SEARCH_KEY_WORD VARCHAR2(20) not null
  , constraint RESTO_SEARCH_KEYWORDS_PKC primary key (ID)
) ;

alter table RESTO_SEARCH_KEYWORDS add constraint RESTO_SEARCH_KEYWORDS_IX1
  unique (RESTAURANT_HISTORY_ID,DISPLAY_ORDER) ;

alter table RESTO_SEARCH_KEYWORDS add constraint RESTO_SEARCH_KEYWORDS_IX2
  unique (RESTAURANT_HISTORY_ID,SEARCH_KEY_WORD) ;

-- 飲食店電話番号
create table RESTO_TELNOES (
  ID NUMBER(11,0) not null
  , RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , TEL_NO VARCHAR2(16) not null
  , constraint RESTO_TELNOES_PKC primary key (ID)
) ;

alter table RESTO_TELNOES add constraint RESTO_TELNOES_IX1
  unique (RESTAURANT_HISTORY_ID,DISPLAY_ORDER) ;

alter table RESTO_TELNOES add constraint RESTO_TELNOES_IX2
  unique (RESTAURANT_HISTORY_ID,TEL_NO) ;

-- 飲食店臨時休業日
create table RESTO_TEMP_CLOSE_DAYS (
  ID NUMBER(11,0) not null
  , RESTAURANT_ID NUMBER(7,0) not null
  , CLOSE_DAY DATE not null
  , constraint RESTO_TEMP_CLOSE_DAYS_PKC primary key (ID)
) ;

alter table RESTO_TEMP_CLOSE_DAYS add constraint RESTO_TEMP_CLOSE_DAYS_IX1
  unique (RESTAURANT_ID,CLOSE_DAY) ;

create index RESTO_TEMP_CLOSE_DAYS_IX2
  on RESTO_TEMP_CLOSE_DAYS(RESTAURANT_ID);

-- 飲食店臨時営業日
create table RESTO_TEMP_OPEN_DAYS (
  ID NUMBER(11,0) not null
  , RESTAURANT_ID NUMBER(7,0) not null
  , OPEN_DAY DATE not null
  , START_TIME DATE not null
  , END_TIME DATE not null
  , constraint RESTO_TEMP_OPEN_DAYS_PKC primary key (ID)
) ;

alter table RESTO_TEMP_OPEN_DAYS add constraint RESTO_TEMP_OPEN_DAYS_IX1
  unique (RESTAURANT_ID,OPEN_DAY,START_TIME) ;

create index RESTO_TEMP_OPEN_DAYS_IX2
  on RESTO_TEMP_OPEN_DAYS(RESTAURANT_ID);

create index RESTO_TEMP_OPEN_DAYS_IX3
  on RESTO_TEMP_OPEN_DAYS(OPEN_DAY);

create index RESTO_TEMP_OPEN_DAYS_IX4
  on RESTO_TEMP_OPEN_DAYS(START_TIME,END_TIME);

-- ユーザー
create table USERS (
  USER_ID NUMBER(7,0) not null
  , MAIL_ADDRESS VARCHAR2(255 CHAR) not null
  , USER_ROLE VARCHAR2(100 CHAR) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint USERS_PKC primary key (USER_ID)
) ;

alter table USERS add constraint USERS_IX1
  unique (MAIL_ADDRESS,USER_ROLE) ;

-- 郵便番号
create table ZIP_CODES (
  JAPANESE_LOCAL_GOVERMENT_CODE VARCHAR2(5)
  , OLD_ZIP_CODE VARCHAR2(5)
  , ZIP_CODE VARCHAR2(7)
  , PREFECTURE_NAME_KANA VARCHAR2(10 CHAR)
  , CITY_NAME_KANA VARCHAR2(30 CHAR)
  , TOWN_NAME_KANA VARCHAR2(100 CHAR)
  , PREFECTURE_NAME VARCHAR2(5 CHAR)
  , CITY_NAME VARCHAR2(30 CHAR)
  , TOWN_NAME VARCHAR2(100 CHAR)
  , TOWN_DEVIDED_FLAG VARCHAR2(1)
  , ISSUED_PER_KOAZA_FLAG VARCHAR2(1)
  , CHOME_TOWN_FLAG VARCHAR2(1)
  , HAS_MULTIPLE_TOWN_FLAG VARCHAR2(1)
  , UPDATE_DISPLAY_FLAG VARCHAR2(1)
  , CHANGE_REASON_FLAG VARCHAR2(1)
) ;

alter table ZIP_CODES add constraint ZIP_CODES_IX1
  unique (JAPANESE_LOCAL_GOVERMENT_CODE,OLD_ZIP_CODE,ZIP_CODE,PREFECTURE_NAME_KANA,CITY_NAME_KANA,TOWN_NAME_KANA,PREFECTURE_NAME,CITY_NAME,TOWN_NAME,TOWN_DEVIDED_FLAG,ISSUED_PER_KOAZA_FLAG,CHOME_TOWN_FLAG,HAS_MULTIPLE_TOWN_FLAG,UPDATE_DISPLAY_FLAG,CHANGE_REASON_FLAG) ;

create index ZIP_CODES_IX2
  on ZIP_CODES(JAPANESE_LOCAL_GOVERMENT_CODE);

create index ZIP_CODES_IX3
  on ZIP_CODES(ZIP_CODE);

create index ZIP_CODES_IX4
  on ZIP_CODES(PREFECTURE_NAME);

create index ZIP_CODES_IX5
  on ZIP_CODES(PREFECTURE_NAME,CITY_NAME);

-- 飲食ジャンル
create table FOOD_GENRES (
  ID NUMBER(11,0) not null
  , FOOT_GENRE_ID NUMBER(6,0) not null
  , START_DATE DATE default '1900-01-01 00:00:00' not null
  , END_DATE DATE default '9999-12-31 23:59:59' not null
  , FOOD_GENRE_NAME VARCHAR2(100 CHAR) not null
  , GENRE_LEVEL NUMBER(1,0) not null
  , DISPLAY_ORDER NUMBER(3,0) default 1 not null
  , SUPERIOR_FOOT_GENRE_ID NUMBER(6,0) not null
  , constraint FOOD_GENRES_PKC primary key (ID)
) ;

alter table FOOD_GENRES add constraint FOOD_GENRES_IX1
  unique (SUPERIOR_FOOT_GENRE_ID,START_DATE,GENRE_LEVEL,DISPLAY_ORDER) ;

alter table FOOD_GENRES add constraint FOOD_GENRES_IX2
  unique (FOOT_GENRE_ID,START_DATE) ;

create index FOOD_GENRES_IX3
  on FOOD_GENRES(FOOT_GENRE_ID);

-- メニュー履歴
create table MENU_HISTORIES (
  MENU_HISTORY_ID NUMBER(11,0) not null
  , MENU_ID NUMBER(10,0) not null
  , APPEAR_START_DATE DATE not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint MENU_HISTORIES_PKC primary key (MENU_HISTORY_ID)
) ;

alter table MENU_HISTORIES add constraint MENU_HISTORIES_IX1
  unique (MENU_ID,APPEAR_START_DATE) ;

-- 注文
create table ORDERS (
  ORDER_ID NUMBER(11,0) not null
  , RESTAURANT_ID NUMBER(7,0) not null
  , CUSTOMER_NAME VARCHAR2(20 CHAR) not null
  , MAIL_ADDRESS VARCHAR2(255 CHAR) not null
  , TEL_NO VARCHAR2(16) not null
  , DESIRED_RECEIPT_DATETIME DATE not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint ORDERS_PKC primary key (ORDER_ID)
) ;

alter table ORDERS add constraint ORDERS_IX1
  unique (RESTAURANT_ID,CUSTOMER_NAME,MAIL_ADDRESS,TEL_NO,DESIRED_RECEIPT_DATETIME,REMOTE_IP_ADDRESS) ;

create index ORDERS_IX2
  on ORDERS(RESTAURANT_ID);

-- 飲食店履歴
create table RESTO_HISTORIES (
  RESTAURANT_HISTORY_ID NUMBER(11,0) not null
  , RESTAURANT_ID NUMBER(7,0) not null
  , START_DATE DATE default '1900-01-01 00:00:00' not null
  , END_DATE DATE default '9999-12-31 23:59:59' not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint RESTO_HISTORIES_PKC primary key (RESTAURANT_HISTORY_ID)
) ;

alter table RESTO_HISTORIES add constraint RESTO_HISTORIES_IX1
  unique (RESTAURANT_ID,START_DATE) ;

create index RESTO_HISTORIES_IX2
  on RESTO_HISTORIES(RESTAURANT_ID);

-- メニュー
create table MENU (
  MENU_ID NUMBER(10,0) not null
  , RESTAURANT_ID NUMBER(7,0) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint MENU_PKC primary key (MENU_ID)
) ;

create index MENU_IX1
  on MENU(RESTAURANT_ID);

-- 飲食店
create table RESTOS (
  RESTAURANT_ID NUMBER(7,0) not null
  , REMOTE_IP_ADDRESS VARCHAR2(15) not null
  , REGISTERED_TIMESTAMP TIMESTAMP not null
  , constraint RESTOS_PKC primary key (RESTAURANT_ID)
) ;

-- 住所
create view ADDRESSES as 
SELECT
  ROW_NUMBER() OVER ( 
    ORDER BY
      ZIP_CODE
      , PREFECTURE_NAME
      , CITY_NAME
      , TOWN_NAME
  ) AS ID
  , ZIP_CODE                                      -- 郵便番号
  , PREFECTURE_NAME                               -- 都道府県名
  , CITY_NAME                                     -- 市区町村名
  , TOWN_NAME                                     -- 町域名
FROM
  ( 
    SELECT
      ZIP_CODE
      , PREFECTURE_NAME
      , CITY_NAME
      , TOWN_NAME 
    FROM
      ( 
        SELECT
          ZIP_CODE
          , PREFECTURE_NAME
          , CITY_NAME
          , CASE 
            WHEN NVL(TOWN_NAME, '') = '以下に掲載がない場合' 
              THEN '' 
            ELSE TOWN_NAME 
            END AS TOWN_NAME
        FROM
          ( 
            SELECT
              ZIP_CODE
              , PREFECTURE_NAME
              , CITY_NAME
              , CASE 
                WHEN INSTR(TOWN_NAME, '（') > 0 
                AND ( 
                  INSTR(TOWN_NAME, '）') = 0 
                  OR INSTR(TOWN_NAME, '（') < INSTR(TOWN_NAME, '）')
                ) 
                  THEN SUBSTR(TOWN_NAME, 1, INSTR(TOWN_NAME, '（') - 1) 
                WHEN INSTR(TOWN_NAME, '）') > 0 
                OR INSTR(TOWN_NAME, '、') > 0 
                  THEN NULL 
                ELSE TOWN_NAME 
                END AS TOWN_NAME 
            FROM
              ZIP_CODES 
            WHERE
              UPDATE_DISPLAY_FLAG IN ('0', '1')
          ) SQ_A 
        WHERE
          TOWN_NAME IS NOT NULL
      ) SQ_B 
    GROUP BY
      ZIP_CODE
      , PREFECTURE_NAME
      , CITY_NAME
      , TOWN_NAME
  ) SQ_C

;

-- 市区町村
create view CITIES as 
SELECT DISTINCT
  SUBSTR(JAPANESE_LOCAL_GOVERMENT_CODE, 1, 2) AS PREFECTURE_CODE -- 都道府県コード
  , JAPANESE_LOCAL_GOVERMENT_CODE -- 地方公共団体コード
  , PREFECTURE_NAME -- 都道府県名
  , CITY_NAME -- 市区町村名
FROM
  ZIP_CODES
WHERE
  UPDATE_DISPLAY_FLAG IN ('0', '1')
;

-- 都道府県
create view PREFECTURES as 
SELECT DISTINCT
  SUBSTR(JAPANESE_LOCAL_GOVERMENT_CODE, 1, 2) AS PREFECTURE_CODE -- 都道府県コード
  , PREFECTURE_NAME -- 都道府県名
FROM
  ZIP_CODES
WHERE
  UPDATE_DISPLAY_FLAG IN ('0', '1')
;

create unique index USERS_IX2
  on USERS(MAIL_ADDRESS);

comment on table MENU_APPEAR_END_DAYS is 'メニュー掲載終了日	 ユーザーに入力してもらう項目のため、履歴テーブルから外出しする';
comment on column MENU_APPEAR_END_DAYS.MENU_HISTORY_ID is 'メニュー履歴ID';
comment on column MENU_APPEAR_END_DAYS.APPEAR_END_DATE is '掲載終了日';
comment on column MENU_APPEAR_END_DAYS.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column MENU_APPEAR_END_DAYS.REGISTERED_TIMESTAMP is '登録日時';

comment on table MENU_MEMOS is 'メニュー説明';
comment on column MENU_MEMOS.MENU_HISTORY_ID is 'メニュー履歴ID';
comment on column MENU_MEMOS.MEMO is 'メニュー説明';

comment on table MENU_NAMES is 'メニュー名';
comment on column MENU_NAMES.MENU_HISTORY_ID is 'メニュー履歴ID';
comment on column MENU_NAMES.MENU_NAME is 'メニュー名';

comment on table MENU_PHOTOS is 'メニュー写真';
comment on column MENU_PHOTOS.ID is 'ID';
comment on column MENU_PHOTOS.MENU_HISTORY_ID is 'メニュー履歴ID';
comment on column MENU_PHOTOS.DISPLAY_ORDER is '表示順';
comment on column MENU_PHOTOS.FILE_NAME is 'ファイル名';

comment on table MENU_PRICES is 'メニュー価格';
comment on column MENU_PRICES.MENU_HISTORY_ID is 'メニュー履歴ID';
comment on column MENU_PRICES.TAX_INCLUDED_PRICE is '税込価格';

comment on table ORDER_DETAILS is '注文明細';
comment on column ORDER_DETAILS.ORDER_DETAIL_ID is '注文明細ID';
comment on column ORDER_DETAILS.ORDER_ID is '注文ID';
comment on column ORDER_DETAILS.MENU_ID is 'メニューID';
comment on column ORDER_DETAILS.MENU_NAME is 'メニュー名';
comment on column ORDER_DETAILS.TAX_INCLUDED_PRICE is '税込価格';
comment on column ORDER_DETAILS.QUANTITY is '数量';

comment on table ORDER_REPLY_MESSAGES is '注文回答メッセージ	 利用客への返信時メッセージを保持する';
comment on column ORDER_REPLY_MESSAGES.ORDER_ID is '注文ID';
comment on column ORDER_REPLY_MESSAGES.MESSAGE is 'メッセージ';

comment on table ORDERS_CANCELED is '注文キャンセル済み	 キャンセルした注文を保持する';
comment on column ORDERS_CANCELED.ORDER_ID is '注文ID';
comment on column ORDERS_CANCELED.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column ORDERS_CANCELED.REGISTERED_TIMESTAMP is '登録日時';

comment on table ORDERS_PROVIDED is '注文提供済み	 利用客へ提供済みの注文を保持する';
comment on column ORDERS_PROVIDED.ORDER_ID is '注文ID';
comment on column ORDERS_PROVIDED.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column ORDERS_PROVIDED.REGISTERED_TIMESTAMP is '登録日時';

comment on table ORDERS_REPLIED is '注文回答済み	 利用客へ返信済みの注文を保持する';
comment on column ORDERS_REPLIED.ORDER_ID is '注文ID';
comment on column ORDERS_REPLIED.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column ORDERS_REPLIED.REGISTERED_TIMESTAMP is '登録日時';

comment on table RESTO_ACCOUNTS is '飲食店アカウント	 飲食店のアカウントを管理する';
comment on column RESTO_ACCOUNTS.RESTAURANT_ID is '飲食店ID';
comment on column RESTO_ACCOUNTS.USER_ID is 'ユーザーID';
comment on column RESTO_ACCOUNTS.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column RESTO_ACCOUNTS.REGISTERED_TIMESTAMP is '登録日時';

comment on table RESTO_ADDRESSES is '飲食店住所';
comment on column RESTO_ADDRESSES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_ADDRESSES.ZIP_CODE is '郵便番号';
comment on column RESTO_ADDRESSES.PREFECTURE_NAME is '都道府県名';
comment on column RESTO_ADDRESSES.CITY_NAME is '市区町村名';
comment on column RESTO_ADDRESSES.TOWN_NAME is '町域名';
comment on column RESTO_ADDRESSES.AFTER_TOWN_NAME is '町域以降';

comment on table RESTO_CLOSE_DAYS_MO_DOW is '飲食店定休日_月次曜日	 飲食店の定休日（月次の曜日）を保持する（MO: MONTHLY、DOW: DAYS_OF_WEEK）';
comment on column RESTO_CLOSE_DAYS_MO_DOW.ID is 'ID';
comment on column RESTO_CLOSE_DAYS_MO_DOW.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_CLOSE_DAYS_MO_DOW.WEEK_OF_MONTH is '月の週番号';
comment on column RESTO_CLOSE_DAYS_MO_DOW.DAY_OF_WEEK is '曜日';

comment on table RESTO_CLOSE_DAYS_WKLY is '飲食店定休日_週次	 飲食店の定休日（週次の曜日）を保持する（WKLY: WEEKLY）';
comment on column RESTO_CLOSE_DAYS_WKLY.ID is 'ID';
comment on column RESTO_CLOSE_DAYS_WKLY.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_CLOSE_DAYS_WKLY.DAY_OF_WEEK is '曜日';

comment on table "RESTO_CLOSΕ_DAYS_MO_DOM" is '飲食店定休日_月次日	 飲食店の定休日（月次の日）を保持する（MO: MONTHLY、DM: DAYS_OFMONTH）';
comment on column "RESTO_CLOSΕ_DAYS_MO_DOM".ID is 'ID';
comment on column "RESTO_CLOSΕ_DAYS_MO_DOM".RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column "RESTO_CLOSΕ_DAYS_MO_DOM".DAY_OF_MONTH is '日';

comment on table RESTO_EXTERNAL_LINKS is '飲食店外部リンク	 公式ホームページやSNSのリンクを保持する';
comment on column RESTO_EXTERNAL_LINKS.ID is 'ID';
comment on column RESTO_EXTERNAL_LINKS.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_EXTERNAL_LINKS.DISPLAY_ORDER is '表示順';
comment on column RESTO_EXTERNAL_LINKS.URL is 'URL';

comment on table RESTO_FAXNOES is '飲食店FAX番号';
comment on column RESTO_FAXNOES.ID is 'ID';
comment on column RESTO_FAXNOES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_FAXNOES.DISPLAY_ORDER is '表示順';
comment on column RESTO_FAXNOES.FAX_NO is 'FAX番号';

comment on table RESTO_GENRES is '飲食店ジャンル	 飲食店のジャンルを保持する';
comment on column RESTO_GENRES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_GENRES.FOOT_GENRE_ID is '飲食ジャンルID';

comment on table RESTO_LOGIN_REQUESTS is '飲食店ログイン要求	 飲食店ログイン要求イベントを保持する';
comment on column RESTO_LOGIN_REQUESTS.ID is 'ID';
comment on column RESTO_LOGIN_REQUESTS.MAIL_ADDRESS is 'メールアドレス';
comment on column RESTO_LOGIN_REQUESTS.ACCESS_TOKEN is 'アクセストークン';
comment on column RESTO_LOGIN_REQUESTS.TOKEN_EXPIRATION_DATETIME is 'アクセストークン有効期限';
comment on column RESTO_LOGIN_REQUESTS.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column RESTO_LOGIN_REQUESTS.REGISTERED_TIMESTAMP is '登録日時';

comment on table RESTO_MESSAGES is '飲食店メッセージ	 飲食店からのメッセージを保持する';
comment on column RESTO_MESSAGES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_MESSAGES.MESSAGE_FROM_RESTAURANT is '飲食店からのメッセージ';

comment on table RESTO_NAMES is '飲食店名	 飲食店名を保持する';
comment on column RESTO_NAMES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_NAMES.RESTAURANT_NAME is '飲食店名';

comment on table RESTO_OPEN_HOURS is '飲食店営業時間	 飲食店の営業時間を保持する';
comment on column RESTO_OPEN_HOURS.ID is 'ID';
comment on column RESTO_OPEN_HOURS.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_OPEN_HOURS.START_TIME is '開始時刻';
comment on column RESTO_OPEN_HOURS.END_TIME is '終了時刻';

comment on table RESTO_ORDER_AVAILABILITY is '飲食店注文可能	 ネットでの注文を受け付ける場合に、このテーブルへ該当飲食店のID（飲食店履歴ID）を保持する';
comment on column RESTO_ORDER_AVAILABILITY.RESTAURANT_HISTORY_ID is '飲食店履歴ID';

comment on table RESTO_ORDER_SETTINGS is '飲食店注文設定	 注文時の設定を保持する';
comment on column RESTO_ORDER_SETTINGS.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_ORDER_SETTINGS.EARLIEST_READY_MINUTES is '最短受取可能時間	 注文入力時点で最短◯分後以降で受取可能とする。（受取可能時間の目安）';
comment on column RESTO_ORDER_SETTINGS.ORDER_AVAILABLE_FUTURE_DAYS is '注文可能未来日	 ◯日先の注文まで受け付けるか';

comment on table RESTO_PHOTOS is '飲食店写真	 店舗や看板メニューの写真のファイル名を保持する';
comment on column RESTO_PHOTOS.ID is 'ID';
comment on column RESTO_PHOTOS.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_PHOTOS.DISPLAY_ORDER is '表示順';
comment on column RESTO_PHOTOS.FILE_NAME is 'ファイル名';

comment on table RESTO_SEARCH_KEYWORDS is '飲食店検索キーワード	 検索用のキーワードを保持する';
comment on column RESTO_SEARCH_KEYWORDS.ID is 'ID';
comment on column RESTO_SEARCH_KEYWORDS.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_SEARCH_KEYWORDS.DISPLAY_ORDER is '表示順';
comment on column RESTO_SEARCH_KEYWORDS.SEARCH_KEY_WORD is '検索キーワード';

comment on table RESTO_TELNOES is '飲食店電話番号';
comment on column RESTO_TELNOES.ID is 'ID';
comment on column RESTO_TELNOES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_TELNOES.DISPLAY_ORDER is '表示順';
comment on column RESTO_TELNOES.TEL_NO is '電話番号';

comment on table RESTO_TEMP_CLOSE_DAYS is '飲食店臨時休業日	 飲食店の臨時休業日を保持する';
comment on column RESTO_TEMP_CLOSE_DAYS.ID is 'ID';
comment on column RESTO_TEMP_CLOSE_DAYS.RESTAURANT_ID is '飲食店ID';
comment on column RESTO_TEMP_CLOSE_DAYS.CLOSE_DAY is '休業日';

comment on table RESTO_TEMP_OPEN_DAYS is '飲食店臨時営業日	 飲食店の臨時営業日を保持する';
comment on column RESTO_TEMP_OPEN_DAYS.ID is 'ID';
comment on column RESTO_TEMP_OPEN_DAYS.RESTAURANT_ID is '飲食店ID';
comment on column RESTO_TEMP_OPEN_DAYS.OPEN_DAY is '営業日';
comment on column RESTO_TEMP_OPEN_DAYS.START_TIME is '開始時刻';
comment on column RESTO_TEMP_OPEN_DAYS.END_TIME is '終了時刻';

comment on table USERS is 'ユーザー	 システム上のユーザーを管理するエンティティ';
comment on column USERS.USER_ID is 'ユーザーID';
comment on column USERS.MAIL_ADDRESS is 'メールアドレス';
comment on column USERS.USER_ROLE is 'ユーザーロール';
comment on column USERS.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column USERS.REGISTERED_TIMESTAMP is '登録日時';

comment on table ZIP_CODES is '郵便番号	 郵便局が提供している郵便番号データ
http://www.post.japanpost.jp/zipcode/dl/readm.htm';
comment on column ZIP_CODES.JAPANESE_LOCAL_GOVERMENT_CODE is '全国地方公共団体コード	 JIS X0401、X0402';
comment on column ZIP_CODES.OLD_ZIP_CODE is '旧郵便番号';
comment on column ZIP_CODES.ZIP_CODE is '郵便番号';
comment on column ZIP_CODES.PREFECTURE_NAME_KANA is '都道府県名カナ	 半角カタカナ（コード順に掲載）';
comment on column ZIP_CODES.CITY_NAME_KANA is '市区町村名カナ	 半角カタカナ（コード順に掲載）';
comment on column ZIP_CODES.TOWN_NAME_KANA is '町域名カナ	 半角カタカナ（五十音順に掲載）';
comment on column ZIP_CODES.PREFECTURE_NAME is '都道府県名	 漢字（コード順に掲載）';
comment on column ZIP_CODES.CITY_NAME is '市区町村名	 漢字（コード順に掲載）';
comment on column ZIP_CODES.TOWN_NAME is '町域名	 漢字（五十音順に掲載）';
comment on column ZIP_CODES.TOWN_DEVIDED_FLAG is '複数番号付与町域フラグ	 一町域が二以上の郵便番号で表される場合の表示（「1」は該当、「0」は該当せず）';
comment on column ZIP_CODES.ISSUED_PER_KOAZA_FLAG is '小字毎番号付与フラグ	 小字毎に番地が起番されている町域の表示（「1」は該当、「0」は該当せず）';
comment on column ZIP_CODES.CHOME_TOWN_FLAG is '丁目保有町域フラグ	 丁目を有する町域の場合の表示（「1」は該当、「0」は該当せず）';
comment on column ZIP_CODES.HAS_MULTIPLE_TOWN_FLAG is '複数町域保有フラグ	 一つの郵便番号で二以上の町域を表す場合の表示（「1」は該当、「0」は該当せず）';
comment on column ZIP_CODES.UPDATE_DISPLAY_FLAG is '更新の表示	 「0」は変更なし、「1」は変更あり、「2」廃止（廃止データのみ使用）';
comment on column ZIP_CODES.CHANGE_REASON_FLAG is '変更理由	 「0」は変更なし、「1」市政・区政・町政・分区・政令指定都市施行、「2」住居表示の実施、「3」区画整理、「4」郵便区調整等、「5」訂正、「6」廃止（廃止データのみ使用）';

comment on table FOOD_GENRES is '飲食ジャンル';
comment on column FOOD_GENRES.ID is 'ID';
comment on column FOOD_GENRES.FOOT_GENRE_ID is '飲食ジャンルID';
comment on column FOOD_GENRES.START_DATE is '開始日';
comment on column FOOD_GENRES.END_DATE is '終了日';
comment on column FOOD_GENRES.FOOD_GENRE_NAME is '飲食ジャンル名';
comment on column FOOD_GENRES.GENRE_LEVEL is 'ジャンル階層';
comment on column FOOD_GENRES.DISPLAY_ORDER is '表示順';
comment on column FOOD_GENRES.SUPERIOR_FOOT_GENRE_ID is '上位飲食ジャンルID	 最上位は0';

comment on table MENU_HISTORIES is 'メニュー履歴';
comment on column MENU_HISTORIES.MENU_HISTORY_ID is 'メニュー履歴ID';
comment on column MENU_HISTORIES.MENU_ID is 'メニューID';
comment on column MENU_HISTORIES.APPEAR_START_DATE is '掲載開始日';
comment on column MENU_HISTORIES.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column MENU_HISTORIES.REGISTERED_TIMESTAMP is '登録日時';

comment on table ORDERS is '注文	 利用者注文のルートエンティティ';
comment on column ORDERS.ORDER_ID is '注文ID';
comment on column ORDERS.RESTAURANT_ID is '飲食店ID';
comment on column ORDERS.CUSTOMER_NAME is '顧客名';
comment on column ORDERS.MAIL_ADDRESS is 'メールアドレス';
comment on column ORDERS.TEL_NO is '電話番号';
comment on column ORDERS.DESIRED_RECEIPT_DATETIME is '受取希望日時';
comment on column ORDERS.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column ORDERS.REGISTERED_TIMESTAMP is '登録日時';

comment on table RESTO_HISTORIES is '飲食店履歴	 飲食店情報変更履歴のルートエンティティ';
comment on column RESTO_HISTORIES.RESTAURANT_HISTORY_ID is '飲食店履歴ID';
comment on column RESTO_HISTORIES.RESTAURANT_ID is '飲食店ID';
comment on column RESTO_HISTORIES.START_DATE is '開始日';
comment on column RESTO_HISTORIES.END_DATE is '終了日';
comment on column RESTO_HISTORIES.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column RESTO_HISTORIES.REGISTERED_TIMESTAMP is '登録日時';

comment on table MENU is 'メニュー	 メニューエンティティのルート';
comment on column MENU.MENU_ID is 'メニューID';
comment on column MENU.RESTAURANT_ID is '飲食店ID';
comment on column MENU.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column MENU.REGISTERED_TIMESTAMP is '登録日時';

comment on table RESTOS is '飲食店	 飲食店エンティティのルート';
comment on column RESTOS.RESTAURANT_ID is '飲食店ID';
comment on column RESTOS.REMOTE_IP_ADDRESS is '登録元IPアドレス';
comment on column RESTOS.REGISTERED_TIMESTAMP is '登録日時';

