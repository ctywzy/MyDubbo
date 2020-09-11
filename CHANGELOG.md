# 1.0.0 (2020-09-11)


### Bug Fixes

* 对象没有实现序列化接口导致转成字节数组失败 ([060b744](https://github.com/ctywzy/MyDubbo/commit/060b744addf431db90402d64dbddb8cfbe9d4e31))
* 未获取方法值 ([e901481](https://github.com/ctywzy/MyDubbo/commit/e901481bf243acb77ea3c3699d89e7d7b1db9330))
* ch.pipline 顺序问题会导致加解码类失效 ([30e1a55](https://github.com/ctywzy/MyDubbo/commit/30e1a559a2728ebd373ee9381aa41b87754d7830))


### Features

* 超时处理完善 ([ce93916](https://github.com/ctywzy/MyDubbo/commit/ce93916b053b3d72f1d68d557fe052f3bbc0d1ed))
* 超时线程增加 ([fb06f84](https://github.com/ctywzy/MyDubbo/commit/fb06f8421531c77aae01aa30585e5fa5e538244e))
* 反射动态代理调用服务端代码 ([3787fe5](https://github.com/ctywzy/MyDubbo/commit/3787fe5164640bb6ed22aa29ce52e914c64eec3c))
* 反射动态代理调用客户端代码 ([c09fd93](https://github.com/ctywzy/MyDubbo/commit/c09fd93e647b03d32d7721111944f73e6a8ea952))
* 封装字节和对象互相转换的方法 ([70d27a4](https://github.com/ctywzy/MyDubbo/commit/70d27a4aa28bdcbab584f341e5a315eeb1604570))
* 给客户端和服务器增加 加解码操作使之可以通信 ([531d85b](https://github.com/ctywzy/MyDubbo/commit/531d85b300b512cb0b31a8885fb949c88a08cbd4))
* 客户端调服务端 ([ef4e48e](https://github.com/ctywzy/MyDubbo/commit/ef4e48e2bf1e1772d47bbad38e550ca974779246))
* 客户端请求发送 ([adfb9fe](https://github.com/ctywzy/MyDubbo/commit/adfb9fe81d4aa145a657bc1591efaa5d09eb7aaa))
* 客户端响应代码添加 ([907b92d](https://github.com/ctywzy/MyDubbo/commit/907b92d6d6b344a141e33015bc241b9b82e5b8a6))
* 利用netty的Bufbyte进行数据传输 ([4772ff1](https://github.com/ctywzy/MyDubbo/commit/4772ff1993bc9e35a3aa4e26cd5efdac8bde4f32))
* 完善服务端向客户端发消息的字节码处理流程 ([7e34570](https://github.com/ctywzy/MyDubbo/commit/7e34570912af9d1cd4a2ef45e49683beabe674c9))
* 晚上超时后client侧的addResponse方法 ([3ceecd0](https://github.com/ctywzy/MyDubbo/commit/3ceecd0ba79db1ebb0b9b7df42e1ce416e872a4e))
* 由自动连接调用改为主动调用 ([ae096e9](https://github.com/ctywzy/MyDubbo/commit/ae096e9732b475329bbf4f6ed803ff97e89f3d68))
* changeLog 功能添加 ([a8e3cec](https://github.com/ctywzy/MyDubbo/commit/a8e3cec532f00a8ec5908ed74b82ed0af9906c08))



