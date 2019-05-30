## 1、什么是Spring ##
 - Spring是分层的JavaSE/EE full-stack(一站式)轻量级开源框架
 - 以IOC（控制反转）和AOP（面向切面编程）为内核
	* IOC：将对象的创建权，交由Spring完成

## 2、应用Spring的好处 ##
 - 方便解耦，简化开发
	* Spring就是一个大工厂，可以将所有对象创建和依赖关系维护，交给Spring管理
 - AOP编程支持
	* Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能
 - 声明式事务的支持
	* 只需要通过配置就可以完成对事务的管理，而无序手动编程
 - 方便程序的测试
	* Spring对Junit4支持，可以通过注解方便的测试Spring程序
 - 方便集成各种优秀框架
	* Spring不排斥各种优秀的开源框架，其内部提供了对各种游戏的框架（Struts、Hibernate、MyBatis、Quartz等）的直接支持
 - 降低了JavaEE API的使用难度
	* Spring对JavaEE开发中非常难用的API（JDBC、JavaMail、远程调控等），提供了封装，使这些API应用难度大大降低

## 3、Spring入门案例 ##
 - A、创建工程引入相应的jar包
	* spring-beans-3.2.0.RELEASE.jar
	* spring-context-3.2.0.RELEASE.jar
	* spring-core-3.2.0.RELEASE.jar
	* spring-expression-3.2.0.RELEASE.jar
	* com.springsource.org.apache.commons.logging-1.1.1.jar(日志包)
	* com.springsource.org.apache. log4j-1.2.15.jar
 - B、创建Spring的配置文件
	* 创建一个applicationContext.xml
	* xsd-config.html，引入xml约束（beans约束）
	* 在配置中配置类

		`<bean id="userService" class="cn.tong.demo1.UserServiceImpl"></bean>`
    * 创建接口
    * 创建实现类实现接口重写里面的方法
	
	* 创建测试类（cn.tong.demo1）

		    //通过Spring容器获得UserService对象
				//加载Spring框架配置文件
    		ApplicationContext applicationContext = 
    				new ClassPathXmlApplicationContext("applicationContext.xml");
    		
    		//通过getBean方法获得Spring容器管理Bean对象
				//通过id名（userService），找到对应的xml配置中对应的bean
    		UserService userService = (UserService) applicationContext.getBean("userService");
    		userService.sayHello();

## 4、IOC和DI的区别 ##
 - IOC：控制反转，将对象的创建权交由Spring完成
 - DI：依赖注入，在Spring创建对象的过程中，把对象依赖的属性注入到类中

## 5、Spring框架Bean实例化的方式 ##
 - **构造方法实例化（默认无参数）**

	`<bean id="userService" class="cn.tong.demo1.UserServiceImpl"></bean>`
 - 静态工厂实例化
 - 实例化工厂实例化

## 6、Bean的其他配置 ##
 - id和name的区别
	* id遵守XML约束对id的约束,id属性保证这个属性是唯一的
	* name没有这些要求
 - 类的作用范围
	* scope属性
        - singleton：单例的（默认值）
        - prototype：多例的
 - Bean的声明周期
    * 配置初始化和销毁的方法
        - init-method=""
        - destroy-method=""
    * 销毁的时候必须手动关闭工厂，而且只对scope="singleton"有效
    * bean的11个生命周期（其中5和8比较重要）
        - 如果存在类实现BeanPostProcessor(后处理Bean)，执行postProcessBeforeInitialization
        - 如果存在类实现BeanPostProcessor(处理Bean)，执行postProcessAfterInitialization

