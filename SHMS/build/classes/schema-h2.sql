
drop table USER;
CREATE TABLE USER (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	ROC_ID VARCHAR NOT NULL,
	ACCOUNT VARCHAR NOT NULL,
	NAME VARCHAR NOT NULL,
	UNIT_ID VARCHAR NOT NULL,
	JOB_NAME VARCHAR NOT NULL,
	JOB_LEVEL INT NOT NULL,
	PHONE VARCHAR,
	EMAIL VARCHAR,
	BIRTHDAY VARCHAR,
	PAGER VARCHAR,
	TEL VARCHAR,
	IS_LEAVE INT NOT NULL
);

drop table UNIT;
CREATE TABLE UNIT (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	UNIT_ID VARCHAR NOT NULL,
	UNIT_NAME VARCHAR NOT NULL,
	TEL VARCHAR,
	MANAGER VARCHAR,
	SAVE_MANAGER VARCHAR,
	FIRE_HELPER VARCHAR,
	HELPER VARCHAR,
	AFFAIRS VARCHAR
);

drop table CONFIG;
CREATE TABLE CONFIG (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	CFG_KEY VARCHAR NOT NULL,
	CFG_TYPE VARCHAR NOT NULL,
	CFG_NAME VARCHAR NOT NULL,
	CFG_VALUE VARCHAR NOT NULL,
	CFG_MEMO VARCHAR NOT NULL,
	CFG_IN_USE	 INT NOT NULL,
	CREATE_ID VARCHAR NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	EDIT_ID VARCHAR,
	EDIT_TIME TIMESTAMP
);

drop table AUTHORIZASTION;
CREATE TABLE AUTHORIZASTION (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	MENU_ID BIGINT NOT NULL,
	AUTH_LV  INT NOT NULL
);

drop table MENU;
CREATE TABLE MENU (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	MENU_NAME VARCHAR NOT NULL,
	MENU_URL VARCHAR,
	MENU_TIER_TWO BIGINT,
	MENU_ORDER INT NOT NULL,
	MENU_API_URL VARCHAR
);


drop table CERTIFICATE;
CREATE TABLE CERTIFICATE (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	ROC_ID VARCHAR NOT NULL,
	CERTIFICATE_TYPE VARCHAR NOT NULL,
	CERTIFICATE_NAME VARCHAR NOT NULL,
	CERTIFICATE_UNIT VARCHAR NOT NULL,
	GET_DATE DATE NOT NULL,
	GET_FEE INT,
	GET_TRAIN_UNIT VARCHAR NOT NULL,	
	REVIEW_ID VARCHAR,
	REVIEW_TIME TIMESTAMP,
	IS_RESPONSIBLE INT NOT NULL,
	CREATE_ID VARCHAR NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	EDIT_ID VARCHAR,
	EDIT_TIME TIMESTAMP
);


drop table CERTIFICATE_RESPONSIBLE_TIME;
CREATE TABLE CERTIFICATE_RESPONSIBLE_TIME (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	ROC_ID VARCHAR NOT NULL,
	CERTIFICATE_ID VARCHAR NOT NULL,
	START_DATE DATE NOT NULL,
	END_DATE DATE NOT NULL
);


drop table EVENT_SAFE_NOTIFICATION;
CREATE TABLE EVENT_SAFE_NOTIFICATION (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	EVENT_NAME VARCHAR NOT NULL,
	CREATE_ID VARCHAR NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	EDIT_ID VARCHAR,
	EDIT_TIME TIMESTAMP
);

drop table EVENT_SAFE_NOTIFICATION_BACK;
CREATE TABLE EVENT_SAFE_NOTIFICATION_BACK (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	EVENT_IT VARCHAR NOT NULL,
	UNIT_ID VARCHAR NOT NULL,
	BACK_TIME TIMESTAMP NOT NULL,
	IS_SAFE INT NOT NULL,
	EFFECT_TYPE VARCHAR,
	MEMO VARCHAR,
	REVIEW_ID VARCHAR,
	REVIEW_TIME TIMESTAMP,	
	CREATE_ID VARCHAR NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	EDIT_ID VARCHAR,
	EDIT_TIME TIMESTAMP
);


drop table LOGIN_LOG;
CREATE TABLE LOGIN_LOG (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	ACCOUNT VARCHAR NOT NULL,
	LOGIN_TIME TIMESTAMP NOT NULL
);

drop table ERROR_LOG;
CREATE TABLE ERROR_LOG (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	ERROR_CLASS VARCHAR,
	ERROR_MSG VARCHAR,
	ERROR_TIME TIMESTAMP NOT NULL
);

drop table ANNOUNCEMENT;
CREATE TABLE ANNOUNCEMENT (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	ANNOUNCEMENT_NAME VARCHAR NOT NULL,
	ANNOUNCEMENT_DATE DATE NOT NULL,
	FILE_NAME VARCHAR NOT NULL,
	FILE_PATH VARCHAR NOT NULL,
	CREATE_ID VARCHAR NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	EDIT_ID VARCHAR,
	EDIT_TIME TIMESTAMP
);

drop table PHONE;
CREATE TABLE PHONE (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	TITLE VARCHAR NOT NULL,
	CONTENT VARCHAR NOT NULL,
	RECIPIENT VARCHAR NOT NULL,
	SEND_TIME TIMESTAMP NOT NULL,
	CREATE_ID VARCHAR NOT NULL
);

drop table MAIL;
CREATE TABLE MAIL (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	TITLE VARCHAR NOT NULL,
	CONTENT VARCHAR NOT NULL,
	RECIPIENT VARCHAR NOT NULL,
	SEND_TIME TIMESTAMP NOT NULL,
	CREATE_ID VARCHAR NOT NULL
);

drop table BATCH_LOG;
CREATE TABLE BATCH_LOG (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	BATCH_ID BIGINT NOT NULL,
	BATCH_NAME VARCHAR NOT NULL,
	BATCH_RESULT VARCHAR NOT NULL,
	BATCH_BEGIN_TIME TIMESTAMP,
	BATCH_END_TIME TIMESTAMP
);

drop table BATCH_SETTING;
CREATE TABLE BATCH_SETTING (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	BATCH_NAME VARCHAR NOT NULL,
	BATCH_CLASS VARCHAR NOT NULL,
	BATCH_TYPE INT,
	BATCH_TYPE_1_TIME TIMESTAMP,
	BATCH_TYPE_2_EVERY_MIN INT,
	BATCH_TYPE_3_EVERY_HOUR INT,
	BATCH_TYPE_4_EVERY_WEEK INT,
	BATCH_TYPE_5_EVERY_MONTH INT,
	LAST_START_TIME TIMESTAMP
);