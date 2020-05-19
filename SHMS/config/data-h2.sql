--USER
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(1, 'sa', 'sa', 'MARK', '', 'CCC', '5', '0911', 'XXX@XX', 0);
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(2, 'H123', 'MARK3835', '黃信康', 'A01419', '打雜', '7', '0911', 'YYY@XX', 0);
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(3, 'A1', 'RRRRR', '主管1', 'A01419', '打雜', '13', '0911', 'YYY@XX', 0);
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(4, 'A2', 'YYYYY', '主管2', 'A01419', '打雜', '12', '0911', 'YYY@XX', 0);
insert into USER(ID, ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values(5, 'A3', 'UUUUU', '主管3', 'A01419', '打雜', '12', '0911', 'YYY@XX', 0);

--UNIT
insert into UNIT(UNIT_ID ,UNIT_NAME ,MANAGER ,SAVE_MANAGER ,FIRE_HELPER ,HELPER ,AFFAIRS , TEL) values('A01419', '資訊OA科', 'A1', 'A2', 'A3', 'H123', 'H123', '02-123456789');


--CONFIG
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'SYSTEM_ADMIN_SUPER_USER', 'SYSTEM_ADMIN', 'sa', 'sa', 'mark', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'SYSTEM_ADMIN_SUPER_USER2', 'SYSTEM_ADMIN', 'MARK3835', 'MARK3835', 'MARK3835', 1, 'MARK3835', sysdate(), 'MARK3835', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_MENU', 'DEFAULT_AUTH_URL', '預設權限_MENU', 'menu/getMenu', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_ANNOUNCEMENT', 'DEFAULT_AUTH_URL', '預設權限_公告', 'announcement/getAnnouncement', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_PAGE', 'DEFAULT_AUTH_URL', '預設權限_頁面', 'page/', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_CONTENT', 'DEFAULT_AUTH_URL', '預設權限_頁面內容', 'content/', '給LoginSessionFilter判定放行', 1, 'MARK', sysdate(), 'MARK', sysdate());

insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_SAVEMANAGER_1', 'CERTIFICATE_TYPE_SAVEMANAGER', '證書種類_安全主管_甲種職業安全衛生主管', '甲種職業安全衛生主管', '證書種類選項_安全主管_甲種職業安全衛生主管', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_SAVEMANAGER_2', 'CERTIFICATE_TYPE_SAVEMANAGER', '證書種類_安全主管_乙種職業安全衛生主管', '乙種職業安全衛生主管', '證書種類選項_安全主管_乙種職業安全衛生主管', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_FIREHELPER_1', 'CERTIFICATE_TYPE_FIREHELPER', '證書種類_防火管理人', '防火管理人', '證書種類選項_防火管理人', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_HELPER_1', 'CERTIFICATE_TYPE_HELPER', '證書種類_急救人員', '急救人員', '證書種類選項_急救人員', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_HELPER_1', 'CERTIFICATE_TYPE_HELPER', '證書種類_急救人員_AED+CPR人員', 'AED+CPR人員', '證書種類選項_急救人員_AED+CPR人員', 1, 'MARK', sysdate(), 'MARK', sysdate());

insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_UNIT_1', 'CERTIFICATE_UNIT', '核發單位1', '中華民國工業安全衛生協會', '核發單位1', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_UNIT_2', 'CERTIFICATE_UNIT', '核發單位2', '中國生產力中心', '核發單位2', 1, 'MARK', sysdate(), 'MARK', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_UNIT_3', 'CERTIFICATE_UNIT', '核發單位3', '財團法人消防教育學術研究基金會', '核發單位3', 1, 'MARK', sysdate(), 'MARK', sysdate());