## 7、Bean中属性注入 ##
 - 构造器注入

        <!-- 在Spring配置文件中，通过<constructor-arg>设置注入的属性 -->
    	<bean id="car" class="cn.tong.demo1.Car">
    		<constructor-arg name="name" value="宝马"/>
    		<constructor-arg name="price" value="1000000"/>
    	</bean>
    
        //测试是否注入成功
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Car car = (Car) applicationContext.getBean("car");
		System.out.println(car);
 - setter方法注入

        <bean id="car2" class="cn.tong.demo1.Car2">
			<property name="name" value="奔驰"/>
			<property name="price" value="500000"/>
	    </bean>
 - SpEL属性的注入

        语法：#{表达式}
    	<bean id="" value="#{表达式}"> 
    
    	<bean id="car2" class="cn.tong.demo2.Car2">
    		<property name="name" value="#{'大众'}"></property>
    		<property name="price" value="#{'120000'}"></property>
    	</bean>
 - 集合属性的注入
    * List集合    
        
            //类
            private List<String> list;

        	public void setList(List<String> list) {
        		this.list = list;
        	}

        	public String toString() {
        		return "CollectionBean [list=" + list + "]";
        	}
            
            //xml配置
            <bean id="collectionBean" class="cn.tong.demo3.CollectionBean">
        		<property name="list">
        			<list>
        				<value>星期一</value>
        				<value>星期二</value>
        			</list>
        		</property>
        	</bean>
    * Set集合
    * Map集合
        
            //xml配置
            <bean id="collectionBean" class="cn.tong.demo3.CollectionBean">
        		<property name="map">
        			<map>
        				<entry key="张三" value="23"/>
        				<entry key="李四" value="24"/>
        			</map>
        		</property>
        	</bean>

