
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.sender.id=?1")
	Collection<Message> senderAllByActor(int idActor);

	@Query("select m from Message m where m.recipient.id=?1")
	Collection<Message> recipientAllByActor(int idActor);
	@Query("select m from Message m where m.topic=?1 and (m.sender.id=?2 or m.recipient.id=?2)")
	Collection<Message> listingByTopicActor(String topic, int idActor);
	@Query("select m from Message m where m.priority=?1 and (m.sender.id=?2 or m.recipient.id=?2)")
	Collection<Message> listingByPriorityActor(String priority, int idActor);
	
}
