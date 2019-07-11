package com.brashmonkey.spriter;

/**
 * A spriterPlayer tweener is responsible for tweening to {@link SpriterPlayer} instances.
 * Such a 
 * @author Trixt0r
 *
 */
public class SpriterPlayerTweener extends SpriterPlayer {
	
	private TweenedAnimation anim;
	private SpriterPlayer spriterPlayer1, spriterPlayer2;
	/**
	 * Indicates whether to update the {@link SpriterPlayer} instances this instance is holding.
	 * If this variable is set to <code>false</code>, you will have to call {@link SpriterPlayer#update()} on your own.
	 */
	public boolean updatePlayers = true;
	
	/**
	 * The name of root bone to start the tweening at.
	 * Set it to null to tween the whole hierarchy.
	 */
	public String baseBoneName = null;
	
	/**
	 * Creates a spriterPlayer tweener which will tween the given two players.
	 * @param spriterPlayer1 the first spriterPlayer
	 * @param spriterPlayer2 the second spriterPlayer
	 */
	public SpriterPlayerTweener(SpriterPlayer spriterPlayer1, SpriterPlayer spriterPlayer2){
		super(spriterPlayer1.getEntity());
		this.setPlayers(spriterPlayer1, spriterPlayer2);
	}
	
	/**
	 * Creates a spriterPlayer tweener based on the entity.
	 * The players to tween will be created by this instance.
	 * @param entity the entity the players will animate
	 */
	public SpriterPlayerTweener(Entity entity){
		this(new SpriterPlayer(entity), new SpriterPlayer(entity));
	}
	
	/**
	 * Tweens the current set players.
	 * This method will update the set players if {@link #updatePlayers} is <code>true</code>.
	 * @throws SpriterException if no bone with {@link #baseBoneName} exists
	 */
	@Override
	public void update(){
		if(updatePlayers){
			spriterPlayer1.update();
			spriterPlayer2.update();
		}
		anim.setAnimations(spriterPlayer1.animation, spriterPlayer2.animation);
		super.update();
		if(baseBoneName != null){
			int index = anim.onFirstMainLine()? spriterPlayer1.getBoneIndex(baseBoneName) : spriterPlayer2.getBoneIndex(baseBoneName);
			if(index == -1) throw new SpriterException("A bone with name \""+baseBoneName+"\" does no exist!");
			anim.base = anim.getCurrentKey().getBoneRef(index);
			super.update();
		}
	}
	
	/**
	 * Sets the players for this tweener.
	 * Both players have to hold the same {@link Entity}
	 * @param spriterPlayer1 the first spriterPlayer
	 * @param spriterPlayer2 the second spriterPlayer
	 */
	public void setPlayers(SpriterPlayer spriterPlayer1, SpriterPlayer spriterPlayer2){
		if(spriterPlayer1.entity != spriterPlayer2.entity)
			throw new SpriterException("spriterPlayer1 and spriterPlayer2 have to hold the same entity!");
		this.spriterPlayer1 = spriterPlayer1;
		this.spriterPlayer2 = spriterPlayer2;
		if(spriterPlayer1.entity == entity) return;
		this.anim = new TweenedAnimation(spriterPlayer1.getEntity());
		anim.setAnimations(spriterPlayer1.animation, spriterPlayer2.animation);
		super.setEntity(spriterPlayer1.getEntity());
		super.setAnimation(anim);
	}
	
	/**
	 * Returns the first set spriterPlayer.
	 * @return the first spriterPlayer
	 */
	public SpriterPlayer getFirstPlayer(){
		return this.spriterPlayer1;
	}
	
	/**
	 * Returns the second set spriterPlayer.
	 * @return the second spriterPlayer
	 */
	public SpriterPlayer getSecondPlayer(){
		return this.spriterPlayer2;
	}
	
	/**
	 * Sets the interpolation weight of this tweener.
	 * @param weight  the interpolation weight between 0.0f  and 1.0f
	 */
	public void setWeight(float weight){
		this.anim.weight = weight;
	}
	
	/**
	 * Returns the interpolation weight.
	 * @return the interpolation weight between 0.0f  and 1.0f
	 */
	public float getWeight(){
		return this.anim.weight;
	}
	
	
	/**
	 * Sets the base animation of this tweener.
	 * Has only an effect if {@link #baseBoneName} is not <code>null</code>. 
	 * @param anim the base animation
	 */
	public void setBaseAnimation(Animation anim){
		this.anim.baseAnimation = anim;
	}
	
	/**
	 * Sets the base animation of this tweener by the given animation index.
	 * Has only an effect if {@link #baseBoneName} is not <code>null</code>. 
	 * @param index the index of the base animation
	 */
	public void setBaseAnimation(int index){
		this.setBaseAnimation(entity.getAnimation(index));
	}
	
	/**
	 * Sets the base animation of this tweener by the given name.
	 * Has only an effect if {@link #baseBoneName} is not <code>null</code>. 
	 * @param name the name of the base animation
	 */
	public void setBaseAnimation(String name){
		this.setBaseAnimation(entity.getAnimation(name));
	}
	
	/**
	 * Returns the base animation if this tweener.
	 * @return the base animation
	 */
	public Animation getBaseAnimation(){
		return this.anim.baseAnimation;
	}
	
	/**
	 * Not supported by this class.
	 */
	@Override
	public void setAnimation(Animation anim){}
	
	/**
	 * Not supported by this class.
	 */
	@Override
	public void setEntity(Entity entity){}
}