--MENU
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報作業', null, null, 10);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報公告', '4', '1', 11);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報資料維護', '4', '1', 12); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '後台管理', null, null, 20, 'login/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '系統參數設定', '/content/configSetting.html', '4', 21, 'config/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '公告設定', '/content/announcementSetting.html', '4', 22, 'announcement/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( 'MENU設定', '/content/menuSetting.html', '4', 23, 'menu/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '權限設定', '/content/authorizastionSetting.html', '4', 24, 'auth/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '登入LOG', '/content/loginLog.html', '4', 25, 'loginLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '錯誤LOG', '/content/errorLog.html', '4', 26, 'errorLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '批次設定', '/content/batchSetting.html', '4', 26, 'errorLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '使用者資料', '/content/userSetting.html', '4', 26, 'user/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '單位資料', '/content/unitSetting.html', '4', 26, 'unitSetting/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '證照資料', '/content/certificateSetting.html', '4', 26, 'certificateSetting/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '單位職安業務基本資料維護', '/content/unitData.html', null, 100 , 'unitData/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '職業安全衛生訓練證照登陸', '/content/certificate.html', null, 200, 'certificate/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '安全狀況反應通報表作業', '7', null, 340);

--AUTHORIZASTION
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 1,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 2,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 3,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 4,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 5,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 6,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 7,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 8,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 9,  7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 10, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 11, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 12, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 13, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 14, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 15, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 16, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 17, 7);

insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 1,  1);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 2,  1);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 3,  1);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 4,  2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 5,  2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 6,  2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 7,  2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 8,  2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 9,  2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 10, 2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 11, 2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 12, 2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 13, 2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 14, 2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 15, 2);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 16, 2);


--ANNOUNCEMENT
insert into ANNOUNCEMENT(ANNOUNCEMENT_NAME, ANNOUNCEMENT_DATE, FILE_PATH, FILE_NAME, CREATE_ID, CREATE_TIME) values( '本行為強化春節安全維護工作，請於農曆春節前辦理完成本（109）年度之第一次自衛編組安全防護演練及相關事項如說明，請查照。合金總行政字第1099200030號', '2020-03-20', '', '', '123', NOW());
insert into ANNOUNCEMENT(ANNOUNCEMENT_NAME, ANNOUNCEMENT_DATE, FILE_PATH, FILE_NAME, CREATE_ID, CREATE_TIME) values( '有關本（12）月份「保全人員執勤督導檢核紀錄通報表」，請各單位總務襄理於109年1月8日前，依說明之作業方式填報，以供彙辦，請查照。金總行政字第1089205403號', '2020-02-03', '', '', '123',NOW());

--CERTICIFATE
insert into CERTIFICATE (ROC_ID , CERTIFICATE_TYPE , CERTIFICATE_NAME , CERTIFICATE_UNIT , GET_DATE , GET_FEE , GET_TRAIN_UNIT, REVIEW_ID , REVIEW_TIME , IS_RESPONSIBLE , CREATE_ID , CREATE_TIME ) 
values( 'H123', '急救人員', '106N8010109', '中華民國工業衛生管理協會', '2020-01-01', '200', 'A01419', 'A1', '2020-01-01 10:10:10', 1, 'H123', NOW());
insert into CERTIFICATE (ROC_ID , CERTIFICATE_TYPE , CERTIFICATE_NAME , CERTIFICATE_UNIT , GET_DATE , GET_FEE , GET_TRAIN_UNIT, REVIEW_ID , REVIEW_TIME , IS_RESPONSIBLE , CREATE_ID , CREATE_TIME ) 
values( 'A2', '職業安全衛生業務主管', '12345467', '中華民國工業衛生管理協會', '2020-01-01', '2000', 'A01419', 'A1', '2020-01-01 10:10:10', 1, 'H123', NOW());
insert into CERTIFICATE (ROC_ID , CERTIFICATE_TYPE , CERTIFICATE_NAME , CERTIFICATE_UNIT , GET_DATE , GET_FEE , GET_TRAIN_UNIT, REVIEW_ID , REVIEW_TIME , IS_RESPONSIBLE , CREATE_ID , CREATE_TIME ) 
values( 'A3', '防火管理人', '7654321', '中華民國工業衛生管理協會', '2020-01-01', '2000', 'A01419', 'A1', '2020-01-01 10:10:10', 1, 'H123', NOW());