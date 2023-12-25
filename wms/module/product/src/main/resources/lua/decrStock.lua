if redis.call('exists',KEYS[1]) == 1 then
    local num = tonumber(ARGV[1])
    local count = tonumber(redis.call('get',KEYS[1]))
    if count >= num then
        redis.call('decrby',KEYS[1],num)
        return '1'
    end
end
return '0'
