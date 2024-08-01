package com.springrest.postsandcomments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.springrest.postsandcomments.entities.Comment;
import com.springrest.postsandcomments.entities.Post;
import com.springrest.postsandcomments.repo.CommentRepository;
import com.springrest.postsandcomments.repo.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import jakarta.transaction.Transactional;

@SpringBootApplication
@EnableJpaAuditing
public class PostsandcommentsApplication implements CommandLineRunner {

	@Autowired
	PostRepository postRepository;

	@Autowired
	CommentRepository commentRepository;

	public static void main(String[] args) {
		SpringApplication.run(PostsandcommentsApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		clearData();
		saveData();
		showData();
	}

	@Transactional
	private void clearData() {
		postRepository.deleteAll();
		commentRepository.deleteAll();
	}

	@Transactional
	private void saveData() {
		savePostAndCommentData();
	}

	// Save Post objects that include Comment
	private void savePostAndCommentData() {
		// Create some Posts
		Post post1 = new Post("First Post Title",
				"One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided",
				"One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. <br/>"
						+ "<br/>"
						+ "He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. <br/>"
						+ "<br/>"
						+ "His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. \"What's happened to me? \" he thought. It wasn't a dream.</br>"
						+ "<br/>"
						+ "His room, a proper human room although a little too small, lay peacefully between its four familiar walls. A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer. Gregor then turned to look out the window at the dull weather. ");

		Post post2 = new Post("Second Post Title",
				"By arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. What's happened to me?",
				"Drops of rain could be heard hitting the pane, which made him feel quite sad.<br/>" + "<br/>"
						+ "How about if I sleep a little bit longer and forget all this nonsense\", he thought, but that was something he was unable to do because he was used to sleeping on his right, and in his present state couldn't get into that position. However hard he threw himself onto his right, he always rolled back to where he was</br>"
						+ "<br/>"
						+ "He must have tried it a hundred times, shut his eyes so that he wouldn't have to look at the floundering legs, and only stopped when he began to feel a mild, dull pain there that he had never felt before. \"Oh, God\", he thought, \"what a strenuous career it is that I've chosen! Travelling day in and day out. Doing business like this takes much more effort than doing your own business at home, and on top of that there's the curse of travelling, worries about making train connections, bad and irregular food, contact with different  people all the time so that you can never get to know anyone or become friendly with them. It can all go to Hell!");

		Post post3 = new Post("Third Post Title",
				"He thought. It wasn't a dream. His room, a proper human room although a little too small, lay peacefully between its four familiar walls. A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it",
				"He felt a slight itch up on his belly; pushed himself slowly up on his back towards the headboard so that he could lift his head better; found where the itch was, and saw that it was covered with lots of little white spots which he didn't know what to make of; and when he tried to feel the place with one of his legs he drew it quickly back because as soon as he touched it he was overcome by a cold shudder.<br/>"
						+ "<br/>"
						+ "He slid back into his former position. \"Getting up early all the time\", he thought, \"it makes you stupid. You've got to get enough sleep. Other travelling salesmen live a life of luxury.<br/>"
						+ "<br/>"
						+ "For instance, whenever I go back to the guest house during the morning to copy out the contract, these gentlemen are always still sitting there eating their breakfasts. I ought to just try that with my boss; I'd get kicked out on the spot. But who knows, maybe that would be the best thing for me. If I didn't have my parents to think about I'd have given in my notice a long time ago.");

		Post post4 = new Post("Fourth Post Title",
				"One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided",
				"One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. <br/>"
						+ "<br/>"
						+ "He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. <br/>"
						+ "<br/>"
						+ "His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. \"What's happened to me? \" he thought. It wasn't a dream.</br>"
						+ "<br/>"
						+ "His room, a proper human room although a little too small, lay peacefully between its four familiar walls. A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer. Gregor then turned to look out the window at the dull weather. ");

		Post post5 = new Post("Fifth Post Title",
				"By arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. What's happened to me?",
				"Drops of rain could be heard hitting the pane, which made him feel quite sad.<br/>" + "<br/>"
						+ "How about if I sleep a little bit longer and forget all this nonsense\", he thought, but that was something he was unable to do because he was used to sleeping on his right, and in his present state couldn't get into that position. However hard he threw himself onto his right, he always rolled back to where he was</br>"
						+ "<br/>" + "<br/>"
						+ "He must have tried it a hundred times, shut his eyes so that he wouldn't have to look at the floundering legs, and only stopped when he began to feel a mild, dull pain there that he had never felt before. \"Oh, God\", he thought, \"what a strenuous career it is that I've chosen! Travelling day in and day out. Doing business like this takes much more effort than doing your own business at home, and on top of that there's the curse of travelling, worries about making train connections, bad and irregular food, contact with different  people all the time so that you can never get to know anyone or become friendly with them. It can all go to Hell!");

		Post post6 = new Post("Sixth Post Title",
				"By arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. What's happened to me?",
				"Drops of rain could be heard hitting the pane, which made him feel quite sad.<br/>" + "<br/>"
						+ "How about if I sleep a little bit longer and forget all this nonsense\", he thought, but that was something he was unable to do because he was used to sleeping on his right, and in his present state couldn't get into that position. However hard he threw himself onto his right, he always rolled back to where he was</br>"
						+ "<br/>" + "<br/>"
						+ "He must have tried it a hundred times, shut his eyes so that he wouldn't have to look at the floundering legs, and only stopped when he began to feel a mild, dull pain there that he had never felt before. \"Oh, God\", he thought, \"what a strenuous career it is that I've chosen! Travelling day in and day out. Doing business like this takes much more effort than doing your own business at home, and on top of that there's the curse of travelling, worries about making train connections, bad and irregular food, contact with different  people all the time so that you can never get to know anyone or become friendly with them. It can all go to Hell!");
		// Save Post
		postRepository.save(post1);
		postRepository.save(post2);
		postRepository.save(post3);
		postRepository.save(post4);
		postRepository.save(post5);
		postRepository.save(post6);

		// Create some Comments
		Comment comment1 = new Comment("Comment 1 to Post 1: Great Post!", "user01");
		comment1.setPost(post1);

		Comment comment2 = new Comment("Comment 2 to Post 1: " + "it doesn't work for me :(", "user02");
		comment2.setPost(post1);

		Comment comment3 = new Comment("Comment 3 to Post 1: Finally. Thanks!", "user03");
		comment3.setPost(post1);

		Comment comment4 = new Comment("Comment 4 to Post 1", "user04");
		comment4.setPost(post1);

		Comment comment5 = new Comment("Comment 5 to Post 1", "user05");
		comment5.setPost(post1);
		
		Comment comment6 = new Comment("Comment 6 to Post 1", "user06");
		comment6.setPost(post1);

		Comment comment7= new Comment("Comment 7 to Post 1", "user07");
		comment7.setPost(post1);
		
		Comment comment8= new Comment("Comment 8 to Post 1", "user08");
		comment8.setPost(post1);
		
		Comment comment9 = new Comment("Comment 1 to Post 2: \u2764 \u2764 \u2764", "user04");
		comment9.setPost(post2);
		
		// Comments related to Post entity
		commentRepository.save(comment1);
		commentRepository.save(comment2);
		commentRepository.save(comment3);
		commentRepository.save(comment4);
		commentRepository.save(comment5);
		commentRepository.save(comment6);
		commentRepository.save(comment7);
		commentRepository.save(comment8);
		commentRepository.save(comment9);
	}

	@Transactional
	private void showData() {
		// get all data
		List<Post> postList = (List<Post>) postRepository.findAll();
		List<Comment> commentList = (List<Comment>) commentRepository.findAll();

		System.out.println("");
		System.out.println("=================== Comment List: ==================");
		commentList.forEach(System.out::println);

		System.out.println("");
		System.out.println("=================== Post List: ==================");
		// postList.forEach(System.out::println);
		postList.forEach(post -> System.out.println(post.toString()));
	}

}
