package com.brashmonkey.spriter;

import java.util.HashMap;
import java.util.Map.Entry;

import com.brashmonkey.spriter.Mainline.Key.BoneRef;
import com.brashmonkey.spriter.Timeline.Key.Bone;

/**
 * A IKResolver is responsible for resolving previously set constraints.
 * @see <a href="http://en.wikipedia.org/wiki/Inverse_kinematics"> Inverse kinematics</a>
 * @author Trixt0r
 *
 */
public abstract class IKResolver {
	
	/**
	 * Resolves the inverse kinematics constraint with a specific algtorithm
	 * @param x the target x value
	 * @param y the target y value
	 * @param chainLength number of parents which are affected
	 * @param effector the actual effector where the resolved information has to be stored in.
	 */
	protected abstract void resolve(float x, float y, int chainLength, BoneRef effector);
	
	protected HashMap<IKObject, BoneRef> ikMap;
	protected float tolerance;
	protected SpriterPlayer spriterPlayer;

	/**
	 * Creates a resolver with a default tolerance of 5f.
	 */
	public IKResolver(SpriterPlayer spriterPlayer) {
		this.tolerance = 5f;
		this.ikMap = new HashMap<IKObject, BoneRef>();
		this.setSpriterPlayer(spriterPlayer);
	}
	
	/**
	 * Sets the spriterPlayer for this resolver.
	 * @param spriterPlayer the spriterPlayer which gets affected.
	 * @throws SpriterException if spriterPlayer is <code>null</code>
	 */
	public void setSpriterPlayer(SpriterPlayer spriterPlayer){
		if(spriterPlayer == null) throw new SpriterException("spriterPlayer cannot be null!");
		this.spriterPlayer = spriterPlayer;
	}
	
	/**
	 * Returns the current set spriterPlayer.
	 * @return the current spriterPlayer.
	 */
	public SpriterPlayer getSpriterPlayer(){
		return this.spriterPlayer;
	}
	
	/**
	 * Resolves the inverse kinematics constraints with the implemented algorithm in {@link #resolve(float, float, int, SpriterAbstractObject, SpriterAbstractPlayer)}.
	 * @param player spriterPlayer to apply the resolving.
	 */
	public void resolve(){
		for(Entry<IKObject, BoneRef> entry: this.ikMap.entrySet()){
			for(int j = 0; j < entry.getKey().iterations; j++)
				this.resolve(entry.getKey().x, entry.getKey().y, entry.getKey().chainLength, entry.getValue());
		}
	}
	
	/**
	 * Adds the given object to the internal IKObject - Bone map.
	 * This means, the values of the given ik object affect the mapped bone.
	 * @param ikObject the ik object
	 * @param boneRef the bone reference which gets affected
	 */
	public void mapIKObject(IKObject ikObject, BoneRef boneRef){
		this.ikMap.put(ikObject, boneRef);
	}
	
	/**
	 * Adds the given object to the internal IKObject - Bone map.
	 * This means, the values of the given ik object affect the mapped bone.
	 * @param ikObject the ik object
	 * @param bone the bone which gets affected
	 */
	public void mapIKObject(IKObject ikObject, Bone bone){
		this.ikMap.put(ikObject, spriterPlayer.getBoneRef(bone));
	}
	
	/**
	 * Removes the given object from the internal map.
	 * @param ikObject the ik object to remove
	 */
	public void unmapIKObject(IKObject ikObject){
		this.ikMap.remove(ikObject);
	}
	
	/**
	 * Returns the tolerance of this resolver.
	 * @return the tolerance
	 */
	public float getTolerance() {
		return tolerance;
	}

	/**
	 * Sets the tolerance distance of this resolver.
	 * The resolver should stop the algorithm if the distance to the set ik object is less than the tolerance.
	 * @param tolerance the tolerance
	 */
	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}

}