## 8、IOC装配Bean(注解方式) ##
 - 使用注解定义Bean
    * Spring提供的三个注解
        - @Repository：用于对DAO实现类进行标注
        - @Service：用于对Service实现类进行标注
        - @Controller：用于对Controller实现类进行标注
    * xsd-config.html，引入xml约束（context schema约束）

            //配置自动扫描哪个包中的东西
            <context:component-scan base-package="cn.tong.demo4"/>
 - 对象属性注入
    * @Autowired
    * @Qualified("userDao)
    * private UserDao userDao;
 - Spring 3.0提供使用Java类定义Bean信息的方法
    
        @Configuration
        public class BeanConfig {
    
        	@Bean(name="car")
        	public Car showCar(){
        		Car car = new Car();
        		car.setName("长安");
        		car.setPrice(40000d);
        		return car;
        	}
        	
        }

        //测试
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		Car car = (Car) applicationContext.getBean("car");
		System.out.println(car);

## 9、Spring整合web开发 ##
 - 正常整合Servlet和Spring没有问题，但是每次执行Servlet的时候加载Spring环境
    * 解决办法：在Servlet的init方法中加载Spring环境
        - 当前这个Servlet可以使用，但是其他的Servlet使用不了
        - 将加载的信息内容放到ServletContext中，ServletContext对象是全局的对象，是服务器启动的时候创建的，在创建ServletContext的时候就加载Spring环境
        - ServletContextListener：用于监听ServletContext对象的创建和销毁
    * 导入spring-web-3.2.0.RELEASE.jar
    * 创建类
        - service实现类(sayHello)
        - servlet（doGet）

                WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        - applicationContext配置（userService）
        - web.xml配置
                
                <listener>
              	    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
                </listener>
                <context-param>
              	    <param-name>contextConfigLocation</param-name>
              	    <param-value>classpath:applicationContext.xml</param-value>
                </context-param>

## 10、Spring集成JUnit测试 ##
 - 导入spring-test-3.2.0.RELEASE.jar
 - 测试
    
        @RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration(locations="classpath:applicationContext.xml")
        public class SpringTest {
    	@Autowired
    	private UserService userService;
    	
    	@Test
    	public void demo1(){
    		userService.sayHello();
    	   }
        }

## 11、AOP概述 ##
 - AOP概述
    * AOP：面向切面编程
    * AOP采取横向抽取机制
    * Spring AOP使用纯Java实现，不需要专门的编译过程和类加载器，在运行期通过代理方式向目标类织入增强代码
    * AspectJ是一个基于Java语言的AOP框架，它扩展了Java语言，提供了一个专门的编译器，在编译时提供横向代码的织入
 - AOP底层原理
    * 代理机制
        - 动态代理（JDK中使用）：JDK的动态代理，对实现了接口的类生成代理
 - Spring的AOP代理
    * JDK动态代理：对实现了接口的类生成代理
    * CGLib代理机制：对类生成代理
 - AOP术语
    * Jointpoint(连接点)：是指那些被拦截到的点。在Spring中，这些点指的都是方法，因为Spring只支持方法类型的链接点
    * Pointcut(切入点)：是指我们要对哪些Jointpoint进行拦截的定义
    * Advice(通知/增强)：是指拦截到Jointpoint之后所要做的事情就是通知。通知分为前置通知，后置通知，异常通知，环绕通知
    * Introduction(引介)：引介是一种特殊的通知在不修改类代码的前提下，Introduction可以在运行期为类动态的添加一些方法或Field
    * Target(目标对象)：代理的目标对象
    * Weaving(织入)：是指把增强应用到目标对象来创建新的代理对象的过程。Spring采用动态代理织入，而AspectJ采用编译器织入和类装载期织入
    * Proxy(代理)：一个类被AOP织入增强后，就产生一个结果代理类
    * Aspect(切面)：是切入点和通知（引介）的结合
### 12、AOP的底层实现 ###
 - JDK动态代理
    * 接口

            public interface UserDao {
                public void add();
                public void update();
            }
    * 实现类

            public class UserDaoImpl implements UserDao {
    
            	public void add() {
            		System.out.println("添加用户");
            	}
                    
            	public void update() {
            		System.out.println("更新用户");
            	}
        
            }
    * 代理类

            public class JDKProxy implements InvocationHandler {
            	private UserDao userDao;
            
            	public JDKProxy(UserDao userDao) {
            		super();
            		this.userDao = userDao;
            	}
            	
            	public UserDao createProxy() {
            		UserDao proxy = 
            				(UserDao) Proxy.newProxyInstance(
            				userDao.getClass().getClassLoader(), 
            				userDao.getClass().getInterfaces(), 
            				this);
            		return proxy;
            	}
            
                //调用目标对象的任何一个方法，都相当于调用了invoke()
            	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            		if("add".equals(method.getName())) {
            			System.out.println("日志记录==========");
            			Object result = method.invoke(userDao, args);
            			return result;
            		}
            		return method.invoke(userDao, args);
            	}
            }
    * 测试类

            public class SpringTest {
            	
            	@Test
            	public void demo1() {
            		UserDao userDao = new UserDaoImpl();
            		UserDao proxy = new JDKProxy(userDao).createProxy();
            		proxy.add();
            		proxy.update();
            	}
            }
 - CGLIB动态代理
    * CGLIB可以在运行期扩展Java类与实现Java接口
    * CGLIB生成代理机制：其实生成了一个真实对象的子类
    * 代理类

            public class CGLibProxy implements MethodInterceptor {
            	private ProductDao productDao;
            
            	public CGLibProxy(ProductDao productDao) {
            		super();
            		this.productDao = productDao;
            	}
            	
            	public ProductDao createProxy() {
            		//创建核心类
            		Enhancer enhancer = new Enhancer();
            		//为其设置父类
            		enhancer.setSuperclass(productDao.getClass());
            		//设置回调
            		enhancer.setCallback(this);
            		//创建代理
            		return (ProductDao) enhancer.create();
            	}
            
            	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            		if("add".equals(method.getName())) {
            			System.out.println("日制记录=======");
            			Object object = methodProxy.invokeSuper(proxy, args);
            			return object;
            		}
            		
            		return methodProxy.invokeSuper(proxy, args);
            	}
            }
    * 测试类

            public class SpringTest2 {
            	
            	@Test
            	public void demo1() {
            		ProductDao productDao = new ProductDao();
            		ProductDao proxy = new CGLibProxy(productDao).createProxy();
            		proxy.add();
            		proxy.update();
            	}
            }
 - **结论：Spring框架，如果类实现了接口，就使用JDK的动态代理生成代理对象，如果这个类没有实现任何接口，使用CGLIB生成代理对象**

