package cn.gs.sb_mongo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.gs.sb_mongo.dao.PersonRepository;
import cn.gs.sb_mongo.domain.Person;
import cn.gs.sb_mongo.domain.Tour;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@RequestMapping("/save")
	public Person save(){
		Person p = new Person("刘六", 66);
		Tour t1 = new Tour("纽约", 1970);
		Tour t2 = new Tour("巴黎", 1971);
		Tour t3 = new Tour("伦敦", 1972);
		List<Tour> ts = new ArrayList<Tour>();
		ts.add(t1);
		ts.add(t2);
		ts.add(t3);
		p.setTours(ts);
		return personRepository.save(p);
	}
	
	@RequestMapping("/q1")
	public Person q1(String name){
		Person p = personRepository.findByName(name);
		return p;
	}
	
	@RequestMapping("/q2")
	public List<Person> q2(int age){
		List<Person> ps = personRepository.withQueryFindByAge(age);
		return ps;
	}
	
}
