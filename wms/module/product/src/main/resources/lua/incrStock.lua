if redis.call('exists',KEYS[1]) == 1 then
    redis.call('incrby',KEYS[1],tonumber(ARGV[1]))
    return '1'
else
    redis.call('set',KEYS[1],tonumber(ARGV[1]))
    return '0'
end