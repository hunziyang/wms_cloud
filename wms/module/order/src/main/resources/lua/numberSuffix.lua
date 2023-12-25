if redis.call('exists',KEYS[1]) == 1 then
    redis.call('incr',KEYS[1])
    return tostring(redis.call('get',KEYS[1]));
else
    redis.call('set',KEYS[1],1)
    redis.call('expire',KEYS[1],108000)
    return '1'
end