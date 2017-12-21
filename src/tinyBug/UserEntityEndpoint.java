package tinyBug;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

@Api(name = "userentityendpoint", namespace = @ApiNamespace(ownerDomain = "mycompany.com", ownerName = "mycompany.com", packagePath = "services"))
public class UserEntityEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUserEntity")
	public CollectionResponse<UserEntity> listUserEntity(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) 
	{

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<UserEntity> execute = null;

		try 
		{
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(UserEntity.class);
			if (cursorString != null && cursorString != "") 
			{
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) 
			{
				query.setRange(0, limit);
			}

			execute = (List<UserEntity>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (UserEntity obj : execute)
				;
		} 
		finally 
		{
			mgr.close();
		}

		return CollectionResponse.<UserEntity> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	 * This method gets the entity having primary Long id. It uses HTTP GET method.
	 *
	 * @param id the primary Long of the java bean.
	 * @return The entity with primary Long id.
	 */
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "getUserEntity")
	public UserEntity getUserEntity(@Named("id") Long id) 
	{
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(UserEntity.class);

		query.setFilter("key == " + id);

		List<UserEntity> result = (List<UserEntity>) pm.newQuery(query).execute();

		if (result.size() == 0)
			return null;

		UserEntity userentity = result.get(0);
		return userentity;
	}
	
	
	

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param userentity the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUserEntity")
	public UserEntity insertUserEntity(UserEntity userentity) 
	{
		PersistenceManager mgr = getPersistenceManager();
		try 
		{
			if (containsUserEntity(userentity)) 
			{
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(userentity);
		} 
		finally 
		{
			mgr.close();
		}
		return userentity;
	}
	
	
	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param userentity the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUserEntity")
	public UserEntity updateUserEntity(UserEntity userentity) 
	{
		PersistenceManager mgr = getPersistenceManager();
		try 
		{
			if (!containsUserEntity(userentity)) 
			{
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(userentity);
		} 
		finally 
		{
			mgr.close();
		}
		return userentity;
	}

	/**
	 * This method removes the entity with primary Long id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary Long of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUserEntity")
	public void removeUserEntity(@Named("id") Long id) 
	{
		PersistenceManager mgr = getPersistenceManager();
		try 
		{
			UserEntity userentity = mgr.getObjectById(UserEntity.class, id);
			mgr.deletePersistent(userentity);
		} 
		finally 
		{
			mgr.close();
		}
	}

	private boolean containsUserEntity(UserEntity userentity) 
	{
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try 
		{
			mgr.getObjectById(UserEntity.class, userentity.getKey());
		} 
		catch (javax.jdo.JDOObjectNotFoundException ex) 
		{
			contains = false;
		} 
		finally 
		{
			mgr.close();
		}
		return contains;
	}
	
	@ApiMethod(name = "suivreUserEntity")
	public UserEntity suivreUserEntity(@Named("idAbonnes") Long idAbonnes, @Named("idAbonnements") Long idAbonnements) throws Exception
	{
		UserEntity abonnes = getUserEntity(idAbonnes);
		UserEntity abonnement = getUserEntity(idAbonnements);

		abonnes.getAbonnements().add(idAbonnements);
		abonnement.getAbonnes().add(idAbonnes);

		return abonnes;
		
	}
	
	@ApiMethod(name = "insertMessageEntity")
	public MessageEntity insertMessageEntity(MessageEntity messageentity) 
	{
		PersistenceManager mgr = getPersistenceManager();
		try 
		{
			if (containsMessageEntity(messageentity)) 
			{
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(messageentity);
		} 
		finally 
		{
			mgr.close();
		}
		return messageentity;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listMessageEntity")
	public CollectionResponse<MessageEntity> listMessageEntity(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) 
	{

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<MessageEntity> execute = null;

		try 
		{
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(MessageEntity.class);
			if (cursorString != null && cursorString != "") 
			{
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) 
			{
				query.setRange(0, limit);
			}

			execute = (List<MessageEntity>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (MessageEntity obj : execute)
				;
		} 
		finally 
		{
			mgr.close();
		}

		return CollectionResponse.<MessageEntity> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listMessageIndexEntity")
	public CollectionResponse<MessageIndexEntity> listMessageIndexEntity(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) 
	{

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<MessageIndexEntity> execute = null;

		try 
		{
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(MessageIndexEntity.class);
			if (cursorString != null && cursorString != "") 
			{
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) 
			{
				query.setRange(0, limit);
			}

			execute = (List<MessageIndexEntity>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (MessageIndexEntity obj : execute)
				;
		} 
		finally 
		{
			mgr.close();
		}

		return CollectionResponse.<MessageIndexEntity> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	@ApiMethod(name = "getMessageEntity")
	public MessageEntity getMessageEntity(@Named("id") Long id) 
	{
		PersistenceManager mgr = getPersistenceManager();
		MessageEntity messageentity = null;
		try 
		{
			messageentity = mgr.getObjectById(MessageEntity.class, id);
		} 
		finally 
		{
			mgr.close();
		}
		return messageentity;
	}
	
	@ApiMethod(name = "tweeter")
	public MessageEntity tweeter(@Named("message") String message, @Named("iduser") Long id) throws Exception
	{
		UserEntity user = getUserEntity(id);
		
		MessageEntity tweet = new MessageEntity();

		tweet.setDate(new Date());
		tweet.setMessage(message);
		tweet.setSender(user);

		
		MessageIndexEntity index = new MessageIndexEntity();
		
		index.setReceivers(user.getAbonnes());
		index.setTweet(tweet);
		
		UserEntity abonnes;
		for(Long i : user.getAbonnes())
		{
			abonnes = getUserEntity(i);
			abonnes.ajoutTimeline(tweet);
		}
		
		PersistenceManager pm = getPersistenceManager();
		Transaction xt = pm.currentTransaction();
		
		try
		{
			xt.begin();
			pm.makePersistent(index);	
			xt.commit();
		}
		finally
		{
			if( xt.isActive())
			{
				xt.rollback();
			}
		}

		pm.close();
		
		return tweet;
	}
	
	@ApiMethod(name = "timelineUserEntity")
	@SuppressWarnings("unchecked")
	public List<MessageEntity> timelineUserEntity(@Named("iduser") Long id)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(MessageIndexEntity.class);

		query.setFilter("receivers == " + id);

		List<MessageIndexEntity> indexes = (List<MessageIndexEntity>) pm.newQuery(query).execute();

		List<MessageEntity> timeline = new ArrayList<MessageEntity>();
		
		for(MessageIndexEntity index : indexes)
		{
			timeline.add(index.getTweet());
		}
		
		
		return timeline;
	}

	private boolean containsMessageEntity(MessageEntity messageentity) 
	{
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try 
		{
			mgr.getObjectById(MessageEntity.class, messageentity.getKey());
		} 
		catch (javax.jdo.JDOObjectNotFoundException ex) 
		{
			contains = false;
		} 
		finally 
		{
			mgr.close();
		}
		return contains;
	}
	
	private static PersistenceManager getPersistenceManager() 
	{
		return PMF.get().getPersistenceManager();
	}

}