## 13、Spring中的AOP ##
 - AOP不是由Spring定义，是AOP联盟组织定义的
 - Spring中的通知（增强代码）
    * 前置通知 org.springframework.aop.MethodBeforeAdvice
        - 在目标方法执行前实施增强
    * 后置通知 org.springframework.aop.MethodAfterReturningAdvice
        - 在目标方法执行后实施增强
    * 环绕通知 org.aopalliance.intercept.MethodInterceptor
        - 在目标方法执行前后实施增强
    * 异常抛出通知 org.springframework.aop.ThrowsAdvice
        - 在方法抛出异常后实施增强
## 14、Spring中的切面类型 ##
 - Advisor：Spring中传统切面
    * Advisor：都是有一个切点和一个通知组合
    * Aspect：多个切点和多个通知组合
 - Advisor：代表一般切面，Advice本身就是一个切面，对目标类所有方法进行拦截（**不带有切点的切面，对所有方法进行拦截**）
 - PointcutAdvisor：代表具有切点的切面，可以指定拦截目标类哪些方法（**带有切点的切面，针对某个方法进行拦截**）
## 15、Spring的AOP开发 ##
 - 针对所有方法的增强（不带有切点的切面）
    * 第一步：导入相应的jar包
        - spring-aop-3.2.0.RELEASE.jar
        - com.springsource.org.aopalliance-1.0.0.jar
    * 第二步：编写被代理对象
        - CustomerDao接口

                public interface CustomerDao {
                	public void add();
                	public void update();
                	public void delete();
                	public void find();
                }
        - CustomerDaoImpl实现类

                public class CustomerDaoImpl implements CustomerDao {
                
                	public void add() {
                		System.out.println("添加客户");
                	}
                
                	public void update() {
                		System.out.println("修改客户");
                	}
                
                	public void delete() {
                		System.out.println("删除客户");
                	}
                
                	public void find() {
                		System.out.println("查询客户");
                	}
                
                }   
    * 第三步：编写增强代码

                public class MyBeforeAdvice implements MethodBeforeAdvice {
                
                    //method：执行方法；args：参数；target：目标对象
                	public void before(Method method, Object[] args, Object target) throws Throwable {
                		System.out.println("前置增强...");
                	}
                	
                }
    * 第四步：生成代理（配置生成代理）
        - 生成代理Spring基于ProxyFactoryBean类，底层自动选择使用JDK的动态代理还是CGLIB代理
        - 属性
            * target：代理的目标对象
            * proxyInterfaces：代理要实现的接口
            * proxyTargetClass：是否对类代理而不是接口，设置为true时，使用CGLIB代理
            * interceptorNames：需要织入目标的Advice
            * singleton：返回代理是否为单实例，默认为单例
            * optimize：当设置为ture时，强制使用CGLIB
        - xml配置

                <!-- 定义增强目标 -->
            	<bean id="customerDao" class="cn.itcast.spring3.demo3.CustomerDaoImpl"></bean>
            	
            	<!-- 定义增强 -->
            	<bean id="beforeAdvice" class="cn.itcast.spring3.demo3.MyBeforeAdvice"></bean>
            	
            	<!-- Spring支持配置生成代理 -->
            	<bean id="customerDaoProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
            		
            		<!-- 设置目标对象 -->
            		<property name="target" ref="customerDao"/>
            		
            		<!-- 设置实现的接口，value中写接口的全路径 -->
            		<property name="proxyInterfaces" value="cn.itcast.spring3.demo3.CustomerDao"/>
            		
            		<!-- 需要使用value：要的名称 -->
            		<property name="interceptorNames" value="beforeAdvice"/>
            	</bean>
        - 测试类

                @RunWith(SpringJUnit4ClassRunner.class)
                @ContextConfiguration("classpath:applicationContext.xml")
                public class SpringTest3 {
                	@Autowired
                	@Qualifier("customerDaoProxy")
                	private CustomerDao customerDao;
                	
                	@Test
                	public void demo1() {
                		customerDao.add();
                		customerDao.update();
                		customerDao.delete();
                		customerDao.find();
                	}
                }
 - 带有切点的切面（针对目标对象的某些方法进行增强）
    * PointcutAdvisor接口：
        - DefaultPointcutAdvisor：最常用的切面类型，它可以通过任意Pointcut和Advice组合定义切面
        - RegexpMethodPointcutAdvisor：构造正则表达式切点切面
    * 第一步：创建被代理对象
        - OrderDao

                public class OrderDao {
                	public void add() {
                		System.out.println("添加客户");
                	}
                
                	public void update() {
                		System.out.println("修改客户");
                	}
                
                	public void delete() {
                		System.out.println("删除客户");
                	}
                	
                	public void find() {
                		System.out.println("查询客户");
                	}
                }
    * 第二步：编写增强的类

                public class MyAroundAdvice implements MethodInterceptor {
                
                	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                		System.out.println("环绕前增强。。。");
                        //执行目标对象的方法
                		Object result = methodInvocation.proceed();
                		System.out.println("环绕后增强。。。");
                		return result;
                	}
                	
                }
    * 第三步：生成代理

            	<!-- 定义目标对象 -->
            	<bean id="orderDao" class="cn.itcast.spring3.demo4.OrderDao"></bean>
            	
            	<!-- 定义增强 -->
            	<bean id="aroundAdvice" class="cn.itcast.spring3.demo4.MyAroundAdvice"></bean>
            	
            	<!-- 定义切点切面 -->
            	<bean id="myPointCutAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
            		<property name="pattern" value=""/>
            		<property name="advice" ref="aroundAdvice"/>
            	</bean>
            	
            	<!-- 定义生成代理对象 -->
            	<bean id="orderDaoProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
            		
            		<!-- 配置目标 -->
            		<property name="target" ref="orderDao"/>
            		
            		<!-- 针对类的代理 -->
            		<property name="proxyTargetClass" value="true"/>
            		
            		<!-- 在目标上应用增强 -->
            		<property name="interceptorNames" value="myPointCutAdvisor"></property>
            	</bean>
