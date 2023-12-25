if redis.call('exists',KEYS[1]) == 1 then
    redis.call('incrby',KEYS[1],tonumber(ARGV[1]))
    return 'incrby'
else
    redis.call('set',KEYS[1],tonumber(ARGV[1]))
    return 'create'
end