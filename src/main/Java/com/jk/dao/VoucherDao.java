package com.jk.dao;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.jk.bans.VoucherPo;
import com.jk.utils.AppUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class VoucherDao {
    private static final Logger ERROR = Logger.getLogger(VoucherDao.class);

    private synchronized List<VoucherPo> getVoucherList(HttpServletRequest request) {
        try {
            String path = AppUtils.getVoucherPath(request);
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> lines = Files.readLines(file, Charsets.UTF_8);
            String text = Joiner.on("").skipNulls().join(lines);
            if (StringUtils.isBlank(text)) {
                return new ArrayList<VoucherPo>();
            }
            List<VoucherPo> vouchers = JSONObject.parseArray(text, VoucherPo.class);
            return vouchers;
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return new ArrayList<VoucherPo>();
    }

    public synchronized List<VoucherPo> addVoucherPo(HttpServletRequest request, VoucherPo voucherPo) {
        try {
            List<VoucherPo> vouchers = getVoucherList(request);
            vouchers.add(voucherPo);
            String path = AppUtils.getVoucherPath(request);
            File file = new File(path);
            Files.write(JSONObject.toJSONString(vouchers), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getVoucherList(request);
    }

    public synchronized List<VoucherPo> removeVoucherPo(HttpServletRequest request, String uId) {
        try {
            List<VoucherPo> vouchers = getVoucherList(request);
            for (Iterator<VoucherPo> it = vouchers.iterator(); it.hasNext(); ) {
                if (it.next().getuId().equals(uId)) {
                    it.remove();
                    break;
                }
            }
            String path = AppUtils.getVoucherPath(request);
            File file = new File(path);
            Files.write(JSONObject.toJSONString(vouchers), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getVoucherList(request);
    }

    public List<VoucherPo> getVoucherPos(HttpServletRequest request, Integer type) {
        List<VoucherPo> voucherPos = getVoucherList(request);
        List<VoucherPo> response = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(voucherPos)) {
            for (VoucherPo voucherPo : voucherPos) {
                if (voucherPo != null && ObjectUtils.equals(voucherPo.getType(), type)) {
                    response.add(voucherPo);
                }
            }
        }
        return response;
    }

}