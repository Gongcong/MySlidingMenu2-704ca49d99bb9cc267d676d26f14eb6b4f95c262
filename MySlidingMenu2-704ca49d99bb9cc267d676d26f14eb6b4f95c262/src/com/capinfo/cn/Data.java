package com.capinfo.cn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.util.Pair;

public class Data {
	
     //�Լ����е�Ԫ�ع���
	@SuppressWarnings("unchecked")
	public static List<Pair<String, List<Person>>> getAllData(List<Person>  allPerson) {
		
		List<Pair<String, List<Person>>> res = new ArrayList<Pair<String, List<Person>>>();
	
		Collections.sort(allPerson, new PersonComparator());//�Լ����е�Ԫ�ذ�������ĸ��ƴ������
	   List< String> firstNicks  = new LinkedList<String>(); //����һ�����ϴ洢���ֵ�����ĸ 
     
	   for(int i=0;i<allPerson.size();i++){
		 
		  String firstPingyingName =SpellUtil.getPingYin((allPerson.get(i).getName())).substring(0, 1).toUpperCase();
		  firstNicks.add(firstPingyingName);

	   }
		//ȥ����������ͬ��Ԫ��
	   dislodgeCommon(firstNicks);
	   
	 //�������
	   List<Person> list;
	   for(int i=0;i<firstNicks.size();i++){
		   String s= firstNicks.get(i);
		     list = new ArrayList<Person>();
		
		      for(int j=0;j<allPerson.size();j++){
		    	  if(s.equals((SpellUtil.getPingYin(allPerson.get(j).getName())).substring(0, 1).toUpperCase())){
		    	
		    		list.add(allPerson.get(j));
		    	  }
		      }
		      
		      res.add(new Pair<String, List<Person>>(s, list));
	   }
	
		return res;
	}
	

	//ȥ����������ͬ��Ԫ��
	 private static void dislodgeCommon(List<String> firstNicks) {
		// TODO Auto-generated method stub
		    Set set = new HashSet();
		      List newList = new ArrayList();
		   for (Iterator iter = firstNicks.iterator(); iter.hasNext();) {
		          Object element = iter.next();
		          if (set.add(element))
		             newList.add(element);
		       } 
		   firstNicks.clear();
		   firstNicks.addAll(newList);
	}
   
}
