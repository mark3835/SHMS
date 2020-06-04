--USER
insert into USERS( ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values( 'A0', 'A0', '員工1', 'A01', '打雜', '7', '0911', 'YYY@XX', 0);
insert into USERS( ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values( 'A1', 'A1', '主管1', 'A01', '打雜', '13', '0911', 'YYY@XX', 0);
insert into USERS( ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values( 'A2', 'A2', '主管2', 'A01', '打雜', '12', '0911', 'YYY@XX', 0);
insert into USERS( ROC_ID, ACCOUNT, NAME, UNIT_ID, JOB_NAME, JOB_LEVEL, PHONE, EMAIL, IS_LEAVE) values( 'A3', 'A3', '主管3', 'A01', '打雜', '12', '0911', 'YYY@XX', 0);

--UNIT
insert into UNIT(UNIT_ID ,UNIT_NAME ,MANAGER ,SAVE_MANAGER ,FIRE_HELPER ,HELPER ,AFFAIRS , TEL) values('A01', '測試單位', 'A1', 'A2', 'A3', 'A0', 'A0', '02-123456789');


--CONFIG
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'SYSTEM_ADMIN_SUPER_USER', 'SYSTEM_ADMIN', 'A0', 'A0', 'A0', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'SYSTEM_ADMIN_SUPER_USER2', 'SYSTEM_ADMIN', 'MARK3835', 'MARK3835', 'MARK3835', 1, 'MARK3835', sysdate(), 'MARK3835', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_MENU', 'DEFAULT_AUTH_URL', '預設權限_MENU', 'menu/getMenu', '給LoginSessionFilter判定放行', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_ANNOUNCEMENT', 'DEFAULT_AUTH_URL', '預設權限_公告', 'announcement/getAnnouncement', '給LoginSessionFilter判定放行', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_PAGE', 'DEFAULT_AUTH_URL', '預設權限_頁面', 'page/', '給LoginSessionFilter判定放行', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'DEFAULT_AUTH_URL_CONTENT', 'DEFAULT_AUTH_URL', '預設權限_頁面內容', 'content/', '給LoginSessionFilter判定放行', 1, 'A0', sysdate(), 'A0', sysdate());

insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_SAVEMANAGER_1', 'CERTIFICATE_TYPE_SAVEMANAGER', '證書種類_安全主管_甲種職業安全衛生主管', '甲種職業安全衛生主管', '證書種類選項_安全主管_甲種職業安全衛生主管', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_SAVEMANAGER_2', 'CERTIFICATE_TYPE_SAVEMANAGER', '證書種類_安全主管_乙種職業安全衛生主管', '乙種職業安全衛生主管', '證書種類選項_安全主管_乙種職業安全衛生主管', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_FIREHELPER_1', 'CERTIFICATE_TYPE_FIREHELPER', '證書種類_防火管理人', '防火管理人', '證書種類選項_防火管理人', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_HELPER_1', 'CERTIFICATE_TYPE_HELPER', '證書種類_急救人員', '急救人員', '證書種類選項_急救人員', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_TYPE_HELPER_1', 'CERTIFICATE_TYPE_HELPER', '證書種類_急救人員_AED+CPR人員', 'AED+CPR人員', '證書種類選項_急救人員_AED+CPR人員', 1, 'A0', sysdate(), 'A0', sysdate());

insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_UNIT_1', 'CERTIFICATE_UNIT', '核發單位1', '中華民國工業安全衛生協會', '核發單位1', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_UNIT_2', 'CERTIFICATE_UNIT', '核發單位2', '中國生產力中心', '核發單位2', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'CERTIFICATE_UNIT_3', 'CERTIFICATE_UNIT', '核發單位3', '財團法人消防教育學術研究基金會', '核發單位3', 1, 'A0', sysdate(), 'A0', sysdate());

insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'EVENT_EFFECT_TYPE_1', 'EVENT_EFFECT_TYPE', '影響類別1', '交通(電信)中斷致相關系統或營業受阻', '影響類別1', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'EVENT_EFFECT_TYPE_2', 'EVENT_EFFECT_TYPE', '影響類別2', '行舍、設備受損致營業受阻', '影響類別2', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'EVENT_EFFECT_TYPE_3', 'EVENT_EFFECT_TYPE', '影響類別3', '有人員傷(亡)', '影響類別3', 1, 'A0', sysdate(), 'A0', sysdate());

insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'LDAP_ACC_KEY', 'LDAP_ACC_TYPE', 'LDAP_ACC', 'MARK3835', '', 1, 'A0', sysdate(), 'A0', sysdate());
insert into CONFIG(CFG_KEY, CFG_TYPE, CFG_NAME, CFG_VALUE, CFG_MEMO, CFG_IN_USE, CREATE_ID, CREATE_TIME, EDIT_ID, EDIT_TIME) 
values( 'LDAP_PSD_KEY', 'LDAP_PSD_TYPE', 'LDAP_PSD', '5tgb^YHN', '', 1, 'A0', sysdate(), 'A0', sysdate());

