package io.github.ericwu0930.service.impl;

import io.github.ericwu0930.dao.PromoMapper;
import io.github.ericwu0930.pojo.Promo;
import io.github.ericwu0930.service.PromoService;
import io.github.ericwu0930.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author erichwu
 * @date 2020/6/8
 */
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoMapper promoMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        // 获取对应商品的秒杀信息
        Promo promo = promoMapper.selectByItemId(itemId);
        PromoModel promoModel = convertFromDataObject(promo);
        if(promoModel==null)
            return null;
        DateTime now =new DateTime();
        if(promoModel.getStartTime().isAfterNow())
            promoModel.setStatus(1);
        else if(promoModel.getEndTime().isBeforeNow())
            promoModel.setStatus(3);
        else
            promoModel.setStatus(2);
        return promoModel;
    }

    private PromoModel convertFromDataObject(Promo promo){
        if(promo==null)
            return null;
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promo,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promo.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promo.getStartTime()));
        promoModel.setEndTime(new DateTime(promo.getEndTime()));
        return promoModel;
    }
}
