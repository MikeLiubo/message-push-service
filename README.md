# message-push-service

SpringCloud、SpringBoot结合RabbitMQ实现监听消息实时转发（webSocket）
方案设计
## 请看这篇文章
https://blog.csdn.net/Be_insighted/article/details/105750986


所用工具（包）
IDEA、SpringCloud、RabbitMQ、SpringData（JPA）、MySQL、SpringBoot、Maven


初识websocket
WebSocket 是HTML5一种新的协议。它实现了浏览器与服务器全双工通信(full-duplex)。一开始的握手需要借助HTTP请求完成。 WebSocket是真正实现了全双工通信的服务器向客户端推的互联网技术。 它是一种在单个TCP连接上进行全双工通讯协议。Websocket通信协议与2011年倍IETF定为标准RFC 6455，Websocket API被W3C定为标准。



为什么要用websocket？
http:短连接，请求响应完之后会断开连接，重新获取新的数据需要再次请求。

WebSocket协议是一种长链接，只需要通过一次请求来初始化链接，然后所有的请求和响应都是通过这个TCP链接进行通讯。




在线测试工具：https://easyswoole.com/wstool.html

总结
如果出现本地测试连接成功，dev等环境失败时，请配置域名和IP+PORT或者nginx等代理

编译成功、运行失败问题
如果你在项目中遇到编译成功、运行失败的问题时，请你停下来看一下自己的工程是否配置OK（未配置、配置错误）？注解OK（加错注解、忘加注解）？依赖OK(冲突、缺少)？

一般情况下编译OK、运行失败不外乎这三个原因所致！！！
