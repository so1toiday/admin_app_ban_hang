package com.quyet.banhang.admin_app_ban_hang.function;

public class FormatMany {
    public static String getMany(int many){
        String m=String.valueOf(many);
        char[] a=m.toCharArray();
        int count=0;
        String result="";
        for(int i=a.length-1;i>=0;i--){
            count++;
            result= a[i]+result;
            if(count==3 && i!=0){
                result="."+result;
                count=0;
            }
        }
        return result+"VNĐ";
    }
}
