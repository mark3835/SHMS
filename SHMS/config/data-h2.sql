--USER
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(1, 'sa', 'sa', 'MARK', '321', 'CCC', '5', '0911', 'XXX@XX', 0);
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(2, '456', '456', 'EQWE', '666', 'CCC', '7', '0911', 'YYY@XX', 0);


--CONFIG
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'SYSTEM_CONFIG_ADMIN', 'SYSTEM_CONFIG', 'ADMIN', 'MARK', 'TEST', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_MENU', 'DEFAULT_AUTH_URL', '預設權限_MENU', 'menu/getMenu', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_ANNOUNCEMENT', 'DEFAULT_AUTH_URL', '預設權限_公告', 'announcement/getAnnouncement', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_PAGE', 'DEFAULT_AUTH_URL', '預設權限_頁面', 'page/', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_CONTENT', 'DEFAULT_AUTH_URL', '預設權限_頁面內容', 'content/', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());

--MENU
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報作業', null, null, 10);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報公告', '4', '1', 11);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報資料維護', '4', '1', 12); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '後台管理', null, null, 20, 'login/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '系統參數設定', '/content/configSetting.html', '4', 21, 'config/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '公告設定', '/content/announcementSetting.html', '4', 22, 'announcemnet/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( 'MENU設定', '/content/menuSetting.html', '4', 23, 'menu/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '權限設定', '/content/authorizastionSetting.html', '4', 24, 'auth/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '登入LOG', '/content/loginLog.html', '4', 25, 'loginLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '錯誤LOG', '/content/errorLog.html', '4', 26, 'errorLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '批次設定', '/content/batchSetting.html', '4', 26, 'errorLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '使用者資料', '/content/userSetting.html', '4', 26, 'user/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '單位資料', '/content/unitSetting.html', '4', 26, 'unit/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '單位職安業務基本資料維護', '/content/unitData.html', null, 100);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '職業安全衛生訓練證照登陸', '/content/certificate.html', null, 200);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '安全狀況反應通報表作業', '7', null, 340);

--AUTHORIZASTION
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 1, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 2, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 3, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 4, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 5, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 6, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 7, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 8, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 9, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 10, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 11, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 12, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 13, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 14, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 15, 0);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 16, 0);

--ANNOUNCEMENT
insert into ANNOUNCEMENT(ANNOUNCEMENT_NAME, ANNOUNCEMENT_DATE, FILE_PATH, FILE_NAME, CREATE_ID, CREATE_TIME) values( '本行為強化春節安全維護工作，請於農曆春節前辦理完成本（109）年度之第一次自衛編組安全防護演練及相關事項如說明，請查照。合金總行政字第1099200030號', '2020-03-20', '', '', '123', NOW());
insert into ANNOUNCEMENT(ANNOUNCEMENT_NAME, ANNOUNCEMENT_DATE, FILE_PATH, FILE_NAME, CREATE_ID, CREATE_TIME) values( '有關本（12）月份「保全人員執勤督導檢核紀錄通報表」，請各單位總務襄理於109年1月8日前，依說明之作業方式填報，以供彙辦，請查照。金總行政字第1089205403號', '2020-02-03', '', '', '123',NOW());