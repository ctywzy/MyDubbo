## 第一步 编写服务端代码
- 启动netty服务，绑定端口

## 第二步 编写客户端代码
- 启动netty服务，根据ip地址和端口进行连接

## 第三步 实现客户端到服务端的调用
- 客户端writeAndFlush以及加码将数据传给服务端
- 服务端拿到数据解码数据然后调用方法执行
- ch.pipeline中addlast方法使用了责任链的设计模式，所以在填写执行类时要注意顺序。

## 第四步 p2p场景客户端主动调用
- 原来是连接后客户端写死的调用
- 改为主动发起调用

## 第五步 加强加解码功能
- 原来的场景下，每一个不懂的对象，都要配一组加解码对象，太过麻烦
- 现在利用字节流和对象的转换进行统一处理。

## 第六步改为通用反射调用
### 要使用动态代理被代理的类必须要实现接口
- 服务端先注册方法信息
- 服务端把已有的服务按 服务号 + 实现类的方式，放在服务容器中
- 客户端拿服务号去请求
- 客户端之前调用方法，并利用动态代理完成整个对服务端的请求，如发起调用
- 服务端根据服务号去匹配对应的服务

## 第七步，为接口增加超时时间
- 在发起请求的时候，计算出接口何时超时
- 一边用1S跑一次的定时线程去检查，何时有接口超时
- 另一遍当将接口返回值塞入map中时，去检查接口是否超时

