package com.ssafy.ChallenMungs.common.util;

import java.util.HashMap;

public class Response {
    public HashMap<String, Object> makeSimpleRes(Object result){
        HashMap<String, Object> v = new HashMap<>();
        v.put("result",result);
        return v;
    }
}
