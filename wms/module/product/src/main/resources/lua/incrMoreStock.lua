for i =1,#KEYS do
    local key = KEYS[i]
    local count = tonumber(ARGV[i])
    redis.call('incrby',key,count)
end