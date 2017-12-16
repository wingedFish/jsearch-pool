### DBCP连接池原理
####流程图
####源码分析
##### BasicDataSource.createDataSource
~~~
// create factory which returns raw physical connections
            ConnectionFactory driverConnectionFactory = createConnectionFactory();
~~~
createConnectionFactory方法实现了两个功能：
加载驱动类---同等于JDBC中的class.forName(driverClassName);
~~~
driverFromCCL = Class.forName(driverClassName);
~~~
创建驱动Driver---等同于JDBC中的DriverManager.getDriver(url)
~~~
DriverManager.getDriver(url);
~~~


##### createPoolableConnectionFactory
~~~
new PoolableConnectionFactory(driverConnectionFactory, registeredJmxName);
~~~
创建PoolableConnectionFactory，该类实现了接口PooledObjectFactory，负责创建池子中的对象：PoolableConnection

##### createConnectionPool
~~~
new GenericObjectPool<>(factory, config, abandonedConfig);
~~~
创建管理链接的池对象GenericObjectPool

##### createDataSourceInstance
~~~
PoolingDataSource<PoolableConnection> pds = new PoolingDataSource<>(connectionPool);
~~~
创建管理连接池的数据源

##### connectionPool.addObject();
~~~
for (int i = 0 ; i < initialSize ; i++) {
                    connectionPool.addObject();
}
~~~
创建初始化数量的链接,如果未设置参数initialSize,则不创建

至此，连接池创建完成，相应的数据源也创建完成.