## 16、自动代理 ##
 - 自动代理（基于后处理Bean，在Bean创建的过程中完成的增强，生成Bean就是代理）
    * BeanNameAutoProxyCreator：根据Bean名称创建代理
    * DefaultAdvisorAutoProxyCreator：根据Advisor本身包含信息创建代理
        - AnnotationAwareAspectJAutoProxyCreator：基于Bean中的AspectJ注解进行自动代理
 - BeanNameAutoProxyCreator：按名称生成代理
    
            <!-- 定义目标对象 -->
        	<bean id="customerDao" class="cn.itcast.spring3.demo5.CustomerDaoImpl"></bean>
        	
        	<!-- 定义增强 -->
        	<bean id="beforeAdvice" class="cn.itcast.spring3.demo5.MyBeforeAdvice"></bean>
        	
        	<!-- 自动代理：按名称的代理，基于后处理Bean，后处理Bean不需要配置ID -->
        	<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
        		<property name="beanNames" value="customerDao"></property>
        		<property name="interceptorNames" value="beforeAdvice"></property>
        	</bean>
    * 测试类

            @RunWith(SpringJUnit4ClassRunner.class)
            @ContextConfiguration("classpath:applicationContext2.xml")
            public class SpringTest3 {
            	@Autowired
            	@Qualifier("customerDao")
            	private CustomerDao customerDao;
            	
            	@Test
            	public void demo1() {
            		customerDao.add();
            		customerDao.update();
            		customerDao.delete();
            		customerDao.find();
            	}
            }
 - DefaultAdvisorAutoProxyCreator：根据切面中定义的信息生成代理

            <!-- 定义目标对象 -->
        	<bean id="customerDao" class="cn.itcast.spring3.demo5.CustomerDaoImpl"></bean>
        	<bean id="orderDao" class="cn.itcast.spring3.demo4.OrderDao"></bean>
        	
        	<!-- 定义增强 -->
        	<bean id="beforeAdvice" class="cn.itcast.spring3.demo5.MyBeforeAdvice"></bean>
        	<bean id="aroundAdvice" class="cn.itcast.spring3.demo4.MyAroundAdvice"></bean>
        	
        	<!-- 定义一个带有切点的切面 -->
        	<bean id="myPointcutAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
        		<property name="pattern" value=".*add.*"></property>
        		<property name="advice" ref="aroundAdvice"></property>
        	</bean>
        	
        	<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"></bean>

