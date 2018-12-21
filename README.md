# EDE
这是一款基于事件驱动的流程引擎（Event-Drive Engine）
在master主干上已经完成了编译模块中的函数与事件关系抽取功能
目前开发更新逻辑事件树的功能，在当前的update-logictree分支上进行
新增一个epc-runtime模块来完成这个功能

第一步：通过epc-compile模块中的EpcComplier类解析EPML文件，将一个EPML文件对应的一个流程模型与该模型对应的编辑器保存到
ProcessInfo类中的processModelList链表中。
第二步：开启流程中消息事件的监听,epc-runtime模块中的ReceiveEventService用于监听所有系统管理下的事件主题
当有事件消息到达的时候，ReceiveEventService首先对消息进行解析，判断出消息属于哪一个流程模型，然后交给ProcessManager处理
ProcessManager是一个处理流程模型和对应流程实例的任务处理器，如果流程消息是开始事件，则创建流程实例
如果流程消息能够找到对应的流程实例，则通过updateProcessInstance方法更新该流程实例的状态（这里更新的其实的逻辑节点的状态）
当更新完逻辑节点的状态以后，判断是否执行函数，继续第二步中的操作。
重点：流程实例创建和更新流程实例的细节
