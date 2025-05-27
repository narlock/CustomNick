package com.narlock.customnick;

public class CustomNickUtils {
    public static String translateColorCodes(String altColorChar, String textToTranslate) {
        char alt = altColorChar.charAt(0);
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == alt && "0123456789abcdefklmnor".indexOf(b[i + 1]) > -1) {
                b[i] = '§';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

}
