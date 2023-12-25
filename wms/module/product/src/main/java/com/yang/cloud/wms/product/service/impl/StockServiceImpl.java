package com.yang.cloud.wms.product.service.impl;

import com.yang.cloud.wms.common.entity.product.Stock;
import com.yang.cloud.wms.common.exception.InternalServerErrorException;
import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;
import com.yang.cloud.wms.product.mapper.StockMapper;
import com.yang.cloud.wms.product.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StockMapper stockMapper;

    private static final String INCR_STOCK = "/lua/incrStock.lua";
    private static final String DECR_STOCK = "/lua/decrStock.lua";

    private static final String DECR_MORE_STOCK = "/lua/decrMoreStock.lua";

    @Override
    public void incrStock(UpdateStockVo updateStockVo) {
        String execute = getOneScript(updateStockVo, INCR_STOCK);
        boolean flag = "0".equals(execute) ? true : false;
        if (flag) {
            Stock stock = new Stock();
            BeanUtils.copyProperties(updateStockVo, stock);
            stockMapper.insert(stock);
        } else {
            stockMapper.incrStock(updateStockVo);
        }

    }

    @Override
    public void decrStock(UpdateStockVo updateStockVo) {
        String execute = getOneScript(updateStockVo, DECR_STOCK);
        boolean flag = "0".equals(execute) ? true : false;
        if (flag) {
            Map<String, String> errors = new HashMap<String, String>() {
                {
                    put("erros", "无对应库存");
                }
            };
            throw new InternalServerErrorException(errors);
        }
        stockMapper.decrStock(updateStockVo);
    }

    @Override
    public void decrStockMore(UpdateStockMoreVo updateStockMoreVo) {
        List<String> keys = new ArrayList<>();
        String[] args = new String[updateStockMoreVo.getUpdateStockVoList().size()];
        for (int i = 0; i < updateStockMoreVo.getUpdateStockVoList().size(); i++) {
            UpdateStockVo updateStockVo = updateStockMoreVo.getUpdateStockVoList().get(i);
            keys.add(String.valueOf(updateStockVo.getProductId()));
            args[i] = String.valueOf(updateStockVo.getCount());
        }
        String flag = getMoreScript(DECR_MORE_STOCK, keys, args);
        if ("0".equals(flag)){
            Map<String,String> errors = new HashMap<String,String>(){
                {
                    put("errors","部分商品库存不足");
                }
            };
            throw new InternalServerErrorException(errors);
        }
        updateStockMoreVo.getUpdateStockVoList().forEach(updateStockVo -> stockMapper.decrStock(updateStockVo));

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
