package giveaway;

public class Comment {

	private String user;
	private String comment;
	
	public Comment(String user, String comment) {
		this.user = user;
		this.comment = comment;
	}
	
	 /**
     * Returns true if a user tags another user in a
     * comment.
     *
     * @param comment the comment to look for tags in
     * @return true if the comment contains a tag.
     */
    public boolean didTagAnotherUser(String poster) {

        String[] words = comment.split(" ");

        for(String word : words) {
            if (word.startsWith("@") && word.length() > 1) {
                String taggedUser = word.substring(1);
                if (!taggedUser.equals(poster) && !taggedUser.equals(user))
                    return true;
            }
        }

        return false;
    }
	
}
