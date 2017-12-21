package tinyBug;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class UserEntity {
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	Long key;

	@Persistent
	String pseudo;
	
	//si ça marche plus, mettez les NotPersistent en Persistent puis refaites l'inverse
	@Persistent 
	Set<Long> abonnes = new HashSet<Long>();
	
	@Persistent
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	Set<Long> abonnements = new HashSet<Long>();
	
	@Persistent
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	List<MessageEntity> timeline = new ArrayList<MessageEntity>();
	
	public Long getKey()
	{
		return key;
	}

	public void setKey(Long key) 
	{
		this.key = key;
	}
	
	public String getPseudo() 
	{
		return pseudo;
	}

	public void setPseudo(String pseudo) 
	{
		this.pseudo = pseudo;
	}

	public Set<Long> getAbonnes() 
	{
		return abonnes;
	}

	public void setAbonnes(Set<Long> abonnes) 
	{
		this.abonnes = abonnes;
	}

	public Set<Long> getAbonnements() 
	{
		return abonnements;
	}

	public void setAbonnements(Set<Long> abonnements) 
	{
		this.abonnements = abonnements;
	}

	public void ajoutAbonnements(Long e)
	{
		this.abonnements.add(e);
	}
	
	public void ajoutAbonnes(Long e)
	{
		this.abonnes.add(e);
	}
	
	public List<MessageEntity> getTimeline() 
	{
		return timeline;
	}

	public void setTimeline(List<MessageEntity> timeline) 
	{
		this.timeline = timeline;
	}
	
	public void ajoutTimeline(MessageEntity message)
	{
		this.timeline.add(message);
	}
	
}
