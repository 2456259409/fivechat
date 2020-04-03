package com.renjian.fivechase.util;

import com.renjian.fivechase.model.User;

public class UserSerialize {
    public static User getSerialize(String ustr){
        User user=new User();
        byte[] bytes=ustr.getBytes();
        String content=new String(bytes,6,bytes.length-7);
        String[] splits = content.split(",");
        user.setId(Long.valueOf(splits[0].split("=")[1]));
        user.setUsername(splits[1].split("=")[1]);
        user.setPassword(splits[2].split("=")[1]);
        user.setNickname(splits[3].split("=")[1]);
        return user;
    }
}
