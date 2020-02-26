package com.eju.cy.uploadcardlibrary.global;



import com.eju.cy.uploadcardlibrary.utils.FileUtils;

import java.io.File;



/**
 * @ Name: Caochen
 * @ Date: 2020-02-25
 * @ Time: 10:53
 * @ Description： 常量
 */
public class Constant {
    public static final String APP_NAME = "EjuJdHome";//app名称
    public static final String BASE_DIR = APP_NAME + File.separator;//WildmaIDCardCamera/
    public static final String DIR_ROOT = FileUtils.getRootPath() + File.separator + Constant.BASE_DIR;//文件夹根目录 /storage/emulated/0/WildmaIDCardCamera/
}