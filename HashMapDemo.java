package cn.itcast_01;

import java.util.HashMap;
import java.util.Set;

public class HashMapDemo {

	public static void main(String[] args) {
		HashMap<Student, String> hm = new HashMap<>();
		
		Student s1 = new Student("张三", 23);
		Student s2 = new Student("李四", 24);
		Student s3 = new Student("王五", 25);
		
		hm.put(s1, "zhangsan");
		hm.put(s2, "lisi");
		hm.put(s3, "wangwu");
		
		Set<Student> set = hm.keySet();
		for (Student value : set) {
			String s = hm.get(value);
			System.out.println(value.getName() + "---" + value.getAge() + s);
		}
	}

}
