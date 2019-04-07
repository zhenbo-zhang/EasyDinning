1. 前后端分离项目：
	前端：VueJS （手机端）
	后端：SpringBoot, Bootstrap + FreeMarker + JQuery。前后端通过restful接口相连。（PC端）
	数据库：SpringBoot + JPA / SpringBoot + MyBatis
	缓存：SpringBoot + Redis（分布式Session，分布式锁）
	消息推送：WebSocket

2. 前后端数据请求先通过NGINX服务器 -> Tomcat（分布式，多台服务器） -> Redis(有缓存)
									|
								 MySQL（无缓存）	

3. 日志框架：实现日志（描述系统运行的状态）输出的工具包。
	日志门面：SLF4j
	日志实现：Logback

4. Backend URL: http://127.0.0.1:8080/sell/seller/order/list
5. Frontend URL: 192.168.0.113/#/order (sell.com/#/)
6. 压测模拟并发Apache ab. 如果用sychronized的，对于不同的商品，其实并发是可以的，但是这个关键字会把整段代码都锁了，不管是哪件商品，所以控制力度不够细。而Redis作为分布式锁：多台机器上多个进程对同一个数据操作的互斥。只会对同一商品的并发上锁，不同商品之间不影响。
	