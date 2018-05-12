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

    public synchronized List<VoucherPo> getVoucherList(HttpServletRequest request) {
        try {
            File file = new File(AppUtils.createVoucherTableIfNotExist(request));
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
            boolean find = Boolean.FALSE;
            for (VoucherPo po : vouchers) {
                if (po.getName().equals(voucherPo.getName())) {
                    find = Boolean.TRUE;
                    break;
                }
            }
            if (!find) {
                vouchers.add(voucherPo);
            }
            File file = new File(AppUtils.createVoucherTableIfNotExist(request));
            Files.write(JSONObject.toJSONString(vouchers), file, Charsets.UTF_8);
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return getVoucherList(request);
    }

    public synchronized Boolean findVoucherPo(HttpServletRequest request, VoucherPo voucherPo) {
        Boolean find = Boolean.FALSE;
        try {
            List<VoucherPo> vouchers = getVoucherList(request);
            for (VoucherPo po : vouchers) {
                if (po.getName().equals(voucherPo.getName())) {
                    find = Boolean.TRUE;
                    break;
                }
            }
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
        return find;
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
            File file = new File(AppUtils.createVoucherTableIfNotExist(request));
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