package com.capinfo.cn;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {
 //��person����ƴ������
	@Override
	public int compare(Person p1, Person p2) {

		return SpellUtil.getPingYin(p1.getName()).compareTo(
				SpellUtil.getPingYin(p2.getName()));

	}

}
