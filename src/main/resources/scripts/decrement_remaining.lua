local eventKey = KEYS[1]
local cartKey = KEYS[2]
local productId = ARGV[1]
local quantity = tonumber(ARGV[2])

local remaining = tonumber(redis.call("HGET", eventKey, "remaining"))
if remaining == nil then
    return -1 -- 이벤트 상품 없음
end

if remaining < quantity then
    return 0 -- 재고 부족
end

redis.call("HINCRBY", eventKey, "remaining", -quantity)

redis.call("HINCRBY", cartKey, productId, quantity)

return 1 -- 성공