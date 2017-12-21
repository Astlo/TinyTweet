package tinyBug;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class MessageIndexEntity {
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	Long key;
	
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Set<Long> receivers;
	
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	MessageEntity tweet;
	
	public MessageEntity getTweet() {
		return tweet;
	}

	public void setTweet(MessageEntity tweet) {
		this.tweet = tweet;
	}

	public Long getKey() 
	{
		return key;
	}

	public void setKey(Long key) 
	{
		this.key = key;
	}

	public Set<Long> getReceivers() 
	{
		return receivers;
	}

	public void setReceivers(Set<Long> receivers) 
	{
		this.receivers = receivers;
	}
	
}