package com.wangjie.androidbucket.core.collecion;


import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

/**
 * 汉字排序
 */
public class LanguageComparator_CN_Object<T extends PinyinKeySortable> implements Comparator<T> {

    @Override
    public int compare(T lhs, T rhs) {

        String ostr1 = lhs.getPendKey();
        String ostr2 = rhs.getPendKey();

        for (int i = 0; i < ostr1.length() && i < ostr2.length(); i++) {

            int codePoint1 = transChineseChar(ostr1.charAt(i));
            int codePoint2 = transChineseChar(ostr2.charAt(i));
            if (Character.isSupplementaryCodePoint(codePoint1)
                    || Character.isSupplementaryCodePoint(codePoint2)) {
                i++;
            }
            if (codePoint1 != codePoint2) {
                if (Character.isSupplementaryCodePoint(codePoint1)
                        || Character.isSupplementaryCodePoint(codePoint2)) {
                    return codePoint1 - codePoint2;
                }
                String pinyin1 = pinyin((char) codePoint1);
                String pinyin2 = pinyin((char) codePoint2);

                if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
                    if (!pinyin1.equals(pinyin2)) {
                        return pinyin1.compareTo(pinyin2);
                    }
                } else {
                    return codePoint1 - codePoint2;
                }
            }
        }
        return ostr1.length() - ostr2.length();
    }

    // 获得汉字拼音的首字符
    private String pinyin(char c) {
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyins == null) {
            return null;
        }
        return pinyins[0];
    }

    //获得字符串的首字母 首字符 转汉语拼音
    public int transChineseChar(char c) {
        // 返回
        char result = 0;
        // 是否是非汉字
        String[] print = PinyinHelper.toHanyuPinyinStringArray(c);

        if (print == null) {

            // 将小写字母改成大写
            if ((c >= 97 && c <= 122)) {
                c -= 32;
            }
            if (c >= 65 && c <= 90) {
                result = c;
            } else {
                // 认为首字符为数字或者特殊字符
                result = '#';
            }
        } else {
            // 如果是中文 分类大写字母
            result = (char) (print[0].charAt(0) - 32);
        }
        if (result == 0) {
            result = '?';
        }
        return result;
    }
}
