local num = table.getn(KEYS)
-- 检查对应商品是否都有库存
for i =1,num do
    local key = KEYS[i]
    local count = tonumber(ARGV[i])
    local exitsCount = tonumber(redis.call('get',key))
    if exitsCount < count then
        return KEYS[i]
    end
end
-- 商品减少库存
for i =1,num do
    local key = KEYS[i]
    local count = tonumber(ARGV[i])
    redis.call('decrby',key,count)
end
return 'success'
