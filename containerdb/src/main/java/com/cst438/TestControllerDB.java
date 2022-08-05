package com.cst438;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllerDB {
	
	@Autowired
	NameRepository nameRepository;
	
	/*
	 * return list of all names
	 */
	@GetMapping("/name")
	public Object[] getData( ) {
		System.out.println("getData called. Names retrieved are:");
		List<Name> names =  nameRepository.findAll();
		for (Name n: names) {
			System.out.println(n.toString());		 
		}
		Name[] rc = new Name[names.size()];
		System.out.println("getData end " + names.size() + " names listed.");
		return names.toArray(rc);
	}
	
	/*
	 * insert a new name.  Id and OtherData are overwritten with next key value and server ip address.
	 */
	@PostMapping("/name")
	public Name postData(@RequestBody Name name) {
		System.out.printf("postData called with data = %s\n", name.toString());
		name.setId(0);
		name.setOtherData(getIpAddress());
		Name new_name = nameRepository.save(name);
		System.out.printf("postData end.  saved data = %s \n", new_name);
		return new_name;
	}
	
	/* 
	 * health check
	 */
	@GetMapping("/check")
	public String healthCheck() {
		System.out.println("healthCheck");
		return "ok";
	}
	
	/*
	 * terminate the server
	 */
	@GetMapping("/fail")
	public void fail() {
		System.exit(1);
	}
	
	
	private String getIpAddress() {
		try {
			String ip = InetAddress.getLocalHost().toString();
			return ip;
		} catch (UnknownHostException e) {
			return "unknown ip";
		}
	}

}