--MENU
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER) values( '平安通報作業', null, null, 10);
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '平安通報公告', '/content/eventSafeNotification/eventSafeNotification.html', '1', 11, 'eventSafeNotification/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '平安通報設定', '/content/eventSafeNotification/eventSafeNotificationSetting.html', '1', 12, 'eventSafeNotificationSetting/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '平安通報審核', '/content/eventSafeNotification/eventSafeNotificationReview.html', '1', 13, 'eventSafeNotificationReview/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '平安通報回報資料', '/content/eventSafeNotification/eventSafeNotificationReturnData.html', '1', 14, 'eventSafeNotificationReturnData/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '後台管理', null, null, 20, 'login/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '系統參數設定', '/content/setting/configSetting.html', 	  	'6', 21, 'config/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '公告設定', '/content/setting/announcementSetting.html',   	'6', 22, 'announcement/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( 'MENU設定', '/content/setting/menuSetting.html', 			'6', 23, 'menu/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '權限設定', '/content/setting/authorizastionSetting.html', 	'6', 24, 'auth/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '登入LOG', '/content/setting/loginLog.html', 				'6', 25, 'loginLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '錯誤LOG', '/content/setting/errorLog.html', 				'6', 26, 'errorLog/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '批次設定', '/content/setting/batchSetting.html', 			'6', 26, 'batchSetting/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '使用者資料', '/content/setting/userSetting.html', 			'6', 26, 'user/api/'); 
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '單位資料', '/content/setting/unitSetting.html', 			'6', 26, 'unitSetting/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '證照資料', '/content/setting/certificateSetting.html', 		'6', 26, 'certificateSetting/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '單位職安業務基本資料維護', '/content/unitData/unitData.html', null, 100 , 'unitData/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '職業安全衛生訓練證照登陸', '/content/certificate/certificate.html', null, 200, 'certificate/api/');
insert into MENU(MENU_NAME, MENU_URL, MENU_TIER_TWO, MENU_ORDER, MENU_API_URL) values( '證照審核', '/content/certificate/certificateReview.html', null, 201, 'certificateReview/api/');

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
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 18, 7);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 19, 7);

insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 1,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 2,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 3,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 4,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 5,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 6,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 7,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 8,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 9,  6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 10, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 11, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 12, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 13, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 14, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 15, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 16, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 17, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 18, 6);
insert into AUTHORIZASTION(MENU_ID, AUTH_LV) values( 19, 6);

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
insert into ANNOUNCEMENT(ANNOUNCEMENT_NAME, ANNOUNCEMENT_DATE, FILE_PATH, FILE_NAME, CREATE_ID, CREATE_TIME) values( '本行為強化春節安全維護工作，請於農曆春節前辦理完成本（109）年度之第一次自衛編組安全防護演練及相關事項如說明，請查照。合金總行政字第1099200030號', '2020-03-20', '', '', 'A0', NOW());
insert into ANNOUNCEMENT(ANNOUNCEMENT_NAME, ANNOUNCEMENT_DATE, FILE_PATH, FILE_NAME, CREATE_ID, CREATE_TIME) values( '有關本（12）月份「保全人員執勤督導檢核紀錄通報表」，請各單位總務襄理於109年1月8日前，依說明之作業方式填報，以供彙辦，請查照。金總行政字第1089205403號', '2020-02-03', '', '', 'A0',NOW());

--CERTICIFATE
insert into CERTIFICATE (ROC_ID , CERTIFICATE_TYPE , CERTIFICATE_NAME , CERTIFICATE_UNIT , GET_DATE , GET_FEE , GET_TRAIN_UNIT, REVIEW_ID , REVIEW_TIME , IS_RESPONSIBLE , CREATE_ID , CREATE_TIME ) 
values( 'A0', '急救人員', '106N8010109', '中華民國工業衛生管理協會', '2020-01-01', '200', 'A01', 'A1', '2020-01-01 10:10:10', 1, 'A0', NOW());
insert into CERTIFICATE (ROC_ID , CERTIFICATE_TYPE , CERTIFICATE_NAME , CERTIFICATE_UNIT , GET_DATE , GET_FEE , GET_TRAIN_UNIT, REVIEW_ID , REVIEW_TIME , IS_RESPONSIBLE , CREATE_ID , CREATE_TIME ) 
values( 'A2', '職業安全衛生業務主管', '12345467', '中華民國工業衛生管理協會', '2020-01-01', '2000', 'A01', 'A1', '2020-01-01 10:10:10', 1, 'A0', NOW());
insert into CERTIFICATE (ROC_ID , CERTIFICATE_TYPE , CERTIFICATE_NAME , CERTIFICATE_UNIT , GET_DATE , GET_FEE , GET_TRAIN_UNIT, REVIEW_ID , REVIEW_TIME , IS_RESPONSIBLE , CREATE_ID , CREATE_TIME ) 
values( 'A3', '防火管理人', '7654321', '中華民國工業衛生管理協會', '2020-01-01', '2000', 'A01', 'A1', '2020-01-01 10:10:10', 1, 'A0', NOW());