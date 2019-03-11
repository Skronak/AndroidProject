package com.guco.tap.actor;

import com.guco.tap.Animation.AnimatedBaseActor;

public class BeamActor extends AnimatedBaseActor {

    private StationActor stationActor;

    public BeamActor(StationActor stationActor){
        this.stationActor=stationActor;
    }

    @Override
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        setPosition(stationActor.getX()+85, stationActor.getY()-getHeight()+20);
    }

}
