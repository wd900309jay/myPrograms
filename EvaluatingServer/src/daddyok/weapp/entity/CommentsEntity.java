package daddyok.weapp.entity;


public class CommentsEntity {
	private String Content;
	
	private String CreateTime;
	
	private int StarLevel;
	
	private int LikeCount;
	
	private String nickName;
	
	private String avataUrl;
	
	private int CommentId;
	
	private int EnvLevel;
	
	private int DevLevel;
	
	private int AirLevel;
	
	
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public int getStarLevel() {
		return StarLevel;
	}

	public void setStarLevel(int starLevel) {
		StarLevel = starLevel;
	}

	public int getLikeCount() {
		return LikeCount;
	}

	public void setLikeCount(int likeCount) {
		LikeCount = likeCount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvataUrl() {
		return avataUrl;
	}

	public void setAvataUrl(String avataUrl) {
		this.avataUrl = avataUrl;
	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	public int getEnvLevel() {
		return EnvLevel;
	}

	public void setEnvLevel(int envLevel) {
		EnvLevel = envLevel;
	}

	public int getDevLevel() {
		return DevLevel;
	}

	public void setDevLevel(int devLevel) {
		DevLevel = devLevel;
	}

	public int getAirLevel() {
		return AirLevel;
	}

	public void setAirLevel(int airLevel) {
		AirLevel = airLevel;
	}
	
	
}