## 17、Spring的AspectJ的AOP（*********） ##
 - AspectJ表达式
    * 语法：execution(表达式) 
        - execution(<访问修饰符>？<返回类型><方法名>(<参数>)<异常>)
    * execution("* cn.tong.spring3.demo1.dao.*(..)")
        - 只检索当前包
    * execution("* cn.tong.spring3.demo1.dao..*(..)")
        - 检索包及当前包的子包
 - AspectJ增强
    * @Before：前置通知，相当于BeforAdvice
    * @AfterReturning：后置通知，相当于AferReturningAdvice
    * @Around：环绕通知，相当于MetodInterceptor
    * @AfterThrowing：抛出通知，相当于ThrowAdvice
    * @Afer：最终final通知，不管是否异常，该通知都会执行
 - **基于注解**
    * 第一步：引入相应jar包
        - aspectj依赖aop环境
        - spring-aspects-3.2.0.RELEASE.jar
        - com.springsource.org.aspectj.weave-1.6.8.RELEASE.jar
    * 第二步：编写被增强的类
        - UserDao

                 public class UserDao {
                	public void add(){
                		System.out.println("添加用户");
                	}
                	public int update(){
                		System.out.println("修改用户");
                		return 1;
                	}
                	public void delete(){
                		System.out.println("删除用户");
                	}
                	public void find(){
                		System.out.println("查询用户");
                		//int d = 1/ 0;
                	}
                }
    * 第三步：使用AspectJ注解形式

                @Aspect
                public class MyAspect {
                	
                	@Before("execution(* cn.itcast.spring3.demo1.UserDao.add(..))")
                	public void before(JoinPoint joinPoint){
                		System.out.println("前置增强...."+joinPoint);
                	}
                	
                	@AfterReturning(value="execution(* cn.itcast.spring3.demo1.UserDao.update(..))",returning="returnVal")
                	public void afterReturin(Object returnVal){
                		System.out.println("后置增强....方法的返回值:"+returnVal);
                	}
                	
                	@Around(value="MyAspect.myPointcut()")
                	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
                		System.out.println("环绕前增强....");
                		Object obj = proceedingJoinPoint.proceed();
                		System.out.println("环绕后增强....");
                		return obj;
                	}
                	
                	@AfterThrowing(value="MyAspect.myPointcut()",throwing="e")
                	public void afterThrowing(Throwable e){
                		System.out.println("不好了 出异常了!!!"+e.getMessage());
                	}
                	
                	@After("MyAspect.myPointcut()")
                	public void after(){
                		System.out.println("最终通知...");
                	}
                	
                	@Pointcut("execution(* cn.itcast.spring3.demo1.UserDao.find(..))")
                	private void myPointcut(){}
                }
    * 第四步：创建applicationContext.xml
        - 引入aop的约束
        - <aop:aspectj-autoproxy />自动生成代理

                <!-- 自动生成代理  底层就是AnnotationAwareAspectJAutoProxyCreator -->
            	<aop:aspectj-autoproxy />
            
            	<bean id="userDao" class="cn.itcast.spring3.demo1.UserDao"></bean>
            
            	<bean id="myAspect" class="cn.itcast.spring3.demo1.MyAspect"></bean>
 - AspectJ的通知类型
    * @Before：前置通知，相当于BeforeAdvice
        - 就在方法之前执行。没有办法阻止目标方法执行
    * @AfterReturning：后置通知，相当于AfterReturningAdvice
        - 后置通知，获得方法返回值
    * @Around：环绕通知，相当于MethodInterceptor
        - 可以在方法之前和之后执行，而且可以阻止目标方法的执行
    * @AfterThrowing：抛出通知，相当于ThrowAdvice
    * @Afer：最终final通知，不管是否异常，该通知都会执行
 - Advisor和Aspect的区别
    * Advisor：Spring传统意义上的切面，支持一个切点和一个通知的组合
    * Aspect：可以支持多个切点多个通知的组合
 - **基于XML**
    * 第一步：编写被增强的类
        - ProductDao

    * 第二步：定义切面

            public class MyAspectXML {
            	public void before() {
            		System.out.println("前置通知。。。");
            	}
            	
            	public void afterReturning(Object returnVal) {
            		System.out.println("后置通知。。。" + returnVal);
            	}
            	
            	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            		System.out.println("环绕前通知。。。");
            		Object result = proceedingJoinPoint.proceed();
            		System.out.println("环绕后通知。。。");
            		return result;
            	}
            	
            	public void afterThrowing(Throwable e) {
            		System.out.println("异常通知。。。" + e.getMessage());
            	}
            	
            	public void after() {
            		System.out.println("最终通知。。。");
            	}
            }
    * 第三步配置applicationContext.xml

            <!-- 定义被增强的类 -->
        	<bean id="productDao" class="cn.itcast.spring3.demo2.ProductDao"></bean>
        	
        	<!-- 定义切面 -->
        	<bean id="myAspectXML" class="cn.itcast.spring3.demo2.MyAspectXML"></bean>
        	
        	<!-- 定义切点 -->
        	<aop:config>
        		<aop:pointcut expression="execution(* cn.itcast.spring3.demo2.ProductDao.add(..))" id="mypointcut"/>
        		<aop:aspect ref="myAspectXML">
        			<!-- 前置通知 -->
        			<aop:before method="before" pointcut-ref="mypointcut"/>
        			
        			<!-- 后置通知 -->
        			<aop:after-returning method="afterReturning" pointcut-ref="mypointcut" returning="returnVal"/>
        		
        			<!-- 环绕后通知 -->
        			<aop:around method="around" pointcut-ref="mypointcut"/>
        			
        			<!-- 异常通知 -->
        			<aop:after-throwing method="afterThrowing" pointcut-ref="mypointcut"  throwing="e"/>
        			
        			<!-- 最终通知 -->
        			<aop:after method="after" pointcut-ref="mypointcut"/>
        		</aop:aspect>
        	</aop:config>

