# JVM 

Java源文件  经编译为.class文件， 可以通过JVM 翻译成机器码从而可以让机器识别，在机器上运行（Java是可移植，体现在这里，经过不通版本的JVM进行汇编，然后就可以在机器上【linux、Windows】运行）

Javap反汇编指令：将.class文件反汇编成人能解读的指令

JVM主要分为  堆和栈两块大内存【堆是线程共享的、栈是线程私有的】

线程共享的还有方法区（元空间 JDK1.8之后的）

**例子：**

Person person = new Person();

new一个对象

源Person.class存在于元空间；

new Person() new出来的对象存在于堆空间；

person 对象的引用存在于栈空间

对象使用：由栈指向堆，堆指向元空间

```java
Person person1 = new Person()
Person person2 = new Person()
// person1、person2存在于栈空间；
// 第一个new出来的Person存在于堆空间；
// 第二个new出来的Person存在于堆空间；
// 两个对象的引用都指向元空间，即Person.class
```



## 栈

栈中有栈帧（一个对象一个栈帧）；栈帧包含有：局部变量表、操作数栈、动态链接、返回地址、栈数据区、程序计数器  等。。。

程序计数器：就是在反汇编之后的文件中每一行指令对应的行号，机器在执行时，会将其计入程序计数器，以便知道运行到哪一步





## 堆

主要分为两大块  新生代 和 老年代 （内存比例  1:2）【新生代主要采用Minor GC回收机制；老年代主要采用Full GC ( 或称为 Major GC ) 回收机制】

新生代又分为 eden区、S0(From Survivor)、S1(To Survivor) （内存比例  8:1:1）

new的对象 都会先存放于eden区，当eden区满后会进行垃圾回收，检测还在使用的对象就会进入from区，当from满之后，会检测是否有在使用，会回收将继续使用的存入S1区（此时S1区就变为from），下一次GC时，从S1-->S0；当一个对象，发生15次回收都没有回收掉时，就会进入老年代。