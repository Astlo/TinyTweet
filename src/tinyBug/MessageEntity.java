package tinyBug;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class MessageEntity {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key key;

	@NotPersistent
	UserEntity sender;
	
	@Persistent 
	String message;
	
	@Persistent
	Date date;
	
	public Key getKey() 
	{
		return key;
	}

	public void setKey(Key key) 
	{
		this.key = key;
	}

	public UserEntity getSender() 
	{
		return sender;
	}

	public void setSender(UserEntity sender) 
	{
		this.sender = sender;
	}

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}

	public Date getDate() 
	{
		return date;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}

}
