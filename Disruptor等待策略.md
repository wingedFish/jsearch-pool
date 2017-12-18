#### 等待策略(WaitStrategy)
#### 一、BlockingWaitStrategy
~~~
processorNotifyCondition.await();
~~~
线程阻塞方式,等待生成者唤醒

#### 二、YieldingWaitStrategy
改策略适应于大数据处理,ring buffer中会一直有数据.但是比较耗费CPU.
~~~
if (0 == counter)
        {
            Thread.yield();
        }
~~~
循环取100次,取不到数据,调用线程yield()方法进入可运行状态,出让时间片,让同等优先级的线程执行.但是此方法并不会进入等待/阻塞状态,所以该线程可能还会被操作系统立即调用.

#### 三、BusySpinWaitStrategy
自旋等待策略,不做出让CPU时间片的操作,适应于Ring Buffer中一直有数据存在的场景.
~~~
 while ((availableSequence = dependentSequence.get()) < sequence)
        {
            barrier.checkAlert();
        }
~~~

#### 四、SleepingWaitStrategy

~~~
else if (counter > 0)
        {
            --counter;
            Thread.yield();
        }
        else
        {
            LockSupport.parkNanos(1L);
        }
~~~
该策略综合了策略一和二,在开始的前100次重试中通过yield()出让时间片方式,后面100次阻塞1L Nanos.

#### 五、TimeoutBlockingWaitStrategy
超时未获取到数据,抛出异常.
~~~
nanos = processorNotifyCondition.awaitNanos(nanos);
if (nanos <= 0)
{
    throw TimeoutException.INSTANCE;
}
~~~
到达等待时间还未被生产者唤醒,则抛出超时异常.
