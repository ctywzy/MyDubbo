## 第一步 编写服务端代码
- 启动netty服务，绑定端口

## 第二步 编写客户端代码
- 启动netty服务，根据ip地址和端口进行连接

## 第三步 实现客户端到服务端的调用
- 客户端writeAndFlush以及加码将数据传给服务端
- 服务端拿到数据解码数据然后调用方法执行
- ch.pipeline中addlast方法使用了责任链的设计模式，所以在填写执行类时要注意顺序。

## 第四步 p2p场景客户端主动调用
