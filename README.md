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
	