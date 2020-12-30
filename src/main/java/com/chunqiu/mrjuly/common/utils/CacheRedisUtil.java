package com.chunqiu.mrjuly.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 缓存工具类
 * 
 * @author wy
 */
@Component
public final class CacheRedisUtil {
	private static Logger log = LoggerFactory.getLogger(CacheRedisUtil.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 
	 * 根据RedisKey设置缓存失效时间
	 * 
	 * @param key
	 *            键
	 * @param time
	 *            时间(秒)，会覆盖以前的过期时间
	 * @return true-成功,false-失败
	 */
	public boolean expire(String key, long time) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("key is null");
				return false;
			}
			if (time > 0) {
				return redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据RedisKey获取剩余过期时间
	 * 
	 * @param key
	 *            键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public Long getExpire(String key) {
		if (StringUtils.isBlank(key)) {
			log.error("key is null");
			return null;
		}
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * 判断RedisKey是否存在
	 * 
	 * @param key
	 *            键
	 * @return true-存在,false-不存在
	 */
	public boolean hasKey(String key) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("key is null");
				return false;
			}
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * 根据RedisKey删除缓存
	 * 
	 * @param key
	 *            可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				if (StringUtils.isNotBlank(key[0])) {
					redisTemplate.delete(key[0]);
				}
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 
	 * 根据RedisKey获取单一值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public String get(String key) {
		return StringUtils.isBlank(key) ? null : JSONObject
				.toJSONString(redisTemplate.opsForValue().get(key));
	}

	/**
	 * 根据RedisKey和对象类型获取对象
	 * 
	 * @param key
	 * @param valueClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getByClass(String key, Class<T> valueClass) {
		try {
			if (StringUtils.isBlank(key)) {
				return null;
			}
			Object queryResult = redisTemplate.opsForValue().get(key);
			if (queryResult != null) {
				return (T) queryResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * 根据RedisKey覆盖存储缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null) {
				log.error("Value is null");
				return false;
			}
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据Rediskey覆盖存储缓存并设置过期时间
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null) {
				log.error("Value is null");
				return false;
			}
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time,
						TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 存储value，并提供是否覆盖选项
	 * 
	 * @param key
	 *            键
	 * @param value
	 * @param coverOnExist
	 *            true-覆盖,false-不覆盖
	 */
	public boolean set(String key, Object value, boolean coverOnExist) {
		if (StringUtils.isBlank(key))
			return false;

		if (coverOnExist) {
			return set(key, value);
		} else {
			if (hasKey(key)) {
				return false;
			} else {
				return set(key, value);
			}
		}
	}

	/**
	 * 根据Redis存储value，并覆盖
	 * 
	 * @param key
	 * @param value
	 */
	public boolean replace(String key, Object value) {
		return set(key, value, true);
	}

	/**
	 * 
	 * 递增
	 * 
	 * @param key
	 *            键
	 * @param delta
	 *            要增加几(大于0)
	 * @return 递增后的结果
	 */
	public Long incr(String key, long delta) {
		if (StringUtils.isBlank(key)) {
			log.error("Redis Key is null");
			return null;
		}
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 
	 * 递减
	 * 
	 * @param key
	 *            键
	 * @param delta
	 *            要减少几(小于0)
	 * @return 递减后的结果
	 */
	public Long decr(String key, long delta) {
		if (StringUtils.isBlank(key)) {
			log.error("Redis Key is null");
			return null;
		}
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// ================================Map=================================

	/**
	 * 
	 * 根据RedisKey获取Map中指定的HashKey的值
	 * 
	 * @param redisKey
	 *            Redis键 不能为null
	 * @param hashKey
	 *            Map键 不能为null
	 * @return hashKey对应的值
	 */
	public Object hget(String redisKey, String hashKey) {
		if (StringUtils.isBlank(redisKey)) {
			log.error("Redis Key is null");
			return null;
		}
		if (StringUtils.isBlank(hashKey)) {
			log.error("Hash Key is null");
			return null;
		}
		return redisTemplate.opsForHash().get(redisKey, hashKey);
	}

	/**
	 * 
	 * 根据RedisKey获取整个Map
	 * 
	 * @param redisKey
	 *            Redis键
	 * @return Map集合
	 */
	public Map<Object, Object> hmget(String redisKey) {
		if (StringUtils.isBlank(redisKey)) {
			log.error("Redis Key is null");
			return null;
		}
		return redisTemplate.opsForHash().entries(redisKey);
	}

	/**
	 * 
	 * 根据RedisKey保存整个Map
	 * 
	 * @param redisKey
	 *            Redis键
	 * @param map
	 *            map集合
	 * @return true-成功，false-失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean saveMap(String redisKey, Map map) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				log.error("Redis Key is null");
				return false;
			}
			if (map == null || map.size() <= 0) {
				log.error("Map is null");
				return false;
			}
			del(redisKey);
			redisTemplate.opsForHash().putAll(redisKey, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存整个HashMap，并提供设置过期时间
	 * 
	 * @param redisKey
	 *            Redis键
	 * @param map
	 *            Map集合
	 * @param time
	 *            过期时间(秒)
	 * @return true-成功,false-失败
	 */
	public boolean hmset(String redisKey, Map<String, Object> map, long time) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				log.error("Redis Key is null");
				return false;
			}
			if (map == null || map.size() <= 0) {
				log.error("Map is null");
				return false;
			}
			redisTemplate.opsForHash().putAll(redisKey, map);
			if (time > 0) {
				expire(redisKey, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存Map中指定的key，若存在则覆盖
	 * 
	 * @param redisKey
	 *            键
	 * @param hashKey
	 *            项
	 * @param value
	 *            值
	 * @return true 成功 false失败
	 */
	public boolean hset(String redisKey, String hashKey, Object value) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				log.error("Redis Key is null");
				return false;
			}
			if (StringUtils.isBlank(hashKey)) {
				log.error("Hash Key is null");
				return false;
			}
			if (value == null) {
				log.error("Value is null");
				return false;
			}
			redisTemplate.opsForHash().put(redisKey, hashKey, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存Map中指定的key，若存在则覆盖，并提供设置过期时间
	 * 
	 * @param redisKey
	 *            键
	 * @param hashKey
	 *            项
	 * @param hashValue
	 *            值
	 * @param time
	 *            时间(秒) ：会覆盖之前的过期时间
	 * @return true-成功，false-失败
	 */
	public boolean hset(String redisKey, String hashKey, Object hashValue,
			long time) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				log.error("Redis Key is null");
				return false;
			}
			if (StringUtils.isBlank(hashKey)) {
				log.error("Hash Key is null");
				return false;
			}
			if (hashValue == null) {
				log.error("Hash Value is null");
				return false;
			}
			redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
			if (time > 0) {
				expire(redisKey, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey删除Map中的HashKey
	 * 
	 * @param key
	 *            Redis键 不能为null
	 * @param hashKey
	 *            HashKey 可以使多个 不能为null
	 */
	public void hdel(String key, Object... hashKey) {
		if (StringUtils.isBlank(key)) {
			log.error("Redis Key is null");
		} else if (hashKey == null || hashKey.length < 1) {
			log.error("Item is null");
		} else {
			redisTemplate.opsForHash().delete(key, hashKey);
		}
	}

	/**
	 * 
	 * 根据RedisKey判断Map是否有对应的MapKey
	 * 
	 * @param key
	 *            Redis键 不能为null
	 * @param hashKey
	 *            Map键 不能为null
	 * @return true-存在，false-不存在
	 */
	public boolean hHasKey(String key, String hashKey) {
		if (StringUtils.isBlank(key)) {
			log.error("Redis Key is null");
			return false;
		}
		if (StringUtils.isBlank(hashKey)) {
			log.error("Map Key is null");
			return false;
		}
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * 根据RedisKey获取整个Map，并将HashValue转换成具体对象
	 * 
	 * @param key
	 *            Redis键
	 * @param valueClass
	 *            转换的对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getMapByClass(String key, Class<T> valueClass) {
		if (StringUtils.isBlank(key)) {
			log.error("Redis Key is null");
			return null;
		}

		Map<Object, Object> queryResult = hmget(key);
		Map<String, T> resultMap = null;
		if (queryResult != null && queryResult.size() > 0) {
			resultMap = new HashMap<String, T>();
			Object queryValue = null;
			for (Object queryKey : queryResult.keySet()) {
				if (queryKey != null) {
					queryValue = queryResult.get(queryKey);
					if (queryValue != null) {
						resultMap.put(queryKey.toString(), (T) queryValue);
					}
				}
			}
		}
		return resultMap;
	}

	// ============================set=============================

	/**
	 * 
	 * 根据key获取Set中的所有值
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据RedisKey获取指定对象类型的Set集合
	 * 
	 * @param redisKey
	 *            Redis键
	 * @param valueClass
	 *            对象类型
	 * @return Set集合
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSetByClass(String redisKey, Class<T> valueClass) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				return null;
			}
			Set<Object> queryResult = redisTemplate.opsForSet().members(
					redisKey);
			Set<T> result = null;
			if (queryResult != null && queryResult.size() > 0) {
				result = new HashSet<T>();
				for (Object object : queryResult) {
					if (object != null) {
						result.add((T) object);
					}
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 根据RedisKey判断Set中是否存在该值
	 * 
	 * @param redisKey
	 *            Redis键
	 * @param value
	 *            值
	 * @return true-存在，false-不存在
	 */
	public boolean sHasKey(String redisKey, Object value) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				log.error("Redis Key is null");
				return false;
			}
			return redisTemplate.opsForSet().isMember(redisKey, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存Set集合
	 * 
	 * @param key
	 *            键
	 * @param values
	 *            值 可以是多个
	 * @return 成功个数
	 */
	public long saveSet(String key, Object... values) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			del(key);
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存Set集合，并提供设置过期时间
	 * 
	 * @param key
	 *            键
	 * @param time
	 *            时间(秒)
	 * @param values
	 *            值 可以是多个
	 * @return 成功个数
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			if (values == null || values.length <= 0) {
				log.error("Value is null");
				return 0;
			}
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 根据RedisKey获取set集合的长度
	 * 
	 * @param key
	 *            Redis键
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 根据RedisKey移除Set中值为value的数据
	 * 
	 * @param key
	 *            键
	 * @param values
	 *            值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key, Object... values) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			if (values == null || values.length <= 0) {
				log.error("Value is null");
				return 0;
			}
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// ===============================list=================================

	/**
	 * 
	 * 根据RedisKey获取List集合指定长度的数据
	 * 
	 * @param key
	 *            键
	 * @param start
	 *            开始
	 * @param end
	 *            结束 0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return null;
			}
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据RedisKey获取指定对象类型的List集合
	 * 
	 * @param redisKey
	 *            Redis键
	 * @param valueClass
	 *            对象类型
	 * @return List集合
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByClass(String redisKey, Class<T> valueClass) {
		try {
			if (StringUtils.isBlank(redisKey)) {
				return null;
			}
			List<Object> queryResult = redisTemplate.opsForList().range(
					redisKey, 0, -1);
			List<T> result = null;
			if (queryResult != null && queryResult.size() > 0) {
				result = new ArrayList<T>();
				for (Object object : queryResult) {
					if (object != null) {
						result.add((T) object);
					}
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 根据RedisKey获取list缓存的长度
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 根据RedisKey获取List索引位置的值
	 * 
	 * @param key
	 *            键
	 * @param index
	 *            索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lGetIndex(String key, long index) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 根据RedisKey向List集合尾部插入数据
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null) {
				log.error("Redis Value is null");
				return false;
			}
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey向List集合尾部插入数据，并提供设置过期时间
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null) {
				log.error("Redis Value is null");
				return false;
			}
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存List集合
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean saveList(String key, List value) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null || value.size() <= 0) {
				log.error("Redis Value is null");
				return false;
			}
			del(key);
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey保存List集合，并提供设置过期时间
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null || value.size() <= 0) {
				log.error("Redis Value is null");
				return false;
			}
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据RedisKey修改List集合指定索引位置的数据
	 * 
	 * @param key
	 *            键
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return false;
			}
			if (value == null) {
				log.error("Redis Value is null");
				return false;
			}
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据RedisKey删除List集合中指定数量对应的值
	 * 
	 * @param key
	 *            键
	 * @param count
	 *            移除多少个
	 * @param value
	 *            值
	 * @return 移除的个数
	 */

	public long lRemove(String key, long count, Object value) {
		try {
			if (StringUtils.isBlank(key)) {
				log.error("Redis Key is null");
				return 0;
			}
			if (value == null) {
				log.error("Redis Value is null");
				return 0;
			}
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
