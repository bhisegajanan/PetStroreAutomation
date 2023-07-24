package api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payloads.User;
import io.restassured.response.Response;

public class Usertests {
	
	Faker fake;
	User userpayload;
	@BeforeClass
	public void SetupData()
	{
		fake=new Faker();
		userpayload=new User();
		
		userpayload.setId(fake.idNumber().hashCode());
		userpayload.setUsername(fake.name().username());
		userpayload.setFirstname(fake.name().firstName());
		userpayload.setLastname(fake.name().lastName());
		userpayload.setEmail(fake.internet().safeEmailAddress());
		userpayload.setPassword(fake.internet().password());
		userpayload.setPhone(fake.phoneNumber().cellPhone());
				
	}
	
	@Test(priority=1)
	public void TestPostUser()
	{
	Response response= UserEndpoints.createUser(userpayload);
	response.then().log().all();
	Assert.assertEquals(response.getStatusCode(),200);
	
	}
	
	@Test(priority=2)
	public void TestGetUserbyName()
	{
		Response response=UserEndpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}

	@Test(priority=3)
	public void TestUpdateUserbyName()
	{
		userpayload.setFirstname(fake.name().firstName());
		userpayload.setLastname(fake.name().lastName());
		userpayload.setEmail(fake.internet().safeEmailAddress());
	Response response= UserEndpoints.updateUser(this.userpayload.getUsername(),userpayload);
	response.then().log().all();
	Assert.assertEquals(response.getStatusCode(),200);
	//checking the data after update
	Response responseafterupdate=UserEndpoints.readUser(this.userpayload.getUsername());
	responseafterupdate.then().log().all();
	responseafterupdate.then().statusCode(200);
	}
	@Test(priority=4)
	public void TestDeleteUserbyName()
	{
		Response response=UserEndpoints.deleteUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}

}
