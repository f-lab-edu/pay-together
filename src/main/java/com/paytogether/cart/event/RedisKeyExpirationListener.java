package com.paytogether.cart.event;

import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyspaceEventMessageListener {

  private final StringRedisTemplate redisTemplate;
  private final EventCartSseService sseService;

  public RedisKeyExpirationListener(
      @Qualifier("redisMessageListenerContainer") RedisMessageListenerContainer listenerContainer,
      StringRedisTemplate redisTemplate,
      EventCartSseService sseService) {
    super(listenerContainer);
    this.redisTemplate = redisTemplate;
    this.sseService = sseService;

    listenerContainer.addMessageListener(this, new PatternTopic("__keyevent@0__:expired"));
  }

  @Override
  protected void doHandleMessage(Message message) {
    String expiredKey = message.toString();
    if (!expiredKey.startsWith("event:product:")) {
      return;
    }

    String[] parts = expiredKey.split(":");
    if (parts.length < 3) {
      return;
    }

    String productId = parts[2];
    String userSetKey = "event:product:" + productId + ":users";

    Set<String> userIds = redisTemplate.opsForSet().members(userSetKey);
    if (userIds != null) {
      for (String userIdStr : userIds) {
        Long userId = Long.parseLong(userIdStr);
        sseService.sendToUser(userId, "상품 " + productId + " 구매모집이 마감되었습니다! 지금 구매하세요!");
      }
      redisTemplate.delete(userSetKey);
    }
  }
}