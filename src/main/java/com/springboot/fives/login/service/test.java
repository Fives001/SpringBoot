package com.springboot.fives.login.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class test {
    public void test(){

        JsonObject totalJson =new JsonObject();
        JsonArray resultJson = new JsonArray();

        JsonObject fwResultJson =new JsonObject();
        fwResultJson.addProperty("totalcount", 10);
        JsonArray fwItemsJson = new JsonArray();
        JsonObject fwItemJson =new JsonObject();
        fwItemJson.addProperty("hkeyword", "<font style='font-size:13px'><font style='color:#CC6633'>a</font>dministr</font>");
        fwItemJson.addProperty("keyword", "administr");
        fwItemJson.addProperty("count", 13);
        fwItemJson.addProperty("type", 1);
        fwItemJson.addProperty("linkname", "");
        fwItemJson.addProperty("linkurl", "");

        fwItemsJson.add(fwItemJson);
        fwResultJson.add("items",fwItemsJson);

        JsonObject bwResultJson =new JsonObject();
        bwResultJson.addProperty("totalcount", 10);
        JsonArray bwItemsJson = new JsonArray();
        JsonObject bwItemJson =new JsonObject();
        fwItemJson.addProperty("", "");
        fwItemJson.addProperty("", "");
        fwItemJson.addProperty("", "");
        fwItemJson.addProperty("", "");
        fwItemJson.addProperty("", "");
        fwItemsJson.add(bwItemJson);
        fwResultJson.add("items",bwItemsJson);

        resultJson.add(fwResultJson);
        resultJson.add(bwResultJson);
       
        totalJson.addProperty("responsestatus",0);
        totalJson.add("result",resultJson);

    }
}
