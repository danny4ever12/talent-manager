package com.ibm.personafusion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ibm.personafusion.model.Person;
import com.ibm.personafusion.model.Person.Role;

public class Engine 
{
	List<Person> people;
	
	public Engine(List<Person> people)
	{
		//this.people = people;
		
		this.people = new ArrayList<Person>();
		for(Person p : people)
		{
			this.people.add(new Person(p.name, p.traits, p.image_url, p.resumeInfo, p.role, p.keyWords));
		}
	}
	
	List<Person> query(Person p)
	{
		this.setQueryPerson(p);
		this.setDistanceWeights(.5, 0 , 5);
		Collections.sort(this.people);
		this.people.remove(0);
		this.convertScores(this.people);
		return this.people;
	}
	
	public List<Person> query(String personName)
	{
		personName = personName.toUpperCase();
		//get person with the person name
		this.setQueryPerson(this.getPersonGivenName(personName));
		this.setDistanceWeights(.5, 0 , .5);
		Collections.sort(this.people);
		this.people.remove(0);
		this.convertScores(this.people);
		return this.people;
	}
	
	Person getPersonGivenName(String personName)
	{
		for(Person p : this.people)
			if(p.name.equals(personName))
				return p;
		return null;
	}
	
	void convertScores(List<Person> people)
	{
		double minDist = Double.MAX_VALUE;
		for(Person p : people)
		{
			if(minDist > p.distToQueryPerson)
				minDist = p.distToQueryPerson;
		}
		
		for(Person p : people)
		{
			p.distToQueryPerson = minDist / (p.distToQueryPerson);
		}
	}
	
	void setQueryPerson(Person p)
	{
		people.get(0).setQueryPerson(p);
	}
	
	void setDistanceWeights(double weightTraits, double weightResume, double weightRole)
	{
		people.get(0).setDistanceWeights(weightTraits, weightResume, weightRole);
	}
}
