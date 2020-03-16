package com.example.menu.data;

import com.example.menu.MainActivity;
import com.example.menu.ui.language.LanguageFragment;

import java.util.List;

public class Language {
    public static String income() {
        return LanguageFragment.isEnglish == 1 ?  "income" : "收入";
    }

    public static String expense() {
        return LanguageFragment.isEnglish == 1 ?  "expense" : "支出";
    }

    public static String monthChooseTitle() {
        return LanguageFragment.isEnglish == 1 ?  "Choose the month" : "请选择年月";
    }

    public static List<String> getCatogories() {
        List<String> categories = MainActivity.db.queryAllCategories(LanguageFragment.isEnglish == 1);
        return categories;
    }

    public static String add() {
        return LanguageFragment.isEnglish == 1 ?  "add" : "添加";
    }

    public static String delete() {
        return LanguageFragment.isEnglish == 1 ?  "delete" : "删除";
    }

    public static String bothEmptyHint() {
        return LanguageFragment.isEnglish == 1 ? "The fields cannot be empty both" : "输入框都不可为空";
    }

    public static String emptyHint() {
        return LanguageFragment.isEnglish == 1 ? "The English fields cannot be empty" : "英文输入框不可为空";
    }

    public static String month() {
        return LanguageFragment.isEnglish == 1 ? "" : "月";
    }

    public static String year() {
        return LanguageFragment.isEnglish == 1 ? "" : "年";
    }
    public static String original() {
        return LanguageFragment.isEnglish == 1 ? "original password" : "原始密码";
    }
    public static String newPassword() {
        return LanguageFragment.isEnglish == 1 ? "new password" : "新密码";
    }
    public static String modify() {
        return LanguageFragment.isEnglish == 1 ? "modify" : "修改";
    }
}
