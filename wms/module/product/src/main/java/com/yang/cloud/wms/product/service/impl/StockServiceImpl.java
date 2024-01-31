package com.yang.cloud.wms.product.service.impl;

import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;
import com.yang.cloud.wms.product.exception.SqlDecrException;
import com.yang.cloud.wms.product.exception.SqlIncrException;
import com.yang.cloud.wms.product.mapper.StockMapper;
import com.yang.cloud.wms.product.service.StockInsertService;
import com.yang.cloud.wms.product.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockInsertService stockInsertService;

    private static final String INCR_STOCK = "/lua/incrStock.lua";
    private static final String DECR_STOCK = "/lua/decrStock.lua";

    private static final String DECR_MORE_STOCK = "/lua/decrMoreStock.lua";
    private static final String INCR_MORE_STOCK = "/lua/incrMoreStock.lua";
    private static final String NO_STOCK = "库存不足";
    private static final String CREATE = "create";

    @Override
    @Transactional
    public Map<String, String> incrStockMore(UpdateStockMoreVo updateStockMoreVo) {
        List<UpdateStockVo> updateStockVoList = updateStockMoreVo.getUpdateStockVoList();
        Map<String, String> failList = new HashMap<>();
        updateStockVoList.forEach(updateStockVo -> {
            try {
                String message = getOneScript(updateStockVo, INCR_STOCK);
                try {
                    stockInsertService.incrStock(updateStockVo, message);
                } catch (Exception e) {
                    if (!(e instanceof SqlIncrException)) {
                        log.warn("mysql incr product err", e);
                    }
                    failList.put(updateStockVo.getProductName(), e.getMessage());
                    getOneScript(updateStockVo, DECR_STOCK);
                }
            } catch (Exception e) {
                log.warn("redis incr product err", e);
                failList.put(updateStockVo.getProductName(), e.getMessage());
            }
        });
        return failList;
    }


    @Override
    @Transactional
    public void decrStockMore(UpdateStockMoreVo updateStockMoreVo) {
        List<String> keys = new ArrayList<>();
        String[] args = new String[updateStockMoreVo.getUpdateStockVoList().size()];
        for (int i = 0; i < updateStockMoreVo.getUpdateStockVoList().size(); i++) {
            UpdateStockVo updateStockVo = updateStockMoreVo.getUpdateStockVoList().get(i);
            keys.add(String.valueOf(updateStockVo.getProductId()));
            args[i] = String.valueOf(updateStockVo.getCount());
        }
        String flag = getMoreScript(DECR_MORE_STOCK, keys, args);
        if (!"success".equals(flag)) {
            Map<String, String> errors = new HashMap<String, String>() {
                {
                    put("errors", String.format("%s%s", flag, NO_STOCK));
                }
            };
            throw new SqlDecrException(errors);
        }
        try {
            updateStockMoreVo.getUpdateStockVoList().forEach(updateStockVo -> stockMapper.decrStock(updateStockVo));
        } catch (Exception e) {
            getMoreScript(INCR_MORE_STOCK, keys, args);
            throw new SqlDecrException();
        }
    }

    @Override
    @Transactional
    public void decer(UpdateStockMoreVo updateStockMoreVo) {
        updateStockMoreVo.getUpdateStockVoList().forEach(updateStockVo -> stockMapper.decrStock(updateStockVo));
        UpdateStockVo updateStockVo = updateStockMoreVo.getUpdateStockVoList().stream().findFirst().orElse(null);
        if (updateStockVo.getProductId() == 4){
            int i = 1/0;
        }
    }

    private String getOneScript(UpdateStockVo updateStockVo, String path) {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        defaultRedisScript.setResultType(String.class);
        String execute = stringRedisTemplate.execute(
                defaultRedisScript,
                Arrays.asList(String.valueOf(updateStockVo.getProductId())),
                String.valueOf(updateStockVo.getCount())
        );
        return execute;
    }

    private String getMoreScript(String path, List<String> keys, String[] args) {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        defaultRedisScript.setResultType(String.class);
        String execute = stringRedisTemplate.execute(
                defaultRedisScript,
                keys,
                args
        );
        return execute;
    }
}