## 18、Spring的JDBCTemplate ##
 - Spring对持久层技术支持
    * JDBC：org.springframework.jdbc.core.JdbcTemplate
    * Hibernate3.0：org.springframework.orm.hibernate3.HibernateTemplate
    * Ibatis(Mybatis)：org.springframework.orm.ibatis.SqlMapClientTemplate
    * JPA：org.springframework.orm.jpa.JpaTemplate
 - 开发JDBCTemplate入门
    * 第一步：引入相应的jar包
        - spring-tx-3.2.0.RELEASE.jar
        - spring-jdbc-3.2.0.RELEASE.jar
        - mysql驱动
    * 第二步：创建applicationContext.xml
    * 第三步：编写一个测试类

## 19、Spring的事务管理（事务处在Service层） ##
 - Spring提供事务管理API
    * PlatformTransactionManager：平台事务管理器
        - commit(TransactionStatus status)
        - getTransaction(TransactionDefinition definition)
        - rollback(TransactionStatus status)
    * TransactionDefinition：事务定义
        - IOSLation_xxx：事务隔离级别
        - PROPAGATION_XXX：事务的传播行为
    * TransactionStatus：事务状态
        - 是否有保存点
        - 是否是一个新的事务
        - 事务是否已经提交
    * 关系：PlatformTransactionManager通过TransactionDefinition设置事务相关信息管理事务，管理事务过程中，产生一些事务状态：状态由TransactionStatus记录。
    * API详解：
        - PlatformTransactionManager：接口
        - Spring为不同的持久化框架提供了不同PlatformTransactionManager实现接口
        - org.springframework.orm.jpa.JpaTransactionManager：使用Hibernate3.0版本进行持久化数据时使用
        - org.springframework.jdo.JdoTransactionManager：当持久化机制是Jdo时使用
        - org.springframework.transaction.jta.JtatransactionManager：使用一个JTA实现来管理事务，在一个事务跨越多个资源时必须使用
    * 事务的传播行为（不是JDBC事务管理，用来解决实际开发的问题）
        - **PROPAGATION_REQUIRED**：支持当前事务，如果不存在就新建一个
            * A，B：如果A有事务，B使用A的事务，如果A没有事务，B就开启一个新的事务（A，B是在一个事务中）
        - PROPAGATION_SUPPORTS：支持当前的事务，如果不存在，就不使用事务
            * A，B：如果A有事务，B使用A的事务，如果A没有事务，B就不使用事务
        - PROPAGATION_MANDATORY：支持当前事务，如果不存在，抛出异常
            * A，B：如果A有事务，B使用A的事务，如果A没有事务，抛出异常
        - **PROPAGATION_REQUIRES_NEW**：如果有事务存在，挂起当前事务，创建一个新的事务
            * A，B：如果A有事务，B将A的事务挂起，重新创建一个新的事务（A，B不在一个事务中，事务互不影响）
        - PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果有事务存在，挂起当前事务
            * A，B：非事务的方式运行，A有事务，就会挂起当前的事务
        - PROPAGATION_NEVER：以非事务方式运行，如果有事务存在，抛出异常
        - **PROPAGATION_NESTED**：如果当前事务存在，则嵌套事务执行
            * 基于SavaPoint技术
            * A，B：A有事务，A执行之后，将A事务执行之后的内容保存到SavePoint。B事务有异常的话，用户需要自己设置事务提交还是回滚
 - Spring的事务管理（两类）
    * 编程式事务管理
        - 手动编写代码完成事务管理
    * 声明式事务管理
        - 不需要手动编写代码，配置
 - 事务操作环境的搭建
    * A、创建一个web项目
        - 导入相应jar包
        - 引入配置文件：applicationContext.xml、log4j.properties、jdbc.properties
    * B、创建类
        - AccountDao

                public interface AccountDao {
                	public void out(String from,Double money);
                	public void in(String to,Double money);
                }
        - AccountDaoImpl

                public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
                	
                	public void out(String from, Double money) {
                		String sql = "update account set money = money - ? where name  = ?";
                		this.getJdbcTemplate().update(sql, money,from);
                	}
                	
                	public void in(String to, Double money) {
                		String sql = "update account set money = money + ? where name  = ?";
                		this.getJdbcTemplate().update(sql, money , to);
                	}
                
                }
        - AccountService

                public interface AccountService {
                	public void transfer(String from,String to,Double money);
                }
        - AccountServiceImpl

                public class AccountServiceImpl implements AccountService {
                	private AccountDao accountDao;
                	private TransactionTemplate transactionTemplate;
                	public void setAccountDao(AccountDao accountDao) {
                		this.accountDao = accountDao;
                	}
                	
                	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
                		this.transactionTemplate = transactionTemplate;
                	}
                
                	
                	public void transfer(final String from, final String to, final Double money) {
                		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                			@Override
                			protected void doInTransactionWithoutResult(TransactionStatus status) {
                				accountDao.out(from, money);
                				accountDao.in(to, money);
                			}
                		});
                	}
                
                }
