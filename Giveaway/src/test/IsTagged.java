package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import giveaway.Comment;

public class IsTagged {

    @Test
    public void onlyTagComment() {

        String poster = "morgan";
        String user = "keegan";
        String input = "@austin";

        Comment comment = new Comment(user, input);
        
        assertTrue(comment.didTagAnotherUser(poster));

    }

    @Test
    public void tagWithinComment() {

        String poster = "morgan";
        String user = "keegan";
        String input = "This is cool! check it out, @austin";
        
        Comment comment = new Comment(user, input);

        assertTrue(comment.didTagAnotherUser(poster));

    }

    @Test
    public void noTagInComment() {

        String poster = "morgan";
        String user = "keegan";
        String input = "This is dumb!";
        
        Comment comment = new Comment(user, input);

        assertFalse(comment.didTagAnotherUser(poster));

    }

    @Test
    public void atSymbolInComment() {

        String poster = "morgan";
        String user = "keegan";
        String input = "I saw you @ that place!";
        
        Comment comment = new Comment(user, input);

        assertFalse(comment.didTagAnotherUser(poster));

    }

    @Test
    public void userTaggedThemselvesInComment() {

        String poster = "morgan";
        String user = "keegan";
        String input = "I like to tag myself! @keegan";
        
        Comment comment = new Comment(user, input);

        assertFalse(comment.didTagAnotherUser(poster));

    }

    @Test
    public void userTaggedPosterInComment() {

        String poster = "morgan";
        String user = "keegan";
        String input = "Great post! @morgan";
        
        Comment comment = new Comment(user, input);

        assertFalse(comment.didTagAnotherUser(poster));

    }

}

