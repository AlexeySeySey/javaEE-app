package entity;

import contract.IEntity;

public final class Token implements IEntity {
	
	private long id;
	
	private long userId;
	
	private String token;
	
	private String expiredAt;
	
	public Token(long id, long userId, String token, String expiredAt) {
		this.id = id;
		this.userId = userId;
		this.token = token;
		this.expiredAt = expiredAt;
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getExpiredAt() {
		return this.expiredAt;
	}
}
