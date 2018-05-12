package com.jk.dao;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.jk.bans.StockPo;
import com.jk.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StockDao {
    private static final Logger ERROR = Logger.getLogger(StockDao.class);

    public synchronized Map<String, StockPo> getStockUid2StockPo(HttpServletRequest request) {
        try {
            File file = new File(AppUtils.createStockTableIfNotExist(request));
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> lines = Files.readLines(file, Charsets.UTF_8);
            String text = Joiner.on("").skipNulls().join(lines);
            if (StringUtils.isBlank(text)) {
                return new HashMap<String, StockPo>();
            }
            return JSONObject.parseObject(text, new TypeReference<Map<String, StockPo>>() {
            });
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return new HashMap<String, StockPo>();
    }

    public synchronized List<StockPo> getStockPos(HttpServletRequest request) {
        try {
            Map<String, StockPo> stringStockPoMap = getStockUid2StockPo(request);
            List<StockPo> stockPos = Lists.newArrayList();
            for (Map.Entry<String, StockPo> entry : stringStockPoMap.entrySet()) {
                stockPos.add(entry.getValue());
            }
            Collections.sort(stockPos);
            return stockPos;
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return new ArrayList<StockPo>();
    }

    public synchronized StockPo getStockPoByStockUid(HttpServletRequest request, String stockUid) {
        try {
            Map<String, StockPo> stringStockPoMap = getStockUid2StockPo(request);
            return stringStockPoMap.get(stockUid);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return null;
    }

    public synchronized List<StockPo> addStockPo(HttpServletRequest request, StockPo stockPo) {
        try {
            Map<String, StockPo> stringStockPoMap = getStockUid2StockPo(request);
            stringStockPoMap.put(AppUtils.getStockUid(stockPo), stockPo);
            File file = new File(AppUtils.createStockTableIfNotExist(request));
            Files.write(JSONObject.toJSONString(stringStockPoMap), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getStockPos(request);
    }

    public synchronized List<StockPo> removeStockPo(HttpServletRequest request, StockPo stockPo) {
        try {
            Map<String, StockPo> stringStockPoMap = getStockUid2StockPo(request);
            stringStockPoMap.remove(AppUtils.getStockUid(stockPo));
            File file = new File(AppUtils.createStockTableIfNotExist(request));
            Files.write(JSONObject.toJSONString(stringStockPoMap), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getStockPos(request);
    }

}