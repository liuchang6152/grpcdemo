1.本项目包括父工程grpcdemo，两个子工程 grpcclint和grpcserver。主要演示GRPC的客户端和服务端调用基本功能,
  基于springboot的项目。

2.用到工具JDK8+、protocbuffer3.11、maven，我的开发工具是idea。

3.server端的src\main\proto中的.proto文件是生成代码的proto文件。具体格式见网络上protoc的教程。
  grpc调用方式主要有四种
  1 客户端用字符串调用，服务端用字符串响应（同步）
  2 客户端用流调用，服务端用字符串响应（同步）
  3 客户端用基本类型调用，服务端用流响应（异步）
  4 客户端用流调用，服务端用流响应（异步）

  看以下.proto文件中的代码是定义了三个服务端的方法。分别表示前三种方法。第四种不常用。
  service StudentService{
      rpc GetRealNameByUsername(MyRequest) returns(MyResponse){ }
      rpc GetStudentsByAge(StudentRequest) returns (stream StudentResponse){}
      rpc GetStudentsWrapperByAges(stream StudentRequest) returns (StudentResponseList){}
  }

3.客户端和服务端的src\main\java\com\liuchang\proto 代码都是maven生成的。只要写好了.proto，引入官方的
  maven build插件（见父工程grpcdemo的pom文件），idea执行maven-install就会自动生成代码，默认在target文
  件里。拷到项目里即可。