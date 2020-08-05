package AlgorithmStudy.study.study01;

public class Test01 {

    // 默认构造方法（即：不显示声明的话  编译后会默认生成）
    public Test01() {
        System.out.println("默认构造方法=====");
    }

    // 自定义构造方法（若自定义了其他有参构造方法，name必须显示创建无参构造方法）
    public Test01(String str1){
        System.out.println("自定义有参构造方法，输出-->" + str1);
    }


}
