package com.jk.dao;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.jk.bans.ScreenPo;
import com.jk.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScreenDao {
    private static final Logger ERROR = Logger.getLogger(ScreenDao.class);

    public synchronized Map<String, ScreenPo> getStockUid2ScreenPo(HttpServletRequest request) {
        try {
            File file = new File(AppUtils.createScreenTableIfNotExist(request));
            List<String> lines = Files.readLines(file, Charsets.UTF_8);
            String text = Joiner.on("").skipNulls().join(lines);
            if (StringUtils.isBlank(text)) {
                return new HashMap<String, ScreenPo>();
            }
            return JSONObject.parseObject(text, new TypeReference<Map<String, ScreenPo>>() {
            });
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return new HashMap<String, ScreenPo>();
    }

    public synchronized List<ScreenPo> getScreenPos(HttpServletRequest request) {
        try {
            Map<String, ScreenPo> stringScreenPoMap = getStockUid2ScreenPo(request);
            List<ScreenPo> screenPos = Lists.newArrayList();
            for (Map.Entry<String, ScreenPo> entry : stringScreenPoMap.entrySet()) {
                screenPos.add(entry.getValue());
            }
            return screenPos;
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return new ArrayList<ScreenPo>();
    }

    public synchronized ScreenPo getScreenPoByMacAddress(HttpServletRequest request, String macAddress) {
        try {
            Map<String, ScreenPo> stringScreenPoMap = getStockUid2ScreenPo(request);
            return stringScreenPoMap.get(macAddress);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return null;
    }

    public synchronized List<ScreenPo> addScreenPo(HttpServletRequest request, ScreenPo screenPo) {
        try {
            Map<String, ScreenPo> stringScreenPoMap = getStockUid2ScreenPo(request);
            stringScreenPoMap.put(screenPo.getMacAddress(), screenPo);
            File file = new File(AppUtils.createScreenTableIfNotExist(request));
            Files.write(JSONObject.toJSONString(stringScreenPoMap), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getScreenPos(request);
    }

    public synchronized List<ScreenPo> removeScreenPo(HttpServletRequest request, ScreenPo screenPo) {
        try {
            Map<String, ScreenPo> stringScreenPoMap = getStockUid2ScreenPo(request);
            stringScreenPoMap.remove(screenPo.getMacAddress());
            File file = new File(AppUtils.createScreenTableIfNotExist(request));
            Files.write(JSONObject.toJSONString(stringScreenPoMap), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getScreenPos(request);
    }

}