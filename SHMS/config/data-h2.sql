--USER
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(1, '123', '123', 'MARK', '321', 'CCC', '5', '0911', 'XXX@XX', 0);
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(2, '456', '456', 'EQWE', '666', 'CCC', '7', '0911', 'YYY@XX', 0);


--CONFIG
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'SYSTEM_CONFIG_ADMIN', 'SYSTEM_CONFIG', 'ADMIN', 'MARK', 'TEST', 1, 'MARK', sysdate(), 'MARK', sysdate());

--MENU
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '單位職安業務基本資料維護', '/content/unitData.html', null, 10);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '職業安全衛生訓練證照登陸', '/content/certificate.html', null, 20);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報作業', null, null, 30);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報公告', '4', '3', 30);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報資料維護', '4', '3', 30);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '安全狀況反應通報表作業', '7', null, 40);

--AUTHORIZASTION
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 1, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 2, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 3, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 4, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 5, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 6, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 7, 0);