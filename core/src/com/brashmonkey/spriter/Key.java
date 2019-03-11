package com.brashmonkey.spriter;

public class Key {
    public int id;
    public long time;
    public Tag[] tag;
    public int tagPointer = 0;

    public Key(int id, int time, int tagRefs){
        this.id = id;
        this.time = time;
        this.tag = new Tag[tagRefs];
    }

    public void addTag(Tag tag) {
        this.tag[tagPointer++] = tag;
    }

    static class Tag {
        String name;

        int id;
        int t;

        public Tag(int id, int t){
            this.id = id;
            this.t = t;
        }
    }
}
